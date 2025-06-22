<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tên công ty</title>
    <link rel="stylesheet" href="login_style.css">
</head>
<body>
    <div class="login-container">
        <h2>Materials Management</h2>
        <form action="/login" method="post">
            <input type="text" name="username" placeholder="username" required><br>
            <input type="text" name="password" placeholder="password" required><br>
            <a href="#" class="forgot_password" >forgot password</a>
            <button type="submit" class="submit-button"> sign in</button>
        </form>

    </div>
</body>
</html>