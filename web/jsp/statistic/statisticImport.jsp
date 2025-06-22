<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Thống kê Nhập kho</title>
        <link href="Admin2/css/bootstrap.min.css" rel="stylesheet">
        <link href="Admin2/css/style.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    </head>
    <body class="d-flex flex-column min-vh-100">
    <div class="container-fluid position-relative d-flex flex-grow-1 p-0">
        
        <%@ include file="Admin2/spinner.jsp" %>

        <div class="container-fluid position-relative d-flex p-0">
            <!-- Sidebar -->
            <jsp:include page="Admin2/sidebar.jsp" />

            <!-- Content -->
            <div class="content">
                <!-- Navbar -->
                <jsp:include page="Admin2/navbar.jsp" />

                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded p-4">

                        <h4 class="mb-4">Thống kê Nhập kho</h4>

                        <!-- Form lọc -->
                        <form method="get" action="statistic" class="row g-3">
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

                        <!-- Bảng thống kê -->
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

                        <!-- Biểu đồ theo loại -->
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

                        <!-- Biểu đồ theo ngày -->
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

                <!-- Footer -->
                <jsp:include page="Admin2/footer.jsp" />
            </div>
        </div> 
        </div>

        <!-- Biểu đồ nhập theo loại -->
        <c:if test="${not empty importByCategory}">
            <script>
                const categoryLabels = [];
                const categoryData = [];

                <c:forEach var="c" items="${importByCategory}">
                categoryLabels.push("${c.category}");
                categoryData.push(${c.quantity});
                </c:forEach>

                new Chart(document.getElementById('importByCategoryChart').getContext('2d'), {
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
            </script>
        </c:if>

        <!-- Biểu đồ nhập theo ngày -->
        <c:if test="${not empty importByDate}">
            <script>
                const dateLabels = [];
                const dateData = [];

                <c:forEach var="d" items="${importByDate}">
                dateLabels.push("${d.materialName}");
                dateData.push(${d.quantity});
                </c:forEach>

                new Chart(document.getElementById('importByDateChart').getContext('2d'), {
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
            </script>
        </c:if>
        <script src="js/jquery-3.4.1.min.js"></script>
        <!-- Waypoints -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/waypoints/4.0.1/jquery.waypoints.min.js"></script>

        <!-- Owl Carousel -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>
        <!-- Bootstrap Bundle -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="lib/chart/chart.min.js"></script>
        <!-- Main JS -->
        <script src="js/main.js"></script>
    
    </body>
</html>
