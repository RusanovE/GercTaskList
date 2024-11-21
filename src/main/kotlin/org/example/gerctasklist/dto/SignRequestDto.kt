package org.example.gerctasklist.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Data Transfer Object (DTO) representing a request for authentication.
 * This DTO is used for the login process where the user provides their credentials.
 */
@Schema(description = "Authentication request containing username and password.")
data class SignRequestDto(

    /**
     * The username provided by the user for authentication.
     * This field is required and cannot be empty.
     */
    @Schema(
        description = "The username of the user attempting to authenticate.",
        example = "Jon"
    )
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters long")
    val username: String? = null,

    /**
     * The password provided by the user for authentication.
     * This field is required and cannot be empty.
     */
    @Schema(
        description = "The password of the user attempting to authenticate.",
        example = "P@ssw0rd!"
    )
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    val password: String? = null
)
