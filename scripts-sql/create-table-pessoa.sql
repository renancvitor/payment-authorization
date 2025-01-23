CREATE TABLE pessoa (
    idpessoa INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    datanascimento DATETIME NOT NULL,
    iddepartamento INT NOT NULL,
    idcargo INT NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    FOREIGN KEY (iddepartamento) REFERENCES departamento(id),
    FOREIGN KEY (idcargo) REFERENCES cargo(id)
);
