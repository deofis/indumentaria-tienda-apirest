package com.deofis.tiendaapirest.autenticacion.services;

import com.deofis.tiendaapirest.autenticacion.dto.NotificationEmail;

public interface MailService {

    void sendEmail(NotificationEmail notificationEmail);

    void sendEmail(NotificationEmail notificationEmail, String url);
}
