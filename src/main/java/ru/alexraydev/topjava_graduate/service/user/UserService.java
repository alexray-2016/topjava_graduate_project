package ru.alexraydev.topjava_graduate.service.user;

import ru.alexraydev.topjava_graduate.model.User;
import ru.alexraydev.topjava_graduate.service.GenericService;
import ru.alexraydev.topjava_graduate.util.exception.NotFoundException;

public interface UserService extends GenericService<User> {
    void evictCache();

    User getByEmail(String email) throws NotFoundException;
}
