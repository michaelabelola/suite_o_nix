package com.suiteonix.nix.spi.Auth;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.suiteonix.nix.shared.ValueObjects.Email;
import com.suiteonix.nix.shared.ValueObjects.Phone;
import com.suiteonix.nix.shared.audit.AuditSection;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;

public record AuthUser(
        @JsonUnwrapped
        NixID id,
        @JsonUnwrapped
        NixRole role,
        @JsonUnwrapped(prefix = "email_")
        Email email,
        @JsonUnwrapped(prefix = "phone_")
        Phone phone,
        @JsonUnwrapped(prefix = "owner_")
        NixID ownerId,
        AuditSection audit
) {
}
