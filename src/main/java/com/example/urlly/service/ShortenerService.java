package com.example.urlly.service;

import com.example.urlly.entity.ShortUrl;
import com.example.urlly.repository.ShortenerRepository;
import com.example.urlly.util.ShortNameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ShortenerService {

    private final ShortNameGenerator shortNameGenerator;
    private final ShortenerRepository shortenerRepository;

    public ShortenerService(ShortNameGenerator shortNameGenerator, ShortenerRepository shortenerRepository) {
        this.shortNameGenerator = shortNameGenerator;
        this.shortenerRepository = shortenerRepository;
    }

    public String getShortName(String url) {
        return generateShortName(url);
    }

    public String getUrl(String shortName) {
        Optional<ShortUrl> shortUrl = shortenerRepository.findByHash(shortName);

        if (!shortUrl.isPresent()) {
            throw new IllegalArgumentException("Short name is invalid");
        }

        log.debug("URL found {}", shortUrl.get().getUrl());
        return shortUrl.get().getUrl();
    }

    private String generateShortName(String url) {
        Long id = saveShortName(url).getId();
        String shortName = shortNameGenerator.encode(id);
        log.debug("Hash created {}", shortName);
        return updateShortName(id, shortName);
    }

    private ShortUrl saveShortName(String url) {
        return shortenerRepository.save(new ShortUrl("", url));
    }

    private String updateShortName(Long id, String shortName) {
        Optional<ShortUrl> shortUrl = shortenerRepository.findById(id);
        shortUrl.ifPresent(url -> {
            url.setHash(shortName);
            shortenerRepository.save(url);
        });
        return shortName;
    }
}
