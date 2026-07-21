package com.ultimamilla.nucleo.domain.model;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Base de la jerarquía de usuarios del sistema (herencia). {@link Cliente},
 * {@link Repartidor} y {@link Administrador} extienden esta clase y cada una
 * decide su propio {@link #rol()} (polimorfismo).
 *
 * <p>Los campos son privados e inmutables — el nombre y el email se validan una
 * sola vez, en la construcción (encapsulamiento).
 */
public abstract class Usuario {

    private static final Pattern PATRON_EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private final UsuarioId id;
    private final String nombre;
    private final String email;

    protected Usuario(UsuarioId id, String nombre, String email) {
        this.id = Objects.requireNonNull(id, "El id no puede ser nulo");
        this.nombre = validarNombre(nombre);
        this.email = validarEmail(email);
    }

    private static String validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        return nombre;
    }

    private static String validarEmail(String email) {
        if (email == null || !PATRON_EMAIL.matcher(email).matches()) {
            throw new IllegalArgumentException("Email inválido: " + email);
        }
        return email;
    }

    public UsuarioId id() {
        return id;
    }

    public String nombre() {
        return nombre;
    }

    public String email() {
        return email;
    }

    /**
     * Rol del usuario dentro del sistema. Cada subtipo lo define — este es el punto
     * de polimorfismo de la jerarquía.
     */
    public abstract String rol();
}
