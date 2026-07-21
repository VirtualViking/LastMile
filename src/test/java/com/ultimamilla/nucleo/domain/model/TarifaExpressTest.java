package com.ultimamilla.nucleo.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class TarifaExpressTest {

    @Test
    void calculaBaseMasDistanciaMasRecargoDePrioridad() {
        Dinero resultado = new TarifaExpress().calcular(new DatosEnvio(5.0));

        // subtotal = 9000 + (1300 * 5) = 15500; recargo 20% = 3100; total = 18600
        assertThat(resultado.monto()).isEqualByComparingTo(new BigDecimal("18600"));
        assertThat(resultado.moneda()).isEqualTo("COP");
    }

    @Test
    void esMasCaraQueLaTarifaEstandarParaLaMismaDistancia() {
        DatosEnvio datos = new DatosEnvio(5.0);

        Dinero estandar = new TarifaEstandar().calcular(datos);
        Dinero express = new TarifaExpress().calcular(datos);

        assertThat(express.monto()).isGreaterThan(estandar.monto());
    }
}
