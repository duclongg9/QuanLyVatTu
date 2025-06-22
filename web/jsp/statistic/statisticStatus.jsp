<%-- 
    Document   : statisticStatus
    Created on : Jun 23, 2025, 12:17:28 AM
    Author     : KIET
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, dao.StatisticDAO" %>
<%
    StatisticDAO dao = new StatisticDAO();
    Map<String, Integer> statusStats = dao.getMaterialCountByStatus();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Thống kê theo tình trạng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <link href="Admin2/css/style.css" rel="stylesheet">
</head>
<body>
    <%@ include file="Admin2/spinner.jsp" %>
<div class="container-xxl position-relative bg-white d-flex p-0">
<div class="container-fluid position-relative d-flex p-0">

    <!-- Sidebar -->
    <jsp:include page="Admin2/sidebar.jsp" />

    <!-- Content -->
    <div class="content">
        <!-- Navbar -->
        <jsp:include page="Admin2/navbar.jsp" />

        <div class="container-fluid pt-4 px-4">
            <div class="bg-light rounded p-4">
                <h4 class="mb-4">Thống kê vật tư theo tình trạng</h4>

                <!-- Bảng dữ liệu -->
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

                <!-- Biểu đồ -->
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

        <!-- Footer -->
        <jsp:include page="Admin2/footer.jsp" />
    </div>
</div>
</div>
<!-- JS -->
<script src="js/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/waypoints/4.0.1/jquery.waypoints.min.js"></script>

        <!-- Owl Carousel -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>
<script src="lib/chart/chart.min.js"></script>
<script src="js/main.js"></script>

<!-- Chart.js -->
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

</body>
</html>

