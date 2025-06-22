<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Admin Page</title>
    <link rel="stylesheet" href="../css/Admin_Home_style.css" />

</head>

<body>
 <%@include file="../template/header.jsp" %>

    <main>
              <%@include file="../template/leftSideBar.jsp" %>
        <section class="main-content">
            <h2>Chỉnh sửa thông tin cá nhân</h2>
            <div style="display: flex; justify-content: space-between; align-items: flex-start; gap: 20px;">
                <!-- FORM -->
                <form id="edit-form">
                    <label for="sex">Name:</label>
                    <input type="text" name="name" required><br><br>

                    <label for="sex">Sex:</label>
                    <select id="ssex" name="status" required>
                        <option value="1">Male</option>
                        <option value="0">Female</option>
                    </select><br><br>

                    <label for="birth">Date of birthbirth:</label>
                    <input type="date" id="birth" name="birth" required><br><br>

                    <label for="address">AddressAddress:</label>
                    <input type="text" id="address" name="address" required><br><br>

                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required><br><br>

                     <label for="roll">Roll:</label>
                    <select id="status" name="status" required>
                        <option value="warehourseStaff">Warehouse Staff</option>
                        <option value="manager">Manager</option>
                    </select><br><br>

                    <label for="status">StatusStatus:</label>
                    <select id="status" name="status" required>
                        <option value="active">Active</option>
                        <option value="inactive">Inactive</option>
                    </select><br><br>
                    <button type="submit">Lưu</button>
                </form>

                <!-- ẢNH NHÂN VIÊN -->
                <div style="text-align: left">
                    <img src="132990a6-7a0c-47b8-ac21-e260cc356005.png" alt="Ảnh nhân viên"
                        style="width: 90px; height: 120px; border: 1px solid #ccc;" />
                    <p style="text-align: center;">Ảnh 3x4</p>
                </div>
            </div>
        </section>
    </main>


   <%@include file="../template/footer.jsp" %>


</body>

</html>