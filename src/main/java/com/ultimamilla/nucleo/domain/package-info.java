/**
 * Capa de dominio: el corazón del servicio.
 *
 * <p>Contiene las entidades, agregados, value objects y reglas de negocio puras
 * del Servicio Núcleo (Pedido, Cliente, Comercio, Repartidor, Tarifa...).
 *
 * <p>Regla innegociable: este paquete NO importa nada de {@code application} ni de
 * {@code infrastructure}, ni depende de Spring, JPA ni ningún framework externo.
 * Si una clase de aquí necesita un {@code @Autowired} o un import de
 * {@code jakarta.persistence}, algo está mal ubicado.
 */
package com.ultimamilla.nucleo.domain;
