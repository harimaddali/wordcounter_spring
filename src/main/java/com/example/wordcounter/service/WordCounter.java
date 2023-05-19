package com.example.wordcounter.service;

import com.example.wordcounter.service.ExTranslator;
import com.example.wordcounter.service.Translator;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class WordCounter {
    private final Map<String, Integer> wordCounts;
    private final Translator translator;

    public WordCounter(ExTranslator translator) {
        this.translator = translator;
        this.wordCounts = new ConcurrentHashMap<>();
    }

    public void addWords(String... words) {
        if (!Objects.isNull(words)) {
            Stream.of(words)
                    .filter(this::isNotAlphabetic)
                    .map(translator::translate)
                    .forEach(word -> wordCounts.merge(word, 1, Integer::sum));
        }
    }

    public int getWordCount(String word) {
        if (null != word) {
            return wordCounts.getOrDefault(word, 0);
        }
        return 0;
    }

    public int getCount() {
        return wordCounts.size();
    }

    private boolean isNotAlphabetic(String word) {
        return !word.matches(".*[^a-zA-Z].*");
    }

}
