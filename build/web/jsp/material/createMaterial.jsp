<%-- 

--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>


 <html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Material</title>
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<%@include file="../template/spinner.jsp" %>
<div class="container-xxl position-relative bg-white d-flex p-0">
    <%@include file="../template/sidebar.jsp" %>
    <div class="content d-flex flex-column min-vh-100">
        <%@include file="../template/navbar.jsp" %>

        <div class="container-fluid pt-4 px-4 flex-grow-1">
            <div class="bg-light rounded p-4">
                <h4 class="mb-4">Create Material</h4>
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/materialController" method="post" enctype="multipart/form-data" class="w-50">
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" id="name" name="name" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="unitId" class="form-label">Unit</label>
                <select id="unitId" name="unitId" class="form-select" required>
                    <c:forEach var="u" items="${units}">
                        <option value="${u.id}">${u.unitName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="categoryId" class="form-label">Category</label>
                <select id="categoryId" name="categoryId" class="form-select" required>
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.id}">${c.category}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="image" class="form-label">Image</label>
                <input type="file" id="image" name="image" accept="image/*" class="form-control">
            </div>
            <div class="mb-3">
                <button type="submit" class="btn btn-success">Submit</button>
                <button type="button" class="btn btn-secondary" onclick="history.back()">Cancel</button>
            </div>
            <div class="text-danger">
                <c:if test="${not empty error}">${error}</c:if>
            </div>
        </form>
    </div>
        </div>

        <%@include file="../template/footer.jsp" %>
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
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
 </html>
