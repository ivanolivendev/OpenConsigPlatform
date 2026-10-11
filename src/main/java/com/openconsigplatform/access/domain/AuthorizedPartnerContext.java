package com.openconsigplatform.access.domain;

import java.util.Set;
import java.util.UUID;

public record AuthorizedPartnerContext(
        UUID tenantId,
        UUID partnerId,
        UUID applicationId,
        Set<String> scopes
) {

    public AuthorizedPartnerContext {
        if (tenantId == null || partnerId == null || applicationId == null) {
            throw new IllegalArgumentException("Authorization identifiers are required");
        }
        scopes = Set.copyOf(scopes == null ? Set.of() : scopes);
    }
}
