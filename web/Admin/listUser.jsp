<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@page contentType="text/html; charset=UTF-8" %>
            <title>Admin Dashboard</title>
            <link rel="stylesheet" href="Admin/CSS/userListStyle.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/Admin/CSS/layout.css" />
    </head>

    <body>
        <div class="wrapper">
        <div class="container">
            <%@include file="sidebar.jsp" %>

                <!-- Main content -->
                <div class="main">
                    <%@include file="header.jsp" %>

                        <!-- Main content area -->
                        <main class="content">
                            <h1>List User</h1>

                            <div class="add-button">
                                <a href="Create">
                                    <button>
                                         Thêm User
                                    </button>
                                </a>
                            </div>
                                    <table class="list-user-table">
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Role</th>
                                        <th>Status</th>
                                        <th>Image</th>
                                        <th>Edit</th>
                                    </tr>
                                </thead>
                                <tbody>

                                    <c:forEach var="lu" items="${listUser}">
                                        <tr>
                                            <td>${lu.fullName}</td>
                                            <td>${lu.email}</td>
                                            <td>
                                               ${lu.role.roleName}
                                            </td>
                                            <td>
                                                <c:if test="${lu.status==true}">
                                                    <span style="color:green;">Active</span>
                                                </c:if>
                                                <c:if test="${lu.status==false}">
                                                    <span style="color:red;">Inactive</span>
                                                </c:if>
                                            </td>
                                            <td><img class="avata"
                                                    src="${pageContext.request.contextPath}/images/${lu.image}" alt="anh">
                                            </td>
                                            <td><a href="Update?uid=${lu.id}" class="delete-btn">Update</a> <a a
                                                    href="userList?id=${lu.id}&action=delete"
                                                    onclick="return confirm('Bạn có chắc muốn đổi trạng thái tài khoản không')"
                                                    class="delete-btn">Change Status</a> </td>
                                        </tr>
                                    </c:forEach>




                                </tbody>
                            </table>
                            <div class="pagination">
                                <c:forEach begin="1" end="${endP}" var="i">
                                    <a href="userList?index=${i}" class="${tag == i ? 'active' : ''}">${i}</a>
                                </c:forEach>
                            </div>
                        </main>

                        
                </div>
                
        </div>
                    <%@ include file="footer.jsp" %>
                    </div>
    </body>

    </html>