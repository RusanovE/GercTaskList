package org.example.gerctasklist.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.example.gerctasklist.dto.enums.Role

/**
 * Data Transfer Object (DTO) representing a user.
 * This DTO is used for transferring user data between different layers of the application.
 */
@Schema(description = "User data transfer object")
data class UserDto(

    /**
     * The unique identifier of the user.
     * This field is read-only and cannot be null.
     */
    @Schema(description = "User ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    val id: Long? = 0,

    /**
     * The username of the user.
     * This field is required and cannot be blank.
     * The username must be between 3 and 50 characters long.
     */
    @Schema(description = "Username of the user", example = "john_doe")
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    val username: String,

    /**
     * A list of roles assigned to the user.
     * This field is required and cannot be null.
     */
    @Schema(description = "Roles assigned to the user")
    @NotNull(message = "Roles cannot be null")
    var roles: MutableList<Role>? = null,

    /**
     * A list of tasks assigned to the user.
     * This field is optional and may be left empty (null).
     */
    @Schema(description = "List of tasks assigned to the user", nullable = true)
    var tasks: MutableList<TaskDto>? = null
)
