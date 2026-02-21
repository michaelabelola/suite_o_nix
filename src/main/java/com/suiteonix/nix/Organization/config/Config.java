package com.suiteonix.nix.Organization.config;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("organizationConfig")
@RequiredArgsConstructor
class Config {
    @Bean("organizationApiGroup")
    GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("Organization")
                .pathsToMatch("/organizations/**", "/organizations", "/organization/**", "/organization")
                .build();
    }
}
