package ru.alexraydev.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.alexraydev.AuthorizedUser;
import ru.alexraydev.model.User;
import ru.alexraydev.repository.user.UserRepository;
import ru.alexraydev.util.exception.NotFoundException;

import java.util.List;

import static ru.alexraydev.util.ValidationUtil.*;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public User save(User entity) {
        Assert.notNull(entity, "user must not be null");
        return userRepository.save(entity);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public User update(User entity) throws NotFoundException {
        Assert.notNull(entity, "user must not be null");
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

    @Override
    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(userRepository.getByEmail(email), "email=" + email);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}