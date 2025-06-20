<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Danh sách đơn vị tính</title>
    <link href="${pageContext.request.contextPath}/Admin2/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/Admin2/css/style.css" rel="stylesheet" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">

</head>

<body>
    <%@ include file="Admin2/spinner.jsp" %>
<div class="container-xxl position-relative bg-white d-flex p-0">
    <%@ include file="Admin2/sidebar.jsp" %>
    <div class="content d-flex flex-column min-vh-100">
        <%@ include file="Admin2/navbar.jsp" %>

        <div class="container-fluid pt-4 px-4 flex-grow-1">
            <div class="bg-light rounded p-4">
                <h4 class="mb-4">Danh sách đơn vị tính</h4>

                <!-- Thông báo -->
                <c:if test="${not empty message}">
                    <div class="alert alert-${messageType} alert-dismissible fade show" role="alert">
                        ${message}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <a href="unit?action=add" class="btn btn-primary mb-3">+ Thêm mới</a>

                <table class="table table-hover table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Tên đơn vị</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="u" items="${list}">
                            <tr>
                                <td>${u.id}</td>
                                <td>${u.name}</td>
                                <td>
                                    <span class="badge bg-${u.status ? 'success' : 'secondary'}">
                                        ${u.status ? 'Hiển thị' : 'Đã ẩn'}
                                    </span>
                                </td>
                                <td>
                                    <a href="unit?action=edit&id=${u.id}" class="btn btn-sm btn-warning">Sửa</a>
                                    <a href="unit?action=confirmDelete&id=${u.id}" class="btn btn-sm btn-danger">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <nav>
 

                </table>
                <nav>
                 <ul class="pagination justify-content-center">
        <c:forEach begin="1" end="${totalPage}" var="i">
            <li class="page-item ${page == i ? 'active' : ''}">
                <a class="page-link" href="unit?page=${i}">${i}</a>
            </li>
        </c:forEach>
    </ul>
</nav>
            </div>
        </div>

        <%@ include file="Admin2/footer.jsp" %>
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
<script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
