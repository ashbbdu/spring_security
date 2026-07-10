package com.ecommer_admin.admin_ecommerce.auth.service;

import com.ecommer_admin.admin_ecommerce.user.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.swing.text.html.parser.Parser;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Set;

//This service is responsible only for two methods 1 : Create a JWT 2 : Verify the token
@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey generateKey () {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken (UserEntity user) {
      return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email" , user.getEmail())
                .claim("roles" , Set.of("ADMIN" , "USER")) // hardcoding it for now
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60))
                .signWith(generateKey())
                .compact()
        ;
    }

    public Long getIdFromToken (String token) {
//        JwtParser jwtParser = Jwts.parser().verifyWith(generateKey()).build();
//        System.out.println(jwtParser.parse(token).getPayload());;
//        return 1L;
        Claims claims = Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();
        return Long.valueOf(claims.getId());
    }
}
