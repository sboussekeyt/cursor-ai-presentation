package com.taskmanagement.dto;

import com.taskmanagement.entity.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a task in the API response.
 * Includes task details and metadata, converted from {@link com.taskmanagement.entity.Task} entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Task response")
public class TaskResponse {

    @Schema(description = "Task ID", example = "1")
    private Long id;

    @Schema(description = "Task title", example = "Complete project documentation")
    private String title;

    @Schema(description = "Task description", example = "Write comprehensive documentation for the API")
    private String description;

    @Schema(description = "Task status", example = "TODO")
    private Task.TaskStatus status;

    @Schema(description = "Task priority", example = "HIGH")
    private Task.TaskPriority priority;

    @Schema(description = "Task due date", example = "2024-12-31T23:59:59")
    private LocalDateTime dueDate;

    @Schema(description = "Assigned user username", example = "john.doe")
    private String assignedUserUsername;

    @Schema(description = "Task creation timestamp", example = "2024-01-01T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Task last update timestamp", example = "2024-01-15T14:30:00")
    private LocalDateTime updatedAt;

    public static TaskResponse fromEntity(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());
        response.setDueDate(task.getDueDate());
        response.setAssignedUserUsername(task.getAssignedUser().getUsername());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        return response;
    }
}
