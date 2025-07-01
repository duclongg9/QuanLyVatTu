CREATE DATABASE  IF NOT EXISTS `ql_vat_tu` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ql_vat_tu`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: ql_vat_tu
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `permission_role`
--

DROP TABLE IF EXISTS `permission_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `permissionId` int NOT NULL,
  `roleId` int NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `permissionId` (`permissionId`),
  KEY `roleId` (`roleId`),
  CONSTRAINT `permission_role_ibfk_1` FOREIGN KEY (`permissionId`) REFERENCES `permission` (`id`),
  CONSTRAINT `permission_role_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission_role`
--

LOCK TABLES `permission_role` WRITE;
/*!40000 ALTER TABLE `permission_role` DISABLE KEYS */;
INSERT INTO `permission_role` VALUES (1,1,1,1),(2,1,2,1),(3,1,3,1),(4,1,4,1),(5,2,1,1),(6,2,2,1),(7,2,3,1),(8,2,4,1),(9,3,1,1),(10,3,2,1),(11,3,3,1),(12,3,4,1),(13,4,1,1),(14,4,2,1),(15,4,3,1),(16,4,4,1),(17,5,1,1),(18,5,2,1),(19,5,3,1),(20,5,4,1),(21,6,1,1),(22,6,2,1),(23,6,3,1),(24,6,4,1),(25,7,1,1),(26,7,2,1),(27,7,3,1),(28,7,4,1),(29,8,1,1),(30,8,2,1),(31,8,3,1),(32,8,4,1),(33,9,1,1),(34,9,2,1),(35,9,3,1),(36,9,4,1),(37,10,1,1),(38,10,2,1),(39,10,3,1),(40,10,4,1),(41,11,1,1),(42,11,2,1),(43,11,3,1),(44,11,4,1),(45,12,1,1),(46,12,2,1),(47,12,3,1),(48,12,4,1),(49,13,1,1),(50,13,2,1),(51,13,3,1),(52,13,4,1),(53,14,1,1),(54,14,2,1),(55,14,3,1),(56,14,4,1),(57,15,1,1),(58,15,2,1),(59,15,3,1),(60,15,4,1),(61,16,1,1),(62,16,2,1),(63,16,3,1),(64,16,4,1),(65,17,1,1),(66,17,2,1),(67,17,3,1),(68,17,4,1),(69,18,1,1),(70,18,2,1),(71,18,3,1),(72,18,4,1),(73,19,1,1),(74,19,2,1),(75,19,3,1),(76,19,4,1),(77,20,1,1),(78,20,2,1),(79,20,3,1),(80,20,4,1),(81,21,1,1),(82,21,2,1),(83,21,3,1),(84,21,4,1),(85,22,1,1),(86,22,2,1),(87,22,3,1),(88,22,4,1),(89,23,1,1),(90,23,2,1),(91,23,3,1),(92,23,4,1),(93,24,1,1),(94,24,2,1),(95,24,3,1),(96,24,4,1);
/*!40000 ALTER TABLE `permission_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-30 14:18:20
