package me.alanton.kinoreviewrewrite.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import me.alanton.kinoreviewrewrite.entity.Actor;
import me.alanton.kinoreviewrewrite.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${auth.token.secret}")
    private String jwtSecret;

    @Value("${auth.token.access.expiration.time}")
    private Long accessTokenExpirationTime;

    @Value("${auth.token.refresh.expiration.time}")
    private Long refreshTokenExpirationTime;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        if (userDetails instanceof Actor customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("email", customUserDetails.getEmail());
            claims.put("roles", customUserDetails.getRoles());
        }

        return generateAccessToken(claims, userDetails);
    }

    @Override
    public String generateRefreshAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        if (userDetails instanceof Actor customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("email", customUserDetails.getEmail());
        }

        return generateRefreshToken(claims, userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);

        return claimsResolvers.apply(claims);
    }

    @Override
    public String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        final Instant expirationInstant = LocalDateTime
                .now()
                .plusSeconds(accessTokenExpirationTime)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        final Date expiration = Date.from(expirationInstant);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(extraClaims)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        final Instant expirationInstant = LocalDateTime
                .now()
                .plusSeconds(refreshTokenExpirationTime)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        final Date expiration = Date.from(expirationInstant);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(extraClaims)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
