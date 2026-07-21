package com.ultimamilla.nucleo.application.port.in;

import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.PedidoId;

/**
 * Caso de uso: cancelar un pedido (solo válido si está CREADO o ASIGNADO).
 */
@FunctionalInterface
public interface CancelarPedidoUseCase {

    Pedido ejecutar(PedidoId pedidoId);
}
