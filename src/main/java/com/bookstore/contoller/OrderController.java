package com.bookstore.contoller;

import com.bookstore.model.responses.OrderRestModel;
import com.bookstore.model.services.OrderService;
import org.json.JSONObject;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("orders")
public class OrderController {
    
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<PageImpl<OrderRestModel>> getAllOrders(Pageable pageable) {
        final PageImpl<OrderRestModel> clients = orderService.getAll(pageable);

        return ResponseEntity.ok(clients);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderRestModel> getById(@PathVariable final Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @PostMapping(
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderRestModel> addOrder(@RequestBody final String order) {
        return ResponseEntity.ok(orderService.add(new JSONObject(order)));
    }
}
