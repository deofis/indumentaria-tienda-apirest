/* Valores pre-cargados para pruebas*/

INSERT INTO categorias (nombre, codigo) VALUES ('Tarjeta de Video', 'GPU');
INSERT INTO categorias (nombre, codigo) VALUES ('Microprocesador', 'CPU');

INSERT INTO marcas (nombre) VALUES ('Intel');
INSERT INTO marcas (nombre) VALUES ('AMD');
INSERT INTO marcas (nombre) VALUES ('Asus');
INSERT INTO marcas (nombre) VALUES ('MSI');

INSERT INTO unidades_medida (nombre, codigo) VALUES ('Unidad', 'U');
INSERT INTO unidades_medida (nombre, codigo) VALUES ('KiloGramo', 'KG');
INSERT INTO unidades_medida (nombre, codigo) VALUES ('Litro', 'L');

INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO usuarios (username, password, enabled, email, fecha_creacion, role_id) VALUES ('neritoo', '$2a$10$UbNq0Ojk5Qb0632FX4a.i.ov8nmYS/voWZ3qBDxe8evvvSD.oQEzS', true, 'ezegavilan95@gmail.com', NOW(), 1);