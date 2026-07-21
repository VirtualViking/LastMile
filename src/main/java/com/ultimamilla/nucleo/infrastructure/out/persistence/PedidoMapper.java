package com.ultimamilla.nucleo.infrastructure.out.persistence;

import com.ultimamilla.nucleo.domain.model.ComercioId;
import com.ultimamilla.nucleo.domain.model.Dinero;
import com.ultimamilla.nucleo.domain.model.Direccion;
import com.ultimamilla.nucleo.domain.model.EstadoPedido;
import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.PedidoId;
import com.ultimamilla.nucleo.domain.model.UsuarioId;

/**
 * Traduce entre el agregado de dominio {@code Pedido} y su representación JPA.
 * Es el único lugar del sistema que conoce ambos mundos a la vez.
 */
public final class PedidoMapper {

    private PedidoMapper() {
    }

    public static PedidoJpaEntity aEntidad(Pedido pedido) {
        return new PedidoJpaEntity(
            pedido.id().valor(),
            pedido.clienteId().valor(),
            pedido.comercioId().valor(),
            pedido.repartidorId() != null ? pedido.repartidorId().valor() : null,
            aEmbeddable(pedido.direccionOrigen()),
            aEmbeddable(pedido.direccionDestino()),
            pedido.tarifa().monto(),
            pedido.tarifa().moneda(),
            pedido.estado().name(),
            pedido.fechaCreacion(),
            pedido.fechaAsignacion(),
            pedido.fechaEntrega()
        );
    }

    /**
     * Reconstruye el agregado de dominio desde su representación JPA — usa
     * {@code Pedido.reconstruir}, el factory pensado exactamente para esto desde la Fase 1.
     */
    public static Pedido aDominio(PedidoJpaEntity entidad) {
        UsuarioId repartidorId = entidad.getRepartidorId() != null
            ? new UsuarioId(entidad.getRepartidorId())
            : null;

        return Pedido.reconstruir(
            new PedidoId(entidad.getId()),
            new UsuarioId(entidad.getClienteId()),
            new ComercioId(entidad.getComercioId()),
            aDireccion(entidad.getDireccionOrigen()),
            aDireccion(entidad.getDireccionDestino()),
            new Dinero(entidad.getTarifaMonto(), entidad.getTarifaMoneda()),
            EstadoPedido.valueOf(entidad.getEstado()),
            repartidorId,
            entidad.getFechaCreacion(),
            entidad.getFechaAsignacion(),
            entidad.getFechaEntrega()
        );
    }

    private static DireccionEmbeddable aEmbeddable(Direccion direccion) {
        return new DireccionEmbeddable(direccion.calle(), direccion.ciudad(),
            direccion.latitud(), direccion.longitud());
    }

    private static Direccion aDireccion(DireccionEmbeddable embeddable) {
        return new Direccion(embeddable.getCalle(), embeddable.getCiudad(),
            embeddable.getLatitud(), embeddable.getLongitud());
    }
}
