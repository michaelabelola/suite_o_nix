package com.suiteonix.db.nix.kernel.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:${server.port}", description = "Dev"),
                @Server(url = "http://nix.localhost:${server.port}", description = "Dev"),
                @Server(url = "https://${app.url}", description = "Staging"),
                @Server(url = "https://nix.${app.url}", description = "Staging"),
        },
        info = @Info(
                title = "${spring.application.name}",
                description = "All endpoints for ${spring.application.name} Backend",
                contact = @Contact(url = "https://***.com", name = "Suiteonix Dev Team", email = "dev@suiteonix.com"),
                version = "0.0.1"
        ),
        security = {
                @SecurityRequirement(name = "jwt"),
                @SecurityRequirement(name = "api-key"),
        }
//        , tags = {
//        @Tag(name = "Real Estate", description = "All Real Estate APIs"),
//        @Tag(name = "Real Estate - item", description = "Real Estate Item API"),
//        @Tag(name = "Real Estate - item properties", description = "Real Estate Item Properties API"),
//        @Tag(name = "Real Estate - rent", description = "Real Estate Rent And Lease APIs")
//}
)
@SecurityScheme(name = "jwt", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER, scheme = "bearer", bearerFormat = "JWT", description = "Auth header")
//@SecurityScheme(name = "cookie", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.COOKIE, scheme = "bearer", paramName = "auth", description = "Auth cookie")
//@SecurityScheme(name = "api-key", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, description = "API Key")
class SpringDocConfig {
    @Bean
    public GroupedOpenApi baseApiGroup() {
        return GroupedOpenApi.builder()
                .group("All Apis")
                .pathsToMatch("/**","/actuator/**")
                .build();
    }


}