<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.List" %>
<%@ page import="model.Supplier" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>DASHMIN - Bootstrap Admin Template</title>
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
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

    <!-- Customized Bootstrap Stylesheet -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="assets/css/style.css" rel="stylesheet">
    </head>

    <body>
        <div class="container-fluid position-relative bg-white d-flex p-0">
            <!-- Spinner Start -->
            <%@include file="../template/spinner.jsp" %>
            <!-- Spinner End -->


            <!-- Sidebar Start -->
            <%@include file="../template/sidebar.jsp" %>
            <!-- Sidebar End -->


            <!-- Content Start -->
            <div class="content">
                <!-- Navbar Start -->
                <%@include file="../template/navbar.jsp" %>
                <!-- Navbar End -->

                <!-- Supplier List Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded p-4">
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <h3 class="mb-0">Update Supplier</h3>
                        </div>



                        <form action="${pageContext.request.contextPath}/processupdatesupplier" method="post">
                            <input type="hidden" name="id" value="${supplier.id}" />

                            <div class="mb-3">
                                <label class="form-label">Name:</label>
                                <input type="text" class="form-control" name="name" value="${supplier.name}" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Phone:</label>
                                <input type="text" class="form-control" name="phone" value="${supplier.phone}" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Address:</label>
                                <input type="text" class="form-control" name="address" value="${supplier.address}" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Status:</label>
                                <select name="status" class="form-select">
                                    <option value="true" ${supplier.status ? "selected" : ""}>Active</option>
                                    <option value="false" ${!supplier.status ? "selected" : ""}>Inactive</option>
                                </select>
                            </div>
                            <!-- Hi?n th? th�ng b�o l?i (n?u c�) -->
                            <c:if test="${not empty msg}">
                                <div class="alert alert-danger">${msg}</div>
                            </c:if>
                            <button type="submit" class="btn btn-primary">Update</button>
                        </form>

                    </div>
                </div>
                <!-- Supplier List End -->






                <!-- Footer Start -->
                <%@include file="../template/footer.jsp" %>
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
        <script src="assets/js/main.js"></script>

    </body>

</html>