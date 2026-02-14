package com.suiteonix.nix.kernel.security.jwt;

import java.security.Key;

public interface JwtProperties {

    Key getSigningKey();

    String ID = "ID";
    String ROLE = "R";
    String ACTOR_ID = "AI";
    String ACTOR_ROLE = "AR";

    String ACCOUNT_NON_EXPIRED = "ae";
    String ACCOUNT_NON_LOCKED = "al";
    String CREDENTIAL_NON_EXPIRED = "ce";
    String ENABLED = "e";

    long getExpirationMs();

    long getRefreshTokenExpirationMs();

    long getEmailVerificationTTL();

    String getIssuer();
}
