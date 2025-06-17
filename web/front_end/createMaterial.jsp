<%-- 

--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html>
 <html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Material</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@include file="header.jsp" %>
<main>
    <%@include file="leftSideBar.jsp" %>
    <section class="main-content p-4">
        <h2 class="mb-3">Create Material</h2>
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
    </section>
</main>
<%@include file="footer.jsp" %>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
