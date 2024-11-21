package org.example.gerctasklist.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.example.gerctasklist.service.StatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/stat")
class StatController(val statService: StatService) {

    /**
     * Endpoint to retrieve task statistics.
     */
    @Operation(
        summary = "Retrieve task statistics",
        description = "Returns statistical data about tasks, such as the total number of tasks, their statuses, and other relevant information.",
        responses = [
            ApiResponse(
                description = "Task statistics retrieved successfully",
                responseCode = "200"
            )
        ]
    )
    @GetMapping
    fun getTaskStatistics(): Map<String, Any> {
        return statService.getTaskStatistics()
    }

    /**
     * Endpoint to export task statistics as a CSV file.
     */
    @Operation(
        summary = "Export task statistics to CSV",
        description = "Generates and returns a CSV file containing the task statistics.",
        responses = [
            ApiResponse(
                description = "Statistics exported successfully",
                responseCode = "200"
            ),
            ApiResponse(
                description = "Failed to export statistics",
                responseCode = "500"
            )
        ]
    )
    @GetMapping("/export")
    fun exportStatistics(): ResponseEntity<*> {
        return statService.exportStatisticsToCsv()
    }
}
