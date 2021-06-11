package com.example.urlly.service;

import com.example.urlly.domain.InformationDto;
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

    public String getUrl(String shortName, String referrerIp) {
        Optional<ShortUrl> entity = shortenerRepository.findByHash(shortName);

        if (!entity.isPresent()) {
            throw new IllegalArgumentException("Short name is invalid");
        }

        ShortUrl shortUrl = entity.get();
        shortUrl.setNumberOfHits(shortUrl.getNumberOfHits() + 1);
        shortUrl.setLastReferrerIp(referrerIp);
        shortenerRepository.save(shortUrl);

        log.debug("URL found {}", shortUrl.getUrl());
        return shortUrl.getUrl();
    }

    public String getShortName(String url) {
        Long id = saveShortName(url).getId();
        String shortName = shortNameGenerator.encode(id);
        log.debug("Hash created {}", shortName);
        return updateShortName(id, shortName);
    }

    public InformationDto getInformation(String shortName) {
        Optional<ShortUrl> entity = shortenerRepository.findByHash(shortName);

        if (!entity.isPresent()) {
            throw new IllegalArgumentException("Short name is invalid");
        }

        ShortUrl shortUrl = entity.get();

        InformationDto dto = new InformationDto();
        dto.setShortName(shortUrl.getHash());
        dto.setUrl(shortUrl.getUrl());
        dto.setCreatedAt(shortUrl.getCreatedAt());
        dto.setLastReferrerIp(shortUrl.getLastReferrerIp());
        dto.setNumberOfHits(shortUrl.getNumberOfHits());

        return dto;
    }

    private ShortUrl saveShortName(String url) {
        return shortenerRepository.save(new ShortUrl("", url));
    }

    private String updateShortName(Long id, String shortName) {
        Optional<ShortUrl> shortUrl = shortenerRepository.findById(id);
        shortUrl.ifPresent(entity -> {
            entity.setHash(shortName);
            entity.setLastReferrerIp("");
            shortenerRepository.save(entity);
        });
        return shortName;
    }

}
