package com.deofis.tiendaapirest.autenticacion.services;

import com.deofis.tiendaapirest.autenticacion.domain.Rol;
import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import com.deofis.tiendaapirest.autenticacion.exceptions.AutenticacionException;
import com.deofis.tiendaapirest.autenticacion.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        Usuario usuario = this.usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new AutenticacionException("Usuario no encontrado con " +
                        "username: " + userEmail));

        return new User(usuario.getEmail(), usuario.getPassword(), usuario.isEnabled(),
                true, true, true,
                getAuthorities(usuario.getRol()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Rol rol) {
        return Collections.singletonList(new SimpleGrantedAuthority(rol.getNombre()));
    }
}
