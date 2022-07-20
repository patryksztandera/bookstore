package com.bookstore.model.responses;

import com.bookstore.model.entities.BookEntity;

public class BookRestModel {

    private Long id;
    private String isbn;
    private String title;
    private Double price;
    private Integer quantity;
    private String description;
    private String publicationDate;
    private String author;
    private String publisher;
    private String genre;

    public BookRestModel(BookEntity entity) {
        this.id = entity.getId();
        this.isbn = entity.getIsbn();
        this.title = entity.getTitle();
        this.price = entity.getPrice();
        this.quantity = entity.getQuantity();
        this.description = entity.getDescription();
        this.publicationDate = entity.getPublicationDate();
        this.author = entity.getAuthor();
        this.publisher = entity.getPublisher();
        this.genre = entity.getGenre();
    }

    public BookRestModel() {
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getGenre() {
        return genre;
    }
}
