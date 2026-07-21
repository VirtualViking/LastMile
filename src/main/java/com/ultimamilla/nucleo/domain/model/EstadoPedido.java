package com.ultimamilla.nucleo.domain.model;

/**
 * Estados posibles de un {@link Pedido}. Las transiciones válidas entre estos
 * estados las controla exclusivamente {@code Pedido} — este enum solo enumera
 * los valores posibles, no la lógica de transición.
 */
public enum EstadoPedido {
    CREADO,
    ASIGNADO,
    EN_CAMINO,
    ENTREGADO,
    CANCELADO
}
