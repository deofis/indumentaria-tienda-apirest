package com.deofis.tiendaapirest.autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotNull(message = "Username is required.")
    private String username;
    @NotNull(message = "Password is required.")
    private String password;
    @NotNull(message = "Email is required.")
    private String email;
}
