package com.deofis.tiendaapirest.emails.services;

import com.deofis.tiendaapirest.emails.dto.NotificationEmail;

public interface MailService {

    void sendEmail(NotificationEmail notificationEmail);

    void sendEmail(NotificationEmail notificationEmail, String url);
}
