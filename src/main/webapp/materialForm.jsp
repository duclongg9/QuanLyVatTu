<%@ page contentType="text/html;charset=UTF-8" %>
<%
    model.Materials m = (model.Materials) request.getAttribute("material");
%>
<h3><%= (m == null ? "Thêm mới" : "Cập nhật") %> vật tư</h3>
<form method="post" action="material">
    <input type="hidden" name="id" value="<%= (m != null ? m.getId() : "") %>">
    Tên vật tư:
    <input type="text" name="name" value="<%= (m != null ? m.getName() : "") %>" required><br>
    Đơn vị ID:
    <input type="number" name="unitId" value="<%= (m != null ? m.getUnitId().getId() : "") %>" required><br>
    Danh mục ID:
    <input type="number" name="categoryId" value="<%= (m != null ? m.getCategoryId().getId() : "") %>" required><br>
    Hình ảnh:
    <input type="text" name="image" value="<%= (m != null ? m.getImage() : "") %>"><br>
    <button type="submit">Lưu</button>
</form>
<a href="material">← Quay lại danh sách</a>