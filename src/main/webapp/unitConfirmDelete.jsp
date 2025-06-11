<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Unit" %>
<%
    Unit unit = (Unit) request.getAttribute("unit");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác nhận xóa đơn vị tính</title>
</head>
<body>
    <h2>Xác nhận xóa</h2>

    <p>Bạn có chắc chắn muốn xóa đơn vị tính sau không?</p>
    <p><strong>ID:</strong> <%= unit.getId() %></p>
    <p><strong>Tên đơn vị:</strong> <%= unit.getName() %></p>

    <form action="unit" method="get">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="id" value="<%= unit.getId() %>">
        <button type="submit">Xác nhận xóa</button>
        <a href="unit"><button type="button">Hủy</button></a>
    </form>
</body>
</html>
