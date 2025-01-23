CREATE TABLE permissoes (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome_permissao VARCHAR(255) NOT NULL UNIQUE,
    descricao TEXT
);
