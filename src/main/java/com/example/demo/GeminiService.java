package com.example.demo;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.*;

import java.util.*;

public class GeminiService {

    private static final String API_KEY = "";
    private static final String URL =
    	    "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash-latest:generateContent?key=" + API_KEY;

    public String ask(String question, String context) {

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = new HashMap<>();

        Map<String, Object> part = new HashMap<>();
        part.put("text", "Answer based on context:\n\n" + context + "\n\nQuestion: " + question);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(part));

        body.put("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            String response = restTemplate.postForObject(URL, request, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            return root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

        } catch (Exception e) {
            e.printStackTrace();
            return "Gemini API Error: " + e.getMessage();
        }
    }
}