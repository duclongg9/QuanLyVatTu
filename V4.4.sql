-- ================================
-- PHẦN 0: TẠO VÀ SỬ DỤNG DATABASE
-- ================================
DROP DATABASE IF EXISTS ql_vat_tu;
CREATE DATABASE ql_vat_tu CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ql_vat_tu;

-- ================================
-- DANH MỤC DANH SÁCH & THAM CHIẾU
-- ================================

-- Danh mục cha - con
CREATE TABLE `Category` (
	`id` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`categoryName` VARCHAR(255),
	`parentCateId` INTEGER,
	`status` BOOLEAN DEFAULT true,
	PRIMARY KEY(`id`)
);

-- Trạng thái vật tư
CREATE TABLE `MaterialStatus` (
	`id` INTEGER AUTO_INCREMENT,
	`status` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`id`)
);

-- Trạng thái yêu cầu
CREATE TABLE `RequestStatus` (
	`id` INTEGER AUTO_INCREMENT,
	`status` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`id`)
);

-- Vai trò
CREATE TABLE `Role` (
	`id` INTEGER AUTO_INCREMENT,
	`role` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`id`)
);

-- Quyền
CREATE TABLE `Permission` (
	`id` INTEGER AUTO_INCREMENT,
	`url` VARCHAR(255) NOT NULL,
	`description` VARCHAR(255),
	`status` BOOLEAN NOT NULL DEFAULT true,
	PRIMARY KEY(`id`)
);

-- Đơn vị vật tư
CREATE TABLE `MaterialUnit` (
	`id` INTEGER AUTO_INCREMENT,
	`unit` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`id`)
);

-- Người dùng
CREATE TABLE `User` (
	`id` INTEGER AUTO_INCREMENT,
	`username` VARCHAR(255) NOT NULL,
	`fullname` VARCHAR(255) NOT NULL,
	`phone` VARCHAR(255) NOT NULL,
	`password` VARCHAR(255) NOT NULL,
	`email` VARCHAR(255) NOT NULL,
	`address` VARCHAR(255) NOT NULL,
	`gender` BOOLEAN,
	`birthDate` DATE,
	`image` VARCHAR(255),
	`status` BOOLEAN DEFAULT true,
	`roleId` INTEGER NOT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`roleId`) REFERENCES `Role`(`id`)
);

-- Phân quyền
CREATE TABLE `Permission_Role` (
	`id` INTEGER AUTO_INCREMENT,
	`permissionId` INTEGER NOT NULL,
	`roleId` INTEGER NOT NULL,
	`status` BOOLEAN NOT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`permissionId`) REFERENCES `Permission`(`id`),
	FOREIGN KEY(`roleId`) REFERENCES `Role`(`id`)
);

-- Vật tư
CREATE TABLE `Materials` (
	`id` INTEGER AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`unitId` INTEGER NOT NULL,
	`image` VARCHAR(255),
	`category` INTEGER NOT NULL, -- Dùng bảng Category mới
	`status` BOOLEAN DEFAULT true,
	`createdAt` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`updatedAt` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`replacementMaterialId` INTEGER,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`unitId`) REFERENCES `MaterialUnit`(`id`),
	FOREIGN KEY(`category`) REFERENCES `Category`(`id`),
	FOREIGN KEY(`replacementMaterialId`) REFERENCES `Materials`(`id`)
);

-- Nhà cung cấp
CREATE TABLE `Supplier` (
	`id` INTEGER AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`phone` VARCHAR(255) NOT NULL,
	`address` VARCHAR(255) NOT NULL,
	`status` BOOLEAN NOT NULL DEFAULT true,
	PRIMARY KEY(`id`)
);

-- Giao vật tư - nhà cung cấp
CREATE TABLE `materials_Supplier` (
	`id` INTEGER AUTO_INCREMENT,
	`materialId` INTEGER NOT NULL,
	`supplierId` INTEGER NOT NULL,
	`note` VARCHAR(255),
	`price` DOUBLE,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`materialId`) REFERENCES `Materials`(`id`),
	FOREIGN KEY(`supplierId`) REFERENCES `Supplier`(`id`)
);

