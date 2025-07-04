<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Xác nhận xóa đơn vị tính</title>
    <link href="assets/img/favicon.ico" rel="icon">

    <!-- Google Web Fonts + Dashmin style -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="../template/spinner.jsp" %>

<div class="container-xxl position-relative bg-white d-flex p-0">
    <%@ include file="../template/sidebar.jsp" %>

    <div class="content d-flex flex-column min-vh-100">
        <%@ include file="../template/navbar.jsp" %>

        <div class="container-fluid pt-4 px-4 flex-grow-1">
            <div class="bg-light rounded p-5">
                <h4 class="mb-4 text-danger">Xác nhận xóa đơn vị tính</h4>

                <p>Bạn có chắc chắn muốn xóa đơn vị sau không?</p>
                <ul>
                    <li><strong>ID:</strong> ${unit.id}</li>
                    <li><strong>Tên đơn vị:</strong> ${unit.unit}</li>
                </ul>

                <form action="unit" method="get">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="id" value="${unit.id}" />

                    <button type="submit" class="btn btn-danger me-2">Xác nhận</button>
                    <a href="unit" class="btn btn-secondary">Hủy</a>
                </form>
            </div>
        </div>

        <%@ include file="../template/footer.jsp" %>
    </div>
</div>

<!-- JS -->
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

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
<script>
    window.addEventListener('load', function () {
        var spinner = document.getElementById('spinner');
        if (spinner) {
            spinner.classList.remove('show');
        }
    });
</script>
</body>
</html>
