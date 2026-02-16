package com.suiteonix.db.nix.Mail.domain;

import com.suiteonix.db.nix.Mail.NixMailSender;
import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class ContentProcessor {
    private ContentProcessor() {
    }

    static void processCCAndBCCs(MimeMessageHelper helper, NixMailSender nixMailSender) {
        Optional.ofNullable(nixMailSender.getCc()).ifPresent(ccs -> {
            try {
                helper.setCc(ccs.toArray(new String[0]));
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
        Optional.ofNullable(nixMailSender.getBcc()).ifPresent(bccs -> {
            try {
                helper.setBcc(bccs.toArray(new String[0]));
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    static File[] processAttachments(Set<File> attachments) {
        return attachments != null ?
                Set.copyOf(attachments).toArray(new File[0]) :
                null;
    }

    static String processContent(NixMailSender nixMailSender, TemplateEngine thymeleafTemplateEngine, TemplateProcessor mustacheTemplateProcessor) {

        return (nixMailSender.getTemplateName() != null) ?
                switch (nixMailSender.getTemplateType()) {
                    case MUSTACHE ->
                            mustacheTemplateProcessor.processTemplateFile(nixMailSender.getTemplateName(), nixMailSender.getVariables());
                    case THYMELEAF ->
                            thymeleafTemplateEngine.process(nixMailSender.getTemplateName(), loadContext(nixMailSender.getVariables()));
                } :
                switch (nixMailSender.getTemplateType()) {
                    case MUSTACHE ->
                            mustacheTemplateProcessor.processTemplateString(nixMailSender.getContent(), nixMailSender.getVariables());
                    case THYMELEAF -> processTemplateString(nixMailSender.getContent(), nixMailSender.getVariables());
                };
    }

    /**
     * ? This is for test.
     * Use thymeleafTemplateEngine.process* to process the template string
     */
    static String processTemplateString(String templateString, Map<String, Object> variables) {
        if (variables != null) {
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                String placeholder = "${" + entry.getKey() + "}";
                String value = entry.getValue() != null ? entry.getValue().toString() : "";
                templateString = templateString.replace(placeholder, value);
            }
        }
        return templateString;
    }

    private static Context loadContext(Map<String, Object> variables) {
        Context context = new Context();
        if (variables != null) variables.forEach(context::setVariable);
        return context;
    }

}
