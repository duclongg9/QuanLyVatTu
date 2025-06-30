<%-- 
    Document   : Export
    Created on : 30 Jun 2025, 11:17:24
    Author     : Dell-PC
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
     <meta charset="utf-8">
    <%@page contentType="text/html; charset=UTF-8" %>
    <title>Export</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">
    <link href="img/favicon.ico" rel="icon">
    <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid position-relative bg-white d-flex p-0">
    <%@include file="../template/spinner.jsp" %>
    <%@include file="../template/sidebar.jsp" %>
    <div class="content">
        <%@include file="../template/navbar.jsp" %>
        <div class="container-fluid pt-4 px-4">
            <div class="row g-4">
                <div class="col-12">
                    <div class="bg-light rounded h-100 p-4">
                        <h6 class="mb-4">Export Warehouse List</h6>
                        <div class="table-responsive">
                            <table class="table" id="exportTable">
                                <thead>
                                <tr>
                                    <th scope="col" onclick="sortTable(0)">ID</th>
                                    <th scope="col" onclick="sortTable(1)">User</th>
                                    <th scope="col" onclick="sortTable(2)">Date</th>
                                    <th scope="col">Detail</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="ow" items="${outputWarehouses}">
                                    <tr>
                                        <td>${ow.id}</td>
                                        <td>${ow.userId.fullName}</td>
                                        <td>${ow.date}</td>
                                        <td><a href="exportDetail?outputWarehouseId=${ow.id}" class="btn btn-primary btn-sm">Detail</a></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div class="d-flex justify-content-center mt-3">
                                <c:forEach begin="1" end="${endP}" var="i">
                                    <a href="ListExport?index=${i}" class="btn btn-outline-primary mx-1">${i}</a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../template/footer.jsp" %>
    </div>
</div>
<%@include file="../template/script.jsp" %>
<script>
    function sortTable(colIndex) {
        var table = document.getElementById("exportTable");
        var rows = Array.from(table.rows).slice(1);
        var isAsc = table.getAttribute("data-sort") !== "asc";
        rows.sort(function(a, b) {
            var cellA = a.cells[colIndex].innerText.toLowerCase();
            var cellB = b.cells[colIndex].innerText.toLowerCase();
            return (cellA > cellB ? 1 : -1) * (isAsc ? 1 : -1);
        });
        var tbody = table.tBodies[0];
        rows.forEach(function(row) { tbody.appendChild(row); });
        table.setAttribute("data-sort", isAsc ? "asc" : "desc");
    }
</script>
</body>
</html>