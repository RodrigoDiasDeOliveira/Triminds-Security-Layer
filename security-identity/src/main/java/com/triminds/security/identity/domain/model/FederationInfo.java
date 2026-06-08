package com.triminds.security.identity.domain.model;
import java.util.Objects;
public record FederationInfo(FederationProvider provider, String externalId, String issuer, String subject) {
    public FederationInfo {
        Objects.requireNonNull(provider); Objects.requireNonNull(externalId);
        Objects.requireNonNull(issuer);   Objects.requireNonNull(subject);
        if (externalId.isBlank()) throw new IllegalArgumentException("externalId não pode ser vazio");
    }
    public enum FederationProvider { SAML2, OIDC, LDAP, GOOGLE, MICROSOFT_ENTRA, GITHUB }
}
