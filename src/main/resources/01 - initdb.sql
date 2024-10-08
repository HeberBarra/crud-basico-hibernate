CREATE DATABASE IF NOT EXISTS db_crud DEFAULT CHARACTER SET = utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;

USE db_crud;

CREATE TABLE IF NOT EXISTS tbEstudante (
    id INTEGER AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE KEY(email)
) DEFAULT CHARSET = utf8mb4;
