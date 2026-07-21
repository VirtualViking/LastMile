package com.ultimamilla.nucleo.infrastructure.in.web;

import com.ultimamilla.nucleo.domain.model.Pedido;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PedidoResponse(
    UUID id,
    UUID clienteId,
    UUID comercioId,
    UUID repartidorId,
    DireccionResponse direccionOrigen,
    DireccionResponse direccionDestino,
    BigDecimal tarifaMonto,
    String tarifaMoneda,
    String estado,
    Instant fechaCreacion,
    Instant fechaAsignacion,
    Instant fechaEntrega
) {

    public static PedidoResponse desde(Pedido pedido) {
        return new PedidoResponse(
            pedido.id().valor(),
            pedido.clienteId().valor(),
            pedido.comercioId().valor(),
            pedido.repartidorId() != null ? pedido.repartidorId().valor() : null,
            DireccionResponse.desde(pedido.direccionOrigen()),
            DireccionResponse.desde(pedido.direccionDestino()),
            pedido.tarifa().monto(),
            pedido.tarifa().moneda(),
            pedido.estado().name(),
            pedido.fechaCreacion(),
            pedido.fechaAsignacion(),
            pedido.fechaEntrega()
        );
    }
}
