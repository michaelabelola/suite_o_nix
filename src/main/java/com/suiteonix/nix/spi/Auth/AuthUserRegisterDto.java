package com.suiteonix.nix.spi.Auth;

import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import io.swagger.v3.oas.annotations.media.Schema;

public record AuthUserRegisterDto(
        NixID id,
        @Schema(example = "CUSTOMER")
        NixRole role,
        @Schema(example = "michaelabelola@gmail.com")
        String email,
        @Schema(example = "+1234567890")
        String phone,
        @Schema(example = "SecurePass123!")
        String password
) {
}
