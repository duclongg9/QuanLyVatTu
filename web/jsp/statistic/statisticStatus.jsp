<%-- 
    Document   : statisticStatus
    Created on : Jun 23, 2025, 12:17:28 AM
    Author     : KIET
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*, dao.statistic.StatisticDAO" %>
<%
    StatisticDAO dao = new StatisticDAO();
    Map<String, Integer> statusStats = dao.getMaterialCountByStatus();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Thống kê theo tình trạng</title>
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
    <%@ include file="../template/spinner.jsp" %>
<div class="container-xxl position-relative bg-white d-flex p-0">
<div class="container-fluid position-relative d-flex p-0">


    <%@ include file="../template/sidebar.jsp" %>


    <div class="content">
        
        <%@ include file="../template/navbar.jsp" %>

        <div class="container-fluid pt-4 px-4">
            <div class="bg-light rounded p-4">
                <h4 class="mb-4">Thống kê vật tư theo tình trạng</h4>

                
                <div class="table-responsive mt-4">
                    <table class="table table-bordered table-hover">
                        <thead class="table-dark">
                        <tr>
                            <th>Tình trạng</th>
                            <th>Số lượng</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            for (Map.Entry<String, Integer> entry : statusStats.entrySet()) {
                        %>
                        <tr>
                            <td><%= entry.getKey() %></td>
                            <td><%= entry.getValue() %></td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>

                
                <div class="card mt-5">
                    <div class="card-header">
                        <h5 class="card-title">Biểu đồ vật tư theo tình trạng</h5>
                    </div>
                    <div class="card-body">
                        <canvas id="statusChart" height="120"></canvas>
                    </div>
                </div>
            </div>
        </div>

        <%@ include file="../template/footer.jsp" %>
    </div>
</div>
</div>


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
    const labels = [];
    const data = [];
    <% for (Map.Entry<String, Integer> entry : statusStats.entrySet()) { %>
        labels.push("<%= entry.getKey() %>");
        data.push(<%= entry.getValue() %>);
    <% } %>

    const ctx = document.getElementById('statusChart').getContext('2d');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Số lượng',
                data: data,
                backgroundColor: '#198754', // Màu xanh Bootstrap success
                borderRadius: 4
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Thống kê vật tư theo tình trạng'
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Số lượng'
                    }
                }
            }
        }
    });
</script>
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

