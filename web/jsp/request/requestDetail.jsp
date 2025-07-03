
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
     <meta charset="utf-8">
    <%@page contentType="text/html; charset=UTF-8" %>
    <title>Request Detail</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">
    
    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="${pageContext.request.contextPath}/assets/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

    <!-- Customized Bootstrap Stylesheet -->
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>

<body>
<div class="container-fluid bg-white d-flex p-0">

    <%@include file="../template/spinner.jsp" %>
    <%@include file="../template/sidebar.jsp" %>

    <div class="content">
        <%@include file="../template/navbar.jsp" %>

        <!-- Table Start -->
        <div class="container-fluid pt-4 px-4">
            <div class="row g-4">
                <div class="col-12">
                    <div class="bg-light rounded h-100 p-4">
                        <h6 class="mb-4">Request Detail</h6>

                        

                        <div class="table-responsive">
                            <c:if test="${userRequest.statusId.id == 1 && sessionScope.account.role.id == 1}">
                            <div class="m-2">
                            <form action="requestDetailController" method="post" style="display:inline;">
                                <input type="hidden" name="requestId" value="${requestId}" />
                                <input type="hidden" name="newStatusId" value="2" /> <!-- 2 = Approve -->
                                <button type="submit" class="btn btn-success">Approve</button>
                            </form>

                            <form action="requestDetailController" method="post" style="display:inline;">
                                <input type="hidden" name="requestId" value="${requestId}" />
                                <input type="hidden" name="newStatusId" value="3" /> <!-- 3 = Reject -->
                                <button type="submit" class="btn btn-danger">Reject</button>
                            </form>

                            <form action="requestDetailController" method="post" style="display:inline;">
                                <input type="hidden" name="requestId" value="${requestId}" />
                                <input type="hidden" name="newStatusId" value="4" /> <!-- 4 = Cancel -->
                                <button type="submit" class="btn btn-warning">Cancel</button>
                            </form> 
                              </div>
                                </c:if>
                            <table id="userTable" class="table table-bordered">
                               <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Material Name</th>
                                    <th>Current Quantity</th>
                                    <th>Unit</th>
                                    <th>Supplier</th>
                                    <th>Note</th>
                                    <th>New Quantity</th> <!-- mới thêm -->
                                    <th>New Note</th>   <!-- tuỳ chọn -->
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    
                                    <c:when test="${userRequest.statusId.id == 1 || userRequest.statusId.id == 3}">
                                        <form method="post" action="updateRequest">
                                            <input type="hidden" name="requestId" value="${requestId}" />
                                            <c:forEach var="m" items="${listRequestDetail}">
                                                <tr>
                                                    <td>
                                                        ${m.id}
                                                        <input type="hidden" name="detailId" value="${m.id}" />
                                                    </td>
                                                    <td>${m.materialItem.materialSupplier.materialId.name}</td>
                                                    <td>${m.quantity}</td>
                                                    <td>${m.materialItem.materialSupplier.materialId.unitId.unitName}</td>
                                                    <td>${m.materialItem.materialSupplier.supplierId.name}</td>
                                                    <td>${m.note}</td>
                                                    <td>
                                                        <input type="number" name="quantity" min="1" class="form-control" value="${m.quantity}" />
                                                    </td>
                                                    <td>
                                                        <input type="text" name="note" class="form-control" value="${m.note}" />
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td colspan="8" class="text-end">
                                                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                                                </td>
                                            </tr>
                                        </form>
                                    </c:when>

                                   
                                    <c:otherwise>
                                        <c:forEach var="m" items="${listRequestDetail}">
                                            <tr>
                                                <td>${m.id}</td>
                                                <td>${m.materialItem.materialSupplier.materialId.name}</td>
                                                <td>${m.quantity}</td>
                                                <td>${m.materialItem.materialSupplier.materialId.unitId.unitName}</td>
                                                <td>${m.materialItem.materialSupplier.supplierId.name}</td>
                                                <td>${m.note}</td>
                                                <td>--</td>
                                                <td>--</td>
                                                
                                            </tr>
                                        </c:forEach>
                                        <tr>
                                            <td colspan="8" class="text-danger text-center fw-bold">Bạn không thể sửa đơn đã được chấp thuận</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>

                            </table>

                            <!-- Pagination -->
                            <div class="d-flex justify-content-center mt-3">
                                <c:forEach begin="1" end="${endP}" var="i">
                                    <a href="requestDetail?index=${i}&requestId=${requestId}" class="btn btn-outline-primary mx-1">${i}</a>
                                </c:forEach>
                            </div>

                            <div class="d-flex mt-4 gap-2">
                                <a href="${pageContext.request.contextPath}/requestList" class="btn btn-secondary rounded-pill">Quay lại</a>
                                 <c:if test="${userRequest.statusId.id == 2 && (sessionScope.account.role.id == 1 || sessionScope.account.role.id == 2)}">
                                    <c:if test="${userRequest.type == 'IMPORT'}">
                                        <form action="createImport" method="post">
                                            <input type="hidden" name="requestId" value="${requestId}" />
                                            <button type="submit" class="btn btn-success">Import to Warehouse</button>
                                        </form>
                                    </c:if>
                                    <c:if test="${userRequest.type == 'EXPORT'}">
                                        <form action="createExport" method="post">
                                            <input type="hidden" name="requestId" value="${requestId}" />
                                            <button type="submit" class="btn btn-danger">Export from Warehouse</button>
                                        </form>
                                    </c:if>
                                </c:if>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <!-- Table End -->

        <%@include file="../template/footer.jsp" %>
    </div>
</div>

<!-- Back to Top -->
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
    rows.forEach(function(row) {
        tbody.appendChild(row);
    });

    table.setAttribute("data-sort", isAsc ? "asc" : "desc");
}
</script>
</body>
</html>
