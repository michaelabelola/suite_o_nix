package com.suiteonix.nix.Mail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.time.temporal.Temporal;
import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NixMailSender {
    boolean isHtml = false;
    String from;
    HashSet<String> to = new HashSet<>();
    String subject = "";
    String templateName;
    String content;
    HashSet<String> cc;
    HashSet<String> bcc;
    HashSet<File> attachments;
    HashSet<String> attachmentLinks;
    TemplateType templateType;
    final HashMap<String, Object> variables = new LinkedHashMap<>();

    public static NixMailSender newInstance() {
        return new NixMailSender();
    }

    public String getFrom() {
        if (from == null) return null;
        if (from.contains("@")) return from;
        from = from + "@" + MailService.GET().getDefaultDomain();
        return from;
    }

    public NixMailSender variables(Map<String, Object> variables) {
        if (variables == null) return this;
        this.variables.putAll(variables);
        return this;
    }

    public NixMailSender html() {
        this.isHtml = true;
        return this;
    }

    public NixMailSender variable(String key, Object value) {
        if (key == null || key.isBlank()) return this;
        if (value == null) return this;
        this.variables.put(key, value);
        return this;
    }

    public NixMailSender from(String from) {
        this.from = from;
        return this;
    }

    public NixMailSender content(String content) {
        this.content = content;
        this.templateName = null;
        return this;
    }

    /**
     * Pre-renders template content so no complex domain objects need to be serialized by JobRunr.
     * Sets {@code content} to the rendered HTML and clears {@code templateName}, {@code templateType},
     * and {@code variables}.
     */
    public NixMailSender preRender(String html) {
        this.content = html;
        this.templateName = null;
        this.templateType = null;
        this.variables.clear();
        return this;
    }

    public NixMailSender to(String to) {
        if (to == null || to.isBlank()) return this;
        if (this.to == null) this.to = new HashSet<>();
        this.to.add(to);
        return this;
    }

    // add the user email to the TO mails and add the account to the variables
//    public NixMailSender to(NixID to) {
//        return to(NixAccountService.INSTANCE().getAccountById(to).orElse(null));
//    }
//
//    public NixMailSender to(NixAccount account) {
//        if (account == null) return this;
//        variables.put("nixAccount", account);
//        return to(account.email());
//    }


    public NixMailSender cc(String cc) {
        if (cc == null || cc.isBlank()) return this;
        if (this.cc == null) this.cc = new HashSet<>();
        this.cc.add(cc);
        return this;
    }

    public NixMailSender cc(Collection<String> cc) {
        if (cc == null) return this;
        if (this.cc == null) this.cc = new HashSet<>();
        this.cc.addAll(cc);
        return this;
    }

    public NixMailSender templateType(TemplateType templateType) {
        this.templateType = templateType;
        return this;
    }

    public NixMailSender templateName(String templateName) {
        this.templateName = templateName;
        this.content = null;
        return this;
    }

    public NixMailSender subject(String subject) {
        this.subject = subject;
        return this;
    }

    public NixMailSender bcc(String bcc) {
        if (bcc == null || bcc.isBlank()) return this;
        if (this.bcc == null) this.bcc = new HashSet<>();
        this.bcc.add(bcc);
        return this;
    }

    public NixMailSender attachments(File... attachments) {
        if (attachments == null) return this;
        return attachments(Arrays.asList(attachments));
    }

    public NixMailSender attachments(Collection<File> attachments) {
        if (attachments == null) return this;
        if (this.attachments == null) this.attachments = new HashSet<>();
        this.attachments.addAll(attachments);
        return this;
    }

    public NixMailSender attachmentLinks(String... attachmentLinks) {
        if (attachmentLinks == null) return this;
        return attachmentLinks(Arrays.asList(attachmentLinks));
    }

    public NixMailSender attachmentLinks(Collection<String> attachmentLinks) {
        if (attachmentLinks == null) return this;
        if (this.attachmentLinks == null) this.attachmentLinks = new HashSet<>();
        this.attachmentLinks.addAll(attachmentLinks);
        return this;
    }

    @Async
    public void queueMail() {
        MailService.GET().queueMail(this);
    }

    @Async
    public void scheduleMail(Temporal sendAt) {
        MailService.GET().scheduleMail(this, sendAt);
    }

    @Async
    public void sendAsyncMail() {
        MailService.GET().sendInstantMailAsync(this);
    }

    public void sendInstantMail() {
        MailService.GET().sendInstantMail(this);
    }

}
