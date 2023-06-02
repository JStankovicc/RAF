package com.example.domaciwp6.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Post {
    private int id;
    private String author;
    private String title;
    private String content;
    private String date;
    private List<Comment> comments;

    public Post(){}
    public Post(String author, String title, String content) {
        this.comments = new ArrayList<>();
        this.date = LocalDate.now().toString();
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public Post(int id, String author, String title, String content, String date){
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public Post(int id, String author, String title, String content, String date, List<Comment> comments) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.date = date;
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return Objects.equals(id, post.id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
