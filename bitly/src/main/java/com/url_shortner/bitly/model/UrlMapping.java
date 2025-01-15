package com.url_shortner.bitly.model;

import com.url_shortner.bitly.service.UserDetailImp;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String original_url;
    private String short_url;
    private LocalDate date;
    private int click_count=0;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "urlMapping")
    private List<ClickEvent> clickEvent;

}
