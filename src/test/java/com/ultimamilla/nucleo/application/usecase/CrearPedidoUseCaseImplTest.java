package com.ultimamilla.nucleo.application.usecase;

import com.ultimamilla.nucleo.application.port.in.CrearPedidoUseCase.CrearPedidoComando;
import com.ultimamilla.nucleo.application.port.in.CrearPedidoUseCase.TipoTarifa;
import com.ultimamilla.nucleo.application.port.out.PedidoRepositoryPort;
import com.ultimamilla.nucleo.domain.model.ComercioId;
import com.ultimamilla.nucleo.domain.model.Direccion;
import com.ultimamilla.nucleo.domain.model.EstadoPedido;
import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.UsuarioId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrearPedidoUseCaseImplTest {

    @Mock
    private PedidoRepositoryPort pedidoRepository;

    private final Direccion origen = new Direccion("Cra 10 #20-30", "Armenia", 4.5339, -75.6811);
    private final Direccion destino = new Direccion("Cll 5 #8-12", "Armenia", 4.5389, -75.6750);

    @Test
    void creaElPedidoConLaEstrategiaEstandarYLoGuarda() {
        when(pedidoRepository.guardar(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));
        CrearPedidoUseCaseImpl useCase = new CrearPedidoUseCaseImpl(pedidoRepository);

        CrearPedidoComando comando = new CrearPedidoComando(
            UsuarioId.nuevo(), ComercioId.nuevo(), origen, destino, TipoTarifa.ESTANDAR, 5.0);

        Pedido resultado = useCase.ejecutar(comando);

        assertThat(resultado.estado()).isEqualTo(EstadoPedido.CREADO);

        ArgumentCaptor<Pedido> captor = ArgumentCaptor.forClass(Pedido.class);
        verify(pedidoRepository).guardar(captor.capture());
        assertThat(captor.getValue().id()).isEqualTo(resultado.id());
    }

    @Test
    void conTipoExpressLaTarifaResultanteEsMasAltaQueConEstandar() {
        when(pedidoRepository.guardar(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));
        CrearPedidoUseCaseImpl useCase = new CrearPedidoUseCaseImpl(pedidoRepository);

        CrearPedidoComando comandoEstandar = new CrearPedidoComando(
            UsuarioId.nuevo(), ComercioId.nuevo(), origen, destino, TipoTarifa.ESTANDAR, 5.0);
        CrearPedidoComando comandoExpress = new CrearPedidoComando(
            UsuarioId.nuevo(), ComercioId.nuevo(), origen, destino, TipoTarifa.EXPRESS, 5.0);

        Pedido estandar = useCase.ejecutar(comandoEstandar);
        Pedido express = useCase.ejecutar(comandoExpress);

        assertThat(express.tarifa().monto()).isGreaterThan(estandar.tarifa().monto());
    }
}
