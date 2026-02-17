package com.suiteonix.nix.Mail.domain;

import java.util.Map;

/**
 * Interface for template processing
 */
interface TemplateProcessor {

    /**
     * Process a template file with the given variables
     *
     * @param templateName name of the template file (without extension)
     * @param variables    variables to be used in the template
     * @return processed template as a string
     */
    String processTemplateFile(String templateName, Map<String, Object> variables);

    /**
     * Process a template string with the given variables
     *
     * @param templateString template content as a string
     * @param variables      variables to be used in the template
     * @return processed template as a string
     */
    String processTemplateString(String templateString, Map<String, Object> variables);
}