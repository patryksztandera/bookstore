package com.bookstore.model.entities;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private ZonedDateTime timestamp;

    @Column
    private Boolean fulfilled;

    @ManyToMany
    @JoinTable(
            name = "book_orders",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<BookEntity> bookEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private ClientEntity clientEntity;

    public OrderEntity(ZonedDateTime timestamp, Boolean fulfilled, List<BookEntity> bookEntities, ClientEntity clientEntity) {
        this.timestamp = timestamp;
        this.fulfilled = fulfilled;
        this.bookEntities = bookEntities;
        this.clientEntity = clientEntity;
    }

    public OrderEntity() {
    }

    public Long getId() {
        return id;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public Boolean getFulfilled() {
        return fulfilled;
    }

    public List<BookEntity> getBookEntities() {
        return bookEntities;
    }

    public ClientEntity getClientEntity() {
        return clientEntity;
    }
}
