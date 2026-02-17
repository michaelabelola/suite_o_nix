package com.suiteonix.db.nix.Mail.domain;

import com.suiteonix.db.nix.Mail.MailService;
import com.suiteonix.db.nix.Mail.NixMailSender;
import com.suiteonix.db.nix.shared.CustomContextHolder;
import com.suiteonix.db.nix.shared.exceptions.EX;
import com.suiteonix.db.nix.shared.exceptions.NixException;
import com.suiteonix.db.nix.shared.utils.TransactionUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@Service
class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine thymeleafTemplateEngine;
    private final TemplateProcessor mustacheTemplateProcessor;
    private final String mailDomain;
    private final MailHandlerMain mailHandlerMain;

    public MailServiceImpl(
            @Value("${nix.mail.domain}")
            String mailDomain,
            JavaMailSender mailSender,
            TemplateEngine thymeleafTemplateEngine,
            TemplateProcessor mustacheTemplateProcessor,
            MailHandlerMain mailHandlerMain) {
        this.mailDomain = mailDomain;
        this.mailSender = mailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
        this.mustacheTemplateProcessor = mustacheTemplateProcessor;
        this.mailHandlerMain = mailHandlerMain;
    }

    /**
     * Send an email using a Thymeleaf template file
     *
     * @param to           recipient email location
     * @param subject      email subject
     * @param templateName name of the Thymeleaf template file (without extension)
     * @param variables    variables to be used in the template
     * @param attachments  optional file attachments
     */
    @Override
    public void sendThymeleafTemplateMail(
            String to,
            String subject,
            String templateName,
            Map<String, Object> variables,
            File... attachments) {

        Context context = new Context();
        if (variables != null) variables.forEach(context::setVariable);

        String content = thymeleafTemplateEngine.process(templateName, context);
        sendMail(to, subject, content, true, attachments);
    }

    /**
     * Send an email using a Thymeleaf template string
     *
     * @param to             recipient email location
     * @param subject        email subject
     * @param templateString Thymeleaf template as a string
     * @param variables      variables to be used in the template
     * @param attachments    optional file attachments
     */
    @Override
    public void sendThymeleafTemplateStringMail(
            String to,
            String subject,
            String templateString,
            Map<String, Object> variables,
            File... attachments) {
        // Simple string replacement for template variables (for testing purposes)
        String content = ContentProcessor.processTemplateString(templateString, variables);
        sendMail(to, subject, content, true, attachments);
    }

    /**
     * Send an email using a Mustache template file
     *
     * @param to           recipient email location
     * @param subject      email subject
     * @param templateName name of the Mustache template file (without extension)
     * @param variables    variables to be used in the template
     * @param attachments  optional file attachments
     */
    @Override
    public void sendMustacheTemplateMail(
            String to,
            String subject,
            String templateName,
            Map<String, Object> variables,
            File... attachments) {

        String content = mustacheTemplateProcessor.processTemplateFile(templateName, variables);
        sendMail(to, subject, content, true, attachments);
    }

    /**
     * Send an email using a Mustache template string
     *
     * @param to             recipient email location
     * @param subject        email subject
     * @param templateString Mustache template as a string
     * @param variables      variables to be used in the template
     * @param attachments    optional file attachments
     */
    @Override
    public void sendMustacheTemplateStringMail(
            String to,
            String subject,
            String templateString,
            Map<String, Object> variables,
            File... attachments) {

        String content = mustacheTemplateProcessor.processTemplateString(templateString, variables);
        sendMail(to, subject, content, true, attachments);
    }

    /**
     * Send a simple email
     *
     * @param to          recipient email location
     * @param subject     email subject
     * @param content     email content
     * @param isHtml      whether the content is HTML
     * @param attachments optional file attachments
     * @throws NixException if there's an error sending the email
     */
    private void sendMail(
            String to,
            String subject,
            String content,
            boolean isHtml,
            File... attachments) {

        // Create a copy of attachments to avoid potential issues with the original array
        final File[] attachmentsCopy = attachments != null ? Arrays.copyOf(attachments, attachments.length) : null;

        // Defer the actual mail sending until after transaction commits
        TransactionUtils.executeAfterCommit(() -> {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(content, isHtml);

                if (attachmentsCopy != null) {
                    for (File file : attachmentsCopy) {
                        helper.addAttachment(file.getName(), file);
                    }
                }
                mailSender.send(message);
            } catch (MessagingException e) {
                System.err.println("Failed to send email after transaction commit: " + e.getMessage());
                throw EX.badRequest("EMAIL_ERROR", "Failed to send email: " + e.getMessage());
            }
        });
    }

//    FOR NIX MAIL SENDER

    @Async
    public void sendInstantMailAsync(NixMailSender nixMailSender) {
        CustomContextHolder.bean(JobScheduler.class).enqueue(() -> mailHandlerMain.sendMail(nixMailSender));
    }

    @Override
    public void sendInstantMail(NixMailSender nixMailSender) {
        mailHandlerMain.sendMail(nixMailSender);
    }

    @Override
    public String getDefaultDomain() {
        return mailDomain;
    }

    private void queueMailInternal(NixMailSender nixMailSender) {
        CustomContextHolder.bean(JobScheduler.class).enqueue(() -> mailHandlerMain.sendMail(nixMailSender));
        log.info("🟡🟡🟡Mail has been queued to be sent");
    }

    @Async
    @Override
    public void queueMail(NixMailSender nixMailSender) {
        TransactionUtils.executeAfterCommit(() -> queueMailInternal(nixMailSender));
    }

    @Async
    @Override
    public void queueImmediately(NixMailSender nixMailSender) {
        TransactionUtils.executeAfterCommit(() -> queueMailInternal(nixMailSender));
    }

    private void scheduleMailInternal(NixMailSender nixMailSender, Temporal sendAt) {
        CustomContextHolder.bean(JobScheduler.class).schedule(sendAt, () -> mailHandlerMain.sendMail(nixMailSender));
        log.info("🟡🕐🟡Mail has been scheduled to be sent at {}", sendAt);
    }

    @Async
    @Override
    public void scheduleMail(NixMailSender nixMailSender, Temporal sendAt) {
        TransactionUtils.executeAfterCommit(() -> scheduleMailInternal(nixMailSender, sendAt));
    }

    @Async
    @Override
    public void scheduleImmediately(NixMailSender nixMailSender, Temporal sendAt) {
        TransactionUtils.executeAfterCommit(() -> scheduleMailInternal(nixMailSender, sendAt));
    }

}

