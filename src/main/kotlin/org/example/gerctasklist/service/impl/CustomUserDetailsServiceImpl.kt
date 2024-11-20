package org.example.gerctasklist.service.impl

import jakarta.transaction.Transactional
import org.example.gerctasklist.dto.JwtAuthenticationResponseDto
import org.example.gerctasklist.dto.SignInRequestDto
import org.example.gerctasklist.repositories.UserRepo
import org.example.gerctasklist.service.JwtService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsServiceImpl( val userRepo: UserRepo, val jwtService: JwtService ) : UserDetailsService {

    //val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepo.findByUsername(username)
        return if (user != null) {
            User(
                user.username,
                user.password,
                setOf(SimpleGrantedAuthority(user.roles.toString()))
            )
        }else throw UsernameNotFoundException("User $username not found")
    }

    fun authentication(signInRequestDto: SignInRequestDto): ResponseEntity<*> {
        val token: String
        try {
            val userDetails = signInRequestDto.username?.let { loadUserByUsername(it) }
            token = userDetails?.let { jwtService.generateToken(it) }.toString()
        } catch (e: BadCredentialsException) {
            return ResponseEntity<Any>(e.message,
                //ErrorDTO(HttpStatus.UNAUTHORIZED.value(), "Bad credential"),
                HttpStatus.UNAUTHORIZED
            )
        }

        return ResponseEntity.ok<Any>(JwtAuthenticationResponseDto(token))
    }
}