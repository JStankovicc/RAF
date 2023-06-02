package com.example.vpdomaci5.model.dto;

public class CommentDTO {
    private String author;
    private String comment;

    public CommentDTO(String name,  String comment) {
        this.author = name;
        this.comment = comment;
    }

    public CommentDTO() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

