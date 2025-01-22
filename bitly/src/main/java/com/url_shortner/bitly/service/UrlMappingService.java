package com.url_shortner.bitly.service;

import com.url_shortner.bitly.Repository.UrlRepo;
import com.url_shortner.bitly.model.UrlMapping;
import com.url_shortner.bitly.model.User;
import com.url_shortner.bitly.request.UrlMappingDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@AllArgsConstructor
public class UrlMappingService {
    private UrlRepo urlRepo;
    public UrlMappingDto createShortUrl(String originalUrl, User user) {
        String shortUrl=generateShorUrl();
        UrlMapping urlMapping=new UrlMapping();
        urlMapping.setOriginal_url(originalUrl);
        urlMapping.setShort_url(shortUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedAt(LocalDateTime.now());
        UrlMapping saved=urlRepo.save(urlMapping);
        return converToUrlMappingDto(saved);
    }

    private UrlMappingDto converToUrlMappingDto(UrlMapping urlMapping){
        UrlMappingDto urlMappingDto=new UrlMappingDto();
        urlMappingDto.setOriginalUrl(urlMappingDto.getOriginalUrl());
        urlMappingDto.setShortUrl(urlMapping.getShort_url());
        urlMappingDto.setUsername(urlMapping.getUser().getUsername());
        urlMappingDto.setCreatedAt(urlMapping.getCreatedAt());
        urlMappingDto.setClickCount(urlMapping.getClick_count());
        urlMappingDto.setId(urlMapping.getId());
        return urlMappingDto;
    }
    private String generateShorUrl() {
        StringBuilder url=new StringBuilder(8);
        String character="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random=new Random();
        for (int i = 0; i < 8; i++) {
            url.append(character.charAt(random.nextInt(character.length())));
        }
        return url.toString();
    }
}
