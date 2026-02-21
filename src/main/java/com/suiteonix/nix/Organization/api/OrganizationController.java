package com.suiteonix.nix.Organization.api;

import com.suiteonix.nix.Organization.domain.OrganizationAggregate;
import com.suiteonix.nix.Organization.services.Organization;
import com.suiteonix.nix.Organization.services.OrganizationCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("organizations")
@RequiredArgsConstructor
@Tag(name = "Organization API", description = "All operations related to the Organization API")
class OrganizationController {

    private final OrganizationAggregate organizationAggregate;

    @PreAuthorize("authentication.isAuthenticated()")
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Register business", description = "Register a new organization")
    OrgRegistrationResponse registerBusiness(@ModelAttribute @Valid OrganizationCreateDto.WithLogos create) {
        return OrgRegistrationResponse.of(organizationAggregate.registerOrganization(create));
    }
}

record OrgRegistrationResponse(
        Organization org
) {
    static OrgRegistrationResponse of(Organization org) {
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
