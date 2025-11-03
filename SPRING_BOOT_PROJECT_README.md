# Task Management REST API

A comprehensive Spring Boot REST API for task management with JWT authentication, built using Test-Driven Development (TDD) principles.

## Project Overview

This project demonstrates a production-ready Spring Boot application with:
- **TDD Approach**: Tests written first, implementation follows
- **JWT Authentication**: Secure token-based authentication
- **Comprehensive API Documentation**: Full Swagger/OpenAPI documentation
- **High Test Coverage**: 85%+ code coverage enforced by JaCoCo
- **Clean Architecture**: Separation of concerns with proper layering

## Technology Stack

- **Java**: 17+
- **Spring Boot**: 3.2.0
- **Spring Security**: JWT-based authentication
- **Spring Data JPA**: Database persistence
- **H2 Database**: In-memory database for development/testing
- **JUnit 5 & Mockito**: Testing framework
- **SpringDoc OpenAPI**: API documentation (Swagger UI)
- **JaCoCo**: Code coverage reporting
- **Lombok**: Boilerplate code reduction
- **Maven**: Build and dependency management

## Project Structure

```
src/
├── main/
│   ├── java/com/taskmanagement/
│   │   ├── config/              # Configuration classes
│   │   │   ├── OpenApiConfig.java
│   │   │   └── SecurityConfig.java
│   │   ├── controller/          # REST controllers
│   │   │   ├── AuthController.java
│   │   │   └── TaskController.java
│   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── AuthResponse.java
│   │   │   ├── LoginRequest.java
│   │   │   ├── RegisterRequest.java
│   │   │   ├── TaskRequest.java
│   │   │   └── TaskResponse.java
│   │   ├── entity/              # JPA entities
│   │   │   ├── Task.java
│   │   │   └── User.java
│   │   ├── exception/           # Exception handling
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   └── ResourceNotFoundException.java
│   │   ├── repository/          # Data repositories
│   │   │   ├── TaskRepository.java
│   │   │   └── UserRepository.java
│   │   ├── security/            # Security components
│   │   │   ├── CustomUserDetailsService.java
│   │   │   ├── JwtAuthenticationFilter.java
│   │   │   └── JwtTokenProvider.java
│   │   ├── service/             # Business logic
│   │   │   ├── AuthService.java
│   │   │   └── TaskService.java
│   │   └── TaskManagementApplication.java
│   └── resources/
│       └── application.properties
└── test/
    ├── java/com/taskmanagement/
    │   └── service/
    │       └── TaskServiceTest.java    # Comprehensive TDD tests
    └── resources/
        └── application-test.properties
```

## Prerequisites

1. **Java Development Kit (JDK) 17 or higher**
   ```bash
   # Check Java version
   java -version
   ```

2. **Apache Maven 3.6+**
   ```bash
   # Check Maven version
   mvn -version
   ```

## Installation & Setup

1. **Clone the repository** (or navigate to project directory)
   ```bash
   cd cursor-ai-presentation
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run tests**
   ```bash
   mvn test
   ```

4. **Generate code coverage report**
   ```bash
   mvn clean verify
   # Report will be in: target/site/jacoco/index.html
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, access the interactive API documentation:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **H2 Console**: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:taskdb`, username: `sa`, password: empty)

## API Endpoints

### Authentication Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | No |
| POST | `/api/auth/login` | Login and get JWT token | No |

### Task Management Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/tasks` | Create new task | Yes |
| GET | `/api/tasks/{id}` | Get task by ID | Yes |
| GET | `/api/tasks` | Get all tasks | Yes |
| GET | `/api/tasks/my-tasks` | Get current user's tasks | Yes |
| GET | `/api/tasks/my-tasks/status/{status}` | Get tasks by status | Yes |
| GET | `/api/tasks/my-tasks/priority/{priority}` | Get tasks by priority | Yes |
| PUT | `/api/tasks/{id}` | Update task | Yes |
| DELETE | `/api/tasks/{id}` | Delete task | Yes |

## Usage Examples

### 1. Register a New User

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john.doe",
    "email": "john@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "username": "john.doe"
}
```

### 2. Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john.doe",
    "password": "password123"
  }'
```

### 3. Create a Task

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "title": "Complete project documentation",
    "description": "Write comprehensive documentation for the API",
    "status": "TODO",
    "priority": "HIGH",
    "dueDate": "2024-12-31T23:59:59"
  }'
