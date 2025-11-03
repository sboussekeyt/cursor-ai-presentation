You are an expert Spring Boot developer. I need you to create a REST API 
for a Task Management system following TDD principles.

Requirements:
- Spring Boot 3.x, Java 17+
- JPA/Hibernate for persistence
- User authentication (JWT tokens)
- CRUD operations for tasks
- Test-first approach with JUnit 5 and Mockito
- Springdoc OpenAPI (Swagger) for API documentation
- Code coverage minimum 85% (verified with JaCoCo)

Deliverables:
1. Test class first (TaskServiceTest) - TDD approach
2. Implementation (TaskService) to pass all tests
3. REST Controller (TaskController) with proper endpoint documentation
4. Simple User authentication system
5. Swagger/OpenAPI annotations for all endpoints
6. JaCoCo configuration for coverage reporting

Quality Standards:
- Write failing tests FIRST, then implementation
- Ensure every public method is tested
- Code coverage must exceed 85%
- All endpoints documented with @Operation, @ApiResponse annotations
- Include request/response examples in Swagger
- Proper exception handling with documented error responses

Endpoint Documentation Requirements:
- Each endpoint must have clear description
- Include parameter descriptions
- Document all possible HTTP status codes (200, 400, 401, 404, 500, etc.)
- Provide example requests and responses
- Mark required vs optional parameters

Expected structure:
- TaskService (business logic with tests)
- TaskController (REST endpoints with Swagger annotations)
- TaskRepository (JPA interface)
- User/Authentication (JWT-based)
- pom.xml with springdoc-openapi and jacoco dependencies
- Test configuration for 85%+ coverage

Please generate the complete implementation with tests first.
