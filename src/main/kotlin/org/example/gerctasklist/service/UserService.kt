package org.example.gerctasklist.service

import org.example.gerctasklist.dto.UserDto

interface  UserService{

    fun getAll(): MutableList<UserDto>

    fun getUser(id: Long): UserDto

    fun addUser(userDto: UserDto): Boolean

    fun deleteUser(id: Long): Boolean

    fun updateUser(id: Long, userDto: UserDto): Boolean
}
