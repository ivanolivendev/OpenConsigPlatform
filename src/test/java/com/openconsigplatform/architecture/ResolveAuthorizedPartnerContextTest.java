package com.openconsigplatform.architecture;

import com.openconsigplatform.access.application.AuthorizationRequest;
import com.openconsigplatform.access.application.PartnerAuthorizationPort;
import com.openconsigplatform.access.application.ResolveAuthorizedPartnerContext;
import com.openconsigplatform.access.application.UnauthorizedAccessException;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResolveAuthorizedPartnerContextTest {

    private static final UUID TENANT_ID = UUID.randomUUID();
    private static final UUID PARTNER_ID = UUID.randomUUID();
    private static final UUID APPLICATION_ID = UUID.randomUUID();

    @Test
    void shouldResolveAuthorizedPartnerContext() {
        var useCase = new ResolveAuthorizedPartnerContext(subject -> Optional.of(
                new PartnerAuthorizationPort.PartnerAuthorization(
                        TENANT_ID, PARTNER_ID, APPLICATION_ID, true, Set.of("credit:read")
                )));

        var result = useCase.execute(new AuthorizationRequest("application-1", Set.of("credit:read")));

        assertEquals(TENANT_ID, result.tenantId());
        assertEquals(PARTNER_ID, result.partnerId());
        assertEquals(Set.of("credit:read"), result.scopes());
    }

    @Test
    void shouldRejectUnknownSubject() {
        var useCase = new ResolveAuthorizedPartnerContext(subject -> Optional.empty());

        assertThrows(UnauthorizedAccessException.class, () ->
                useCase.execute(new AuthorizationRequest("unknown", Set.of())));
    }

    @Test
    void shouldRejectInactiveApplication() {
        var useCase = new ResolveAuthorizedPartnerContext(subject -> Optional.of(
                new PartnerAuthorizationPort.PartnerAuthorization(
                        TENANT_ID, PARTNER_ID, APPLICATION_ID, false, Set.of("credit:read")
                )));

        assertThrows(UnauthorizedAccessException.class, () ->
                useCase.execute(new AuthorizationRequest("application-1", Set.of("credit:read"))));
    }

    @Test
    void shouldRejectUnauthorizedScope() {
        var useCase = new ResolveAuthorizedPartnerContext(subject -> Optional.of(
                new PartnerAuthorizationPort.PartnerAuthorization(
                        TENANT_ID, PARTNER_ID, APPLICATION_ID, true, Set.of("credit:read")
                )));

        assertThrows(UnauthorizedAccessException.class, () ->
                useCase.execute(new AuthorizationRequest("application-1", Set.of("credit:write"))));
    }
}
