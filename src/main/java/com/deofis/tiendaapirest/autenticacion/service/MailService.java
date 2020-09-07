package com.deofis.tiendaapirest.autenticacion.service;

import com.deofis.tiendaapirest.autenticacion.dto.NotificationEmail;

public interface MailService {

    void sendEmail(NotificationEmail notificationEmail);

    void sendEmail(NotificationEmail notificationEmail, String url);
}
