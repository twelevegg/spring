package com.twelvegg.aicc.common.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long accessTokenValidityInMilliseconds = 60 * 60 * 1000; // 1 시간
    private final long refreshTokenValidityInMilliseconds = 3 * 60 * 60 * 1000; // 3 시간

    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey) {
        byte[] keyBytes;
        if (secretKey.length() < 32) {
            keyBytes = secretKey.getBytes();
        } else {
            keyBytes = Decoders.BASE64.decode(secretKey);
        }
        if (keyBytes.length < 32) {
            keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        }

        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String email, Long memberId, String tenantName) {
        return createToken(email, memberId, tenantName, accessTokenValidityInMilliseconds);
    }

    public String createRefreshToken(String email, Long memberId, String tenantName) {
        return createToken(email, memberId, tenantName, refreshTokenValidityInMilliseconds);
    }

    private String createToken(String email, Long memberId, String tenantName, long validity) {
        Date now = new Date();
        Date validityDate = new Date(now.getTime() + validity);

        return Jwts.builder()
                .setSubject(email)
                .claim("memberId", memberId)
                .claim("tenantName", tenantName)
                .setIssuedAt(now)
                .setExpiration(validityDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Log error
            return false;
        }
    }

    public String getEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public Long getMemberId(String token) {
        return extractClaims(token).get("memberId", Long.class);
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
