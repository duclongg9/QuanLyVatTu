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

                <form action="CreateRequestImport" method="post">
                    <!-- Note input -->
                    <div class="mb-3">
                        <label for="note" class="form-label">Note:</label>
                        <textarea name="note" id="note" class="form-control" required></textarea>
                    </div>

                    <div class="table-responsive">
                        <table id="userTable" class="table table-bordered">
                            <thead>
                                <tr>
                                    <th scope="col" onclick="sortTable(0)">Material ID</th>
                                    <th scope="col" onclick="sortTable(1)">Material Name</th>
                                    <th scope="col" onclick="sortTable(2)">Unit</th>
                                    <th scope="col" onclick="sortTable(2)">Supplier</th>
                                    <th scope="col" onclick="sortTable(3)">Category</th>
                                    <th>Input Quantity</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="m" items="${listMaterialRequest}">
                                    <tr>
                                        <td>${m.materialId.id}</td>
                                        <td>${m.materialId.name}</td>
                                        <td>${m.supplierId.name}</td>
                                        <td>${m.materialId.categoryId.category}</td>
                                        <td>
                                            <input type="number" min="0" name="material_${m.id}" class="form-control" placeholder="Enter quantity">
                                        </td>
                                        <td>${m.materialId.unitId.unitName}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <!-- Pagination -->
                        <div class="d-flex justify-content-center mt-3">
                            <c:forEach begin="1" end="${endP}" var="i">
                                <a href="CreateRequestImport?index=${i}" class="btn btn-outline-primary mx-1">${i}</a>
                            </c:forEach>
                        </div>
                        <!-- Pagination End -->
                    </div>

                    <button type="submit" class="btn btn-primary">Create Request</button>
                     <button type="button" class="btn btn-secondary rounded-pill m-2" onclick="history.back()">Cancel</button>
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