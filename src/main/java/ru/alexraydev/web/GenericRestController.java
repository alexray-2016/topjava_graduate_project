package ru.alexraydev.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.alexraydev.util.exception.NotFoundException;

import java.util.List;

public interface GenericRestController<T> {
    ResponseEntity<T> save(T entity);

    ResponseEntity<T> update(int id, T entity) throws NotFoundException;

    ResponseEntity<T> getById(int id) throws NotFoundException;

    ResponseEntity<T> delete(int id) throws NotFoundException;

    ResponseEntity<List<T>> getAll();
}
