package org.example.gerctasklist.service.impl

import org.example.gerctasklist.dto.ErrorDTO
import org.example.gerctasklist.dto.enums.TaskStatus
import org.example.gerctasklist.repositories.TaskRepo
import org.example.gerctasklist.repositories.UserRepo
import org.example.gerctasklist.service.StatService
import org.example.gerctasklist.utill.CsvUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.io.File
import java.util.*

@Service
class StatServiceImpl(val taskRepo: TaskRepo, val userRepo: UserRepo): StatService {

    @Value("\${stat.filepath}")
    lateinit var filePath: String

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

    override fun exportStatisticsToCsv(): ResponseEntity<*> {
        return try {
            val taskStats = getTaskStatistics()

            val headers = arrayOf("Metric/User", "Value/Task Count")
            val generalData = listOf(
                arrayOf("Total Tasks", taskStats["totalTasks"].toString()),
                arrayOf("Completed Tasks", taskStats["completedTasks"].toString()),
                arrayOf("Uncompleted Tasks", taskStats["uncompletedTasks"].toString())
            )
            val userData = (taskStats["userStats"] as Map<String, Int>).map { arrayOf(it.key, it.value.toString()) }

            val reportFilePath = filePath.replace(".csv", "_report.csv")
            CsvUtil.writeCsv(reportFilePath, headers, generalData + userData)

            val file = File(reportFilePath)
            if (!file.exists()) return ResponseEntity.status(404).body("File not found")

            val resource = file.inputStream().use { it.readBytes() }
            ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=${file.name}")
                .body(resource)

        } catch (e: Exception) {
            ResponseEntity.status(500).body(ErrorDTO(500, "Failed to export statistics", Date()))
        }
    }
}