package com.ultimamilla.nucleo.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Tarifa express: base y costo por kilómetro más altos que la estándar, más un
 * recargo porcentual por prioridad. Misma interfaz que {@link TarifaEstandar} —
 * intercambiables sin que {@code Pedido} se entere.
 */
public final class TarifaExpress implements EstrategiaTarifa {

    private static final BigDecimal TARIFA_BASE = new BigDecimal("9000");
    private static final BigDecimal COSTO_POR_KM = new BigDecimal("1300");
    private static final BigDecimal RECARGO_PRIORIDAD = new BigDecimal("0.20"); // 20%

    @Override
    public Dinero calcular(DatosEnvio datosEnvio) {
        Objects.requireNonNull(datosEnvio, "Los datos de envío no pueden ser nulos");
        BigDecimal costoDistancia = COSTO_POR_KM.multiply(BigDecimal.valueOf(datosEnvio.distanciaKm()));
        BigDecimal subtotal = TARIFA_BASE.add(costoDistancia);
        BigDecimal recargo = subtotal.multiply(RECARGO_PRIORIDAD);
        BigDecimal total = subtotal.add(recargo).setScale(0, RoundingMode.HALF_UP);
        return Dinero.cop(total);
    }
}
