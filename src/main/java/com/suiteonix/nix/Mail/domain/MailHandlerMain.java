package com.suiteonix.nix.Mail.domain;

import com.suiteonix.nix.Mail.NixMailSender;
import com.suiteonix.nix.shared.exceptions.EX;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
class MailHandlerMain {
    private final JavaMailSender mailSender;
    private final TemplateEngine thymeleafTemplateEngine;
    private final TemplateProcessor mustacheTemplateProcessor;
    @Value("${nix.mail.from}")
    String defaultFrom;


    public MimeMessage buildMessage(NixMailSender nixMailSender) {
        // Create a copy of attachments to avoid potential issues with the original array
        final File[] attachmentsCopy = ContentProcessor.processAttachments(nixMailSender.getAttachments());
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(nixMailSender.getFrom() != null ? nixMailSender.getFrom() : defaultFrom);
            if (nixMailSender.getTo().size() < 2)
                helper.setTo(nixMailSender.getTo().toArray(new String[0]));
            helper.setSubject(nixMailSender.getSubject());
            ContentProcessor.processCCAndBCCs(helper, nixMailSender);
            helper.setText(ContentProcessor.processContent(nixMailSender, thymeleafTemplateEngine, mustacheTemplateProcessor), nixMailSender.isHtml());

            if (attachmentsCopy != null) {
                for (File file : attachmentsCopy) {
                    helper.addAttachment(file.getName(), file);
                }
            }
            return message;
        } catch (MessagingException e) {
            // Log the error since we're in an async context
            System.err.println("Failed to send email after transaction commit: " + e.getMessage());
            throw EX.badRequest("EMAIL_ERROR", "Failed to send email: " + e.getMessage());
        }
    }

    public void sendMail(NixMailSender nixMailSender) {
        sendMailInternal(buildMessage(nixMailSender));
    }

    private void sendMailInternal(MimeMessage message) {
        mailSender.send(message);
        log.info("✅✅✅Email sent successfully");
    }

}
