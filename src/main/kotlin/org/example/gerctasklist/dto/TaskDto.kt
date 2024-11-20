package org.example.gerctasklist.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.example.gerctasklist.dto.enums.TaskPriority
import org.example.gerctasklist.dto.enums.TaskStatus

@Schema(description = "Task data transfer object")
data class TaskDto(
    @Schema(description = "Task ID", example = "101", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull(message = "Id cannot be null")
    val id: Long? = 0, ///Todo delete id from dto

    @Schema(description = "Title of the task", example = "Complete project documentation")
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    val title: String,

    @Schema(description = "Description of the task", example = "Write detailed project documentation", nullable = true)
    @Size(max = 500, message = "Description must not exceed 500 characters")
    var description: String? = null,

    @Schema(description = "Priority of the task", example = "HIGH")
    var priority: TaskPriority? = null,

    @Schema(description = "Status of the task", example = "COMPLETED")
    var status: TaskStatus? = null
)
