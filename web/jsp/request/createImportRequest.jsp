<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Import Request</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
</head>
<body class="p-4">
    <div class="container">
        <h3>Create New Import Request</h3>

        <!-- Form thêm vật tư -->
   <form method="post" action="CreateRequestImport">
        <input type="hidden" name="action" value="addSlot" />
        <input type="hidden" name="slotCount" value="${fn:length(slotList)}" />

        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Category</th>
                <th>SubCategory</th>
                <th>Material</th>
                <th>Supplier</th>
                <th>Quantity</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="slot" items="${slotList}" varStatus="i">
                <tr>
                    <td>
                        <select name="categoryId${i.index}" onchange="this.form.submit()" class="form-select">
                            <option value="">-- Select --</option>
                            <c:forEach var="c" items="${categoryList}">
                                <option value="${c.id}" <c:if test="${c.id == slot.categoryId}">selected</c:if>>${c.category}</option>
                            </c:forEach>
                        </select>
                    </td>

                    <td>
                        <select name="subCategoryId${i.index}" onchange="this.form.submit()" class="form-select">
                            <option value="">-- Select --</option>
                            <c:forEach var="sc" items="${slot.subCategoryList}">
                                <option value="${sc.id}" <c:if test="${sc.id == slot.subCategoryId}">selected</c:if>>${sc.subCategoryName}</option>
                            </c:forEach>
                        </select>
                    </td>

                    <td>
                        <select name="materialId${i.index}" onchange="this.form.submit()" class="form-select">
                            <option value="">-- Select --</option>
                            <c:forEach var="m" items="${slot.materialList}">
                                <option value="${m.id}" <c:if test="${m.id == slot.materialId}">selected</c:if>>${m.name}</option>
                            </c:forEach>
                        </select>
                    </td>

                    <td>
                        <select name="supplierId${i.index}" class="form-select">
                            <option value="">-- Select --</option>
                            <c:forEach var="s" items="${slot.supplierList}">
                                <option value="${s.supplierId.id}" <c:if test="${s.supplierId.id == slot.supplierId}">selected</c:if>>${s.supplierId.name}</option>
                            </c:forEach>
                        </select>
                    </td>

                    <td>
                        <input type="number" name="quantity${i.index}" class="form-control" value="${slot.quantity != null ? slot.quantity : 1}" min="1" />
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <button type="submit" class="btn btn-success">+ Add Slot</button>
    </form>
        
        <form method="post" action="CreateRequestImport">
        <input type="hidden" name="action" value="cancel" />
        <button class="btn btn-danger mt-2">Cancel</button>
    </form>

    </div>

  
  
</body>
</html>
