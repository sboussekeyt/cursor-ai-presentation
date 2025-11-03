package com.taskmanagement.service;

import com.taskmanagement.dto.TaskRequest;
import com.taskmanagement.dto.TaskResponse;
import com.taskmanagement.entity.Task;
import com.taskmanagement.entity.User;
import com.taskmanagement.exception.ResourceNotFoundException;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TaskService Tests - TDD Approach")
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private User testUser;
    private Task testTask;
    private TaskRequest taskRequest;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");

        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Task");
        testTask.setDescription("Test Description");
        testTask.setStatus(Task.TaskStatus.TODO);
        testTask.setPriority(Task.TaskPriority.MEDIUM);
        testTask.setAssignedUser(testUser);
        testTask.setCreatedAt(LocalDateTime.now());
        testTask.setUpdatedAt(LocalDateTime.now());

        taskRequest = new TaskRequest();
        taskRequest.setTitle("New Task");
        taskRequest.setDescription("New Description");
        taskRequest.setStatus(Task.TaskStatus.TODO);
        taskRequest.setPriority(Task.TaskPriority.HIGH);
    }

    @Test
    @DisplayName("Should create task successfully")
    void shouldCreateTaskSuccessfully() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        // When
        TaskResponse response = taskService.createTask(taskRequest, "testuser");

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo(testTask.getTitle());
        assertThat(response.getAssignedUserUsername()).isEqualTo("testuser");

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(taskCaptor.capture());
        Task savedTask = taskCaptor.getValue();
        assertThat(savedTask.getTitle()).isEqualTo(taskRequest.getTitle());
        assertThat(savedTask.getDescription()).isEqualTo(taskRequest.getDescription());
    }

    @Test
    @DisplayName("Should throw exception when creating task with non-existent user")
    void shouldThrowExceptionWhenCreatingTaskWithNonExistentUser() {
        // Given
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> taskService.createTask(taskRequest, "nonexistent"))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("User not found");

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Should get task by ID successfully")
    void shouldGetTaskByIdSuccessfully() {
        // Given
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));

        // When
        TaskResponse response = taskService.getTaskById(1L);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("Test Task");
        verify(taskRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when getting non-existent task")
    void shouldThrowExceptionWhenGettingNonExistentTask() {
        // Given
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> taskService.getTaskById(999L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Task not found");
    }

    @Test
    @DisplayName("Should get all tasks for user")
    void shouldGetAllTasksForUser() {
        // Given
        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");
        task2.setAssignedUser(testUser);
        task2.setStatus(Task.TaskStatus.IN_PROGRESS);
        task2.setPriority(Task.TaskPriority.LOW);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(taskRepository.findByAssignedUser(testUser)).thenReturn(Arrays.asList(testTask, task2));

        // When
        List<TaskResponse> tasks = taskService.getTasksByUser("testuser");

        // Then
        assertThat(tasks).hasSize(2);
        assertThat(tasks.get(0).getTitle()).isEqualTo("Test Task");
        assertThat(tasks.get(1).getTitle()).isEqualTo("Task 2");
        verify(taskRepository).findByAssignedUser(testUser);
    }

    @Test
    @DisplayName("Should get all tasks")
    void shouldGetAllTasks() {
        // Given
        when(taskRepository.findAll()).thenReturn(Arrays.asList(testTask));

        // When
        List<TaskResponse> tasks = taskService.getAllTasks();

        // Then
        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getTitle()).isEqualTo("Test Task");
        verify(taskRepository).findAll();
    }

    @Test
    @DisplayName("Should update task successfully")
    void shouldUpdateTaskSuccessfully() {
        // Given
        TaskRequest updateRequest = new TaskRequest();
        updateRequest.setTitle("Updated Title");
        updateRequest.setDescription("Updated Description");
        updateRequest.setStatus(Task.TaskStatus.COMPLETED);
        updateRequest.setPriority(Task.TaskPriority.URGENT);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        // When
        TaskResponse response = taskService.updateTask(1L, updateRequest);

        // Then
        assertThat(response).isNotNull();
        verify(taskRepository).findById(1L);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent task")
    void shouldThrowExceptionWhenUpdatingNonExistentTask() {
        // Given
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> taskService.updateTask(999L, taskRequest))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Task not found");

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Should delete task successfully")
    void shouldDeleteTaskSuccessfully() {
        // Given
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));
        doNothing().when(taskRepository).delete(testTask);

        // When
        taskService.deleteTask(1L);

        // Then
        verify(taskRepository).findById(1L);
        verify(taskRepository).delete(testTask);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent task")
    void shouldThrowExceptionWhenDeletingNonExistentTask() {
        // Given
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> taskService.deleteTask(999L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Task not found");

        verify(taskRepository, never()).delete(any(Task.class));
    }

    @Test
    @DisplayName("Should get tasks by status")
    void shouldGetTasksByStatus() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(taskRepository.findByAssignedUserAndStatus(testUser, Task.TaskStatus.TODO))
            .thenReturn(Arrays.asList(testTask));

        // When
        List<TaskResponse> tasks = taskService.getTasksByStatus("testuser", Task.TaskStatus.TODO);

        // Then
        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getStatus()).isEqualTo(Task.TaskStatus.TODO);
        verify(taskRepository).findByAssignedUserAndStatus(testUser, Task.TaskStatus.TODO);
    }

    @Test
    @DisplayName("Should get tasks by priority")
    void shouldGetTasksByPriority() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(taskRepository.findByAssignedUserAndPriority(testUser, Task.TaskPriority.HIGH))
            .thenReturn(Arrays.asList(testTask));

        // When
        List<TaskResponse> tasks = taskService.getTasksByPriority("testuser", Task.TaskPriority.HIGH);

        // Then
        assertThat(tasks).hasSize(1);
        verify(taskRepository).findByAssignedUserAndPriority(testUser, Task.TaskPriority.HIGH);
    }

    @Test
    @DisplayName("Should throw exception when getting tasks for non-existent user")
    void shouldThrowExceptionWhenGettingTasksForNonExistentUser() {
        // Given
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> taskService.getTasksByUser("nonexistent"))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("User not found");
    }
}
