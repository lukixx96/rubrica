CREATE DATABASE IF NOT EXISTS rubrica;
USE rubrica;

CREATE TABLE IF NOT EXISTS persone (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    indirizzo VARCHAR(100),
    telefono VARCHAR(20),
    eta INT
    );

INSERT INTO persone (nome, cognome, indirizzo, telefono, eta) VALUES
    ('Steve', 'Jobs', 'via Cupertino 13', '0612344', 56),
    ( 'Bill', 'Gates', 'via Redmond 10', '06688989', 60),
    ('Babbo', 'Natale', 'via del Polo Nord', '00000111', 99);
