<!--              <%@ page contentType="text/html; charset=UTF-8" %> -->
<!-- Set current page path -->
<c:set var="currentPage" value="${pageContext.request.servletPath}" />

<!-- Sidebar Start -->
        <div class="sidebar pe-4 pb-3">
            <nav class="navbar bg-light navbar-light">
                <a href="index.jsp" class="navbar-brand mx-4 mb-3">
                    <h3 class="text-primary"><i class="fa fa-hashtag me-2"></i>MMS</h3>
                </a>
                <div class="d-flex align-items-center ms-4 mb-4">
                    <div class="position-relative">
                        <img class="rounded-circle" src="${pageContext.request.contextPath}/images/${sessionScope.account.image}" alt="" style="width: 40px; height: 40px;">
                        <div class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"></div>
                    </div>
                    <div class="ms-3">
                        <h6 class="mb-0">${sessionScope.account.fullName}</h6>
                        <span>${sessionScope.account.role.roleName}</span>
                    </div>
                </div>
                <div class="navbar-nav w-100">
                    <!-- Quản lý người dùng -->
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"> <i class="fa fa-box me-2"></i>Users</a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <a href="${pageContext.request.contextPath}/createUser" class="dropdown-item">Thêm người dùng</a>
                            <a href="${pageContext.request.contextPath}/userList" class="dropdown-item">Danh sách người dùng</a>
                            <a href="${pageContext.request.contextPath}/permission" class="dropdown-item">Phân quyền người dùng</a>
                        </div>
                    </div>
                    <!-- Quản lý vật tư -->
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-item nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-box me-2"></i>Materials </a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <h6 class="dropdown-header">Danh mục đơn vị</h6>
                            <a href="${pageContext.request.contextPath}/jsp/unit/unitList.jsp" class="dropdown-item">️Đơn vị tính</a>
                            <h6 class="dropdown-header">Danh mục vật tư</h6>
                            <a href="${pageContext.request.contextPath}/categoryController?action=add" class="dropdown-item">Thêm danh mục</a>
                            <a href="${pageContext.request.contextPath}/subCategoryController?action=add" class="dropdown-item">Thêm mục con</a>
                            <a href="${pageContext.request.contextPath}/categoryController" class="dropdown-item">Xem danh mục</a>
                            <a href="${pageContext.request.contextPath}/subCategoryController" class="dropdown-item">Xem mục con</a>
                            <div class="dropdown-divider"></div>
                            <h6 class="dropdown-header">Danh sách vật tư</h6>
                            <a href="${pageContext.request.contextPath}/materialController?action=add" class="dropdown-item">Thêm mới vật tư</a>
                            <a href="${pageContext.request.contextPath}/materialController?action=list" class="dropdown-item">Danh sách vật tư</a>
                            <div class="dropdown-divider"></div>
                            <h6 class="dropdown-header">Thống kê vật tư</h6>
                            <a href="#" class="dropdown-item">Theo loại</a>
                            <a href="#" class="dropdown-item">Theo tình trạng</a>
                        </div>
                    </div>

                    <!-- Kho vật tư -->
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-box me-2"></i>Storages</a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <h6 class="dropdown-header">Nhập kho</h6>
                            <a href="CreateRequestImport" class="dropdown-item">Tạo đơn nhập kho</a>
                            <a href="ListImport" class="dropdown-item">Lịch sử nhập kho</a>
                            <h6 class="dropdown-header">Xuất kho</h6>
                            <a href="createExport" class="dropdown-item">Đề nghị xuất kho</a>
                            <a href="#" class="dropdown-item">Đề nghị sửa chữa vật tư</a>
                            <a href="ListExport" class="dropdown-item">Lịch sử xuất kho</a>
                            <h6 class="dropdown-header">Thống kê</h6>
                            <a href="#" class="dropdown-item">Xuất – Nhập – Tồn</a>
                        </div>
                    </div>

                    <!-- Yêu cầu vật tư -->
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-box me-2"></i>List Request</a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <a href="CreateRequestImport" class="dropdown-item">Đề nghị mua vật tư</a>
                            <a href="#" class="dropdown-item">Đề nghị sửa chữa vật tư</a>
                            <a href="#" class="dropdown-item">Phê duyệt yêu cầu</a>
                            <a href="requestList" class="dropdown-item">Danh sách yêu cầu</a>
                        </div>
                    </div>
                    <!-- Thống kê chi phí -->
                  <div class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" id="statisticDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
        <i class="fa fa-chart-bar me-2"></i> Statistics
    </a>
    <div class="dropdown-menu bg-transparent border-0" aria-labelledby="statisticDropdown">
        <a class="dropdown-item" href="${pageContext.request.contextPath}/jsp/statistic/statisticsDashboard.jsp">Statistics Dashboard</a>
        <a class="dropdown-item" href="${pageContext.request.contextPath}/jsp/statistic/statisticImport.jsp">Import Statistics</a>
        <a class="dropdown-item" href="${pageContext.request.contextPath}/jsp/statistic/statisticExport.jsp">Export Statistics</a>
        <a class="dropdown-item" href="${pageContext.request.contextPath}/jsp/statistic/statisticRemain.jsp">Inventory Statistics</a>
        <a class="dropdown-item" href="${pageContext.request.contextPath}/jsp/statistic/statisticStatus.jsp">Status Statistics</a>
    </div>
</div>

                    <!-- Hệ thống -->
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-box me-2"></i>Systems</a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <a href="suppliercontroller" class="dropdown-item">Nhà cung cấp</a>
                        </div>
                </div>
                </div>
            </nav>
        </div>
        <!-- Sidebar End -->
