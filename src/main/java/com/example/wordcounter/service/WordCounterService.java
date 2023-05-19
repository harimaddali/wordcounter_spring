// Service interface
package com.example.wordcounter.service;

public interface WordCounterService {
    void addWords(String[] words);
    int getCount(String word);
}
