package com.suiteonix.db.nix.Onboarding.User;

import com.suiteonix.db.nix.shared.ids.NixID;
import com.suiteonix.db.nix.shared.ids.NixRole;
import com.suiteonix.db.nix.Auth.service.AuthProfile;
import com.suiteonix.db.nix.User.service.User;
import com.suiteonix.db.nix.spi.location.HomeAddress;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

class UserOnboarding {

    @Schema(name = "UserOnboarding.Request", description = "UserOnboarding.Request")
    record Request(
            @Schema(example = "michaelabelola@gmail.com")
            String email,
            @Schema(example = "+1234567890")
            String phone,
            @Schema(example = "SecurePass123!")
            String password,
            @Schema(example = "Michael")
            String firstname,
            @Schema(example = "Abel")
            String lastname,
            LocalDate dateOfBirth,
            @Schema(example = "This is a short bio about me")
            String bio,
            HomeAddress.Create address
    ) {
        public Request {
            if (email != null)
                email = email.toLowerCase();
        }

        public User.Create toUserCreate() {
            return User.Create.builder()
                    .firstname(firstname())
                    .lastname(lastname()).email(email()).phone(phone())
                    .dateOfBirth(dateOfBirth()).bio(bio()).address(address()).build();
        }

        public AuthProfile.Register toAuthUserCreate(NixID id) {
            return AuthProfile.Register.builder()
                    .id(id).role(NixRole.CUSTOMER).email(email()).phone(phone()).password(password()).build();
        }
    }

    @Schema(name = "UserRegistration.Response", description = "UserRegistration.Response")
    record Response(
            AuthProfile auth,
            User user
//            String token,
//            String refreshToken
    ) {

        public static Response OF(User user, AuthProfile authProfile) {
            return new Response(authProfile, user);
        }
    }

}
