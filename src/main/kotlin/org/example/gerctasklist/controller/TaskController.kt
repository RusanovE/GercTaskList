package org.example.gerctasklist.controller

import org.example.gerctasklist.dto.TaskDto
import org.example.gerctasklist.dto.enums.TaskStatus
import org.example.gerctasklist.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks")
class TaskController(val taskService: TaskService) {

    @GetMapping("/{userId}")
    fun getAllTasks(@PathVariable userId: Long): ResponseEntity<List<TaskDto>> {
        val tasks = taskService.getAllTask(userId)
        return if (tasks.isNotEmpty()) {
            ResponseEntity.ok(tasks)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/{userId}/filter")
    fun getFilteredTasks(
        @PathVariable userId: Long,
        @RequestParam status: TaskStatus
    ): ResponseEntity<List<TaskDto>> {
        val tasks = taskService.getFilterTask(userId, status)
        return if (tasks.isNotEmpty()) {
            ResponseEntity.ok(tasks)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @PostMapping("/{userId}")
    fun addTask(
        @PathVariable userId: Long,
        @RequestBody taskDto: TaskDto
    ): ResponseEntity<String> {
        val isAdded = taskService.addTask(userId, taskDto)
        return if (isAdded) {
            ResponseEntity.status(HttpStatus.CREATED).body("Task added successfully")
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add task")
        }
    }

    @PutMapping("/{userId}/{taskId}")
    fun updateTask(
        @PathVariable userId: Long,
        @PathVariable taskId: Long,
        @RequestBody taskDto: TaskDto
    ): ResponseEntity<String> {
        val isUpdated = taskService.updateTask(userId, taskId, taskDto)
        return if (isUpdated) {
            ResponseEntity.ok("Task updated successfully")
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found or update failed")
        }
    }

    @DeleteMapping("/{userId}/{taskId}")
    fun deleteTask(
        @PathVariable userId: Long,
        @PathVariable taskId: Long
    ): ResponseEntity<String> {
        val isDeleted = taskService.deleteTask(userId, taskId)
        return if (isDeleted) {
            ResponseEntity.ok("Task deleted successfully")
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found or delete failed")
        }
    }

    @PatchMapping("/{userId}/{taskId}/status")
    fun updateTaskStatus(
        @PathVariable userId: Long,
        @PathVariable taskId: Long,
        @RequestParam status: TaskStatus
    ): ResponseEntity<String> {
        val isStatusUpdated = taskService.updateTaskStatus(userId, taskId, status)
        return if (isStatusUpdated) {
            ResponseEntity.ok("Task status updated successfully")
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found or update failed")
        }
    }
}
