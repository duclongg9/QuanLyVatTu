<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Admin Update User</title>
    <link rel="stylesheet" href="Admin/CSS/layout.css" />
    <link rel="stylesheet" href="Admin/CSS/userListStyle.css">
    <link rel="stylesheet" href="Admin/CSS/updateUserStyle.css">
    
</head>

<body>
    <div class="wrapper">
    <div class="container">
        <%@ include file="sidebar.jsp" %>

       <main class="content">
    <h1>Update User</h1>

   <div class="user-form">
    <form method="post"  action="${pageContext.request.contextPath}/Update">
        <input type="hidden" name="id" value="${user.id}" />

        <div class="form-grid">
            <div class="avatar-container">
                 <img class="avata" src="${pageContext.request.contextPath}/images/${user.image}" alt="User Avatar" class="avatar">
            </div>
            <!-- Cột trái: Thông tin tĩnh -->
             
            <div class="form-group">
                <label>Tên nhân viên:</label>
                <span class="readonly-text">${user.fullName}</span>
            </div>

            <div class="form-group">
                <label>Phone:</label>
                <span class="readonly-text">${user.phone}</span>
            </div>

            <div class="form-group">
                <label>Address:</label>
                <span class="readonly-text">${user.address}</span>
            </div>

            <div class="form-group">
                <label>Current Role:</label>
                <span class="readonly-text">${user.role.roleName}</span>
            </div>

            <!-- Cột phải: 2 dropdown -->
            <div class="form-group">
                <label>Select Role:</label>
                <select name="roleId" required>
                    <c:forEach var="r" items="${listRole}">
                        <option value="${r.id}" ${user.role.id eq r.id ? "selected" : ""}>
                            ${r.roleName}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label>Select Status:</label>
                <select name="status" required>
                    <option value="true" ${user.status eq true ? "selected" : ""}>Active</option>
                    <option value="false" ${user.status eq false ? "selected" : ""}>Inactive</option>
                </select>
            </div>
        </div>

        <div class="submit-row">
            <button type="submit">Submit</button>
            <button class="button-cancel" type="button" onclick="history.back()">Cancel</button>
        </div>
    </form>
</div>
       </main>




            
        </div>
                <%@ include file="footer.jsp" %>
    </div>
</body>

</html>
