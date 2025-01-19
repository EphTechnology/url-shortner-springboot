package com.url_shortner.bitly.jwtservice;

import com.url_shortner.bitly.service.UserDetailImp;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationTime;


    public String getJwtFromHeader(HttpServletRequest request){
        String jwtBearerToken=request.getHeader("Authorization");
        if (jwtBearerToken!=null&&jwtBearerToken.startsWith("Bearer ")){
            jwtBearerToken=jwtBearerToken.substring(7);
            return jwtBearerToken;
        }
        return null;
    }
    public String generateJwtToken(UserDetailImp userDetailImp){
        String username=userDetailImp.getUsername();
        String role=userDetailImp.getAuthorities().stream().
                map(authority->authority.getAuthority()).
                collect(Collectors.joining(","));

        return Jwts.builder().
                subject(username).
                claim("roles",role)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+jwtExpirationTime))
                .signWith((SecretKey) key()).compact();
    }

    public String getUsernameFromJwt(String token){
        return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token)
                .getPayload().getSubject();
    }
    public boolean verifyToken(String authToken){
        try {
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            return true;
        } catch (JwtException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

    }
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }
}
