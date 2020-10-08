package com.deofis.tiendaapirest.autenticacion.security.oauth2;

import com.deofis.tiendaapirest.autenticacion.exceptions.BadRequestException;
import com.deofis.tiendaapirest.autenticacion.security.JwtProveedor;
import com.deofis.tiendaapirest.autenticacion.util.CookieUtils;
import com.deofis.tiendaapirest.config.AppProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.deofis.tiendaapirest.autenticacion.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@AllArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProveedor jwtProveedor;

    private final AppProperties appProperties;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = this.determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("La response ya ha sido entregada. No se pudo redirigir a " + targetUrl);
            return;
        }

        this.clearAuthenticationAttributes(request, response);
        this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("Lo siento! Se obtuvo una Redirect URI NO AUTORIZADA, por lo que no" +
                    " se puede continuar con la autenticación");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        String token = this.jwtProveedor.generateToken(authentication);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("authToken", token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getoAuth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    URI authorizedUri = URI.create(authorizedRedirectUri);

                    return authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost()) &&
                            authorizedUri.getPort() == clientRedirectUri.getPort();
                });
    }
}
