<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Đổi mật khẩu - MMS</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&display=swap" rel="stylesheet">

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: 'Poppins', sans-serif;
            background-color: #f0f2f5;
        }

        .container {
            width: 100%;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login-box {
            background: #ffffff;
            padding: 40px 30px;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }

        .app-title {
            text-align: center;
            font-size: 26px;
            font-weight: 700;
            color: #2c3e50;
            margin-bottom: 5px;
            text-transform: uppercase;
            letter-spacing: 1.5px;
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
        }

        .login-title {
            text-align: center;
            font-size: 20px;
            font-weight: 500;
            margin-bottom: 25px;
            color: #555;
        }

        .form-group {
            margin-bottom: 18px;
        }

        .form-group label {
            display: block;
            margin-bottom: 6px;
            font-weight: 500;
        }

        .form-group input {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 14px;
        }

        .form-group input:focus {
            border-color: #007bff;
            outline: none;
        }

        .error-message {
            color: red;
            font-size: 14px;
            margin-bottom: 15px;
            text-align: center;
        }

        .btn {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 12px;
            width: 100%;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="login-box">
        <h2 class="app-title">Materials Management</h2>
        <h3 class="login-title">Đổi mật khẩu mới</h3>

        

        <form action="${pageContext.request.contextPath}/resetpassword" method="post" onsubmit="return validatePasswordMatch()">
            <div class="form-group">
                <label for="newpassword">Mật khẩu mới:</label>
                <input type="password" id="newpassword" name="newpassword" placeholder="Nhập mật khẩu mới" required />
            </div>

            <div class="form-group">
                <label for="confirmpassword">Xác nhận mật khẩu:</label>
                <input type="password" id="confirmpassword" name="confirmpassword" placeholder="Nhập lại mật khẩu" required />
            </div>

            <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

            <button type="submit" class="btn">Đổi mật khẩu</button>
        </form>
    </div>
</div>

<script>
    function validatePasswordMatch() {
        const password = document.getElementById("newpassword").value;
        const confirm = document.getElementById("confirmpassword").value;
        const error = document.getElementById("confirm-error");

        if (password !== confirm) {
            error.style.display = "block";
            return false;
        } else {
            error.style.display = "none";
            return true;
        }
    }
</script>

</body>
</html>
