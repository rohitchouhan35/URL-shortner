package com.rohitchouhan35.urlShortner.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "url_info_model")
public class UrlModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String actualUrl;
    String shortUrl;
    Date createdAt;
    Date lastCrawled;

}
