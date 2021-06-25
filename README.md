# lab-spring-security

## README

### para crear la red
docker network create springcloud

### Cofig-Server
docker build -t config-server:v1 .
docker run -p 8888:8888 --name config-server --network springcloud config-server:v1 

### para la BD
docker pull mysql:8
docker run -p 3306:3306 --name ms-mysql8 --network springcloud -e MYSQL_ROOT_PASSWORD=Ory1102 -e MYSQL_DATABASE=micros -d mysql:8

### Product Service
docker build -t product-service:v1 .
docker run -P --network springcloud product-service:v1 

###zuul-server
docker build -t zuul-server:v1 .
docker run -p 8090:8090 --network springcloud zuul-server:v1 

### USER Service
docker build -t user-service:v1 .
docker run -P --network springcloud user-service:v1 

### oauth Service
docker build -t oauth-service:v1 .
docker run -p 9100:9100 --network springcloud oauth-service:v1

### item Service
docker build -t item-service:v1 .
docker run -p 8002:8002 -p 8005:8005 -p 8007:8007 --network springcloud item-service:v1

### Zipkin server
docker build -t zipkin-server:v1 .
docker run -p 9411:9411 --name zipkin-server --network springcloud zipkin-server:v1

## INSERTS
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('sacunam', '1234', 1, 'Sebastián', 'Acuña', 'sacunam@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('jluffin', '1234', 1, 'Jorge', 'Lufín', 'conserjeria.cibercentro@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('esachez', '1234', 1, 'Edgar', 'Sánchez', 'es651259@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('jsadoval', '1234', 1, 'Julio', 'Sandoval', 'sacunam@hotmail.com');
 
 
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO roles (nombre) VALUES ('ROLE_MAYORDOMO');
INSERT INTO roles (nombre) VALUES ('ROLE_EMPLEADO');
INSERT INTO roles (nombre) VALUES ('ROLE_VECINO');
 
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 3);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (3, 4);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 4);


$2a$10$jSnrA6j63NSCGuEzg7MpwOiUJqGNY6F/j80nrM7Jk/aIrPfY/bRIy
$2a$10$KgrcKVilMaPvV9LDK2uM7.f328BIg.CmAxxXvGNm0wYNoOY6of5ha

INSERT INTO micros.users (email, enabled, last_name, name, password, username) VALUES ('cami@correo.com', 1, 'nava', 'cami', '12345', 'cami');

INSERT INTO micros.roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO micros.roles (name) VALUES ('ROLE_USER');

SELECT * FROM micros.users_roles;

INSERT INTO micros.users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO micros.users_roles (user_id, role_id) VALUES (2, 2);


SELECT * FROM micros.users;

INSERT INTO micros.users (email, enabled, last_name, name, password, username) VALUES ('cami@correo.com', 1, 'nava', 'cami', '12345', 'cami');

SELECT * FROM micros.products;

use micros.products;

INSERT INTO micros.products (name, price, creat_at) VALUES('Panasonic', 800, NOW());
INSERT INTO micros.products (name, price, creat_at) VALUES('Sony', 700, NOW());
INSERT INTO micros.products (name, price, creat_at) VALUES('Apple', 1000, NOW());
INSERT INTO micros.products (name, price, creat_at) VALUES('Sony Notebook', 1000, NOW());
INSERT INTO micros.products (name, price, creat_at) VALUES('Hewlett Packard', 500, NOW());
INSERT INTO micros.products (name, price, creat_at) VALUES('Bianchi', 600, NOW());
INSERT INTO micros.products (name, price, creat_at) VALUES('Nike', 100, NOW());
INSERT INTO micros.products (name, price, creat_at) VALUES('Adidas', 200, NOW());
INSERT INTO micros.products (name, price, creat_at) VALUES('Reebok', 300, NOW());

# Esquema ZIPKIN DDL

--
-- Copyright 2015-2019 The OpenZipkin Authors
--
-- Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
-- in compliance with the License. You may obtain a copy of the License at
--
-- http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software distributed under the License
-- is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
-- or implied. See the License for the specific language governing permissions and limitations under
-- the License.
--

