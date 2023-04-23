package com.example.penman.holderClasses;

import android.graphics.Bitmap;

public class LibraryBook {

    String document_id;
    private String author;
    private String title;
    private String edition;
    private String category;

    private Bitmap book_img;

    public LibraryBook(String author, String title, String edition, String category, Bitmap book_img, String document_id) {
        this.author = author;
        this.title = title;
        this.edition = edition;
        this.category = category;
        this.book_img = book_img;
        this.document_id = document_id;
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

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public Bitmap getBook_img() {
        return book_img;
    }

    public void setBook_img(Bitmap book_img) {
        this.book_img = book_img;
    }

    public String getDocument_id() {
        return document_id;
    }

    public void setDocument_id(String document_id) {
        this.document_id = document_id;
    }
}
