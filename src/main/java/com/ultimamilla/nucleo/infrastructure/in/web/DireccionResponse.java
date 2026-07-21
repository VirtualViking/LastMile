package com.ultimamilla.nucleo.infrastructure.in.web;

import com.ultimamilla.nucleo.domain.model.Direccion;

public record DireccionResponse(String calle, String ciudad, double latitud, double longitud) {

    public static DireccionResponse desde(Direccion direccion) {
        return new DireccionResponse(direccion.calle(), direccion.ciudad(),
            direccion.latitud(), direccion.longitud());
    }
}
