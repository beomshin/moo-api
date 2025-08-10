package com.kr.moo.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JWT 발급/검증 유틸 클래스
 */
@Component
public class JwtProvider {
    private final String secretKey = "ADNMOZYZACZFLBBIYOXGTGXVQCYFMBQU"; // 토큰 키
    private final long validityInMilliseconds = 1000 * 60 * 60; // 1시간

    // JWT 생성
    public String createToken(Long userId){
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT에서 유저 ID 추출
    public String getUserId(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 유효성 검증
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e){
            return  false;
        }
    }
}
