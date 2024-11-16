package org.example.gerctasklist.service.impl

import jakarta.transaction.Transactional
import org.example.gerctasklist.dto.enums.Role
import org.example.gerctasklist.dto.TaskDto
import org.example.gerctasklist.dto.enums.TaskPriority
import org.example.gerctasklist.dto.UserDto
import org.example.gerctasklist.entities.TaskEntity
import org.example.gerctasklist.entities.UserEntity
import org.example.gerctasklist.repositories.UserRepo
import org.example.gerctasklist.service.UserService
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull

@Service
class UserServiceImpl(val userRepo: UserRepo) : UserService {

    override fun getAll(): MutableList<UserDto> {
        return userRepo.findAll().map { userEntity -> UserDto(
            userEntity.id!!,
            userEntity.name,
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
            existUser.get().name,
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
                   name = userDto.name,
                   roles = userDto.roles as ArrayList<Role>,
                   tasks = mutableListOf()))

           return true
       } catch (e: Exception) {
           return false
       }
    }

    @Transactional
    override fun deleteUser(id: Long): Boolean {
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

        try{
            val existUser = userRepo.findById(id)
            existUser.apply {
                existUser.get().name = userDto.name
                existUser.get().roles = userDto.roles
            }

            userRepo.save(existUser.getOrNull()?: return false)
            return true
        }catch (e: Exception){
            println(e.message)
            return false
        }
    }
}