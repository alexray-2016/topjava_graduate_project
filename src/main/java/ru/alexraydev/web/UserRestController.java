package ru.alexraydev.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.alexraydev.AuthorizedUser;
import ru.alexraydev.model.Restaurant;
import ru.alexraydev.model.UserVote;
import ru.alexraydev.service.restaurant.RestaurantService;
import ru.alexraydev.service.uservote.UserVoteService;
import ru.alexraydev.util.ValidationUtil;
import ru.alexraydev.util.exception.NotFoundException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = UserRestController.REST_URL)
public class UserRestController {

    static final String REST_URL = "rest/profile";

    @Autowired
    private UserVoteService userVoteService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping(value = "/vote", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVote> save(@Valid @RequestBody UserVote entity) {
        ValidationUtil.checkNew(entity);

        UserVote created = userVoteService.save(entity, 2/*AuthorizedUser.id()*/);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/vote")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/vote", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVote> update(@Valid @RequestBody UserVote entity) throws NotFoundException {
        ValidationUtil.checkDateConsistent(entity);
        ValidationUtil.checkTimeConsistent(entity);
        UserVote userVote = userVoteService.getTodaysVote(2/*AuthorizedUser.id()*/);
        ValidationUtil.checkIdConsistent(entity, userVote.getId());
        userVote.setDate(entity.getDate());
        userVote.setTime(entity.getTime());
        userVote.setChosenRestaurantId(entity.getChosenRestaurantId());
        userVote.setUser(entity.getUser());

        userVoteService.update(userVote, 2/*AuthorizedUser.id()*/);
        return new ResponseEntity<>(userVote, HttpStatus.OK);
    }

    @DeleteMapping(value = "/vote")
    public ResponseEntity<UserVote> delete() throws NotFoundException {
        UserVote userVote = userVoteService.getTodaysVote(2/*AuthorizedUser.id()*/);
        ValidationUtil.checkTimeConsistent(userVote);
        userVoteService.delete(userVote.getId(), AuthorizedUser.id());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/vote", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVote> getTodaysVote() throws NotFoundException {
        UserVote userVote = userVoteService.getTodaysVote(2/*AuthorizedUser.id()*/);
        return new ResponseEntity<>(userVote, HttpStatus.OK);
    }

    @GetMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return new ResponseEntity<>(restaurantService.getAll(), HttpStatus.OK);
    }


}
