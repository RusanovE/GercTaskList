package org.example.gerctasklist.service.impl

import jakarta.transaction.Transactional
import org.example.gerctasklist.dto.ErrorDTO
import org.example.gerctasklist.dto.SignRequestDto
import org.example.gerctasklist.dto.UserDto
import org.example.gerctasklist.entities.UserEntity
import org.example.gerctasklist.repositories.UserRepo
import org.example.gerctasklist.service.UserService
import org.example.gerctasklist.utill.ObjMapper
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserServiceImpl(val userRepo: UserRepo, val objMapper: ObjMapper) : UserService {

    override fun getAll(): MutableList<UserDto> =
        userRepo.findAll().map { objMapper.toDto(it) }.toMutableList()

    override fun getUser(username: String): UserEntity =
        userRepo.findByUsername(username) ?: throw NoSuchElementException("User not found")

    override fun getCurrentUserId(): Long {
        val username = SecurityContextHolder.getContext().authentication.name
        return getUser(username).id ?: throw IllegalStateException("Current user ID is null")
    }

    @Transactional
    override fun addUser(signRequestDto: SignRequestDto): ResponseEntity<*> {
        return try {
            if (userRepo.existsByUsername(signRequestDto.username)) {
                return ResponseEntity.status(400).body(ErrorDTO(400, "Username already exists", Date()))
            }

            val userEntity = objMapper.toEntity(signRequestDto)
            userRepo.save(userEntity)
            ResponseEntity.ok("User added successfully")
        } catch (e: Exception) {
            ResponseEntity.status(500).body(ErrorDTO(500, "Failed to add user", Date()))
        }
    }

    @Transactional
    override fun deleteUser(id: Long): ResponseEntity<*> {
        return try {
            if (!userRepo.existsById(id)) {
                return ResponseEntity.status(404).body(ErrorDTO(404, "User not found", Date()))
            }

            userRepo.deleteById(id)
            ResponseEntity.ok("User deleted successfully")
        } catch (e: Exception) {
            ResponseEntity.status(500).body(ErrorDTO(500, "Failed to delete user", Date()))
        }
    }

    @Transactional
    override fun updateUser(id: Long, userDto: UserDto): ResponseEntity<*> {
        return try {
            val existUser = userRepo.findById(id).orElse(null)
                ?: return ResponseEntity.status(404).body(ErrorDTO(404, "User not found", Date()))

            if (userDto.username.isBlank()) {
                return ResponseEntity.status(400).body(ErrorDTO(400, "Username cannot be blank", Date()))
            }

            existUser.apply {
                username = userDto.username
                roles = userDto.roles?: roles
            }

            userRepo.save(existUser)

            ResponseEntity.ok("User updated successfully")
        } catch (e: Exception) {
            ResponseEntity.status(500).body(ErrorDTO(500, "Failed to update user", Date()))
        }
    }

}