package org.example.gerctasklist.utill

import org.example.gerctasklist.dto.SignRequestDto
import org.example.gerctasklist.dto.TaskDto
import org.example.gerctasklist.dto.UserDto
import org.example.gerctasklist.dto.enums.Role
import org.example.gerctasklist.dto.enums.TaskPriority
import org.example.gerctasklist.dto.enums.TaskStatus
import org.example.gerctasklist.entities.TaskEntity
import org.example.gerctasklist.entities.UserEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class ObjMapper(val passwordEncoder: PasswordEncoder) {
    fun toDto(entity: UserEntity): UserDto = UserDto(
        id = entity.id,
        username = entity.username,
        roles = entity.roles.toMutableList(),
        tasks = entity.tasks?.map { toTaskDto(it) }?.toMutableList()
    )

    fun toEntity(dto: SignRequestDto): UserEntity = UserEntity(
        username = dto.username?: "NoName User",
        password = passwordEncoder.encode(dto.password ?: "null"),
        roles =  mutableListOf(Role.ROLE_USER),
        tasks = mutableListOf()
    )

    fun toTaskDto(entity: TaskEntity): TaskDto = TaskDto(
        id = entity.id,
        title = entity.title,
        description = entity.description,
        deadLine = entity.deadLine,
        priority = entity.priority,
        status = entity.status
    )

    fun toTaskEntity(dto: TaskDto, userEntity: UserEntity): TaskEntity = TaskEntity(
        title = dto.title,
        description = dto.description,
        deadLine = dto.deadLine,
        priority = dto.priority ?: TaskPriority.MEDIUM, // Значение по умолчанию
        status = dto.status ?: TaskStatus.UNCOMPLETED, // Значение по умолчанию
        user = userEntity
    )
}
