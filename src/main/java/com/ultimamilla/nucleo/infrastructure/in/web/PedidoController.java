package com.ultimamilla.nucleo.infrastructure.in.web;

import com.ultimamilla.nucleo.application.port.in.AsignarRepartidorUseCase;
import com.ultimamilla.nucleo.application.port.in.CancelarPedidoUseCase;
import com.ultimamilla.nucleo.application.port.in.ConsultarPedidoUseCase;
import com.ultimamilla.nucleo.application.port.in.CrearPedidoUseCase;
import com.ultimamilla.nucleo.application.port.in.CrearPedidoUseCase.CrearPedidoComando;
import com.ultimamilla.nucleo.application.port.in.IniciarEnvioUseCase;
import com.ultimamilla.nucleo.application.port.in.MarcarEntregadoUseCase;
import com.ultimamilla.nucleo.domain.model.ComercioId;
import com.ultimamilla.nucleo.domain.model.Pedido;
import com.ultimamilla.nucleo.domain.model.PedidoId;
import com.ultimamilla.nucleo.domain.model.UsuarioId;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Adaptador de entrada REST. Traduce HTTP hacia los casos de uso (puertos de entrada)
 * y viceversa — no contiene ninguna regla de negocio propia.
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final CrearPedidoUseCase crearPedidoUseCase;
    private final AsignarRepartidorUseCase asignarRepartidorUseCase;
    private final IniciarEnvioUseCase iniciarEnvioUseCase;
    private final MarcarEntregadoUseCase marcarEntregadoUseCase;
    private final CancelarPedidoUseCase cancelarPedidoUseCase;
    private final ConsultarPedidoUseCase consultarPedidoUseCase;

    public PedidoController(CrearPedidoUseCase crearPedidoUseCase,
                             AsignarRepartidorUseCase asignarRepartidorUseCase,
                             IniciarEnvioUseCase iniciarEnvioUseCase,
                             MarcarEntregadoUseCase marcarEntregadoUseCase,
                             CancelarPedidoUseCase cancelarPedidoUseCase,
                             ConsultarPedidoUseCase consultarPedidoUseCase) {
        this.crearPedidoUseCase = crearPedidoUseCase;
        this.asignarRepartidorUseCase = asignarRepartidorUseCase;
        this.iniciarEnvioUseCase = iniciarEnvioUseCase;
        this.marcarEntregadoUseCase = marcarEntregadoUseCase;
        this.cancelarPedidoUseCase = cancelarPedidoUseCase;
        this.consultarPedidoUseCase = consultarPedidoUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<PedidoResponse> crear(@Valid @RequestBody CrearPedidoRequest request) {
        CrearPedidoComando comando = new CrearPedidoComando(
            new UsuarioId(request.clienteId()),
            new ComercioId(request.comercioId()),
            request.direccionOrigen().aDominio(),
            request.direccionDestino().aDominio(),
            request.tipoTarifa(),
            request.distanciaKm()
        );
        Pedido pedido = crearPedidoUseCase.ejecutar(comando);
        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoResponse.desde(pedido));
    }

    @PostMapping("/{id}/asignar-repartidor")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<PedidoResponse> asignarRepartidor(@PathVariable UUID id,
                                                              @Valid @RequestBody AsignarRepartidorRequest request) {
        Pedido pedido = asignarRepartidorUseCase.ejecutar(new PedidoId(id), new UsuarioId(request.repartidorId()));
        return ResponseEntity.ok(PedidoResponse.desde(pedido));
    }

    @PostMapping("/{id}/iniciar-envio")
    public ResponseEntity<PedidoResponse> iniciarEnvio(@PathVariable UUID id) {
        Pedido pedido = iniciarEnvioUseCase.ejecutar(new PedidoId(id));
        return ResponseEntity.ok(PedidoResponse.desde(pedido));
    }

    @PostMapping("/{id}/marcar-entregado")
    public ResponseEntity<PedidoResponse> marcarEntregado(@PathVariable UUID id) {
        Pedido pedido = marcarEntregadoUseCase.ejecutar(new PedidoId(id));
        return ResponseEntity.ok(PedidoResponse.desde(pedido));
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<PedidoResponse> cancelar(@PathVariable UUID id) {
        Pedido pedido = cancelarPedidoUseCase.ejecutar(new PedidoId(id));
        return ResponseEntity.ok(PedidoResponse.desde(pedido));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> consultar(@PathVariable UUID id) {
        Pedido pedido = consultarPedidoUseCase.ejecutar(new PedidoId(id));
        return ResponseEntity.ok(PedidoResponse.desde(pedido));
    }
}
