package com.url_shortner.bitly.Repository;

import com.url_shortner.bitly.model.ClickEvent;
import com.url_shortner.bitly.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClickEventRepo extends JpaRepository<ClickEvent,Long> {
    List<ClickEvent> findByUrlMappingAndClickDateBetween(UrlMapping urlMapping, LocalDateTime start, LocalDateTime end);
    List<ClickEvent> findByUrlMappingInAndClickDateBetween(List<UrlMapping> urlMappings,LocalDateTime start, LocalDateTime end);
}
