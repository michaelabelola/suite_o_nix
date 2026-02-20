package com.suiteonix.nix.Onboarding.User;

import com.suiteonix.nix.Auth.service.AuthenticationService;
import com.suiteonix.nix.Mail.MailService;
import com.suiteonix.nix.Mail.NixMailSender;
import com.suiteonix.nix.Mail.TemplateType;
import com.suiteonix.nix.User.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Service
@RequiredArgsConstructor
class UserRegistrationService {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final MailService mailService;

    @Transactional
    public UserOnboarding.Response execute(UserOnboarding.Request onboarding) {
        var userCreate = onboarding.toUserCreate();
        var user = userService.registerUser(userCreate);
        var authProfileCreate = onboarding.toAuthUserCreate(user.id());
        var authProfile = authenticationService.register(authProfileCreate);



        mailService.queueMail(
                NixMailSender.newInstance()
                        .to(user.email())
                        .templateName("onboarding/user/user-onboarding-email")
                        .variables(Map.of(
                                "user", user,
                                "authProfile", authProfile
                        ))
                        .templateType(TemplateType.THYMELEAF)
        );

        return UserOnboarding.Response.OF(user, authProfile);
    }
}

@RequestMapping
@RequiredArgsConstructor
@RestController("userRegistrationController")
@Tag(name = "UserController", description = "User Registration API")
class Controller {
    private final UserRegistrationService registrationService;

    @PostMapping("/onboard/user")
    @Operation(description = "Onboard New User", summary = "Register new User")
    public UserOnboarding.Response register(@RequestBody UserOnboarding.Request onboarding) {
        return registrationService.execute(onboarding);
    }
}

