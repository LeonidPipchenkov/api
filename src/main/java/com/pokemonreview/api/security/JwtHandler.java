package com.pokemonreview.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtHandler {

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date creationDate = new Date();
        Date expirationDate = new Date(creationDate.getTime() + SecurityConstants.JWT_EXPIRATION);
        SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes());
        return Jwts.builder()
                .subject(username)
                .issuedAt(creationDate)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token) {
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
            SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes());
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("Incorrect JWT");
        }
    }

}
