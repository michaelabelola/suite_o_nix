package com.suiteonix.nix.Auth.internal.api;

import com.suiteonix.nix.Auth.internal.domain.AuthModule;
import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserMapper;
import com.suiteonix.nix.Auth.service.AuthProfile;
import com.suiteonix.nix.Permission.systemPermissions.annotations.Allow;
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
    AuthProfile getAuthProfile(@PathVariable String id) {
        return AuthUserMapper.INSTANCE.toDto(authModule.getAuthUserById(id));
    }

    @PostMapping("/verify-email")
    void verifyEmail() {
        // TODO: Implement login logic
    }

    //    @PermissionDefinition()
    @Allow("/login")
    @PostMapping("/login")
    void login() {
        // TODO: Implement login logic
    }

    @PostMapping("/logout")
    void logout() {
        // TODO: Implement logout logic
    }

}
