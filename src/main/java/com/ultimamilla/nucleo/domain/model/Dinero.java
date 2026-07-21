package com.ultimamilla.nucleo.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value object monetario. Encapsula el monto y la moneda, y protege el invariante
 * de que un monto nunca puede ser negativo.
 */
public record Dinero(BigDecimal monto, String moneda) {

    public Dinero {
        Objects.requireNonNull(monto, "El monto no puede ser nulo");
        Objects.requireNonNull(moneda, "La moneda no puede ser nula");
        if (monto.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo: " + monto);
        }
    }

    public static Dinero cop(BigDecimal monto) {
        return new Dinero(monto, "COP");
    }

    public Dinero sumar(Dinero otro) {
        Objects.requireNonNull(otro, "El dinero a sumar no puede ser nulo");
        if (!this.moneda.equals(otro.moneda)) {
            throw new IllegalArgumentException(
                "No se pueden sumar montos en monedas distintas: " + this.moneda + " y " + otro.moneda);
        }
        return new Dinero(this.monto.add(otro.monto), this.moneda);
    }
}
