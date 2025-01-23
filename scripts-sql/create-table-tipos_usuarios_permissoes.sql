CREATE TABLE tipos_usuarios_permissoes (
    id_tipo_usuario INT NOT NULL,
    id_permissao INT NOT NULL,
    PRIMARY KEY (id_tipo_usuario, id_permissao),
    FOREIGN KEY (id_tipo_usuario) REFERENCES tipos_usuarios(id),
    FOREIGN KEY (id_permissao) REFERENCES permissoes(id)
);

INSERT INTO tipos_usuarios_permissoes (id_tipo_usuario, id_permissao)
VALUES 
(1, 1),  -- GERENCIAR_USUARIOS
(1, 2),  -- REALIZAR_SOLICITAOES
(1, 3),  -- VISUALIZAR_TODAS_SOLICITACOES
(1, 4);  -- APROVAR_REPROVAR_SOLICITACOES

INSERT INTO tipos_usuarios_permissoes (id_tipo_usuario, id_permissao)
VALUES 
(2, 2),  -- REALIZAR_SOLICITAOES
(2, 3),  -- VISUALIZAR_TODAS_SOLICITACOES
(2, 4);  -- APROVAR_REPROVAR_SOLICITACOES

INSERT INTO tipos_usuarios_permissoes (id_tipo_usuario, id_permissao)
VALUES 
(3, 2),  -- REALIZAR_SOLICITAOES
(3, 3);  -- VISUALIZAR_TODAS_SOLICITACOES

INSERT INTO tipos_usuarios_permissoes (id_tipo_usuario, id_permissao)
VALUES 
(4, 2);  -- REALIZAR_SOLICITAOES
