package org.example.gerctasklist.dto

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Data Transfer Object (DTO) representing the response containing an access token.
 */
@Schema(description = "Response containing the JWT access token.")
data class JwtAuthenticationResponseDto(

    /**
     * The JWT (JSON Web Token) access token issued to the client upon successful authentication.
     */
    @Schema(
        description = "The JWT access token that the client uses to authenticate subsequent API requests.",
        example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj...",
        type = "string"
    )
    val token: String? = null
)
