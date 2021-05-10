-- --------------------------------------------------------
-- Hôte:                         127.0.0.1
-- Version du serveur:           8.0.21 - MySQL Community Server - GPL
-- SE du serveur:                Win64
-- HeidiSQL Version:             11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Listage de la structure de la base pour park
CREATE DATABASE IF NOT EXISTS `park` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `park`;

-- Listage de la structure de la table park. client
CREATE TABLE IF NOT EXISTS `client` (
  `id_client` int NOT NULL AUTO_INCREMENT,
  `cin` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `nom` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `prenom` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `gsm` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `adresse` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `id_Vehicule` int NOT NULL,
  PRIMARY KEY (`id_client`,`id_Vehicule`) USING BTREE,
  UNIQUE KEY `id_client` (`id_client`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `cin` (`cin`),
  KEY `fk_Client_Vehicule1_idx` (`id_Vehicule`) USING BTREE,
  CONSTRAINT `fk_Client_Vehicule1` FOREIGN KEY (`id_Vehicule`) REFERENCES `vehicule` (`id_vehicule`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Listage des données de la table park.client : ~14 rows (environ)
DELETE FROM `client`;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` (`id_client`, `cin`, `nom`, `prenom`, `gsm`, `adresse`, `email`, `id_Vehicule`) VALUES
	(1, 'J7889', 'kermit', 'Ayoub', '061245', 'Agadir', 'kermitayoub@gmail.com', 4),
	(2, 'G856', 'BB', 'BB', '0661', 'Maison', 'bb@gmail.com', 8),
	(3, 'A36', 'cc', 'cc', '0664', 'Immeuble 16', 'cc@hotmail.fr', 8),
	(4, 'd30', 'bb', 'bb', '0662', 'rue 15', 'tt@gmail.com', 2),
	(5, 'bh283604', 'hadraoui', 'zakaria', '0661979688', 'Meknes', 'zhadraoui@gmail.com', 1),
	(9, 'bh28360', 'hadraoui', 'zakaria', '0661979688', 'Meknes', 'gl@gmail.com', 1),
	(10, 'bh28304', 'hadraoui', 'zakaria', '0661979688', 'Meknes', '1@gmail.com', 1),
	(11, 'bh28604', 'hadraoui', 'zakaria', '0661979688', 'Meknes', '2@gmail.com', 1),
	(12, 'bh23604', 'hadraoui', 'zakaria', '0661979688', 'Meknes', '99@gmail.com', 1),
	(13, 'bh83604', 'hadraoui', 'zakaria', '0661979688', 'Meknes', '3@gmail.com', 1),
	(14, 'bh283605', 'hadraoui', 'zakaria', '0661979688', 'Meknes', '6@gmail.com', 1),
	(15, 'bh283606', 'hadraoui', 'zakaria', '0661979688', 'Meknes', '5@gmail.com', 1),
	(16, 'bh283607', 'hadraoui', 'zakaria', '0661979688', 'Meknes', '4@gmail.com', 1),
	(17, '17', '17', '17', '17', '17', '17', 8);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;

-- Listage de la structure de la vue park. get_parking_nb_place_not_paye
-- Création d'une table temporaire pour palier aux erreurs de dépendances de VIEW
CREATE TABLE `get_parking_nb_place_not_paye` (
	`id_parking` INT(10) NOT NULL,
	`adresse` VARCHAR(45) NULL COLLATE 'utf8_unicode_ci',
	`nombre` BIGINT(19) NOT NULL
) ENGINE=MyISAM;

-- Listage de la structure de la vue park. get_parking_nb_place_used
-- Création d'une table temporaire pour palier aux erreurs de dépendances de VIEW
CREATE TABLE `get_parking_nb_place_used` (
	`id_parking` INT(10) NOT NULL,
	`adresse` VARCHAR(45) NULL COLLATE 'utf8_unicode_ci',
	`capacite` INT(10) NULL,
	`nombre_place` BIGINT(19) NOT NULL
) ENGINE=MyISAM;

-- Listage de la structure de la table park. occupation
CREATE TABLE IF NOT EXISTS `occupation` (
  `id_occupation` int NOT NULL AUTO_INCREMENT,
  `date_debut` datetime DEFAULT NULL,
  `date_fin` datetime DEFAULT NULL,
  `id_place` int NOT NULL,
  `id_vehicule` int NOT NULL,
  PRIMARY KEY (`id_occupation`) USING BTREE,
  KEY `id_place` (`id_place`),
  KEY `fk_reservation_vehicule` (`id_vehicule`) USING BTREE,
  CONSTRAINT `fk_reservation_place` FOREIGN KEY (`id_place`) REFERENCES `place` (`id_Place`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_reservation_vehicule` FOREIGN KEY (`id_vehicule`) REFERENCES `vehicule` (`id_vehicule`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- Listage des données de la table park.occupation : ~6 rows (environ)
DELETE FROM `occupation`;
/*!40000 ALTER TABLE `occupation` DISABLE KEYS */;
INSERT INTO `occupation` (`id_occupation`, `date_debut`, `date_fin`, `id_place`, `id_vehicule`) VALUES
	(1, '2021-05-01 01:01:00', '2021-05-01 02:30:00', 8, 2),
	(2, '2021-05-09 23:59:00', '2021-05-10 18:00:00', 5, 5),
	(5, '2021-05-03 14:10:00', '2021-05-03 15:10:00', 12, 8),
	(6, '2021-05-09 00:01:00', '2021-05-09 06:06:00', 9, 1),
	(7, '2021-05-09 00:01:00', NULL, 10, 1),
	(8, '2021-05-09 04:30:00', NULL, 12, 1);
/*!40000 ALTER TABLE `occupation` ENABLE KEYS */;

-- Listage de la structure de la table park. paiement
CREATE TABLE IF NOT EXISTS `paiement` (
  `id_paiement` int NOT NULL AUTO_INCREMENT,
  `id_occupation` int DEFAULT NULL,
  `date_paiement` timestamp NULL DEFAULT NULL,
  `duree` float DEFAULT NULL,
  `montant` float DEFAULT NULL,
  PRIMARY KEY (`id_paiement`) USING BTREE,
  UNIQUE KEY `id_occupation` (`id_occupation`),
  CONSTRAINT `FK_paiment_occupation` FOREIGN KEY (`id_occupation`) REFERENCES `occupation` (`id_occupation`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Listage des données de la table park.paiement : ~4 rows (environ)
DELETE FROM `paiement`;
/*!40000 ALTER TABLE `paiement` DISABLE KEYS */;
INSERT INTO `paiement` (`id_paiement`, `id_occupation`, `date_paiement`, `duree`, `montant`) VALUES
	(1, 1, '2021-05-09 23:36:01', 89, 13.35),
	(2, 2, '2021-05-10 00:11:46', 1081, 108.1),
	(3, 5, '2021-05-09 23:36:13', 60, 10),
	(4, 6, '2021-05-09 23:34:15', 365, 42.5833);
/*!40000 ALTER TABLE `paiement` ENABLE KEYS */;

-- Listage de la structure de la table park. parking
CREATE TABLE IF NOT EXISTS `parking` (
  `id_parking` int NOT NULL AUTO_INCREMENT,
  `adresse` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `capacite` int DEFAULT NULL,
  PRIMARY KEY (`id_parking`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Listage des données de la table park.parking : ~9 rows (environ)
DELETE FROM `parking`;
/*!40000 ALTER TABLE `parking` DISABLE KEYS */;
INSERT INTO `parking` (`id_parking`, `adresse`, `capacite`) VALUES
	(1, 'Nador', 2),
	(2, 'Safi', 2),
	(3, 'Fes', 1),
	(4, 'Agadir', 2),
	(5, 'Meknes', 1),
	(6, 'Casa', 1),
	(7, 'Rabat', 2),
	(8, 'Tanger', 1),
	(9, 'TIZNIT', 0);
/*!40000 ALTER TABLE `parking` ENABLE KEYS */;

-- Listage de la structure de la table park. place
CREATE TABLE IF NOT EXISTS `place` (
  `id_Place` int NOT NULL AUTO_INCREMENT,
  `descr` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `etat` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `id_Parking` int DEFAULT NULL,
  PRIMARY KEY (`id_Place`),
  KEY `fk_Place_Parking1` (`id_Parking`),
  CONSTRAINT `fk_Place_Parking1` FOREIGN KEY (`id_Parking`) REFERENCES `parking` (`id_parking`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Listage des données de la table park.place : ~12 rows (environ)
DELETE FROM `place`;
/*!40000 ALTER TABLE `place` DISABLE KEYS */;
INSERT INTO `place` (`id_Place`, `descr`, `etat`, `id_Parking`) VALUES
	(2, 'PLACE2', 'OUI', 3),
	(3, 'Place3', 'OUI', 7),
	(4, 'Place5', 'OUI', 5),
	(5, 'Place5', 'NON', 1),
	(6, 'Place6', 'OUI', 6),
	(7, 'Place77', 'OUI', 4),
	(8, 'Place8', 'OUI', 1),
	(9, 'Place9', 'OUI', 2),
	(10, 'Place10', 'OUI', 4),
	(12, 'PLACE12', 'Non', 7),
	(18, 'Place18', 'OUI', 8),
	(19, 'place19', 'OUI', 2);
/*!40000 ALTER TABLE `place` ENABLE KEYS */;

-- Listage de la structure de la table park. tarif
CREATE TABLE IF NOT EXISTS `tarif` (
  `id_Tarif` int NOT NULL AUTO_INCREMENT,
  `heure_debut` int DEFAULT NULL,
  `heure_fin` int DEFAULT NULL,
  `prix` float DEFAULT NULL,
  PRIMARY KEY (`id_Tarif`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Listage des données de la table park.tarif : ~6 rows (environ)
DELETE FROM `tarif`;
/*!40000 ALTER TABLE `tarif` DISABLE KEYS */;
INSERT INTO `tarif` (`id_Tarif`, `heure_debut`, `heure_fin`, `prix`) VALUES
	(1, 1, 60, 10),
	(2, 61, 120, 9),
	(3, 121, 240, 8),
	(4, 241, 600, 7),
	(5, 601, 1440, 6),
	(6, 1441, 1000000000, 5);
/*!40000 ALTER TABLE `tarif` ENABLE KEYS */;

-- Listage de la structure de la table park. user
CREATE TABLE IF NOT EXISTS `user` (
  `id_user` int NOT NULL AUTO_INCREMENT,
  `login` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `pwd` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `first_inscr` timestamp NULL DEFAULT NULL,
  `last_conn` timestamp NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `roles` varchar(60) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Listage des données de la table park.user : ~3 rows (environ)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id_user`, `login`, `pwd`, `first_inscr`, `last_conn`, `email`, `roles`) VALUES
	(1, 'zakaria', '827ccb0eea8a706c4c34a16891f84e7b', '2021-01-01 00:00:00', '2021-05-10 08:07:30', 'zakaria@gmail.com', 'user'),
	(2, 'admin', '21232f297a57a5a743894a0e4a801fc3', '2020-06-16 00:00:00', '2021-05-10 08:31:35', 'admin@gmail.com', 'admin'),
	(3, 'user', '827ccb0eea8a706c4c34a16891f84e7b', '2021-10-11 00:00:00', NULL, 'user@gmail.com', 'user');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Listage de la structure de la table park. vehicule
CREATE TABLE IF NOT EXISTS `vehicule` (
  `id_Vehicule` int NOT NULL AUTO_INCREMENT,
  `matricule` varchar(99) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_Vehicule`),
  UNIQUE KEY `id_Vehicule` (`id_Vehicule`),
  UNIQUE KEY `matricule` (`matricule`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Listage des données de la table park.vehicule : ~6 rows (environ)
DELETE FROM `vehicule`;
/*!40000 ALTER TABLE `vehicule` DISABLE KEYS */;
INSERT INTO `vehicule` (`id_Vehicule`, `matricule`) VALUES
	(1, '01/A/1998'),
	(5, '09-a-2014'),
	(8, '15/A/202111'),
	(2, '16/A/202111'),
	(4, '22-j-2000'),
	(3, '78-a-5796');
/*!40000 ALTER TABLE `vehicule` ENABLE KEYS */;

-- Listage de la structure de déclencheur park. after_delete_place
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='';
DELIMITER //
CREATE DEFINER=`root`@`localhost` TRIGGER `after_delete_place` AFTER DELETE ON `place` FOR EACH ROW BEGIN
DECLARE NB INT DEFAULT 0;

SELECT COUNT(*) INTO NB FROM place WHERE id_Parking = old.id_Parking;

UPDATE parking SET parking.capacite=nb
WHERE parking.id_parking=old.id_Parking;




END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Listage de la structure de déclencheur park. after_insert_place
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='';
DELIMITER //
CREATE DEFINER=`root`@`localhost` TRIGGER `after_insert_place` AFTER INSERT ON `place` FOR EACH ROW BEGIN
DECLARE NB INT DEFAULT 0;
SELECT COUNT(*) INTO NB FROM place WHERE id_Parking = NEW.id_Parking;

UPDATE parking SET parking.capacite=nb
WHERE parking.id_parking=NEW.id_Parking;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Listage de la structure de déclencheur park. after_update_place
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='';
DELIMITER //
CREATE DEFINER=`root`@`localhost` TRIGGER `after_update_place` AFTER UPDATE ON `place` FOR EACH ROW BEGIN
DECLARE NB INT DEFAULT 0;
DECLARE nbs INT DEFAULT 0;

SELECT COUNT(*) INTO NB FROM place WHERE id_Parking = old.id_Parking;

UPDATE parking SET parking.capacite=nb
WHERE parking.id_parking=old.id_Parking;



SELECT COUNT(*) INTO nbs FROM place WHERE id_Parking = new.id_Parking;

UPDATE parking SET parking.capacite=nbs
WHERE parking.id_parking=new.id_Parking;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Listage de la structure de la vue park. get_parking_nb_place_not_paye
-- Suppression de la table temporaire et création finale de la structure d'une vue
DROP TABLE IF EXISTS `get_parking_nb_place_not_paye`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `get_parking_nb_place_not_paye` AS select `parking`.`id_parking` AS `id_parking`,`parking`.`adresse` AS `adresse`,count(0) AS `nombre` from ((`occupation` join `place` on((`occupation`.`id_place` = `place`.`id_Place`))) join `parking` on((`parking`.`id_parking` = `place`.`id_Parking`))) where (`occupation`.`date_fin` is null) group by `parking`.`id_parking`;

-- Listage de la structure de la vue park. get_parking_nb_place_used
-- Suppression de la table temporaire et création finale de la structure d'une vue
DROP TABLE IF EXISTS `get_parking_nb_place_used`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `get_parking_nb_place_used` AS select `parking`.`id_parking` AS `id_parking`,`parking`.`adresse` AS `adresse`,`parking`.`capacite` AS `capacite`,count(0) AS `nombre_place` from (`parking` join `place` on((`place`.`id_Parking` = `parking`.`id_parking`))) group by `parking`.`id_parking`;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
