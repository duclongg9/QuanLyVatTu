<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
<%@page contentType="text/html; charset=UTF-8" %>
    <title>User List</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Favicon -->
    <link href="assets/img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">
    
    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="assets/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="assets/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

    <!-- Customized Bootstrap Stylesheet -->
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>

<body>
    <div class="container-fluid position-relative bg-white d-flex p-0">
<%@include file="../template/spinner.jsp" %>


<%@include file="../template/sidebar.jsp" %>


        <!-- Content Start -->
        <div class="content">
<%@include file="../template/navbar.jsp" %>


            <!-- Table Start -->
            <div class="container-fluid pt-4 px-4">
                <div class="row g-4">
                   
                    <div class="col-12">
                        <div class="bg-light rounded h-100 p-4">
                            <h6 class="mb-4">User</h6>
                           <a href="Create" class="btn btn-primary m-2">Add User</a>
                            <form action="userList" method="get" class="d-flex align-items-center gap-2">
                                <select name="roleFilter" class="form-select" style="width: 200px;" onchange="this.form.submit()">
                                    <option value="">-- All Roles --</option>
<c:forEach var="r" items="${listRole}">
                                        <option value="${r.id}" ${param.roleFilter == r.id ? 'selected' : ''}>${r.roleName}</option>
</c:forEach>
                                </select>
                                <input class="form-control border-0" type="search" placeholder="Search" name="search" value="${param.search}">

                                <button type="submit" class="btn btn-primary">Search</button>
                            </form>
                           
                            <div class="table-responsive">
                                <table class="table" id="userTable">
                                    <thead>
                                        <tr>
                                            <th scope="col" onclick="sortTable(0)" >ID</th>
                                            <th scope="col" onclick="sortTable(1)">Name</th>
                                            <th scope="col" onclick="sortTable(2)">Role</th>
                                            <th scope="col" onclick="sortTable(3)">Status</th>
                                            <th scope="col" onclick="sortTable(4)">Email</th>
                                            <th scope="col" onclick="sortTable(5)">Edit</th>
                                        </tr>
                                    </thead>
                                    <tbody>
<c:forEach var="lu" items="${listUser}">
                                        <tr>
                                            <th scope="row">${lu.id}</th>
                                            <td>${lu.fullName}</td>
                                            <td>${lu.role.roleName}</td>
                                            <td>
    <c:if test="${lu.status==true}">
                                                    <span style="color:green;">Active</span>
    </c:if>
    <c:if test="${lu.status==false}">
                                                    <span style="color:red;">Inactive</span>
    </c:if>
                                            </td>
                                            <td>${lu.email}</td>
                                            <td><a href="Update?uid=${lu.id}" class="delete-btn">Update</a> <a a
                                                    href="userList?id=${lu.id}&action=delete"
                                                    onclick="return confirm('Bạn có chắc muốn đổi trạng thái tài khoản không')"
                                                    class="delete-btn">Change Status</a> </td>
                                        </tr>
</c:forEach>
                                    </tbody>
                                </table>
                                <!-- Pagination -->
                                <div class="btn-group me-2" role="group" aria-label="First group">
                                     <div>
<c:forEach begin="1" end="${endP}" var="i">
                                            <a href="userList?index=${i}&roleFilter=${param.roleFilter}&search=${param.search}" class="btn btn-primary">${i}</a>
</c:forEach>
                                     </div>
                                </div>
                                
                               
                                <!-- Pagination -->
                            </div>
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

    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/chart/chart.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/easing/easing.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/waypoints/waypoints.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/tempusdominus/js/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/tempusdominus/js/moment-timezone.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

    <!-- Template Javascript -->
    <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
    <script>
        

function sortTable(column) {
    var table = document.getElementById("userTable"); // lấy bảng theo ID
    var rows = Array.from(table.rows).slice(1); // lấy tất cả dòng (trừ tiêu đề)
    var isAsc = table.getAttribute("data-sort") !== "asc"; // kiểm tra tăng hay giảm

    // Sắp xếp các dòng
    rows.sort(function(rowA, rowB) {
        var cellA = rowA.cells[column].innerText.toLowerCase();
        var cellB = rowB.cells[column].innerText.toLowerCase();
        
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