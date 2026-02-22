package com.suiteonix.nix.Auth.service;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Login")
public class Login {

    @Schema(name = "Login.Request")
    public record Request(
            @Schema(example = "user@example.com") String email,
            @Schema(example = "+1234567890") String phone,
            @Schema(example = "SecurePass123!") String password,
            Long orgID
    ) {}

    @Schema(name = "Login.Response")
    public record Response(
            String accessToken,
            String refreshToken,
            String tokenType
    ) {}

    private Login() {}
}
