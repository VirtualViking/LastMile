package com.ultimamilla.nucleo.application.exception;

/**
 * Se lanza cuando el login falla — sea porque el email no existe o porque la
 * contraseña no coincide. Deliberadamente NO distingue cuál de las dos fue,
 * para no dar pistas a quien intente adivinar cuentas existentes.
 * Se mapea a HTTP 401 en {@code infrastructure.in.web}.
 */
public class CredencialesInvalidasException extends RuntimeException {

    public CredencialesInvalidasException() {
        super("Email o contraseña incorrectos");
    }
}
