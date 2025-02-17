package com.url_shortner.bitly.service;

import com.url_shortner.bitly.Dto.ClickEventDto;
import com.url_shortner.bitly.Repository.ClickEventRepo;
import com.url_shortner.bitly.Repository.UrlRepo;
import com.url_shortner.bitly.model.ClickEvent;
import com.url_shortner.bitly.model.UrlMapping;
import com.url_shortner.bitly.model.User;
import com.url_shortner.bitly.request.UrlMappingDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlMappingService {
    private UrlRepo urlRepo;
    private ClickEventRepo clickEventRepo;
    public UrlMappingDto createShortUrl(String originalUrl, User user) {
        String shortUrl=generateShorUrl();
        UrlMapping urlMapping=new UrlMapping();
        urlMapping.setOriginal_url(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedAt(LocalDateTime.now());
        UrlMapping saved=urlRepo.save(urlMapping);
        return converToUrlMappingDto(saved);
    }

    public UrlMappingDto converToUrlMappingDto(UrlMapping urlMapping){
        UrlMappingDto urlMappingDto=new UrlMappingDto();
        urlMappingDto.setOriginalUrl(urlMapping.getOriginal_url());
        urlMappingDto.setShortUrl(urlMapping.getShortUrl());
        urlMappingDto.setUsername(urlMapping.getUser().getUsername());
        urlMappingDto.setCreatedAt(urlMapping.getCreatedAt());
        urlMappingDto.setClickCount(urlMapping.getClick_count());
        urlMappingDto.setId(urlMapping.getId());
        return urlMappingDto;
    }
    public String generateShorUrl() {
        StringBuilder url=new StringBuilder(8);
        String character="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random=new Random();
        for (int i = 0; i < 8; i++) {
            url.append(character.charAt(random.nextInt(character.length())));
        }
        return url.toString();
    }

    public List<UrlMappingDto> getUrlByUser(User user){
        return urlRepo.findByUser(user).stream().map(this::converToUrlMappingDto).toList();
    }

    public List<ClickEventDto> getClickEventsByDate(String shortURl, LocalDateTime start, LocalDateTime end) {
        UrlMapping urlMapping=urlRepo.findByShortUrl(shortURl);
        if (urlMapping!=null){
            return clickEventRepo.findByUrlMappingAndClickDateBetween(urlMapping,start,end).stream()
                    .collect(Collectors.groupingBy(click->click.getClickDate(),Collectors.counting()))
                    .entrySet().stream().map(entry->{
                        ClickEventDto clickEventDto=new ClickEventDto();
                        clickEventDto.setClicked_date(entry.getKey());
                        clickEventDto.setCount(entry.getValue());
                        return clickEventDto;
                    }).collect(Collectors.toList());
        }
        return null;

    }

    public Map<LocalDate, Long> getTotalClickByUser(User user, LocalDate start, LocalDate end) {
        List<UrlMapping> urlMappings=urlRepo.findByUser(user);
        List<ClickEvent> clickEvents=clickEventRepo.findByUrlMappingInAndClickDateBetween(urlMappings,start.atStartOfDay(),
                end.plusDays(1).atStartOfDay());
        return clickEvents.stream().collect(Collectors.groupingBy(click->click.getClickDate(),
                Collectors.counting()));
    }

    public UrlMapping getByShortUrl(String shortUrl) {
        return urlRepo.findByShortUrl(shortUrl);
    }
}
