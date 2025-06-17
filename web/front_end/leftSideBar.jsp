<%@ page contentType="text/html; charset=UTF-8" %> 


<!DOCTYPE html>
 <aside class="sidebar">
            <a href="${pageContext.request.contextPath}/userList">User Management</a>
            <a href="${pageContext.request.contextPath}/materialController?action=list">Material List</a>
            <a href="${pageContext.request.contextPath}/materialController?action=add">Add Material</a>
            <a href="${pageContext.request.contextPath}/front_end/phanquyen.jsp">Access Control</a>
            <a href="${pageContext.request.contextPath}/front_end/xuatkho.jsp">Stock out</a>
            <a href="${pageContext.request.contextPath}/front_end/nhapkho.jsp">Stock in</a>
            <a href="${pageContext.request.contextPath}/front_end/thongke.jsp">Thống kê</a>
            <a href="${pageContext.request.contextPath}/front_end/suachua.jsp">Yêu cầu sửa chữa</a>
        </aside>