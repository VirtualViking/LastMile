package com.ultimamilla.nucleo.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class TarifaEstandarTest {

    @Test
    void calculaBaseMasCostoPorDistancia() {
        Dinero resultado = new TarifaEstandar().calcular(new DatosEnvio(5.0));

        // base 5000 + (800 * 5) = 9000
        assertThat(resultado.monto()).isEqualByComparingTo(new BigDecimal("9000"));
        assertThat(resultado.moneda()).isEqualTo("COP");
    }
}
