package com.bookstore.model.services;

import com.bookstore.exceptions.BadRequestException;
import com.bookstore.model.entities.OrderEntity;
import com.bookstore.model.repositories.BookRepository;
import com.bookstore.model.repositories.ClientRepository;
import com.bookstore.model.repositories.OrderRepository;
import com.bookstore.model.responses.BookRestModel;
import com.bookstore.model.responses.OrderRestModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final ClientRepository clientRepository;

    public OrderService(OrderRepository orderRepository, BookRepository bookRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.clientRepository = clientRepository;
    }

    @Deprecated
    public List<OrderRestModel> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(OrderRestModel::new)
                .collect(Collectors.toList());
    }

    public PageImpl<OrderRestModel> getAll(Pageable pageable) {
        return new PageImpl<>(orderRepository
                .findAll(pageable)
                .stream()
                .map(OrderRestModel::new)
                .collect(Collectors.toList()));
    }

    public OrderRestModel getById(final Long id) {
        return new OrderRestModel(orderRepository.getReferenceById(id));
    }

    public OrderRestModel add(OrderRestModel orderRestModel) {
        return new OrderRestModel(orderRepository.save(mapRestModel(orderRestModel)));
    }

    public OrderRestModel add(JSONObject order) {
        return new OrderRestModel(orderRepository.save(createOrder(order)));
    }

    public List<OrderRestModel> addAll(List<OrderRestModel> orderRestModels) {
        Set<OrderEntity> entities = orderRestModels
                .stream()
                .map(this::mapRestModel)
                .collect(Collectors.toSet());
        List<OrderEntity> savedEntities = orderRepository
                .saveAll(entities);
        return savedEntities
                .stream()
                .map(OrderRestModel::new)
                .collect(Collectors.toList());
    }

    private OrderEntity mapRestModel(final OrderRestModel model) {
        return new OrderEntity(model.getTimestamp(), model.getFulfilled(), bookRepository.findAllById(model.getBooks().stream().map(BookRestModel::getId).collect(Collectors.toList())), clientRepository.getByEmail(model.getClient().getEmail()));
    }

    public OrderEntity createOrder(final JSONObject order) {

        if (!order.has("clientEmail")) {
            throw new BadRequestException("Missing required parameter clientID");
        }
        if (!order.has("bookIDs")) {
            throw new BadRequestException("Missing required parameter bookIDs");
        }
        if (!(order.get("bookIDs") instanceof JSONArray)) {
            throw new BadRequestException("Wrong type of bookIDs");
        }
        JSONArray bookIDsArray = (JSONArray) order.get("bookIDs");
        List<Object> objects = bookIDsArray.toList();

        return new OrderEntity(ZonedDateTime.now(), false,
                bookRepository.findAllById(objects.stream().map(this::validateID).collect(Collectors.toList())),
                clientRepository.getByEmail(validateEmail(order.get("clientEmail"))));
    }

    private String validateEmail(Object email) {
        if (!(email instanceof String)) {
            throw new BadRequestException("Wrong JSON type");
        }
        return (String) email;
    }

    private Long validateID(Object id) {
        if (!(id instanceof HashMap)) {
            throw new BadRequestException("Wrong JSON type in bookIDs");
        }
        if (!((HashMap) id).containsKey("ID")) {
            throw new BadRequestException("Missing required parameter ID");
        }
        return Long.valueOf(String.valueOf(((HashMap<?, ?>) id).get("ID")));
    }
}
