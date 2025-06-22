<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Material List</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    </head>
    <body>
        <%@include file="../spinner.jsp" %>
        <div class="container-xxl position-relative bg-white d-flex p-0">
            <%@include file="../sidebar.jsp" %>
            <div class="content d-flex flex-column min-vh-100">
                <%@include file="../navbar.jsp" %>

                <div class="container-fluid pt-4 px-4 flex-grow-1">
                    <div class="row g-4">
                        <div class="col-12">
                            <div class="bg-light rounded p-4">
                                <h4 class="mt-5 mb-3">Quarterly cost of purchasing and repairing materials (Year ${year})</h4>

                                <form method="get" action="statistics" class="mb-3 d-flex align-items-center gap-2">
                                    <input type="number" name="year" value="${year}" class="form-control w-auto" />
                                    <button type="submit" class="btn btn-primary">Xem</button>
                                </form>

                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>Quarter</th>
                                            <th>Type</th>
                                            <th>Total price (VNĐ)</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="entry" items="${costData}">
                                            <tr>
                                                <td>${fn:split(entry.key, " - ")[0]}</td>
                                                <td>${fn:split(entry.key, " - ")[1]}</td>
                                                <td><fmt:formatNumber value="${entry.value}" type="currency" currencySymbol="₫"/></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>

                                <canvas id="costChart" height="100"></canvas>
                                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                                <script>
                                    const ctx = document.getElementById('costChart').getContext('2d');
                                    const costChart = new Chart(ctx, {
                                    type: 'bar',
                                            data: {
                                            labels: [
                                    <c:forEach var="entry" items="${costData}" varStatus="loop">
                                            "${entry.key}"<c:if test="${!loop.last}">,</c:if>
                                    </c:forEach>
                                            ],
                                                    datasets: [{
                                                    label: 'Total price (VNĐ)',
                                                            data: [
                                    <c:forEach var="entry" items="${costData}" varStatus="loop">
                                        ${entry.value}<c:if test="${!loop.last}">,</c:if>
                                    </c:forEach>
                                                            ],
                                                            backgroundColor: 'rgba(255, 99, 132, 0.7)',
                                                            borderColor: 'rgba(255, 99, 132, 1)',
                                                            borderWidth: 1,
                                                            borderRadius: 6
                                                    }]
                                            },
                                            options: {
                                            responsive: true,
                                                    plugins: {
                                                    legend: { display: false },
                                                            title: {
                                                            display: true,
                                                                    text: 'Quarterly costs (Import & Repair)',
                                                                    font: { size: 18 }
                                                            }
                                                    },
                                                    scales: {
                                                    y: {
                                                    beginAtZero: true,
                                                            ticks: {
                                                            callback: value => value.toLocaleString("vi-VN") + " ₫"
                                                            }
                                                    }
                                                    }
                                            }
                                    });</script>
                                <!--
                                                                <nav>
                                                                    <ul class="pagination justify-content-center">
                                <c:forEach begin="1" end="${endP}" var="i">
                                    <li class="page-item ${tag == i ? 'active' : ''}">
                                        <a class="page-link" href="materialController?action=list&index=${i}&search=${param.search}">${i}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </nav>-->
                                <h5 class="mt-5">Material transaction details</h5>
                                <table class="table table-striped table-hover">
                                    <thead class="table-dark">
                                        <tr>
                                            <th>Quarter</th>
                                            <th>Date</th>
                                            <th>Type</th>
                                            <th>Name Material</th>
                                            <th>Unit</th>
                                            <th>Category</th>
                                            <th>Amount</th>
                                            <th>Input price (₫)</th>
                                            <th>Total price (₫)</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="d" items="${details}">
                                            <tr>
                                                <td>Q${d.quarter}</td>
                                                <td><fmt:formatDate value="${d.date}" pattern="dd-MM-yyyy" /></td>
                                        <td>
                                            <span class="badge bg-${d.type == 'Import' ? 'primary' : 'warning'}">
                                                ${d.type}
                                            </span>
                                        </td>
                                        <td>${d.name}</td>
                                        <td>${d.unit}</td>
                                        <td>${d.category}</td>
                                        <td>${d.quantity}</td>
                                        <td><fmt:formatNumber value="${d.price}" type="currency" currencySymbol="₫" /></td>
                                        <td><fmt:formatNumber value="${d.totalCost}" type="currency" currencySymbol="₫" /></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>

                            </div>
                        </div>
                    </div>
                    <%@include file="../footer.jsp" %>
                </div>
            </div>

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

            <!-- JS -->
            <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/main.js"></script>
    </body>
</html>