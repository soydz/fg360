-- INSERCIÓN DE DATOS DE PRUEBA

-- Tipos de documento
INSERT INTO type_of_id (user_id_type) VALUES
('CEDULA_CIUDADANIA'),
('TARJETA_IDENTIDAD'),
('CEDULA_EXTRANJERIA'),
('PASAPORTE'),
('REGISTRO_CIVIL'),
('REGISTRO_ESPECIAL_PERMANENCIA');

-- Roles
INSERT INTO role (role_name) VALUES
('ADMINISTRADOR'),
('CONDUCTOR'),
('OPERADOR');

-- Permisos
INSERT INTO permission (permission_name) VALUES
('CREATE'),
('READ'),
('UPDATE'),
('DELETE');

-- Usuarios (contraseñas hasheadas con bcrypt)
INSERT INTO app_user (id, name, lastname, email, password, disable, account_expired, account_locked, credential_expired, type_of_id) VALUES
(1, 'Carlos', 'Gómez', 'carlos@example.com',    '$2a$10$DNwVrtXylvjmwlodSpDksezK1z9qO41DceikeKd4R/8./2TdWa5C.', false, false, false, false, 1), -- clave: carlos12
(2, 'Ana',    'López', 'ana@example.com',       '$2a$10$kPAkSNrJYMLacM0Fgr.heeZSureiDS3EwXs7U0soK9IRo9LwiuTae', false, false, false, false, 2), -- clave: ana12345
(3, 'Miguel', 'Pérez', 'miguel@example.com',    '$2a$10$3xbckxcuoHoWuTEQfwfJ5uqtQgeccnxuKntBYbEOl2fzeO8oP7ndm', false, false, false, false, 1), -- clave: miguel12
(4, 'Laura',  'Rodríguez', 'laura@example.com', '$2a$10$0.RxOubrwuyvRGGY1b4/eO7ZJKC4gnybw7ZkS5H/sil7qcoYtI17i', false, false, false, false, 4); -- clave: laura123


-- Relación User - Role (ManyToMany)
INSERT INTO user_role (user_id, role_id) VALUES
(1, 1),  -- Carlos → ADMINISTRADOR
(2, 2),  -- Ana → CONDUCTOR
(3, 3),  -- Miguel → OPERADOR
(4, 2);  -- Laura → CONDUCTOR

-- Relación Role - Permission (ManyToMany)
INSERT INTO role_permission (role_id, permission_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), -- ADMINISTRADOR: todos los permisos
(2, 2),                         -- CONDUCTOR: READ
(3, 2), (3, 3);                 -- OPERADOR: READ, UPDATE

