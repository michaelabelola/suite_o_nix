package com.suiteonix.nix.Onboarding;

import com.suiteonix.nix.Auth.service.AuthProfile;
import com.suiteonix.nix.Auth.service.AuthenticationService;
import com.suiteonix.nix.Mail.MailService;
import com.suiteonix.nix.Mail.NixMailSender;
import com.suiteonix.nix.Mail.TemplateType;
import com.suiteonix.nix.User.service.User;
import com.suiteonix.nix.User.service.UserCreateDto;
import com.suiteonix.nix.User.service.UserService;
import com.suiteonix.nix.shared.ConfigFlag;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import com.suiteonix.nix.spi.location.HomeAddress;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
class UserOnboardingService {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final MailService mailService;

    @Transactional
    public UserOnboardingDtos.Response execute(UserOnboardingDtos.Request onboarding) {
        var user = userService.registerUser(onboarding.toUserCreate());
        var authProfile = authenticationService.register(onboarding.toAuthUserCreate(user.id()));

        mailService.queueMail(
                NixMailSender.newInstance()
                        .to(user.email())
                        .html()
                        .templateName("onboarding/user/user-onboarding-email")
                        .variables(Map.of(
                                "user", user,
                                "authProfile", authProfile
                        ))
                        .templateType(TemplateType.THYMELEAF)
        );

        return UserOnboardingDtos.Response.OF(user, authProfile);
    }
}

@RequestMapping
@RequiredArgsConstructor
@RestController("userOnboardingController")
@Tag(name = "Onboarding")
class UserOnboardingController {
    private final UserOnboardingService registrationService;

    @PostMapping("/onboard/user")
    @Operation(description = "Onboard New User", summary = "Register new User")
    public UserOnboardingDtos.Response register(@RequestBody UserOnboardingDtos.Request onboarding) {
        return registrationService.execute(onboarding);
    }
}

class UserOnboardingDtos {

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
            if (email != null) email = email.toLowerCase();
        }

        public UserCreateDto toUserCreate() {
            return UserCreateDto.builder()
                    .firstname(firstname())
                    .lastname(lastname()).email(email()).phone(phone())
                    .dateOfBirth(dateOfBirth()).bio(bio()).address(address()).build();
        }

        public AuthProfile.Register toAuthUserCreate(NixID id) {
            return AuthProfile.Register.builder()
                    .id(id)
                    .role(NixRole.USER)
                    .email(email())
                    .phone(phone())
                    .password(password())
                    .signInOptions(AuthProfile.SignInOptions.builder()
                            .emailAndPassword(ConfigFlag.ACTIVE)
                            .emailAndEmailToken(ConfigFlag.INACTIVE)
                            .phoneAndPassword(ConfigFlag.INACTIVE)
                            .phoneAndPhoneToken(ConfigFlag.INACTIVE)
                            .build())
                    .configFlags(AuthProfile.ConfigFlags.builder()
                            .jwtAuthEnabled(ConfigFlag.ACTIVE)
                            .linkedAccountLogin(ConfigFlag.ACTIVE)
                            .sendMailVerification(ConfigFlag.ACTIVE)
                            .build())
                    .build();
        }
    }

    @Schema(name = "UserRegistration.Response", description = "UserRegistration.Response")
    record Response(
            AuthProfile auth,
            User.Detailed user
//            String token,
//            String refreshToken
    ) {

        public static Response OF(User.Detailed user, AuthProfile authProfile) {
            return new Response(authProfile, user);
        }
    }

}