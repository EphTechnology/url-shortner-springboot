package com.url_shortner.bitly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String email;
    private String role;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<UrlMapping> urlMapping;
}
