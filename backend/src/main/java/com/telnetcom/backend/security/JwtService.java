package com.telnetcom.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import java.security.Key;

import java.util.Date;

@Service
public class JwtService {

        private final String SECRET =
                "telnetcomsecretkeytelnetcomsecretkey123456";
        private final Key key =
                Keys.hmacShaKeyFor(SECRET.getBytes());
        public String generateToken(String username){
                return Jwts.builder()
                        .setSubject(username)
                        .setIssuedAt(new Date())
                        .setExpiration(
                                new Date(System.currentTimeMillis() + 86400000)
                        )
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact();
        }

        public String extractUsername(String token) {
                return extractClaims(token).getSubject();
        }

        public boolean isTokenValid(String token) {
                try {
                        Claims claims = extractClaims(token);
                        return claims.getExpiration().after(new Date());
                } catch (Exception exception) {
                        return false;
                }
        }

        private Claims extractClaims(String token) {
                return Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
        }
}
