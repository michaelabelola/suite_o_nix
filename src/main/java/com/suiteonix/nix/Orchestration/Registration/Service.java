package com.suiteonix.nix.Orchestration.Registration;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Service
@RequiredArgsConstructor
class UserRegistrationService {

    public String execute() {
//        TODO: Implement user registration logic
//        <li>auth register</li>
//        <li>user register</li>
//        <li>send mail</li>

        return "";
    }
}

@RequestMapping
@RequiredArgsConstructor
@RestController("userRegistrationController")
@Tag(name = "User Registration", description = "User Registration API")
class Controller {
    private final UserRegistrationService registrationService;

    @PostMapping("/onboard/user")
    public String register() {
        return registrationService.execute();
    }
}

