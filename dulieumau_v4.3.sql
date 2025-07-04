
-- ========================================
-- Dữ liệu mẫu cho hệ thống quản lý vật tư
-- ========================================
USE ql_vat_tu;
-- Loại vật tư
INSERT INTO CategoryMaterial (category,status) VALUES 
('Xi măng',1), ('Sắt thép',1), ('Gạch',1), ('Cát Đá',1);

-- Nhóm con vật tư
INSERT INTO SubCategory (subCategoryName, categoryMaterialId,status) VALUES
('Xi măng Hà Tiên', 1,1),
('Thép D10 Việt Nhật', 2,1),
('Gạch Tuynel BD', 3,1),
('Cát vàng Đồng Nai', 4,1);

-- Trạng thái vật tư
INSERT INTO MaterialStatus (status) VALUES 
('Mới'), ('Đang sử dụng'), ('Hư hỏng');

-- Trạng thái yêu cầu
INSERT INTO RequestStatus (status) VALUES 
('Pending'), ('Approve'), ('Reject'), ('Cancel'), ('Complete');

-- Đơn vị tính
INSERT INTO MaterialUnit (unit) VALUES 
('Bao'), ('Kg'), ('Viên'), ('Khối');

-- Vai trò
INSERT INTO Role (role) VALUES 
('Admin'), ('Warehouse Staff'), ('Company Staff'), ('CEO');

-- Quyền truy cập
INSERT INTO Permission (id, url, description) VALUES (1, '/userList', 'Quản lý người dùng');
INSERT INTO Permission (id, url, description) VALUES (2, '/updateUser', 'Quản lý vật tư');
INSERT INTO Permission (id, url, description) VALUES (3, '/resetPassword', 'Yêu cầu nhập/xuất');
INSERT INTO Permission (id, url, description) VALUES (4, '/createUser', 'Báo cáo');
INSERT INTO Permission (id, url, description) VALUES (5, '/updatesupplier', 'Cập nhật nhà cung cấp');
INSERT INTO Permission (id, url, description) VALUES (6, '/deletesupplier', 'Xóa nhà cung cấp');
INSERT INTO Permission (id, url, description) VALUES (7, '/addsupplier', 'Thêm nhà cung cấp');
INSERT INTO Permission (id, url, description) VALUES (8, '/suppliercontroller', 'Quản lý nhà cung cấp');
INSERT INTO Permission (id, url, description) VALUES (9, '/statistic', 'Thống kê hệ thống');
INSERT INTO Permission (id, url, description) VALUES (10, '/RequestDetailController', 'Chi tiết yêu cầu');
INSERT INTO Permission (id, url, description) VALUES (11, '/updateRequest', 'Cập nhật yêu cầu');
INSERT INTO Permission (id, url, description) VALUES (12, '/requestList', 'Danh sách yêu cầu');
INSERT INTO Permission (id, url, description) VALUES (13, '/updateprofile', 'Cập nhật hồ sơ người dùng');
INSERT INTO Permission (id, url, description) VALUES (14, '/userprofile', 'Hồ sơ người dùng');
INSERT INTO Permission (id, url, description) VALUES (15, '/permission', 'Phân quyền người dùng');
INSERT INTO Permission (id, url, description) VALUES (16, '/materialController', 'Quản lý vật tư');
INSERT INTO Permission (id, url, description) VALUES (17, '/ListImport', 'Danh sách phiếu nhập');
INSERT INTO Permission (id, url, description) VALUES (18, '/CreateImport', 'Tạo phiếu nhập');
INSERT INTO Permission (id, url, description) VALUES (19, '/CreateRequestImport', 'Tạo yêu cầu nhập');



INSERT INTO permission_role (permissionId, roleId, status) VALUES
(1, 1, 1), (1, 2, 1), (1, 3, 1), (1, 4, 1),
(2, 1, 1), (2, 2, 1), (2, 3, 1), (2, 4, 1),
(3, 1, 1), (3, 2, 1), (3, 3, 1), (3, 4, 1),
(4, 1, 1), (4, 2, 1), (4, 3, 1), (4, 4, 1),
(5, 1, 1), (5, 2, 1), (5, 3, 1), (5, 4, 1),
(6, 1, 1), (6, 2, 1), (6, 3, 1), (6, 4, 1),
(7, 1, 1), (7, 2, 1), (7, 3, 1), (7, 4, 1),
(8, 1, 1), (8, 2, 1), (8, 3, 1), (8, 4, 1),
(9, 1, 1), (9, 2, 1), (9, 3, 1), (9, 4, 1),
(10, 1, 1), (10, 2, 1), (10, 3, 1), (10, 4, 1),
(11, 1, 1), (11, 2, 1), (11, 3, 1), (11, 4, 1),
(12, 1, 1), (12, 2, 1), (12, 3, 1), (12, 4, 1),
(13, 1, 1), (13, 2, 1), (13, 3, 1), (13, 4, 1),
(14, 1, 1), (14, 2, 1), (14, 3, 1), (14, 4, 1),
(15, 1, 1), (15, 2, 1), (15, 3, 1), (15, 4, 1),
(16, 1, 1), (16, 2, 1), (16, 3, 1), (16, 4, 1),
(17, 1, 1), (17, 2, 1), (17, 3, 1), (17, 4, 1),
(18, 1, 1), (18, 2, 1), (18, 3, 1), (18, 4, 1),
(19, 1, 1), (19, 2, 1), (19, 3, 1), (19, 4, 1)
;


