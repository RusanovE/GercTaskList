import org.example.gerctasklist.dto.JwtAuthenticationResponseDto
import org.example.gerctasklist.dto.SignRequestDto
import org.example.gerctasklist.dto.enums.Role
import org.example.gerctasklist.repositories.UserRepo
import org.example.gerctasklist.service.JwtService
import org.example.gerctasklist.service.impl.CustomUserDetailsServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class CustomUserDetailsServiceImplTest {

    private lateinit var userRepo: UserRepo
    private lateinit var jwtService: JwtService
    private lateinit var customUserDetailsService: CustomUserDetailsServiceImpl

    @BeforeEach
    fun setUp() {
        userRepo = Mockito.mock(UserRepo::class.java)
        jwtService = Mockito.mock(JwtService::class.java)
        customUserDetailsService = CustomUserDetailsServiceImpl(userRepo, jwtService)
    }

    @Test
    fun `loadUserByUsername should return user details when user exists`() {
        val username = "testUser"
        val password = "testPassword"
        val roles = mutableListOf(Role.ROLE_USER)

        val mockUser = org.example.gerctasklist.entities.UserEntity(username = username, password = password, roles = roles)
        Mockito.`when`(userRepo.findByUsername(username)).thenReturn(mockUser)

        val userDetails = customUserDetailsService.loadUserByUsername(username)
        println(userDetails.toString())

        assertNotNull(userDetails)
        assertEquals(username, userDetails.username)
        assertEquals(password, userDetails.password)
        assertTrue(userDetails.authorities.any { it.authority == roles[0].name })
    }

    @Test
    fun `loadUserByUsername should throw exception when user does not exist`() {
        val username = "nonExistentUser"

        Mockito.`when`(userRepo.findByUsername(username)).thenReturn(null)

        val exception = org.junit.jupiter.api.assertThrows<UsernameNotFoundException> {
            customUserDetailsService.loadUserByUsername(username)
        }

        assertEquals("User $username not found", exception.message)
    }

    @Test
    fun `authentication should return token for valid credentials`() {
        val username = "testUser"
        val password = "testPassword"
        val roles = mutableListOf(Role.ROLE_USER)
        val mockToken = "mockJwtToken"

        val mockUser = org.example.gerctasklist.entities.UserEntity(username = username, password = password, roles = roles)
        Mockito.`when`(userRepo.findByUsername(username)).thenReturn(mockUser)
        Mockito.`when`(jwtService.generateToken(any())).thenReturn(mockToken)

        val signRequestDto = SignRequestDto(username, password)
        val response = customUserDetailsService.authentication(signRequestDto)

        assertEquals(HttpStatus.OK, response.statusCode)
        val responseBody = response.body as JwtAuthenticationResponseDto
        assertEquals(mockToken, responseBody.token)
    }

    @Test
    fun `authentication should return unauthorized for invalid credentials`() {
        val username = "testUser"

        Mockito.`when`(userRepo.findByUsername(username)).thenReturn(null)

        val signRequestDto = SignRequestDto(username, "invalidPassword")

        val response = customUserDetailsService.authentication(signRequestDto)

        assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
        assertTrue(response.body is String)
        assertEquals("Bad credential", response.body)
    }
}
