<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h3>Danh sách Đơn vị tính</h3>
<a href="unit?action=add">+ Thêm mới</a>
<table border="1" cellpadding="5">
    <thead>
        <tr>
            <th>ID</th>
            <th>Tên đơn vị</th>
            <th>Hành động</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="u" items="${list}">
        <tr>
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>
                <a href="unit?action=edit&id=${u.id}">Sửa</a> |
                <a href="unit?action=delete&id=${u.id}" onclick="return confirm('Bạn chắc chắn muốn xóa?')">Xóa</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
