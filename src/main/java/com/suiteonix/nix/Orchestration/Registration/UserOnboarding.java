package com.suiteonix.nix.Orchestration.Registration;

import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import io.swagger.v3.oas.annotations.media.Schema;

public class UserOnboarding {
    @Schema(name = "UserOnboarding.Request", description = "UserOnboarding.Request")
    record Request(
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



    @Schema(name = "UserRegistration.Response", description = "UserRegistration.Response")
    record Response() {

    }

}
