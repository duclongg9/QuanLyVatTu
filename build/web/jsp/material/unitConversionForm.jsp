<%-- 
    Document   : unitConversionForm
    Created on : Jun 18, 2025, 4:22:43 PM
    Author     : KIET
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.UnitConversion, model.Unit, model.Materials" %>
<%
    UnitConversion uc = (UnitConversion) request.getAttribute("unitConversion");
    List<Unit> units = (List<Unit>) request.getAttribute("units");
    List<Materials> materials = (List<Materials>) request.getAttribute("materials");
    boolean isEdit = (uc != null);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><%= isEdit ? "Chỉnh sửa" : "Thêm mới" %> Quy đổi đơn vị</title>
    <link href="Admin2/css/bootstrap.min.css" rel="stylesheet">
    <link href="Admin2/css/style.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid position-relative d-flex p-0">
    <%@ include file="Admin2/sidebar.jsp" %>
    <div class="content">
        <%@ include file="Admin2/navbar.jsp" %>

        <div class="container-fluid pt-4 px-4">
            <div class="bg-light rounded p-4">
                <h6 class="mb-4"><%= isEdit ? "Chỉnh sửa" : "Thêm mới" %> Quy đổi đơn vị</h6>
                <form action="unitConversion" method="post">
                    <% if (isEdit) { %>
                        <input type="hidden" name="action" value="<%= (uc != null) ? "update" : "create" %>">
                        <input type="hidden" name="id" value="<%= (uc != null) ? uc.getId() : "" %>">
                    <% } else { %>
                        <input type="hidden" name="action" value="create">
                    <% } %>

                    <div class="mb-3">
                        <label for="materialId" class="form-label">Vật tư</label>
                        <select class="form-select" id="materialId" name="materialId" required>
                            <option value="">Chọn vật tư</option>
                            <% for (Materials m : materials) { %>
                                <option value="<%= m.getId() %>" <%= (uc != null && uc.getMaterialId() == m.getId()) ? "selected" : "" %>>
                                    <%= m.getName() %>
                                </option>
                            <% } %>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="fromUnitId" class="form-label">Từ đơn vị</label>
                        <select class="form-select" id="fromUnitId" name="fromUnitId" required>
                            <option value="">Chọn đơn vị</option>
                            <% for (Unit u : units) { %>
                                <option value="<%= u.getId() %>" <%= (uc != null && uc.getFromUnitId() == u.getId()) ? "selected" : "" %>>
                                    <%= u.getName() %>
                                </option>
                            <% } %>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="toUnitId" class="form-label">Sang đơn vị</label>
                        <select class="form-select" id="toUnitId" name="toUnitId" required>
                            <option value="">Chọn đơn vị</option>
                            <% for (Unit u : units) { %>
                                <option value="<%= u.getId() %>" <%= (uc != null && uc.getToUnitId() == u.getId()) ? "selected" : "" %>>
                                    <%= u.getName() %>
                                </option>
                            <% } %>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="ratio" class="form-label">Tỉ lệ (VD: 10)</label>
                        <input type="number" step="0.01" class="form-control" id="ratio" name="ratio"
                               value="<%= (uc != null) ? uc.getRatio() : "" %>" required>
                    </div>

                    <button type="submit" class="btn btn-primary"><%= isEdit ? "Cập nhật" : "Thêm mới" %></button>
                    <a href="unitConversion" class="btn btn-secondary">Hủy</a>
                </form>
            </div>
        </div>

        <%@ include file="Admin2/footer.jsp" %>
    </div>
</div>


<!-- jQuery -->
<script src="js/jquery-3.4.1.min.js"></script>

<!-- Waypoints -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/waypoints/4.0.1/jquery.waypoints.min.js"></script>

<!-- Owl Carousel -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>

<!-- Bootstrap Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="Admin2/js/main.js"></script>
<script>
    // Toggle sidebar mở/ẩn
    document.querySelector('.sidebar-toggler')?.addEventListener('click', function (e) {
        e.preventDefault();
        document.querySelector('.sidebar')?.classList.toggle('open');
        document.querySelector('.content')?.classList.toggle('open');
    });
</script>
</body>

</html>

