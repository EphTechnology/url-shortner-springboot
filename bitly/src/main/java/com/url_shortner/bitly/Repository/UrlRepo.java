package com.url_shortner.bitly.Repository;

import com.url_shortner.bitly.model.UrlMapping;
import com.url_shortner.bitly.model.User;
import com.url_shortner.bitly.request.UrlMappingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlRepo extends JpaRepository<UrlMapping,Long> {
    List<UrlMapping> findByUser(User user);

    UrlMapping findByShortUrl(String shortUrl);
}
