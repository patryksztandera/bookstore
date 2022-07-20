package com.bookstore.model.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name="book_generator", sequenceName = "book_seq", initialValue = 10)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column
    private  String isbn;

    @Column
    private  String title;

    @Column
    private  Double price;

    @Column
    private  Integer quantity;

    @Column
    private  String description;

    @Column(name = "publication_date")
    private  String publicationDate;

    private  String author;

    private  String genre;

    private  String publisher;

    @ManyToMany(mappedBy = "bookEntities")
    private  Set<OrderEntity> orders;

    public BookEntity(String isbn, String title, Double price, Integer quantity, String description, String publicationDate, String author, String genre, String publisher) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.publicationDate = publicationDate;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.orders = new HashSet<>();
    }

    public BookEntity() {
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

    public String getGenre() {
        return genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public Set<OrderEntity> getOrders() {
        return orders;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
