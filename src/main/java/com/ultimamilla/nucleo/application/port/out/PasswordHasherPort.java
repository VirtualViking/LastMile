package com.ultimamilla.nucleo.application.port.out;

/**
 * Puerto de salida para hashear y verificar contraseñas. La implementación real
 * (BCrypt de Spring Security) vive en {@code infrastructure.security} — la aplicación
 * nunca sabe qué algoritmo hay detrás.
 */
public interface PasswordHasherPort {

    String hash(String passwordPlano);

    boolean coincide(String passwordPlano, String hashAlmacenado);
}
