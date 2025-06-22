QuanLyVatTu/
├── src/
│   └── java/
│       ├── controller/            # Các servlet chia theo chức năng
│       │   ├── user/
│       │   │   ├── UserListController.java
│       │   │   ├── UserCreateController.java
│       │   │   └── UserUpdateController.java
│       │   ├── material/
│       │   │   ├── MaterialListController.java
│       │   │   └── MaterialCreateController.java
│       │   └── request/
│       │       ├── RequestListController.java
│       │       └── RequestDetailController.java
│       ├── dao/                   # DAO chia giống controller
│       │   ├── user/
│       │   ├── material/
│       │   └── request/
|	 |   |___ connect/          # chứa DBconnect
│       ├── model/                 # Các entity/POJO / giữ nguyên vị trí không chia model
│       └── util/                  # Tiện ích (EmailConfig, Validator…) / giữ nguyên vị trí không chia util
|
└── web/
    ├── jsp/                       # Trang JSP tổ chức theo module
    │   ├── user/
    │   │   ├── listUser.jsp
    │   │   ├── createUser.jsp
    │   │   └── updateUser.jsp
    │   ├── material/
    │   │   ├── listMaterial.jsp
    │   │   ├── createMaterial.jsp
    │   │   └── updateMaterial.jsp
    │   └── request/
    │   |   ├── listRequest.jsp
    │   |    └── requestDetail.jsp
    |   |____|____template/
    |   |     |___footer.jsp/login.jsp/header.jsp/navbar.jsp/sidebar.jsp/…..
    └── assets/                    # CSS/JS/ảnh dùng chung
        ├── css/
        ├── js/
        └── images/