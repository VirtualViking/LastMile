package com.ultimamilla.nucleo.domain.model;

/**
 * Estrategia de cálculo de tarifa (patrón Strategy). {@link Pedido} recibe una
 * implementación al crearse y no sabe — ni le importa — cuál es; solo invoca
 * {@link #calcular(DatosEnvio)}. Este es el punto de polimorfismo del cálculo
 * de tarifas.
 */
public interface EstrategiaTarifa {

    Dinero calcular(DatosEnvio datosEnvio);
}
