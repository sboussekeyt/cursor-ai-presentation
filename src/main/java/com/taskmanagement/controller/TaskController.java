package com.taskmanagement.controller;

import com.taskmanagement.dto.TaskRequest;
import com.taskmanagement.dto.TaskResponse;
import com.taskmanagement.entity.Task;
import com.taskmanagement.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing CRUD endpoints for tasks.
 *
 * Responsibilities:
 * - Documents and validates HTTP endpoints; delegates business logic to {@link com.taskmanagement.service.TaskService}.
 * - Secured with JWT: all routes require authentication (see {@code SecurityConfig}); current user is resolved via {@link org.springframework.security.core.Authentication}.
 * - Uses DTOs: accepts {@link com.taskmanagement.dto.TaskRequest} and returns {@link com.taskmanagement.dto.TaskResponse}; entities are never exposed.
 * - Errors are handled centrally by {@link com.taskmanagement.exception.GlobalExceptionHandler} (e.g., 400/401/404).
 *
 * Endpoints overview:
 * - POST    /api/tasks                              → create task for authenticated user
 * - GET     /api/tasks/{id}                         → get task by ID
 * - GET     /api/tasks                              → list all tasks (admin use)
 * - GET     /api/tasks/my-tasks                     → list current user's tasks
 * - GET     /api/tasks/my-tasks/status/{status}     → filter current user's tasks by status
 * - GET     /api/tasks/my-tasks/priority/{priority} → filter current user's tasks by priority
 * - PUT     /api/tasks/{id}                         → update task
 * - DELETE  /api/tasks/{id}                         → delete task
 *
 * OpenAPI annotations provide rich documentation and mark endpoints as secured via {@code @SecurityRequirement("bearerAuth")}.
 */
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Task management endpoints for CRUD operations")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(
        summary = "Create a new task",
        description = "Creates a new task assigned to the authenticated user. Title is required, other fields are optional."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Task created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TaskResponse.class),
                examples = @ExampleObject(value = """
                    {
                        "id": 1,
                        "title": "Complete project documentation",
                        "description": "Write comprehensive documentation for the API",
                        "status": "TODO",
                        "priority": "HIGH",
                        "dueDate": "2024-12-31T23:59:59",
                        "assignedUserUsername": "john.doe",
                        "createdAt": "2024-01-01T10:00:00",
                        "updatedAt": "2024-01-01T10:00:00"
                    }
                    """)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid request body - validation failed"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing JWT token"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<TaskResponse> createTask(
        @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Task creation request",
            required = true,
            content = @Content(
                schema = @Schema(implementation = TaskRequest.class),
                examples = @ExampleObject(value = """
                    {
                        "title": "Complete project documentation",
                        "description": "Write comprehensive documentation for the API",
                        "status": "TODO",
                        "priority": "HIGH",
                        "dueDate": "2024-12-31T23:59:59"
                    }
                    """)
            )
        ) TaskRequest request,
        Authentication authentication
    ) {
        TaskResponse response = taskService.createTask(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get task by ID",
        description = "Retrieves a single task by its unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Task retrieved successfully",
            content = @Content(schema = @Schema(implementation = TaskResponse.class))
        ),
        @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing JWT token"),
        @ApiResponse(responseCode = "404", description = "Task not found with the specified ID")
    })
    public ResponseEntity<TaskResponse> getTaskById(
        @Parameter(description = "Task ID", required = true, example = "1")
        @PathVariable Long id
    ) {
        TaskResponse response = taskService.getTaskById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
        summary = "Get all tasks",
        description = "Retrieves all tasks in the system. Admin operation that returns tasks for all users."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Tasks retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class))
            )
        ),
        @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing JWT token")
    })
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<TaskResponse> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/my-tasks")
    @Operation(
        summary = "Get current user's tasks",
        description = "Retrieves all tasks assigned to the authenticated user"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User tasks retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class))
            )
        ),
        @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing JWT token"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<TaskResponse>> getMyTasks(Authentication authentication) {
        List<TaskResponse> tasks = taskService.getTasksByUser(authentication.getName());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/my-tasks/status/{status}")
    @Operation(
        summary = "Get tasks by status",
        description = "Retrieves all tasks for the authenticated user filtered by status"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Tasks retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class))
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid status value"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing JWT token"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(
        @Parameter(
            description = "Task status filter",
            required = true,
            schema = @Schema(allowableValues = {"TODO", "IN_PROGRESS", "COMPLETED", "CANCELLED"}),
            example = "TODO"
        )
        @PathVariable Task.TaskStatus status,
        Authentication authentication
    ) {
        List<TaskResponse> tasks = taskService.getTasksByStatus(authentication.getName(), status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/my-tasks/priority/{priority}")
    @Operation(
        summary = "Get tasks by priority",
        description = "Retrieves all tasks for the authenticated user filtered by priority level"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Tasks retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class))
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid priority value"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing JWT token"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<TaskResponse>> getTasksByPriority(
        @Parameter(
            description = "Task priority filter",
            required = true,
            schema = @Schema(allowableValues = {"LOW", "MEDIUM", "HIGH", "URGENT"}),
            example = "HIGH"
        )
        @PathVariable Task.TaskPriority priority,
        Authentication authentication
    ) {
        List<TaskResponse> tasks = taskService.getTasksByPriority(authentication.getName(), priority);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update a task",
        description = "Updates an existing task. All fields in the request body are optional - only provided fields will be updated."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Task updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TaskResponse.class),
                examples = @ExampleObject(value = """
                    {
                        "id": 1,
                        "title": "Updated task title",
                        "description": "Updated description",
                        "status": "IN_PROGRESS",
                        "priority": "URGENT",
                        "dueDate": "2024-12-31T23:59:59",
                        "assignedUserUsername": "john.doe",
                        "createdAt": "2024-01-01T10:00:00",
                        "updatedAt": "2024-01-15T14:30:00"
                    }
                    """)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid request body - validation failed"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing JWT token"),
        @ApiResponse(responseCode = "404", description = "Task not found with the specified ID")
    })
    public ResponseEntity<TaskResponse> updateTask(
        @Parameter(description = "Task ID", required = true, example = "1")
        @PathVariable Long id,
        @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Task update request (all fields optional)",
            required = true,
            content = @Content(
                schema = @Schema(implementation = TaskRequest.class),
                examples = @ExampleObject(value = """
                    {
                        "title": "Updated task title",
                        "status": "IN_PROGRESS",
                        "priority": "URGENT"
                    }
                    """)
            )
        ) TaskRequest request
    ) {
        TaskResponse response = taskService.updateTask(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a task",
        description = "Permanently deletes a task by its ID. This action cannot be undone."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing JWT token"),
        @ApiResponse(responseCode = "404", description = "Task not found with the specified ID")
    })
    public ResponseEntity<Void> deleteTask(
        @Parameter(description = "Task ID to delete", required = true, example = "1")
        @PathVariable Long id
    ) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
