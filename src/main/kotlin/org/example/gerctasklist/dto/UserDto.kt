package org.example.gerctasklist.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.example.gerctasklist.dto.enums.Role


@Schema(description = "User data transfer object")
data class UserDto(
    @Schema(description = "User ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    val id: Long? = 0, ///Todo delete id from dto

    @Schema(description = "Username of the user", example = "john_doe")
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    val username: String,

    @Schema(description = "User password", example = "P@ssw0rd!")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    val password: String?,

    @Schema(description = "Roles assigned to the user")
    @NotNull(message = "Roles cannot be null")
    var roles: MutableList<Role>? = null,

    @Schema(description = "List of tasks assigned to the user", nullable = true)
    var tasks: MutableList<TaskDto>? = null
)
