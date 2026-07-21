package com.ultimamilla.nucleo.application.usecase;

import com.ultimamilla.nucleo.application.exception.PedidoNoEncontradoException;
import com.ultimamilla.nucleo.application.port.in.ConsultarPedidoUseCase;
import com.ultimamilla.nucleo.application.port.out.PedidoRepositoryPort;
import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.PedidoId;

import java.util.Objects;

public final class ConsultarPedidoUseCaseImpl implements ConsultarPedidoUseCase {

    private final PedidoRepositoryPort pedidoRepository;

    public ConsultarPedidoUseCaseImpl(PedidoRepositoryPort pedidoRepository) {
        this.pedidoRepository = Objects.requireNonNull(pedidoRepository, "El repositorio no puede ser nulo");
    }

    @Override
    public Pedido ejecutar(PedidoId pedidoId) {
        return pedidoRepository.buscarPorId(pedidoId)
            .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));
    }
}
