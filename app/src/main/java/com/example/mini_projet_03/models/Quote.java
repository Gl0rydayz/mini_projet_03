package com.example.mini_projet_03.models;

public class Quote {
    private String quote;
    private String author;

    //region Getters and Setters
    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    //endregion

    public Quote(String quote, String author) {
        this.quote = quote;
        this.author = author;
    }
}
