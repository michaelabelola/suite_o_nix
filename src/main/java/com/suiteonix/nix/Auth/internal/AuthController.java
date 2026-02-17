package com.suiteonix.nix.Auth.internal;

import com.suiteonix.nix.Auth.service.AuthProfile;
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

    @PostMapping(name = "/login")
    void login() {
        // TODO: Implement login logic
    }

    @PostMapping("/logout")
    void logout() {
        // TODO: Implement logout logic
    }

}
