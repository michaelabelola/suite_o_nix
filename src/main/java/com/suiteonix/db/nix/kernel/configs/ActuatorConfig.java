package com.suiteonix.db.nix.kernel.configs;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("actuatorCustomConfig")
@RequiredArgsConstructor
class ActuatorConfig {

    @Bean("ActuatorApiGroup")
    GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("Actuator")
                .pathsToMatch("/actuator/**")
                .build();
    }
}