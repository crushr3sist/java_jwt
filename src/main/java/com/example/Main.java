package com.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class Main {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static void main(String[] args) {
        System.out.println("Hello world");

        String jwt = generateToken("user", "user@example.com");
        System.out.println("Generated Token: " + jwt);

        Claims claims = decodeToken(jwt);
        System.out.println("Decoded JWT Claims:");
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Email: " + claims.get("email"));
        System.out.println("Expiration: " + claims.getExpiration());

    }

    public static String generateToken(String subject, String email) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("email", email)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(key)
                .compact();
    }

    public static Claims decodeToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }
}
