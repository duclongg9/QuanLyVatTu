<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Statistic" %>
<%@ page import="dao.statistic.StatisticDAO" %>
<%@ page import="java.sql.Date" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Thống kê Nhập kho</title>
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
    <body class="d-flex flex-column min-vh-100">
    <div class="container-fluid position-relative d-flex flex-grow-1 p-0">
        
        <%@ include file="../template/spinner.jsp" %>

        <div class="container-fluid position-relative d-flex p-0">
            
            <%@ include file="../template/sidebar.jsp" %>

            
            <div class="content">
                
                <%@ include file="../template/navbar.jsp" %>

                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded p-4">

                        <h4 class="mb-4">Thống kê Nhập kho</h4>

 <%
    String from = request.getParameter("from");
    String to = request.getParameter("to");
    if (from != null && to != null) {
        java.sql.Date d1 = java.sql.Date.valueOf(from);
        java.sql.Date d2 = java.sql.Date.valueOf(to);
        dao.statistic.StatisticDAO dao = new dao.statistic.StatisticDAO();

        
        List<model.Statistic> importByCategory   = dao.getImportStatistic(d1, d2);
        List<model.Statistic> statByUser = dao.getImportByUser(d1, d2);
        List<model.Statistic> statByCategory = dao.getImportByCategory(d1, d2);
        List<model.Statistic> importByDate = dao.getImportByDate(d1, d2);

        request.setAttribute("importByCategory", importByCategory);
        request.setAttribute("statByUser", statByUser);
        request.setAttribute("statByCategory", statByCategory);
        request.setAttribute("importByDate", importByDate);

       
    }
%>


            <c:if test="${not empty param.from and not empty param.to}">
    <c:choose>
        <c:when test="${empty statByUser and empty statByCategory}">
            <div class="alert alert-warning">
                Không có dữ liệu thống kê từ ${param.from} đến ${param.to}
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-success">
                Có dữ liệu thống kê từ ${param.from} đến ${param.to}
            </div>
        </c:otherwise>
    </c:choose>
</c:if>


                        <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>

                        <form method="get" action="${pageContext.request.contextPath}/jsp/statistic/statisticImport.jsp" class="row g-3">



                            <input type="hidden" name="action" value="import"/>

                            <div class="col-md-4">
                                <label>Từ ngày:</label>
                                <input type="date" name="from" class="form-control" required>
                            </div>
                            <div class="col-md-4">
                                <label>Đến ngày:</label>
                                <input type="date" name="to" class="form-control" required>
                            </div>
                            <div class="col-md-4 d-flex align-items-end">
                                <button type="submit" class="btn btn-primary">Thống kê</button>
                            </div>
                        </form>

                        
                        <c:if test="${not empty statisticList}">
                            <div class="table-responsive mt-4">
                                <table class="table table-bordered table-hover">
                                    <thead class="table-dark">
                                        <tr>
                                            <th>Tên vật tư</th>
                                            <th>Số lượng</th>
                                            <th>Đơn vị</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="s" items="${statisticList}">
                                            <tr>
                                                <td>${s.materialName}</td>
                                                <td>${s.quantity}</td>
                                                <td>${s.unitName}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:if>

                        <c:if test="${not empty statByUser}">
  <div class="mt-5 mb-2">
    <h5 class="text-success">👤 Thống kê theo người nhập</h5>
</div>
    <table class="table table-bordered">
        <thead><tr><th>Người nhập</th><th>Tổng số lượng</th></tr></thead>
        <tbody>
            <c:forEach var="s" items="${statByUser}">
                <tr>
                    <td>${s.user}</td>
                    <td>${s.quantity}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>
    
    <c:if test="${not empty statByCategory}">
    <div class="mt-5 mb-2">
        <h5 class="text-primary">📂 Thống kê theo loại vật tư</h5>
    </div>
    <table class="table table-bordered">
        <thead><tr><th>Loại vật tư</th><th>Số lượng</th></tr></thead>
        <tbody>
            <c:forEach var="s" items="${statByCategory}">
                <tr>
                    <td>${s.category}</td>
                    <td>${s.quantity}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

    
                        <c:if test="${not empty importByCategory}">
                            <div class="card mt-5">
                                <div class="card-header">
                                    <h5 class="card-title">Biểu đồ nhập kho theo loại vật tư</h5>
                                </div>
                                <div class="card-body">
                                    <canvas id="importByCategoryChart" height="120"></canvas>
                                </div>
                            </div>
                        </c:if>

                        
                        <c:if test="${not empty importByDate}">
                            <div class="card mt-5">
                                <div class="card-header">
                                    <h5 class="card-title">Biểu đồ nhập kho theo ngày</h5>
                                </div>
                                <div class="card-body">
                                    <canvas id="importByDateChart" height="120"></canvas>
                                </div>
                            </div>
                        </c:if>
                        
                    </div>
                    
                </div>

                
                <%@ include file="../template/footer.jsp" %>
            </div>
        </div> 
        </div>

        
        <c:if test="${not empty importByCategory}">
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const ctx = document.getElementById('importByCategoryChart');
            if (ctx) {
                const categoryLabels = [];
                const categoryData = [];

                <c:forEach var="c" items="${importByCategory}">
                    categoryLabels.push("${c.category}");
                    categoryData.push(${c.quantity});
                </c:forEach>

                new Chart(ctx.getContext('2d'), {
                    type: 'bar',
                    data: {
                        labels: categoryLabels,
                        datasets: [{
                            label: 'Số lượng nhập',
                            data: categoryData,
                            backgroundColor: '#0d6efd',
                            borderRadius: 4
                        }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            title: {
                                display: true,
                                text: 'Tổng số lượng nhập theo loại vật tư'
                            }
                        }
                    }
                });
            }
        });
    </script>
</c:if>

<c:if test="${not empty importByDate}">
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const ctx = document.getElementById('importByDateChart');
            if (ctx) {
                const dateLabels = [];
                const dateData = [];

                <c:forEach var="d" items="${importByDate}">
                    dateLabels.push("${d.materialName}");
                    dateData.push(${d.quantity});
                </c:forEach>

                new Chart(ctx.getContext('2d'), {
                    type: 'line',
                    data: {
                        labels: dateLabels,
                        datasets: [{
                            label: 'Số lượng nhập',
                            data: dateData,
                            borderColor: '#198754',
                            backgroundColor: 'rgba(25,135,84,0.2)',
                            fill: true,
                            tension: 0.3
                        }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            title: {
                                display: true,
                                text: 'Số lượng nhập theo từng ngày'
                            }
                        }
                    }
                });
            }
        });
    </script>
</c:if>

            
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const toggleBtn = document.querySelector(".sidebar-toggler");
        const sidebar = document.querySelector(".sidebar");
        const content = document.querySelector(".content");

        if (toggleBtn && sidebar && content) {
            toggleBtn.addEventListener("click", function () {
                sidebar.classList.toggle("open");
                content.classList.toggle("open");
            });
        }
    });
</script>

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
    window.addEventListener('load', function () {
        var spinner = document.getElementById('spinner');
        if (spinner) {
            spinner.classList.remove('show');
        }
    });
</script>

    </body>

</html>