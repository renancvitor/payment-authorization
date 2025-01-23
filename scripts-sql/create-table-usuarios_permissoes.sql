CREATE TABLE usuarios_permissoes (
    id_usuario INT NOT NULL,
    id_permissao INT NOT NULL,
    PRIMARY KEY (id_usuario, id_permissao),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_permissao) REFERENCES permissoes(id)
);
