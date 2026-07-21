/**
 * Eventos de dominio: hechos que ya ocurrieron y que otras partes del sistema
 * (u otros servicios, como el de Tracking) necesitan conocer.
 *
 * <p>Igual que el resto de {@code domain} — Java puro, sin Spring. La publicación
 * real (a AWS SQS) vive en {@code infrastructure.out.messaging}, a partir de la Fase 7.
 */
package com.ultimamilla.nucleo.domain.event;
