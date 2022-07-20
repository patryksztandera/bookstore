package com.bookstore.model.services;

import com.bookstore.model.entities.BookEntity;
import com.bookstore.model.entities.OrderEntity;
import com.bookstore.model.repositories.BookRepository;
import com.bookstore.model.repositories.OrderRepository;
import com.bookstore.model.responses.BookRestModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookRestModel> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(BookRestModel::new)
                .collect(Collectors.toList());
    }

    public BookRestModel getById(final Long id) {
        return new BookRestModel(bookRepository.getReferenceById(id));
    }

    public BookRestModel add(BookRestModel bookRestModel) {
        return new BookRestModel(bookRepository.save(mapRestModel(bookRestModel)));
    }

    public List<BookRestModel> addAll(List<BookRestModel> BookRestModels) {
        Set<BookEntity> entities = BookRestModels
                .stream()
                .map(this::mapRestModel).collect(Collectors.toSet());
        List<BookEntity> savedEntities = bookRepository
                .saveAll(entities);
        return savedEntities
                .stream()
                .map(BookRestModel::new)
                .collect(Collectors.toList());
    }

    private BookEntity mapRestModel(final BookRestModel model) {
        return new BookEntity(model.getIsbn(), model.getTitle(), model.getPrice(), model.getQuantity(), model.getDescription(), model.getPublicationDate(), model.getAuthor(), model.getGenre(), model.getPublisher());
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public BookRestModel update(BookRestModel book) {
        BookEntity entity = bookRepository.getReferenceById(book.getId());
        entity.setQuantity(book.getQuantity());
        entity.setPrice(book.getPrice());
        entity.setDescription(book.getDescription());
        entity.setGenre(book.getGenre());
        return new BookRestModel(bookRepository.save(entity));
    }
}
