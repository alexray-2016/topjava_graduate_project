package ru.alexraydev.repository.user;

import ru.alexraydev.model.User;
import ru.alexraydev.repository.GenericRepository;

public interface UserRepository extends GenericRepository<User>{

    //null if not found
    User getByEmail(String email);
}
