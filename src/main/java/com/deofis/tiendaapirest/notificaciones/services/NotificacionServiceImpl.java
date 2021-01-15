package com.deofis.tiendaapirest.notificaciones.services;

import com.deofis.tiendaapirest.notificaciones.dto.Notificacion;
import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class NotificacionServiceImpl implements NotificacionService {

    private final CreadorNotificaciones creadorNotificaciones;
    private final String magicBellApiKey;
    private final String magicBellApiSecret;

    @Async
    @Override
    public void enviarNotificacion(Operacion operacion, String email) {
        String titulo = "¡Nueva compra registrada!";
        String contenido = "Tu compra con N° ".concat(String.valueOf(operacion.getNroOperacion()).concat(" se completó" +
                " con éxito."));
        String actionUrl = "/user-my-purchases";

        // Creamos la notificación con los datos que creamos.
        Notificacion notificacion = this.creadorNotificaciones.build(titulo, contenido, actionUrl, email);

        // Creamos un MAP con los datos de la notificación que se integrarán en la request POST a MagicBell
        Map<String, Object> data = new HashMap<>();
        data.put("title", notificacion.getTitulo());
        data.put("content", notificacion.getContenido());
        data.put("category", notificacion.getCategoria());
        data.put("action_url", notificacion.getUrl());
        data.put("recipient", Map.of("email", notificacion.getUsuario().getEmail()));

        // Hacemos el POST a la API y lo guardamos en request por si lo necesitamos.
        HttpResponse<JsonNode> response = Unirest.post("https://api.magicbell.io/notifications")
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("X-MAGICBELL-API-KEY", this.magicBellApiKey)
                .header("X-MAGICBELL-API-SECRET", this.magicBellApiSecret)
                .body(Map.of("notification", data)).asJson();

        log.info(String.valueOf(response.getBody()));
    }
}