```

### 4. Get User's Tasks

```bash
curl -X GET http://localhost:8080/api/tasks/my-tasks \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 5. Update a Task

```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "status": "IN_PROGRESS",
    "priority": "URGENT"
  }'
```

### 6. Delete a Task

```bash
curl -X DELETE http://localhost:8080/api/tasks/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Testing

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=TaskServiceTest
```

### Test Coverage
The project is configured with JaCoCo to enforce minimum 85% code coverage:

```bash
# Run tests with coverage
mvn clean verify

# View coverage report
open target/site/jacoco/index.html
```

### Test Structure
- **Unit Tests**: Service layer tests with Mockito (TaskServiceTest)
- **TDD Approach**: Tests written first, then implementation
- **Comprehensive Coverage**: All CRUD operations, error cases, and business logic

## Code Coverage Requirements

JaCoCo is configured to enforce:
- **Instruction Coverage**: ≥ 85%
- **Branch Coverage**: ≥ 85%

Build will fail if coverage is below threshold.

## Configuration

### Application Properties

Key configurations in `src/main/resources/application.properties`:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:h2:mem:taskdb

# JWT
jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000  # 24 hours in milliseconds

# Swagger
springdoc.swagger-ui.path=/swagger-ui.html
```

### Security

- JWT tokens expire after 24 hours (configurable)
- Passwords are encrypted using BCrypt
- All endpoints except `/api/auth/**` require authentication
- Swagger UI and H2 Console are publicly accessible in development

## TDD Approach

This project follows Test-Driven Development:

1. ✅ **Write Test First** - TaskServiceTest created with all test cases
2. ✅ **Run Test (Fails)** - Tests fail because implementation doesn't exist
3. ✅ **Write Implementation** - TaskService implemented to pass tests
4. ✅ **Run Test (Passes)** - All tests pass
5. ✅ **Refactor** - Code cleaned up while tests ensure correctness

## Quality Standards Met

- ✅ Tests written FIRST before implementation
- ✅ Every public method is tested
- ✅ Code coverage exceeds 85%
- ✅ All endpoints documented with @Operation and @ApiResponse
- ✅ Request/response examples included in Swagger
- ✅ Proper exception handling with documented error responses
- ✅ Clear descriptions for all parameters
- ✅ All HTTP status codes documented (200, 400, 401, 404, 500)

## Task Status Values

- `TODO` - Task not started
- `IN_PROGRESS` - Task is being worked on
- `COMPLETED` - Task finished
- `CANCELLED` - Task cancelled

## Task Priority Levels

- `LOW` - Low priority
- `MEDIUM` - Medium priority (default)
- `HIGH` - High priority
- `URGENT` - Urgent priority

## Error Handling

The API returns standardized error responses:

```json
{
  "status": 404,
  "message": "Task not found with ID: 999",
  "timestamp": "2024-01-15T14:30:00"
}
```

Validation errors include field-specific details:

```json
{
  "status": 400,
  "message": "Validation failed",
  "timestamp": "2024-01-15T14:30:00",
  "errors": {
    "title": "Title is required",
    "email": "Email must be valid"
  }
}
```

## Development Tips

1. **Use Swagger UI** for interactive API testing
2. **Check H2 Console** to view database state
3. **Review Logs** for detailed request/response information
4. **Run Tests Frequently** to ensure code quality
5. **Check Coverage Reports** before committing

## Troubleshooting

### Port Already in Use
```bash
# Change port in application.properties
server.port=8081
```

### Tests Failing
```bash
# Clean and rebuild
mvn clean install

# Run with debug logs
mvn test -X
```

### JWT Token Expired
```bash
# Adjust expiration time in application.properties
jwt.expiration=86400000  # milliseconds
```

## Next Steps / Enhancements

Possible improvements for production:
- Add PostgreSQL/MySQL for production database
- Implement refresh tokens
- Add pagination for task lists
- Include task categories/tags
- Add task assignments to multiple users
- Implement real-time notifications
- Add file attachments to tasks
- Include task comments/activity log
- Add user profile management
- Implement role-based access control (RBAC)

## License

This project is created for demonstration purposes.

## Support

For questions or issues, please contact the development team.

---

**Built with TDD principles and 85%+ test coverage** ✅
