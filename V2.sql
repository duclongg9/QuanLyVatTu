
-- Bảng danh mục loại vật tư
CREATE TABLE CategoryMaterial (
	id INT AUTO_INCREMENT PRIMARY KEY,
	category VARCHAR(255) NOT NULL
);

-- Bảng trạng thái vật tư
CREATE TABLE MaterialStatus (
	id INT AUTO_INCREMENT PRIMARY KEY,
	status VARCHAR(255) NOT NULL
);

-- Bảng trạng thái yêu cầu
CREATE TABLE RequestStatus (
	id INT AUTO_INCREMENT PRIMARY KEY,
	status VARCHAR(255) NOT NULL
);

-- Bảng vai trò
CREATE TABLE Role (
	id INT AUTO_INCREMENT PRIMARY KEY,
	role VARCHAR(255) NOT NULL
);

-- Bảng quyền
CREATE TABLE Permission (
	id INT AUTO_INCREMENT PRIMARY KEY,
	url VARCHAR(255) NOT NULL,
	status BOOLEAN NOT NULL
);

-- Bảng đơn vị tính
CREATE TABLE MaterialUnit (
	id INT AUTO_INCREMENT PRIMARY KEY,
	unit VARCHAR(255) NOT NULL
);

-- Bảng người dùng
CREATE TABLE User (
	id INT AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(255) NOT NULL,
	fullname VARCHAR(255) NOT NULL,
	phone VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	address VARCHAR(255) NOT NULL,
	gender BOOLEAN,
	birthDate DATE,
	image VARCHAR(255),
	status BOOLEAN DEFAULT TRUE,
	roleId INT NOT NULL,
	FOREIGN KEY (roleId) REFERENCES Role(id)
);

-- Bảng liên kết quyền - vai trò
CREATE TABLE Permission_Role (
	id INT AUTO_INCREMENT PRIMARY KEY,
	permissionId INT NOT NULL,
	roleId INT NOT NULL,
	FOREIGN KEY (permissionId) REFERENCES Permission(id),
	FOREIGN KEY (roleId) REFERENCES Role(id)
);

-- Bảng vật tư
CREATE TABLE Materials (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	unitId INT NOT NULL,
	image VARCHAR(255),
	categoryId INT NOT NULL,
	status BOOLEAN DEFAULT TRUE,
	FOREIGN KEY (unitId) REFERENCES MaterialUnit(id),
	FOREIGN KEY (categoryId) REFERENCES CategoryMaterial(id)
);

-- Bảng nhà cung cấp
CREATE TABLE Supplier (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	phone VARCHAR(255) NOT NULL,
	address VARCHAR(255) NOT NULL
);

-- Bảng liên kết vật tư - nhà cung cấp
CREATE TABLE materials_Supplier (
	id INT AUTO_INCREMENT PRIMARY KEY,
	materialId INT NOT NULL,
	supplierId INT NOT NULL,
	note VARCHAR(255),
	FOREIGN KEY (materialId) REFERENCES Materials(id),
	FOREIGN KEY (supplierId) REFERENCES Supplier(id)
);

-- Bảng tồn kho vật tư cụ thể theo nhà cung cấp (lưu cứng)
CREATE TABLE MaterialItem (
	id INT AUTO_INCREMENT PRIMARY KEY,
	materialId INT NOT NULL,
	supplierId INT NOT NULL,
	statusId INT NOT NULL,
	quantity INT NOT NULL DEFAULT 0,
	FOREIGN KEY (materialId) REFERENCES Materials(id),
	FOREIGN KEY (supplierId) REFERENCES Supplier(id),
	FOREIGN KEY (statusId) REFERENCES MaterialStatus(id)
);

-- Bảng yêu cầu nhập/xuất kho
CREATE TABLE Request (
	id INT AUTO_INCREMENT PRIMARY KEY,
	date DATE,
	statusId INT,
	userId INT,
	note VARCHAR(255),
	type ENUM('Import', 'Export', 'Repair') NOT NULL,
	approvedBy INT,
	FOREIGN KEY (statusId) REFERENCES RequestStatus(id),
	FOREIGN KEY (userId) REFERENCES User(id),
	FOREIGN KEY (approvedBy) REFERENCES User(id)
);

-- Bảng chi tiết yêu cầu nhập/xuất kho (có nhà cung cấp)
CREATE TABLE requestDetail (
	id INT AUTO_INCREMENT PRIMARY KEY,
	requestId INT NOT NULL,
	materialId INT NOT NULL,
	supplierId INT NOT NULL,
	quantity INT NOT NULL,
	note VARCHAR(255),
	FOREIGN KEY (requestId) REFERENCES Request(id),
	FOREIGN KEY (materialId) REFERENCES Materials(id),
	FOREIGN KEY (supplierId) REFERENCES Supplier(id)
);

-- Bảng phiếu nhập kho
CREATE TABLE InputWarehouse (
	id INT AUTO_INCREMENT PRIMARY KEY,
	dateInput DATE NOT NULL,
	userId INT,
	FOREIGN KEY (userId) REFERENCES User(id)
);

-- Bảng phiếu xuất kho
CREATE TABLE OutputWarehouse (
	id INT AUTO_INCREMENT PRIMARY KEY,
	date DATE NOT NULL,
	userId INT,
	FOREIGN KEY (userId) REFERENCES User(id)
);

-- Bảng chi tiết nhập kho (có vật tư & nhà cung cấp)
CREATE TABLE InputDetail (
	id INT AUTO_INCREMENT PRIMARY KEY,
	quantity INT NOT NULL,
	inputWarehouseId INT,
	requestDetailId INT,
	materialId INT NOT NULL,
	supplierId INT NOT NULL,
	inputPrice DOUBLE,
	FOREIGN KEY (inputWarehouseId) REFERENCES InputWarehouse(id),
	FOREIGN KEY (requestDetailId) REFERENCES requestDetail(id),
	FOREIGN KEY (materialId) REFERENCES Materials(id),
	FOREIGN KEY (supplierId) REFERENCES Supplier(id)
);

-- Bảng chi tiết xuất kho (có vật tư & nhà cung cấp)
CREATE TABLE OutputDetail (
	id INT AUTO_INCREMENT PRIMARY KEY,
	quantity INT NOT NULL,
	outputWarehouseId INT,
	requestDetailId INT,
	materialId INT NOT NULL,
	supplierId INT NOT NULL,
	FOREIGN KEY (outputWarehouseId) REFERENCES OutputWarehouse(id),
	FOREIGN KEY (requestDetailId) REFERENCES requestDetail(id),
	FOREIGN KEY (materialId) REFERENCES Materials(id),
	FOREIGN KEY (supplierId) REFERENCES Supplier(id)
);
