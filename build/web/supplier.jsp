<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.List" %>
<%@ page import="Model.Supplier" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>DASHMIN - Bootstrap Admin Template</title>
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
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Link CDN Bootstrap Icons -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <!-- Bootstrap Pagination Styling (?ã tích h?p s?n n?u dùng Bootstrap) -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">


        <!-- Template Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
    </head>

    <body>
        <div class="container-fluid position-relative bg-white d-flex p-0">
            <!-- Spinner Start -->
            <%@include file="spinner.jsp" %>
            <!-- Spinner End -->


            <!-- Sidebar Start -->
            <%@include file="sidebar.jsp" %>
            <!-- Sidebar End -->


            <!-- Content Start -->
            <div class="content">
                <!-- Navbar Start -->
                <%@include file="navbar.jsp" %>
                <!-- Navbar End -->

                <!-- Supplier List Start -->
                <div class="container-fluid pt-4 px-4">
    <div class="bg-light text-center rounded p-4">
        <div class="d-flex align-items-center justify-content-between mb-4">
            <h3 class="mb-0">Supplier List</h3>
        </div>

        <!-- FORM TÌM KI?M -->
        <form action="suppliercontroller" method="get" class="mb-3 d-flex justify-content-end">
    <input type="text" name="keyword" class="form-control w-25 me-2" placeholder="Tìm theo tên..."
           value="${keyword != null ? keyword : ''}" />
    
    <select name="status" class="form-select w-25 me-2">
        <option value="all" ${status == 'all' ? 'selected' : ''}>All
        </option>
        <option value="active" ${status == 'active' ? 'selected' : ''}>Active</option>
        <option value="inactive" ${status == 'inactive' ? 'selected' : ''}>Inactive</option>
    </select>

    <button type="submit" class="btn btn-primary">Tìm</button>
</form>

        <!-- DANH SÁCH NHÀ CUNG C?P -->
        <div class="table-responsive">
            <table class="table text-start align-middle table-bordered table-hover mb-0">
                <thead class="text-dark">
                    <tr>
                        <th scope="col">Id</th>
                        <th scope="col">Name</th>
                        <th scope="col">Phone</th>
                        <th scope="col">Address</th>
                        <th scope="col">Image</th>
                        <th scope="col">Status</th>
                        <th scope="col">Edit</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="s" items="${listS}">
                        <tr>
                            <td>${s.id}</td>
                            <td>${s.name}</td>
                            <td>${s.phone}</td>
                            <td>${s.address}</td>
                            <td>
                                <img class="avata" src="${pageContext.request.contextPath}/images/mau.jpg"
                                     alt="anh" style="width: 50px; height: 50px; object-fit: cover;">
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${s.status}">
                                        <span style="color: green;">Active</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: red;">Inactive</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/updatesupplier?sid=${s.id}" 
                                   class="btn btn-sm btn-success rounded-circle me-1">
                                    <i class="bi bi-pencil"></i>
                                </a>
                                <a href="${pageContext.request.contextPath}/deletesupplier?sid=${s.id}&action=delete"
                                   onclick="return confirm('B?n có ch?c mu?n xóa nhà cung c?p này không')"
                                   class="btn btn-sm btn-danger rounded-circle me-1">
                                    <i class="bi bi-trash"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- PHÂN TRANG -->
        <div class="d-flex justify-content-center mt-3">
            <nav>
                <ul class="pagination">
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="suppliercontroller?page=${i}<c:if test='${not empty keyword}'>&keyword=${keyword}</c:if>">
                                ${i}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </div>
    </div>
</div>

                
                
                <!-- Supplier List End -->

                <!-- Footer Start -->
                <%@include file="footer.jsp" %>
                <!-- Footer End -->
            </div>
            <!-- Content End -->


            <!-- Back to Top -->
            <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
        </div>

        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="lib/chart/chart.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/waypoints/waypoints.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="lib/tempusdominus/js/moment.min.js"></script>
        <script src="lib/tempusdominus/js/moment-timezone.min.js"></script>
        <script src="lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

        <!-- Template Javascript -->
        <script src="js/main.js"></script>
    </body>

</html>