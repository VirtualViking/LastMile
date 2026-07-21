package com.ultimamilla.nucleo.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Tarifa estándar: costo base más un costo por kilómetro. Valores en COP —
 * ajústalos si tu caso de negocio usa otra moneda o tabla de precios.
 */
public final class TarifaEstandar implements EstrategiaTarifa {

    private static final BigDecimal TARIFA_BASE = new BigDecimal("5000");
    private static final BigDecimal COSTO_POR_KM = new BigDecimal("800");

    @Override
    public Dinero calcular(DatosEnvio datosEnvio) {
        Objects.requireNonNull(datosEnvio, "Los datos de envío no pueden ser nulos");
        BigDecimal costoDistancia = COSTO_POR_KM.multiply(BigDecimal.valueOf(datosEnvio.distanciaKm()));
        return Dinero.cop(TARIFA_BASE.add(costoDistancia));
    }
}