CREATE TABLE IF NOT EXISTS zipkin_spans (
  `trace_id_high` BIGINT NOT NULL DEFAULT 0 COMMENT 'If non zero, this means the trace uses 128 bit traceIds instead of 64 bit',
  `trace_id` BIGINT NOT NULL,
  `id` BIGINT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `remote_service_name` VARCHAR(255),
  `parent_id` BIGINT,
  `debug` BIT(1),
  `start_ts` BIGINT COMMENT 'Span.timestamp(): epoch micros used for endTs query and to implement TTL',
  `duration` BIGINT COMMENT 'Span.duration(): micros used for minDuration and maxDuration query',
  PRIMARY KEY (`trace_id_high`, `trace_id`, `id`)
) ENGINE=InnoDB ROW_FORMAT=COMPRESSED CHARACTER SET=utf8 COLLATE utf8_general_ci;

ALTER TABLE zipkin_spans ADD INDEX(`trace_id_high`, `trace_id`) COMMENT 'for getTracesByIds';
ALTER TABLE zipkin_spans ADD INDEX(`name`) COMMENT 'for getTraces and getSpanNames';
ALTER TABLE zipkin_spans ADD INDEX(`remote_service_name`) COMMENT 'for getTraces and getRemoteServiceNames';
ALTER TABLE zipkin_spans ADD INDEX(`start_ts`) COMMENT 'for getTraces ordering and range';

CREATE TABLE IF NOT EXISTS zipkin_annotations (
  `trace_id_high` BIGINT NOT NULL DEFAULT 0 COMMENT 'If non zero, this means the trace uses 128 bit traceIds instead of 64 bit',
  `trace_id` BIGINT NOT NULL COMMENT 'coincides with zipkin_spans.trace_id',
  `span_id` BIGINT NOT NULL COMMENT 'coincides with zipkin_spans.id',
  `a_key` VARCHAR(255) NOT NULL COMMENT 'BinaryAnnotation.key or Annotation.value if type == -1',
  `a_value` BLOB COMMENT 'BinaryAnnotation.value(), which must be smaller than 64KB',
  `a_type` INT NOT NULL COMMENT 'BinaryAnnotation.type() or -1 if Annotation',
  `a_timestamp` BIGINT COMMENT 'Used to implement TTL; Annotation.timestamp or zipkin_spans.timestamp',
  `endpoint_ipv4` INT COMMENT 'Null when Binary/Annotation.endpoint is null',
  `endpoint_ipv6` BINARY(16) COMMENT 'Null when Binary/Annotation.endpoint is null, or no IPv6 address',
  `endpoint_port` SMALLINT COMMENT 'Null when Binary/Annotation.endpoint is null',
  `endpoint_service_name` VARCHAR(255) COMMENT 'Null when Binary/Annotation.endpoint is null'
) ENGINE=InnoDB ROW_FORMAT=COMPRESSED CHARACTER SET=utf8 COLLATE utf8_general_ci;

ALTER TABLE zipkin_annotations ADD UNIQUE KEY(`trace_id_high`, `trace_id`, `span_id`, `a_key`, `a_timestamp`) COMMENT 'Ignore insert on duplicate';
ALTER TABLE zipkin_annotations ADD INDEX(`trace_id_high`, `trace_id`, `span_id`) COMMENT 'for joining with zipkin_spans';
ALTER TABLE zipkin_annotations ADD INDEX(`trace_id_high`, `trace_id`) COMMENT 'for getTraces/ByIds';
ALTER TABLE zipkin_annotations ADD INDEX(`endpoint_service_name`) COMMENT 'for getTraces and getServiceNames';
ALTER TABLE zipkin_annotations ADD INDEX(`a_type`) COMMENT 'for getTraces and autocomplete values';
ALTER TABLE zipkin_annotations ADD INDEX(`a_key`) COMMENT 'for getTraces and autocomplete values';
ALTER TABLE zipkin_annotations ADD INDEX(`trace_id`, `span_id`, `a_key`) COMMENT 'for dependencies job';

CREATE TABLE IF NOT EXISTS zipkin_dependencies (
  `day` DATE NOT NULL,
  `parent` VARCHAR(255) NOT NULL,
  `child` VARCHAR(255) NOT NULL,
  `call_count` BIGINT,
  `error_count` BIGINT,
  PRIMARY KEY (`day`, `parent`, `child`)
) ENGINE=InnoDB ROW_FORMAT=COMPRESSED CHARACTER SET=utf8 COLLATE utf8_general_ci;
