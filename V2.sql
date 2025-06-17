-- Bảng danh mục loại vật tư
CREATE TABLE `CategoryMaterial` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`category` VARCHAR(255) NOT NULL
);

-- Bảng trạng thái vật tư
CREATE TABLE `MaterialStatus` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`status` VARCHAR(255) NOT NULL
);

-- Bảng trạng thái yêu cầu nhập/xuất
CREATE TABLE `RequestStatus` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`status` VARCHAR(255) NOT NULL
);

-- Bảng vai trò người dùng
CREATE TABLE `Role` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`role` VARCHAR(255) NOT NULL
);

-- Bảng quyền
CREATE TABLE `Permission` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`url` VARCHAR(255) NOT NULL,
	`status` BOOLEAN NOT NULL
);

-- Bảng người dùng
CREATE TABLE `User` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`username` VARCHAR(255) NOT NULL,
	`fullname` VARCHAR(255) NOT NULL,
	`phone` VARCHAR(255) NOT NULL,
	`password` VARCHAR(255) NOT NULL,
	`email` VARCHAR(255) NOT NULL,
	`address` VARCHAR(255) NOT NULL,
	`gender` BOOLEAN,
	`birthDate` DATE,
	`image` VARCHAR(255),
	`status` BOOLEAN DEFAULT TRUE,
	`roleId` INT NOT NULL
);

-- Bảng liên kết quyền - vai trò
CREATE TABLE `Permission_Role` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`permissionId` INT NOT NULL,
	`roleId` INT NOT NULL
);

-- Bảng đơn vị tính
CREATE TABLE `MaterialUnit` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`unit` VARCHAR(255) NOT NULL
);

-- Bảng vật tư
CREATE TABLE `Materials` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`name` VARCHAR(255) NOT NULL,
	`unitId` INT NOT NULL,
	`image` VARCHAR(255),
	`categoryId` INT NOT NULL
);

-- Bảng nhà cung cấp
CREATE TABLE `Supplier` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`name` VARCHAR(255) NOT NULL,
	`phone` VARCHAR(255) NOT NULL,
	`address` VARCHAR(255) NOT NULL
);

-- Bảng liên kết vật tư - nhà cung cấp
CREATE TABLE `materials_Supplier` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`materialId` INT NOT NULL,
	`supplierId` INT NOT NULL,
	`note` VARCHAR(255)
);

-- Bảng nhập kho
CREATE TABLE `InputWarehouse` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`dateInput` DATE NOT NULL,
	`userId` INT
);

-- Bảng xuất kho
CREATE TABLE `OutputWarehouse` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`date` DATE NOT NULL,
	`userId` INT
);

-- Bảng chi tiết nhập kho
CREATE TABLE `InputDetail` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`quantity` INT NOT NULL,
	`inputWarehouseId` INT,
	`requestDetailId` INT,
	`inputPrice` DOUBLE
);

-- Bảng chi tiết xuất kho
CREATE TABLE `OutputDetail` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`quantity` INT NOT NULL,
	`requestDetailId` INT,
	`outputWarehouseId` INT
);

-- Bảng yêu cầu nhập/xuất kho
CREATE TABLE `Request` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`date` DATE,
	`statusId` INT,
	`userId` INT,
	`note` VARCHAR(255),
	`type` ENUM('Import', 'Export','Repair') NOT NULL,
	`approvedBy` INT
);

-- Bảng chi tiết yêu cầu nhập/xuất kho
CREATE TABLE `requestDetail` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`requestId` INT NOT NULL,
	`materialId` INT NOT NULL,
	`quantity` INT NOT NULL,
	`note` VARCHAR(255) NOT NULL
);

-- Bảng lưu tình trạng & số lượng từng trạng thái của vật tư
CREATE TABLE `MaterialItem` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`materialId` INT NOT NULL,
	`statusId` INT NOT NULL,
	`quantity` INT NOT NULL DEFAULT 0
);
 
 
 -- User
ALTER TABLE `User` 
ADD CONSTRAINT `fk_user_role` FOREIGN KEY (`roleId`) REFERENCES `Role`(`id`);

-- Permission_Role
ALTER TABLE `Permission_Role` 
ADD CONSTRAINT `fk_permrole_permission` FOREIGN KEY (`permissionId`) REFERENCES `Permission`(`id`),
ADD CONSTRAINT `fk_permrole_role` FOREIGN KEY (`roleId`) REFERENCES `Role`(`id`);

-- Materials
ALTER TABLE `Materials` 
ADD CONSTRAINT `fk_materials_unit` FOREIGN KEY (`unitId`) REFERENCES `MaterialUnit`(`id`),
ADD CONSTRAINT `fk_materials_category` FOREIGN KEY (`categoryId`) REFERENCES `CategoryMaterial`(`id`);

-- materials_Supplier
ALTER TABLE `materials_Supplier` 
ADD CONSTRAINT `fk_matsupp_material` FOREIGN KEY (`materialId`) REFERENCES `Materials`(`id`),
ADD CONSTRAINT `fk_matsupp_supplier` FOREIGN KEY (`supplierId`) REFERENCES `Supplier`(`id`);

-- InputWarehouse, OutputWarehouse
ALTER TABLE `InputWarehouse` 
ADD CONSTRAINT `fk_inputwh_user` FOREIGN KEY (`userId`) REFERENCES `User`(`id`);

ALTER TABLE `OutputWarehouse` 
ADD CONSTRAINT `fk_outputwh_user` FOREIGN KEY (`userId`) REFERENCES `User`(`id`);

-- InputDetail, OutputDetail
ALTER TABLE `InputDetail` 
ADD CONSTRAINT `fk_inputdetail_inputwh` FOREIGN KEY (`inputWarehouseId`) REFERENCES `InputWarehouse`(`id`),
ADD CONSTRAINT `fk_inputdetail_requestdetail` FOREIGN KEY (`requestDetailId`) REFERENCES `requestDetail`(`id`);

ALTER TABLE `OutputDetail` 
ADD CONSTRAINT `fk_outputdetail_outputwh` FOREIGN KEY (`outputWarehouseId`) REFERENCES `OutputWarehouse`(`id`),
ADD CONSTRAINT `fk_outputdetail_requestdetail` FOREIGN KEY (`requestDetailId`) REFERENCES `requestDetail`(`id`);

-- Request
ALTER TABLE `Request` 
ADD CONSTRAINT `fk_request_status` FOREIGN KEY (`statusId`) REFERENCES `RequestStatus`(`id`),
ADD CONSTRAINT `fk_request_user` FOREIGN KEY (`userId`) REFERENCES `User`(`id`),
ADD CONSTRAINT `fk_request_approvedby` FOREIGN KEY (`approvedBy`) REFERENCES `User`(`id`);

-- requestDetail
ALTER TABLE `requestDetail` 
ADD CONSTRAINT `fk_requestdetail_request` FOREIGN KEY (`requestId`) REFERENCES `Request`(`id`),
ADD CONSTRAINT `fk_requestdetail_material` FOREIGN KEY (`materialId`) REFERENCES `Materials`(`id`);

-- MaterialItem
ALTER TABLE `MaterialItem` 
ADD CONSTRAINT `fk_materialitem_material` FOREIGN KEY (`materialId`) REFERENCES `Materials`(`id`),
ADD CONSTRAINT `fk_materialitem_status` FOREIGN KEY (`statusId`) REFERENCES `MaterialStatus`(`id`);
