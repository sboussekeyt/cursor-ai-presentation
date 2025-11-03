package com.taskmanagement.dto;

import com.taskmanagement.entity.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Task creation/update request")
public class TaskRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    @Schema(description = "Task title", example = "Complete project documentation", required = true)
    private String title;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(description = "Task description", example = "Write comprehensive documentation for the API")
    private String description;

    @Schema(description = "Task status", example = "TODO", allowableValues = {"TODO", "IN_PROGRESS", "COMPLETED", "CANCELLED"})
    private Task.TaskStatus status;

    @Schema(description = "Task priority", example = "HIGH", allowableValues = {"LOW", "MEDIUM", "HIGH", "URGENT"})
    private Task.TaskPriority priority;

    @Schema(description = "Task due date", example = "2024-12-31T23:59:59")
    private LocalDateTime dueDate;
}
