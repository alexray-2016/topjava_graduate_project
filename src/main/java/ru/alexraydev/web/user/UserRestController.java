package ru.alexraydev.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.alexraydev.model.User;
import ru.alexraydev.service.user.UserService;
import ru.alexraydev.util.ValidationUtil;
import ru.alexraydev.util.exception.NotFoundException;
import ru.alexraydev.web.GenericRestController;

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(UserRestController.REST_URL)
public class UserRestController implements GenericRestController<User> {
    static final String REST_URL = "/rest/users";

    @Autowired
    private UserService userService;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> save(@RequestBody User entity) {

        ValidationUtil.checkNew(entity);

        User created = userService.save(entity);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> update(@PathVariable("id") int id, @RequestBody User entity) throws NotFoundException {
        ValidationUtil.checkIdConsistent(entity, id);

        User user = userService.getById(id);

        user.setName(entity.getName());
        user.setAdmin(entity.isAdmin());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setVotes(entity.getVotes());

        userService.update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getById(@PathVariable("id") int id) throws NotFoundException {
        User user = userService.getById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> delete(@PathVariable("id") int id) throws NotFoundException {
        User user = userService.getById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<List<User>>(userService.getAll(), HttpStatus.OK);
    }
}
