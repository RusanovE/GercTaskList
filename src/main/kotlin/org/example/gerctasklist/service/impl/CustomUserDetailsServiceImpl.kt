package org.example.gerctasklist.service.impl

import org.example.gerctasklist.dto.ErrorDTO
import org.example.gerctasklist.dto.JwtAuthenticationResponseDto
import org.example.gerctasklist.dto.SignRequestDto
import org.example.gerctasklist.repositories.UserRepo
import org.example.gerctasklist.service.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomUserDetailsServiceImpl( val userRepo: UserRepo, val jwtService: JwtService ) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepo.findByUsername(username)
        return if (user != null) {
            val authorities = user.roles.map { role -> SimpleGrantedAuthority(role.name) }.toSet()
            User(
                user.username,
                user.password,
                authorities
            )
        }else throw UsernameNotFoundException("User $username not found")
    }

    fun authentication(signInRequestDto: SignRequestDto): ResponseEntity<*> {
        val token: String
        try {
            val userDetails = signInRequestDto.username?.let { loadUserByUsername(it) }
            token = userDetails?.let { jwtService.generateToken(it) }.toString()
        } catch (e: BadCredentialsException) {
            return ResponseEntity.status(401).body(ErrorDTO(401, "Bad credential", Date()))
        }

        return ResponseEntity.ok<Any>(JwtAuthenticationResponseDto(token))
    }
}