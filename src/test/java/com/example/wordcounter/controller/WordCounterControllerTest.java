package com.example.wordcounter.controller;

import com.example.wordcounter.service.WordCounterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class WordCounterControllerTest {
    private MockMvc mockMvc;

    @Mock
    private WordCounterService wordCounterService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new WordCounterController(wordCounterService)).build();
    }

    @Test
    public void testAddWords() throws Exception {
        mockMvc.perform(post("/word/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"flower\", \"flor\", \"blume\"]"))
                .andExpect(status().isOk());

        verify(wordCounterService, times(3)).addWords(any());
    }

    @Test
    public void testGetCount() throws Exception {
        when(wordCounterService.getCount("flower")).thenReturn(3);

        MvcResult result = mockMvc.perform(get("/word/count/flower"))
                .andExpect(status().isOk())
                .andReturn();

        int count = Integer.parseInt(result.getResponse().getContentAsString());
        assertEquals(3, count);

        verify(wordCounterService, times(1)).getCount("flower");
    }
}
