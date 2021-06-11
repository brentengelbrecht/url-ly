package com.example.urlly.controller;

import com.example.urlly.domain.InformationDto;
import com.example.urlly.domain.InformationResponse;
import com.example.urlly.domain.ShortenerRequest;
import com.example.urlly.domain.ShortenerResponse;
import com.example.urlly.service.ShortenerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
public class ShortenerController {

    private final ShortenerService shortenerService;

    public ShortenerController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createShortUrl(@RequestBody ShortenerRequest request) {
        ShortenerResponse response = new ShortenerResponse();
        response.setShortName(shortenerService.getShortName(request.getUrl()));

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/{hash}", method = RequestMethod.GET)
    public ResponseEntity<?> redirectShortUrl(@PathVariable String hash, HttpServletRequest request) throws URISyntaxException {
        String shortName;
        try {
             shortName = shortenerService.getUrl(hash, request.getRemoteAddr());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Short name does not exist");
        }

        URI uri = new URI(shortName);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @RequestMapping(value = "/{hash}/info", method = RequestMethod.GET)
    public ResponseEntity<InformationResponse> getInformation(@PathVariable String hash) {
        InformationDto dto;
        try {
            dto = shortenerService.getInformation(hash);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Short name does not exist");
        }

        InformationResponse body = new InformationResponse();
        BeanUtils.copyProperties(dto, body);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

}
