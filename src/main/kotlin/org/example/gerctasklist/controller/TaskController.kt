package org.example.gerctasklist.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.example.gerctasklist.dto.TaskDto
import org.example.gerctasklist.dto.enums.TaskStatus
import org.example.gerctasklist.service.TaskService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users/tasks")
class TaskController(val taskService: TaskService) {

    /**
     * Endpoint to get all tasks for a user.
     */
    @Operation(
        summary = "Get all tasks for the user",
        description = "Retrieves a list of all tasks associated with the current user.",
        responses = [
            ApiResponse(description = "List of tasks", responseCode = "200"),
            ApiResponse(description = "No tasks found", responseCode = "204")
        ]
    )
    @GetMapping
    fun getAllTasks(): ResponseEntity<List<TaskDto>> {
        val tasks = taskService.getAllUserTask()
        return if (tasks.isNotEmpty()) {
            ResponseEntity.ok(tasks)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    /**
     * Endpoint to get tasks filtered by status.
     */
    @Operation(
        summary = "Get filtered tasks by status",
        description = "Retrieves a list of tasks for the user filtered by their status.",
        responses = [
            ApiResponse(description = "Filtered list of tasks", responseCode = "200"),
            ApiResponse(description = "No tasks found", responseCode = "204")
        ]
    )
    @GetMapping("/filter")
    fun getFilteredTasks(@RequestParam status: TaskStatus): ResponseEntity<List<TaskDto>> {
        val tasks = taskService.getFilteredTask(status)
        return if (tasks.isNotEmpty()) {
            ResponseEntity.ok(tasks)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    /**
     * Endpoint to add a new task.
     */
    @Operation(
        summary = "Add a new task",
        description = "Allows the user to create a new task by providing task details.",
        responses = [
            ApiResponse(description = "Task successfully added", responseCode = "201"),
            ApiResponse(description = "Failed to add task", responseCode = "400")
        ]
    )
    @PostMapping("/add")
    fun addTask(@RequestBody taskDto: TaskDto): ResponseEntity<*> {
        return taskService.addTask(taskDto)
    }

    /**
     * Endpoint to update an existing task.
     */
    @Operation(
        summary = "Update an existing task",
        description = "Updates the details of a specific task by its ID.",
        responses = [
            ApiResponse(description = "Task successfully updated", responseCode = "200"),
            ApiResponse(description = "Task not found", responseCode = "404")
        ]
    )
    @PutMapping("/upd/{taskId}")
    fun updateTask(@PathVariable taskId: Long, @RequestBody taskDto: TaskDto): ResponseEntity<*> {
        return taskService.updateTask(taskId, taskDto)
    }

    /**
     * Endpoint to delete a task.
     */
    @Operation(
        summary = "Delete a task",
        description = "Deletes a task by its ID.",
        responses = [
            ApiResponse(description = "Task successfully deleted", responseCode = "200"),
            ApiResponse(description = "Task not found", responseCode = "404")
        ]
    )
    @DeleteMapping("/del/{taskId}")
    fun deleteTask(@PathVariable taskId: Long): ResponseEntity<*> {
        return taskService.deleteTask(taskId)
    }

    /**
     * Endpoint to update the status of a task.
     */
    @Operation(
        summary = "Update the status of a task",
        description = "Updates the status of a specific task by its ID.",
        responses = [
            ApiResponse(description = "Task status successfully updated", responseCode = "200"),
            ApiResponse(description = "Task not found", responseCode = "404")
        ]
    )
    @PatchMapping("/{taskId}/upd-status")
    fun updateTaskStatus(@PathVariable taskId: Long): ResponseEntity<*> {
        return taskService.updateTaskStatus(taskId)
    }
}
