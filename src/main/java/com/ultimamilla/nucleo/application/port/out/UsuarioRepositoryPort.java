package com.ultimamilla.nucleo.application.port.out;

import com.ultimamilla.nucleo.domain.model.Usuario;

import java.util.Optional;

/**
 * Puerto de salida hacia la persistencia de {@link Usuario}. El hash de la contraseña
 * viaja junto al usuario, pero deliberadamente no es parte del objeto de dominio —
 * es un detalle de autenticación, no un atributo de negocio de "quién es" el usuario.
 */
public interface UsuarioRepositoryPort {

    Usuario guardar(Usuario usuario, String passwordHash);

    Optional<UsuarioConCredencial> buscarPorEmailConCredencial(String email);

    boolean existeEmail(String email);

    record UsuarioConCredencial(Usuario usuario, String passwordHash) {
    }
}
