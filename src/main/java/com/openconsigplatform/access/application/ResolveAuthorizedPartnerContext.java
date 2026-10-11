package com.openconsigplatform.access.application;

import com.openconsigplatform.access.domain.AuthorizedPartnerContext;

public final class ResolveAuthorizedPartnerContext {

    private final PartnerAuthorizationPort authorizationPort;

    public ResolveAuthorizedPartnerContext(PartnerAuthorizationPort authorizationPort) {
        this.authorizationPort = authorizationPort;
    }

    public AuthorizedPartnerContext execute(AuthorizationRequest request) {
        var authorization = authorizationPort.findBySubject(request.subject())
                .filter(PartnerAuthorizationPort.PartnerAuthorization::active)
                .orElseThrow(() -> new UnauthorizedAccessException("Partner application is not authorized"));

        if (!authorization.allowedScopes().containsAll(request.requestedScopes())) {
            throw new UnauthorizedAccessException("Requested scopes are not authorized");
        }

        return new AuthorizedPartnerContext(
                authorization.tenantId(),
                authorization.partnerId(),
                authorization.applicationId(),
                request.requestedScopes()
        );
    }
}
