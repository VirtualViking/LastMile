package com.ultimamilla.nucleo.domain.model;

import com.ultimamilla.nucleo.domain.exception.TransicionEstadoInvalidaException;

import java.time.Instant;
import java.util.Objects;

/**
 * Agregado raíz del dominio. Controla su propio ciclo de vida — todo cambio de
 * estado pasa por un método que valida la transición; no hay setters. Este es
 * el punto central de encapsulamiento del modelo.
 *
 * <p>Transiciones válidas:
 * <pre>
 * CREADO ──asignarRepartidor──▶ ASIGNADO ──iniciarEnvio──▶ EN_CAMINO ──marcarEntregado──▶ ENTREGADO
 *   │                              │
 *   └──────────cancelar───────────┘
 * </pre>
 * Un pedido EN_CAMINO o ENTREGADO ya no se puede cancelar.
 */
public final class Pedido {

    private final PedidoId id;
    private final UsuarioId clienteId;
    private final ComercioId comercioId;
    private final Direccion direccionOrigen;
    private final Direccion direccionDestino;
    private final Dinero tarifa;
    private final Instant fechaCreacion;

    private EstadoPedido estado;
    private UsuarioId repartidorId;
    private Instant fechaAsignacion;
    private Instant fechaEntrega;

    private Pedido(PedidoId id, UsuarioId clienteId, ComercioId comercioId, Direccion direccionOrigen,
                    Direccion direccionDestino, Dinero tarifa, Instant fechaCreacion) {
        this.id = id;
        this.clienteId = clienteId;
        this.comercioId = comercioId;
        this.direccionOrigen = direccionOrigen;
        this.direccionDestino = direccionDestino;
        this.tarifa = tarifa;
        this.fechaCreacion = fechaCreacion;
        this.estado = EstadoPedido.CREADO;
    }

    /**
     * Crea un pedido nuevo. La tarifa se calcula aquí, a través de la estrategia
     * recibida — el pedido no sabe si es estándar, express, o cualquier otra futura.
     */
    public static Pedido crear(UsuarioId clienteId, ComercioId comercioId, Direccion direccionOrigen,
                                Direccion direccionDestino, EstrategiaTarifa estrategiaTarifa, double distanciaKm) {
        Objects.requireNonNull(clienteId, "El cliente no puede ser nulo");
        Objects.requireNonNull(comercioId, "El comercio no puede ser nulo");
        Objects.requireNonNull(direccionOrigen, "La dirección de origen no puede ser nula");
        Objects.requireNonNull(direccionDestino, "La dirección de destino no puede ser nula");
        Objects.requireNonNull(estrategiaTarifa, "La estrategia de tarifa no puede ser nula");

        Dinero tarifa = estrategiaTarifa.calcular(new DatosEnvio(distanciaKm));
        return new Pedido(PedidoId.nuevo(), clienteId, comercioId, direccionOrigen, direccionDestino,
            tarifa, Instant.now());
    }

    /**
     * Reconstruye un pedido ya existente desde persistencia, con su estado real.
     * A diferencia de {@link #crear}, no recalcula nada ni valida reglas de creación —
     * lo usa exclusivamente el mapper de {@code infrastructure.out.persistence} (Fase 3).
     */
    public static Pedido reconstruir(PedidoId id, UsuarioId clienteId, ComercioId comercioId,
                                      Direccion direccionOrigen, Direccion direccionDestino, Dinero tarifa,
                                      EstadoPedido estado, UsuarioId repartidorId, Instant fechaCreacion,
                                      Instant fechaAsignacion, Instant fechaEntrega) {
        Pedido pedido = new Pedido(id, clienteId, comercioId, direccionOrigen, direccionDestino,
            tarifa, fechaCreacion);
        pedido.estado = estado;
        pedido.repartidorId = repartidorId;
        pedido.fechaAsignacion = fechaAsignacion;
        pedido.fechaEntrega = fechaEntrega;
        return pedido;
    }

    public void asignarRepartidor(UsuarioId repartidorId) {
        Objects.requireNonNull(repartidorId, "El repartidor no puede ser nulo");
        if (estado != EstadoPedido.CREADO) {
            throw new TransicionEstadoInvalidaException(
                "No se puede asignar un repartidor a un pedido en estado " + estado);
        }
        this.repartidorId = repartidorId;
        this.estado = EstadoPedido.ASIGNADO;
        this.fechaAsignacion = Instant.now();
    }

    public void iniciarEnvio() {
        if (estado != EstadoPedido.ASIGNADO) {
            throw new TransicionEstadoInvalidaException(
                "No se puede iniciar el envío de un pedido en estado " + estado);
        }
        this.estado = EstadoPedido.EN_CAMINO;
    }

    public void marcarEntregado() {
        if (estado != EstadoPedido.EN_CAMINO) {
            throw new TransicionEstadoInvalidaException(
                "No se puede marcar como entregado un pedido en estado " + estado);
        }
        this.estado = EstadoPedido.ENTREGADO;
        this.fechaEntrega = Instant.now();
    }

    public void cancelar() {
        if (estado == EstadoPedido.EN_CAMINO || estado == EstadoPedido.ENTREGADO) {
            throw new TransicionEstadoInvalidaException(
                "No se puede cancelar un pedido en estado " + estado);
        }
        this.estado = EstadoPedido.CANCELADO;
    }

    public PedidoId id() {
        return id;
    }

    public UsuarioId clienteId() {
        return clienteId;
    }

    public ComercioId comercioId() {
        return comercioId;
    }

    public Direccion direccionOrigen() {
        return direccionOrigen;
    }

    public Direccion direccionDestino() {
        return direccionDestino;
    }

    public Dinero tarifa() {
        return tarifa;
    }

    public EstadoPedido estado() {
        return estado;
    }

    public UsuarioId repartidorId() {
        return repartidorId;
    }

    public Instant fechaCreacion() {
        return fechaCreacion;
    }

    public Instant fechaAsignacion() {
        return fechaAsignacion;
    }

    public Instant fechaEntrega() {
        return fechaEntrega;
    }
}
