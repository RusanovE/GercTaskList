import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.example.gerctasklist.service.impl.JwtServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Duration
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class JwtServiceImplTest {

    private lateinit var jwtService: JwtServiceImpl
    private lateinit var secretKey: String
    private val jwtLifetime: Duration = Duration.ofHours(1)

    @BeforeEach
    fun setUp() {
        jwtService = JwtServiceImpl()
        secretKey = Base64.getEncoder().encodeToString("your-secret-key-must-be-32-bytes".toByteArray())

        // Устанавливаем значения аннотаций @Value
        val secretField = JwtServiceImpl::class.java.getDeclaredField("secret")
        secretField.isAccessible = true
        secretField.set(jwtService, secretKey)

        val lifetimeField = JwtServiceImpl::class.java.getDeclaredField("jwtLifetime")
        lifetimeField.isAccessible = true
        lifetimeField.set(jwtService, jwtLifetime)
    }

    @Test
    fun `generateToken should create a valid JWT`() {
        val userDetails = Mockito.mock(UserDetails::class.java)
        Mockito.`when`(userDetails.username).thenReturn("testUser")
        Mockito.`when`(userDetails.authorities).thenReturn(
            listOf(
                GrantedAuthority { "ROLE_USER" },
                GrantedAuthority { "ROLE_ADMIN" }
            )
        )

        val token = jwtService.generateToken(userDetails)
        assertNotNull(token)

        val claims = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)))
            .build()
            .parseClaimsJws(token).body

        assertEquals("testUser", claims.subject)
        assertEquals(listOf("ROLE_USER", "ROLE_ADMIN"), claims["roles"])
    }

    @Test
    fun `getUsername should extract username from token`() {
        val token = generateTestToken("testUser", listOf("ROLE_USER"))
        val username = jwtService.getUsername(token)

        assertEquals("testUser", username)
    }

    @Test
    fun `getRoles should extract roles from token`() {
        val roles = listOf("ROLE_USER", "ROLE_ADMIN")
        val token = generateTestToken("testUser", roles)
        val extractedRoles = jwtService.getRoles(token)

        assertEquals(roles, extractedRoles)
    }

    private fun generateTestToken(username: String, roles: List<String>): String {
        val issuedDate = Date()
        val expiredDate = Date(issuedDate.time + jwtLifetime.toMillis())
        val claims = mapOf("roles" to roles)

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(issuedDate)
            .setExpiration(expiredDate)
            .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)), SignatureAlgorithm.HS256)
            .compact()
    }
}
