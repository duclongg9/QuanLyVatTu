
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
                        <h3 class="mb-4">Warehouse Import Form:</h3>
                        <div class="row mb-3">
                        <div class="col-md-3">
                            
                            
                             <div class="col-md-12 mb-1">
                                    <label for="createdBy" class="form-label fw-bold"> Warehouse Receiver:</label>
                                    <input type="text" class="form-control" id="createBy"  value="${inputWarehouse.userId.fullName}" readonly>
                  
                                </div>
                        <div class="col-md-12 mb-1">
                                    <label for="date" class="form-label fw-bold">Import Date:</label>
                                    <input type="date" class="form-control" id="date" value="${inputWarehouse.dateInput}" readonly>
              
                                </div>
                        <div class="col-md-12 mb-1">
                                    <label for="requestType" class="form-label fw-bold">Type:</label>
                                    <input type="text" class="form-control" id="requestType" value="${decription.type}"readonly>
                  
                                </div>
                        </div>
                                    <div class="col-md-9">
                                       
                                    
                                    <div class="col-md-12 mb-1">
                                    <label for="requestStatus" class="form-label fw-bold">Note:</label>
                                    <textarea  rows="2" class="form-control" id="requestStatus" readonly>${decription.note}</textarea>
                                </div>
                                    </div>
                                    </div>
                        
                            <table id="userTable" class="table table-bordered">
                               <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Material Name</th>
                                    <th>Current Quantity</th>
                                    <th>Unit</th>
                                    <th>Supplier</th>
                                    <th>Note</th>
                                    <th>Price</th>
                                </tr>
                            </thead>
                            <tbody>
                               
                                        <c:forEach var="m" items="${listImportDetail}" varStatus="status">
                                            <tr>
                                                <td>${status.index + 1}</td>
                                                <td>${m.materialItemId.materialSupplier.materialId.name}</td>
                                                <td>${m.quantity}</td>
                                                <td>${m.materialItemId.materialSupplier.materialId.unitId.unitName}</td>
                                                <td>${m.materialItemId.materialSupplier.supplierId.name}</td>
                                                <td>${m.requestDetailId.note}</td>
                                                <td><fmt:formatNumber value="${m.inputPrice}" type="currency" currencySymbol="VNĐ" groupingUsed="true" maxFractionDigits="0"/></td>
                                                
                                            </tr>
                                        </c:forEach>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td>TOTAL:</td>
                                                <td><fmt:formatNumber value="${totalPrice}" type="currency" currencySymbol="VNĐ" groupingUsed="true" maxFractionDigits="0"/></td>
                                                
                                            </tr>
                            </tbody>

                            </table>

                            <!-- Pagination -->
                            <div class="d-flex justify-content-center mt-3">
                                <c:forEach begin="1" end="${endP}" var="i">
                                    <a href="importDetail?index=${i}&importWarehouseId=${importWarehouseId}" class="btn btn-outline-primary mx-1">${i}</a>
                                </c:forEach>
                            </div>

                            <div class="d-flex mt-4 gap-2">
                                <a href="${pageContext.request.contextPath}/ListImport" class="btn btn-secondary rounded-pill">Quay lại</a>
                                 
                            </div>
                        </div>

                    </div>
                </div>
                        <%@include file="../template/footer.jsp" %>        
            </div>
        </div>
        <!-- Table End -->

        
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
