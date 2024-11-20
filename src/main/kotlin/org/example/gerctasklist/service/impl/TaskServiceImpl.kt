package org.example.gerctasklist.service.impl

import jakarta.transaction.Transactional
import org.example.gerctasklist.dto.TaskDto
import org.example.gerctasklist.dto.enums.TaskPriority
import org.example.gerctasklist.dto.enums.TaskStatus
import org.example.gerctasklist.entities.TaskEntity
import org.example.gerctasklist.repositories.TaskRepo
import org.example.gerctasklist.repositories.UserRepo
import org.example.gerctasklist.service.TaskService
import org.example.gerctasklist.utill.CsvUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.jvm.optionals.getOrElse

@Service
class TaskServiceImpl(val taskRepo: TaskRepo, val userRepo: UserRepo) : TaskService {

    @Value("\${stat.filepath}")
    lateinit var filePath: String

    override fun getAllTask(userId: Long): MutableList<TaskDto> {
        return taskRepo.findByUserId(userId).map { taskEntity -> TaskDto(
            taskEntity.id!!,
            taskEntity.title,
            taskEntity.description,
            taskEntity.priority,
            taskEntity.status,

        ) }.toMutableList()
    }

    override fun getFilteredTask(userId: Long, taskStatus: TaskStatus): MutableList<TaskDto> {
        return taskRepo.findByUserIdAndStatus(userId, taskStatus).map { taskEntity -> TaskDto(
            taskEntity.id!!,
            taskEntity.title,
            taskEntity.description,
            taskEntity.priority,
            taskEntity.status,
        ) }.toMutableList()
    }

    override fun addTask(userId: Long, taskDto: TaskDto): Boolean {
        val existUser = userRepo.findById(userId).getOrElse { return false }

        try{
            taskRepo.save(
                TaskEntity(
                    title = taskDto.title,
                    description = taskDto.description,
                    dueDate = LocalDate.now(), //Todo make variable usefully
                    priority = taskDto.priority?: TaskPriority.MEDIUM,
                    status = taskDto.status?: TaskStatus.UNCOMPLETED,
                    user = existUser
                )
            )

            return true
        }catch (e: Exception){
            println(e.message)
            return false
        }

    }

    @Transactional
    override fun updateTask(userId: Long, taskId: Long, taskDto: TaskDto): Boolean {
        val existTask = taskRepo.findByUserIdAndId(userId, taskId)

        try{
            taskRepo.save(existTask.apply {
                title = taskDto.title
                description = taskDto.description ?: ""
                status = taskDto.status ?: TaskStatus.UNCOMPLETED
                priority = taskDto.priority ?: TaskPriority.MEDIUM
            })

            return true
        }catch (e: Exception){
            println(e.message)
            return false
        }
    }

    @Transactional
    override fun deleteTask(userId: Long, taskId: Long): Boolean {  ///Todo optimise return
       try {
           taskRepo.deleteByUserIdAndId(userId, taskId)
           return true
       } catch (e: Exception){
           println(e.message)
           return false
       }
    }

    @Transactional
    override fun updateTaskStatus(userId: Long, taskId: Long, taskStatus: TaskStatus): Boolean { ///Todo optimise return
        try{
            val existTask = taskRepo.findByUserIdAndId(userId, taskId)
            existTask.status = taskStatus
            taskRepo.save(existTask)
            return true

        }catch (e: Exception){
            println(e.message)
            return false
        }
    }

    override fun getTaskStatistics(): Map<String, Any> {
        val totalTasks = taskRepo.count()
        val completedTasks = taskRepo.countByStatus(TaskStatus.COMPLETED)
        val uncompletedTasks = totalTasks - completedTasks

        val userStats = userRepo.findAll().associate { user ->
            user.username to (user.tasks?.size ?: 0)
        }

        return mapOf(
            "totalTasks" to totalTasks,
            "completedTasks" to completedTasks,
            "uncompletedTasks" to uncompletedTasks,
            "userStats" to userStats
        )
    }

    override fun exportStatisticsToCsv(): Boolean {
        return try {
            val taskStats = getTaskStatistics()
            val headers = arrayOf("Metric", "Value")
            val data = listOf(
                arrayOf("Total Tasks", taskStats["totalTasks"].toString()),
                arrayOf("Completed Tasks", taskStats["completedTasks"].toString()),
                arrayOf("Uncompleted Tasks", taskStats["uncompletedTasks"].toString())
            )

            CsvUtil.writeCsv(filePath, headers, data)

            // User stats
            val userHeaders = arrayOf("User", "Task Count")
            val userData = (taskStats["userStats"] as Map<String, Int>).map {
                arrayOf(it.key, it.value.toString())
            }


            CsvUtil.writeCsv(filePath.replace(".csv", "_users.csv"), userHeaders, userData)

            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

}