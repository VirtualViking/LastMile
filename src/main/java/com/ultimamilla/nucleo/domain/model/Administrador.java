package com.ultimamilla.nucleo.domain.model;

/**
 * Administrador con acceso operativo a la plataforma (gestión de comercios,
 * repartidores y disputas de pedidos).
 */
public final class Administrador extends Usuario {

    public Administrador(UsuarioId id, String nombre, String email) {
        super(id, nombre, email);
    }

    @Override
    public String rol() {
        return "ADMINISTRADOR";
    }
}
