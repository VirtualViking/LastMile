/**
 * Infraestructura de seguridad: JWT (emisión y validación) y hasheo de contraseñas.
 *
 * <p>Vive separado de {@code infrastructure.config} y de {@code infrastructure.in.web}
 * porque cruza ambos — {@link JwtTokenProviderAdapter} y
 * {@link BCryptPasswordHasherAdapter} son adaptadores de salida (implementan puertos
 * de {@code application.port.out}), mientras que {@link JwtAuthenticationFilter} es
 * un adaptador de entrada (intercepta peticiones HTTP). Agruparlos aquí, por tecnología
 * (JWT), fue más claro que forzarlos a vivir en paquetes de dirección separados.
 */
package com.ultimamilla.nucleo.infrastructure.security;
