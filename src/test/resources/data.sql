INSERT INTO user_app
(id, email, password, active, registration_date, role_user, first_name)
VALUES(1, 'user1@prueba.com', '$2a$10$La/204IX68uuOacbmsLs3.YHvEpvB.UZeVQUU18PQy6Uc.1Knc0xK', 1, '2023-06-17 01:58:18', 'ADMIN', 'user1');


INSERT INTO empleado
(id, cedula, nombre, primer_apellido, segundo_apellido, direccion, ciudad, telefono, habilitado, fecha_inicio, fecha_fin)
VALUES(1, 1111111111, 'empleado1', 'apellido1', 'apellido2', 'calle 1 # 1-1', 'Florida', 1111111111, 1, '2023-06-10', NULL);
INSERT INTO empleado
(id, cedula, nombre, primer_apellido, segundo_apellido, direccion, ciudad, telefono, habilitado, fecha_inicio, fecha_fin)
VALUES(2, 2222222222, 'empleado2', 'apellido2', 'apellido2_1', 'calle 2 # 2-2', 'Cauca', 2222222222, 1, '2000-05-23', NULL);
INSERT INTO empleado
(id, cedula, nombre, primer_apellido, segundo_apellido, direccion, ciudad, telefono, habilitado, fecha_inicio, fecha_fin)
VALUES(3, 111111111, 'empleado1_3', 'apellido1_1', 'apellido2_1', 'calle 1 # 1 - 1', 'Florida', 111111111111, 1, '2023-06-17', NULL);


INSERT INTO cliente
(id, nombre, direccion, ciudad, telefono, habilitado, fecha_corte, fecha_inicio, fecha_fin)
VALUES(1, 'cliente1', 'calle 1 # 1-1', 'Florida', 1111111111, 1, 25, '2023-11-25', NULL);

INSERT INTO cliente
(id, nombre, direccion, ciudad, telefono, habilitado, fecha_corte, fecha_inicio, fecha_fin)
VALUES(2, 'cliente2', 'calle 2 # 2-2', 'Cali', 2222222222, 0, 20, '2023-10-20', NULL);

INSERT INTO guia
(id_guia, destinatario, direccion, ciudad, telefono, habilitado, estado, fecha_entrada, fecha_entrega, id_cliente, id_empleado)
VALUES(1, 'destinatario1', 'calle 1 # 1-1', 'Palmira', 1111111111, 1, 'reparto', '2023-11-28', '2023-11-29', 1, 1);

