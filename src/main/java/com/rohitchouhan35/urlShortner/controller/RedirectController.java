package com.rohitchouhan35.urlShortner.controller;

import com.rohitchouhan35.urlShortner.model.UrlModel;
import com.rohitchouhan35.urlShortner.service.UrlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
public class RedirectController {

    private static final Logger logger = LoggerFactory.getLogger(RedirectController.class);

    @Autowired
    private UrlMapper urlMapper;

    @GetMapping("/")
    public String home() {
        return "<h1>Hi! Current time is: " + LocalDateTime.now() + "</h1>";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUrl(@RequestBody UrlModel urlModel) {
        try {
            String shortUrl = urlMapper.registerUrl(urlModel);
            return ResponseEntity.ok("Short URL registered successfully: " + shortUrl);
        } catch (Exception e) {
            logger.error("Failed to register URL: {}", urlModel.getActualUrl(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to register URL. Please try again later.");
        }
    }

    @GetMapping("/r/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            String fullUrl = urlMapper.getFullUrl(shortUrl);
            if (fullUrl == null) {
                logger.error("Short URL not found: {}", shortUrl);
                return ResponseEntity.notFound().build();
            }
            httpHeaders.setLocation(URI.create(fullUrl));
            logger.info("Redirecting to: {}", fullUrl);
            return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
        } catch (Exception e) {
            logger.error("Failed to redirect to short URL: {}", shortUrl, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
