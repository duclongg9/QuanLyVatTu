-- ====================================================================
-- Phần 0: Tạo và Sử dụng Database
-- (Bạn nên chạy 3 dòng này đầu tiên để đảm bảo database sạch và đúng)
-- ====================================================================
DROP DATABASE IF EXISTS ql_vat_tu;
CREATE DATABASE ql_vat_tu CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ql_vat_tu;

-- Table: CategoryMaterial
CREATE TABLE `CategoryMaterial` (
	 `id` INTEGER AUTO_INCREMENT,
        `category` VARCHAR(255) NOT NULL,
        `status` BOOLEAN DEFAULT true,
        PRIMARY KEY(`id`)
);

-- Table: SubCategory
CREATE TABLE `SubCategory` (
	`id` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
        `subCategoryName` VARCHAR(255),
        `categoryMaterialId` INTEGER,
        `status` BOOLEAN DEFAULT true,
        PRIMARY KEY(`id`),
        FOREIGN KEY (`categoryMaterialId`) REFERENCES `CategoryMaterial`(`id`)
);

-- Table: MaterialStatus
CREATE TABLE `MaterialStatus` (
	`id` INTEGER AUTO_INCREMENT,
	`status` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`id`)
);

-- Table: RequestStatus
CREATE TABLE `RequestStatus` (
	`id` INTEGER AUTO_INCREMENT,
	`status` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`id`)
);

-- Table: Role
CREATE TABLE `Role` (
	`id` INTEGER AUTO_INCREMENT,
	`role` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`id`)
);

-- Table: Permission
CREATE TABLE `Permission` (
	`id` INTEGER AUTO_INCREMENT,
	`url` VARCHAR(255) NOT NULL,
	`description` VARCHAR(255),
	`status` BOOLEAN NOT NULL DEFAULT TRUE,
	PRIMARY KEY(`id`)
);

-- Table: MaterialUnit
CREATE TABLE `MaterialUnit` (
	`id` INTEGER AUTO_INCREMENT,
	`unit` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`id`)
);

-- Table: User
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

-- Table: Permission_Role
CREATE TABLE `Permission_Role` (
	`id` INTEGER AUTO_INCREMENT,
	`permissionId` INTEGER NOT NULL,
	`roleId` INTEGER NOT NULL,
	`status` BOOLEAN NOT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`permissionId`) REFERENCES `Permission`(`id`),
	FOREIGN KEY(`roleId`) REFERENCES `Role`(`id`)
);

-- Table: Materials
CREATE TABLE `Materials` (
	`id` INTEGER AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`unitId` INTEGER NOT NULL,
	`image` VARCHAR(255),
	`subCategoryId` INTEGER NOT NULL,
	`status` BOOLEAN DEFAULT true,
    `createdAt` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`updatedAt` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`replacementMaterialId` INT,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`unitId`) REFERENCES `MaterialUnit`(`id`),
	FOREIGN KEY(`subCategoryId`) REFERENCES `SubCategory`(`id`),
    FOREIGN KEY (`replacementMaterialId`) REFERENCES `Materials` (`id`)
);

-- Table: Supplier
CREATE TABLE `Supplier` (
	`id` INTEGER AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`phone` VARCHAR(255) NOT NULL,
	`address` VARCHAR(255) NOT NULL,
    `status` Boolean NOT NULL DEFAULT TRUE,
	PRIMARY KEY(`id`)
);

-- Table: materials_Supplier
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

-- Table: MaterialItem
CREATE TABLE `MaterialItem` (
	`id` INTEGER AUTO_INCREMENT,
	`statusId` INTEGER NOT NULL,
	`quantity` INTEGER NOT NULL DEFAULT 0,
	`materials_SupplierId` INTEGER,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`statusId`) REFERENCES `MaterialStatus`(`id`),
	FOREIGN KEY(`materials_SupplierId`) REFERENCES `materials_Supplier`(`id`)
);

-- Table: Request
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

-- Table: requestDetail
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

-- Table: InputWarehouse
CREATE TABLE `InputWarehouse` (
	`id` INTEGER AUTO_INCREMENT,
	`dateInput` DATE NOT NULL,
	`userId` INTEGER,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`userId`) REFERENCES `User`(`id`)
);

-- Table: OutputWarehouse
CREATE TABLE `OutputWarehouse` (
	`id` INTEGER AUTO_INCREMENT,
	`date` DATE NOT NULL,
	`userId` INTEGER,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`userId`) REFERENCES `User`(`id`)
);

-- Table: InputDetail
CREATE TABLE `InputDetail` (
	`id` INTEGER AUTO_INCREMENT,
	`quantity` INTEGER NOT NULL,
	`inputWarehouseId` INTEGER,
	`requestDetailId` INTEGER,
	`materialItemId` INTEGER,
	`inputPrice` DOUBLE,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`inputWarehouseId`) REFERENCES `InputWarehouse`(`id`),
	FOREIGN KEY(`requestDetailId`) REFERENCES `requestDetail`(`id`)
);

-- Table: OutputDetail
CREATE TABLE `OutputDetail` (
	`id` INTEGER AUTO_INCREMENT,
	`quantity` INTEGER NOT NULL,
	`outputWarehouseId` INTEGER,
	`requestDetailId` INTEGER,
	`materialItemId` INTEGER,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`outputWarehouseId`) REFERENCES `OutputWarehouse`(`id`),
	FOREIGN KEY(`requestDetailId`) REFERENCES `requestDetail`(`id`)
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