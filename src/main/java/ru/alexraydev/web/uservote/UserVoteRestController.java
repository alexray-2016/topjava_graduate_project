package ru.alexraydev.web.uservote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.alexraydev.model.User;
import ru.alexraydev.model.UserVote;
import ru.alexraydev.service.uservote.UserVoteService;
import ru.alexraydev.util.ValidationUtil;
import ru.alexraydev.util.exception.NotFoundException;
import ru.alexraydev.web.GenericRestController;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(UserVoteRestController.REST_URL)
public class UserVoteRestController implements GenericRestController<UserVote>{

    static final String REST_URL = "/rest/uservotes";

    @Autowired
    private UserVoteService userVoteService;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVote> save(@RequestBody UserVote entity) {
        ValidationUtil.checkNew(entity);

        UserVote created = userVoteService.save(entity);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVote> update(int id, UserVote entity) throws NotFoundException {
        ValidationUtil.checkIdConsistent(entity, id);

        UserVote userVote = userVoteService.getById(id);

        userVote.setDateTime(entity.getDateTime());
        userVote.setChosenRestaurantId(entity.getChosenRestaurantId());
        userVote.setUser(entity.getUser());

        userVoteService.update(userVote);
        return new ResponseEntity<>(userVote, HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVote> getById(int id) throws NotFoundException {
        UserVote userVote = userVoteService.getById(id);
        if (userVote == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(userVote, HttpStatus.OK);
        }
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserVote> delete(int id) throws NotFoundException {
        UserVote userVote = userVoteService.getById(id);
        if (userVote == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userVoteService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserVote>> getAll() {
        return new ResponseEntity<List<UserVote>>(userVoteService.getAll(), HttpStatus.OK);
    }
}
