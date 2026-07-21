package com.ultimamilla.nucleo.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RepartidorTest {

    @Test
    void empiezaDisponibleYExponeSuRolPorPolimorfismo() {
        Repartidor repartidor = new Repartidor(UsuarioId.nuevo(), "Camilo Ríos", "camilo@lastmile.co");

        assertThat(repartidor.disponibilidad()).isEqualTo(DisponibilidadRepartidor.DISPONIBLE);
        assertThat(repartidor.rol()).isEqualTo("REPARTIDOR");
    }

    @Test
    void unRepartidorInactivoNoPuedeMarcarseOcupadoDirectamente() {
        Repartidor repartidor = new Repartidor(UsuarioId.nuevo(), "Camilo Ríos", "camilo@lastmile.co");
        repartidor.marcarInactivo();

        assertThatThrownBy(repartidor::marcarOcupado)
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void rechazaEmailInvalidoAlConstruirse() {
        assertThatThrownBy(() -> new Repartidor(UsuarioId.nuevo(), "Camilo Ríos", "no-es-un-email"))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
