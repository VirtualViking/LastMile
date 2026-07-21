package com.ultimamilla.nucleo.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * TEMPORAL — acceso abierto a todos los endpoints. Se reemplaza por autenticación
 * JWT real en la Fase 4. Sin esto, Spring Security bloquearía todo por defecto y
 * pediría el usuario/password autogenerado que se ve en el log al arrancar.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