-- Vật tư chi tiết (quản lý theo trạng thái + NCC)
CREATE TABLE `MaterialItem` (
	`id` INTEGER AUTO_INCREMENT,
	`statusId` INTEGER NOT NULL,
	`quantity` INTEGER NOT NULL DEFAULT 0,
	`materials_SupplierId` INTEGER,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`statusId`) REFERENCES `MaterialStatus`(`id`),
	FOREIGN KEY(`materials_SupplierId`) REFERENCES `materials_Supplier`(`id`)
);

-- Yêu cầu nhập/xuất
CREATE TABLE `Request` (
	`id` INTEGER AUTO_INCREMENT,
	`date` DATE,
	`statusId` INTEGER,
	`userId` INTEGER,
	`note` VARCHAR(255),
	`type` ENUM('IMPORT', 'EXPORT', 'REPAIR', 'PURCHASE') NOT NULL,
	`approvedBy` INTEGER,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`statusId`) REFERENCES `RequestStatus`(`id`),
	FOREIGN KEY(`userId`) REFERENCES `User`(`id`),
	FOREIGN KEY(`approvedBy`) REFERENCES `User`(`id`)
);

-- Chi tiết yêu cầu
CREATE TABLE `requestDetail` (
	`id` INTEGER AUTO_INCREMENT,
	`requestId` INTEGER NOT NULL,
	`quantity` INTEGER NOT NULL,
	`note` VARCHAR(255),
	`materialItemId` INTEGER,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`requestId`) REFERENCES `Request`(`id`),
	FOREIGN KEY(`materialItemId`) REFERENCES `MaterialItem`(`id`)
);

-- Phiếu nhập kho
CREATE TABLE `InputWarehouse` (
	`id` INTEGER AUTO_INCREMENT,
	`dateInput` DATE NOT NULL,
	`userId` INTEGER,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`userId`) REFERENCES `User`(`id`)
);

-- Phiếu xuất kho
CREATE TABLE `OutputWarehouse` (
	`id` INTEGER AUTO_INCREMENT,
	`date` DATE NOT NULL,
	`userId` INTEGER,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`userId`) REFERENCES `User`(`id`)
);

-- Chi tiết nhập
CREATE TABLE `InputDetail` (
	`id` INTEGER AUTO_INCREMENT,
	`quantity` INTEGER NOT NULL,
	`inputWarehouseId` INTEGER,
	`requestDetailId` INTEGER,
	`materialItemId` INTEGER,
	`inputPrice` DOUBLE,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`inputWarehouseId`) REFERENCES `InputWarehouse`(`id`),
	FOREIGN KEY(`requestDetailId`) REFERENCES `requestDetail`(`id`),
	FOREIGN KEY(`materialItemId`) REFERENCES `MaterialItem`(`id`)
);

-- Chi tiết xuất
CREATE TABLE `OutputDetail` (
	`id` INTEGER AUTO_INCREMENT,
	`quantity` INTEGER NOT NULL,
	`outputWarehouseId` INTEGER,
	`requestDetailId` INTEGER,
	`materialItemId` INTEGER,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`outputWarehouseId`) REFERENCES `OutputWarehouse`(`id`),
	FOREIGN KEY(`requestDetailId`) REFERENCES `requestDetail`(`id`),
	FOREIGN KEY(`materialItemId`) REFERENCES `MaterialItem`(`id`)
);


-- create user 
DELIMITER //

CREATE PROCEDURE CreateNewUser (
    IN p_username VARCHAR(255),
    IN p_fullname VARCHAR(255),
    IN p_phone VARCHAR(255),
    IN p_password VARCHAR(255),
    IN p_email VARCHAR(255),
    IN p_address VARCHAR(255),
    IN p_gender boolean,
    IN p_birthDate DATE,
    IN p_image VARCHAR(255),
    IN p_status BOOLEAN,
    IN p_roleId INTEGER
)
BEGIN
    INSERT INTO `User` (
        username, fullname, phone, password, email,
        address, gender, birthDate, image, status, roleId
    )
    VALUES (
        p_username, p_fullname, p_phone, p_password, p_email,
        p_address, p_gender, p_birthDate, p_image, p_status, p_roleId
    );
END //

DELIMITER ;

-- CALL CreateNewUser(?,?,?,?,?,?,?,?,?,?,?);
-- DROP PROCEDURE IF EXISTS CreateNewUser;