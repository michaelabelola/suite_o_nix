package com.suiteonix.db.nix.Onboarding.User;

import com.suiteonix.db.nix.Auth.service.AuthenticationService;
import com.suiteonix.db.nix.Mail.MailService;
import com.suiteonix.db.nix.User.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        mailService.scheduleMail();

        return UserOnboarding.Response.OF(user, authProfile);
    }
}

@RequestMapping
@RequiredArgsConstructor
@RestController("userRegistrationController")
//@Tag(name = "User Registration", description = "User Registration API")
class Controller {
    private final UserRegistrationService registrationService;

    @PostMapping("/onboard/user")
    @Operation(description = "Onboard New User", summary = "Register new User")
    public UserOnboarding.Response register(UserOnboarding.Request onboarding) {
        return registrationService.execute(onboarding);
    }
}

