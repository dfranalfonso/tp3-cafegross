-- Tabla paramétrica de Orígenes --
CREATE TABLE origen_cafe (
    id_origen INT AUTO_INCREMENT PRIMARY KEY,
    pais VARCHAR(50) NOT NULL,
    variedad VARCHAR(50) NOT NULL,
    perfil_taza VARCHAR(100) NOT NULL
);

-- Tabla de Tolvas --
CREATE TABLE tolva (
    id_tolva INT AUTO_INCREMENT PRIMARY KEY,
    numero_tolva INT NOT NULL UNIQUE,
    id_origen INT NOT NULL,
    pesomax DECIMAL(8,2) NOT NULL,
    kilos_actuales DECIMAL(8,2) DEFAULT 0.00,
    tipo_tolva ENUM('ESTANDAR', 'HERMETICA') NOT NULL,
    presion_atmosferica DECIMAL(5,2) DEFAULT 1.00,
    estado_operativo BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_tolva_origen FOREIGN KEY (id_origen) REFERENCES origen_cafe(id_origen)
);

-- Tabla de Registro de Cargas --
CREATE TABLE registro_carga (
    id_carga INT AUTO_INCREMENT PRIMARY KEY,
    id_tolva INT NOT NULL,
    fecha_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    kilos_ingresados DECIMAL(6,2) NOT NULL,
    operador VARCHAR(60) NOT NULL,
    CONSTRAINT fk_carga_tolva FOREIGN KEY (id_tolva) REFERENCES tolva(id_tolva),
    CONSTRAINT chk_rango_carga CHECK (kilos_ingresados >= 10.00 AND kilos_ingresados <= 70.00)
);

-- Carga inicial de datos --
INSERT INTO origen_cafe (pais, variedad, perfil_taza) VALUES
('Colombia', 'Castillo / Caturra', 'Acidez cítrica brillante y notas de caramelo'),
('Etiopía', 'Heirloom Yirgacheffe', 'Notas florales a jazmín y frutos silvestres'),
('Bolivia', 'Típica de Altura (Caranavi)', 'Cuerpo denso, notas a chocolate amargo y nuez');

INSERT INTO tolva (numero_tolva, id_origen, pesomax, kilos_actuales, tipo_tolva, presion_atmosferica, estado_operativo) VALUES
(101, 1, 1500.00, 450.00, 'ESTANDAR', 1.00, TRUE),
(102, 2, 800.00, 210.00, 'HERMETICA', 1.08, TRUE),
(103, 3, 1200.00, 0.00, 'ESTANDAR', 1.00, TRUE);

INSERT INTO registro_carga (id_tolva, kilos_ingresados, operador) VALUES
(1, 60.00, 'Francisco Alfonso'),
(1, 55.50, 'Francisco Alfonso'),
(2, 35.00, 'Operador Deposito 1');

-- Consulta de verificación de tolvas y stock --
SELECT 
    t.numero_tolva AS 'Tolva N°',
    o.pais AS 'Origen',
    o.variedad AS 'Variedad',
    t.tipo_tolva AS 'Tipo',
    t.kilos_actuales AS 'Stock Actual (kg)',
    t.pesomax AS 'Capacidad Máx (kg)',
    ROUND((t.kilos_actuales / t.pesomax) * 100, 2) AS 'Ocupación (%)'
FROM tolva t
INNER JOIN origen_cafe o ON t.id_origen = o.id_origen;

-- Consulta de trazabilidad histórica --
SELECT 
    rc.id_carga AS 'ID Carga',
    t.numero_tolva AS 'Tolva',
    o.pais AS 'Origen',
    rc.kilos_ingresados AS 'Kilos',
    rc.fecha_hora AS 'Fecha y Hora',
    rc.operador AS 'Operador'
FROM registro_carga rc
INNER JOIN tolva t ON rc.id_tolva = t.id_tolva
INNER JOIN origen_cafe o ON t.id_origen = o.id_origen
ORDER BY rc.fecha_hora DESC;
