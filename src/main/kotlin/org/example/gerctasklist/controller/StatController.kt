package org.example.gerctasklist.controller

import org.example.gerctasklist.service.TaskService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse

@RestController
@RequestMapping("/users/stat")
class StatController(val taskService: TaskService) {

    @Operation(summary = "Получить статистику по задачам", responses = [
        ApiResponse(description = "Статистика задач", responseCode = "200")
    ])
    @GetMapping
    fun getTaskStatistics(): Map<String, Any> {
        return taskService.getTaskStatistics()
    }

    @Operation(summary = "Экспортировать статистику в CSV", responses = [
        ApiResponse(description = "Экспорт успешен", responseCode = "200"),
        ApiResponse(description = "Ошибка экспорта", responseCode = "500")
    ])
    @GetMapping("/export")
    fun exportStatistics(): String {
        return if (taskService.exportStatisticsToCsv()) {
            "Statistics exported successfully"
        } else {
            "Failed to export statistics"
        }
    }
}
