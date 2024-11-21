package org.example.gerctasklist.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.example.gerctasklist.dto.TaskDto
import org.example.gerctasklist.dto.UserDto
import org.example.gerctasklist.service.TaskService
import org.example.gerctasklist.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class UserController(val userService: UserService, val taskService: TaskService) {

    /**
     * Endpoint to get all users and their tasks.
     */
    @Operation(
        summary = "Retrieve all users and their tasks",
        description = "Returns a list of all users along with their associated tasks.",
        responses = [
            ApiResponse(description = "List of all users and their tasks", responseCode = "200")
        ]
    )
    @GetMapping
    fun getAllUsers(): MutableList<UserDto> {
        return userService.getAll()
    }

    /**
     * Endpoint to delete a user by ID.
     */
    @Operation(
        summary = "Delete a user",
        description = "Deletes a user by their ID. All associated data will also be removed.",
        responses = [
            ApiResponse(description = "User successfully deleted", responseCode = "200"),
            ApiResponse(description = "Failed to delete user", responseCode = "403")
        ]
    )
    @DeleteMapping("/del-user/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<*> {
        return userService.deleteUser(id)
    }

    /**
     * Endpoint to update user information.
     */
    @Operation(
        summary = "Update user details",
        description = "Updates the details of a specific user by their ID.",
        responses = [
            ApiResponse(description = "User successfully updated", responseCode = "200"),
            ApiResponse(description = "Failed to update user", responseCode = "403")
        ]
    )
    @PutMapping("/upd-user/{userId}")
    fun updateUser(@PathVariable userId: Long, @RequestBody userDto: UserDto): ResponseEntity<*> {
        return userService.updateUser(userId, userDto)
    }

    /**
     * Endpoint to delete a task for a specific user.
     */
    @Operation(
        summary = "Delete a task",
        description = "Deletes a task associated with a specific user by the task ID and user ID.",
        responses = [
            ApiResponse(description = "Task successfully deleted", responseCode = "200"),
            ApiResponse(description = "Failed to delete task", responseCode = "403")
        ]
    )
    @DeleteMapping("/del-task/{userId}/{taskId}")
    fun deleteTasks(@PathVariable userId: Long, @PathVariable taskId: Long): ResponseEntity<*> {
        return taskService.deleteTask(taskId, userId)
    }

    /**
     * Endpoint to update a task for a specific user.
     */
    @Operation(
        summary = "Update task details",
        description = "Updates the details of a specific task for a specific user by task ID and user ID.",
        responses = [
            ApiResponse(description = "Task successfully updated", responseCode = "200"),
            ApiResponse(description = "Failed to update task", responseCode = "403")
        ]
    )
    @PutMapping("/upd-task/{userId}/{taskId}")
    fun updateTasks(
        @PathVariable taskId: Long,
        @PathVariable userId: Long,
        @RequestBody taskDto: TaskDto
    ): ResponseEntity<*> {
        return taskService.updateTask(taskId, taskDto, userId)
    }
}
