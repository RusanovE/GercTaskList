package org.example.gerctasklist.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.example.gerctasklist.dto.enums.TaskPriority
import org.example.gerctasklist.dto.enums.TaskStatus

/**
 * Data Transfer Object (DTO) representing a task.
 * This DTO is used for transferring task data between different layers of the application.
 */
@Schema(description = "Task data transfer object")
data class TaskDto(

    /**
     * The unique identifier of the task.
     * This field is read-only and cannot be null.
     */
    @Schema(description = "Task ID", accessMode = Schema.AccessMode.READ_ONLY, nullable = true)
    val id: Long? = 0,

    /**
     * The title of the task.
     * This field is required and cannot be empty.
     * The title must be between 3 and 100 characters long.
     */
    @Schema(description = "Title of the task", example = "Complete project documentation", nullable = false)
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    val title: String,

    /**
     * The description of the task.
     * This field is optional and may be left empty.
     * The description must not exceed 500 characters.
     */
    @Schema(description = "Description of the task", example = "Write detailed project documentation", nullable = true)
    @Size(max = 500, message = "Description must not exceed 500 characters")
    var description: String? = "null",

    /**
     * The deadline for the task.
     * This field is optional and has a default value of "No time limit" if not provided.
     */
    @Schema(description = "Deadline of the task", example = "2024-12-31")
    val deadLine: String? = "No time limit",

    /**
     * The priority of the task.
     * This field is optional and defaults to "MEDIUM".
     */
    @Schema(description = "Priority of the task")
    var priority: TaskPriority? = TaskPriority.MEDIUM,

    /**
     * The status of the task.
     * This field is optional and defaults to "UNCOMPLETED".
     */
    @Schema(description = "Status of the task")
    var status: TaskStatus? = TaskStatus.UNCOMPLETED
)
