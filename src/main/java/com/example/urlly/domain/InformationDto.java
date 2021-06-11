package com.example.urlly.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InformationDto {

    private String shortName;
    private String url;
    private LocalDateTime createdAt;
    private Long numberOfHits;
    private String lastReferrerIp;

}
