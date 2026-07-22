package com.ultimamilla.nucleo.infrastructure.in.web;

import com.ultimamilla.nucleo.domain.model.Usuario;

import java.util.UUID;

public record UsuarioResponse(UUID id, String nombre, String email, String rol) {

    public static UsuarioResponse desde(Usuario usuario) {
        return new UsuarioResponse(usuario.id().valor(), usuario.nombre(), usuario.email(), usuario.rol());
    }
}
