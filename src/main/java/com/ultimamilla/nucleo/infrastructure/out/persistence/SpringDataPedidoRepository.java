package com.ultimamilla.nucleo.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repositorio técnico de Spring Data — no confundir con {@code PedidoRepositoryPort}
 * (el puerto de la aplicación). {@link PedidoRepositoryAdapter} usa este para
 * implementar el puerto.
 */
public interface SpringDataPedidoRepository extends JpaRepository<PedidoJpaEntity, UUID> {
}
