package com.ultimamilla.nucleo.domain.model;

import java.util.Objects;

/**
 * Comercio (pyme) que publica pedidos para que la plataforma los entregue.
 * No es un {@link Usuario} — es administrado por uno (su {@code propietarioId}),
 * pero es una entidad de negocio propia.
 */
public final class Comercio {

    private final ComercioId id;
    private final String nombreComercial;
    private final Direccion direccion;
    private final UsuarioId propietarioId;

    public Comercio(ComercioId id, String nombreComercial, Direccion direccion, UsuarioId propietarioId) {
        this.id = Objects.requireNonNull(id, "El id no puede ser nulo");
        this.direccion = Objects.requireNonNull(direccion, "La dirección no puede ser nula");
        this.propietarioId = Objects.requireNonNull(propietarioId, "El propietario no puede ser nulo");
        if (nombreComercial == null || nombreComercial.isBlank()) {
            throw new IllegalArgumentException("El nombre comercial no puede estar vacío");
        }
        this.nombreComercial = nombreComercial;
    }

    public ComercioId id() {
        return id;
    }

    public String nombreComercial() {
        return nombreComercial;
    }

    public Direccion direccion() {
        return direccion;
    }

    public UsuarioId propietarioId() {
        return propietarioId;
    }
}
