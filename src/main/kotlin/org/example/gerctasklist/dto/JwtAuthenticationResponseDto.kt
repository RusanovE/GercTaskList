package org.example.gerctasklist.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Ответ c токеном доступа")
data class JwtAuthenticationResponseDto(
    @Schema(description = "Токен доступа", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj...")
    val token: String? = null)
