package com.ultimamilla.nucleo.application.usecase;

import com.ultimamilla.nucleo.application.exception.CredencialesInvalidasException;
import com.ultimamilla.nucleo.application.port.in.IniciarSesionUseCase.IniciarSesionComando;
import com.ultimamilla.nucleo.application.port.out.PasswordHasherPort;
import com.ultimamilla.nucleo.application.port.out.TokenProviderPort;
import com.ultimamilla.nucleo.application.port.out.UsuarioRepositoryPort;
import com.ultimamilla.nucleo.application.port.out.UsuarioRepositoryPort.UsuarioConCredencial;
import com.ultimamilla.nucleo.domain.model.Cliente;
import com.ultimamilla.nucleo.domain.model.UsuarioId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IniciarSesionUseCaseImplTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @Mock
    private PasswordHasherPort passwordHasher;

    @Mock
    private TokenProviderPort tokenProvider;

    @Test
    void conCredencialesCorrectasDevuelveElToken() {
        Cliente cliente = new Cliente(UsuarioId.nuevo(), "Ana Gómez", "ana@lastmile.co");
        UsuarioConCredencial encontrado = new UsuarioConCredencial(cliente, "hash-almacenado");

        when(usuarioRepository.buscarPorEmailConCredencial("ana@lastmile.co"))
            .thenReturn(Optional.of(encontrado));
        when(passwordHasher.coincide("password123", "hash-almacenado")).thenReturn(true);
        when(tokenProvider.generarToken(cliente.id(), "CLIENTE")).thenReturn("token-simulado");

        IniciarSesionUseCaseImpl useCase = new IniciarSesionUseCaseImpl(usuarioRepository, passwordHasher,
            tokenProvider);

        String token = useCase.ejecutar(new IniciarSesionComando("ana@lastmile.co", "password123"));

        assertThat(token).isEqualTo("token-simulado");
    }

    @Test
    void conPasswordIncorrectoLanzaCredencialesInvalidasSinRevelarCual() {
        Cliente cliente = new Cliente(UsuarioId.nuevo(), "Ana Gómez", "ana@lastmile.co");
        UsuarioConCredencial encontrado = new UsuarioConCredencial(cliente, "hash-almacenado");

        when(usuarioRepository.buscarPorEmailConCredencial("ana@lastmile.co"))
            .thenReturn(Optional.of(encontrado));
        when(passwordHasher.coincide("password-incorrecto", "hash-almacenado")).thenReturn(false);

        IniciarSesionUseCaseImpl useCase = new IniciarSesionUseCaseImpl(usuarioRepository, passwordHasher,
            tokenProvider);

        assertThatThrownBy(() -> useCase.ejecutar(new IniciarSesionComando("ana@lastmile.co", "password-incorrecto")))
            .isInstanceOf(CredencialesInvalidasException.class);
    }

    @Test
    void conEmailInexistenteLanzaLaMismaExcepcionQueUnPasswordIncorrecto() {
        when(usuarioRepository.buscarPorEmailConCredencial("no-existe@lastmile.co"))
            .thenReturn(Optional.empty());

        IniciarSesionUseCaseImpl useCase = new IniciarSesionUseCaseImpl(usuarioRepository, passwordHasher,
            tokenProvider);

        assertThatThrownBy(() -> useCase.ejecutar(new IniciarSesionComando("no-existe@lastmile.co", "cualquiera")))
            .isInstanceOf(CredencialesInvalidasException.class);
    }
}
