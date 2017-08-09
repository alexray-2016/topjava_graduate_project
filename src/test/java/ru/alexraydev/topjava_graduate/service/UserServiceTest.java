package ru.alexraydev.topjava_graduate.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.alexraydev.topjava_graduate.model.Role;
import ru.alexraydev.topjava_graduate.model.User;
import ru.alexraydev.topjava_graduate.service.user.UserService;
import ru.alexraydev.topjava_graduate.util.exception.NotFoundException;

import javax.persistence.PersistenceException;
import java.util.*;

import static ru.alexraydev.topjava_graduate.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest{

    @Autowired
    private UserService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @Test
    public void testSave() throws Exception {
        User newUser = new User("New", "new@gmail.com", "newPass", Role.ROLE_USER);
        User created = service.save(newUser);
        newUser.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, FIRST_USER, SECOND_USER, newUser), service.getAll());
    }

    @Test(expected = PersistenceException.class)
    public void testDuplicateMailSave() throws Exception {
        service.save(new User("Duplicate", "admin@gmail.com", "adminpassword", Role.ROLE_ADMIN));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(FIRST_USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, SECOND_USER), service.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(100500);
    }

    @Test
    public void testGet() throws Exception {
        User user = service.getById(ADMIN_ID);
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.getById(100500);
    }

    @Test
    public void testGetByEmail() throws Exception {
        User user = service.getByEmail("admin@gmail.com");
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<User> all = service.getAll();
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, FIRST_USER, SECOND_USER), all);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(FIRST_USER_ID, FIRST_USER.getName(), FIRST_USER.getEmail(), FIRST_USER.getPassword(), FIRST_USER.getRoles());
        updated.setName("UpdatedName");
        updated.setPassword("changedPassword");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        service.update(updated);
        MATCHER.assertEquals(updated, service.getById(FIRST_USER_ID));
    }
}
