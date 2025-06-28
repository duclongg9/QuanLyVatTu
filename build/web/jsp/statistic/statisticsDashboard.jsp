<%-- 
    Document   : dashboard
    Created on : Jun 22, 2025, 3:52:35 AM
    Author     : KIET
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Dashboard Tổng hợp</title>

       
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">

        
        
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" />
        
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    </head>

    <body>
        <%@ include file="../template/spinner.jsp" %>

        <div class="container-fluid position-relative d-flex p-0">
            
            <%@ include file="../template/sidebar.jsp" %>

            
            <div class="content d-flex flex-column w-100">
                
<%@ include file="../template/navbar.jsp" %>
                <div class="container-fluid pt-4 px-4">
                    <div class="row g-4">
                        
                        <div class="col-sm-6 col-xl-3">
                            <div class="bg-light rounded d-flex align-items-center justify-content-between p-4">
                                <i class="fa fa-cubes fa-2x text-primary"></i>
                                <div class="ms-3">
                                    <p class="mb-2">Tổng vật tư</p>
                                    <h6 class="mb-0">${totalMaterials}</h6>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-sm-6 col-xl-3">
                            <div class="bg-light rounded d-flex align-items-center justify-content-between p-4">
                                <i class="fa fa-tags fa-2x text-success"></i>
                                <div class="ms-3">
                                    <p class="mb-2">Loại vật tư</p>
                                    <h6 class="mb-0">${totalCategories}</h6>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-sm-6 col-xl-3">
                            <div class="bg-light rounded d-flex align-items-center justify-content-between p-4">
                                <i class="fa fa-balance-scale fa-2x text-warning"></i>
                                <div class="ms-3">
                                    <p class="mb-2">Đơn vị tính</p>
                                    <h6 class="mb-0">${totalUnits}</h6>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-sm-6 col-xl-3">
                            <div class="bg-light rounded d-flex align-items-center justify-content-between p-4">
                                <i class="fa fa-thermometer-half fa-2x text-danger"></i>
                                <div class="ms-3">
                                    <p class="mb-2">Tình trạng</p>
                                    <h6 class="mb-0">${totalStatuses}</h6>
                                </div>
                            </div>
                        </div>
                    </div>

                    
                    <div class="row g-4 mt-3">
                        <
                        <div class="col-lg-6">
                            <div class="bg-light rounded p-4">
                                <h6 class="mb-4">Số lượng vật tư theo tình trạng</h6>
                                <canvas id="statusChart"></canvas>
                            </div>
                        </div>
                        
                        <div class="col-lg-6">
                            <div class="bg-light rounded p-4">
                                <h6 class="mb-4">Tỷ lệ vật tư theo đơn vị tính</h6>
                                <canvas id="unitChart"></canvas>
                            </div>
                        </div>
                    </div>

                    
                    <div class="row g-4 mt-2">
                        <div class="col-lg-12">
                            <div class="bg-light rounded p-4">
                                <h6 class="mb-4">Biểu đồ nhập – xuất kho theo tháng</h6>
                                <canvas id="inputOutputChart"></canvas>
                            </div>
                        </div>
                    </div>
                </div>

                
                <%@ include file="../template/footer.jsp" %>
            </div>
        </div>

        
        <c:if test="${not empty statusMap}">
            <script>
                const statusLabels = [], statusData = [];
                <c:forEach var="entry" items="${statusMap}">
            statusLabels.push("${entry.key}");
            statusData.push(${entry.value});
                </c:forEach>

                new Chart(document.getElementById('statusChart').getContext('2d'), {
                    type: 'bar',
                    data: {
                        labels: statusLabels,
                        datasets: [{
                                label: 'Số lượng',
                                data: statusData,
                                backgroundColor: '#0d6efd',
                                borderRadius: 4
                            }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            title: {
                                display: true,
                                text: 'Số lượng vật tư theo tình trạng'
                            }
                        }
                    }
                });
            </script>
        </c:if>

        <c:if test="${not empty unitMap}">
            <script>
                const unitLabels = [], unitData = [];
                <c:forEach var="entry" items="${unitMap}">
            unitLabels.push("${entry.key}");
            unitData.push(${entry.value});
                </c:forEach>

                new Chart(document.getElementById('unitChart').getContext('2d'), {
                    type: 'pie',
                    data: {
                        labels: unitLabels,
                        datasets: [{
                                data: unitData,
                                backgroundColor: ['#198754', '#ffc107', '#dc3545', '#0dcaf0']
                            }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            title: {
                                display: true,
                                text: 'Tỷ lệ vật tư theo đơn vị tính'
                            }
                        }
                    }
                });
            </script>
        </c:if>

        <c:if test="${not empty inputMap or not empty outputMap}">
            <script>
                const months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
                const inputData = [], outputData = [];
                <c:forEach begin="1" end="12" var="i">
            inputData.push(${inputMap[i] != null ? inputMap[i] : 0});
            outputData.push(${outputMap[i] != null ? outputMap[i] : 0});
                </c:forEach>

                new Chart(document.getElementById('inputOutputChart').getContext('2d'), {
                    type: 'line',
                    data: {
                        labels: months,
                        datasets: [
                            {
                                label: 'Nhập kho',
                                data: inputData,
                                borderColor: '#198754',
                                backgroundColor: 'rgba(25,135,84,0.2)',
                                fill: true,
                                tension: 0.3
                            },
                            {
                                label: 'Xuất kho',
                                data: outputData,
                                borderColor: '#dc3545',
                                backgroundColor: 'rgba(220,53,69,0.2)',
                                fill: true,
                                tension: 0.3
                            }
                        ]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            title: {
                                display: true,
                                text: 'Nhập – Xuất kho theo tháng'
                            }
                        }
                    }
                });
            </script>
        </c:if>

        
        <script src="js/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/waypoints/4.0.1/jquery.waypoints.min.js"></script>

        
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>
        <script src="lib/chart/chart.min.js"></script>
        <script src="js/main.js"></script>
    </body>
</html>
