package com.ultimamilla.nucleo.infrastructure.security;

import com.ultimamilla.nucleo.application.port.out.PasswordHasherPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordHasherAdapter implements PasswordHasherPort {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String hash(String passwordPlano) {
        return encoder.encode(passwordPlano);
    }

    @Override
    public boolean coincide(String passwordPlano, String hashAlmacenado) {
        return encoder.matches(passwordPlano, hashAlmacenado);
    }
}
