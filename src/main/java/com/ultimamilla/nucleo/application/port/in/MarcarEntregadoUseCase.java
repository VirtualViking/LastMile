package com.ultimamilla.nucleo.application.port.in;

import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.PedidoId;

/**
 * Caso de uso: marcar un pedido EN_CAMINO como ENTREGADO.
 */
@FunctionalInterface
public interface MarcarEntregadoUseCase {

    Pedido ejecutar(PedidoId pedidoId);
}
