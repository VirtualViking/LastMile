package com.ultimamilla.nucleo.application.usecase;

import com.ultimamilla.nucleo.application.exception.PedidoNoEncontradoException;
import com.ultimamilla.nucleo.application.port.out.PedidoRepositoryPort;
import com.ultimamilla.nucleo.domain.model.ComercioId;
import com.ultimamilla.nucleo.domain.model.Direccion;
import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.PedidoId;
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
class ConsultarPedidoUseCaseImplTest {

    @Mock
    private PedidoRepositoryPort pedidoRepository;

    @Test
    void devuelveElPedidoCuandoExiste() {
        Direccion origen = new Direccion("Cra 10 #20-30", "Armenia", 4.5339, -75.6811);
        Direccion destino = new Direccion("Cll 5 #8-12", "Armenia", 4.5389, -75.6750);
        Pedido pedido = Pedido.crear(UsuarioId.nuevo(), ComercioId.nuevo(), origen, destino,
            new TarifaEstandar(), 5.0);

        when(pedidoRepository.buscarPorId(pedido.id())).thenReturn(Optional.of(pedido));

        Pedido resultado = new ConsultarPedidoUseCaseImpl(pedidoRepository).ejecutar(pedido.id());

        assertThat(resultado.id()).isEqualTo(pedido.id());
    }

    @Test
    void lanzaExcepcionCuandoNoExiste() {
        when(pedidoRepository.buscarPorId(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> new ConsultarPedidoUseCaseImpl(pedidoRepository).ejecutar(PedidoId.nuevo()))
            .isInstanceOf(PedidoNoEncontradoException.class);
    }
}
