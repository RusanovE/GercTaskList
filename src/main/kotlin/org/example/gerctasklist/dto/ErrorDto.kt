package org.example.gerctasklist.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

/**
 * Data Transfer Object (DTO) representing an error response.
 */
@Schema(description = "Error data transfer object, used to standardize error responses in the API.")
data class ErrorDTO(

    /**
     * HTTP status code representing the error.
     * Example: 404 for "Not Found", 500 for "Internal Server Error".
     */
    @Schema(
        description = "The HTTP status code associated with the error.",
        example = "404",
        type = "integer"
    )
    val status: Int,

    /**
     * Human-readable error message providing details about the issue.
     * Example: "Resource not found" or "Bad credentials".
     */
    @Schema(
        description = "A detailed message explaining the error.",
        example = "Bad credentials"
    )
    val message: String,

    /**
     * The timestamp when the error occurred.
     * Example: "2024-11-21T10:15:30Z".
     */
    @Schema(
        description = "The timestamp indicating when the error occurred.",
        example = "2024-11-21T10:15:30Z",
        type = "string",
        format = "date-time"
    )
    val timestamp: Date
)
