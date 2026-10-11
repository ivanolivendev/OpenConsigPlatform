package com.openconsigplatform.access.application;

import java.util.Set;

public record AuthorizationRequest(
        String subject,
        Set<String> requestedScopes
) {

    public AuthorizationRequest {
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Authorization subject is required");
        }
        requestedScopes = Set.copyOf(requestedScopes == null ? Set.of() : requestedScopes);
    }
}
