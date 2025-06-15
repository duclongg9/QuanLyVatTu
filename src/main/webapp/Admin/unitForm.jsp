<%@ page contentType="text/html;charset=UTF-8" %>
<%
    model.Unit u = (model.Unit) request.getAttribute("unit");
%>
<h3><%= (u == null ? "Thêm mới" : "Cập nhật") %> đơn vị tính</h3>
<form method="post" action="unit">
    <input type="hidden" name="id" value="<%= (u != null ? u.getId() : "") %>">
    Tên đơn vị:
    <input type="text" name="name" value="<%= (u != null ? u.getName() : "") %>" required>
    <button type="submit">Lưu</button>
</form>
<a href="unit">← Quay lại danh sách</a>
