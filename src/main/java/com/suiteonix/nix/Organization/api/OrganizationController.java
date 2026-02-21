package com.suiteonix.nix.Organization.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("organizations")
@RequiredArgsConstructor
@Tag(name = "Organization API", description = "All operations related to the Organization API")
public class OrganizationController {

}
