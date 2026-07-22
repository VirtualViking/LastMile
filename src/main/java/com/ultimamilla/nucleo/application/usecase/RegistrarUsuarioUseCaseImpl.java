package com.ultimamilla.nucleo.application.usecase;

import com.ultimamilla.nucleo.application.exception.EmailYaRegistradoException;
import com.ultimamilla.nucleo.application.port.in.RegistrarUsuarioUseCase;
import com.ultimamilla.nucleo.application.port.out.PasswordHasherPort;
import com.ultimamilla.nucleo.application.port.out.UsuarioRepositoryPort;
import com.ultimamilla.nucleo.domain.model.Administrador;
import com.ultimamilla.nucleo.domain.model.Cliente;
import com.ultimamilla.nucleo.domain.model.Repartidor;
import com.ultimamilla.nucleo.domain.model.Usuario;
import com.ultimamilla.nucleo.domain.model.UsuarioId;

import java.util.Objects;

public final class RegistrarUsuarioUseCaseImpl implements RegistrarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordHasherPort passwordHasher;

    public RegistrarUsuarioUseCaseImpl(UsuarioRepositoryPort usuarioRepository, PasswordHasherPort passwordHasher) {
        this.usuarioRepository = Objects.requireNonNull(usuarioRepository, "El repositorio no puede ser nulo");
        this.passwordHasher = Objects.requireNonNull(passwordHasher, "El hasher no puede ser nulo");
    }

    @Override
    public Usuario ejecutar(RegistrarUsuarioComando comando) {
        Objects.requireNonNull(comando, "El comando no puede ser nulo");

        if (usuarioRepository.existeEmail(comando.email())) {
            throw new EmailYaRegistradoException(comando.email());
        }

        Usuario usuario = crearUsuario(comando);
        String passwordHash = passwordHasher.hash(comando.passwordPlano());

        return usuarioRepository.guardar(usuario, passwordHash);
    }

    private static Usuario crearUsuario(RegistrarUsuarioComando comando) {
        UsuarioId id = UsuarioId.nuevo();
        return switch (comando.rol()) {
            case CLIENTE -> new Cliente(id, comando.nombre(), comando.email());
            case REPARTIDOR -> new Repartidor(id, comando.nombre(), comando.email());
            case ADMINISTRADOR -> new Administrador(id, comando.nombre(), comando.email());
        };
    }
}
