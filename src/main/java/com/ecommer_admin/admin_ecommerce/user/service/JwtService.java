package com.ecommer_admin.admin_ecommerce.user.service;

import com.ecommer_admin.admin_ecommerce.user.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    public SecretKey getSecretKey () {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken (UserEntity user) {
       return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email" , user.getEmail())
                .claim("roles" , Set.of("ADMIN" , "USER"))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60)) //expires after 60seconds
                .signWith(getSecretKey()) // this is the secret
               .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
}
