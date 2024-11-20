package org.example.gerctasklist.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


@Schema(description = "Запрос на аутентификацию")
data class SignInRequestDto(
    @Schema(description = "Имя пользователя", example = "Jon")
    @Size(min = 3, max = 50, message = "Имя пользователя должно содержать от 3 до 50 символов")
    val username: @NotBlank(message = "Имя пользователя не может быть пустыми") String? = null,

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(min = 5, max = 255, message = "Длина пароля должна быть от  5 до 255 символов")
    val password: @NotBlank(message = "Пароль не может быть пустыми") String? = null
)