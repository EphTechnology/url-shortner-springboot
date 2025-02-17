package com.url_shortner.bitly.controller;

import com.url_shortner.bitly.Dto.ClickEventDto;
import com.url_shortner.bitly.model.User;
import com.url_shortner.bitly.request.UrlMappingDto;
import com.url_shortner.bitly.service.UrlMappingService;
import com.url_shortner.bitly.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")

public class UrlMappingController {
    UserService userService;
    UrlMappingService urlMappingService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlMappingDto> createShorUrl(@RequestBody Map<String,String> request,
                                                       Principal principal){
        String originalUrl=request.get("originalUrl");
        User user=userService.findByUserName(principal.getName());
        UrlMappingDto urlMappingDto=urlMappingService.createShortUrl(originalUrl,user);
        return new ResponseEntity<>(urlMappingDto, HttpStatus.OK);
    }

    @GetMapping("/myurls")
    public ResponseEntity<List<UrlMappingDto>> getShortUrl(Principal principal){
        User user=userService.findByUserName(principal.getName());
        List<UrlMappingDto> urlMappingDto=urlMappingService.getUrlByUser(user);
        return new ResponseEntity<>(urlMappingDto,HttpStatus.OK);
    }
    @GetMapping("/analytics/{shortUrl}")
    public ResponseEntity<List<ClickEventDto>> getAnalytics(
            @PathVariable String shortURl, @RequestParam String startDate, @RequestParam String endDate
    ){
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        LocalDateTime start=LocalDateTime.parse(startDate,dateTimeFormatter);
        LocalDateTime end=LocalDateTime.parse(endDate,dateTimeFormatter);
        List<ClickEventDto> clickEventDto=urlMappingService.getClickEventsByDate(shortURl,start,end);
        return new ResponseEntity<>(clickEventDto,HttpStatus.OK);
    }
    @GetMapping("/totalClicks")
    public ResponseEntity<Map<LocalDate,Long>> getTotalClicks(Principal principal,
                                                              @RequestParam String startDate,
                                                              @RequestParam String endDate){

        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ISO_LOCAL_DATE;
        User user=userService.findByUserName(principal.getName());
        LocalDate start=LocalDate.parse(startDate,dateTimeFormatter);
        LocalDate end=LocalDate.parse(endDate,dateTimeFormatter);

        Map<LocalDate,Long> totalClicks=urlMappingService.getTotalClickByUser(user,start,end);
        return new ResponseEntity<>(totalClicks, HttpStatus.OK);
    }

}
