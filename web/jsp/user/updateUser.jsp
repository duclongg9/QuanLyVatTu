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

            <form method="post"  action="${pageContext.request.contextPath}/Update">
                <input type="hidden" name="id" value="${user.id}">
        <div class="row">
            <div class="col-sm-6">
                <div class="bg-light rounded h-100 p-4">

                   <div class="testimonial-item text-center">
                                    <img class="img-fluid rounded-circle mx-auto mb-4" src="${pageContext.request.contextPath}/images/${user.image}" style="width: 100px; height: 100px;">
                                    <h5 class="mb-1">${user.fullName}</h5>
                                    <p>${user.role.roleName}</p>
                                    
                    </div>

                     <div class="form-floating mb-3">
                         <div class="bg-light rounded h-100 p-4">
                            <h6 class="mb-4">Profile</h6>
                            <table class="table">
                               
                                <tbody>
                                    <tr>
                                        <th scope="row">Email</th>
                                        <th scope="row">${user.email}</th>
                                        
                                    </tr>
                                     <tr>
                                        <th scope="row">Gender</th>
                                        <th scope="row"> 
                                                <c:if test="${user.gender==true}">
                                                    <a>Male</a>
                                                </c:if>
                                                <c:if test="${user.gender==false}">
                                                    <a>Female</a>
                                                </c:if>
                                        </th>
                                        
                                    </tr>
                                    <tr>
                                        <th scope="row">Phone</th>
                                        <th scope="row">${user.phone}</th>
                                        
                                    </tr>
                                    <tr>
                                        <th scope="row">Address</th>
                                        <th scope="row">${user.address}</th>
                                        
                                    </tr>
                                    <tr>
                                        <th scope="row">Birth Date</th>
                                        <th scope="row">${user.birthDate}</th>
                                        
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-6">
                <div class="bg-light rounded h-100 p-4">
                   
                     <div class="form-floating mb-3">
                         <a>Select Role:</a>
                         <select class="form-select"  name="roleId" aria-label="Floating label select example">
                            <option value="" disabled selected>Select role</option>
                            <c:forEach var="r" items="${listRole}">
                            <option value="${r.id}">${r.roleName}</option>
                            </c:forEach>
                        </select>
                        
                    </div>
                     <a>Account Status:</a>
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