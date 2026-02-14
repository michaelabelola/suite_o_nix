package com.suiteonix.nix.Auth;

import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;

public record AuthUserRegisterDto(
        NixID id,
        NixRole role,
        String email,
        String phone,
        String password
) {
}
