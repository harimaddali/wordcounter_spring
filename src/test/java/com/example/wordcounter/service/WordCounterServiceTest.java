package com.example.wordcounter.service;

import com.example.wordcounter.service.Translator;
import com.example.wordcounter.service.WordCounterService;
import com.example.wordcounter.service.WordCounterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordCounterServiceTest {
    @Mock
    private Translator translator;

    private WordCounterService wordCounterService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        wordCounterService = new WordCounterServiceImpl(translator);
    }

    @Test
    public void testAddWords_Positive() {
        String[] words = {"flower", "flor", "blume"};
        Mockito.when(translator.translate(ArgumentMatchers.anyString())).thenReturn("flower");

        wordCounterService.addWords(words);

        int count = wordCounterService.getCount("flower");
        assertEquals(3, count);

        Mockito.verify(translator, Mockito.times(3)).translate(ArgumentMatchers.anyString());
    }

    @Test
    public void testAddWords_Negative() {
        String[] words = {"flower", "1234", "123!@#"};
        Mockito.when(translator.translate(ArgumentMatchers.anyString())).thenReturn("flower");

        wordCounterService.addWords(words);

        int count = wordCounterService.getCount("flower");
        assertEquals(1, count);

        Mockito.verify(translator, Mockito.times(1)).translate(ArgumentMatchers.anyString());
    }
}
