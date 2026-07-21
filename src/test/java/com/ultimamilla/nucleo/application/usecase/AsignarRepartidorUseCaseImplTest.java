package com.ultimamilla.nucleo.application.usecase;

import com.ultimamilla.nucleo.application.exception.PedidoNoEncontradoException;
import com.ultimamilla.nucleo.application.port.out.EventPublisherPort;
import com.ultimamilla.nucleo.application.port.out.PedidoRepositoryPort;
import com.ultimamilla.nucleo.domain.event.EventoDominio;
import com.ultimamilla.nucleo.domain.event.PedidoAsignadoEvento;
import com.ultimamilla.nucleo.domain.model.ComercioId;
import com.ultimamilla.nucleo.domain.model.Direccion;
import com.ultimamilla.nucleo.domain.model.EstadoPedido;
import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.PedidoId;
import com.ultimamilla.nucleo.domain.model.TarifaEstandar;
import com.ultimamilla.nucleo.domain.model.UsuarioId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AsignarRepartidorUseCaseImplTest {

    @Mock
    private PedidoRepositoryPort pedidoRepository;

    @Mock
    private EventPublisherPort eventPublisher;

    private final Direccion origen = new Direccion("Cra 10 #20-30", "Armenia", 4.5339, -75.6811);
    private final Direccion destino = new Direccion("Cll 5 #8-12", "Armenia", 4.5389, -75.6750);

    @Test
    void asignaElRepartidorYPublicaElEvento() {
        Pedido pedido = Pedido.crear(UsuarioId.nuevo(), ComercioId.nuevo(), origen, destino,
            new TarifaEstandar(), 5.0);
        UsuarioId repartidorId = UsuarioId.nuevo();

        when(pedidoRepository.buscarPorId(pedido.id())).thenReturn(Optional.of(pedido));
        when(pedidoRepository.guardar(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        AsignarRepartidorUseCaseImpl useCase = new AsignarRepartidorUseCaseImpl(pedidoRepository, eventPublisher);

        Pedido resultado = useCase.ejecutar(pedido.id(), repartidorId);

        assertThat(resultado.estado()).isEqualTo(EstadoPedido.ASIGNADO);
        assertThat(resultado.repartidorId()).isEqualTo(repartidorId);

        ArgumentCaptor<EventoDominio> captor = ArgumentCaptor.forClass(EventoDominio.class);
        verify(eventPublisher).publicar(captor.capture());
        assertThat(captor.getValue()).isInstanceOf(PedidoAsignadoEvento.class);

        PedidoAsignadoEvento evento = (PedidoAsignadoEvento) captor.getValue();
        assertThat(evento.pedidoId()).isEqualTo(pedido.id());
        assertThat(evento.repartidorId()).isEqualTo(repartidorId);
    }

    @Test
    void siElPedidoNoExisteLanzaExcepcionYNoPublicaNada() {
        when(pedidoRepository.buscarPorId(any())).thenReturn(Optional.empty());
        AsignarRepartidorUseCaseImpl useCase = new AsignarRepartidorUseCaseImpl(pedidoRepository, eventPublisher);

        assertThatThrownBy(() -> useCase.ejecutar(PedidoId.nuevo(), UsuarioId.nuevo()))
            .isInstanceOf(PedidoNoEncontradoException.class);

        verify(eventPublisher, never()).publicar(any());
    }
}
