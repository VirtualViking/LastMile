package com.ultimamilla.nucleo.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Identificador de un {@link Usuario} (Cliente, Repartidor o Administrador).
 */
public record UsuarioId(UUID valor) {

    public UsuarioId {
        Objects.requireNonNull(valor, "El id de usuario no puede ser nulo");
    }

    public static UsuarioId nuevo() {
        return new UsuarioId(UUID.randomUUID());
    }
}
