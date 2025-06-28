<%-- 
    Document   : statisticExport
    Created on : Jun 16, 2025, 12:02:02 PM
    Author     : KIET
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.*"%>
<%@page import="model.ExportStatistic"%>

<!DOCTYPE html>
<html lang="en">
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thống kê Xuất kho</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

        
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">


    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" />

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

                        
                        <form method="post" action="statistic" class="row g-3">
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
                <nav>
                    <ul class="pagination justify-content-center">
                        <c:forEach begin="1" end="${totalPage}" var="i">
                            <li class="page-item ${page == i ? 'active' : ''}">
                                <a class="page-link" href="statistic?page=${i}">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
                
                <%@ include file="../template/footer.jsp" %>
            </div>
        </div>
        </div>
        
        <script src="js/jquery-3.4.1.min.js"></script>

        
        <script src="https://cdnjs.cloudflare.com/ajax/libs/waypoints/4.0.1/jquery.waypoints.min.js"></script>

        
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>

        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="lib/chart/chart.min.js"></script>
        
        <script src="js/main.js"></script>


        




        
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



    </body>



</html>

