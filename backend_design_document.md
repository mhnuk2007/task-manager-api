# 🧠 Backend Design Document: Task Management System (Trello Clone)

## I. Introduction and Goals

This document outlines the architecture and design specifications for the **backend API** of the collaborative **Task Management System**, a clone of **Trello**.  
The primary goal is to provide a **robust**, **secure**, and **real-time capable** foundation for the frontend application.

### 🧩 Key Technologies
| Layer | Technology |
|-------|-------------|
| **Framework** | Spring Boot 3+ (Java) |
| **Database** | MySQL (Relational) |
| **Persistence** | Spring Data JPA / Hibernate |
| **Security** | JWT (JSON Web Tokens) |
| **Real-time** | Spring WebSocket (STOMP) |

### 🎯 Core Backend Objectives
- Provide **secure, stateless authentication** using JWT.
- Enforce **Role-Based Access Control (RBAC)** for admin and user operations.
- Implement **CRUD** for Boards, Lists (Columns), and Tasks.
- Enable **real-time collaboration** via WebSockets for live updates.

---

## II. Architecture Overview

The backend employs a **Layered Architecture** commonly used in Spring Boot applications.

### A. Layers

| Layer | Responsibility | Components |
|-------|----------------|-------------|
| **Controller** | Handles HTTP requests, input validation, and returns responses. | `@RestController` classes |
| **Service** | Business logic, transaction management, and repository interaction. | `@Service` classes |
| **Repository** | Data access using Spring Data JPA. | `@Repository` interfaces |
| **Security** | JWT-based authentication and authorization. | `SecurityConfig`, `JwtFilter` |
| **WebSocket** | Handles STOMP messaging for real-time updates. | `WebSocketConfig`, message handlers |

---

## III. Data Model (Entity-Relationship Design)

The database schema revolves around **User**, **Board**, **List (Column)**, and **Task** entities.

| Entity | Description | Key Fields | Relationships |
|---------|--------------|-------------|----------------|
| **User** | Represents a system user or team member. | `id`, `username`, `email`, `password (hashed)`, `role` | 1:M with Boards (Owner), M:N with Tasks (Assignee) |
| **Board** | The main workspace for organizing tasks. | `id`, `title`, `description`, `ownerId` | 1:M with Lists |
| **List** | Represents a column within a board (e.g., To Do, In Progress, Done). | `id`, `title`, `position` | M:1 with Board, 1:M with Tasks |
| **Task** | A unit of work within a list. | `id`, `title`, `description`, `priority`, `dueDate`, `listId` | M:1 with List, M:N with Users (Assignees) |

---

## IV. API Design (REST Endpoints)

All protected endpoints require a valid **JWT** in the `Authorization: Bearer <token>` header.

| Feature | Method | Endpoint | Authorization | Description |
|----------|---------|-----------|----------------|--------------|
| **Auth** | POST | `/api/auth/register` | Public | Register a new user |
| **Auth** | POST | `/api/auth/login` | Public | Authenticate and receive JWT |
| **Boards** | GET | `/api/boards` | AUTHENTICATED | Get all boards for the current user |
| **Boards** | POST | `/api/boards` | AUTHENTICATED | Create a new board |
| **Tasks** | POST | `/api/boards/{boardId}/tasks` | AUTHENTICATED | Create a new task in a board |
| **Tasks** | PATCH | `/api/tasks/{taskId}/move` | AUTHENTICATED | Move a task between lists/boards |
| **Tasks** | PUT | `/api/tasks/{taskId}/assign` | AUTHENTICATED | Assign users to a task |
| **Admin** | GET | `/api/admin/users` | ROLE_ADMIN | Get all registered system users |

---

## V. Security Design (JWT & RBAC)

### A. Authentication Flow
1. **Client Login:** User sends credentials to `/api/auth/login`.
2. **Server Validation:** If valid, the server generates a **JWT** with user ID, username, and role.
3. **Token Return:** The JWT is returned to the client in the response body or header.
4. **Subsequent Requests:** Client includes the token in the `Authorization` header for all protected APIs.

### B. Authorization (RBAC)

| Role | Permissions |
|------|--------------|
| **ROLE_ADMIN** | Full CRUD access to all resources and admin endpoints |
| **ROLE_USER** | Access limited to boards and tasks they own or are assigned to |

- Role checks implemented via **Spring Security** using JWT claims.
- Use `@PreAuthorize` annotations for fine-grained method-level security.
- Additional rule enforcement occurs in the **Service layer**.

---

## VI. Real-time Communication Design (WebSocket)

WebSockets enable **real-time updates** for all users collaborating on the same board, reducing the need for API polling.

### A. WebSocket Configuration (STOMP)
- **Endpoint:** `/ws`
- **Broker:** Simple in-memory broker (default) or RabbitMQ for scalability.
- **Library:** `spring-boot-starter-websocket`

### B. Subscription Strategy

| Event | Topic | Client Action | Server Action |
|--------|--------|----------------|----------------|
| Board opened | `/topic/board/{boardId}` | Client subscribes when viewing a board | Server pushes updates when tasks change |
| Task moved | `/topic/board/{boardId}` | Client updates UI | Backend publishes `TASK_MOVED` event |

#### Flow Example:
1. User A opens **Board 123** → subscribes to `/topic/board/123`.
2. User B moves a task in Board 123 → triggers `PATCH /api/tasks/{id}/move`.
3. The backend updates MySQL and broadcasts an update via:
   ```java
   simpMessagingTemplate.convertAndSend("/topic/board/123", messagePayload);
4. User A’s frontend updates instantly.

### C. Message Content
Messages should be minimal and descriptive:
```json
{
"event": "TASK_MOVED",
"taskId": 45,
"newListId": 3,
"newPosition": 2
}
```

## 🏁 Summary
This backend design ensures:
- Scalable REST API with JWT authentication
- Role-based access and secure endpoints
- Real-time collaboration via WebSockets
- Clean architecture separating controller, service, and repository logic
