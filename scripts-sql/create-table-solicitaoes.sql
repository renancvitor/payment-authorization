CREATE TABLE solicitacoes (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fornecedor VARCHAR(255) NOT NULL,
    descricao TEXT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_pagamento DATE,
    forma_pagamento VARCHAR(255),
    valor_total DECIMAL(10,2),
    status VARCHAR(30),
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);
