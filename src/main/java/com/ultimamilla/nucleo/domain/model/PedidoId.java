package com.ultimamilla.nucleo.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Identificador de un {@link Pedido}.
 */
public record PedidoId(UUID valor) {

    public PedidoId {
        Objects.requireNonNull(valor, "El id de pedido no puede ser nulo");
    }

    public static PedidoId nuevo() {
        return new PedidoId(UUID.randomUUID());
    }
}
