<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    model.Unit u = (model.Unit) request.getAttribute("unit");
    String message = (String) request.getAttribute("message");
    String messageType = (String) request.getAttribute("messageType");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thêm/Cập nhật đơn vị tính</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>
<%@ include file="spinner.jsp" %>
<body>
    
<div class="container-xxl position-relative bg-white d-flex p-0">

    <%@ include file="sidebar.jsp" %>

    <div class="content d-flex flex-column min-vh-100">
        <%@ include file="navbar.jsp" %>

        <div class="container-fluid pt-4 px-4 flex-grow-1">
            <div class="bg-light rounded p-4">
                <h4 class="mb-4"><%= (u == null ? "Thêm mới" : "Cập nhật") %> đơn vị tính</h4>

                <c:if test="${not empty message}">
                    <div class="alert alert-${messageType} alert-dismissible fade show" role="alert">
                        ${message}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <form action="unit" method="post">
                    <input type="hidden" name="id" value="<%= (u != null ? u.getId() : "") %>"/>

                    <div class="mb-3 row">
                        <label class="col-sm-2 col-form-label">Tên đơn vị</label>
                        <div class="col-sm-10">
                            <input type="text" name="name" class="form-control"
                                   value="<%= (u != null ? u.getName() : "") %>" required />
                        </div>
                    </div>

                    <div class="text-center">
                        <button type="submit" class="btn btn-primary">Lưu</button>
                        <a href="unit" class="btn btn-secondary">Hủy</a>
                    </div>
                </form>
            </div>
        </div>

        <%@ include file="footer.jsp" %>
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


<!-- Scripts -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
