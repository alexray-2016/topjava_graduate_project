package ru.alexraydev.service.user;

import ru.alexraydev.model.User;
import ru.alexraydev.service.GenericService;

public interface UserService extends GenericService<User> {
    void evictCache();
}
