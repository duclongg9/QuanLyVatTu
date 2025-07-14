<%-- 
    Document   : materialHistory
    Created on : 2 Jul 2025, 11:15:05
    Author     : Dell-PC
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Material Update History</title>
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
                <h4 class="mb-4">Material Update History</h4>
                <a href="${pageContext.request.contextPath}/materialController?action=list" class="btn btn-secondary mb-3">Back to List</a>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Material</th>
                            <th>Name</th>
                            <th>Unit</th>
                            <th>Sub Category</th>
                            <th>Archived At</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="h" items="${historyList}">
                            <tr>
                                <td>${h.id}</td>
                                <td>${h.material.id}</td>
                                <td>${h.name}</td>
                                <td>${h.unit.unitName}</td>
                                <td>${h.subCategory.categoryName}</td>
                                <td>${h.archivedAt}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
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