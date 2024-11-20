package org.example.gerctasklist.controller


import org.example.gerctasklist.dto.SignInRequestDto
import org.example.gerctasklist.dto.UserDto
import org.example.gerctasklist.service.UserService
import org.example.gerctasklist.service.impl.CustomUserDetailsServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class AuthController(val customUserDetailsService: CustomUserDetailsServiceImpl, val userService: UserService, val authenticationManager: AuthenticationManager) {

    @PostMapping("/login")
    fun createAuthToken(@RequestBody signInRequestDto: SignInRequestDto): ResponseEntity<*> {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    signInRequestDto.username,
                    signInRequestDto.password
                )
            )
        } catch (e: AuthenticationException) {
            return ResponseEntity<Any>(
                //ErrorDTO(HttpStatus.UNAUTHORIZED.value(), "Bad credential"),
                HttpStatus.UNAUTHORIZED
            )
        }
        return customUserDetailsService.authentication(signInRequestDto)
    }

    @PostMapping("/signUp")
    fun createNewUser(@RequestBody userDto: UserDto?): ResponseEntity<*> {
        return if (userDto?.let { userService.addUser(it) } == true){
            ResponseEntity.ok("User added successfully")
        }else ResponseEntity.ok("User added unsuccessfully")


    }
}