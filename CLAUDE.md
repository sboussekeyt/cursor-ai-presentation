# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository Overview

This repository contains **two distinct projects**:

1. **French AI Presentation Website** (HTML/CSS/JS) - Static site about Cursor and AI coding tools
2. **Spring Boot Task Management API** (Java/Spring) - Production-ready REST API with TDD approach

## Build & Test Commands

### Spring Boot API

```bash
# Build project
mvn clean install

# Run tests only
mvn test

# Run specific test class
mvn test -Dtest=TaskServiceTest

# Generate code coverage report (enforces 85% minimum)
mvn clean verify
# View report: target/site/jacoco/index.html

# Run application
mvn spring-boot:run
# Access Swagger UI: http://localhost:8080/swagger-ui.html
# Access H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:taskdb, user: sa)

# Run with different port
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081

# Compile only (faster than full build)
mvn compile

# Force dependency re-download
mvn clean install -U
```

### Presentation Website

No build process - static HTML files. Open `index.html` directly in browser or use any HTTP server.

## Architecture: Spring Boot Task Management API

### TDD-First Development Pattern

This codebase follows **strict Test-Driven Development**:
1. **Tests written FIRST** - See `src/test/java/com/taskmanagement/service/TaskServiceTest.java`
2. **Implementation follows** - See `src/main/java/com/taskmanagement/service/TaskService.java`
3. **Coverage enforced** - JaCoCo fails build if < 85%

When adding features: Write test → Run (should fail) → Implement → Run (should pass) → Refactor

### Layered Architecture Flow

```
HTTP Request
    ↓
Controller (REST endpoints + Swagger docs)
  → @RestController, @RequestMapping
  → Validates input with @Valid
  → Returns DTOs (TaskResponse, AuthResponse)
    ↓
Service (Business logic + @Transactional)
  → Single responsibility per service
  → Throws ResourceNotFoundException on errors
  → Converts Entity ↔ DTO
    ↓
Repository (Data access via Spring Data JPA)
  → Extends JpaRepository<Entity, ID>
  → Custom query methods (findByAssignedUser, etc.)
    ↓
Database (H2 in-memory, easily swappable)
```

### Security Architecture

**JWT Token Flow:**
1. User registers/logs in via `/api/auth/register` or `/api/auth/login`
2. `AuthService` validates credentials using `AuthenticationManager`
3. `JwtTokenProvider` generates signed JWT token
4. Client includes token in `Authorization: Bearer <token>` header
5. `JwtAuthenticationFilter` intercepts requests, validates token
6. `CustomUserDetailsService` loads user details
7. Spring Security `SecurityContext` is populated
8. Controller methods access authenticated user via `Authentication` parameter

**Security Configuration:**
- Public endpoints: `/api/auth/**`, `/swagger-ui/**`, `/v3/api-docs/**`, `/h2-console/**`
- All other endpoints require valid JWT
- Stateless sessions (no server-side session storage)
- BCrypt password encoding

### Entity Relationships

```
User (1) ←→ (N) Task
  ↓               ↓
- id            - id
- username      - title
- email         - description
- password      - status (enum: TODO, IN_PROGRESS, COMPLETED, CANCELLED)
- roles         - priority (enum: LOW, MEDIUM, HIGH, URGENT)
                - dueDate
                - assignedUser (ManyToOne)
                - createdAt, updatedAt (auto-managed)
```

### DTO Pattern Usage

**Never expose entities directly in API responses:**
- `TaskRequest` (input) → `Task` entity → `TaskResponse` (output)
- Use `TaskResponse.fromEntity(task)` for conversion
- DTOs include Swagger `@Schema` annotations for documentation

### Exception Handling Strategy

**Centralized via `GlobalExceptionHandler`:**
- `ResourceNotFoundException` → 404 with custom error message
- `MethodArgumentNotValidException` → 400 with field-level validation errors
- `BadCredentialsException` → 401 for authentication failures
- Generic `Exception` → 500 with sanitized message

All error responses follow consistent format:
```json
{
  "status": 404,
  "message": "Task not found with ID: 999",
  "timestamp": "2024-01-15T14:30:00"
}
```

## Key Configuration Files

### application.properties
- `jwt.secret`: Must be long enough for HS256 (64+ chars)
- `jwt.expiration`: Token lifetime in milliseconds (default: 24 hours)
- `spring.jpa.hibernate.ddl-auto`: Set to `create-drop` for H2, change for production DB

### pom.xml
- JaCoCo enforces 85% instruction + branch coverage
- Uses Spring Boot 3.2.0 (Jakarta EE 9+, not javax)
- Lombok requires annotation processing enabled in IDE

## Swagger/OpenAPI Documentation

**Every endpoint must include:**
- `@Operation` with summary and description
- `@ApiResponses` for ALL status codes (200, 201, 400, 401, 404, 500)
- `@Parameter` descriptions with examples
- Request/response body examples using `@ExampleObject`
- `@SecurityRequirement(name = "bearerAuth")` for protected endpoints

**Configuration:** See `OpenApiConfig.java` for global API metadata

## Testing Strategy

### Service Layer Tests (Unit)
- Located in `src/test/java/com/taskmanagement/service/`
- Mock repositories using `@Mock` and Mockito
- Use `@ExtendWith(MockitoExtension.class)`
- Test both happy paths and error scenarios
- Verify repository method calls with `ArgumentCaptor`

### Test Organization
```java
@BeforeEach   // Setup test data
@Test         // Individual test cases
@DisplayName  // Descriptive test names
```

