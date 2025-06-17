<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    model.Materials m = (model.Materials) request.getAttribute("material");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Material</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@include file="header.jsp" %>
<main>
    <%@include file="leftSideBar.jsp" %>
    <section class="main-content p-4">
        <h2 class="mb-3">Update Material</h2>
        <form action="${pageContext.request.contextPath}/materialController" method="post" enctype="multipart/form-data" class="w-50">
            <input type="hidden" name="id" value="<%=m.getId()%>">
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" id="name" name="name" value="<%=m.getName()%>" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="unitId" class="form-label">Unit</label>
                <select id="unitId" name="unitId" class="form-select" required>
                    <c:forEach var="u" items="${units}">
                        <option value="${u.id}" ${u.id == m.unitId.id ? 'selected' : ''}>${u.unitName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="categoryId" class="form-label">Category</label>
                <select id="categoryId" name="categoryId" class="form-select" required>
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.id}" ${c.id == m.categoryId.id ? 'selected' : ''}>${c.category}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label">Current Image</label><br/>
                <img src="${pageContext.request.contextPath}/images/${m.image}" alt="" width="100">
            </div>
            <div class="mb-3">
                <label for="image" class="form-label">New Image</label>
                <input type="file" id="image" name="image" accept="image/*" class="form-control">
            </div>
            <div class="mb-3">
                <button type="submit" class="btn btn-success">Save</button>
                <a href="${pageContext.request.contextPath}/materialController?action=list" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </section>
</main>
<%@include file="footer.jsp" %>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>