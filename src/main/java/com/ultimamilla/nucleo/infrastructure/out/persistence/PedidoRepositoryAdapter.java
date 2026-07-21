package com.ultimamilla.nucleo.infrastructure.out.persistence;

import com.ultimamilla.nucleo.application.port.out.PedidoRepositoryPort;
import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.PedidoId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementación de {@link PedidoRepositoryPort} sobre PostgreSQL vía Spring Data JPA.
 * Es la única clase de esta capa que Spring conoce como bean — el puerto en sí
 * (en {@code application.port.out}) no sabe que esto existe.
 */
@Repository
public class PedidoRepositoryAdapter implements PedidoRepositoryPort {

    private final SpringDataPedidoRepository springDataRepository;

    public PedidoRepositoryAdapter(SpringDataPedidoRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Pedido guardar(Pedido pedido) {
        PedidoJpaEntity entidad = PedidoMapper.aEntidad(pedido);
        PedidoJpaEntity guardada = springDataRepository.save(entidad);
        return PedidoMapper.aDominio(guardada);
    }

    @Override
    public Optional<Pedido> buscarPorId(PedidoId id) {
        return springDataRepository.findById(id.valor()).map(PedidoMapper::aDominio);
    }
}
