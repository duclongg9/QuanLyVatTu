<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Material List</title>
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
            <div class="row g-4">
                <div class="col-12">
                    <div class="bg-light rounded p-4">
                        <h4 class="mb-4">Material List</h4>
                        <a href="${pageContext.request.contextPath}/materialController?action=add" class="btn btn-primary mb-3">Add New</a>
                        <a href="${pageContext.request.contextPath}/materialController?action=deleted" class="btn btn-outline-secondary mb-3 ms-2">View Deleted</a>
                        <form action="materialController" method="get" class="d-flex align-items-center gap-2 mb-3">
                            <input type="hidden" name="action" value="list"/>
                            <select name="categoryId" class="form-select w-auto">
                                <option value="">All Category</option>
                                <c:forEach var="c" items="${categoryFilter}">
                                    <option value="${c.id}" ${selectedCategory == c.id ? 'selected' : ''}>${c.category}</option>
                                </c:forEach>
                            </select>
                            <input class="form-control border-0" type="search" placeholder="Search" name="search" value="${param.search}" />
                            <button type="submit" class="btn btn-primary">Search</button>
                        </form>
                          <table class="table table-hover table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Unit</th>
                            <th>Sub Category</th>
                            <th>Replaces</th>
                            <th>Updated By</th>
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
                                <td>${m.subCategoryId.subCategoryName}</td>
                                <td>${m.replacementMaterialId != null ? m.replacementMaterialId.name : '-'}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${updatedMap[m.id] != null}">
                                            ${updatedMap[m.id].name}
                                        </c:when>
                                        <c:otherwise>-</c:otherwise>
                                    </c:choose>
                                </td>
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
                            <nav>
                    <ul class="pagination justify-content-center">
                        <c:forEach begin="1" end="${endP}" var="i">
                            <li class="page-item ${tag == i ? 'active' : ''}">
                                <a class="page-link" href="materialController?action=list&index=${i}&search=${param.search}&categoryId=${selectedCategory}">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
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