package com.suiteonix.nix.Auth.api;

import com.suiteonix.nix.Auth.internal.AuthUserMapper;
import com.suiteonix.nix.Auth.internal.AuthUserService;
import com.suiteonix.nix.spi.Auth.AuthUser;
import com.suiteonix.nix.spi.Auth.AuthUserRegisterDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication API")
class AuthController {
    private final AuthUserService authUserService;

    @GetMapping("{id}")
    AuthUser getAuthUser(@PathVariable String id) {
        return AuthUserMapper.INSTANCE.toDto(authUserService.getAuthUserById(id));
    }

    @PostMapping(name = "/login")
    void login() {
        // TODO: Implement login logic
    }

    @PostMapping("/logout")
    void logout() {
        // TODO: Implement logout logic
    }

    @PostMapping("/register")
    AuthUser register(AuthUserRegisterDto registerDto) {
        var user = authUserService.register(registerDto);
        return AuthUserMapper.INSTANCE.toDto(user);
    }

}
