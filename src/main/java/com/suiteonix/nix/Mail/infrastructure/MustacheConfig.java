package com.suiteonix.nix.Mail.infrastructure;

import com.samskivert.mustache.Mustache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MustacheConfig {

    @Bean
    public Mustache.Compiler mustacheCompiler() {
        return Mustache.compiler()
                .defaultValue("")
                .nullValue("")
                .escapeHTML(true);
    }
}