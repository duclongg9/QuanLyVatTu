<%-- 
    Document   : exportDetail
    Created on : 30 Jun 2025, 11:17:48
    Author     : Dell-PC
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
     <meta charset="utf-8">
    <%@page contentType="text/html; charset=UTF-8" %>
    <title>Export Detail</title>
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
<div class="container-fluid bg-white d-flex p-0">
    <%@include file="../template/spinner.jsp" %>
    <%@include file="../template/sidebar.jsp" %>
    <div class="content">
        <%@include file="../template/navbar.jsp" %>
        <div class="container-fluid pt-4 px-4">
            <div class="row g-4">
                <div class="col-12">
                    <div class="bg-light rounded h-100 p-4">
                        <h4 class="mb-4">Export Detail</h4>
                        <div>
                            <h6>ID: ${exportId}</h6>
                            <h6>User Export: ${outputWarehouse.userId.fullName}</h6>
                            <h6>Export Date: ${outputWarehouse.date}</h6>
                        </div>
                        <table id="userTable" class="table table-bordered">
                           <thead>
                            <tr>
                                <th>ID</th>
                                <th>Material Name</th>
                                <th>Quantity</th>
                                <th>Unit</th>
                                <th>Supplier</th>
                                <th>Note</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="m" items="${listOutputDetail}">
                                <tr>
                                    <td>${m.id}</td>
                                    <td>${m.materialItemId.materialSupplier.materialId.name}</td>
                                    <td>${m.quantity}</td>
                                    <td>${m.materialItemId.materialSupplier.materialId.unitId.unitName}</td>
                                    <td>${m.materialItemId.materialSupplier.supplierId.name}</td>
                                    <td>${m.requestDetailId.note}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        </table>
                        <div class="d-flex justify-content-center mt-3">
                            <c:forEach begin="1" end="${endP}" var="i">
                                <a href="exportDetail?index=${i}&outputWarehouseId=${outputWarehouseId}" class="btn btn-outline-primary mx-1">${i}</a>
                            </c:forEach>
                        </div>
                        <div class="d-flex mt-4 gap-2">
                            <a href="${pageContext.request.contextPath}/ListExport" class="btn btn-secondary rounded-pill">Quay lại</a>
                        </div>
                    </div>
                </div>
                        <%@include file="../template/footer.jsp" %>
            </div>
        </div>
    </div>
</div>
<a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
<%@include file="../template/script.jsp" %>
<script>
function sortTable(colIndex) {
    var table = document.getElementById("userTable");
    var rows = Array.from(table.rows).slice(1);
    var isAsc = table.getAttribute("data-sort") !== "asc";
    rows.sort(function(rowA, rowB) {
        var cellA = rowA.cells[colIndex].innerText.toLowerCase();
        var cellB = rowB.cells[colIndex].innerText.toLowerCase();
        if (!isNaN(cellA) && !isNaN(cellB)) {
            return (cellA - cellB) * (isAsc ? 1 : -1);
        }
        return (cellA > cellB ? 1 : -1) * (isAsc ? 1 : -1);
    });
    var tbody = table.tBodies[0];
    rows.forEach(function(row) { tbody.appendChild(row); });
    table.setAttribute("data-sort", isAsc ? "asc" : "desc");
}
</script>
</body>
</html>
