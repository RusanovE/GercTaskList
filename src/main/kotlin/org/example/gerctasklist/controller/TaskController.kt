package org.example.gerctasklist.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.example.gerctasklist.dto.TaskDto
import org.example.gerctasklist.dto.enums.TaskStatus
import org.example.gerctasklist.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users/{userId}/tasks")
class TaskController(val taskService: TaskService) {

    @Operation(summary = "Получить все задачи пользователя", responses = [
        ApiResponse(description = "Список задач", responseCode = "200"),
        ApiResponse(description = "Задачи не найдены", responseCode = "204")
    ])
    @GetMapping
    fun getAllTasks(@PathVariable userId: Long): ResponseEntity<List<TaskDto>> {
        val tasks = taskService.getAllTask(userId)
        return if (tasks.isNotEmpty()) {
            ResponseEntity.ok(tasks)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @Operation(summary = "Получить отфильтрованные задачи по статусу", responses = [
        ApiResponse(description = "Список отфильтрованных задач", responseCode = "200"),
        ApiResponse(description = "Задачи не найдены", responseCode = "204")
    ])
    @GetMapping("/filter")
    fun getFilteredTasks(
        @PathVariable userId: Long,
        @RequestParam status: TaskStatus
    ): ResponseEntity<List<TaskDto>> {
        val tasks = taskService.getFilteredTask(userId, status)
        return if (tasks.isNotEmpty()) {
            ResponseEntity.ok(tasks)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @Operation(summary = "Добавить задачу", responses = [
        ApiResponse(description = "Задача успешно добавлена", responseCode = "201"),
        ApiResponse(description = "Ошибка добавления задачи", responseCode = "400")
    ])
    @PostMapping
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

    @Operation(summary = "Обновить задачу", responses = [
        ApiResponse(description = "Задача успешно обновлена", responseCode = "200"),
        ApiResponse(description = "Ошибка обновления задачи", responseCode = "404")
    ])
    @PutMapping("/{taskId}")
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

    @Operation(summary = "Удалить задачу", responses = [
        ApiResponse(description = "Задача успешно удалена", responseCode = "200"),
        ApiResponse(description = "Ошибка удаления задачи", responseCode = "404")
    ])
    @DeleteMapping("/{taskId}")
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

    @Operation(summary = "Обновить статус задачи", responses = [
        ApiResponse(description = "Статус задачи успешно обновлен", responseCode = "200"),
        ApiResponse(description = "Ошибка обновления статуса задачи", responseCode = "404")
    ])
    @PatchMapping("/{taskId}/status")
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
