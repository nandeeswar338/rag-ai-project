package com.example.demo;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/ask")
    public Map<String, Object> ask(@RequestBody Map<String, String> request) {

        String question = request.get("query");

        GeminiService geminiService = new GeminiService();
        PdfService pdfService = new PdfService();
        EmbeddingService embeddingService = new EmbeddingService();
        SimilarityService similarityService = new SimilarityService();

        // ✅ GOOGLE DRIVE FILE ID (your file)
        String fileId = "1HwyYHWAuZ3Ftstm3a0y6pZeaZx0kRhKG";

        String filePath = "";

        try {
            // ✅ Download file from Google Drive
            filePath = DriveDownloader.downloadFile(fileId);
        } catch (Exception e) {
            throw new RuntimeException("Error downloading file from Drive: " + e.getMessage());
        }

        // ✅ Read PDF from downloaded file
        List<String> chunks = pdfService.readPdfChunks(filePath);

        List<Double> questionVec = embeddingService.getEmbedding(question);

        List<Map.Entry<String, Double>> scoredChunks = new ArrayList<>();

        for (String chunk : chunks) {
            List<Double> chunkVec = embeddingService.getEmbedding(chunk);
            double score = similarityService.cosineSimilarity(questionVec, chunkVec);
            scoredChunks.add(new AbstractMap.SimpleEntry<>(chunk, score));
        }

        // Sort by similarity
        scoredChunks.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        List<Map<String, Object>> topResults = new ArrayList<>();

        for (int i = 0; i < Math.min(3, scoredChunks.size()); i++) {
            String chunk = scoredChunks.get(i).getKey();
            double score = scoredChunks.get(i).getValue();

            chunk = cleanText(chunk);

            if (chunk.length() > 200) {
                chunk = chunk.substring(0, 200) + "...";
            }

            chunk = highlight(chunk, question);

            Map<String, Object> result = new HashMap<>();
            result.put("text", chunk);
            result.put("score", score);
            result.put("source", "resume.pdf");

            topResults.add(result);
        }

        Map<String, Object> response = new HashMap<>();
        StringBuilder context = new StringBuilder();

        for (Map<String, Object> r : topResults) {
            context.append(r.get("text")).append("\n");
        }

        String finalAnswer = "Top matching results fetched successfully.";

        response.put("answer", finalAnswer);
        response.put("sources", List.of("resume.pdf"));
        response.put("results", topResults);

        return response;
    }

    private String cleanText(String text) {
        return text.replace("\r\n", " ")
                   .replace("\n", " ")
                   .replace("•", " ")
                   .replaceAll("\\s+", " ")
                   .trim();
    }

    private String highlight(String text, String keyword) {
        return text.replaceFirst("(?i)" + keyword, "<b>" + keyword + "</b>");
    }
}