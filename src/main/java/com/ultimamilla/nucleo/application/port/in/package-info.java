/**
 * Puertos de entrada: contratos que la capa de aplicación expone hacia el exterior.
 *
 * <p>Interfaces que declaran QUÉ casos de uso ofrece el servicio
 * (ej. {@code CrearPedidoUseCase}, {@code AsignarRepartidorUseCase}), sin decir
 * CÓMO se invocan — eso lo decide {@code infrastructure.in}, ya sea vía REST,
 * mensajería, o cualquier otro adaptador de entrada.
 */
package com.ultimamilla.nucleo.application.port.in;
