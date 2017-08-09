package ru.alexraydev.topjava_graduate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.alexraydev.topjava_graduate.AuthorizedUser;
import ru.alexraydev.topjava_graduate.model.Restaurant;
import ru.alexraydev.topjava_graduate.model.UserVote;
import ru.alexraydev.topjava_graduate.service.restaurant.RestaurantService;
import ru.alexraydev.topjava_graduate.service.uservote.UserVoteService;
import ru.alexraydev.topjava_graduate.to.UserVoteTo;
import ru.alexraydev.topjava_graduate.util.UserVoteUtil;
import ru.alexraydev.topjava_graduate.util.ValidationUtil;
import ru.alexraydev.topjava_graduate.util.exception.NotFoundException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = UserRestController.REST_URL)
public class UserRestController {

    private static final Logger LOG = LoggerFactory.getLogger(UserRestController.class);

    static final String REST_URL = "rest/profile";

    @Autowired
    private UserVoteService userVoteService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping(value = "/vote", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVoteTo> save(@Valid @RequestBody UserVoteTo userVoteTo) {
        ValidationUtil.checkNew(userVoteTo);
        ValidationUtil.checkDateConsistent(userVoteTo);
        ValidationUtil.checkTimeConsistentForSave(userVoteTo);
        UserVote entity = UserVoteUtil.createNewUserVoteFromTo(userVoteTo);

        int userId = AuthorizedUser.id();
        userVoteService.checkIfUserVoteForTodayAlreadyExists(userId);
        LOG.info("create user vote {} for user with id={}", entity, userId);
        UserVote created = userVoteService.save(entity, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/vote")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(UserVoteUtil.asToForUser(created));
    }

    @PutMapping(value = "/vote", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVoteTo> update(@Valid @RequestBody UserVoteTo userVoteTo) throws NotFoundException {
        int userId = AuthorizedUser.id();
        UserVote userVote = userVoteService.getTodaysVote(userId);
        ValidationUtil.checkDateConsistent(userVoteTo);
        ValidationUtil.checkTimeConsistentForUpdate(userVoteTo);
        ValidationUtil.checkIdConsistent(userVoteTo, userVote.getId());
        UserVote entity = UserVoteUtil.createNewUserVoteFromTo(userVoteTo);
        userVote.setDate(entity.getDate());
        userVote.setTime(entity.getTime());
        userVote.setRestaurant(entity.getRestaurant());
        userVote.setUser(entity.getUser());

        LOG.info("update {} with id={} for user with id={}", entity, entity.getId(), userId);
        userVoteService.update(userVote, userId);
        return new ResponseEntity<>(UserVoteUtil.asToForUser(userVote), HttpStatus.OK);
    }

    @DeleteMapping(value = "/vote")
    public ResponseEntity<UserVote> delete() throws NotFoundException {
        int userId = AuthorizedUser.id();
        UserVote userVote = userVoteService.getTodaysVote(userId);
        ValidationUtil.checkTimeConsistentForDelete();
        LOG.info("delete user vote {} for user with id={}", userVote, userId);
        userVoteService.delete(userVote.getId(), userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/vote", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVoteTo> getTodaysVote() throws NotFoundException {
        int userId = AuthorizedUser.id();
        LOG.info("get today's user vote for user with id={}", userId);
        UserVote userVote = userVoteService.getTodaysVote(userId);
        return new ResponseEntity<>(UserVoteUtil.asToForUser(userVote), HttpStatus.OK);
    }

    @GetMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        int userId = AuthorizedUser.id();
        LOG.info("getAll restaurants from user with id={}", userId);
        return new ResponseEntity<>(restaurantService.getAll(), HttpStatus.OK);
    }


}
