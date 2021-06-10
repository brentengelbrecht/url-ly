package com.example.urlly.repository;

import com.example.urlly.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortenerRepository extends JpaRepository<ShortUrl, Long> {

    Optional<ShortUrl> findByUrl(String url);
    Optional<ShortUrl> findByHash(String hash);

}
