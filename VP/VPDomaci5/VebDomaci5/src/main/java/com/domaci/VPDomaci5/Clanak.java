package com.domaci.VPDomaci5;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Clanak {
    private Integer id;
    private String author;
    private String tittle;
    private String text;
    private List<String> comments;
    private String date;

    public Clanak() {
        this.comments = new CopyOnWriteArrayList<>();
        this.date = LocalDate.now().toString();
    }

    public Clanak(Integer id, String author, String tittle, String text) {
        this();
        this.id = id;
        this.author = author;
        this.tittle = tittle;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
