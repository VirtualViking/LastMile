package com.ultimamilla.nucleo.application.port.in;

import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.PedidoId;

/**
 * Caso de uso: consultar un pedido existente por su id.
 */
@FunctionalInterface
public interface ConsultarPedidoUseCase {

    Pedido ejecutar(PedidoId pedidoId);
}
