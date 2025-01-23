CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    id_tipo_usuario INT,
    FOREIGN KEY (id_tipo_usuario) REFERENCES tipos_usuarios(id),
    cpf VARCHAR(14) NOT NULL UNIQUE
);

ALTER TABLE sistema_pagamentos.usuarios
DROP INDEX fk_usuarios_pessoa_idx;

ALTER TABLE pessoa ADD COLUMN cpf VARCHAR(14) NOT NULL UNIQUE;