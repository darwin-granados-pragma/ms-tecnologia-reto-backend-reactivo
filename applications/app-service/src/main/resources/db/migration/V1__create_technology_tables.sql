CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE tecnologia (
  id_tecnologia VARCHAR(50) PRIMARY KEY DEFAULT gen_random_uuid(),
  nombre VARCHAR(50) NOT NULL,
  descripcion VARCHAR(90) NOT NULL,
  CONSTRAINT nombre_unique_constraint UNIQUE (nombre)
);
