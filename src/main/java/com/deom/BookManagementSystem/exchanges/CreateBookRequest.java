package com.deom.BookManagementSystem.exchanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBookRequest {
    private String title;
    private String author;
    private String genre;

    public CreateBookRequest(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public CreateBookRequest() {
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
