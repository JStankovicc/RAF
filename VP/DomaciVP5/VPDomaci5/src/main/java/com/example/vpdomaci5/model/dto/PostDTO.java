package com.example.vpdomaci5.model.dto;

public class PostDTO {
    private String author;
    private String title;
    private String content;

    public PostDTO(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public PostDTO() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
