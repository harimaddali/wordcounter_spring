// Service implementation class
package com.example.wordcounter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class WordCounterServiceImpl implements WordCounterService {
    private final Map<String, Integer> wordCountCache;
    private final Translator translator;
    private final ReentrantLock lock;

    @Autowired
    public WordCounterServiceImpl(Translator translator) {
        this.wordCountCache = new ConcurrentHashMap<>();
        this.translator = translator;
        this.lock = new ReentrantLock();
    }

    @Override
    public void addWords(String[] words) {
        for (String word : words) {
            if (isAlphabetic(word)) {
                String englishWord = translator.translate(word);
                lock.lock();
                try {
                    wordCountCache.compute(englishWord, (k, v) -> (v == null) ? 1 : v + 1);
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @Override
    public int getCount(String word) {
        String englishWord = translator.translate(word);
        return wordCountCache.getOrDefault(englishWord, 0);
    }

    private boolean isAlphabetic(String word) {
        return !word.matches(".*[^a-zA-Z].*");
    }
}
