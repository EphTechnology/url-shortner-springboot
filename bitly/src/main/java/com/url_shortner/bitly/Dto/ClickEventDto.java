package com.url_shortner.bitly.Dto;

import com.url_shortner.bitly.model.UrlMapping;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClickEventDto {
    private Long count;

    private LocalDate clicked_date;
}
