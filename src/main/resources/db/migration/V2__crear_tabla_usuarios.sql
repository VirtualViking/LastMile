CREATE TABLE usuarios (
    id             UUID PRIMARY KEY,
    nombre         VARCHAR(255) NOT NULL,
    email          VARCHAR(255) NOT NULL UNIQUE,
    password_hash  VARCHAR(255) NOT NULL,
    rol            VARCHAR(20) NOT NULL,
    disponibilidad VARCHAR(20)
);
