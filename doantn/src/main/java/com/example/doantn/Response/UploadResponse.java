package com.example.doantn.Response;

import java.util.List;

public class UploadResponse {
    private String message;
    private List<String> imageUrls;

    // Constructors, getters, and setters

    public UploadResponse(String message, List<String> imageUrls) {
        this.message = message;
        this.imageUrls = imageUrls;
    }

    public UploadResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}