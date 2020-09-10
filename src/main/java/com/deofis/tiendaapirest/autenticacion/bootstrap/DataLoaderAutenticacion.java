package com.deofis.tiendaapirest.autenticacion.bootstrap;

import com.deofis.tiendaapirest.autenticacion.domain.Rol;
import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import com.deofis.tiendaapirest.autenticacion.exceptions.AutenticacionException;
import com.deofis.tiendaapirest.autenticacion.repositories.RolRepository;
import com.deofis.tiendaapirest.autenticacion.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
public class DataLoaderAutenticacion implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (!this.rolRepository.findByNombre("ROLE_ADMIN").isPresent()) {
            Rol admin = Rol.builder()
                    .nombre("ROLE_ADMIN")
                    .build();
            try {
                this.rolRepository.save(admin);
            } catch (DataIntegrityViolationException e) {
                e.getMessage();
            }

        }

        if (!this.rolRepository.findByNombre("ROLE_USER").isPresent()) {
            Rol user = Rol.builder()
                    .nombre("ROLE_USER")
                    .build();

            try {
                this.rolRepository.save(user);
            } catch (DataIntegrityViolationException e) {
                e.getMessage();
            }

        }

        if (!this.usuarioRepository.findByEmail("ezegavilan95@gmail.com").isPresent()) {
            Usuario administrador = Usuario.builder()
                    .email("ezegavilan95@gmail.com")
                    .enabled(true)
                    .fechaCreacion(new Date())
                    .rol(this.rolRepository.findByNombre("ROLE_ADMIN").orElse(null))
                    .password(passwordEncoder.encode("12345"))
                    .build();

            try {
                this.usuarioRepository.save(administrador);
            } catch (DataIntegrityViolationException e) {
                throw new AutenticacionException(e.getMessage());
            }

        }

    }
}
