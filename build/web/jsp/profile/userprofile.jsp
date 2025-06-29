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

                    <div class="bg-light rounded h-100 p-4">
                        <!-- Avatar + Tên + Vai trò -->
                        <div class="testimonial-item text-center">
                            <img class="img-fluid rounded-circle mx-auto mb-4"
                                 src="${pageContext.request.contextPath}/images/${user.image}"
                                 alt="Avatar"
                                 style="width: 100px; height: 100px; object-fit: cover;">
                            <h5 class="mb-1">${user.fullName}</h5>
                            
                        </div>

                        <!-- B?ng thông tin ng??i dùng -->
                        <div class="form-floating mb-3">
                            <div class="bg-light rounded h-100 p-4">
                                <h6 class="mb-4 text-start">Profile</h6>
                                <table class="table table-bordered">
                                    <tbody>
                                        <tr>
                                            <th scope="row">Email</th>
                                            <td>${user.email}</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Gender</th>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${user.gender}">Male</c:when>
                                                    <c:otherwise>Female</c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Phone</th>
                                            <td>${user.phone}</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Address</th>
                                            <td>${user.address}</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Birth Date</th>
                                            <td>${user.birthDate}</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Role</th>
                                            <td>${user.role.roleName}</td>
                                        </tr>
                                    </tbody>
                                </table>
                                <!-- Nút ch?nh s?a & ??i m?t kh?u -->
                                <div class="d-flex justify-content-center mt-4" >
                                    <a href="${pageContext.request.contextPath}/updateprofile?uid=${user.id}" class="btn btn-primary me-3">
                                        <i class="bi bi-pencil-square me-1"></i> Update Profile
                                    </a>
                                    <a href="${pageContext.request.contextPath}/changepassword?uid=${user.id}" class="btn btn-warning">
                                        <i class="bi bi-lock-fill me-1"></i> Change Password
                                    </a>
                                    <a  href="${pageContext.request.contextPath}/index.jsp" class="btn btn-dark ms-3">
                                         Cancel
                                    </a>
                                </div>
                            </div>
                        </div>
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