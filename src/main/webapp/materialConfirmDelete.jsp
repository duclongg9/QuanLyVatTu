<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Materials" %>
<%
    Materials material = (Materials) request.getAttribute("material");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác nhận xóa vật tư</title>
</head>
<body>
<h2>Xác nhận xóa</h2>

<p>Bạn có chắc chắn muốn xóa vật tư sau không?</p>
<p><strong>ID:</strong> <%= material.getId() %></p>
<p><strong>Tên vật tư:</strong> <%= material.getName() %></p>

<form action="material" method="get">
    <input type="hidden" name="action" value="delete">
    <input type="hidden" name="id" value="<%= material.getId() %>">
    <button type="submit">Xác nhận xóa</button>
    <a href="material"><button type="button">Hủy</button></a>
</form>
</body>
</html>