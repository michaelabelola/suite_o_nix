package com.suiteonix.nix.Auth.internal.api;

import com.suiteonix.nix.Auth.internal.domain.services.AuthModule;
import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserMapper;
import com.suiteonix.nix.Auth.service.AuthProfile;
import com.suiteonix.nix.Auth.service.Login;
import com.suiteonix.nix.shared.NixModule;
import com.suiteonix.nix.shared.permissions.system.annotations.Allow;
import com.suiteonix.nix.shared.permissions.system.annotations.PermissionDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication API")
class AuthController {
    private final AuthModule authModule;

    @GetMapping("{id}")
    AuthProfile.Detailed getAuthProfile(@PathVariable Long id) {
        return AuthUserMapper.INSTANCE.detailed(authModule.getAuthUserById(id));
    }

    @Operation(summary = "Verify email via JWT link", description = "Called when the user clicks the verification link in the email. Accepts the JWT token and optional orgID as query params.")
    @GetMapping("/verify-email")
    void verifyEmailByLink(@RequestParam String token,
                           @RequestParam(required = false) String orgID) {
        authModule.verifyEmailByJwt(token, orgID);
    }

    @Operation(summary = "Verify email via OTP code", description = "Accepts the 6-digit OTP code submitted by the user along with their email and optional orgID.")
    @PostMapping("/verify-email")
    void verifyEmailByOtp(@RequestBody VerifyEmailRequest request) {
        authModule.verifyEmailByOtp(request.email(), request.token(), request.orgID());
    }

    @Operation(summary = "Resend email verification", description = "Generates new OTP + JWT tokens and resends the verification email. Accepts email and optional orgID.")
    @PostMapping("/resend-verification")
    void resendVerification(@RequestBody ResendVerificationRequest request) {
        authModule.resendVerification(request.email(), request.orgID());
    }

    //    @PermissionDefinition()
    @Allow("/login")
    @PostMapping("/login")
    Login.Response login(@RequestBody LoginRequest request) {
        return authModule.login(new Login.Request(request.email(), request.phone(), request.password(), request.orgID()));
    }

    @PermissionDefinition(
            name = "LOGOUT",
            id = "logout",
            module = NixModule.AUTH,
            action = "logout"
    )
    @PostMapping("/logout")
    void logout() {
        // TODO: Implement logout logic
    }

    record VerifyEmailRequest(String email, String token, Long orgID) {}

    record ResendVerificationRequest(String email, Long orgID) {}

    record LoginRequest(String email, String phone, String password, Long orgID) {}
}
