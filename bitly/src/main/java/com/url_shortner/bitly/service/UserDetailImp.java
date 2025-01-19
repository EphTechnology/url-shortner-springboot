package com.url_shortner.bitly.service;

import com.url_shortner.bitly.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Data
@AllArgsConstructor
public class UserDetailImp implements UserDetails {
    private static final Long serialVersionUID=1L;
    private Long id;
    private String username;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailImp build(User user){
        GrantedAuthority authority=new SimpleGrantedAuthority(user.getRole());
        return new UserDetailImp(
                user.getId(), user.getUsername(),
                user.getEmail(), user.getPassword(), Collections.singleton(authority)
                );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
