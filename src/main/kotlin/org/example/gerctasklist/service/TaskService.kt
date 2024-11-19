package org.example.gerctasklist.service

import org.example.gerctasklist.dto.TaskDto
import org.example.gerctasklist.dto.enums.TaskStatus

interface TaskService {

    fun getAllTask(userId: Long): MutableList<TaskDto>

    fun getFilteredTask(userId: Long, taskStatus: TaskStatus): MutableList<TaskDto>

    fun addTask(userId: Long, taskDto: TaskDto): Boolean

    fun updateTask(userId: Long, taskId: Long, taskDto: TaskDto ): Boolean

    fun deleteTask(userId: Long, taskId: Long): Boolean

    fun updateTaskStatus(userId: Long, taskId: Long, taskStatus: TaskStatus): Boolean
    fun getTaskStatistics(): Map<String, Any>
    fun exportStatisticsToCsv(): Boolean
}