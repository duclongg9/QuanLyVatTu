<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%@ page contentType="text/html; charset=UTF-8" %>
    <title>Audit Log - All Modules</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="container-fluid p-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
    <h4 class="mb-0">Audit Log - All Modules</h4>
    <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary">
         Back to home page
    </a>
</div>

    <form method="get" action="AuditLog" class="row g-3 mb-4">
        <input type="hidden" name="page" value="1">
        <div class="col-md-3">
            <label>Module (Table):</label>
            <select class="form-select" name="tableName">
                <option value="">All</option>
                <c:forEach var="table" items="${listTableName}">
                    <option value="${table}" ${param.tableName == table ? 'selected' : ''}>${table}</option>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-3">
            <label>Action Type:</label>
            <select class="form-select" name="actionType">
                <option value="">All</option>
                <option value="INSERT" ${param.actionType == 'INSERT' ? 'selected' : ''}>INSERT</option>
                <option value="UPDATE" ${param.actionType == 'UPDATE' ? 'selected' : ''}>UPDATE</option>
                <option value="DELETE" ${param.actionType == 'DELETE' ? 'selected' : ''}>DELETE</option>
            </select>
        </div>

        <div class="col-md-3">
            <label>Changed By:</label>
            <select class="form-select" name="changedBy">
                <option value="">All</option>
                <c:forEach var="u" items="${userList}">
                    <option value="${u.id}" ${param.changedBy == u.id ? 'selected' : ''}>${u.fullName}</option>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-3">
            <label>From Date:</label>
            <input type="date" class="form-control" name="fromDate" value="${param.fromDate}">
        </div>

        <div class="col-md-3">
            <label>To Date:</label>
            <input type="date" class="form-control" name="toDate" value="${param.toDate}">
        </div>

        <div class="col-md-12">
            <button type="submit" class="btn btn-primary">Filter</button>
        </div>
    </form>

    <div class="table-responsive">
        <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Table</th>
                <th>Record ID</th>
                <th>Action</th>
                <th>Message</th> 
                <th>Changed By</th>
                <th>Changed At</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="log" items="${listAuditLog}">
                <tr>
                    <td>${log.id}</td>
                    <td>${log.tableName}</td>
                    <td>${log.recordId}</td>
                    <td>${log.actionType}</td>
                    <td>${log.message}</td> 
                    <td>${log.changeBy.fullName}</td>
                    <td>${log.changeAt}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${totalPages > 0}">
            <nav>
                <ul class="pagination justify-content-center">
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link"
                               href="?page=${i}&tableName=${param.tableName}&actionType=${param.actionType}&changedBy=${param.changedBy}&fromDate=${param.fromDate}&toDate=${param.toDate}">
                                ${i}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </c:if>
    </div>
</div>
</body>
</html>
