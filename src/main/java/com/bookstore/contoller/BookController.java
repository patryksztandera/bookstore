package com.bookstore.contoller;

import com.bookstore.model.responses.BookRestModel;
import com.bookstore.model.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<BookRestModel>> listOfAllBooks(Pageable pageable) {
        final Page<BookRestModel> clients = bookService.getMany(pageable);

        return ResponseEntity.ok(clients);
    }

    @GetMapping("{id}")
    public ResponseEntity<BookRestModel> getById(@PathVariable final Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @PostMapping( value="/add",
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookRestModel> addBook(@RequestBody final BookRestModel book) {
        return ResponseEntity.ok(bookService.add(book));
    }

    @PatchMapping( value="{id}",
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookRestModel> updateBook(@PathVariable final Long id,
                                                    @RequestBody final BookRestModel book) {
        return ResponseEntity.ok(bookService.updateOne(id, book));
    }

    @PatchMapping(
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookRestModel>> updateBooks(@RequestBody final List<BookRestModel> books) {
        return ResponseEntity.ok(bookService.updateMany(books));
    }

    @PostMapping(
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
