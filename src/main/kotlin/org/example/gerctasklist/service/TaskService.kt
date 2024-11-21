package org.example.gerctasklist.service

import org.example.gerctasklist.dto.TaskDto
import org.example.gerctasklist.dto.enums.TaskStatus
import org.springframework.http.ResponseEntity

interface TaskService {

    fun getAllUserTask(): MutableList<TaskDto>

    fun getFilteredTask( taskStatus: TaskStatus): MutableList<TaskDto>

    fun addTask( taskDto: TaskDto): ResponseEntity<*>

    fun updateTask(taskId: Long, taskDto: TaskDto, userId: Long? = null, ): ResponseEntity<*>

    fun deleteTask(taskId: Long, userId: Long? = null): ResponseEntity<*>

    fun updateTaskStatus( taskId: Long): ResponseEntity<*>

}