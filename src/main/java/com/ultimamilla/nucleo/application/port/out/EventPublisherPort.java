package com.ultimamilla.nucleo.application.port.out;

import com.ultimamilla.nucleo.domain.event.EventoDominio;

/**
 * Puerto de salida para publicar eventos de dominio. La implementación real (AWS SQS)
 * vive en {@code infrastructure.out.messaging}, a partir de la Fase 7.
 */
public interface EventPublisherPort {

    void publicar(EventoDominio evento);
}
