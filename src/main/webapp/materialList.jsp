<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h3>Danh sách Vật tư</h3>
<a href="material?action=add">+ Thêm mới</a>
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
                <a href="material?action=edit&id=${m.id}">Sửa</a> |
                <a href="material?action=delete&id=${m.id}" onclick="return confirm('Bạn chắc chắn muốn xóa?')">Xóa</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>