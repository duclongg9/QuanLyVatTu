<%@ page contentType="text/html; charset=UTF-8" %> 


<!DOCTYPE html>
 <aside class="sidebar">
            <a href="${pageContext.request.contextPath}/userList">User Management</a>
            <a href="${pageContext.request.contextPath}/materialController?action=list">Material List</a>
            <a href="${pageContext.request.contextPath}/materialController?action=add">Add Material</a>
            <a href="${pageContext.request.contextPath}/materialController?action=deleted">Deleted Materials</a>
            <a href="${pageContext.request.contextPath}/jsp/request/requestList.jsp">Request List</a>
            <a href="${pageContext.request.contextPath}/jsp/request/createImportRequest.jsp">Create Import Request</a>
            <a href="${pageContext.request.contextPath}/jsp/material/listMaterials.jsp">Stock in</a>
            <a href="${pageContext.request.contextPath}/jsp/material/DeletedMaterials.jsp">Deleted Materials</a>
        </aside>