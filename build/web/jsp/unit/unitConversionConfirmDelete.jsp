<%-- 
    Document   : unitConversionConfirmDelete
    Created on : Jun 18, 2025, 4:35:34 PM
    Author     : KIET
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.UnitConversion, model.Materials, model.Unit" %>
<%
    UnitConversion uc = (UnitConversion) request.getAttribute("uc");
    Materials material = (Materials) request.getAttribute("material");
    Unit fromUnit = (Unit) request.getAttribute("fromUnit");
    Unit toUnit = (Unit) request.getAttribute("toUnit");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Xác nhận xóa quy đổi đơn vị</title>
    <link href="Admin2/css/bootstrap.min.css" rel="stylesheet">
    <link href="Admin2/css/style.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<!-- Spinner Start -->
<div id="spinner" class="show bg-white position-fixed w-100 vh-100 d-flex align-items-center justify-content-center">
    <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status"></div>
</div>
<!-- Spinner End -->

<div class="container-fluid position-relative d-flex p-0">
    <%@ include file="Admin2/sidebar.jsp" %>

    <!-- Content Start -->
    <div class="content">
        <%@ include file="Admin2/navbar.jsp" %>

        <div class="container-fluid pt-4 px-4">
            <div class="bg-light rounded p-4">
                <h5 class="mb-4">Xác nhận xóa quy đổi đơn vị</h5>
                <div class="alert alert-danger" role="alert">
                    Bạn có chắc chắn muốn xóa quy đổi đơn vị sau không?
                </div>
                <ul class="list-group mb-4">
                    <li class="list-group-item"><strong>Vật tư:</strong> <%= material.getName() %></li>
                    <li class="list-group-item"><strong>Đơn vị từ:</strong> <%= fromUnit.getName() %></li>
                    <li class="list-group-item"><strong>Đơn vị sang:</strong> <%= toUnit.getName() %></li>
                    <li class="list-group-item"><strong>Tỉ lệ:</strong> 1 : <%= uc.getRatio() %></li>
                </ul>

                <form method="post" action="unitConversion">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="<%= uc.getId() %>">
                    <a href="unitConversion" class="btn btn-secondary">Hủy</a>
                    <button type="submit" class="btn btn-danger">Xác nhận xóa</button>
                </form>
            </div>
        </div>

        <%@ include file="Admin2/footer.jsp" %>
    </div>
    <!-- Content End -->
</div>

<!-- jQuery nếu dùng main.js -->
<script src="js/jquery-3.4.1.min.js"></script>

<!-- Bootstrap Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- Main.js (nếu bạn có) -->
<script src="${pageContext.request.contextPath}/js/main.js"></script>

<!-- Sidebar toggle -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const toggleBtn = document.querySelector(".sidebar-toggler");
        const sidebar = document.querySelector(".sidebar");
        const content = document.querySelector(".content");

        if (toggleBtn && sidebar && content) {
            toggleBtn.addEventListener("click", function () {
                sidebar.classList.toggle("open");
                content.classList.toggle("open");
            });
        }
    });
</script>

</body>
</html>
