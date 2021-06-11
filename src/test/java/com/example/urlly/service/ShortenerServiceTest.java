package com.example.urlly.service;

import com.example.urlly.entity.ShortUrl;
import com.example.urlly.repository.ShortenerRepository;
import com.example.urlly.util.SequentialNumericGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ShortenerServiceTest {

    private ShortenerService shortenerService;

    @Mock
    private ShortenerRepository shortenerRepository;

    @Mock
    private SequentialNumericGenerator sequentialNumericGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        shortenerService = new ShortenerService(sequentialNumericGenerator, shortenerRepository);
    }

    @Test
    void given_validShortName_return_url() {
        String shortName = "b";
        Long id = 1L;
        String expected = "http://www.google.com";

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(id);
        shortUrl.setHash(shortName);
        shortUrl.setUrl(expected);

        Mockito.when(shortenerRepository.findByHash(shortName)).thenReturn(Optional.of(shortUrl));

        String result = shortenerService.getUrl(shortName, "");

        assertTrue(expected.equals(result));
    }

    @Test
    void given_invalidShortName_throw_exception() {
        String shortName = "x";

        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            shortenerService.getUrl(shortName, "");
        });

        assertTrue(e.getMessage().equals("Short name is invalid"));
    }

    @Test
    void given_url_create_and_return_shortName() {
        String url = "http://www.google.com";
        String expected = "b";
        Long id = 1L;

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(id);
        shortUrl.setHash(expected);
        shortUrl.setUrl(url);

        Mockito.when(sequentialNumericGenerator.encode(id)).thenReturn(expected);

        Mockito.when(shortenerRepository.save(any())).thenReturn(shortUrl);

        String result = shortenerService.getShortName(url);

        verify(sequentialNumericGenerator, times(1)).encode(id);
        //verify(shortenerRepository, times(2)).save(any()); // Not picking up the 2nd call inside the lambda

        assertTrue(expected.equals(result));

    }
}
