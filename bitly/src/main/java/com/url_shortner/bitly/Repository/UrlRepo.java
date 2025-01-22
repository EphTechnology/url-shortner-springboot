package com.url_shortner.bitly.Repository;

import com.url_shortner.bitly.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepo extends JpaRepository<UrlMapping,Long> {
}
