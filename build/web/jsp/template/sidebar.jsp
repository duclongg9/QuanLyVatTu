<%@ page contentType="text/html; charset=UTF-8" %> 
<!-- Set current page path -->
<c:set var="currentPage" value="${pageContext.request.servletPath}" />

<!-- Sidebar Start -->
        <div class="sidebar pe-4 pb-3">
            <nav class="navbar bg-light navbar-light">
                <a href="index.jsp" class="navbar-brand mx-4 mb-3">
                    <h3 class="text-primary"><i class="fa fa-hashtag me-2"></i>Material</h3>
                </a>
                <div class="d-flex align-items-center ms-4 mb-4">
                    <div class="position-relative">
                        <img class="rounded-circle" src="${pageContext.request.contextPath}/assets/img/user.jpg" alt="" style="width: 40px; height: 40px;">
                        <div class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"></div>
                    </div>
                    <div class="ms-3">
                        <h6 class="mb-0">Jhon Doe</h6>
                        <span>Admin</span>
                    </div>
                </div>
                <div class="navbar-nav w-100">
                    <!-- Quản lý người dùng -->
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Quản lý người dùng</a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <a href="${pageContext.request.contextPath}/userList" class="dropdown-item">Danh sách người dùng</a>
                            <a href="${pageContext.request.contextPath}/createUser" class="dropdown-item">Thêm người dùng</a>
                            <a href="#" class="dropdown-item">Sửa/Xoá người dùng</a>
                            <a href="#" class="dropdown-item">Phân quyền người dùng</a>
                            <a href="#" class="dropdown-item">Thông tin cá nhân</a>
                        </div>
                    </div>
<!-- Quản lý vật tư -->
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Quản lý vật tư</a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <h6 class="dropdown-header">Danh mục vật tư</h6>
                            <a href="#" class="dropdown-item">Thêm danh mục</a>
                            <a href="#" class="dropdown-item">Xem danh mục</a>
                            <div class="dropdown-divider"></div>
                            <h6 class="dropdown-header">Danh sách vật tư</h6>
                            <a href="${pageContext.request.contextPath}/materialController?action=add" class="dropdown-item">Thêm mới vật tư</a>
                            <a href="${pageContext.request.contextPath}/materialController?action=list" class="dropdown-item"> Sửa vật tư</a>
                            <a href="${pageContext.request.contextPath}/materialController?action=list" class="dropdown-item">Xoá vật tư</a>
                            <div class="dropdown-divider"></div>
                            <h6 class="dropdown-header">Thống kê vật tư</h6>
                            <a href="#" class="dropdown-item">Theo loại</a>
                            <a href="#" class="dropdown-item">Theo tình trạng</a>
                            <a href="#" class="dropdown-item">Theo phòng ban sử dụng</a>
                        </div>
                    </div>

                    <!-- Kho vật tư -->
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Kho vật tư</a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <a href="#" class="dropdown-item">Nhập kho</a>
                            <a href="#" class="dropdown-item">Xuất kho</a>
                            <a href="#" class="dropdown-item">Thống kê Xuất – Nhập – Tồn</a>
                        </div>
                    </div>

                    <!-- Yêu cầu vật tư -->
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Yêu cầu vật tư</a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <a href="#" class="dropdown-item">Yêu cầu xuất kho</a>
                            <a href="#" class="dropdown-item">Đề nghị mua vật tư</a>
                            <a href="#" class="dropdown-item">Đề nghị sửa chữa vật tư</a>
                            <a href="#" class="dropdown-item">Phê duyệt yêu cầu</a>
                            <a href="#" class="dropdown-item">Danh sách yêu cầu</a>
                        </div>
                    </div>
                    <!-- Thống kê chi phí -->
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Thống kê chi phí</a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <a href="#" class="dropdown-item">Theo kỳ</a>
                            <a href="#" class="dropdown-item">Theo loại chi phí</a>
                        </div>
                    </div>

                    <!-- Hệ thống -->
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Hệ thống</a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <a href="#" class="dropdown-item">Quyền và vai trò</a>
                            <a href="#" class="dropdown-item">Gán quyền cho vai trò</a>
                            <a href="${pageContext.request.contextPath}/unitList" class="dropdown-item">️Đơn vị tính</a>
                            <a href="#" class="dropdown-item">Nhà cung cấp</a>
                        </div>
                </div>
                </div>
            </nav>
        </div>
        <!-- Sidebar End -->
