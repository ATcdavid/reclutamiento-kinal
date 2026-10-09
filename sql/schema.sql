-- Base de datos de reclutamiento (3FN): rol 1---N usuario ; aspirante independiente
CREATE DATABASE IF NOT EXISTS reclutamiento_db CHARACTER SET utf8mb4;
USE reclutamiento_db;

CREATE TABLE IF NOT EXISTS rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(60) NOT NULL,
    apellido VARCHAR(60) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    id_rol INT NOT NULL,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

CREATE TABLE IF NOT EXISTS aspirante (
    id_aspirante INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(60) NOT NULL,
    apellido VARCHAR(60) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(15) NOT NULL,
    direccion VARCHAR(150) NOT NULL,
    edad INT NOT NULL CHECK (edad BETWEEN 18 AND 100)
);

INSERT IGNORE INTO rol (nombre) VALUES ('USER'), ('ADMIN');
