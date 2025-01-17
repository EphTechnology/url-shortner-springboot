package com.url_shortner.bitly.jwtservice;

import com.url_shortner.bitly.service.UserDetailImp;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtUtils {
    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    @Value("${jwt.expiration.time}")
    private String jwtExpirationTime;


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
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }
}
