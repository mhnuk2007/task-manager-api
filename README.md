# 📝 Task Manager API

**Task Manager API** is a RESTful backend built with **Spring Boot 3**, **Spring Security**, and **JPA/Hibernate** for managing users, boards, and tasks. It supports JWT-based authentication, role-based registration, and CRUD operations for tasks and boards.

---

## 🚀 Features

- **Authentication & Authorization**
  - User registration
  - Admin registration
  - JWT-based login and token validation

- **Boards Management**
  - Create, retrieve, and delete boards
  - Fetch boards by user or fetch all boards

- **Tasks Management**
  - Create, retrieve, and delete tasks
  - Fetch tasks by board or by user

- **Cross-Origin Support**
  - Configured to allow requests from frontend (`http://localhost:5173`)

---

## 🏗️ Architecture Overview

```
Frontend (React/Next.js)
          |
          v
     API Gateway / Direct Requests
          |
 +--------+--------+
 |  AuthController |
 | BoardController |
 | TaskController  |
 +----------------+
          |
       Services
   (AuthService, BoardService, TaskService)
          |
      Repository Layer
          |
       MySQL Database
```

---

## 📦 Technologies Used

- **Backend**: Spring Boot 3, Spring Security
- **Database**: MySQL
- **ORM**: JPA/Hibernate
- **Authentication**: JWT
- **Build Tool**: Maven
- **Java Version**: 17+
- **Libraries**: Lombok, JJWT

---

## ⚡ Endpoints Overview

### AuthController

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/auth/register` | POST | Register a user |
| `/api/auth/register-admin` | POST | Register an admin |
| `/api/auth/login` | POST | Authenticate and receive JWT |

### BoardController

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/boards` | POST | Create a new board |
| `/api/boards` | GET | Get all boards |
| `/api/boards/user/{userId}` | GET | Get boards by user |
| `/api/boards/{id}` | GET | Get a board by ID |
| `/api/boards/{id}` | DELETE | Delete a board |

### TaskController

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/tasks` | POST | Create a new task |
| `/api/tasks/board/{boardId}` | GET | Get tasks for a board |
| `/api/tasks/user/{userId}` | GET | Get tasks assigned to a user |
| `/api/tasks/{taskId}` | DELETE | Delete a task |

---

## 🛠️ Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL database
- Postman (or any REST client) for testing

### Setup Steps

1. Clone the repository:
```bash
git clone https://github.com/mhnuk2007/task-manager-api.git
cd task-manager-api
```

2. Configure your `application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
jwt.secret=your_jwt_secret_key
```

3. Build and run the application:
```bash
mvn clean install
mvn spring-boot:run
```

4. The API will be accessible at: `http://localhost:8080`

---

## 🔑 Authentication Flow

1. Users register or login via `/api/auth`.
2. AuthService issues a JWT token.
3. Include JWT in the `Authorization` header for all secured requests:
```
Authorization: Bearer <JWT_TOKEN>
```

---

## 📂 Project Structure

```
task-manager-api/
│
├── src/main/java/com/learning/taskmanagerapi/
│   ├── controller/       # REST Controllers
│   ├── service/          # Business logic
│   ├── repository/       # JPA Repositories
│   ├── dto/              # Data Transfer Objects
│   └── entity/           # Database Entities
│
├── src/main/resources/
│   └── application.properties
│
├── pom.xml
└── README.md
```

---

## 📜 License

This project is licensed under the **MIT License**.

---

## 👨‍💻 Author

**Mohan Lal (mhnuk2007)**  
GitHub: [https://github.com/mhnuk2007](https://github.com/mhnuk2007)

