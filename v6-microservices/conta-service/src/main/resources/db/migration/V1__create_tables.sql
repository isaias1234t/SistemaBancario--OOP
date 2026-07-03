CREATE TABLE contas (
                                numero BIGINT PRIMARY KEY NOT NULL,
                                saldo NUMERIC(19,2) NOT NULL,
                                ativa BOOLEAN NOT NULL,
                                pessoa_id BIGINT NOT NULL,
                                tipo VARCHAR(255),
                                limite NUMERIC(19,2),
                                taxa_rendimento NUMERIC(19,2)
);

CREATE TABLE transacoes (
                           id BIGSERIAL PRIMARY KEY NOT NULL,
                           tipo VARCHAR(255) NOT NULL,
                           valor NUMERIC(19,2) NOT NULL,
                           data TIMESTAMP NOT NULL,
                           conta_numero BIGINT REFERENCES contas(numero)
);