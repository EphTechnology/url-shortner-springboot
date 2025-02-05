package com.url_shortner.bitly.controller;

import com.url_shortner.bitly.model.User;
import com.url_shortner.bitly.request.UrlMappingDto;
import com.url_shortner.bitly.service.UrlMappingService;
import com.url_shortner.bitly.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
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
    public ResponseEntity<List<UrlMappingDto>> createShorUrl(Principal principal){
        User user=userService.findByUserName(principal.getName());
        List<UrlMappingDto> urlMappingDto=urlMappingService.getUrlByUser(user);
        return new ResponseEntity<>(urlMappingDto,HttpStatus.OK);
    }

}
