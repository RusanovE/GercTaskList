package org.example.gerctasklist.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.example.gerctasklist.dto.ErrorDTO
import org.example.gerctasklist.dto.SignRequestDto
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
import java.util.*
import io.swagger.v3.oas.annotations.parameters.RequestBody as OpenApiRequestBody

@RestController
@RequestMapping("/users")
class AuthController(
    val customUserDetailsService: CustomUserDetailsServiceImpl,
    val userService: UserService,
    val authenticationManager: AuthenticationManager
) {

    /**
         * Method for user authorization.
     */
    @Operation(
        summary = "User authorization",
        description = "Allows the user to log in by providing a username and password.",
        requestBody = OpenApiRequestBody(
            description = "Login details",
            required = true,
            content = [
                Content(
                    schema = Schema(implementation = SignRequestDto::class)
                )
            ]
        ),
        responses = [
            ApiResponse(
                description = "Successful authorization",
                responseCode = "200",
                content = [Content(schema = Schema(implementation = String::class))]
            ),
            ApiResponse(
                description = "Incorrect credentials",
                responseCode = "401",
                content = [Content(schema = Schema(implementation = ErrorDTO::class))]
            )
        ]
    )
    @PostMapping("/login")
    fun createAuthToken(@RequestBody signRequestDto: SignRequestDto): ResponseEntity<*> {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    signRequestDto.username,
                    signRequestDto.password
                )
            )
        } catch (e: AuthenticationException) {
            return ResponseEntity.status(401).body(ErrorDTO(HttpStatus.UNAUTHORIZED.value(), "Bad credentials", Date()))
        }
        return customUserDetailsService.authentication(signRequestDto)
    }

    /**
     * Method for registering a new user.
     */
    @Operation(
        summary = "New User Registration",
        description = "Allows you to create a new user by providing a username and password.",
        requestBody = OpenApiRequestBody(
            description = "Registration details",
            required = true,
            content = [
                Content(
                    schema = Schema(implementation = SignRequestDto::class)
                )
            ]
        ),
        responses = [
            ApiResponse(
                description = "User registered successfully",
                responseCode = "200",
                content = [Content(schema = Schema(implementation = String::class))]
            ),
            ApiResponse(
                description = "Error while registering user",
                responseCode = "403",
                content = [Content(schema = Schema(implementation = ErrorDTO::class))]
            )
        ]
    )
    @PostMapping("/signUp")
    fun createNewUser(@RequestBody signRequestDto: SignRequestDto?): ResponseEntity<*>? {
        return signRequestDto?.let { userService.addUser(it) }
    }
}
