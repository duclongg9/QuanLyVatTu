<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Admin Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/CSS/Admin_Home_style.css" />

</head>

<body>
<%@include file="../template/header.jsp" %>

    <main>
<%@include file="../template/leftSideBar.jsp" %>
        <section class="main-content">
            <h2>Phần màn hình chức năng</h2>
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
            <a href="${pageContext.request.contextPath}/addUser.jsp">
                <button style="padding: 6px 12px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer;">
                + Thêm User
                </button>
            </a>
</div>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Role</th>
                        <th>Status</th>
                        <th>Image</th>
                        <th>Edit</th>
                    </tr>
                </thead>
                <tbody>
                    
<c:forEach var="lu" items="${listUser}">
                        <tr>
                        <td>${lu.getName()}</td>
                        <td>
                            <c:if test="${lu.rollId == 1}">
                             Admin
                            </c:if>
                            <c:if test="${lu.rollId != 1}">
                                Nhân viên
                            </c:if>
                        </td>
                        <td>
                             <c:if test="${lu.status==true}">
                             <span style="color:green;">Active</span>
                            </c:if>
                            <c:if test="${lu.status==false}">
                                <span style="color:red;">Inactive</span>
                            </c:if>
                        </td>
                        <td><img class="avata" src="${pageContext.request.contextPath}/images/${lu.image}" alt="anh"></td>
                        <td><a href="#" class="delete-btn" >Update</a> <a a href="userList?id=${lu.id}&action=delete" onclick="return confirm('Bạn có chắc muốn xóa không?')"
                                class="delete-btn">Delete</a> </td>
                    </tr>
</c:forEach>
                    
                   
                    
                   
                </tbody>
            </table>
                 <c:forEach begin="1" end="${endP}" var="i">
                     <a class="${tag == i?"active":""}" href="userList?index=${i}">${i} <a>
                    </c:forEach>
        </section>
    </main>


<%@include file="../template/footer.jsp" %>


</body>

</html>