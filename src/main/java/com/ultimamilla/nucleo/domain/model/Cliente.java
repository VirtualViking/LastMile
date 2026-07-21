package com.ultimamilla.nucleo.domain.model;

/**
 * Cliente que realiza pedidos en la plataforma.
 */
public final class Cliente extends Usuario {

    public Cliente(UsuarioId id, String nombre, String email) {
        super(id, nombre, email);
    }

    @Override
    public String rol() {
        return "CLIENTE";
    }
}
