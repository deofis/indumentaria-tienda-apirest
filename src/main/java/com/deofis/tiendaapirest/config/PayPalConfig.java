package com.deofis.tiendaapirest.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "paypal")
@Configuration
@Data
public class PayPalConfig {

    private String clientId;
    private String clientSecret;

    @Bean
    public PayPalEnvironment payPalEnvironment() {
        return new PayPalEnvironment.Sandbox(clientId, clientSecret);
    }

    @Bean
    public PayPalHttpClient payPalHttpClient() {
        return new PayPalHttpClient(payPalEnvironment());
    }
}
