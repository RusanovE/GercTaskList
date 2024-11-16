package org.example.gerctasklist.dto

import org.example.gerctasklist.dto.enums.Role
import org.example.gerctasklist.entities.TaskEntity


data class UserDto(
    val id: Long? = null,
    val name: String,
    var roles: MutableList<Role>,
    var tasks: MutableList<TaskDto>?,
    ){

}
