package com.ultimamilla.nucleo.application.exception;

/**
 * Se lanza al intentar registrar un usuario con un email que ya existe.
 * Se mapea a HTTP 409 en {@code infrastructure.in.web}.
 */
public class EmailYaRegistradoException extends RuntimeException {

    public EmailYaRegistradoException(String email) {
        super("Ya existe una cuenta registrada con el email " + email);
    }
}
