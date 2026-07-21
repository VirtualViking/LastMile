package com.ultimamilla.nucleo.infrastructure.out.persistence;

import jakarta.persistence.Embeddable;

/**
 * Representación JPA de {@code Direccion}. Se usa dos veces dentro de
 * {@link PedidoJpaEntity} (origen y destino), cada una con sus propias columnas
 * vía {@code @AttributeOverrides}.
 */
@Embeddable
public class DireccionEmbeddable {

    private String calle;
    private String ciudad;
    private double latitud;
    private double longitud;

    protected DireccionEmbeddable() {
        // requerido por JPA
    }

    public DireccionEmbeddable(String calle, String ciudad, double latitud, double longitud) {
        this.calle = calle;
        this.ciudad = ciudad;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getCalle() {
        return calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
