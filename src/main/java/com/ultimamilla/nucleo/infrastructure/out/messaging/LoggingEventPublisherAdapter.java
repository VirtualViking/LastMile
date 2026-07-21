package com.ultimamilla.nucleo.infrastructure.out.messaging;

import com.ultimamilla.nucleo.application.port.out.EventPublisherPort;
import com.ultimamilla.nucleo.domain.event.EventoDominio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Implementación TEMPORAL de {@link EventPublisherPort} — solo registra el evento
 * en el log. Se reemplaza por un publicador real a AWS SQS en la Fase 7. Existe para
 * que el servicio arranque y funcione de punta a punta mientras tanto.
 */
@Component
public class LoggingEventPublisherAdapter implements EventPublisherPort {

    private static final Logger log = LoggerFactory.getLogger(LoggingEventPublisherAdapter.class);

    @Override
    public void publicar(EventoDominio evento) {
        log.info("Evento de dominio publicado (stub, sin SQS todavía): {}", evento);
    }
}
