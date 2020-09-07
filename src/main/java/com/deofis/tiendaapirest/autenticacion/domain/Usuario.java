package com.deofis.tiendaapirest.autenticacion.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // No necesario.
    @NotNull(message = "El nombre de usuario es obligatorio.")
    @Column(unique = true)
    private String username;

    @NotNull(message = "La contraseña es obligatoria.")
    private String password;

    @NotNull(message = "El email es obligatorio.")
    private String email;

    private boolean enabled;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
}
