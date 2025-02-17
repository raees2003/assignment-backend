package com.IMS.dto;
import java.util.List;

public class AIResponse {
        private String title;
        private String author;
        private String description;
        private String aiGeneratedSummary;

        public AIResponse(String title, String author, String description, String aiGeneratedSummary) {
            this.title = title;
            this.author = author;
            this.description = description;
            this.aiGeneratedSummary = aiGeneratedSummary;
        }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAiGeneratedSummary() {
        return aiGeneratedSummary;
    }

    public void setAiGeneratedSummary(String aiGeneratedSummary) {
        this.aiGeneratedSummary = aiGeneratedSummary;
    }
}
