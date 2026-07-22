package com.ultimamilla.nucleo.infrastructure.out.persistence;

import com.ultimamilla.nucleo.application.port.out.UsuarioRepositoryPort;
import com.ultimamilla.nucleo.domain.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final SpringDataUsuarioRepository springDataRepository;

    public UsuarioRepositoryAdapter(SpringDataUsuarioRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Usuario guardar(Usuario usuario, String passwordHash) {
        UsuarioJpaEntity entidad = UsuarioMapper.aEntidad(usuario, passwordHash);
        UsuarioJpaEntity guardado = springDataRepository.save(entidad);
        return UsuarioMapper.aDominio(guardado);
    }

    @Override
    public Optional<UsuarioConCredencial> buscarPorEmailConCredencial(String email) {
        return springDataRepository.findByEmail(email)
            .map(entidad -> new UsuarioConCredencial(UsuarioMapper.aDominio(entidad), entidad.getPasswordHash()));
    }

    @Override
    public boolean existeEmail(String email) {
        return springDataRepository.existsByEmail(email);
    }
}
