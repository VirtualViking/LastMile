package com.ultimamilla.nucleo.infrastructure.config;

import com.ultimamilla.nucleo.application.port.in.AsignarRepartidorUseCase;
import com.ultimamilla.nucleo.application.port.in.CancelarPedidoUseCase;
import com.ultimamilla.nucleo.application.port.in.ConsultarPedidoUseCase;
import com.ultimamilla.nucleo.application.port.in.CrearPedidoUseCase;
import com.ultimamilla.nucleo.application.port.in.IniciarEnvioUseCase;
import com.ultimamilla.nucleo.application.port.in.IniciarSesionUseCase;
import com.ultimamilla.nucleo.application.port.in.MarcarEntregadoUseCase;
import com.ultimamilla.nucleo.application.port.in.RegistrarUsuarioUseCase;
import com.ultimamilla.nucleo.application.port.out.EventPublisherPort;
import com.ultimamilla.nucleo.application.port.out.PasswordHasherPort;
import com.ultimamilla.nucleo.application.port.out.PedidoRepositoryPort;
import com.ultimamilla.nucleo.application.port.out.TokenProviderPort;
import com.ultimamilla.nucleo.application.port.out.UsuarioRepositoryPort;
import com.ultimamilla.nucleo.application.usecase.AsignarRepartidorUseCaseImpl;
import com.ultimamilla.nucleo.application.usecase.CancelarPedidoUseCaseImpl;
import com.ultimamilla.nucleo.application.usecase.ConsultarPedidoUseCaseImpl;
import com.ultimamilla.nucleo.application.usecase.CrearPedidoUseCaseImpl;
import com.ultimamilla.nucleo.application.usecase.IniciarEnvioUseCaseImpl;
import com.ultimamilla.nucleo.application.usecase.IniciarSesionUseCaseImpl;
import com.ultimamilla.nucleo.application.usecase.MarcarEntregadoUseCaseImpl;
import com.ultimamilla.nucleo.application.usecase.RegistrarUsuarioUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cablea los casos de uso como beans de Spring. Las clases {@code *UseCaseImpl} en
 * {@code application.usecase} siguen sin ninguna anotación de Spring — este es el
 * único punto del sistema donde Spring "se entera" de que existen.
 */
@Configuration
public class UseCaseConfig {

    @Bean
    public CrearPedidoUseCase crearPedidoUseCase(PedidoRepositoryPort pedidoRepository) {
        return new CrearPedidoUseCaseImpl(pedidoRepository);
    }

    @Bean
    public AsignarRepartidorUseCase asignarRepartidorUseCase(PedidoRepositoryPort pedidoRepository,
                                                               EventPublisherPort eventPublisher) {
        return new AsignarRepartidorUseCaseImpl(pedidoRepository, eventPublisher);
    }

    @Bean
    public IniciarEnvioUseCase iniciarEnvioUseCase(PedidoRepositoryPort pedidoRepository) {
        return new IniciarEnvioUseCaseImpl(pedidoRepository);
    }

    @Bean
    public MarcarEntregadoUseCase marcarEntregadoUseCase(PedidoRepositoryPort pedidoRepository) {
        return new MarcarEntregadoUseCaseImpl(pedidoRepository);
    }

    @Bean
    public CancelarPedidoUseCase cancelarPedidoUseCase(PedidoRepositoryPort pedidoRepository) {
        return new CancelarPedidoUseCaseImpl(pedidoRepository);
    }

    @Bean
    public ConsultarPedidoUseCase consultarPedidoUseCase(PedidoRepositoryPort pedidoRepository) {
        return new ConsultarPedidoUseCaseImpl(pedidoRepository);
    }

    @Bean
    public RegistrarUsuarioUseCase registrarUsuarioUseCase(UsuarioRepositoryPort usuarioRepository,
                                                            PasswordHasherPort passwordHasher) {
        return new RegistrarUsuarioUseCaseImpl(usuarioRepository, passwordHasher);
    }

    @Bean
    public IniciarSesionUseCase iniciarSesionUseCase(UsuarioRepositoryPort usuarioRepository,
                                                      PasswordHasherPort passwordHasher,
                                                      TokenProviderPort tokenProvider) {
        return new IniciarSesionUseCaseImpl(usuarioRepository, passwordHasher, tokenProvider);
    }
}
