<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Xác nhận xóa đơn vị tính</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <%@ include file="../template/spinner.jsp" %>
<div class="container-xxl position-relative bg-white d-flex p-0">
    <%@ include file="../template/sidebar.jsp" %>

    <div class="content d-flex flex-column min-vh-100">
        <%@ include file="../template/navbar.jsp" %>

        <div class="container-fluid pt-4 px-4 flex-grow-1">
            <div class="bg-light rounded p-5">
                <h4 class="mb-4 text-danger"> Confirm Delete</h4>

                <p>Are you sure to inactive unit_Material?</p>
                <ul>
                    <li><strong>ID:</strong> ${unit.id}</li>
                    <li><strong>Name</strong> ${unit.name}</li>
                </ul>

                <form action="unit" method="get">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="id" value="${unit.id}" />

                    <button type="submit" class="btn btn-danger">Done</button>
                    <a href="unit" class="btn btn-secondary">Cancel</a>
                </form>
            </div>
        </div>

                    <%@ include file="../template/footer.jsp" %>
    </div>
</div>
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


<!-- JS -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
