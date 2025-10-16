-- INSERCIÓN DE DATOS

-- Insertar valores en NivelPrioridad
INSERT INTO nivel_prioridad (nombre) VALUES
('Bajo'),
('Medio'),
('Alto'),
('Crítico');

-- Insertar valores en TipoAlerta
INSERT INTO tipo_alerta (nombre, descripcion, nivel_prioridad_id, tipo_encargado) VALUES
('Falla mecánica leve', 'Problemas menores detectados en el sistema.', 1, 'MECANICO'),
('Accidente de tránsito', 'Colisión o incidente vial.', 4, 'CONDUCTOR'),
('Falla en red', 'Problema con la conexión de red interna.', 3, 'SOPORTE_TECNICO'),
('Retraso logístico', 'Demora en la entrega de materiales.', 2, 'OPERADOR_LOGISTICA'),
('Alerta de seguridad', 'Incidente en zona restringida.', 4, 'SEGURIDAD');
