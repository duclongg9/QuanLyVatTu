<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.List" %>

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

                <!-- Content Body Start -->
                <div class="container-fluid pt-4 px-4">

                    <div class="bg-light rounded h-100 p-4">
                        <!-- Avatar + FullName -->
                        <div class="testimonial-item text-center">
                            <img class="img-fluid rounded-circle mx-auto mb-4"
                                 src="${pageContext.request.contextPath}/images/${user.image}"
                                 alt="Avatar"
                                 style="width: 100px; height: 100px; object-fit: cover;">
                            <h5 class="mb-1">${user.fullName}</h5>

                        </div>

                        <!-- Form c?p nh?t -->
                        <div class="form-floating mb-3">
                            <div class="bg-light rounded h-100 p-4">
                                <h6 class="mb-4 text-start">Update Profile</h6>

                                <form action="${pageContext.request.contextPath}/updateprofile" method="post" enctype="multipart/form-data">
                                    
                                    <input type="hidden" name="id" value="${user.id}">

                                    <div class="mb-3">
                                        <label>Email</label>
                                        <input name="email" type="email" class="form-control" value="${user.email}" required>
                                    </div>

                                    <div class="mb-3">
                                        <label>Phone</label>
                                        <input name="phone" type="text" class="form-control" value="${user.phone}" required>
                                    </div>

                                    <div class="mb-3">
                                        <label>Address</label>
                                        <input name="address" type="text" class="form-control" value="${user.address}" required>
                                    </div>

                                    <div class="mb-3">
                                        <label>Gender</label>
                                        <select name="gender" class="form-select">
                                            <option value="true" ${user.gender ? 'selected' : ''}>Male</option>
                                            <option value="false" ${!user.gender ? 'selected' : ''}>Female</option>
                                        </select>
                                    </div>

                                    <div class="mb-3">
                                        <label>Birth Date</label>
                                        <input name="birthDate" type="date" class="form-control" value="${user.birthDate}">
                                    </div>


                                    <c:if test="${not empty msg}">
                                        <div class="alert alert-danger">${msg}</div>
                                    </c:if>
                                    <div class="d-flex justify-content-center mt-4">
                                        <a href="${pageContext.request.contextPath}/userprofile" class="btn btn-secondary me-3">
                                            <i class="bi bi-arrow-left me-1"></i> Cancel
                                        </a>
                                        <button type="submit" class="btn btn-success">
                                            <i class="bi bi-save me-1"></i> Save Changes
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                </div>
                <!-- Content Body End -->

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
        <script src="assets/js/main.js"></script>
    </body>


</html>