-- Người dùng
INSERT INTO User (username, fullname, phone, password, email, address,
gender, birthDate, image, status, roleId) VALUES
('admin', 'Nguyễn Tuấn Kiệt', '0909000000', 'mNy+OfwBVzPj9442CM86ANCQGd0=', 'aanhtuankiet@gmail.com', 'Hà Nội', 1, '2004-10-18', 'mau.jpg', TRUE, 1),
('kho1', 'Trần Thị Kho', '0911000000', '123456', 'kho1@kho.com', 'TP HCM', 0, '1990-05-10', 'kho1.png', TRUE, 2),
('cty1', 'Lê Thị Công Ty', '0922000000', '123456', 'cty1@cty.com', 'Đà Nẵng', 0, '1992-08-15', 'cty1.png', FALSE, 3),
('ceo1', 'Phạm Văn CEO', '0933000000', '123456', 'ceo1@corp.com', 'TP HCM', 1, '1980-03-01', 'ceo1.jpg', TRUE, 4),
('kho2', 'Nguyễn Văn Kho 2', '0911222333', '123456', 'kho2@kho.com', 'Cần Thơ', 1, '1991-01-01', 'kho2.jpg', TRUE, 2),
('cty2', 'Lê Công Ty 2', '0922444555', '123456', 'cty2@cty.com', 'Hải Phòng', 0, '1993-06-20', 'cty2.png', TRUE, 3);

-- Vật tư
INSERT INTO Materials (name, unitId, subCategoryId, status) VALUES
('Xi măng Hà Tiên', 1, 1, 1),
('Thép Việt Nhật D10', 2, 2, 1),
('Gạch Tuynel Bình Dương', 3, 3, 1),
('Cát Vàng Đồng Nai', 4, 4, 1),
('Thép Việt Mỹ D12', 2, 2, 1),
('Gạch ống Đồng Nai', 3, 3, 1);

-- Nhà cung cấp
INSERT INTO Supplier (name, phone, address,status) VALUES
('Công ty VLXD Hà Tiên', '0933111000', 'Quận 9, TP HCM',1),
('Công ty Thép Việt Nhật', '0944222000', 'Quận 2, TP HCM',1),
('Công ty Gạch Bình Dương', '0955333000', 'Thủ Dầu Một, Bình Dương',1),
('Công ty Cát Đồng Nai', '0966444000', 'Biên Hòa, Đồng Nai',1),
('Công ty Thép Việt Mỹ', '0977777777', 'Quận Thủ Đức, TP HCM',1),
('Công ty Gạch Đồng Nai', '0988888888', 'Long Thành, Đồng Nai',0);

-- Liên kết vật tư - nhà cung cấp
INSERT INTO materials_Supplier (materialId, supplierId, note, price) VALUES
(1, 1, 'Xi măng chất lượng cao', 75000),
(2, 2, 'Thép phi D10', 18000),
(3, 3, 'Gạch đỏ chất lượng', 1200),
(4, 4, 'Cát vàng xây dựng', 350000),
(5, 5, 'Thép phi D12 Việt Mỹ', 19000),
(6, 6, 'Gạch ống cho xây dựng', 1000);

-- Tồn kho
INSERT INTO MaterialItem (materials_SupplierId, statusId, quantity) VALUES
(1, 1, 500),
(2, 1, 3000),
(3, 1, 10000),
(4, 1, 200),
(5, 1, 1500),
(6, 1, 8000);

-- Yêu cầu nhập kho
INSERT INTO Request (date, statusId, userId, type, note) VALUES
('2025-06-22', 1, 2, 'Import', NULL),
('2025-06-24', 1, 2, 'Import', 'Thêm thép và gạch ống');

-- Chi tiết yêu cầu
INSERT INTO requestDetail (requestId, materialItemId, quantity, note) VALUES
(1, 1, 100, 'Nhập 100 bao Xi măng Hà Tiên'),
(1, 2, 500, 'Nhập 500 Kg Thép Việt Nhật'),
(2, 5, 400, 'Nhập 400 Kg Thép D12'),
(2, 6, 3000, 'Nhập 3000 viên gạch ống');

-- Phiếu nhập
INSERT INTO InputWarehouse (id, dateInput, userId, reason, note, requestId) VALUES
(1, '2025-06-23', 2, 'Nhập theo yêu cầu #1', 'Không có vấn đề', 1),
(2, '2025-06-25', 2, 'Nhập theo yêu cầu #2', 'Chậm giao 1 ngày', 2);

-- Chi tiết nhập kho
INSERT INTO InputDetail (quantity, inputWarehouseId, requestDetailId, materialItemId, inputPrice) VALUES
(100, 1, 1, 1, 75000),
(500, 1, 2, 2, 18000),
(400, 2, 3, 5, 19000),
(3000, 2, 4, 6, 1000);

-- Phiếu xuất kho
INSERT INTO OutputWarehouse (id, date, userId, reason, note, requestId) VALUES
(1, '2025-06-28', 2, 'Xuất theo yêu cầu #1', 'Xuất gấp cho công trình X', 1);

-- Chi tiết xuất kho
INSERT INTO OutputDetail (quantity, outputWarehouseId, requestDetailId, materialItemId) VALUES
(100, 1, 1, 1),
(200, 1, 2, 2);


-- Vật tư thay thế cho Thép Việt Nhật D10 (id = 2)
INSERT INTO Materials (name, unitId, subCategoryId, replacementMaterialId) VALUES
('Thép Việt Nhật D10 - Phiên bản mới', 2, 2, 2);

-- Vật tư thay thế cho Gạch Tuynel Bình Dương (id = 3)
INSERT INTO Materials (name, unitId, subCategoryId, replacementMaterialId) VALUES
('Gạch Tuynel BD Loại A', 3, 3, 3);
