package com.nsma.popularmovies.Models;

public class Review {

    String author,content,rating;

    public Review() {

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Review(String author, String content, String rating) {
        this.author = author;
        this.content = content;
        this.rating = rating;
    }
}
