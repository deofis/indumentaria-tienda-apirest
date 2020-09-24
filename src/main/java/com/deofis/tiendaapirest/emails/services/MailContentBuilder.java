package com.deofis.tiendaapirest.emails.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;

    String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);

        return templateEngine.process("mailTemplate", context);
    }

    String build(String message, String url) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("url", url);

        return templateEngine.process("mailTemplate", context);
    }

}
