package com.suiteonix.nix.User.config;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("UserConfig")
@RequiredArgsConstructor
class Config {
    @Bean("userApiGroup")
    GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("User")
                .pathsToMatch("/users/**", "/users", "/user/**", "/user")
                .build();
    }
}
