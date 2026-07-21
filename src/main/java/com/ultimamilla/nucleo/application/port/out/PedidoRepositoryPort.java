package com.ultimamilla.nucleo.application.port.out;

import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.PedidoId;

import java.util.Optional;

/**
 * Puerto de salida hacia la persistencia de {@link Pedido}. La implementación real
 * (JPA + PostgreSQL) vive en {@code infrastructure.out.persistence}, a partir de la Fase 3.
 */
public interface PedidoRepositoryPort {

    Pedido guardar(Pedido pedido);

    Optional<Pedido> buscarPorId(PedidoId id);
}
