INSERT INTO tipo(
	idtipo, nombre, valor)
	VALUES (1, 'Cancha', 'Cancha');

INSERT INTO declaracion(
	iddeclaracion, creadapor, fechafin,  nombre, estado, esbase)
	VALUES (1, 'wuiler@gmail.com', now(), 'Declaración Jurada COVID-19', 1, true);

INSERT INTO pregunta(
	idpregunta, nombre, tipo)
	VALUES (1, 'Ha desarrollado en los últimos días episodio febriles con temperatura mayor a los 37.5, tenido cefalea, diarrea, perdida del olfato o gusto, tos o dificultad', 'option');

INSERT INTO pregunta(
	idpregunta, nombre, tipo)
	VALUES (2, 'En los últimos 14 días, ¿ha estado en contacto con personas sospechosas o confirmadas de COVID-19?', 'option');

INSERT INTO pregunta(
	idpregunta, nombre, tipo)
	VALUES (3, 'En los últimos 14 días, ¿ha permanecidoo visitado alguno de los hospitales COVID-19 en el ámbito de Argentina?', 'option');        

INSERT INTO pregunta(
	idpregunta, nombre, tipo)
	VALUES (4, '¿Ha regresado de viaje del exterior en los últimos 14 días?', 'option');

INSERT INTO pregunta(
	idpregunta, nombre, tipo)
	VALUES (5, '¿Ha estado expuesto a grupos humanos numerosos desconocidos sin respetar la distancia social establecida', 'option');        

INSERT INTO pregunta(
	idpregunta, nombre, tipo)
	VALUES (6, '¿Permanecio en lugares cerrados con grupos humanos numerosos por mas de 30 minutos seguidos respectando o no la distancia social', 'option');

INSERT INTO pregunta(
	idpregunta, nombre, tipo)
	VALUES (7, 'Temperatura', 'text');

INSERT INTO declaracionpreguntas(
	iddeclaracion, idpregunta)
	VALUES (1, 1);
INSERT INTO declaracionpreguntas(
	iddeclaracion, idpregunta)
	VALUES (1, 2);
INSERT INTO declaracionpreguntas(
	iddeclaracion, idpregunta)
	VALUES (1, 3);
INSERT INTO declaracionpreguntas(
	iddeclaracion, idpregunta)
	VALUES (1, 4);
INSERT INTO declaracionpreguntas(
	iddeclaracion, idpregunta)
	VALUES (1, 5);
INSERT INTO declaracionpreguntas(
	iddeclaracion, idpregunta)
	VALUES (1, 6);
INSERT INTO declaracionpreguntas(
	iddeclaracion, idpregunta)
	VALUES (1, 7);


--INSERT INTO persona(
--	mail, apellido, barrio, cuil, departamento, domicilio, estadocivil, fechanacimiento, nacionalidad, nombre, numero, numerodocumento, pais, piso, sexo, telefonofijo, telefonomovil, tipodocumento)
--	VALUES ('wuiler@gmail.com', 'Pozzi', '', '', '', 'Davila y Cartegena 2161', '', '', '', 'Jose Wuiler', 0, 26853788, '', '', '', '', '3516840233', 1);

--INSERT INTO entidad(
--	identidad, direccion, nombre, nombrecorto, telefono, url, idtipo, creadopor)
--	VALUES (1, 'Laguna Honda 9100', 'Laguna Squash', 'Laguna' , '3516840233', 'https://www.laguna.com/', 1,'wuiler@gmail.com');

--INSERT INTO entidad(
--	identidad, direccion, nombre, nombrecorto, telefono, url, idtipo, creadopor)
--  VALUES (2, 'Larreta 222', 'Monami Paddle & Squash', 'Monami' , '3516840233', 'https://www.monami.com/', 1,'wuiler@gmail.com');

--INSERT INTO declaracionentidad(
--	iddeclaracionentidad, estado, fecha, iddeclaracion, identidad)
--	VALUES (1, 1, now(), 1, 1);

--INSERT INTO declaracionentidad(
--	iddeclaracionentidad, estado, fecha, iddeclaracion, identidad)
--	VALUES (2, 1, now(), 1, 2);

