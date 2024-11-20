package org.example.gerctasklist.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.stream.Collectors

interface JwtService {

    fun generateToken(userDetails: UserDetails): String

    fun getUsername(token: String): String

    fun getRoles(token: String?): List<*>
}