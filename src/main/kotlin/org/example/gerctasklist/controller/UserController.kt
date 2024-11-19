package org.example.gerctasklist.controller

import org.example.gerctasklist.dto.UserDto
import org.example.gerctasklist.service.UserService
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {

    @GetMapping
    fun getAllUsers(): MutableList<UserDto> {
        return userService.getAll()
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<UserDto>{
        return ResponseEntity.ok(userService.getUser(id))
    }

    @PostMapping
    fun addUser(@RequestBody userDto: UserDto): ResponseEntity<HttpStatusCode>{
        return if (userService.addUser(userDto)) ResponseEntity.ok(HttpStatusCode.valueOf(200))
        else ResponseEntity(HttpStatusCode.valueOf(403))
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<HttpStatusCode>{
        return if (userService.deleteUser(id)) ResponseEntity.ok(HttpStatusCode.valueOf(200))
        else ResponseEntity(HttpStatusCode.valueOf(403))
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody userDto: UserDto): ResponseEntity<HttpStatusCode>{
        return if (userService.updateUser(id, userDto)) ResponseEntity.ok(HttpStatusCode.valueOf(200))
        else ResponseEntity(HttpStatusCode.valueOf(403))
    }

}