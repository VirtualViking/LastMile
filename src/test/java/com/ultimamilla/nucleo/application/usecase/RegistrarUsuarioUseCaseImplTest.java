package com.ultimamilla.nucleo.application.usecase;

import com.ultimamilla.nucleo.application.exception.EmailYaRegistradoException;
import com.ultimamilla.nucleo.application.port.in.RegistrarUsuarioUseCase.RegistrarUsuarioComando;
import com.ultimamilla.nucleo.application.port.in.RegistrarUsuarioUseCase.Rol;
import com.ultimamilla.nucleo.application.port.out.PasswordHasherPort;
import com.ultimamilla.nucleo.application.port.out.UsuarioRepositoryPort;
import com.ultimamilla.nucleo.domain.model.Repartidor;
import com.ultimamilla.nucleo.domain.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrarUsuarioUseCaseImplTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @Mock
    private PasswordHasherPort passwordHasher;

    @Test
    void registraUnRepartidorYGuardaElHashNoElPasswordPlano() {
        when(usuarioRepository.existeEmail("camilo@lastmile.co")).thenReturn(false);
        when(passwordHasher.hash("password123")).thenReturn("hash-simulado");
        when(usuarioRepository.guardar(any(Usuario.class), eq("hash-simulado")))
            .thenAnswer(inv -> inv.getArgument(0));

        RegistrarUsuarioUseCaseImpl useCase = new RegistrarUsuarioUseCaseImpl(usuarioRepository, passwordHasher);
        RegistrarUsuarioComando comando = new RegistrarUsuarioComando(
            "Camilo Ríos", "camilo@lastmile.co", "password123", Rol.REPARTIDOR);

        Usuario resultado = useCase.ejecutar(comando);

        assertThat(resultado).isInstanceOf(Repartidor.class);
        assertThat(resultado.rol()).isEqualTo("REPARTIDOR");

        ArgumentCaptor<String> hashCaptor = ArgumentCaptor.forClass(String.class);
        verify(usuarioRepository).guardar(any(Usuario.class), hashCaptor.capture());
        assertThat(hashCaptor.getValue()).isEqualTo("hash-simulado");
    }

    @Test
    void rechazaRegistrarUnEmailYaExistente() {
        when(usuarioRepository.existeEmail("camilo@lastmile.co")).thenReturn(true);
        RegistrarUsuarioUseCaseImpl useCase = new RegistrarUsuarioUseCaseImpl(usuarioRepository, passwordHasher);

        RegistrarUsuarioComando comando = new RegistrarUsuarioComando(
            "Camilo Ríos", "camilo@lastmile.co", "password123", Rol.CLIENTE);

        assertThatThrownBy(() -> useCase.ejecutar(comando))
            .isInstanceOf(EmailYaRegistradoException.class);

        verify(usuarioRepository, never()).guardar(any(), anyString());
    }
}
