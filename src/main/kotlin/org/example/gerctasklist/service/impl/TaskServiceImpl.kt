package org.example.gerctasklist.service.impl

import jakarta.transaction.Transactional
import org.example.gerctasklist.dto.ErrorDTO
import org.example.gerctasklist.dto.TaskDto
import org.example.gerctasklist.dto.enums.TaskPriority
import org.example.gerctasklist.dto.enums.TaskStatus
import org.example.gerctasklist.repositories.TaskRepo
import org.example.gerctasklist.repositories.UserRepo
import org.example.gerctasklist.service.TaskService
import org.example.gerctasklist.service.UserService
import org.example.gerctasklist.utill.ObjMapper
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class TaskServiceImpl(val taskRepo: TaskRepo, val userRepo: UserRepo, val userService: UserService, val mapper: ObjMapper) : TaskService {

    override fun getAllUserTask(): MutableList<TaskDto> {
        val userId = userService.getCurrentUserId()

        return taskRepo.findByUserId(userId)
            .map { taskEntity -> mapper.toTaskDto(taskEntity) }
            .toMutableList()
    }

    override fun getFilteredTask(taskStatus: TaskStatus): MutableList<TaskDto> {
        val userId = userService.getCurrentUserId()

        return taskRepo.findByUserIdAndStatus(userId, taskStatus)
            .map { taskEntity -> mapper.toTaskDto(taskEntity) }
            .toMutableList()
    }

    @Transactional
    override fun addTask(taskDto: TaskDto): ResponseEntity<*> {
        val userId = userService.getCurrentUserId()
        val existUser = userRepo.findById(userId).orElse(null)
            ?: return ResponseEntity.status(404).body(ErrorDTO(404, "User not found", Date()))

        return try {
            val taskEntity = mapper.toTaskEntity(taskDto, existUser)
            taskRepo.save(taskEntity)
            ResponseEntity.ok("Task added successfully")
        } catch (e: Exception) {
            ResponseEntity.status(500).body(ErrorDTO(500, "Failed to add task", Date()))
        }
    }

    @Transactional
    override fun updateTask(taskId: Long, taskDto: TaskDto, userId: Long?): ResponseEntity<*> {
        val resolvedUserId = userId ?: userService.getCurrentUserId()
        val existTask = taskRepo.findByUserIdAndId(resolvedUserId, taskId)
            ?: return ResponseEntity.status(404).body(ErrorDTO(404, "Task not found", Date()))

        return try {
            taskRepo.save(existTask.apply {
                title = taskDto.title
                description = taskDto.description ?: ""
                priority = taskDto.priority ?: TaskPriority.MEDIUM
            })
            ResponseEntity.ok("Task updated successfully")
        } catch (e: Exception) {
            ResponseEntity.status(500).body(ErrorDTO(500, "Failed to update task", Date()))
        }
    }

    @Transactional
    override fun deleteTask(taskId: Long, userId: Long?): ResponseEntity<*> {
        val resolvedUserId = userId ?: userService.getCurrentUserId()
        return try {
            val rowsDeleted = taskRepo.deleteByUserIdAndId(resolvedUserId, taskId)
            if (rowsDeleted == 0) {
                ResponseEntity.status(404).body(ErrorDTO(404, "Task not found", Date()))
            } else {
                ResponseEntity.ok("Task deleted successfully")
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).body(ErrorDTO(500, "Failed to delete task", Date()))
        }
    }

    @Transactional
    override fun updateTaskStatus(taskId: Long): ResponseEntity<*> {
        val userId =  userService.getCurrentUserId()
        val existTask = taskRepo.findByUserIdAndId(userId, taskId)
            ?: return ResponseEntity.status(404).body(ErrorDTO(404, "Task not found", Date()))

        return try {
            existTask.status = TaskStatus.COMPLETED
            taskRepo.save(existTask)
            ResponseEntity.ok("Task status updated successfully")
        } catch (e: Exception) {
            ResponseEntity.status(500).body(ErrorDTO(500, "Failed to update task status", Date()))
        }
    }

}