package org.ssp.security;

import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.ssp.exceptions.SspTokenException;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.ssp.exceptions.SspException.Ssp_5;
import static org.ssp.exceptions.SspException.Ssp_6;

@Service
public class TokenService {
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String login) {
        Date date = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ClaimJwtException ex) {
            throw new SspTokenException(Ssp_5, ex);
        }
    }

    public String getLoginFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (ClaimJwtException ex) {
            throw new SspTokenException(Ssp_6, ex);
        }
    }
}
