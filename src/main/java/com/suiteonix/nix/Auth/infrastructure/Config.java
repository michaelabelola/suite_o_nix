package com.suiteonix.nix.Auth.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("authConfig")
@RequiredArgsConstructor
class Config {
    @Bean("authApiGroup")
    GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("Authentication")
                .pathsToMatch("/auth/**", "/auth")
//                .packagesToScan("com.suiteonix.nix")
                .build();
    }
}
