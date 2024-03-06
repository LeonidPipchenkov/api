package com.pokemonreview.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTHandler {

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date creationDate = new Date();
        Date expirationDate = new Date(creationDate.getTime() + SecurityConstants.JWT_EXPIRATION);
        Key secretKey = Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes());
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(creationDate)
                .setExpiration(expirationDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        Claims claims = getClaimsFromToken(token);
        Date expirationDate = claims.getExpiration();
        if (expirationDate.before(new Date())) {
            throw new CredentialsExpiredException("JWT expired");
        }
        return true;
    }

    private Claims getClaimsFromToken(String token) {
        try {
            Key secretKey = Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes());
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("Incorrect JWT");
        }
    }

}
