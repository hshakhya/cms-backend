package com.anyessglobal.cms_backend.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    // Inject values from application.properties
    @Value("${app.mail.attachment-url:}")
    private String attachmentUrl;

    @Value("${app.mail.attachment-display-name:Attachment.pdf}")
    private String attachmentName;

    @Value("${app.mail.sender-email}")
    private String senderEmail;

    @Value("${app.mail.sender-name}")
    private String senderName;

    public void sendReplyWithAttachment(String to, String subject, String message, String refId) {
        try {
            // 1. Load HTML Template
            // Ensure this file exists at src/main/resources/templates/email-reply.html
            ClassPathResource resource = new ClassPathResource("templates/email-reply.html");
            String template = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            // 2. Replace Placeholders
            // Note: message.replace("\n", "<br>") handles line breaks from the React text area
            String finalHtml = template
                    .replace("{{message}}", message.replace("\n", "<br>"))
                    .replace("{{refId}}", refId != null ? refId : "N/A");

            // 3. Prepare Email
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Re: [Anyess Global] " + (subject != null ? subject : "Inquiry"));

            // FIX: Valid SMTP format "Name <email@domain.com>"
            helper.setFrom(String.format("%s <%s>", senderName, senderEmail));

            helper.setText(finalHtml, true);

            // 4. Attach PDF (Fail-Safe Logic)
            if (attachmentUrl != null && !attachmentUrl.isEmpty()) {
                try {
                    URL url = new URL(attachmentUrl);
                    // Reads the PDF bytes from the web URL
                    byte[] pdfBytes = url.openStream().readAllBytes();
                    helper.addAttachment(attachmentName, new ByteArrayResource(pdfBytes));
                } catch (Exception e) {
                    System.err.println("WARNING: Failed to attach PDF. Sending email without it. Cause: " + e.getMessage());
                }
            }

            // 5. Send
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            // Log full error for debugging
            e.printStackTrace();
            throw new RuntimeException("Critical Email Error: " + e.getMessage());
        }
    }
}