CREATE TABLE CategoryMaterial (
	id INT AUTO_INCREMENT PRIMARY KEY,
	category VARCHAR(255)
);

CREATE TABLE Status (
	id INT AUTO_INCREMENT PRIMARY KEY,
	status VARCHAR(255)
);

CREATE TABLE Role (
	id INT AUTO_INCREMENT PRIMARY KEY,
	role VARCHAR(255) NOT NULL
);

CREATE TABLE Permission (
	id INT AUTO_INCREMENT PRIMARY KEY,
	url VARCHAR(255)
);

CREATE TABLE User (
	id INT AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(255) NOT NULL,
	fullname VARCHAR(255) NOT NULL,
	phone VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	address VARCHAR(255) NOT NULL,
	roleId INT NOT NULL,
	FOREIGN KEY (roleId) REFERENCES Role(id)
);

CREATE TABLE Permission_Role (
	id INT AUTO_INCREMENT PRIMARY KEY,
	permissionId INT NOT NULL,
	roleId INT NOT NULL,
	FOREIGN KEY (permissionId) REFERENCES Permission(id),
	FOREIGN KEY (roleId) REFERENCES Role(id)
);

CREATE TABLE Materials (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	unit VARCHAR(255) NOT NULL,
	image VARCHAR(255),
	status VARCHAR(255),  -- nếu dùng bảng Status thì đổi thành statusId INT + FK
	categoryId INT,
	FOREIGN KEY (categoryId) REFERENCES CategoryMaterial(id)
);

CREATE TABLE Supplier (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	phone VARCHAR(255) NOT NULL,
	address VARCHAR(255) NOT NULL
);

CREATE TABLE materials_Supplier (
	id INT AUTO_INCREMENT PRIMARY KEY,
	materialId INT NOT NULL,
	supplierId INT NOT NULL,
	note VARCHAR(255),
	FOREIGN KEY (materialId) REFERENCES Materials(id),
	FOREIGN KEY (supplierId) REFERENCES Supplier(id)
);

CREATE TABLE InputWarehouse (
	id INT AUTO_INCREMENT PRIMARY KEY,
	dateInput DATE NOT NULL
);

CREATE TABLE OutputWarehouse (
	id INT AUTO_INCREMENT PRIMARY KEY,
	date DATE NOT NULL
);

CREATE TABLE InputDetail (
	id INT AUTO_INCREMENT PRIMARY KEY,
	quantity INT NOT NULL,
	inputWarehouseId INT,
	materialId INT,
	inputPrice DOUBLE,
	userId INT,
	FOREIGN KEY (inputWarehouseId) REFERENCES InputWarehouse(id),
	FOREIGN KEY (materialId) REFERENCES Materials(id),
	FOREIGN KEY (userId) REFERENCES User(id)
);

CREATE TABLE OutputDetail (
	id INT AUTO_INCREMENT PRIMARY KEY,
	quantity INT,
	materialId INT,
	outputWarehouseId INT,
	userId INT,
	FOREIGN KEY (outputWarehouseId) REFERENCES OutputWarehouse(id),
	FOREIGN KEY (materialId) REFERENCES Materials(id),
	FOREIGN KEY (userId) REFERENCES User(id)
);

CREATE TABLE Request (
	id INT AUTO_INCREMENT PRIMARY KEY,
	date DATE,
	statusId INT,
	userId INT,
	note VARCHAR(255),
	FOREIGN KEY (statusId) REFERENCES Status(id),
	FOREIGN KEY (userId) REFERENCES User(id)
);

CREATE TABLE requestDetail (
	id INT AUTO_INCREMENT PRIMARY KEY,
	requestId INT NOT NULL,
	materialId INT NOT NULL,
	FOREIGN KEY (requestId) REFERENCES Request(id),
	FOREIGN KEY (materialId) REFERENCES Materials(id)
);
