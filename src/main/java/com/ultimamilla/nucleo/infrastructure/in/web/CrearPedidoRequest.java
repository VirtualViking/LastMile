package com.ultimamilla.nucleo.infrastructure.in.web;

import com.ultimamilla.nucleo.application.port.in.CrearPedidoUseCase.TipoTarifa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CrearPedidoRequest(
    @NotNull UUID clienteId,
    @NotNull UUID comercioId,
    @Valid @NotNull DireccionRequest direccionOrigen,
    @Valid @NotNull DireccionRequest direccionDestino,
    @NotNull TipoTarifa tipoTarifa,
    @Positive double distanciaKm
) {
}
