package com.ultimamilla.nucleo.application.usecase;

import com.ultimamilla.nucleo.application.exception.PedidoNoEncontradoException;
import com.ultimamilla.nucleo.application.port.in.AsignarRepartidorUseCase;
import com.ultimamilla.nucleo.application.port.out.EventPublisherPort;
import com.ultimamilla.nucleo.application.port.out.PedidoRepositoryPort;
import com.ultimamilla.nucleo.domain.event.PedidoAsignadoEvento;
import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.PedidoId;
import com.ultimamilla.nucleo.domain.model.UsuarioId;

import java.util.Objects;

public final class AsignarRepartidorUseCaseImpl implements AsignarRepartidorUseCase {

    private final PedidoRepositoryPort pedidoRepository;
    private final EventPublisherPort eventPublisher;

    public AsignarRepartidorUseCaseImpl(PedidoRepositoryPort pedidoRepository, EventPublisherPort eventPublisher) {
        this.pedidoRepository = Objects.requireNonNull(pedidoRepository, "El repositorio no puede ser nulo");
        this.eventPublisher = Objects.requireNonNull(eventPublisher, "El publicador de eventos no puede ser nulo");
    }

    @Override
    public Pedido ejecutar(PedidoId pedidoId, UsuarioId repartidorId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId)
            .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));

        pedido.asignarRepartidor(repartidorId);
        Pedido guardado = pedidoRepository.guardar(pedido);

        eventPublisher.publicar(PedidoAsignadoEvento.desde(guardado));

        return guardado;
    }
}
