package com.ultimamilla.nucleo.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Identificador de un {@link Comercio}.
 */
public record ComercioId(UUID valor) {

    public ComercioId {
        Objects.requireNonNull(valor, "El id de comercio no puede ser nulo");
    }

    public static ComercioId nuevo() {
        return new ComercioId(UUID.randomUUID());
    }
}
