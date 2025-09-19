-- -------------------------------
-- 1. Permisos
-- -------------------------------
INSERT INTO public."permission" (permission_name) VALUES
('CREATE'),
('READ'),
('UPDATE'),
('DELETE');

-- 2. Roles
-- -------------------------------
INSERT INTO public."role" (role_name) VALUES
('CONDUCTOR'),
('ADMINISTRADOR'),
('OPERADOR');

-- -------------------------------
-- 3. Tipos de identificación
-- -------------------------------
INSERT INTO public.type_of_id (user_id_type) VALUES
(0), -- CEDULA_CIUDADANIA
(1), -- TARJETA_IDENTIDAD
(2), -- CEDULA_EXTRANJERIA
(3), -- PASAPORTE
(4), -- REGISTRO_CIVIL
(5); -- REGISTRO_ESPECIAL_PERMANENCIA

DELETE FROM app_user ;
-- -------------------------------
-- 4. Usuarios de prueba
-- -------------------------------
INSERT INTO public.app_user
(id, "name", lastname, email, "password", is_enable, account_no_expired, account_no_locked, credential_no_expired, type_of_id)
VALUES
(1, 'Lucas', 'Ramirez', 'lucas.ramirez@example.com', 'lucas', TRUE, TRUE, TRUE, TRUE, 26),
(2, 'Maria', 'Lopez', 'maria.lopez@example.com', 'maria', TRUE, TRUE, TRUE, TRUE, 27),
(3, 'Juan', 'Perez', 'juan.perez@example.com', 'juan', TRUE, TRUE, TRUE, TRUE, 28);


-- -------------------------------
-- 5. Asignar roles a usuarios
-- -------------------------------
INSERT INTO public.user_role (user_id, role_id) VALUES
(1, 5), -- Lucas -> ADMINISTRADOR
(2, 4), -- Maria -> CONDUCTOR
(3, 6); -- Juan -> OPERADOR

-- -------------------------------
-- 6. Asignar permisos a roles
-- -------------------------------
-- ADMINISTRADOR: todos los permisos
INSERT INTO public.role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM public."role" r, public."permission" p
WHERE r.role_name = 'ADMINISTRADOR';

-- CONDUCTOR: solo READ
INSERT INTO public.role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM public."role" r, public."permission" p
WHERE r.role_name = 'CONDUCTOR'
  AND p.permission_name IN ('READ');

-- OPERADOR: solo READ y UPDATE
INSERT INTO public.role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM public."role" r, public."permission" p
WHERE r.role_name = 'OPERADOR'
  AND p.permission_name IN ('READ','UPDATE');