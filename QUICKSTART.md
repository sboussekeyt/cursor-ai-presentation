# Spring Boot Task Management API - Quick Start Guide

## 🚀 Get Started in 5 Minutes

### Prerequisites
- Java 17+ ([Download OpenJDK](https://adoptium.net/))
- Maven 3.6+ ([Download Maven](https://maven.apache.org/download.cgi))

### Installation

```bash
# 1. Verify Java installation
java -version
# Should show: java version "17.x.x" or higher

# 2. Verify Maven installation
mvn -version
# Should show: Apache Maven 3.x.x

# 3. Navigate to project directory
cd cursor-ai-presentation

# 4. Build the project
mvn clean install

# 5. Run the application
mvn spring-boot:run
```

### Verify Installation

Open your browser and navigate to:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs
- **H2 Console**: http://localhost:8080/h2-console

If you see the Swagger UI interface, you're ready to go! 🎉

## 🧪 Test the API

### Step 1: Register a User

Click on **POST /api/auth/register** in Swagger UI, then click "Try it out":

```json
{
  "username": "testuser",
  "email": "test@example.com",
  "password": "password123",
  "firstName": "Test",
  "lastName": "User"
}
```

**Response:** You'll receive a JWT token
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "username": "testuser"
}
```

### Step 2: Authorize

1. Copy the `accessToken` from the response
2. Click the **Authorize** button (🔓 icon at top)
3. Enter: `Bearer <paste-your-token-here>`
4. Click **Authorize**, then **Close**

### Step 3: Create a Task

Click on **POST /api/tasks**, then "Try it out":

```json
{
  "title": "My First Task",
  "description": "Testing the API",
  "status": "TODO",
  "priority": "HIGH"
}
```

### Step 4: Get Your Tasks

Click on **GET /api/tasks/my-tasks**, then "Execute"

You'll see your created task in the response!

## 📝 Common Commands

```bash
# Run tests only
mvn test

# Run tests with coverage report
mvn clean verify
open target/site/jacoco/index.html

# Clean and rebuild
mvn clean install

# Run with different port
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081

# Run in debug mode
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

## 🎯 Key Features

- ✅ **JWT Authentication**: Secure token-based auth
- ✅ **CRUD Operations**: Full task management
- ✅ **Swagger UI**: Interactive API documentation
- ✅ **TDD Approach**: Tests first, 85%+ coverage
- ✅ **Exception Handling**: Proper error responses
- ✅ **Data Validation**: Request validation

## 📚 Available Endpoints

### Authentication (Public)
- `POST /api/auth/register` - Create account
- `POST /api/auth/login` - Get JWT token

### Tasks (Protected)
- `POST /api/tasks` - Create task
- `GET /api/tasks/{id}` - Get task by ID
- `GET /api/tasks/my-tasks` - Get my tasks
- `GET /api/tasks/my-tasks/status/{status}` - Filter by status
- `GET /api/tasks/my-tasks/priority/{priority}` - Filter by priority
- `PUT /api/tasks/{id}` - Update task
- `DELETE /api/tasks/{id}` - Delete task

## 🔧 Configuration

Edit [src/main/resources/application.properties](src/main/resources/application.properties) to customize:

```properties
# Change server port
server.port=8080

# JWT expiration (milliseconds)
jwt.expiration=86400000

# Database URL (H2 in-memory)
spring.datasource.url=jdbc:h2:mem:taskdb
```

## 🐛 Troubleshooting

### Port 8080 already in use
```bash
# Option 1: Change port
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081

# Option 2: Kill process using port 8080
lsof -ti:8080 | xargs kill -9
```

### Tests failing
```bash
# Clean and rebuild
mvn clean install

# Run tests with detailed output
mvn test -X
```

### "Command not found: mvn"
Maven is not installed. Download from: https://maven.apache.org/download.cgi

### "Unable to locate a Java Runtime"
Java is not installed. Download OpenJDK from: https://adoptium.net/

## 📖 Next Steps

1. **Read Full Documentation**: [SPRING_BOOT_PROJECT_README.md](SPRING_BOOT_PROJECT_README.md)
2. **Check Implementation Details**: [claudedocs/spring-boot-implementation-summary.md](claudedocs/spring-boot-implementation-summary.md)
3. **Explore Swagger UI**: http://localhost:8080/swagger-ui.html
4. **View Test Coverage**: `mvn verify` then open `target/site/jacoco/index.html`

## 🎓 Learning Resources

- **Spring Boot Docs**: https://spring.io/projects/spring-boot
- **Spring Security**: https://spring.io/projects/spring-security
- **JWT Introduction**: https://jwt.io/introduction
- **SpringDoc OpenAPI**: https://springdoc.org/

## 💡 Pro Tips

1. Use Swagger UI for interactive testing - no need for Postman!
2. Check H2 Console to see database state in real-time
3. Look at test cases to understand expected behavior
4. JWT tokens expire after 24 hours - just login again
5. Use the "Authorize" button once, works for all endpoints

## ⚡ Quick Test Flow

```bash
# 1. Start app
mvn spring-boot:run

# 2. In another terminal, test with curl
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"demo","email":"demo@test.com","password":"password123"}'

# 3. Copy the token from response, then:
curl -X GET http://localhost:8080/api/tasks/my-tasks \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

---

**Need help?** Check the [full documentation](SPRING_BOOT_PROJECT_README.md) or review the [implementation summary](claudedocs/spring-boot-implementation-summary.md).

**Ready to code?** Start exploring the codebase at [src/main/java/com/taskmanagement/](src/main/java/com/taskmanagement/)!
