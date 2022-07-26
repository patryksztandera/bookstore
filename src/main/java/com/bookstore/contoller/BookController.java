package com.bookstore.contoller;

import com.bookstore.model.responses.BookRestModel;
import com.bookstore.model.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookRestModel>> listOfAllBooks() {
        final List<BookRestModel> clients = bookService.getAll();

        return ResponseEntity.ok(clients);
    }

    @GetMapping("{id}")
    public ResponseEntity<BookRestModel> getById(@PathVariable final Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @PostMapping(
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookRestModel> addBook(@RequestBody final BookRestModel book) {
        return ResponseEntity.ok(bookService.add(book));
    }

    @PatchMapping(
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookRestModel> updateBook(@RequestBody final BookRestModel book) {
        return ResponseEntity.ok(bookService.update(book));
    }

    @PostMapping( value="/multiple",
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookRestModel>> addBooks(@RequestBody final List<BookRestModel> books) {
        return ResponseEntity.ok(bookService.addAll(books));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
