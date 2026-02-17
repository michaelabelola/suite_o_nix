package com.suiteonix.nix.Mail;

import com.suiteonix.nix.shared.CustomContextHolder;
import jakarta.mail.MessagingException;

import java.io.File;
import java.time.temporal.Temporal;
import java.util.Map;

/**
 * Service interface for sending and scheduling emails using various template engines.
 */
public interface MailService {
    /**
     * Retrieves the MailService instance from the application context.
     *
     * @return the MailService bean
     */
    static MailService GET() {
        return CustomContextHolder.context().getBean(MailService.class);
    }

    void sendThymeleafTemplateMail(
            String to,
            String subject,
            String templateName,
            Map<String, Object> variables,
            File... attachments) throws MessagingException;

    void sendThymeleafTemplateStringMail(
            String to,
            String subject,
            String templateString,
            Map<String, Object> variables,
            File... attachments) throws MessagingException;

    void sendMustacheTemplateMail(
            String to,
            String subject,
            String templateName,
            Map<String, Object> variables,
            File... attachments) throws MessagingException;

    void sendMustacheTemplateStringMail(
            String to,
            String subject,
            String templateString,
            Map<String, Object> variables,
            File... attachments) throws MessagingException;

    void queueMail(NixMailSender nixMailSender);

    void queueImmediately(NixMailSender nixMailSender);

    void scheduleMail(NixMailSender nixMailSender, Temporal sendAt);

    void scheduleImmediately(NixMailSender nixMailSender, Temporal sendAt);

    void sendInstantMailAsync(NixMailSender nixMailSender);

    void sendInstantMail(NixMailSender nixMailSender);

    String getDefaultDomain();
}
