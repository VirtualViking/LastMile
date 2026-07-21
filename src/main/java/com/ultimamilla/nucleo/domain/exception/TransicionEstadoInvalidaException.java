package com.ultimamilla.nucleo.domain.exception;

/**
 * Se lanza cuando se intenta una transición de estado no permitida sobre un {@code Pedido}
 * (ej. marcar como entregado un pedido que aún no está en camino).
 */
public class TransicionEstadoInvalidaException extends DomainException {

    public TransicionEstadoInvalidaException(String mensaje) {
        super(mensaje);
    }
}
