# RAG AI Project (Spring Boot)

## 📌 Description

This project implements a Retrieval-Augmented Generation (RAG) system using Spring Boot. It processes PDF documents, extracts text, and returns relevant answers based on user queries.

## 🚀 Features

* REST API using Spring Boot
* PDF text extraction (Apache PDFBox)
* Text chunking
* Embedding generation
* Cosine similarity search
* Top result retrieval
* Google Drive file integration

## 🛠 Tech Stack

* Java 17
* Spring Boot
* Apache PDFBox
* Google Drive API

## 📡 API Endpoint

POST /ask

### Request:

{
"query": "What skills does he have?"
}

### Response:

{
"answer": "Top matching results fetched successfully.",
"sources": ["resume.pdf"],
"results": [...]
}

## 📂 Project Structure

* Controller → Handles API requests
* Service Layer → PDF, Embedding, Similarity
* Integration → Google Drive

## ⚠️ Note

LLM (Gemini) integration is optional and can be added for answer generation.

## 👨‍💻 Author

Your Name
