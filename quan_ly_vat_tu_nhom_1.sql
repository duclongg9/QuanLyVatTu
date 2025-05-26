-- Tạo database
CREATE DATABASE IF NOT EXISTS ql_vattu CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ql_vattu;

-- Bảng Role
CREATE TABLE Role (
  id INT NOT NULL AUTO_INCREMENT,
  Role_Name VARCHAR(255) NOT NULL,
  Decrition VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
) COMMENT='Quyền trên trang WEB';

-- Bảng Group
CREATE TABLE `Group` (
  id INT NOT NULL AUTO_INCREMENT,
  url TEXT NOT NULL,
  Decription TEXT NOT NULL,
  PRIMARY KEY (id)
);

-- Bảng Group_Role
CREATE TABLE Group_Role (
  id INT NOT NULL AUTO_INCREMENT,
  Group_Id INT,
  Role_Id INT,
  Status TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  CONSTRAINT fk_GroupRole_Group FOREIGN KEY (Group_Id) REFERENCES `Group`(id),
  CONSTRAINT fk_GroupRole_Role FOREIGN KEY (Role_Id) REFERENCES Role(id)
);

-- Bảng Status (Trạng thái vật tư)
CREATE TABLE Status (
  id INT NOT NULL AUTO_INCREMENT,
  status_Name VARCHAR(255),
  PRIMARY KEY (id)
);

-- Bảng Unit (Đơn vị tính)
CREATE TABLE Unit (
  id INT NOT NULL AUTO_INCREMENT,
  Unit_name VARCHAR(255),
  PRIMARY KEY (id)
);

-- Bảng Materials_type (Loại vật tư)
CREATE TABLE Materials_type (
  id INT NOT NULL AUTO_INCREMENT,
  Type_name VARCHAR(255),
  PRIMARY KEY (id)
);

-- Bảng Supplies (Nhà cung cấp)
CREATE TABLE Supplies (
  id INT NOT NULL AUTO_INCREMENT,
  Name VARCHAR(255) NOT NULL,
  Address VARCHAR(255) NOT NULL,
  Phone VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

-- Bảng Materials (Vật tư)
CREATE TABLE Materials (
  id INT NOT NULL AUTO_INCREMENT,
  Material_name VARCHAR(255) NOT NULL,
  Price VARCHAR(255) NOT NULL,
  Decription VARCHAR(255) NOT NULL,
  Status INT NOT NULL,
  Type_Id INT NOT NULL,
  Unit_Id INT NOT NULL,
  Supplies_Id INT,
  Number DOUBLE,
  active TINYINT(1) NOT NULL DEFAULT 1 COMMENT '0=inactive, 1=active',
  PRIMARY KEY (id),
  CONSTRAINT fk_Materials_Status FOREIGN KEY (Status) REFERENCES Status(id),
  CONSTRAINT fk_Materials_Type FOREIGN KEY (Type_Id) REFERENCES Materials_type(id),
  CONSTRAINT fk_Materials_Unit FOREIGN KEY (Unit_Id) REFERENCES Unit(id),
  CONSTRAINT fk_Materials_Supplies FOREIGN KEY (Supplies_Id) REFERENCES Supplies(id)
);

-- Bảng Staff (Nhân viên)
CREATE TABLE Staff (
  id INT NOT NULL AUTO_INCREMENT,
  Name VARCHAR(255),
  Sex TINYINT(1) NOT NULL,
  Birth DATE NOT NULL,
  Address VARCHAR(255) NOT NULL,
  Image VARCHAR(255),
  Email VARCHAR(255) NOT NULL,
  Account VARCHAR(255) NOT NULL DEFAULT 'Account',
  Password VARCHAR(255) NOT NULL,
  Role_Id INT NOT NULL,
  Status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '0=inactive, 1=active',
  PRIMARY KEY (id),
  CONSTRAINT fk_Staff_Role FOREIGN KEY (Role_Id) REFERENCES Role(id)
);

-- Bảng WarehouseTransaction (Phiếu xuất nhập kho)
CREATE TABLE WarehouseTransaction (
  id INT NOT NULL AUTO_INCREMENT,
  Date DATETIME DEFAULT CURRENT_TIMESTAMP,
  Reason VARCHAR(255),
  Staff_ID INT,
  proposed_by INT NOT NULL,
  approved_by INT DEFAULT NULL,
  received_by INT DEFAULT NULL,
  proposed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  approved_at DATETIME DEFAULT NULL,
  received_at DATETIME DEFAULT NULL,
  export_status TINYINT(1) NOT NULL DEFAULT 0 COMMENT '0=Xuất đủ, 1=Xuất thiếu',
  approval_status TINYINT(1) NOT NULL DEFAULT 0 COMMENT '0=Chưa phê duyệt,1=Đã phê duyệt,2=Từ chối',
  PRIMARY KEY (id),
  CONSTRAINT fk_WarehouseTransaction_Staff FOREIGN KEY (Staff_ID) REFERENCES Staff(id),
  CONSTRAINT fk_WarehouseTransaction_ProposedBy FOREIGN KEY (proposed_by) REFERENCES Staff(id),
  CONSTRAINT fk_WarehouseTransaction_ApprovedBy FOREIGN KEY (approved_by) REFERENCES Staff(id),
  CONSTRAINT fk_WarehouseTransaction_ReceivedBy FOREIGN KEY (received_by) REFERENCES Staff(id)
) COMMENT='Dùng để lưu đơn xuất nhập';

-- Bảng TransactionDetail (Chi tiết phiếu xuất nhập)
CREATE TABLE TransactionDetail (
  id INT NOT NULL AUTO_INCREMENT,
  Material_Id INT,
  WTransaction_Id INT,
  PRIMARY KEY (id),
  CONSTRAINT fk_TransactionDetail_Material FOREIGN KEY (Material_Id) REFERENCES Materials(id),
  CONSTRAINT fk_TransactionDetail_WTransaction FOREIGN KEY (WTransaction_Id) REFERENCES WarehouseTransaction(id)
);

-- Bảng Type_Request (Loại yêu cầu)
CREATE TABLE Type_Request (
  id INT NOT NULL AUTO_INCREMENT,
  Name VARCHAR(255),
  PRIMARY KEY (id)
);

-- Bảng Request (Yêu cầu)
CREATE TABLE Request (
  id INT NOT NULL AUTO_INCREMENT,
  Date DATETIME NOT NULL,
  Decription VARCHAR(255) NOT NULL,
  Type_id INT NOT NULL,
  Staff_Id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_Request_Type FOREIGN KEY (Type_id) REFERENCES Type_Request(id),
  CONSTRAINT fk_Request_Staff FOREIGN KEY (Staff_Id) REFERENCES Staff(id)
);

ALTER TABLE TransactionDetail
ADD COLUMN Quantity INT NOT NULL DEFAULT 1 COMMENT 'Số lượng vật tư trong chi tiết';


ALTER TABLE Request
ADD COLUMN WarehouseTransaction_Id INT NULL,
ADD CONSTRAINT fk_Request_WarehouseTransaction FOREIGN KEY (WarehouseTransaction_Id) REFERENCES WarehouseTransaction(id);
