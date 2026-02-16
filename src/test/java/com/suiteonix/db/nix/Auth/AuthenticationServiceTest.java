package com.suiteonix.db.nix.Auth;

import com.suiteonix.db.nix.shared.ids.NixID;
import com.suiteonix.db.nix.shared.ids.NixRole;
import com.suiteonix.db.nix.Auth.service.AuthProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@DisplayName("AuthenticationModule Tests")
class AuthenticationServiceTest {

    private AuthProfile.Register validRegisterDto;
    private NixID userId;

    @BeforeEach
    void setUp() {
        userId = NixID.of(UUID.randomUUID().toString());
        
        validRegisterDto = new AuthProfile.Register(
                userId,
                NixRole.CUSTOMER,
                "test@example.com",
                "+1234567890",
                "SecurePass123!"
        );
    }

}