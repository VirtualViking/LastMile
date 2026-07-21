package com.ultimamilla.nucleo.domain.model;

/**
 * Datos de entrada para que una {@link EstrategiaTarifa} calcule el costo de un envío.
 */
public record DatosEnvio(double distanciaKm) {

    public DatosEnvio {
        if (distanciaKm <= 0) {
            throw new IllegalArgumentException("La distancia debe ser mayor a cero: " + distanciaKm);
        }
    }
}
