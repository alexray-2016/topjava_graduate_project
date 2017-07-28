package ru.alexraydev.service;

import ru.alexraydev.util.exception.NotFoundException;

import java.util.List;

public interface GenericService<T> {
    T save(T entity);

    T update(T entity) throws NotFoundException;

    T getById(int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    List<T> getAll();
}
