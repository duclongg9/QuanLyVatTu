<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Admin Create User</title>
        <link rel="stylesheet" href="Admin/CSS/createStyle.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Admin/CSS/layout.css"/>
        <style>
            .form-box {
                background-color: #ecf0f1;
                padding: 30px;
                border-radius: 8px;
                max-width: 700px;
                margin: 0 auto;
            }

            .form-group {
                display: flex;
                flex-direction: column;
                margin-bottom: 20px;
            }

            .form-group label {
                font-weight: 500;
                margin-bottom: 8px;
            }

            .form-group input {
                padding: 10px;
                font-size: 16px;
                border-radius: 6px;
                border: 1px solid #ccc;
                background-color: #f9f9f9;
            }

            .button-row {
                display: flex;
                justify-content: flex-end;
                gap: 10px;
            }

            .btn {
                padding: 8px 16px;
                border-radius: 6px;
                font-size: 14px;
                font-weight: 500;
                text-decoration: none;
                border: none;
                cursor: pointer;
            }

            .btn.green {
                background-color: #2ecc71;
                color: white;
            }

            .btn.gray {
                background-color: #7f8c8d;
                color: white;
            }

            h1 {
                font-weight: bold;
                font-size: 26px;
                margin-bottom: 20px;
                color: #2c3e50;
            }
        </style>
    </head>

    <body>
        <div class="wrapper">
            <div class="container">
                <%@ include file="sidebar.jsp" %>

                <!-- Main content -->
                <div class="main">
                    <%@ include file="header.jsp" %>

                    <!-- Main content area -->
                    <main class="content">
                        <h1>CREATE MATERIAL LIST</h1>
                        <c:if test="${not empty error}">
                            <div class="error">${error}</div>
                        </c:if>
                        <c:if test="${not empty success}">
                            <div style="color: #27ae60; font-weight: bold; margin-bottom: 15px; text-align: center; padding: 10px; background-color: #e8f8e8; border-radius: 5px; border-left: 4px solid #27ae60;">
                                ${success}
                            </div>
                        </c:if>

                        <form method="post" action="${pageContext.request.contextPath}/Create" class="form-box">
                            <div class="form-group">
                                <label for="name">Name</label>
                                <input type="text" name="roomNumber" id="name" required />
                            </div>

                            <div class="form-group">
                                <label for="unit">Unit</label>
                                <input type="text" name="roomType" id="unit" required />
                            </div>

                            <div class="form-group">
                                <label for="status">StatusId</label>
                                <input type="number" name="capacity" id="status" required />
                            </div>

                            <div class="form-group">
                                <label for="category">CategoryId</label>
                                <input type="number" step="0.01" name="price" id="category" required />
                            </div>

                            <div class="form-group">
                                <label for="img">Image URL</label>
                                <input type="text" name="img" id="img" />
                            </div>

                            <div class="button-row">
                                <a href="materialDashboard.jsp"  class="btn green">Add Materials</button>
                                <a href="roomManagementController" class="btn gray">Cancel</a>
                            </div>
                        </form>
                    </main>
                </div>
            </div>
            <%@ include file="footer.jsp" %>
        </div>
    </body>
</html>  