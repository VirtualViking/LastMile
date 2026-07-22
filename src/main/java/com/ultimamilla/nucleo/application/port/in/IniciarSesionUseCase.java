package com.ultimamilla.nucleo.application.port.in;

/**
 * Caso de uso: iniciar sesión. Devuelve el token JWT ya emitido — el llamador
 * no necesita saber nada más que "esto es lo que hay que mandar en el header".
 */
@FunctionalInterface
public interface IniciarSesionUseCase {

    String ejecutar(IniciarSesionComando comando);

    record IniciarSesionComando(String email, String passwordPlano) {
    }
}
