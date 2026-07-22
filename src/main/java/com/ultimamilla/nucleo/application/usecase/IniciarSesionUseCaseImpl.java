package com.ultimamilla.nucleo.application.usecase;

import com.ultimamilla.nucleo.application.exception.CredencialesInvalidasException;
import com.ultimamilla.nucleo.application.port.in.IniciarSesionUseCase;
import com.ultimamilla.nucleo.application.port.out.PasswordHasherPort;
import com.ultimamilla.nucleo.application.port.out.TokenProviderPort;
import com.ultimamilla.nucleo.application.port.out.UsuarioRepositoryPort;
import com.ultimamilla.nucleo.application.port.out.UsuarioRepositoryPort.UsuarioConCredencial;
import com.ultimamilla.nucleo.domain.model.Usuario;

import java.util.Objects;

public final class IniciarSesionUseCaseImpl implements IniciarSesionUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordHasherPort passwordHasher;
    private final TokenProviderPort tokenProvider;

    public IniciarSesionUseCaseImpl(UsuarioRepositoryPort usuarioRepository, PasswordHasherPort passwordHasher,
                                     TokenProviderPort tokenProvider) {
        this.usuarioRepository = Objects.requireNonNull(usuarioRepository, "El repositorio no puede ser nulo");
        this.passwordHasher = Objects.requireNonNull(passwordHasher, "El hasher no puede ser nulo");
        this.tokenProvider = Objects.requireNonNull(tokenProvider, "El proveedor de tokens no puede ser nulo");
    }

    @Override
    public String ejecutar(IniciarSesionComando comando) {
        Objects.requireNonNull(comando, "El comando no puede ser nulo");

        UsuarioConCredencial encontrado = usuarioRepository.buscarPorEmailConCredencial(comando.email())
            .orElseThrow(CredencialesInvalidasException::new);

        if (!passwordHasher.coincide(comando.passwordPlano(), encontrado.passwordHash())) {
            throw new CredencialesInvalidasException();
        }

        Usuario usuario = encontrado.usuario();
        return tokenProvider.generarToken(usuario.id(), usuario.rol());
    }
}
