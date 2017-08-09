package ru.alexraydev.topjava_graduate.repository.user;

import ru.alexraydev.topjava_graduate.model.User;
import ru.alexraydev.topjava_graduate.repository.GenericRepository;

public interface UserRepository extends GenericRepository<User>{

    //null if not found
    User getByEmail(String email);
}
