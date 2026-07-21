package com.ultimamilla.nucleo.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DineroTest {

    @Test
    void rechazaMontosNegativos() {
        assertThatThrownBy(() -> new Dinero(new BigDecimal("-100"), "COP"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void sumaDosMontosEnLaMismaMoneda() {
        Dinero total = Dinero.cop(new BigDecimal("1000")).sumar(Dinero.cop(new BigDecimal("500")));

        assertThat(total.monto()).isEqualByComparingTo(new BigDecimal("1500"));
    }

    @Test
    void rechazaSumarMontosEnMonedasDistintas() {
        Dinero enPesos = Dinero.cop(new BigDecimal("1000"));
        Dinero enDolares = new Dinero(new BigDecimal("10"), "USD");

        assertThatThrownBy(() -> enPesos.sumar(enDolares))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
