package com.ultimamilla.nucleo.application.port.in;

import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.PedidoId;

/**
 * Caso de uso: marcar un pedido ASIGNADO como EN_CAMINO.
 */
@FunctionalInterface
public interface IniciarEnvioUseCase {

    Pedido ejecutar(PedidoId pedidoId);
}
