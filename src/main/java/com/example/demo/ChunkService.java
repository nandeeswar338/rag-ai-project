package com.example.demo;

import java.util.*;

public class ChunkService {

    public List<String> chunkText(String text) {
        int chunkSize = 200;
        List<String> chunks = new ArrayList<>();

        for (int i = 0; i < text.length(); i += chunkSize) {
            chunks.add(text.substring(i, Math.min(text.length(), i + chunkSize)));
        }

        return chunks;
    }
}
