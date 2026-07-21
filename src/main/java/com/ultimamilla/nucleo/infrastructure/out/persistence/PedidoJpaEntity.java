package com.ultimamilla.nucleo.infrastructure.out.persistence;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Representación JPA de {@code Pedido}, mapeada a la tabla {@code pedidos}
 * (creada por la migración {@code V1__crear_tabla_pedidos.sql}).
 *
 * <p>Es una clase distinta del agregado de dominio {@code Pedido} a propósito —
 * el mapeo entre ambas vive en {@link PedidoMapper}. Esto es lo que permite que
 * {@code domain} no sepa nada de JPA.
 */
@Entity
@Table(name = "pedidos")
public class PedidoJpaEntity {

    @Id
    private UUID id;

    @Column(name = "cliente_id", nullable = false)
    private UUID clienteId;

    @Column(name = "comercio_id", nullable = false)
    private UUID comercioId;

    @Column(name = "repartidor_id")
    private UUID repartidorId;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "calle", column = @Column(name = "origen_calle")),
        @AttributeOverride(name = "ciudad", column = @Column(name = "origen_ciudad")),
        @AttributeOverride(name = "latitud", column = @Column(name = "origen_latitud")),
        @AttributeOverride(name = "longitud", column = @Column(name = "origen_longitud"))
    })
    private DireccionEmbeddable direccionOrigen;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "calle", column = @Column(name = "destino_calle")),
        @AttributeOverride(name = "ciudad", column = @Column(name = "destino_ciudad")),
        @AttributeOverride(name = "latitud", column = @Column(name = "destino_latitud")),
        @AttributeOverride(name = "longitud", column = @Column(name = "destino_longitud"))
    })
    private DireccionEmbeddable direccionDestino;

    @Column(name = "tarifa_monto", nullable = false, precision = 12, scale = 2)
    private BigDecimal tarifaMonto;

    @Column(name = "tarifa_moneda", nullable = false, length = 3)
    private String tarifaMoneda;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "fecha_creacion", nullable = false)
    private Instant fechaCreacion;

    @Column(name = "fecha_asignacion")
    private Instant fechaAsignacion;

    @Column(name = "fecha_entrega")
    private Instant fechaEntrega;

    protected PedidoJpaEntity() {
        // requerido por JPA
    }

    public PedidoJpaEntity(UUID id, UUID clienteId, UUID comercioId, UUID repartidorId,
                            DireccionEmbeddable direccionOrigen, DireccionEmbeddable direccionDestino,
                            BigDecimal tarifaMonto, String tarifaMoneda, String estado,
                            Instant fechaCreacion, Instant fechaAsignacion, Instant fechaEntrega) {
        this.id = id;
        this.clienteId = clienteId;
        this.comercioId = comercioId;
        this.repartidorId = repartidorId;
        this.direccionOrigen = direccionOrigen;
        this.direccionDestino = direccionDestino;
        this.tarifaMonto = tarifaMonto;
        this.tarifaMoneda = tarifaMoneda;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaEntrega = fechaEntrega;
    }

    public UUID getId() {
        return id;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public UUID getComercioId() {
        return comercioId;
    }

    public UUID getRepartidorId() {
        return repartidorId;
    }

    public DireccionEmbeddable getDireccionOrigen() {
        return direccionOrigen;
    }

    public DireccionEmbeddable getDireccionDestino() {
        return direccionDestino;
    }

    public BigDecimal getTarifaMonto() {
        return tarifaMonto;
    }

    public String getTarifaMoneda() {
        return tarifaMoneda;
    }

    public String getEstado() {
        return estado;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public Instant getFechaAsignacion() {
        return fechaAsignacion;
    }

    public Instant getFechaEntrega() {
        return fechaEntrega;
    }
}
