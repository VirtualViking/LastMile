package com.ultimamilla.nucleo.application.exception;

import com.ultimamilla.nucleo.domain.model.PedidoId;

/**
 * Se lanza cuando un caso de uso busca un {@code Pedido} por id y no existe.
 * Se mapea a HTTP 404 en {@code infrastructure.in.web} (Fase 3).
 */
public class PedidoNoEncontradoException extends RuntimeException {

    public PedidoNoEncontradoException(PedidoId id) {
        super("No existe un pedido con id " + id.valor());
    }
}
