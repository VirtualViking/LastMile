package com.ultimamilla.nucleo.domain.model;

import com.ultimamilla.nucleo.domain.exception.TransicionEstadoInvalidaException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PedidoTest {

    private final UsuarioId clienteId = UsuarioId.nuevo();
    private final UsuarioId repartidorId = UsuarioId.nuevo();
    private final ComercioId comercioId = ComercioId.nuevo();
    private final Direccion origen = new Direccion("Cra 10 #20-30", "Armenia", 4.5339, -75.6811);
    private final Direccion destino = new Direccion("Cll 5 #8-12", "Armenia", 4.5389, -75.6750);

    @Test
    void alCrearseQuedaEnEstadoCreadoConLaTarifaCalculada() {
        Pedido pedido = Pedido.crear(clienteId, comercioId, origen, destino, new TarifaEstandar(), 5.0);

        assertThat(pedido.estado()).isEqualTo(EstadoPedido.CREADO);
        assertThat(pedido.repartidorId()).isNull();
        assertThat(pedido.tarifa()).isEqualTo(new TarifaEstandar().calcular(new DatosEnvio(5.0)));
    }

    @Test
    void elFlujoFelizRecorreTodosLosEstadosEnOrden() {
        Pedido pedido = Pedido.crear(clienteId, comercioId, origen, destino, new TarifaEstandar(), 5.0);

        pedido.asignarRepartidor(repartidorId);
        assertThat(pedido.estado()).isEqualTo(EstadoPedido.ASIGNADO);
        assertThat(pedido.repartidorId()).isEqualTo(repartidorId);

        pedido.iniciarEnvio();
        assertThat(pedido.estado()).isEqualTo(EstadoPedido.EN_CAMINO);

        pedido.marcarEntregado();
        assertThat(pedido.estado()).isEqualTo(EstadoPedido.ENTREGADO);
        assertThat(pedido.fechaEntrega()).isNotNull();
    }

    @Test
    void noSePuedeIniciarEnvioSinAsignarRepartidor() {
        Pedido pedido = Pedido.crear(clienteId, comercioId, origen, destino, new TarifaEstandar(), 5.0);

        assertThatThrownBy(pedido::iniciarEnvio)
            .isInstanceOf(TransicionEstadoInvalidaException.class);
    }

    @Test
    void noSePuedeMarcarEntregadoSinEstarEnCamino() {
        Pedido pedido = Pedido.crear(clienteId, comercioId, origen, destino, new TarifaEstandar(), 5.0);
        pedido.asignarRepartidor(repartidorId);

        assertThatThrownBy(pedido::marcarEntregado)
            .isInstanceOf(TransicionEstadoInvalidaException.class);
    }

    @Test
    void sePuedeCancelarUnPedidoCreadoOAsignado() {
        Pedido pedidoCreado = Pedido.crear(clienteId, comercioId, origen, destino, new TarifaEstandar(), 5.0);
        pedidoCreado.cancelar();
        assertThat(pedidoCreado.estado()).isEqualTo(EstadoPedido.CANCELADO);

        Pedido pedidoAsignado = Pedido.crear(clienteId, comercioId, origen, destino, new TarifaEstandar(), 5.0);
        pedidoAsignado.asignarRepartidor(repartidorId);
        pedidoAsignado.cancelar();
        assertThat(pedidoAsignado.estado()).isEqualTo(EstadoPedido.CANCELADO);
    }

    @Test
    void noSePuedeCancelarUnPedidoEnCaminoNiEntregado() {
        Pedido pedido = Pedido.crear(clienteId, comercioId, origen, destino, new TarifaEstandar(), 5.0);
        pedido.asignarRepartidor(repartidorId);
        pedido.iniciarEnvio();

        assertThatThrownBy(pedido::cancelar)
            .isInstanceOf(TransicionEstadoInvalidaException.class);

        pedido.marcarEntregado();

        assertThatThrownBy(pedido::cancelar)
            .isInstanceOf(TransicionEstadoInvalidaException.class);
    }
}
