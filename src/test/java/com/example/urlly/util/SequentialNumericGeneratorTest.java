package com.example.urlly.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SequentialNumericGeneratorTest {

    private SequentialNumericGenerator sequentialNumericGenerator;

    @BeforeEach
    void setUp() {
        sequentialNumericGenerator = new SequentialNumericGenerator();
    }

    @ParameterizedTest(name = "Long numeric {0} encodes to ShortName {1}")
    @CsvSource({"1, b", "61, 9", "62, ba", "124, ca"})
    void encodeTest(Long value, String result) {
        assertTrue(sequentialNumericGenerator.encode(value).equals(result));
    }

    @ParameterizedTest(name = "ShortName {1} decodes to Long number {0}")
    @CsvSource({"1, b", "61, 9", "62, ba", "124, ca"})
    void decodeTest(Long result, String value) {
        assertEquals(result, sequentialNumericGenerator.decode(value));
    }
}