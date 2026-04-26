package com.example.demo;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.google.api.services.drive.Drive;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PdfService {

    // 🔹 EXISTING METHOD (keep this)
    public List<String> readPdfChunks(String filePath) {
        List<String> chunks = new ArrayList<>();

        try {
            PDDocument document = PDDocument.load(new File(filePath));
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();

            chunks = splitIntoChunks(text);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return chunks;
    }

    // 🔹 NEW METHOD (Google Drive InputStream)
    public List<String> readPdfChunks(InputStream inputStream) {
        List<String> chunks = new ArrayList<>();

        try {
            PDDocument document = PDDocument.load(inputStream);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();

            chunks = splitIntoChunks(text);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return chunks;
    }

    // 🔹 COMMON CHUNK LOGIC (reuse your logic)
    private List<String> splitIntoChunks(String text) {
        List<String> chunks = new ArrayList<>();

        String[] sentences = text.split("\\. ");
        StringBuilder chunk = new StringBuilder();

        for (String sentence : sentences) {
            if (chunk.length() + sentence.length() < 300) {
                chunk.append(sentence).append(". ");
            } else {
                chunks.add(chunk.toString());
                chunk = new StringBuilder(sentence + ". ");
            }
        }

        if (chunk.length() > 0) {
            chunks.add(chunk.toString());
        }

        return chunks;
    }

    // 🔹 NEW METHOD (Download from Google Drive)
    public InputStream downloadFile(String fileId) {
        try {
            Drive driveService = DriveServiceUtil.getDriveService();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            driveService.files()
                    .get(fileId)
                    .executeMediaAndDownloadTo(outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}