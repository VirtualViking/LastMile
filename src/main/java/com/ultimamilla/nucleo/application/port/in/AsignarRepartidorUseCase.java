package com.ultimamilla.nucleo.application.port.in;

import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.PedidoId;
import com.ultimamilla.nucleo.domain.model.UsuarioId;

/**
 * Caso de uso: asignar un repartidor a un pedido en estado CREADO.
 * Al completarse, publica {@code PedidoAsignadoEvento}.
 */
@FunctionalInterface
public interface AsignarRepartidorUseCase {

    Pedido ejecutar(PedidoId pedidoId, UsuarioId repartidorId);
}
