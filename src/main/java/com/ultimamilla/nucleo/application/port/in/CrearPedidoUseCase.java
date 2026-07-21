package com.ultimamilla.nucleo.application.port.in;

import com.ultimamilla.nucleo.domain.model.ComercioId;
import com.ultimamilla.nucleo.domain.model.Direccion;
import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.UsuarioId;

/**
 * Caso de uso: crear un pedido nuevo.
 */
@FunctionalInterface
public interface CrearPedidoUseCase {

    Pedido ejecutar(CrearPedidoComando comando);

    /**
     * Datos de entrada. {@code tipoTarifa} es la elección del cliente (estándar o express);
     * el caso de uso decide, a partir de ahí, qué {@code EstrategiaTarifa} del dominio usar —
     * quien llama no conoce esas clases directamente.
     */
    record CrearPedidoComando(
        UsuarioId clienteId,
        ComercioId comercioId,
        Direccion direccionOrigen,
        Direccion direccionDestino,
        TipoTarifa tipoTarifa,
        double distanciaKm
    ) {
    }

    enum TipoTarifa {
        ESTANDAR,
        EXPRESS
    }
}
