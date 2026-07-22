package com.ultimamilla.nucleo.application.port.out;

import com.ultimamilla.nucleo.domain.model.UsuarioId;

import java.util.Optional;

/**
 * Puerto de salida para emitir y validar tokens de sesión. La implementación real
 * (JWT con la librería jjwt) vive en {@code infrastructure.security} — la aplicación
 * no sabe que existe JWT, solo que puede pedir un "token" y validarlo.
 */
public interface TokenProviderPort {

    String generarToken(UsuarioId usuarioId, String rol);

    Optional<TokenPayload> validarToken(String token);

    record TokenPayload(UsuarioId usuarioId, String rol) {
    }
}
