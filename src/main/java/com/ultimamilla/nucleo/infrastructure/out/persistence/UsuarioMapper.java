package com.ultimamilla.nucleo.infrastructure.out.persistence;

import com.ultimamilla.nucleo.domain.model.Administrador;
import com.ultimamilla.nucleo.domain.model.Cliente;
import com.ultimamilla.nucleo.domain.model.DisponibilidadRepartidor;
import com.ultimamilla.nucleo.domain.model.Repartidor;
import com.ultimamilla.nucleo.domain.model.Usuario;
import com.ultimamilla.nucleo.domain.model.UsuarioId;

/**
 * Traduce entre {@code Usuario} (y sus subtipos) y su representación JPA de tabla única.
 */
public final class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static UsuarioJpaEntity aEntidad(Usuario usuario, String passwordHash) {
        String disponibilidad = usuario instanceof Repartidor repartidor
            ? repartidor.disponibilidad().name()
            : null;

        return new UsuarioJpaEntity(
            usuario.id().valor(),
            usuario.nombre(),
            usuario.email(),
            passwordHash,
            usuario.rol(),
            disponibilidad
        );
    }

    public static Usuario aDominio(UsuarioJpaEntity entidad) {
        UsuarioId id = new UsuarioId(entidad.getId());

        return switch (entidad.getRol()) {
            case "CLIENTE" -> new Cliente(id, entidad.getNombre(), entidad.getEmail());
            case "REPARTIDOR" -> Repartidor.reconstruir(id, entidad.getNombre(), entidad.getEmail(),
                DisponibilidadRepartidor.valueOf(entidad.getDisponibilidad()));
            case "ADMINISTRADOR" -> new Administrador(id, entidad.getNombre(), entidad.getEmail());
            default -> throw new IllegalStateException("Rol desconocido en la base de datos: " + entidad.getRol());
        };
    }
}
