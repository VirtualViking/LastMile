package com.ultimamilla.nucleo.application.usecase;

import com.ultimamilla.nucleo.application.port.in.CrearPedidoUseCase;
import com.ultimamilla.nucleo.application.port.out.PedidoRepositoryPort;
import com.ultimamilla.nucleo.domain.model.EstrategiaTarifa;
import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.TarifaEstandar;
import com.ultimamilla.nucleo.domain.model.TarifaExpress;

import java.util.Objects;

public final class CrearPedidoUseCaseImpl implements CrearPedidoUseCase {

    private final PedidoRepositoryPort pedidoRepository;

    public CrearPedidoUseCaseImpl(PedidoRepositoryPort pedidoRepository) {
        this.pedidoRepository = Objects.requireNonNull(pedidoRepository, "El repositorio no puede ser nulo");
    }

    @Override
    public Pedido ejecutar(CrearPedidoComando comando) {
        Objects.requireNonNull(comando, "El comando no puede ser nulo");

        EstrategiaTarifa estrategiaTarifa = resolverEstrategia(comando.tipoTarifa());
        Pedido pedido = Pedido.crear(
            comando.clienteId(),
            comando.comercioId(),
            comando.direccionOrigen(),
            comando.direccionDestino(),
            estrategiaTarifa,
            comando.distanciaKm()
        );

        return pedidoRepository.guardar(pedido);
    }

    private static EstrategiaTarifa resolverEstrategia(TipoTarifa tipoTarifa) {
        Objects.requireNonNull(tipoTarifa, "El tipo de tarifa no puede ser nulo");
        return switch (tipoTarifa) {
            case ESTANDAR -> new TarifaEstandar();
            case EXPRESS -> new TarifaExpress();
        };
    }
}
