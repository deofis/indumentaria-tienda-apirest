package com.deofis.tiendaapirest.emails.services;

import com.deofis.tiendaapirest.emails.dto.NotificationEmail;

public interface MailService {

    /**
     * Sends an email without an url.
     * @param notificationEmail Data for mail sending.
     */
    void sendEmail(NotificationEmail notificationEmail);

    /**
     * Sends an email with url.
     * @param notificationEmail data for mail sending.
     * @param url link to verify token.
     */
    void sendEmail(NotificationEmail notificationEmail, String url);
}
