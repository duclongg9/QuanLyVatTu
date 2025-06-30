<%-- 
    Document   : statisticExport
    Created on : Jun 16, 2025, 12:02:02 PM
    Author     : KIET
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.*"%>
<%@page import="model.Statistic"%>

<!DOCTYPE html>
<html lang="en">
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thống kê Xuất kho</title>
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
        
            
        <div class="container-fluid position-relative d-flex p-0">
<div class="container-fluid position-relative d-flex p-0">
            
            <%@ include file="../template/sidebar.jsp" %>

            
            <div class="content">
                
                <%@ include file="../template/navbar.jsp" %>

                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded p-4">

                        <h4 class="mb-4">Thống kê Xuất kho</h4>

                        
                        <form method="post" action="${pageContext.request.contextPath}/statistic" class="row g-3">
                            <input type="hidden" name="action" value="export">

                            <div class="col-md-3">
                                <label>Từ ngày:</label>
                                <input type="date" name="fromDate" class="form-control" required>
                            </div>
                            <div class="col-md-3">
                                <label>Đến ngày:</label>
                                <input type="date" name="toDate" class="form-control" required>
                            </div>
                            <div class="col-md-3">
                                <label>Loại vật tư:</label>
                                <select name="categoryId" class="form-select">
                                    <option value="0">Tất cả</option>
                                    
                                    <c:forEach var="c" items="${categoryList}">
                                        <option value="${c.id}">${c.category}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <label>Người dùng:</label>
                                <select name="userId" class="form-select">
                                    <option value="0">Tất cả</option>
                                    
                                    <c:forEach var="u" items="${userList}">
                                        <option value="${u.id}">${u.fullname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-12 text-end">
                                <button type="submit" class="btn btn-primary mt-3">Thống kê</button>
                            </div>
                        </form>

                        
                        <c:if test="${not empty exportStats}">
                            <div class="table-responsive mt-4">
                                <table class="table table-bordered table-hover">
                                    <thead class="table-dark">
                                        <tr>
                                            <th>Tên vật tư</th>
                                            <th>Loại</th>
                                            <th>Số lượng</th>
                                            <th>Đơn vị</th>
                                            <th>Người xuất</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="s" items="${exportStats}">
                                            <tr>
                                                <td>${s.materialName}</td>
                                                <td>${s.category}</td>
                                                <td>${s.quantity}</td>
                                                <td>${s.unitName}</td>
                                                <td>${s.user}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            
                            <div class="card mt-5">
                                <div class="card-header">
                                    <h5 class="card-title">Biểu đồ số lượng vật tư đã xuất</h5>
                                </div>
                                <div class="card-body">
                                    <canvas id="exportChart" height="120"></canvas>
                                </div>
                            </div>
                        </c:if>


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


        




        
        <c:if test="${not empty exportStats}">
            <script>
                const labels = [];
                const data = [];

                <c:forEach var="s" items="${exportStats}">
                labels.push("${s.materialName}");

                data.push(${s.quantity});
                </c:forEach>

                const ctx = document.getElementById('exportChart').getContext('2d');
                new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: labels,
                        datasets: [{
                                label: 'Số lượng xuất',
                                data: data,
                                backgroundColor: '#0d6efd',
                                borderRadius: 4
                            }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            title: {
                                display: true,
                                text: 'Thống kê số lượng vật tư đã xuất'
                            }
                        }
                    }
                });
                console.log("Labels:", labels);
                console.log("Data:", data);

            </script>
        </c:if>

    <script>
    // Spinner
    var spinner = function () {
        setTimeout(function () {
            const spinner = document.getElementById('spinner');
            if (spinner) {
                spinner.classList.remove('show');
            }
        }, 1); // thời gian nhỏ để đợi DOM load
    };
    spinner();
</script>

    </body>



</html>

