package com.suiteonix.nix.Onboarding;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("OnboardingConfig")
@RequiredArgsConstructor
class Config {
    @Bean("OnboardingApiGroup")
    GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("Onboarding")
                .packagesToScan("com.suiteonix.nix.Onboarding")
                .build();
    }
}
