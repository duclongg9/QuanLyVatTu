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
    <div class="container-flrid position-relative bg-white d-flex p-0">
        <%@include file="../template/spinner.jsp" %>


        <%@include file="../template/sidebar.jsp" %>


        <!-- Content Start -->
        <div class="content">
            <%@include file="../template/navbar.jsp" %>


           <!-- Table Start -->
<div class="container-flrid pt-4 px-4">
    <div class="row g-4">
        <div class="col-12">
            <div class="bg-light rounded h-100 p-4">
                <h6 class="mb-4">Create New Request</h6>
<!-- Form lọc vật tư (GET) -->
<form method="get" action="CreateRequestImport" class="row g-3 mb-4">
    <div class="col-md-4">
        <label for="categoryMaterialId" class="form-label">Category</label>
        <select name="categoryMaterialId" id="categoryMaterialId" class="form-select" onchange="this.form.submit()">
            <option value="0">-- ALL --</option>
            <c:forEach var="cat" items="${categoryList}">
                <option value="${cat.id}" ${param.categoryMaterialId == cat.id ? 'selected' : ''}>${cat.category}</option>
            </c:forEach>
        </select>
    </div>

    <div class="col-md-4">
        <label for="subCategoryId" class="form-label">Sub Category</label>
        <select name="subCategoryId" id="subCategoryId" class="form-select" onchange="this.form.submit()">
            <option value="0">-- ALL --</option>
            <c:forEach var="sub" items="${subCategoryList}">
                <option value="${sub.id}" ${param.subCategoryId == sub.id ? 'selected' : ''}>${sub.subCategoryName}</option>
            </c:forEach>
        </select>
    </div>

    <div class="col-md-4">
        <label for="keyword" class="form-label">Search Name</label>
        <input type="text" name="keyword" id="keyword" class="form-control"
               value="${param.keyword}" placeholder="Nhập tên vật tư...">
    </div>
</form>

<!-- Form thêm số lượng vật tư vào session (POST) -->
<form method="post" action="CreateRequestImport">
    <input type="hidden" name="action" value="add" />

    <div class="table-responsive">
        <table class="table table-bordered">
            <thead class="table-light">
            <tr>
                <th>Material Name</th>
                <th>Unit</th>
                <th>Supplier</th>
                <th>Sub Category</th>
                <th>In Stock</th>
                <th>Quantity</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="m" items="${materialItemList}">
                <tr>
                    <td>${m.materialSupplier.materialId.name}</td>
                    <td>${m.materialSupplier.materialId.unitId.unitName}</td>
                    <td>${m.materialSupplier.supplierId.name}</td>
                    <td>${m.materialSupplier.materialId.subCategoryId.subCategoryName}</td>
                    <td>${m.quantity}</td>
                    <td>
                        <input type="hidden" name="materialItemIds" value="${m.id}" />
                        <input type="number" name="quantities[${m.id}]" class="form-control"
                               min="0"  value="${m.selectedQuantity != null ? m.selectedQuantity : 0}" />
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Nút Add -->
    <button type="submit" class="btn btn-warning mt-2">Add Selected Items</button>
</form>

<!-- Form tạo yêu cầu nhập kho (tách biệt) -->
<form method="post" action="CreateRequestImport" class="mt-4">
    <input type="hidden" name="action" value="create" />

    <div class="mb-3">
        <label for="note" class="form-label">Note</label>
        <textarea name="note" id="note" class="form-control" rows="3" required></textarea>
    </div>

    <button type="submit" class="btn btn-primary">Create Request</button>
    <a href="requestList" class="btn btn-secondary">Cancel</a>
</form>



            </div>
        </div>
    </div>
</div>
<!-- Table End -->


<%@include file="../template/footer.jsp" %>
        </div>
        <!-- Content End -->


        <!-- Back to Top -->
        <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
    </div>

   <%@include file="../template/script.jsp" %>
    <script>
        

function sortTable(colrmn) {
    var table = document.getElementById("userTable"); // lấy bảng theo ID
    var rows = Array.from(table.rows).slice(1); // lấy tất cả dòng (trừ tiêu đề)
    var isAsc = table.getAttribute("data-sort") !== "asc"; // kiểm tra tăng hay giảm

    // Sắp xếp các dòng
    rows.sort(function(rowA, rowB) {
        var cellA = rowA.cells[colrmn].innerText.toLowerCase();
        var cellB = rowB.cells[colrmn].innerText.toLowerCase();
        
        if(!isNaN(cellA) && !isNaN(cellB)) { // Nếu là số thì so sánh số
            return (cellA - cellB) * (isAsc ? 1 : -1);
        }
        return (cellA > cellB ? 1 : -1) * (isAsc ? 1 : -1); // So sánh chữ
    });

    // Đưa các dòng đã sắp xếp lại vào bảng
    var tbody = table.tBodies[0];
    rows.forEach(function(row) {
        tbody.appendChild(row);
    });

    // Lưu trạng thái sắp xếp cho lần click sau
    table.setAttribute("data-sort", isAsc ? "asc" : "desc");
}
</script>
</body>

</html>