package com.example.urlly.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ShortUrl {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String hash;

    @Column(nullable = false)
    private String url;

    private LocalDateTime createdAt = LocalDateTime.now();
    private Long numberOfHits = 0L;
    private String lastReferrerIp;

    public ShortUrl() { }

    public ShortUrl(final String hash, final String url) {
        this.hash = hash;
        this.url = url;
    }

}
