package com.deofis.tiendaapirest.emails.services;

import com.deofis.tiendaapirest.autenticacion.exceptions.MailSenderException;
import com.deofis.tiendaapirest.emails.dto.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service for sending emails.
 */
@Service
@AllArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;

    /**
     * Sends an email without an url.
     * @param notificationEmail Data for mail sending.
     */
    @Override
    @Async
    public void sendEmail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("deofis.mailsender-598aee@inbox.mailtrap.io");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()) , true);
        };

        try {
            javaMailSender.send(messagePreparator);
            log.info("Email sent!");
        } catch (MailException e) {
            throw new MailSenderException("Excepción al enviar email a: " + notificationEmail.getRecipient());
        }
    }

    /**
     * Sends an email with url.
     * @param notificationEmail data for mail sending.
     * @param url link to verify token.
     */
    @Override
    @Async
    public void sendEmail(NotificationEmail notificationEmail, String url) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("deofis.mailsender-598aee@inbox.mailtrap.io");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody(), url) , true);
        };

        try {
            javaMailSender.send(messagePreparator);
            log.info("Email sent!");
        } catch (MailException e) {
            throw new MailSenderException("Excepción al enviar email a: " + notificationEmail.getRecipient());
        }
    }

}
