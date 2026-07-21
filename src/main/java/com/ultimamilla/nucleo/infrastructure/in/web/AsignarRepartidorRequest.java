package com.ultimamilla.nucleo.infrastructure.in.web;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AsignarRepartidorRequest(@NotNull UUID repartidorId) {
}
