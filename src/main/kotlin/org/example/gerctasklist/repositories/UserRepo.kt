package org.example.gerctasklist.repositories

import org.example.gerctasklist.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo: JpaRepository<UserEntity,Long> {
    fun findByUsername(username: String?): UserEntity?

    fun existsByUsername(username: String?): Boolean
}