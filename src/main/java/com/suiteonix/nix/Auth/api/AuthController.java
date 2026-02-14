package com.suiteonix.nix.Auth.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication API")
class AuthController {
    @PostMapping(name = "/login")
    public void login() {
        // TODO: Implement login logic
    }

    @PostMapping("/logout")
    public void logout() {
        // TODO: Implement logout logic
    }


}
