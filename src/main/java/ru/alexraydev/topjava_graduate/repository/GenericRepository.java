package ru.alexraydev.topjava_graduate.repository;

import java.util.List;

public interface GenericRepository<T> {
    T save(T entity);

    //null if not found
    T getById(int id);

    //false if not found
    boolean delete(int id);

    List<T> getAll();
}
