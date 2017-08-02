package ru.alexraydev.service.user;

import ru.alexraydev.model.User;
import ru.alexraydev.service.GenericService;
import ru.alexraydev.util.exception.NotFoundException;

public interface UserService extends GenericService<User> {
    void evictCache();

    User getByEmail(String email) throws NotFoundException;
}
