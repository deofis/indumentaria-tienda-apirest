package com.deofis.tiendaapirest.autenticacion.bootstrap;

import com.deofis.tiendaapirest.autenticacion.domain.Rol;
import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import com.deofis.tiendaapirest.autenticacion.repository.RolRepository;
import com.deofis.tiendaapirest.autenticacion.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
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
            this.rolRepository.save(admin);
        }

        if (!this.rolRepository.findByNombre("ROLE_USER").isPresent()) {
            Rol user = Rol.builder()
                    .nombre("ROLE_USER")
                    .build();
            this.rolRepository.save(user);
        }

        if (!this.usuarioRepository.findByEmail("ezegavilan95@gmail.com").isPresent()) {
            Usuario administrador = Usuario.builder()
                    .email("ezegavilan95@gmail.com")
                    .enabled(true)
                    .fechaCreacion(new Date())
                    .rol(this.rolRepository.findByNombre("ROLE_ADMIN").orElse(null))
                    .password(passwordEncoder.encode("12345"))
                    .build();
            this.usuarioRepository.save(administrador);
        }

    }
}
