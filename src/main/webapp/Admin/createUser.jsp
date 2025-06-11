<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
   
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
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

    <!-- Customized Bootstrap Stylesheet -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>

<body>
    <div class="container-fluid position-relative bg-white d-flex p-0">
        <%@include file="../spinner.jsp" %>


        <%@include file="../sidebar.jsp" %>


        <!-- Content Start -->
        <div class="content">
            <%@include file="../navbar.jsp" %>

            <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/Create">
        <div class="row">
            <div class="col-sm-6">
                <div class="bg-light rounded h-100 p-4">

                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" name="fullname" placeholder="Nguyễn Văn A" required>
                        <label for="floatingInput1">Name</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control"  name="username" placeholder="NVa1990" required>
                        <label for="floatingPassword1">username</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="email" class="form-control" name="email" placeholder="name@example.com"required>
                        <label for="floatingInput1">Email address</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" name="phone" placeholder="0879388939"required>
                        <label for="floatingInput1">Phone</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" name="address" placeholder="name@example.com"required>
                        <label for="floatingInput1">Address</label>
                    </div>
                     <div class="form-floating mb-3">
                         <input type="file" class="form-control" name="image" accept="image/*" >
                    </div>
                </div>
            </div>

            <div class="col-sm-6">
                <div class="bg-light rounded h-100 p-4">
                    <div class="form-floating mb-3">
                        <select class="form-select"  name="gender" aria-label="Floating label select example">
                            <option value="true" selected>Male</option>
                            <option value="false">Female</option>
                        </select>
                       
                    </div>
                     <div class="form-floating mb-3">
                         <select class="form-select"  name="roleId" aria-label="Floating label select example">
                            <option value="" disabled selected>Select role</option>
                            <c:forEach var="r" items="${listRole}">
                            <option value="${r.id}">${r.roleName}</option>
                            </c:forEach>
                        </select>
                        
                    </div>
                     <div class="form-floating mb-3">
                        <input type="date" class="form-control" name="birthDate" placeholder="name@example.com">
                    </div>
                     <div class="form-floating mb-3">
                        <select class="form-select"  name="status" aria-label="Floating label select example">
                            <option value="true" selected>Active</option>
                            <option value="false">Inactive</option>
                        </select>
                       
                    </div>
                    <div class="form-floating mb-3">
                        <button type="submit" class="btn btn-success rounded-pill m-2">Submit</button>
                     <button type="button" class="btn btn-secondary rounded-pill m-2" onclick="history.back()">Cancel</button>
                        <div style="color: red">
                            <c:if test="${not empty error}">
                                ${error}
                            </c:if>
                        </div>
                  </div>
                </div>
            </div>
        </div>
        
           

        
</form>
            <%@include file="../footer.jsp" %>
        </div>
        <!-- Content End -->


        <!-- Back to Top -->
        <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
    </div>

    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/lib/chart/chart.min.js"></script>
    <script src="${pageContext.request.contextPath}/lib/easing/easing.min.js"></script>
    <script src="${pageContext.request.contextPath}/lib/waypoints/waypoints.min.js"></script>
    <script src="${pageContext.request.contextPath}/lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="${pageContext.request.contextPath}/lib/tempusdominus/js/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/lib/tempusdominus/js/moment-timezone.min.js"></script>
    <script src="${pageContext.request.contextPath}/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

    <!-- Template Javascript -->
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>

</html>