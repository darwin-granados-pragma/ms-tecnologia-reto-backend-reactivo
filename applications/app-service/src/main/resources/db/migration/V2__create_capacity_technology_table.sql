CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE capacidad_tecnologias (
  id_capacidad_tecnologia VARCHAR(50) PRIMARY KEY DEFAULT gen_random_uuid(),
  id_tecnologia VARCHAR(50) NOT NULL,
  id_capacidad VARCHAR(50) NOT NULL,
  FOREIGN KEY (id_tecnologia) REFERENCES tecnologia(id_tecnologia)
);
