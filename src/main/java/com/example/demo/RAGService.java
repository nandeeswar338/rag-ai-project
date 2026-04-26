package com.example.demo;

import java.util.*;

public class RAGService {

    private EmbeddingService embeddingService = new EmbeddingService();
    private SimilarityService similarityService = new SimilarityService();
    private VectorStore store;

    public RAGService(VectorStore store) {
        this.store = store;
    }

    public String ask(String question) {
        List<Double> queryEmbedding = embeddingService.getEmbedding(question);

        double bestScore = -1;
        String bestText = "";

        for (DocumentChunk chunk : store.getAll()) {
            double score = similarityService.cosineSimilarity(
                    queryEmbedding,
                    chunk.getEmbedding()
            );

            if (score > bestScore) {
                bestScore = score;
                bestText = chunk.getText();
            }
        }

        return "Answer (from document): " + bestText;
    }
}
