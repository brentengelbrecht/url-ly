package com.example.urlly.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ShortenerRequest {

    @NotNull
    private String url;

}
