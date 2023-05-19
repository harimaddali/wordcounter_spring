package com.example.wordcounter.util;

import com.example.wordcounter.service.ExTranslator;
import com.example.wordcounter.service.Translator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TranslatorTest {
    private Translator translator;

    @BeforeEach
    public void setUp() {
        translator = new ExTranslator();
    }

    @Test
    public void testTranslate_Positive() {
        String translatedWord = translator.translate("flor");
        assertEquals("flower", translatedWord);
    }

    @Test
    public void testTranslate_Negative() {
        String translatedWord = translator.translate("1234");
        assertEquals("1234", translatedWord);
    }
}
