CREATE TABLE pedidos (
    id                UUID PRIMARY KEY,
    cliente_id        UUID NOT NULL,
    comercio_id       UUID NOT NULL,
    repartidor_id     UUID,

    origen_calle      VARCHAR(255) NOT NULL,
    origen_ciudad     VARCHAR(255) NOT NULL,
    origen_latitud    DOUBLE PRECISION NOT NULL,
    origen_longitud   DOUBLE PRECISION NOT NULL,

    destino_calle     VARCHAR(255) NOT NULL,
    destino_ciudad    VARCHAR(255) NOT NULL,
    destino_latitud   DOUBLE PRECISION NOT NULL,
    destino_longitud  DOUBLE PRECISION NOT NULL,

    tarifa_monto      NUMERIC(12, 2) NOT NULL,
    tarifa_moneda     VARCHAR(3) NOT NULL,

    estado            VARCHAR(20) NOT NULL,

    fecha_creacion    TIMESTAMPTZ NOT NULL,
    fecha_asignacion  TIMESTAMPTZ,
    fecha_entrega     TIMESTAMPTZ
);
