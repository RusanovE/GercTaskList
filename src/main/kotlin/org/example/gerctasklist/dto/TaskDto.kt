package org.example.gerctasklist.dto

import org.example.gerctasklist.dto.enums.TaskPriority
import org.example.gerctasklist.dto.enums.TaskStatus

data class TaskDto(
    val id: Long,
    val title: String,
    var description: String?,
    var priority: TaskPriority?,
    var status: TaskStatus?,
    //val user: UserDto,
) {

}
