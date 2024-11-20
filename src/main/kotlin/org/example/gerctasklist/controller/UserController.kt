package org.example.gerctasklist.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.example.gerctasklist.dto.UserDto
import org.example.gerctasklist.service.UserService
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {

    @Operation(summary = "Получить всех пользователей", responses = [
        ApiResponse(description = "Список всех пользователей", responseCode = "200")
    ])
    @GetMapping
    fun getAllUsers(): MutableList<UserDto> {
        return userService.getAll()
    }

    @Operation(summary = "Получить пользователя по ID", responses = [
        ApiResponse(description = "Информация о пользователе", responseCode = "200"),
        ApiResponse(description = "Пользователь не найден", responseCode = "404")
    ])
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.getUser(id))
    }

    @Operation(summary = "Добавить нового пользователя", responses = [
        ApiResponse(description = "Пользователь успешно добавлен", responseCode = "200"),
        ApiResponse(description = "Ошибка добавления пользователя", responseCode = "403")
    ])
    @PostMapping
    fun addUser(@RequestBody userDto: UserDto): ResponseEntity<HttpStatusCode> {
        return if (userService.addUser(userDto)) ResponseEntity.ok(HttpStatusCode.valueOf(200))
        else ResponseEntity(HttpStatusCode.valueOf(403))
    }

    @Operation(summary = "Удалить пользователя", responses = [
        ApiResponse(description = "Пользователь успешно удален", responseCode = "200"),
        ApiResponse(description = "Ошибка удаления пользователя", responseCode = "403")
    ])
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<HttpStatusCode> {
        return if (userService.deleteUser(id)) ResponseEntity.ok(HttpStatusCode.valueOf(200))
        else ResponseEntity(HttpStatusCode.valueOf(403))
    }

    @Operation(summary = "Обновить данные пользователя", responses = [
        ApiResponse(description = "Пользователь успешно обновлен", responseCode = "200"),
        ApiResponse(description = "Ошибка обновления пользователя", responseCode = "403")
    ])
    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody userDto: UserDto): ResponseEntity<HttpStatusCode> {
        return if (userService.updateUser(id, userDto)) ResponseEntity.ok(HttpStatusCode.valueOf(200))
        else ResponseEntity(HttpStatusCode.valueOf(403))
    }
}
