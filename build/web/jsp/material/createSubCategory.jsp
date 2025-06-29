<%-- 
    Document   : createSubCategory
    Created on : 30 Jun 2025, 06:40:37
    Author     : Dell-PC
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Sub Category</title>
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
                <h4 class="mb-4">Create Sub Category</h4>
                <form action="${pageContext.request.contextPath}/subCategoryController" method="post" class="w-50">
                    <div class="mb-3">
                        <label for="subCategoryName" class="form-label">Sub Category Name</label>
                        <input type="text" id="subCategoryName" name="subCategoryName" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="categoryMaterialId" class="form-label">Category</label>
                        <select id="categoryMaterialId" name="categoryMaterialId" class="form-select" required>
                            <c:forEach var="c" items="${categories}">
                                <option value="${c.id}">${c.category}</option>
                            </c:forEach>
                        </select>
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
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
</html>