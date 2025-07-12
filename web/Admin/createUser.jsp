<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Admin Create User</title>
    <link rel="stylesheet" href="Admin/CSS/createStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Admin/CSS/layout.css" />
</head>

<body>
    <div class="wrapper">
    <div class="container">
<%@ include file="sidebar.jsp" %>

        <!-- Main content -->
        <div class="main">
<%@ include file="header.jsp" %>

            <!-- Main content area -->
            <main class="content">
                <h1>CREATE USER</h1>
                <c:if test="${not empty error}">
                    <div style="color:red; font-weight:bold; margin-bottom: 10px;">
                        ${error}
                    </div>
                </c:if>
                
                <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/Create">
                   
                    <input type="hidden" name="id"  />
                    <table class="register">
                        <thead>
                            <tr>
                                <th colspan="4">ADD USER INFORMATION</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Username:</td>
                                <td><input type="text" name="username" required></td>
                                <td>Full Name:</td>
                                <td><input type="text" name="fullname" required></td>
                            </tr>
                            <tr>
                                <td>Password:</td>
                                <td><input type="password" name="password" placeholder="Leave blank if unchanged"></td>
                                <td>Gender:</td>
                                <td>
                                    <select name="gender" required>
                                        <option disabled>-- Select --</option>
                                        <option value="true" >Male</option>
                                        <option value="false" >Female</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>Email:</td>
                                <td><input type="email" name="email"  required></td>
                                <td>Address:</td>
                                <td><input type="text" name="address" required></td>
                            </tr>
                            <tr>
                                <td>Phone:</td>
                                <td><input type="text" name="phone"  required></td>
                                <td>Birth Date:</td>
                                <td><input type="date" name="birthDate"  required></td>
                            </tr>
                            <tr>
                                <td>Image:</td>
                                <td colspan="3"><input type="file" name="image" accept="image/*"></td>
                            </tr>
                            <tr>
                                <td>Status:</td>
                                <td>
                                    <select name="status" required>
                                        <option value="true">Active</option>
                                        <option value="false">Inactive</option>
                                    </select>
                                </td>
                                <td>Role ID:</td>
                                <td>
                                    <select name="roleId" required>
<c:forEach var="r" items="${listRole}">
                                            <option value="${r.id}">
    ${r.roleName}
                                            </option>
</c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4" class="submit-row">
                                    <button type="submit">Submit</button>
                                    <button class="button-cancel" type="button" onclick="history.back()">Cancel</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </form>

            </main>


        </div>
        
    </div>
                <%@ include file="footer.jsp" %>
                </div>
</body>

</html>
