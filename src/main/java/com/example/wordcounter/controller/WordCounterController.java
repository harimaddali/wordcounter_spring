// Controller class
package com.example.wordcounter.controller;

import com.example.wordcounter.service.WordCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/word")
public class WordCounterController {
    private final WordCounterService wordCounterService;

    @Autowired
    public WordCounterController(WordCounterService wordCounterService) {
        this.wordCounterService = wordCounterService;
    }

    @PostMapping("/add")
    public void addWords(@RequestBody String[] words) {
        wordCounterService.addWords(words);
    }

    @GetMapping("/count/{word}")
    public int getCount(@PathVariable String word) {
        return wordCounterService.getCount(word);
    }
}
