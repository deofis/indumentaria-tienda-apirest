package com.deofis.tiendaapirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TiendaApirestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiendaApirestApplication.class, args);
    }

}
