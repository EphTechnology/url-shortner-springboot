package com.url_shortner.bitly.controller;

import com.url_shortner.bitly.model.UrlMapping;
import com.url_shortner.bitly.service.UrlMappingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RedirectController {
    UrlMappingService urlMappingService;
    @GetMapping("/{shortUrl}")

    ResponseEntity<Void> redirect(@PathVariable String shortUrl){
        UrlMapping urlMapping=urlMappingService.getByShortUrl(shortUrl);
        if (urlMapping!=null){
            HttpHeaders httpHeaders=new HttpHeaders();
            httpHeaders.add("Location",urlMapping.getOriginal_url());
            return ResponseEntity.status(302).headers(httpHeaders).build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
