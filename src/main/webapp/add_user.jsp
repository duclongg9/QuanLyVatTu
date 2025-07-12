<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thêm người dùng mới</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function validateForm() {
            let name = document.getElementById("name").value;
            let email = document.getElementById("email").value;
            let account = document.getElementById("account").value;
            let password = document.getElementById("password").value;
            let roleId = document.getElementById("roleId").value;
            let status = document.getElementById("status").value;
            let birth = document.getElementById("birth").value;
            let emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

            if (!name) {
                alert("Tên không được để trống!");
                return false;
            }
            if (!email || !emailRegex.test(email)) {
                alert("Email không hợp lệ!");
                return false;
            }
            if (!account) {
                alert("Tài khoản không được để trống!");
                return false;
            }
            if (!password) {
                alert("Mật khẩu không được để trống!");
                return false;
            }
            if (!roleId) {
                alert("Vui lòng chọn vai trò!");
                return false;
            }
            if (!status) {
                alert("Vui lòng chọn trạng thái!");
                return false;
            }
            if (!birth) {
                alert("Ngày sinh không được để trống!");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<div class="container mt-5">
    <h2>Thêm người dùng mới</h2>
    <form action="AddUserServlet" method="post" onsubmit="return validateForm()">
        <div class="mb-3">
            <label for="name" class="form-label">Họ và tên</label>
            <input type="text" class="form-control" id="name" name="name">
        </div>
        <div class="mb-3">
            <label for="sex" class="form-label">Giới tính</label>
            <select class="form-select" id="sex" name="sex">
                <option value="1">Nam</option>
                <option value="0">Nữ</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="birth" class="form-label">Ngày sinh</label>
            <input type="date" class="form-control" id="birth" name="birth">
        </div>
        <div class="mb-3">
            <label for="address" class="form-label">Địa chỉ</label>
            <input type="text" class="form-control" id="address" name="address">
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email">
        </div>
        <div class="mb-3">
            <label for="account" class="form-label">Tài khoản</label>
            <input type="text" class="form-control" id="account" name="account">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Mật khẩu</label>
            <input type="password" class="form-control" id="password" name="password">
        </div>
        <div class="mb-3">
            <label for="roleId" class="form-label">Vai trò</label>
            <select class="form-select" id="roleId" name="roleId">
                <!-- Điền động từ servlet hoặc database -->
                <option value="">Chọn vai trò</option>
                <option value="1">Admin</option>
                <option value="2">Nhân viên kho</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="status" class="form-label">Trạng thái</label>
            <select class="form-select" id="status" name="status">
                <option value="1">Hoạt động</option>
                <option value="0">Không hoạt động</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Thêm người dùng</button>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>