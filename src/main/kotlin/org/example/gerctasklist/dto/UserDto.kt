package org.example.gerctasklist.dto

import org.example.gerctasklist.dto.enums.Role


data class UserDto(
    val id: Long? = null, ///Todo delete id from dto
    val name: String,
    var roles: MutableList<Role>,
    var tasks: MutableList<TaskDto>?,
    ){

}
