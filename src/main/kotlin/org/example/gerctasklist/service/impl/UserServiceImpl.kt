package org.example.gerctasklist.service.impl

import jakarta.transaction.Transactional
import org.example.gerctasklist.dto.TaskDto
import org.example.gerctasklist.dto.UserDto
import org.example.gerctasklist.dto.enums.Role
import org.example.gerctasklist.entities.UserEntity
import org.example.gerctasklist.repositories.UserRepo
import org.example.gerctasklist.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserServiceImpl(val userRepo: UserRepo, val passwordEncoder: PasswordEncoder) : UserService {

    override fun getAll(): MutableList<UserDto> {
        return userRepo.findAll().map { userEntity -> UserDto(
            userEntity.id!!,
            userEntity.username,
            userEntity.password,
            userEntity.roles,
            userEntity.tasks?.map { taskEntity -> TaskDto(
                taskEntity.id!!,
                taskEntity.title,
                taskEntity.description,
                taskEntity.priority,
                taskEntity.status
            ) }?.toMutableList(),

        )  }.toMutableList()
    }

    override fun getUser(id: Long): UserDto {
        val existUser = userRepo.findById(id)
        return UserDto(
            existUser.get().id!!,
            existUser.get().username,
            existUser.get().password,
            existUser.get().roles,
            existUser.get().tasks?.map { taskEntity -> TaskDto(
                taskEntity.id!!,
                taskEntity.title,
                taskEntity.description,
                taskEntity.priority,
                taskEntity.status
            ) }?.toMutableList(),)
    }


    override fun addUser(userDto: UserDto): Boolean {

       try {
           userRepo.save(
               UserEntity(
                   username = userDto.username,
                   password = passwordEncoder.encode(userDto.password),
                   roles = userDto.roles as ArrayList<Role>,
                   tasks = mutableListOf()))
           return true
       } catch (e: Exception) {
           println(e.message)
           return false
       }
    }

    @Transactional
    override fun deleteUser(id: Long): Boolean {  ///Todo optimise return
        try {
            userRepo.deleteById(id)
            return true
        }catch (e: Exception){
            println(e.message)
            return false
        }

    }

    @Transactional
    override fun updateUser(id: Long, userDto: UserDto): Boolean {
        val existUser = userRepo.findById(id).getOrNull() ?: return false
        existUser.apply {
            username = userDto.username
            roles.add(Role.ROLE_ADMIN)
        }
        userRepo.save(existUser)
        return true
    }
}