package com.example.demo;

import java.util.*;

public class VectorStore {

    private List<DocumentChunk> store = new ArrayList<>();

    public void addChunk(DocumentChunk chunk) {
        store.add(chunk);
    }

    public List<DocumentChunk> getAll() {
        return store;
    }
}
