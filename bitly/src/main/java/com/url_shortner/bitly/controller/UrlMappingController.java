package com.url_shortner.bitly.controller;

import com.url_shortner.bitly.model.User;
import com.url_shortner.bitly.request.UrlMappingDto;
import com.url_shortner.bitly.service.UrlMappingService;
import com.url_shortner.bitly.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {
    UserService userService;
    UrlMappingService urlMappingService;
    @PostMapping
    public ResponseEntity<UrlMappingDto> createShorUrl(@RequestBody Map<String,String> request, Principal principal){
        String originalUrl=request.get("originalUrl");
        User user=userService.findByUserName(principal.getName());
        UrlMappingDto urlMappingDto=urlMappingService.createShortUrl(originalUrl,user);
    }
}
