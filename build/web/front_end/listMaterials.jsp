<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                                <h4 class="mb-4">Material List</h4>
                                <a href="${pageContext.request.contextPath}/materialController?action=add" class="btn btn-primary mb-3">Add New</a>
                                <a href="${pageContext.request.contextPath}/materialController?action=deleted" class="btn btn-outline-secondary mb-3 ms-2">View Deleted</a>
                                <form action="materialController" method="get" class="d-flex align-items-center gap-2 mb-3">
                                    <input type="hidden" name="action" value="list"/>
                                    <input class="form-control border-0" type="search" placeholder="Search" name="search" value="${param.search}" />
                                    <button type="submit" class="btn btn-primary">Search</button>
                                </form>
                                <table class="table table-hover table-striped">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>Unit</th>
                                            <th>Category</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="m" items="${materials}">
                                            <tr>
                                                <td>${m.id}</td>
                                                <td>${m.name}</td>
                                                <td>${m.unitId.unitName}</td>
                                                <td>${m.categoryId.category}</td>
                                                <td>
                                                    <span class="badge bg-${m.status ? 'success' : 'secondary'}">
                                                        ${m.status ? 'Active' : 'Hidden'}
                                                    </span>
                                                </td>
                                                <td>
                                                    <a href="materialController?action=edit&id=${m.id}" class="btn btn-sm btn-warning">Edit</a>
                                                    <a href="materialController?action=delete&id=${m.id}" class="btn btn-sm btn-danger" onclick="return confirm('Delete this item?');">Delete</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <nav>
                                    <ul class="pagination justify-content-center">
                                        <c:forEach begin="1" end="${endP}" var="i">
                                            <li class="page-item ${tag == i ? 'active' : ''}">
                                                <a class="page-link" href="materialController?action=list&index=${i}&search=${param.search}">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </nav>
                                <%
                                    Map<String, Integer> materialStats = (Map<String, Integer>) request.getAttribute("materialStats");
                                %>

                                <hr class="my-4">
                                <h5 class="mb-3">Material Category Statistics</h5>
                                <div style="max-width: 800px; margin: auto;">
                                    <canvas id="materialChart"></canvas>
                                </div>

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
            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
            <script>
                            const ctx = document.getElementById('materialChart').getContext('2d');
                            const chart = new Chart(ctx, {
                                type: 'bar',
                                data: {
                                    labels: [<%= materialStats.keySet().stream()
                            .map(c -> "\"" + c + "\"")
                            .collect(java.util.stream.Collectors.joining(",")) %>],
                                    datasets: [{
                                            label: 'Number of Materials',
                                            data: [<%= materialStats.values().stream()
                                .map(String::valueOf)
                                .collect(java.util.stream.Collectors.joining(",")) %>],
                                            backgroundColor: 'rgba(54, 162, 235, 0.7)',
                                            borderColor: 'rgba(54, 162, 235, 1)',
                                            borderWidth: 1,
                                            borderRadius: 8
                                        }]
                                },
                                options: {
                                    responsive: true,
                                    plugins: {
                                        legend: {display: false},
                                        title: {
                                            display: true,
                                            text: 'Materials by Category',
                                            font: {size: 16}
                                        }
                                    },
                                    scales: {
                                        y: {
                                            beginAtZero: true,
                                            ticks: {stepSize: 1}
                                        }
                                    }
                                }
                            });
            </script>
    </body>
</html>