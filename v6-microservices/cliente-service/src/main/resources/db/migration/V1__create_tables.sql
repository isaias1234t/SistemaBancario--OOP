CREATE table pessoas(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    telefone VARCHAR (11) NOT NULL,
    tipo VARCHAR (255) NOT NULL,
    nome VARCHAR (255),
    cpf VARCHAR (11),
    razao_social VARCHAR(255),
    cnpj VARCHAR(14)
);