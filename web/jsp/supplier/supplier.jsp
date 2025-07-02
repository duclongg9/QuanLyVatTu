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
                    <div class="bg-light text-center rounded p-4">
                        <div class="mb-4 text-start">
    <h3 class="mb-3">Supplier List</h3>
    <a href="${pageContext.request.contextPath}/addsupplier" class="btn btn-success">
        <i class="bi bi-plus-circle"></i> Add Supplier
    </a>
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
 
</body>

</html>