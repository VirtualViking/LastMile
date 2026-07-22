package com.ultimamilla.nucleo.infrastructure.in.web;

import com.ultimamilla.nucleo.application.port.in.RegistrarUsuarioUseCase.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistroRequest(
    @NotBlank String nombre,
    @NotBlank @Email String email,
    @NotBlank @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres") String password,
    @NotNull Rol rol
) {
}
