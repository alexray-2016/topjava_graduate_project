package ru.alexraydev.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.alexraydev.AuthorizedUser;
import ru.alexraydev.model.UserVote;
import ru.alexraydev.service.restaurant.RestaurantService;
import ru.alexraydev.service.uservote.UserVoteService;
import ru.alexraydev.util.ValidationUtil;
import ru.alexraydev.util.exception.NotFoundException;

import java.net.URI;

@RestController
@RequestMapping(value = UserRestController.REST_URL)
public class UserRestController {

    static final String REST_URL = "rest/profile";

    @Autowired
    private UserVoteService userVoteService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping(value = "/vote", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVote> save(@RequestBody UserVote entity) {
        ValidationUtil.checkNew(entity);

        UserVote created = userVoteService.save(entity, AuthorizedUser.getId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/vote")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/vote", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVote> update(@RequestBody UserVote entity) throws NotFoundException {
        ValidationUtil.checkDateConsistent(entity);
        UserVote userVote = userVoteService.getTodaysVote(AuthorizedUser.getId());
        ValidationUtil.checkIdConsistent(entity, userVote.getId());
        userVote.setDate(entity.getDate());
        userVote.setTime(entity.getTime());
        userVote.setChosenRestaurantId(entity.getChosenRestaurantId());
        userVote.setUser(entity.getUser());

        userVoteService.update(userVote, AuthorizedUser.getId());
        return new ResponseEntity<>(userVote, HttpStatus.OK);
    }

    @GetMapping(value = "/vote", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVote> getTodaysVote() throws NotFoundException {
        UserVote userVote = userVoteService.getTodaysVote(AuthorizedUser.getId());
        return new ResponseEntity<>(userVote, HttpStatus.OK);
    }


}