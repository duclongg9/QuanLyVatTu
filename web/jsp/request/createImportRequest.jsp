<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Import Request</title>
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid position-relative bg-white d-flex p-0">
    <%@ include file="../template/sidebar.jsp" %>

    <div class="content">
        <%@ include file="../template/navbar.jsp" %>

        <div class="container-fluid pt-4 px-4">
            <div class="row g-4">
                <div class="col-12">
                    <div class="bg-light rounded h-100 p-4">
                        <h4 class="mb-4">Create Import Request Form:</h4>

                        <form action="CreateRequestImport" method="post">
                            <div class="row mb-3">
                                <div class="col-md-3">
                                    <div class="col-md-12 mb-1">
                                        <label class="form-label fw-bold">Created by:</label>
                                        <input type="text" class="form-control" value="${userName}" readonly>
                                    </div>
                                    <div class="col-md-12 mb-1">
                                        <label class="form-label fw-bold">Type:</label>
                                        <input type="text" class="form-control" value="IMPORT" readonly>
                                    </div>
                                </div>
                                <div class="col-md-9">
                                    <div class="col-md-12 mb-2">
                                        <label class="form-label fw-bold">Note:</label>
                                        <textarea rows="4" class="form-control" name="note"></textarea>
                                    </div>
                                </div>
                            </div>

                            <h6 class="mb-4">Materials:</h6>
                            <table class="table" id="materialTable">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Category</th>
                                    <th>SubCategory</th>
                                    <th>Material</th>
                                    <th>Supplier</th>
                                    <th>Quantity</th>
                                    <th>Delete</th>
                                </tr>
                                </thead>
                                <tbody id="slot-container">
                                    <!-- Row 1: Rendered by JSTL -->
                                    <tr>
                                        <th scope="row">1</th>
                                        <td>
                                            <select class="form-select" name="categoryId" onchange="onParentCategoryChange(this)">
                                                <option value="">--Select--</option>
                                                <c:forEach var="cat" items="${categoryList}">
                                                    <option value="${cat.id}">${cat.categoryName}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <select class="form-select subcategory" name="subCategoryId" onchange="onSubCategoryChange(this)">
                                                <option value="">--Select--</option>
                                            </select>
                                        </td>
                                        <td>
                                            <select class="form-select material" name="materialId" onchange="onMaterialChange(this)">
                                                <option value="">--Select--</option>
                                            </select>
                                        </td>
                                        <td>
                                            <select class="form-select supplier" name="supplierId">
                                                <option value="">--Select--</option>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="number" name="quantity" class="form-control" min="1"/>
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-sm btn-danger" onclick="removeSlot(this)">
                                                <i class="fa fa-trash"></i>
                                            </button>
                                        </td>
                                    </tr>

                                    <!-- Template row for cloning (hidden) -->
                                    <tr class="slot-template d-none">
                                        <th scope="row">#</th>
                                        <td>
                                            <select class="form-select" name="categoryId" onchange="onParentCategoryChange(this)">
                                                <option value="">--Select--</option>
                                                <c:forEach var="cat" items="${categoryList}">
                                                    <option value="${cat.id}">${cat.categoryName}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <select class="form-select subcategory" name="subCategoryId" onchange="onSubCategoryChange(this)">
                                                <option value="">--Select--</option>
                                            </select>
                                        </td>
                                        <td>
                                            <select class="form-select material" name="materialId" onchange="onMaterialChange(this)">
                                                <option value="">--Select--</option>
                                            </select>
                                        </td>
                                        <td>
                                            <select class="form-select supplier" name="supplierId">
                                                <option value="">--Select--</option>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="number" name="quantity" class="form-control" min="1"/>
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-sm btn-danger" onclick="removeSlot(this)">
                                                <i class="fa fa-trash"></i>
                                            </button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>

                            <button type="button" class="btn btn-sm btn-outline-primary" onclick="addSlot()">Add Slot</button>
                            <br><br>
                            <input type="submit" class="btn btn-success" value="Create Request"/>
                            <a href="${pageContext.request.contextPath}/requestList" class="btn btn-secondary rounded-pill">Back to list</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <%@ include file="../template/footer.jsp" %>
    </div>
</div>

<!-- Scripts -->
<%@ include file="../template/script.jsp" %>

<script>
    function removeSlot(button) {
        const row = button.closest("tr");
        row.remove();
        reindexSlots();
    }

    function reindexSlots() {
        const rows = document.querySelectorAll("#slot-container tr:not(.slot-template)");
        rows.forEach((row, index) => {
            row.querySelector("th").innerText = index + 1;
        });
    }

    function addSlot() {
        const container = document.getElementById("slot-container");
        const template = container.querySelector(".slot-template");
        const newRow = template.cloneNode(true);
        newRow.classList.remove("slot-template", "d-none");

        // Reset non-category selects
        newRow.querySelectorAll("select").forEach(select => {
            if (!select.name.includes("categoryId")) {
                select.innerHTML = '<option value="">--Select--</option>';
            }
        });

        newRow.querySelector("input[name='quantity']").value = "";

        container.appendChild(newRow);
        reindexSlots();
    }

    function onParentCategoryChange(selectElement) {
        const parentId = selectElement.value;
        const row = selectElement.closest("tr");
        const subSelect = row.querySelector(".subcategory");
        const materialSelect = row.querySelector(".material");
        const supplierSelect = row.querySelector(".supplier");

        subSelect.innerHTML = '<option value="">--Select--</option>';
        materialSelect.innerHTML = '<option value="">--Select--</option>';
        supplierSelect.innerHTML = '<option value="">--Select--</option>';

        if (!parentId) return;

        fetch('SubCategorysController?parentId=' + parentId)
            .then(res => res.ok ? res.json() : Promise.reject("Network error"))
            .then(data => {
                data.forEach(c => {
                    const option = document.createElement("option");
                    option.value = c.id;
                    option.textContent = c.categoryName;
                    subSelect.appendChild(option);
                });
            })
            .catch(error => console.error("❌ SubCategory fetch failed:", error));
    }

    function onSubCategoryChange(selectElement) {
        const subId = selectElement.value;
        const row = selectElement.closest("tr");
        const materialSelect = row.querySelector(".material");
        const supplierSelect = row.querySelector(".supplier");

        materialSelect.innerHTML = '<option value="">--Select--</option>';
        supplierSelect.innerHTML = '<option value="">--Select--</option>';

        if (!subId) return;

        fetch('MaterialsController?subId=' + subId)
            .then(res => res.ok ? res.json() : Promise.reject("Network error"))
            .then(data => {
                data.forEach(m => {
                    const option = document.createElement("option");
                    option.value = m.id;
                    option.textContent = m.name;
                    materialSelect.appendChild(option);
                });
            })
            .catch(error => console.error("Material fetch failed:", error));
    }

    function onMaterialChange(selectElement) {
        const materialId = selectElement.value;
        const row = selectElement.closest("tr");
        const supplierSelect = row.querySelector(".supplier");

        supplierSelect.innerHTML = '<option value="">--Select--</option>';

        if (!materialId) return;

        fetch('SuppliersController?materialId=' + materialId)
            .then(res => res.ok ? res.json() : Promise.reject("Network error"))
            .then(data => {
                data.forEach(s => {
                    const option = document.createElement("option");
                    option.value = s.id;
                    option.textContent = s.name;
                    supplierSelect.appendChild(option);
                });
            })
            .catch(error => console.error("Supplier fetch failed:", error));
    }
</script>
</body>
</html>
