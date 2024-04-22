package com.rohitchouhan35.urlShortner.service;

import com.rohitchouhan35.urlShortner.model.UrlModel;
import com.rohitchouhan35.urlShortner.repository.UrlModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlMapper {

    @Autowired
    private UrlModelRepository urlModelRepository;

    private static final Logger logger = LoggerFactory.getLogger(UrlMapper.class);
    private static final String SECRET_KEY = "your_secret_key";

    public String getFullUrl(String shortUrl) {
        try {
            return urlModelRepository.findFullUrlByShortUrl(shortUrl);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving full URL for short URL: {}", shortUrl, e);
            return null;
        }
    }

    public String registerUrl(UrlModel urlModel) {
        try {
            String shortUrl = generateUniqueShortUrl(urlModel.getActualUrl());
            urlModel.setShortUrl(shortUrl);
            urlModelRepository.save(urlModel);
            return urlModel.getShortUrl();
        } catch (Exception e) {
            logger.error("Error occurred while registering URL: {}", urlModel.getActualUrl(), e);
            return null;
        }
    }

    private String generateUniqueShortUrl(String actualUrl) {
        return generateRandomString();
    }

    private String generateRandomString() {
        int length = 6;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
