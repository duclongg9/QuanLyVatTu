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
                            <h6 class="mb-4">Request</h6>
                            <div class="d-flex g-0">
                            <form action="requestList" method="get">
                                <h5>Status:</h5>
                                <select class="form-select mb-3" aria-label="Default select example" name="requestStatus">
                                <option selected="0">select status of request</option>
                                <c:forEach var="status" items="">
                                    <option value="1">One</option>
                                </c:forEach>
                                
                            </select>
                                <h5>Type:</h5>
                                <select class="form-select mb-3" aria-label="Default select example" name="requestType">
                                <option selected="0">select type of request</option>
                                <c:forEach var="status" items="">
                                    <option value="1">One</option>
                                </c:forEach>
                                
                            </select>
                                
                            </form>
                                </div>
                            <div class="table-responsive">
                                
                                <table class="table" id="userTable">
                                    <thead>
                                        <tr>
                                            <th scope="col" onclick="sortTable(0)" >ID</th>
                                            <th scope="col" onclick="sortTable(1)">USER</th>
                                            <th scope="col" onclick="sortTable(2)">TYPE</th>
                                            <th scope="col" onclick="sortTable(3)">DATE</th>
                                            <th scope="col" onclick="sortTable(4)">NOTE</th>
                                            <th scope="col" onclick="sortTable(6)">STATUS</th>
                                            <th scope="col" onclick="sortTable(7)">APPROVED BY</th>
                                            <th scope="col" >Edit</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                       <c:forEach var="lr" items="${listRequest}">
                                        <tr>
                                            <th scope="row">${lr.id}</th>
                                            <td>${lr.userId.fullName}</td>
                                            <td>${lr.type}</td>
                                            <td>${lr.date}</td>
                                            <td>${lr.note}</td>
                                            <td>${lr.statusId.status}</td>
                                            <td>${lr.approvedBy.fullName}</td>
                                            <td><a href="requestDetailController?requestId=${lr.id}" class="delete-btn">Detail</a> </td>
                                        </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <!-- Pagination -->
                                <div class="btn-group me-2" role="group" aria-label="First group">
                                     <div>
                                        <c:forEach begin="1" end="${endP}" var="i">
                                            <a href="requestList?index=${i}" class="btn btn-primary">${i}</a>
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