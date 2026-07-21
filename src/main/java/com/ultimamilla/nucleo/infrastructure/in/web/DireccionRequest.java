package com.ultimamilla.nucleo.infrastructure.in.web;

import com.ultimamilla.nucleo.domain.model.Direccion;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public record DireccionRequest(
    @NotBlank String calle,
    @NotBlank String ciudad,
    @DecimalMin("-90.0") @DecimalMax("90.0") double latitud,
    @DecimalMin("-180.0") @DecimalMax("180.0") double longitud
) {

    public Direccion aDominio() {
        return new Direccion(calle, ciudad, latitud, longitud);
    }
}
