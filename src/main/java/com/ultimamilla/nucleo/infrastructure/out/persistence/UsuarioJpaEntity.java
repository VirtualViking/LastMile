package com.ultimamilla.nucleo.infrastructure.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

/**
 * Representación JPA de {@code Usuario} — una sola tabla para Cliente, Repartidor
 * y Administrador, discriminada por la columna {@code rol}. Se prefirió esto sobre
 * una tabla por subtipo porque las diferencias entre ellos son mínimas (solo
 * {@code disponibilidad}, exclusiva de Repartidor).
 */
@Entity
@Table(name = "usuarios")
public class UsuarioJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false, length = 20)
    private String rol;

    /** Solo aplica si rol = REPARTIDOR; nula para los demás. */
    @Column(length = 20)
    private String disponibilidad;

    protected UsuarioJpaEntity() {
        // requerido por JPA
    }

    public UsuarioJpaEntity(UUID id, String nombre, String email, String passwordHash, String rol,
                             String disponibilidad) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.disponibilidad = disponibilidad;
    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRol() {
        return rol;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }
}
