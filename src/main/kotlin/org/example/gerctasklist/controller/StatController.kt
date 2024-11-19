package org.example.gerctasklist.controller

import org.example.gerctasklist.service.TaskService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users/stat")
class StatController(val taskService: TaskService) {

    @GetMapping
    fun getTaskStatistics(): Map<String, Any> {
        return taskService.getTaskStatistics()
    }

    @GetMapping("/export")
    fun exportStatistics(): String {

        return if (taskService.exportStatisticsToCsv()) {
            "Statistics exported successfully"
        } else {
            "Failed to export statistics"
        }
    }

}