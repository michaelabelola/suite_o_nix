package com.suiteonix.nix.Auth;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.suiteonix.nix.shared.ValueObjects.Email;
import com.suiteonix.nix.shared.ValueObjects.Password;
import com.suiteonix.nix.shared.ValueObjects.Phone;
import com.suiteonix.nix.shared.audit.AuditSection;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;

public record AuthUser(
        @JsonUnwrapped
        NixID id,
        NixRole role,
        Email email,
        Phone phone,
        Password password,
        @JsonUnwrapped(prefix = "owner_")
        NixID ownerId,
        AuditSection audit
) {
}
