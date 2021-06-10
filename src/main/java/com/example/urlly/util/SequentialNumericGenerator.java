package com.example.urlly.util;

import org.springframework.stereotype.Component;

@Component
public class SequentialNumericGenerator implements ShortNameGenerator {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALPHABET.length();

    @Override
    public String encode(long number) {
        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            sb.append(ALPHABET.charAt((int)(number % BASE)));
            number /= BASE;
        }
        return sb.reverse().toString();
    }

    @Override
    public long decode(String shortName) {
        long number = 0L;
        for (int i = 0; i < shortName.length(); i++)
            number = number * BASE + ALPHABET.indexOf(shortName.charAt(i));
        return number;
    }

}
