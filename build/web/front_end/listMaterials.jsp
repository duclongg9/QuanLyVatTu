<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Material List</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@include file="header.jsp" %>
<main>
    <%@include file="leftSideBar.jsp" %>
    <section class="main-content p-4">
        <h2 class="mb-3">Material List</h2>
        <a href="${pageContext.request.contextPath}/materialController?action=add" class="btn btn-primary mb-3">Add New</a>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Unit</th>
                    <th>Category</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="m" items="${materials}">
                    <tr>
                        <td>${m.id}</td>
                        <td>${m.name}</td>
                        <td>${m.unitId.unitName}</td>
                        <td>${m.categoryId.category}</td>
                        <td>
                            <span class="badge bg-${m.status ? 'success' : 'secondary'}">
                                ${m.status ? 'Active' : 'Hidden'}
                            </span>
                        </td>
                        <td>
                            <a href="materialController?action=edit&id=${m.id}" class="btn btn-sm btn-warning">Edit</a>
                            <a href="materialController?action=delete&id=${m.id}" class="btn btn-sm btn-danger" onclick="return confirm('Delete this item?');">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </section>
</main>
<%@include file="footer.jsp" %>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>