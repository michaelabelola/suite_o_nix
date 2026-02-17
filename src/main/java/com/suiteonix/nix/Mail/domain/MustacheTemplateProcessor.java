package com.suiteonix.db.nix.Mail.domain;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
class MustacheTemplateProcessor implements TemplateProcessor {

    private final Mustache.Compiler compiler;
    private final String templatePrefix;
    private final String templateSuffix;

    public MustacheTemplateProcessor(
            Mustache.Compiler compiler,
            @Value("${spring.mustache.prefix:classpath:/templates/}") String templatePrefix,
            @Value("${spring.mustache.suffix:.mustache}") String templateSuffix) {
        this.compiler = compiler;
        this.templatePrefix = templatePrefix.replace("classpath:", "").replaceFirst("^/", "");
        this.templateSuffix = templateSuffix;
    }

    @Override
    public String processTemplateFile(String templateName, Map<String, Object> variables) {
        try {
            String templatePath = templatePrefix + templateName + templateSuffix;
            var inputStream = getClass().getClassLoader().getResourceAsStream(templatePath);
            if (inputStream == null) {
                throw new RuntimeException("Template not found: " + templatePath);
            }

            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            Template template = compiler.compile(reader);

            // Ensure variables is not null and provide default values for expected template variables
            Map<String, Object> safeVariables = variables != null ? new java.util.HashMap<>(variables) : new java.util.HashMap<>();

            // Provide default values for common template variables if they're missing
            safeVariables.putIfAbsent("name", "");
            safeVariables.putIfAbsent("message", "");
            safeVariables.putIfAbsent("timestamp", "");

            return template.execute(safeVariables);
        } catch (Exception e) {
            throw new RuntimeException("Error processing Mustache template file: " + templateName, e);
        }
    }

    @Override
    public String processTemplateString(String templateString, Map<String, Object> variables) {
        try {
            Template template = compiler.compile(new StringReader(templateString));

            // Ensure variables is not null and provide default values for expected template variables
            Map<String, Object> safeVariables = variables != null ? new java.util.HashMap<>(variables) : new java.util.HashMap<>();

            // Provide default values for common template variables if they're missing
            safeVariables.putIfAbsent("name", "");
            safeVariables.putIfAbsent("message", "");
            safeVariables.putIfAbsent("timestamp", "");

            return template.execute(safeVariables);
        } catch (Exception e) {
            throw new RuntimeException("Error processing Mustache template string", e);
        }
    }
}
