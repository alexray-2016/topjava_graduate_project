package ru.alexraydev.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.alexraydev.model.Restaurant;
import ru.alexraydev.model.User;
import ru.alexraydev.repository.restaurant.RestaurantRepository;
import ru.alexraydev.repository.user.UserRepository;
import ru.alexraydev.util.exception.NotFoundException;

import java.util.List;

import static ru.alexraydev.util.ValidationUtil.*;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public User save(User entity) {
        return userRepository.save(entity);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public User update(User entity) throws NotFoundException {
        return checkNotFoundWithId(userRepository.save(entity), entity.getId());
    }

    @Override
    public User getById(int id) throws NotFoundException {
        return checkNotFoundWithId(userRepository.getById(id), id);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(userRepository.delete(id), id);
    }

    @Cacheable("users")
    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void evictCache() {
    }
}
