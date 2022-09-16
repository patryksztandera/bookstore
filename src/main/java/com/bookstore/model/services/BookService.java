package com.bookstore.model.services;

import com.bookstore.exceptions.BadRequestException;
import com.bookstore.model.entities.BookEntity;
import com.bookstore.model.repositories.BookRepository;
import com.bookstore.model.responses.BookRestModel;
import org.json.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
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
        return new BookEntity(
                model.getIsbn(),
                model.getTitle(),
                model.getPrice(),
                model.getQuantity(),
                model.getDescription(),
                model.getPublicationDate(),
                model.getAuthor(),
                model.getGenre(),
                model.getPublisher());
    }

    public void deleteById(Long id) {
        try {
            bookRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BadRequestException("No book with id " + id + " exists");
        }
    }

    public List<BookRestModel> updateMany(List<BookRestModel> bookRestModels) {
        List<Long> ids = bookRestModels
                .stream()
                .map(BookRestModel::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        validateIds(bookRestModels, ids);
        List<BookEntity> storedEntities = bookRepository.findAllById(ids);

        bookRestModels.sort(Comparator.comparing(BookRestModel::getId));
        storedEntities.sort(Comparator.comparing(BookEntity::getId));

        List<BookRestModel> updatedBooks = new ArrayList<>();
        for (int i = 0; i < bookRestModels.size(); i++) {
            updatedBooks.add(updateEntity(bookRestModels.get(i), storedEntities.get(i)));
        }
        return updatedBooks;
    }

    public BookRestModel updateOne(Long id, BookRestModel bookRestModel) {
        if (bookRestModel.getId() != null) {
            throw new BadRequestException("ID is not allowed in the body");
        }
        try {
            BookEntity entity = bookRepository.getReferenceById(id);
            return updateEntity(bookRestModel, entity);
        } catch (EntityNotFoundException e) {
            throw new BadRequestException("Unable to find book with id " + id);
        }
    }

    private BookRestModel updateEntity(BookRestModel bookRestModel, BookEntity entity) {
        if (bookRestModel.getQuantity() != null) {
            entity.setQuantity(bookRestModel.getQuantity());
        }
        if (bookRestModel.getPrice() != null) {
            entity.setPrice(bookRestModel.getPrice());
        }
        if (bookRestModel.getDescription() != null) {
            entity.setDescription(bookRestModel.getDescription());
        }
        if (bookRestModel.getGenre() != null) {
            entity.setGenre(bookRestModel.getGenre());
        }
        if (bookRestModel.getPublicationDate() != null) {
            entity.setPublicationDate(bookRestModel.getPublicationDate());
        }
        if (bookRestModel.getAuthor() != null) {
            entity.setAuthor(bookRestModel.getAuthor());
        }
        if (bookRestModel.getTitle() != null) {
            entity.setTitle(bookRestModel.getTitle());
        }
        if (bookRestModel.getIsbn() != null) {
            entity.setIsbn(bookRestModel.getIsbn());
        }
        if (bookRestModel.getPublisher() != null) {
            entity.setPublisher(bookRestModel.getPublisher());
        }
        return new BookRestModel(bookRepository.save(entity));
    }

    private void validateIds(List<BookRestModel> bookRestModels, List<Long> ids) {
        if (bookRestModels.size() != ids.size()) {
            BookRestModel bookRestModelWithoutId = bookRestModels
                    .stream()
                    .filter(bookRestModel -> bookRestModel.getId() == null)
                    .findFirst()
                    .get();
            throw new BadRequestException("Missing required attribute id in: " +
                    new JSONObject(bookRestModelWithoutId));
        }
    }
}
