package com.bookstore.contoller;

import com.bookstore.model.responses.ClientRestModel;
import com.bookstore.model.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("clients")
@SuppressWarnings("unused")
public class ClientController {

    private final ClientService clientService;

    public ClientController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientRestModel>> listAllClients() {
        final List<ClientRestModel> clients = clientService.getAll();

        return ResponseEntity.ok(clients);
    }

    @GetMapping("{id}")
    public ResponseEntity<ClientRestModel> getById(@PathVariable final Long id) {
        return ResponseEntity.ok(clientService.getById(id));
    }

    @PostMapping(
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientRestModel> addClient(@RequestBody final ClientRestModel client) {
        return ResponseEntity.ok(clientService.add(client));
    }

    @PostMapping(value="/multiple",
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClientRestModel>> addClients(@RequestBody final List<ClientRestModel> clients) {
        return ResponseEntity.ok(clientService.addAll(clients));
    }
}
