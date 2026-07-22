package com.ultimamilla.nucleo.infrastructure.security;

import com.ultimamilla.nucleo.application.port.out.TokenProviderPort;
import com.ultimamilla.nucleo.domain.model.UsuarioId;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtTokenProviderAdapter implements TokenProviderPort {

    private final SecretKey key;
    private final long expiracionMinutos;

    public JwtTokenProviderAdapter(@Value("${app.jwt.secret}") String secreto,
                                    @Value("${app.jwt.expiracion-minutos}") long expiracionMinutos) {
        this.key = Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));
        this.expiracionMinutos = expiracionMinutos;
    }

    @Override
    public String generarToken(UsuarioId usuarioId, String rol) {
        Instant ahora = Instant.now();
        Instant expiracion = ahora.plus(Duration.ofMinutes(expiracionMinutos));

        return Jwts.builder()
            .subject(usuarioId.valor().toString())
            .claim("rol", rol)
            .issuedAt(Date.from(ahora))
            .expiration(Date.from(expiracion))
            .signWith(key)
            .compact();
    }

    @Override
    public Optional<TokenPayload> validarToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

            UsuarioId usuarioId = new UsuarioId(UUID.fromString(claims.getSubject()));
            String rol = claims.get("rol", String.class);
            return Optional.of(new TokenPayload(usuarioId, rol));
        } catch (JwtException | IllegalArgumentException ex) {
            return Optional.empty();
        }
    }
}
