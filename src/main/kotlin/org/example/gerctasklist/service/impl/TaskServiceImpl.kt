package org.example.gerctasklist.service.impl

import jakarta.transaction.Transactional
import org.example.gerctasklist.dto.TaskDto
import org.example.gerctasklist.dto.UserDto
import org.example.gerctasklist.dto.enums.TaskPriority
import org.example.gerctasklist.dto.enums.TaskStatus
import org.example.gerctasklist.entities.TaskEntity
import org.example.gerctasklist.repositories.TaskRepo
import org.example.gerctasklist.repositories.UserRepo
import org.example.gerctasklist.service.TaskService
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull

@Service
class TaskServiceImpl(val taskRepo: TaskRepo, val userRepo: UserRepo) : TaskService {

    override fun getAllTask(userId: Long): MutableList<TaskDto> {
        return taskRepo.findByUserId(userId).map { taskEntity -> TaskDto(
            taskEntity.id!!,
            taskEntity.title,
            taskEntity.description,
            taskEntity.priority,
            taskEntity.status,
            //taskEntity.user?.let { UserDto(it.id, it.name, it.roles , it.tasks)}!!

        ) }.toMutableList()
    }

    override fun getFilterTask(userId: Long, taskStatus: TaskStatus): MutableList<TaskDto> {
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
                    dueDate = LocalDate.now(),
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
    override fun deleteTask(userId: Long, taskId: Long): Boolean {
       try {
           taskRepo.deleteByUserIdAndId(userId, taskId)
           return true
       } catch (e: Exception){
           println(e.message)
           return false
       }
    }

    @Transactional
    override fun updateTaskStatus(userId: Long, taskId: Long, taskStatus: TaskStatus): Boolean {
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
}