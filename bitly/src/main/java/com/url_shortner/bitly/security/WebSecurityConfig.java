package com.url_shortner.bitly.security;

import com.url_shortner.bitly.jwtservice.JwtFilter;
import com.url_shortner.bitly.service.UserDetailImp;
import com.url_shortner.bitly.service.UserDetailServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
@Configuration
public class WebSecurityConfig {
    @Autowired
    UserDetailServiceImp userDetailServiceImp;

    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthProv=new DaoAuthenticationProvider();
        daoAuthProv.setUserDetailsService(userDetailServiceImp);
        daoAuthProv.setPasswordEncoder(passwordEncoder());
        return daoAuthProv;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http.csrf(AbstractHttpConfigurer::disable);
       http.authorizeHttpRequests(authorize->authorize.
               requestMatchers("/api/auth/**").permitAll().
               requestMatchers("/{shortUrl}").permitAll().
               requestMatchers("/api/urls/**").authenticated().
               anyRequest().authenticated());
        http.authenticationProvider(daoAuthenticationProvider());
       http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



}