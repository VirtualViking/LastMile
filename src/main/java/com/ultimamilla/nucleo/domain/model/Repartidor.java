package com.ultimamilla.nucleo.domain.model;

/**
 * Repartidor que entrega pedidos. Además de los datos de {@link Usuario}, protege
 * su propio invariante de disponibilidad: un repartidor inactivo no puede pasar
 * directamente a ocupado.
 */
public final class Repartidor extends Usuario {

    private DisponibilidadRepartidor disponibilidad;

    public Repartidor(UsuarioId id, String nombre, String email) {
        super(id, nombre, email);
        this.disponibilidad = DisponibilidadRepartidor.DISPONIBLE;
    }

    @Override
    public String rol() {
        return "REPARTIDOR";
    }

    public DisponibilidadRepartidor disponibilidad() {
        return disponibilidad;
    }

    public void marcarOcupado() {
        if (disponibilidad == DisponibilidadRepartidor.INACTIVO) {
            throw new IllegalStateException(
                "Un repartidor inactivo no puede marcarse ocupado; primero debe activarse");
        }
        this.disponibilidad = DisponibilidadRepartidor.OCUPADO;
    }

    public void marcarDisponible() {
        this.disponibilidad = DisponibilidadRepartidor.DISPONIBLE;
    }

    public void marcarInactivo() {
        this.disponibilidad = DisponibilidadRepartidor.INACTIVO;
    }

    /**
     * Reconstruye un repartidor ya existente desde persistencia, con su disponibilidad
     * real — a diferencia del constructor normal, que siempre arranca en DISPONIBLE.
     * Lo usa exclusivamente el mapper de infraestructura.
     */
    public static Repartidor reconstruir(UsuarioId id, String nombre, String email,
                                          DisponibilidadRepartidor disponibilidad) {
        Repartidor repartidor = new Repartidor(id, nombre, email);
        repartidor.disponibilidad = disponibilidad;
        return repartidor;
    }
}
