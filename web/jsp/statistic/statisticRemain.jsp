<%-- 
    Document   : statisticRemain
    Created on : Jun 23, 2025, 12:44:25 AM
    Author     : KIET
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, dao.StatisticDAO"%>

<%
    StatisticDAO dao = new StatisticDAO();
    List<Map<String, Object>> remainStats = dao.getRemainByCategoryAndStatus();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thống kê tồn kho</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <link href="Admin2/css/style.css" rel="stylesheet">
</head>
<body>
    <%@ include file="Admin2/spinner.jsp" %>
<div class="container-xxl position-relative bg-white d-flex p-0">
<div class="container-fluid position-relative d-flex p-0">
    <!-- Sidebar -->
    <jsp:include page="Admin2/sidebar.jsp"/>
    <!-- Content -->
    <div class="content">
        <!-- Navbar -->
        <jsp:include page="Admin2/navbar.jsp"/>

        <div class="container-fluid pt-4 px-4">
            <div class="bg-light rounded p-4">
                <h4 class="mb-4">Thống kê tồn kho</h4>

                <!-- Bảng dữ liệu -->
                <div class="table-responsive">
                    <table class="table table-bordered table-hover">
                        <thead class="table-dark">
                        <tr>
                            <th>Loại vật tư</th>
                            <th>Tình trạng</th>
                            <th>Số lượng</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            for (Map<String, Object> row : remainStats) {
                        %>
                        <tr>
                            <td><%= row.get("category") %></td>
                            <td><%= row.get("status") %></td>
                            <td><%= row.get("total") %></td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>

                <!-- Biểu đồ tồn kho -->
                <div class="card mt-5">
                    <div class="card-header">
                        <h5 class="card-title">Biểu đồ tồn kho theo loại và tình trạng</h5>
                    </div>
                    <div class="card-body">
                        <canvas id="remainChart" height="120"></canvas>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="Admin2/footer.jsp"/>
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

<!-- Vẽ biểu đồ bằng Chart.js -->
<script>
    const remainLabels = [];
    const datasetsMap = {};

    <% for (Map<String, Object> row : remainStats) {
        String category = (String) row.get("category");
        String status = (String) row.get("status");
        int total = (Integer) row.get("total");
    %>
    if (!datasetsMap["<%= status %>"]) datasetsMap["<%= status %>"] = {};
    if (!remainLabels.includes("<%= category %>")) remainLabels.push("<%= category %>");
    datasetsMap["<%= status %>"]["<%= category %>"] = <%= total %>;
    <% } %>

    // Chuyển dữ liệu thành định dạng Chart.js datasets
    const statusKeys = Object.keys(datasetsMap);
    const datasets = statusKeys.map((status, idx) => {
        const colorPalette = ['#0d6efd', '#20c997', '#ffc107', '#dc3545', '#6610f2'];
        return {
            label: status,
            data: remainLabels.map(cat => datasetsMap[status][cat] || 0),
            backgroundColor: colorPalette[idx % colorPalette.length],
            borderRadius: 4
        };
    });

    const ctx = document.getElementById('remainChart').getContext('2d');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: remainLabels,
            datasets: datasets
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Tồn kho theo loại và tình trạng'
                }
            },
            scales: {
                x: { stacked: true },
                y: { stacked: true }
            }
        }
    });
</script>
</body>
</html>
