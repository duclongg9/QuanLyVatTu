<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Danh sách Vật Tư</title>
    <style>
        table {
            border-collapse: collapse;
            width: 80%;
            margin: 20px auto;
        }
        th, td {
            border: 1px solid #333;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        h1 {
            text-align: center;
            margin-top: 40px;
        }
    </style>
</head>
<body>
    <h1>Danh sách Vật Tư</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>Tên</th>
            <th>Đơn vị</th>
            <th>Trạng thái</th>
            <th>Danh mục</th>
        </tr>
        <c:forEach var="m" items="${materials}">
            <tr>
                <td>${m.id}</td>
                <td>${m.name}</td>
                <td>${m.unit}</td>
                <td>${m.status}</td>
                <td>${m.category}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>