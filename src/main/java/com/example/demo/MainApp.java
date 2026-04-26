package com.example.demo;

import java.util.List;

public class MainApp {

    public static void main(String[] args) {

        String document = "Your company refund policy is valid for 30 days. After 30 days no refund.";

        ChunkService chunkService = new ChunkService();
        EmbeddingService embeddingService = new EmbeddingService();
        VectorStore store = new VectorStore();

        List<String> chunks = chunkService.chunkText(document);

        for (String chunk : chunks) {
            List<Double> embedding = embeddingService.getEmbedding(chunk);
            store.addChunk(new DocumentChunk(chunk, embedding));
        }

        RAGService rag = new RAGService(store);

        String answer = rag.ask("What is refund policy?");
        System.out.println(answer);
    }
}
