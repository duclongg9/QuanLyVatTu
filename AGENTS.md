# AGENTS.md

## 🛠 Project Context

**System Name**: Vật Tư Management System  
**Tech Stack**: JSP/Servlet (No framework), MySQL, HTML/CSS/JS, Template Dashmin  
**Purpose**: Manage inventory materials, handle requests, and track usage across departments.

---

## 👥 User Roles (Agents)

| Role ID | Role Name (EN)        | Role Name (VN)            | Description |
|---------|------------------------|-----------------------------|-------------|
| R1      | Warehouse Manager      | Quản lý kho                 | Full access to all system functions. Handles system accounts, inventory, requests, and reports. |
| R2      | Warehouse Staff        | Nhân viên kho               | Performs inventory-related operations: import, export, update, view reports. |
| R3      | Company Director       | Giám đốc công ty            | Reviews & approves material requests. Views system-wide statistics. |
| R4      | Company Employee       | Nhân viên công ty           | Submits export/purchase/repair requests. Views personal request history. |

---

## 🔐 Role Permissions Matrix

| Functionality                               | Warehouse Manager (R1) | Warehouse Staff (R2) | Director (R3) | Employee (R4) |
|--------------------------------------------|-------------------------|-----------------------|---------------|---------------|
| Login/Logout                                | ✅                      | ✅                    | ✅            | ✅            |
| View Dashboard                              | ✅                      | ✅                    | ✅            | ✅            |
| Create/Edit/Delete Users                    | ✅                      | ❌                    | ❌            | ❌            |
| Assign Roles                                | ✅                      | ❌                    | ❌            | ❌            |
| View/Edit Profile                           | ✅                      | ✅                    | ✅            | ✅            |
| Add New Materials                           | ✅                      | ✅                    | ❌            | ❌            |
| Edit/Delete Materials                       | ✅                      | ✅                    | ❌            | ❌            |
| Manage Material Categories                  | ✅                      | ✅                    | ❌            | ❌            |
| Import Materials                            | ✅                      | ✅                    | ❌            | ❌            |
| Export Materials                            | ✅                      | ✅                    | ❌            | ❌            |
| Submit Export Request                       | ❌                      | ❌                    | ❌            | ✅            |
| Submit Purchase Request                     | ❌                      | ❌                    | ❌            | ✅            |
| Submit Repair Request                       | ✅                      | ✅                    | ❌            | ❌            |
| Approve/Reject Requests                     | ✅                      | ❌                    | ✅            | ❌            |
| View All Requests                           | ✅                      | ✅                    | ✅            | ❌            |
| View Personal Requests                      | ✅                      | ✅                    | ✅            | ✅            |
| Generate Export/Purchase/Repair Receipts    | ✅                      | ✅                    | ✅            | ❌            |
| View Inventory Statistics                   | ✅                      | ✅                    | ✅            | ❌            |
| View Cost Summary                           | ✅                      | ❌                    | ✅            | ❌            |

---

## 🖼 Sample Screens by Role (Dashmin Template Usage)

| Role              | Screen/Module                           | JSP View                      | Servlet Controller         |
|------------------|------------------------------------------|-------------------------------|----------------------------|
| Warehouse Manager | `/admin/users.jsp`, `/admin/stats.jsp`  | `listUsers.jsp`, `stats.jsp` | `UserController`, `StatsController` |
| Warehouse Staff   | `/inventory/materials.jsp`              | `listMaterials.jsp`          | `MaterialController`       |
| Director          | `/approvals/requests.jsp`               | `requestApproval.jsp`        | `ApprovalController`       |
| Employee          | `/requests/newExport.jsp`, `/requests/myRequests.jsp` | `submitExport.jsp`, `myRequests.jsp` | `RequestController`         |

---

## 📘 Notes for Developers (Codex/GPT)

- Role info should be stored in session after login: `session.setAttribute("role", "R1")`
- Use RBAC checks before rendering critical screens or performing actions (e.g., `if(role.equals("R1"))`)
- JSP should use JSTL `<c:if>` to hide unauthorized UI components
- Use Servlet filters to protect backend endpoints (e.g., `/admin/*`)
- Apply naming consistency: `listX.jsp`, `createX.jsp`, `updateX.jsp`, `ControllerX.java`

---

## 📦 Suggestion for Future Files

Consider creating:
- `ROLES.sql` – predefined roles for database seeding
- `menu-config.json` – for dynamic sidebar menus per role (optional)
