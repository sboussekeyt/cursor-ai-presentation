package com.taskmanagement.repository;

import com.taskmanagement.entity.Task;
import com.taskmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link Task} entities.
 * Provides custom query methods for filtering tasks by user and status/priority.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedUser(User user);

    List<Task> findByAssignedUserAndStatus(User user, Task.TaskStatus status);

    List<Task> findByAssignedUserAndPriority(User user, Task.TaskPriority priority);

    List<Task> findByStatus(Task.TaskStatus status);
}
