package com.ultimamilla.nucleo.infrastructure.in.web;

import com.ultimamilla.nucleo.application.port.in.IniciarSesionUseCase;
import com.ultimamilla.nucleo.application.port.in.IniciarSesionUseCase.IniciarSesionComando;
import com.ultimamilla.nucleo.application.port.in.RegistrarUsuarioUseCase;
import com.ultimamilla.nucleo.application.port.in.RegistrarUsuarioUseCase.RegistrarUsuarioComando;
import com.ultimamilla.nucleo.domain.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final IniciarSesionUseCase iniciarSesionUseCase;

    public AuthController(RegistrarUsuarioUseCase registrarUsuarioUseCase,
                           IniciarSesionUseCase iniciarSesionUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
        this.iniciarSesionUseCase = iniciarSesionUseCase;
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody RegistroRequest request) {
        Usuario usuario = registrarUsuarioUseCase.ejecutar(
            new RegistrarUsuarioComando(request.nombre(), request.email(), request.password(), request.rol()));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponse.desde(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = iniciarSesionUseCase.ejecutar(
            new IniciarSesionComando(request.email(), request.password()));
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
