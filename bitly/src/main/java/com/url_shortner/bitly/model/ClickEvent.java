package com.url_shortner.bitly.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class ClickEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "urlMapping_id")
    private UrlMapping urlMapping;

    private LocalDate clickDate;
}
