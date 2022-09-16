package com.bookstore.model.responses;

import com.bookstore.model.entities.OrderEntity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderRestModel {
    private Long id;
    private ZonedDateTime timestamp;
    private Boolean fulfilled;
    private List<BookRestModel> books;
    private ClientRestModel client;

    public OrderRestModel(OrderEntity entity) {
        this.id = entity.getId();
        this.timestamp = entity.getTimestamp();
        this.fulfilled = entity.getFulfilled();
        this.books = entity.getBookEntities().stream().map(BookRestModel::new).collect(Collectors.toList());
        this.client = new ClientRestModel(entity.getClientEntity());
    }

    public OrderRestModel() {
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

    public List<BookRestModel> getBooks() {
        return books;
    }

    public ClientRestModel getClient() {
        return client;
    }
}
