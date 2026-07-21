package com.ultimamilla.nucleo.domain.exception;

/**
 * Excepción base para toda violación de una regla de negocio del dominio.
 *
 * <p>Las excepciones de aplicación/infraestructura (validación de DTOs, errores
 * técnicos) NO deben extender esta clase — es exclusiva para reglas del dominio.
 */
public abstract class DomainException extends RuntimeException {

    protected DomainException(String mensaje) {
        super(mensaje);
    }
}
