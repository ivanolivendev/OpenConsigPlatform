package com.openconsigplatform.access.application;

import java.util.Optional;

public interface PartnerAuthorizationPort {

    Optional<PartnerAuthorization> findBySubject(String subject);

    record PartnerAuthorization(
            java.util.UUID tenantId,
            java.util.UUID partnerId,
            java.util.UUID applicationId,
            boolean active,
            java.util.Set<String> allowedScopes
    ) {
        public PartnerAuthorization {
            allowedScopes = java.util.Set.copyOf(allowedScopes == null ? java.util.Set.of() : allowedScopes);
        }
    }
}