**Coverage Requirements:**
- Every public method in Service classes must have tests
- Both success and failure scenarios
- Edge cases (null values, empty lists, invalid IDs)

## Lombok Usage

**All entities and DTOs use Lombok:**
- `@Data`: Generates getters, setters, toString, equals, hashCode
- `@NoArgsConstructor`: JPA requires no-arg constructor
- `@AllArgsConstructor`: Convenience constructor
- `@RequiredArgsConstructor`: For services with final fields
- `@Slf4j`: Logger injection

**IDE Setup Required:** Enable annotation processing in IDE settings (IntelliJ, Eclipse, VS Code)

## Common Development Patterns

### Adding New Entity/Resource

1. Create entity class in `entity/` with JPA annotations
2. Create repository interface extending `JpaRepository`
3. Create DTOs: Request (input) and Response (output)
4. **Write service tests first** in `test/service/`
5. Implement service class with business logic
6. Create controller with Swagger documentation
7. Run `mvn test` to verify tests pass
8. Run `mvn verify` to ensure 85%+ coverage

### Adding New Endpoint

1. Add method to Controller with full Swagger annotations
2. Ensure `@ApiResponses` covers all status codes
3. Use `@Valid` for request body validation
4. Return appropriate `ResponseEntity<T>` with HTTP status
5. Handle authentication via `Authentication` parameter
6. Test via Swagger UI at `/swagger-ui.html`

### Modifying Security Rules

Edit `SecurityConfig.java`:
- Add public endpoints to `.requestMatchers("/path/**").permitAll()`
- Protected endpoints automatically require JWT
- Never disable CSRF for non-stateless authentication

## Database Migrations

**Current Setup:** H2 in-memory with `ddl-auto=create-drop` (dev only)

**For Production:**
1. Switch to PostgreSQL/MySQL in `application.properties`
2. Change `ddl-auto` to `validate`
3. Add Flyway/Liquibase for schema migrations
4. Remove H2 dependency from `pom.xml`

## Important Notes

- **Jakarta vs javax:** Spring Boot 3.x uses `jakarta.persistence.*`, not `javax.persistence.*`
- **Java Records:** Used in `GlobalExceptionHandler` for error responses (requires Java 14+)
- **JWT Secret:** Default secret in properties is for development only - use environment variable in production
- **Lazy Loading:** Task entity uses `@ManyToOne(fetch = FetchType.LAZY)` - ensure user is loaded before conversion to DTO
- **Test Profile:** Tests use separate `application-test.properties` with different configuration

## Project Directories

- `prompts/` - Contains `init.md` with original TDD requirements
- `claudedocs/` - Claude-generated documentation and implementation summaries
- `src/main/java/com/taskmanagement/` - Main application code
- `src/test/java/com/taskmanagement/` - Test code
- `target/` - Build output (ignored by git)
- `docs/` - French presentation website (separate from Spring Boot API)

## ⚠️ CRITICAL: TDD & Coverage Requirements

### 🔴 NON-NEGOTIABLE RULES

1. **Tests MUST be written FIRST** - No implementation code without tests
2. **85% minimum coverage** - JaCoCo enforces this in `mvn verify`
3. **Build fails if coverage < 85%** - This is intentional and enforced
4. **Every public method needs tests** - No exceptions

### TDD Workflow - ALWAYS FOLLOW

```
1. Write Test (Red)    → Test MUST fail initially
2. Implement Code      → Make test pass
3. Run Tests (Green)   → Verify test passes
4. Refactor           → Improve code quality
5. Run mvn verify     → Ensure 85%+ coverage
```

### Coverage Checklist

Before marking any task complete, verify:
- [ ] All new public methods have tests
- [ ] Both success and failure scenarios are tested
- [ ] Edge cases are covered (null, empty, invalid input)
- [ ] `mvn test` passes (all tests green)
- [ ] `mvn verify` passes (coverage ≥85%)
- [ ] Coverage report reviewed: `target/site/jacoco/index.html`

### Current Missing Tests (MUST BE ADDED)

**HIGH PRIORITY:**
- `AuthService` - user registration, login, password encoding
- `AuthController` - register/login endpoints, validation errors
- `TaskController` - all CRUD endpoints with auth checks

**MEDIUM PRIORITY:**
- `JwtTokenProvider` - token generation, validation, expiration
- `JwtAuthenticationFilter` - token extraction, validation flow
- `CustomUserDetailsService` - user loading, error handling

### Quick Commands

```bash
# Run tests only
mvn test

# Check coverage (enforces 85%)
mvn verify

# View coverage report
open target/site/jacoco/index.html

# Test specific class
mvn test -Dtest=AuthServiceTest

# Run with debug
mvn test -X
```

## Troubleshooting

**JDK 25 + Lombok incompatibility:** See [TROUBLESHOOTING.md](TROUBLESHOOTING.md) - MUST use JDK 21 or 17

**Compilation errors with getters/setters:** Enable Lombok annotation processing in IDE

**Tests fail:** Ensure mocks are properly configured and test data matches expectations

**Coverage below 85%:** Build will fail - add tests for uncovered methods (THIS IS EXPECTED BEHAVIOR)

**Port 8080 in use:** Change port with `-Dspring-boot.run.arguments=--server.port=8081`

**JWT validation fails:** Check token expiration, ensure secret matches between generation and validation

**For detailed troubleshooting:** See [TROUBLESHOOTING.md](TROUBLESHOOTING.md)
