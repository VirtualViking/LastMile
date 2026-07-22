package com.ultimamilla.nucleo.infrastructure.security;

import com.ultimamilla.nucleo.application.port.out.TokenProviderPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Lee el header {@code Authorization: Bearer <token>}, lo valida vía
 * {@link TokenProviderPort}, y si es válido llena el {@code SecurityContext} con
 * el id de usuario como principal y el rol como authority ({@code ROLE_<rol>},
 * la convención que Spring Security espera para {@code hasRole(...)}).
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProviderPort tokenProvider;

    public JwtAuthenticationFilter(TokenProviderPort tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                     @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            tokenProvider.validarToken(token).ifPresent(payload -> {
                var authority = new SimpleGrantedAuthority("ROLE_" + payload.rol());
                var authentication = new UsernamePasswordAuthenticationToken(
                    payload.usuarioId(), null, List.of(authority));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            });
        }

        filterChain.doFilter(request, response);
    }
}
