# 📇 MyContact — CRUD & Form-based Login

## 📖 Giới thiệu
**MyContact** là một ứng dụng Spring Boot cho phép:
- Quản lý danh bạ (CRUD: Tạo, Xem, Sửa, Xóa)
- Đăng nhập bằng **Form-based Authentication**
- Chỉ người dùng đã đăng nhập mới có thể quản lý danh bạ

Ứng dụng được xây dựng với **Spring Boot**, **Spring Security**, và **Thymeleaf**.
---
## 🛠 Công nghệ sử dụng

### Backend
- **Java 17+**
- **Spring Boot** (Web, Security, Data JPA, Validation)
- **Hibernate**
- **MySQL** (hoặc H2 Database cho dev)
- **Maven**

### Frontend
- **Thymeleaf**
---

## ⚙️ Cài đặt và chạy dự án

### 1️ Yêu cầu hệ thống
- Java 17+
- Maven 3.8+
- MySQL (hoặc H2 nếu cấu hình test)

### 2️ Cấu hình Database
Tạo database:
```sql
CREATE DATABASE mycontact CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
