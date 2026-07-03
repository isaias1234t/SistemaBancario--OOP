CREATE TABLE usuarios(
    id    BIGSERIAL PRIMARY KEY NOT NULL,
    email VARCHAR(255)          NOT NULL,
    senha VARCHAR(255)          NOT NULL,
    role  VARCHAR(50)            NOT NULL
);