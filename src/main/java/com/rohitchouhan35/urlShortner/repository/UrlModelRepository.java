package com.rohitchouhan35.urlShortner.repository;

import com.rohitchouhan35.urlShortner.model.UrlModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlModelRepository extends JpaRepository<UrlModel, Long> {

    @Query(value = "SELECT um.actual_url FROM url_info_model um WHERE um.short_url = :shortUrl", nativeQuery = true)
    String findFullUrlByShortUrl(@Param("shortUrl") String shortUrl);
}
