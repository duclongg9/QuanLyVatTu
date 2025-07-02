<%-- 
    Document   : requestApproval
    Created on : 2 Jul 2025, 12:43:29
    Author     : Dell-PC
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%@page contentType="text/html; charset=UTF-8" %>
    <title>Request Approval</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <link href="img/favicon.ico" rel="icon">
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid position-relative bg-white d-flex p-0">
    <%@include file="../template/spinner.jsp" %>
    <%@include file="../template/sidebar.jsp" %>
    <div class="content">
        <%@include file="../template/navbar.jsp" %>
        <div class="container-fluid pt-4 px-4">
            <div class="row g-4">
                <div class="col-12">
                    <div class="bg-light rounded h-100 p-4">
                        <h6 class="mb-4">Pending Export Requests</h6>
                        <div class="table-responsive">
                            <table class="table" id="approvalTable">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Material</th>
                                    <th>Quantity</th>
                                    <th>Status</th>
                                    <th>Check</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="r" items="${requests}">
                                    <c:if test="${r.type == 'EXPORT' && r.statusId.id == 1}">
                                        <c:forEach var="d" items="${detailMap[r.id]}">
                                            <tr>
                                                <td>${r.id}</td>
                                                <td>${d.materialItem.materialSupplier.materialId.name}</td>
                                                <td>${d.quantity}</td>
                                                <td>${r.statusId.status}</td>
                                                <td><a href="requestDetailController?requestId=${r.id}" class="btn btn-primary btn-sm">Check</a></td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../template/footer.jsp" %>
    </div>
</div>
<%@include file="../template/script.jsp" %>
</body>
</html>
