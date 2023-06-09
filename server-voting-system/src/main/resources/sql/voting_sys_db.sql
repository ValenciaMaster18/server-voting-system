-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 06-04-2023 a las 13:49:22
-- Versión del servidor: 8.0.32-0ubuntu0.22.04.2
-- Versión de PHP: 8.1.2-1ubuntu2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;



-- --------------------------------------------------------
DROP DATABASE IF EXISTS voting_sys_db;
CREATE DATABASE voting_sys_db;

CREATE TABLE `aprendices` (
  `id` varchar(25) NOT NULL,
  `apellido` varchar(100) DEFAULT NULL,
  `celular` varchar(15) DEFAULT NULL,
  `correoElectronico` varchar(250) DEFAULT NULL,
  `creationDateTime` datetime(6) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `ficha` varchar(20) DEFAULT NULL,
  `lastModified` datetime(6) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `programa` varchar(250) DEFAULT NULL,
  `tipoDocumento` varchar(10) DEFAULT NULL,
  `idUsuario` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------


CREATE TABLE `candidatos` (
  `id` int NOT NULL,
  `creationDateTime` datetime(6) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `lastModified` datetime(6) DEFAULT NULL,
  `propuestas` varchar(250) DEFAULT NULL,
  `idAprendiz` varchar(25) DEFAULT NULL,
  `idImagen` varchar(25) DEFAULT NULL,
  `idVotacion` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------



CREATE TABLE `imagenes` (
  `id` varchar(25) NOT NULL,
  `creationDateTime` datetime(6) DEFAULT NULL,
  `image` mediumblob,
  `lastModified` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------



CREATE TABLE `Token` (
  `id` int NOT NULL,
  `expired` bit(1) NOT NULL,
  `revoked` bit(1) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `tokenType` varchar(255) DEFAULT NULL,
  `idUsuario` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Token`
--

-- INSERT INTO `Token` (`id`, `expired`, `revoked`, `token`, `tokenType`, `idUsuario`) VALUES
-- (1, b'0', b'0', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTM240QWNNaW4wIiwiaWF0IjoxNjgwNzIwMzY5LCJleHAiOjE2ODA3MjE4MDl9.uwf3NjM4mQ_-XkgEwcwVxrrHMQzJwxGS_qLQQAnMFx0', 'BEARER', 1);

-- --------------------------------------------------------



CREATE TABLE `usuarios` (
  `id` int NOT NULL,
  `accountNonExpired` bit(1) DEFAULT b'1',
  `accountNonLocked` bit(1) DEFAULT b'1',
  `authority` varchar(255) DEFAULT NULL,
  `creationDateTime` datetime(6) DEFAULT NULL,
  `credentialsNonExpired` bit(1) DEFAULT b'1',
  `enabled` bit(1) DEFAULT b'1',
  `lastModified` datetime(6) DEFAULT NULL,
  `password` varchar(250) DEFAULT NULL,
  `username` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

-- INSERT INTO `usuarios` (`id`, `accountNonExpired`, `accountNonLocked`, `authority`, `creationDateTime`, `credentialsNonExpired`, `enabled`, `lastModified`, `password`, `username`) VALUES
-- (1, b'1', b'1', 'ROLE_ADMINISTRADOR', '2023-04-05 13:46:09.057086', b'1', b'1', '2023-04-05 13:46:09.057697', '$2a$10$yI4cYkQMjzAq48lgjkNZKuk0cT24JmVW9O/avV3cA8MzIgR0Hy73G', 'S3n4AcMin0');

-- --------------------------------------------------------



CREATE TABLE `votaciones` (
  `id` int NOT NULL,
  `creationDateTime` datetime(6) DEFAULT NULL,
  `currentVotacion` bit(1) DEFAULT NULL,
  `descripcion` varchar(250) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `lastModified` datetime(6) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------



CREATE TABLE `votos` (
  `id` int NOT NULL,
  `fechaRegistro` datetime(6) DEFAULT NULL,
  `valido` bit(1) DEFAULT NULL,
  `idAprendiz` varchar(25) DEFAULT NULL,
  `idCandidato` int DEFAULT NULL,
  `idVotacion` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;




ALTER TABLE `aprendices`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `aprendices_unique` (`correoElectronico`,`celular`),
  ADD KEY `FKhytrudneruo7533gl5wbk9odo` (`idUsuario`);


ALTER TABLE `candidatos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `candidatos_unique` (`idAprendiz`),
  ADD KEY `FKfnxrvbv3yjlx7crddgvypsh89` (`idImagen`),
  ADD KEY `FKh1rqb7msjhxrs1rbtlr4oa46i` (`idVotacion`);


ALTER TABLE `imagenes`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `Token`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_3wi2t4g8oiplxjflw3o2lkv2y` (`token`),
  ADD KEY `FK56xvftgniyisi7gth8mmeeq7i` (`idUsuario`);


ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `usuarios_unique` (`username`);


ALTER TABLE `votaciones`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `votos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `votos_unique` (`idVotacion`,`idAprendiz`),
  ADD KEY `FKtm7xkwlfqc7lt0075yqf3j4b` (`idAprendiz`),
  ADD KEY `FKci82tovc2snpas4ov101m4ve1` (`idCandidato`);




ALTER TABLE `candidatos`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;


ALTER TABLE `Token`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

ALTER TABLE `usuarios`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;


ALTER TABLE `votaciones`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;


ALTER TABLE `votos`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;




ALTER TABLE `aprendices`
  ADD CONSTRAINT `FKhytrudneruo7533gl5wbk9odo` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`);


ALTER TABLE `candidatos`
  ADD CONSTRAINT `FK8tfgrgsxj7fca13fwnwn5fjak` FOREIGN KEY (`idAprendiz`) REFERENCES `aprendices` (`id`),
  ADD CONSTRAINT `FKfnxrvbv3yjlx7crddgvypsh89` FOREIGN KEY (`idImagen`) REFERENCES `imagenes` (`id`),
  ADD CONSTRAINT `FKh1rqb7msjhxrs1rbtlr4oa46i` FOREIGN KEY (`idVotacion`) REFERENCES `votaciones` (`id`);


ALTER TABLE `Token`
  ADD CONSTRAINT `FK56xvftgniyisi7gth8mmeeq7i` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`);


ALTER TABLE `votos`
  ADD CONSTRAINT `FK3g2njpxay950a468rexypypjo` FOREIGN KEY (`idVotacion`) REFERENCES `votaciones` (`id`),
  ADD CONSTRAINT `FKci82tovc2snpas4ov101m4ve1` FOREIGN KEY (`idCandidato`) REFERENCES `candidatos` (`id`),
  ADD CONSTRAINT `FKtm7xkwlfqc7lt0075yqf3j4b` FOREIGN KEY (`idAprendiz`) REFERENCES `aprendices` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
