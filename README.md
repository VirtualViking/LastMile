# nucleo-service

Servicio Núcleo (Order & Fleet Management) — Plataforma de Logística de Última Milla.

Gestiona clientes, comercios, repartidores, pedidos y tarifas. Consistencia fuerte vía PostgreSQL.
Al asignar un pedido, publica el evento `PedidoAsignado` a AWS SQS para que el `tracking-service`
empiece a recibir y transmitir ubicaciones en vivo.

## Stack
Java 25 · Spring Boot 4.1.0 (MVC) · Spring Data JPA · PostgreSQL 18 · Spring Security + JWT

## Arquitectura
Clean/Hexagonal. Ver el Javadoc de `package-info.java` en cada paquete de `domain`,
`application` e `infrastructure` para la responsabilidad de cada capa.

## Correr en local
1. Levantar Postgres: `docker compose up -d`
2. Ejecutar la app: `mvn spring-boot:run` (o desde IntelliJ, clase `NucleoServiceApplication`)

## Estado
Fase 0 — estructura base. Sin lógica de dominio todavía (Fase 1).
