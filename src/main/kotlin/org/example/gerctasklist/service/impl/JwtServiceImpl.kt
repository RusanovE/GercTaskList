package org.example.gerctasklist.service.impl

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.example.gerctasklist.service.JwtService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.time.Duration
import java.util.*
import java.util.stream.Collectors

@Service
class JwtServiceImpl: JwtService{

    @Value("\${jwt.secret}")
    private val secret: String? = null

    @Value("\${jwt.lifetime}")
    private val jwtLifetime: Duration? = null

    override fun generateToken(userDetails: UserDetails): String {
        val claims: MutableMap<String, Any?> = HashMap()
        val rolesList = userDetails.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.toList())
        claims["roles"] = rolesList

        val issuedDate = Date()
        val expiredDate = Date(issuedDate.time + jwtLifetime!!.toMillis())
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.username)
            .setIssuedAt(issuedDate)
            .setExpiration(expiredDate)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    override fun getUsername(token: String): String {
        return getAllClaimsFromToken(token).subject
    }

    override fun getRoles(token: String?): List<String> {
        return getAllClaimsFromToken(token!!).get("roles", List::class.java) as List<String>
    }


    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).body;
    }

    private fun getSigningKey(): Key {
        val keyBytes = Decoders.BASE64.decode(secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }

}
