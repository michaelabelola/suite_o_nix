package com.suiteonix.nix.Onboarding;

import com.suiteonix.nix.Auth.service.AuthProfile;
import com.suiteonix.nix.Auth.service.AuthenticationService;
import com.suiteonix.nix.Mail.NixMailSender;
import com.suiteonix.nix.Organization.services.Organization;
import com.suiteonix.nix.Organization.services.OrganizationCreateDto;
import com.suiteonix.nix.Organization.services.OrganizationService;
import com.suiteonix.nix.User.service.User;
import com.suiteonix.nix.User.service.UserService;
import com.suiteonix.nix.shared.principal.Principal;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Service
@RequiredArgsConstructor
class OrgRegistrationOrchestration {

    private final OrganizationService organizationService;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Transactional
    public OrgRegistrationResponse execute(OrganizationCreateDto.WithLogos create) {
        var org = organizationService.registerOrganization(create);
        var user = userService.registerDefaultOrgUser(org.id(), Principal.ID());
        var auth = authenticationService.registerOrgUserProfile(Principal.ID(), org.id());
        sendOrgCreatedMail(org);

        return OrgRegistrationResponse.of(org, user, auth);
    }

    private void sendOrgCreatedMail(Organization org) {
        NixMailSender.newInstance()
                .variables(Map.of(
                        "org", org
                ))
                .queueMail();
    }
}

@RestController
@RequestMapping("organizations")
@RequiredArgsConstructor
class OrganizationController {

    private final OrgRegistrationOrchestration registrationOrchestration;

    @PreAuthorize("authentication.isAuthenticated()")
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Register business", description = "Register a new organization")
    OrgRegistrationResponse registerBusiness(@ModelAttribute @Valid OrganizationCreateDto.WithLogos create) {
        return registrationOrchestration.execute(create);
    }
}

record OrgRegistrationResponse(
        Organization org
) {
    static OrgRegistrationResponse of(Organization org, User user, AuthProfile auth) {
        return new OrgRegistrationResponse(org);
    }
}

@Slf4j
@Component
@RequiredArgsConstructor
class StringToBusinessCreateConverter implements Converter<String, OrganizationCreateDto> {

    private final ObjectMapper objectMapper;

    @Override
    public OrganizationCreateDto convert(@NotNull String source) {
        try {
            return objectMapper.readValue(source, OrganizationCreateDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON to Create", e);
        }
    }
}


