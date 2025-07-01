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
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'/userList','Quản lý người dùng',1),(2,'/updateUser','Quản lý vật tư',1),(3,'/resetPassword','Yêu cầu nhập/xuất',1),(4,'/createUser','Báo cáo',1),(5,'/updatesupplier','Cập nhật nhà cung cấp',1),(6,'/deletesupplier','Xóa nhà cung cấp',1),(7,'/addsupplier','Thêm nhà cung cấp',1),(8,'/suppliercontroller','Quản lý nhà cung cấp',1),(9,'/statistic','Thống kê hệ thống',1),(10,'/RequestDetailController','Chi tiết yêu cầu',1),(11,'/updateRequest','Cập nhật yêu cầu',1),(12,'/requestList','Danh sách yêu cầu',1),(13,'/updateprofile','Cập nhật hồ sơ người dùng',1),(14,'/userprofile','Hồ sơ người dùng',1),(15,'/permission','Phân quyền người dùng',1),(16,'/materialController','Quản lý vật tư',1),(17,'/ListImport','Danh sách phiếu nhập',1),(18,'/CreateImport','Tạo phiếu nhập',1),(19,'/CreateRequestImport','Tạo yêu cầu nhập',1),(20,'/categoryController','Tạo mới danh mục cha vật tư',1),(21,'/subCategoryController','Tạo mới danh mục con vật tư',1),(22,'/ListExport','Danh sách xem đơn xuất vật tư',1),(23,'/createExport','Tạo đơn xuất kho',1),(24,'/exportDetail','Xem chi tiết đơn xuất kho',1);
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-30 14:18:19
