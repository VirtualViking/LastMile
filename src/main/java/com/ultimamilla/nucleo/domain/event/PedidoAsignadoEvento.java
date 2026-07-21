package com.ultimamilla.nucleo.domain.event;

import com.ultimamilla.nucleo.domain.model.Direccion;
import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.PedidoId;
import com.ultimamilla.nucleo.domain.model.UsuarioId;

import java.time.Instant;
import java.util.Objects;

/**
 * Se emite cuando un {@link Pedido} pasa a estado ASIGNADO. El Servicio de Tracking
 * consume este evento (vía SQS, desde la Fase 7) para empezar a esperar actualizaciones
 * de ubicación del repartidor para este pedido específico.
 */
public record PedidoAsignadoEvento(PedidoId pedidoId, UsuarioId repartidorId, Direccion direccionOrigen,
                                    Direccion direccionDestino, Instant ocurridoEn) implements EventoDominio {

    public PedidoAsignadoEvento {
        Objects.requireNonNull(pedidoId, "El id de pedido no puede ser nulo");
        Objects.requireNonNull(repartidorId, "El id de repartidor no puede ser nulo");
        Objects.requireNonNull(direccionOrigen, "La dirección de origen no puede ser nula");
        Objects.requireNonNull(direccionDestino, "La dirección de destino no puede ser nula");
        Objects.requireNonNull(ocurridoEn, "La fecha de ocurrencia no puede ser nula");
    }

    /**
     * Construye el evento a partir de un pedido ya asignado. Falla rápido si el pedido
     * recibido en realidad no tiene repartidor — evita publicar un evento incoherente.
     */
    public static PedidoAsignadoEvento desde(Pedido pedido) {
        Objects.requireNonNull(pedido, "El pedido no puede ser nulo");
        if (pedido.repartidorId() == null) {
            throw new IllegalStateException(
                "No se puede construir PedidoAsignadoEvento para un pedido sin repartidor asignado");
        }
        return new PedidoAsignadoEvento(pedido.id(), pedido.repartidorId(), pedido.direccionOrigen(),
            pedido.direccionDestino(), Instant.now());
    }
}
