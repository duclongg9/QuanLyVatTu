<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h3>Danh sách Vật tư đã xóa</h3>
<a href="material">← Quay lại danh sách</a>
<table border="1" cellpadding="5">
    <thead>
    <tr>
        <th>ID</th>
        <th>Tên vật tư</th>
        <th>Đơn vị</th>
        <th>Danh mục</th>
        <th>Hình ảnh</th>
        <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="m" items="${list}">
        <tr>
            <td>${m.id}</td>
            <td>${m.name}</td>
            <td>${m.unitId.unitName}</td>
            <td>${m.categoryId.category}</td>
            <td>${m.image}</td>
            <td>
                <a href="material?action=activate&id=${m.id}" onclick="return confirm('Khôi phục vật tư này?')">Active</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>