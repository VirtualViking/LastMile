package com.ultimamilla.nucleo.application.usecase;

import com.ultimamilla.nucleo.application.port.out.PedidoRepositoryPort;
import com.ultimamilla.nucleo.domain.exception.TransicionEstadoInvalidaException;
import com.ultimamilla.nucleo.domain.model.ComercioId;
import com.ultimamilla.nucleo.domain.model.Direccion;
import com.ultimamilla.nucleo.domain.model.EstadoPedido;
import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.TarifaEstandar;
import com.ultimamilla.nucleo.domain.model.UsuarioId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarcarEntregadoUseCaseImplTest {

    @Mock
    private PedidoRepositoryPort pedidoRepository;

    private final Direccion origen = new Direccion("Cra 10 #20-30", "Armenia", 4.5339, -75.6811);
    private final Direccion destino = new Direccion("Cll 5 #8-12", "Armenia", 4.5389, -75.6750);

    @Test
    void marcaElPedidoEnCaminoComoEntregado() {
        Pedido pedido = Pedido.crear(UsuarioId.nuevo(), ComercioId.nuevo(), origen, destino,
            new TarifaEstandar(), 5.0);
        pedido.asignarRepartidor(UsuarioId.nuevo());
        pedido.iniciarEnvio();

        when(pedidoRepository.buscarPorId(pedido.id())).thenReturn(Optional.of(pedido));
        when(pedidoRepository.guardar(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        Pedido resultado = new MarcarEntregadoUseCaseImpl(pedidoRepository).ejecutar(pedido.id());

        assertThat(resultado.estado()).isEqualTo(EstadoPedido.ENTREGADO);
        assertThat(resultado.fechaEntrega()).isNotNull();
    }

    @Test
    void siElPedidoNoEstaEnCaminoPropagaLaExcepcionDeDominio() {
        Pedido pedido = Pedido.crear(UsuarioId.nuevo(), ComercioId.nuevo(), origen, destino,
            new TarifaEstandar(), 5.0);
        when(pedidoRepository.buscarPorId(pedido.id())).thenReturn(Optional.of(pedido));

        assertThatThrownBy(() -> new MarcarEntregadoUseCaseImpl(pedidoRepository).ejecutar(pedido.id()))
            .isInstanceOf(TransicionEstadoInvalidaException.class);
    }
}
