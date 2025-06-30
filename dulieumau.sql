-- Loại vật tư
INSERT INTO CategoryMaterial (category) VALUES 
('Xi măng'), ('Sắt thép'), ('Gạch'), ('Cát Đá');

-- Trạng thái vật tư
INSERT INTO MaterialStatus (status) VALUES 
('Mới'), ('Đang sử dụng'), ('Hư hỏng');

-- Trạng thái yêu cầu
INSERT INTO RequestStatus (status) VALUES 
('Chờ duyệt'), ('Đã duyệt'), ('Từ chối');

-- Đơn vị tính
INSERT INTO MaterialUnit (unit) VALUES 
('Bao'), ('Kg'), ('Viên'), ('Khối');

-- Vai trò
INSERT INTO Role (role) VALUES 
('Admin'), ('Nhân viên kho');

-- User
INSERT INTO User (username, fullname, phone, password, email, address, roleId) VALUES
('admin', 'Nguyễn Quản Trị', '0909000000', '123456', 'admin@kho.com', 'Hà Nội', 1),
('kho1', 'Trần Thị Kho', '0911000000', '123456', 'kho1@kho.com', 'TP HCM', 2);

-- Vật tư xây dựng
INSERT INTO Materials (name, unitId, categoryId) VALUES
('Xi măng Hà Tiên', 1, 1),
('Thép Việt Nhật D10', 2, 2),
('Gạch Tuynel Bình Dương', 3, 3),
('Cát Vàng Đồng Nai', 4, 4);

-- Nhà cung cấp
INSERT INTO Supplier (name, phone, address) VALUES
('Công ty VLXD Hà Tiên', '0933111000', 'Quận 9, TP HCM'),
('Công ty Thép Việt Nhật', '0944222000', 'Quận 2, TP HCM'),
('Công ty Gạch Bình Dương', '0955333000', 'Thủ Dầu Một, Bình Dương'),
('Công ty Cát Đồng Nai', '0966444000', 'Biên Hòa, Đồng Nai');

-- Liên kết vật tư - nhà cung cấp
INSERT INTO materials_Supplier (materialId, supplierId, note) VALUES
(1, 1, 'Xi măng chất lượng cao'),
(2, 2, 'Thép phi D10'),
(3, 3, 'Gạch đỏ chất lượng'),
(4, 4, 'Cát vàng xây dựng');

-- Tồn kho ban đầu
INSERT INTO MaterialItem (materialId, supplierId, statusId, quantity) VALUES
(1, 1, 1, 500),
(2, 2, 1, 3000),
(3, 3, 1, 10000),
(4, 4, 1, 200);

-- Yêu cầu nhập kho
INSERT INTO Request (date, statusId, userId, type) VALUES
('2025-06-22', 1, 2, 'Import');

INSERT INTO requestDetail (requestId, materialId, supplierId, quantity, note) VALUES
(1, 1, 1, 100, 'Nhập 100 bao Xi măng Hà Tiên'),
(1, 2, 2, 500, 'Nhập 500 Kg Thép Việt Nhật');

-- Nhập kho
INSERT INTO InputWarehouse (dateInput, userId) VALUES
('2025-06-23', 2);

INSERT INTO InputDetail (quantity, inputWarehouseId, requestDetailId, materialId, supplierId, inputPrice) VALUES
(100, 1, 1, 1, 1, 75000),
(500, 1, 2, 2, 2, 18000);
