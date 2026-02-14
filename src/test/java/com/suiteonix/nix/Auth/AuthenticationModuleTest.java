package com.suiteonix.nix.Auth;

import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import com.suiteonix.nix.spi.Auth.AuthUserRegisterDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@DisplayName("AuthenticationModule Tests")
class AuthenticationModuleTest {

    private AuthUserRegisterDto validRegisterDto;
    private NixID userId;

    @BeforeEach
    void setUp() {
        userId = NixID.of(UUID.randomUUID().toString());
        
        validRegisterDto = new AuthUserRegisterDto(
                userId,
                NixRole.CUSTOMER,
                "test@example.com",
                "+1234567890",
                "SecurePass123!"
        );
    }

}