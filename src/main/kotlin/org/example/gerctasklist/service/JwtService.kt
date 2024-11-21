package org.example.gerctasklist.service

import org.springframework.security.core.userdetails.UserDetails

interface JwtService {

    fun generateToken(userDetails: UserDetails): String

    fun getUsername(token: String): String

    fun getRoles(token: String?): List<String>
}