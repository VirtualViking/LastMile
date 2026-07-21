/**
 * Adaptador de salida de mensajería: implementa {@code EventPublisherPort} publicando
 * eventos de dominio (ej. {@code PedidoAsignado}) a AWS SQS, para que los consuma el
 * servicio de Tracking en tiempo real.
 */
package com.ultimamilla.nucleo.infrastructure.out.messaging;
