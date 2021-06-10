package com.example.urlly.util;

public interface ShortNameGenerator {

    String encode(long number);
    long decode(String shortName);

}
