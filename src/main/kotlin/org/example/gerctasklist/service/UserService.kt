package org.example.gerctasklist.service

import org.example.gerctasklist.dto.SignRequestDto
import org.example.gerctasklist.dto.UserDto
import org.example.gerctasklist.entities.UserEntity
import org.springframework.http.ResponseEntity

interface  UserService{

    fun getAll(): MutableList<UserDto>

    fun getUser(username: String): UserEntity

    fun getCurrentUserId(): Long

    fun addUser(signRequestDto: SignRequestDto): ResponseEntity<*>

    fun deleteUser(id: Long): ResponseEntity<*>

    fun updateUser(id: Long, userDto: UserDto): ResponseEntity<*>
}
