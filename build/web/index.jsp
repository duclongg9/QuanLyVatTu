<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="utf-8">
    <title>Quản Lý Vật Tư</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Favicon -->
    <link href="${pageContext.request.contextPath}/assets/img/favicon.ico" rel="icon">

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
    <div class="container-fluid position-relative bg-white d-flex p-0">
        <%@ include file="jsp/template/spinner.jsp" %>
        <%@ include file="jsp/template/sidebar.jsp" %>

        <!-- Content Start -->
        <div class="content">
            <%@ include file="jsp/template/navbar.jsp" %>


<%
    String pageParam = request.getParameter("page");
    String pagePath;
    if(pageParam == null || pageParam.isEmpty()) {
        pagePath = "jsp/dashboard.jsp";
    } else if("materials".equals(pageParam)) {
        pagePath = "jsp/material/listMaterials.jsp";
    } else if("users".equals(pageParam)) {
        pagePath = "jsp/user/listUser.jsp";
    } else if("units".equals(pageParam)) {
        pagePath = "jsp/unit/unitList.jsp";
    } else if("requests".equals(pageParam)) {
        pagePath = "jsp/request/requestList.jsp";
    } else {
        pagePath = "jsp/dashboard.jsp";
    }
%>
<jsp:include page="<%= pagePath %>" />

        </div>
        <!-- Content End -->


        <!-- Back to Top -->
        <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
    </div>

    <%@ include file="jsp/template/script.jsp" %>