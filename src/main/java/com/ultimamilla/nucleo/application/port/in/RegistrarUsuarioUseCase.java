package com.ultimamilla.nucleo.application.port.in;

import com.ultimamilla.nucleo.domain.model.Usuario;

/**
 * Caso de uso: registrar una cuenta nueva (Cliente, Repartidor o Administrador).
 */
@FunctionalInterface
public interface RegistrarUsuarioUseCase {

    Usuario ejecutar(RegistrarUsuarioComando comando);

    record RegistrarUsuarioComando(String nombre, String email, String passwordPlano, Rol rol) {
    }

    enum Rol {
        CLIENTE,
        REPARTIDOR,
        ADMINISTRADOR
    }
}
