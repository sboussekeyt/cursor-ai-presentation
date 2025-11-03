package com.taskmanagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Task Management API",
        version = "1.0.0",
        description = """
            REST API for managing tasks with user authentication.

            ## Features
            - JWT-based authentication
            - Full CRUD operations for tasks
            - Task filtering by status and priority
            - User registration and login

            ## Authentication
            To use protected endpoints:
            1. Register a new account via `/api/auth/register` or login via `/api/auth/login`
            2. Copy the returned JWT token
            3. Click the 'Authorize' button and enter: `Bearer <your-token>`
            4. All subsequent requests will include the authentication header

            ## Test Coverage
            This API is built with TDD principles and maintains 85%+ code coverage.
            """,
        contact = @Contact(
            name = "API Support",
            email = "support@taskmanagement.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Local development server"),
        @Server(url = "https://api.taskmanagement.com", description = "Production server")
    }
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "JWT authentication token. Use the /api/auth/login or /api/auth/register endpoint to obtain a token."
)
public class OpenApiConfig {
}
