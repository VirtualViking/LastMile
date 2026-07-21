package com.ultimamilla.nucleo.domain.model;

/**
 * Value object de una dirección física, con coordenadas para el cálculo de tarifa
 * y para que el servicio de Tracking sepa hacia dónde va el envío.
 */
public record Direccion(String calle, String ciudad, double latitud, double longitud) {

    public Direccion {
        if (calle == null || calle.isBlank()) {
            throw new IllegalArgumentException("La calle no puede estar vacía");
        }
        if (ciudad == null || ciudad.isBlank()) {
            throw new IllegalArgumentException("La ciudad no puede estar vacía");
        }
        if (latitud < -90 || latitud > 90) {
            throw new IllegalArgumentException("Latitud fuera de rango: " + latitud);
        }
        if (longitud < -180 || longitud > 180) {
            throw new IllegalArgumentException("Longitud fuera de rango: " + longitud);
        }
    }
}
