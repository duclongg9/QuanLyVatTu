<%-- 
    Document   : unitConversionList
    Created on : Jun 18, 2025, 4:00:21 PM
    Author     : KIET
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="model.UnitConversion" %>
<%@ page import="model.Materials" %>
<%@ page import="model.Unit" %>
<%@ page import="dao.UnitDAO" %>
<%@ page import="dao.MaterialsDAO" %>
<%
    List<UnitConversion> list = (List<UnitConversion>) request.getAttribute("unitConversionList");
    UnitDAO unitDAO = new UnitDAO();
    MaterialsDAO materialDAO = new MaterialsDAO();
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Danh sách quy đổi đơn vị</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    </head>
    <body>
        <%@ include file="../template/spinner.jsp" %>
        <div id="spinner" class="show bg-white position-fixed w-100 vh-100 d-flex align-items-center justify-content-center">
            <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status"></div>
        </div>


        <div class="container-fluid position-relative d-flex p-0">
            <%@ include file="../template/sidebar.jsp" %>


            <div class="content">
                <%@ include file="../template/navbar.jsp" %>

                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded p-4">
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <h5 class="mb-0">Danh sách quy đổi đơn vị</h5>
                            <a href="unitConversion?action=create" class="btn btn-primary">+ Thêm mới</a>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-bordered text-center align-middle">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Vật tư</th>
                                        <th>Từ đơn vị</th>
                                        <th>Sang đơn vị</th>
                                        <th>Tỉ lệ</th>
                                        <th>Hành động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        int index = 1;
                                        if (list != null && !list.isEmpty()) {
                                            for (UnitConversion uc : list) {
                                                Materials mat = materialDAO.getMaterialById(uc.getMaterialId());
                                                Unit fromUnit = unitDAO.getUnitById(uc.getFromUnitId());
                                                Unit toUnit = unitDAO.getUnitById(uc.getToUnitId());
                                    %>
                                    <tr>
                                        <td><%= index++ %></td>
                                        <td><%= mat != null ? mat.getName() : "?" %></td>
                                        <td><%= fromUnit != null ? fromUnit.getName() : "?" %></td>
                                        <td><%= toUnit != null ? toUnit.getName() : "?" %></td>
                                        <td>1 : <%= uc.getRatio() %></td>
                                        <td>
                                            <a href="unitConversion?action=edit&id=<%= uc.getId() %>" class="btn btn-sm btn-warning">Sửa</a>
                                            <a href="unitConversion?action=confirmDelete&id=<%= uc.getId() %>" class="btn btn-sm btn-danger" onclick="return confirm('Bạn chắc chắn muốn xóa?')">Xóa</a>
                                        </td>
                                    </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="6">Không có dữ liệu quy đổi</td>
                                    </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <nav>
                    <ul class="pagination justify-content-center">
                        <c:forEach begin="1" end="${totalPage}" var="i">
                            <li class="page-item ${page == i ? 'active' : ''}">
                                <a class="page-link" href="unitConversion?page=${i}">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
                <%@ include file="../template/footer.jsp" %>
            </div>

        </div>




        <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>


        <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>


        <script src="${pageContext.request.contextPath}/js/main.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/waypoints/4.0.1/jquery.waypoints.min.js"></script>


        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>



        <script>

                                                document.querySelector('.sidebar-toggler')?.addEventListener('click', function (e) {
                                                    e.preventDefault();
                                                    document.querySelector('.sidebar')?.classList.toggle('open');
                                                    document.querySelector('.content')?.classList.toggle('open');
                                                });
        </script>
    </body>
</html>
