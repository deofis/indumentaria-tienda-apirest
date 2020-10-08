package com.deofis.tiendaapirest.autenticacion.security.oauth2.user;

import com.deofis.tiendaapirest.autenticacion.domain.AuthProvider;
import com.deofis.tiendaapirest.autenticacion.exceptions.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Lo siento! El inicio de sesión con " +
                    registrationId + " no está soportado aún");
        }
    }

}
