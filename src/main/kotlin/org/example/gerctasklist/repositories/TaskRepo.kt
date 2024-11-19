package org.example.gerctasklist.repositories

import org.example.gerctasklist.dto.enums.TaskStatus
import org.example.gerctasklist.entities.TaskEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepo: JpaRepository<TaskEntity,Long> {

    fun findByUserId(userId: Long): MutableList<TaskEntity>

    fun findByUserIdAndStatus(userId: Long, taskStatus: TaskStatus): MutableList<TaskEntity>

    fun findByUserIdAndId(userId: Long, taskId: Long): TaskEntity

    fun deleteByUserIdAndId(userId: Long, taskId: Long)

    fun countByStatus(status: TaskStatus): Int
}