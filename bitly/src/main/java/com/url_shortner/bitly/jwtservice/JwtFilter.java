package com.url_shortner.bitly.jwtservice;

import com.url_shortner.bitly.service.UserDetailImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken=jwtUtils.getJwtFromHeader(request);
            if (jwtToken!=null && jwtUtils.verifyToken(jwtToken)){
                String username=jwtUtils.getUsernameFromJwt(jwtToken);
                UserDetails user= userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request,response);

    }

}
