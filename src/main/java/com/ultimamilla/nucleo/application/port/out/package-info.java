/**
 * Puertos de salida: contratos que la capa de aplicación necesita del exterior.
 *
 * <p>Interfaces como {@code PedidoRepositoryPort} o {@code EventPublisherPort} — el
 * dominio y los casos de uso piden "guárdame esto" o "publica este evento" sin saber
 * si detrás hay JPA + PostgreSQL, SQS, o cualquier otra tecnología. Esa decisión
 * vive en {@code infrastructure.out}.
 */
package com.ultimamilla.nucleo.application.port.out;
