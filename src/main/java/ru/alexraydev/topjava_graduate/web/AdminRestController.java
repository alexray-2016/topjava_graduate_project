package ru.alexraydev.topjava_graduate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.alexraydev.topjava_graduate.model.Dish;
import ru.alexraydev.topjava_graduate.model.Restaurant;
import ru.alexraydev.topjava_graduate.model.UserVote;
import ru.alexraydev.topjava_graduate.service.dish.DishService;
import ru.alexraydev.topjava_graduate.service.restaurant.RestaurantService;
import ru.alexraydev.topjava_graduate.service.uservote.UserVoteService;
import ru.alexraydev.topjava_graduate.to.DishTo;
import ru.alexraydev.topjava_graduate.util.DishUtils;
import ru.alexraydev.topjava_graduate.util.ValidationUtil;
import ru.alexraydev.topjava_graduate.util.exception.NotFoundException;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AdminRestController.REST_URL)
public class AdminRestController {

    static final String REST_URL = "rest/admin";

    private static final Logger LOG = LoggerFactory.getLogger(AdminRestController.class);

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DishService dishService;

    @Autowired
    private UserVoteService userVoteService;

    //Managing restaurants
    @PostMapping(value = "/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> saveRestaurant(@Valid @RequestBody Restaurant entity) {
        ValidationUtil.checkNew(entity);

        LOG.info("create {}", entity);
        Restaurant created = restaurantService.save(entity);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/restaurants/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable("id") int id, @Valid @RequestBody Restaurant entity) throws NotFoundException {
        ValidationUtil.checkIdConsistent(entity, id);

        Restaurant restaurant = restaurantService.getById(id);

        restaurant.setName(entity.getName());

        LOG.info("update {} with id={}", entity, id);
        restaurantService.update(restaurant);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping(value = "/restaurants/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> getByRestaurantId(@PathVariable("id") int id) throws NotFoundException {
        LOG.info("get restaurant with id={}", id);
        Restaurant restaurant = restaurantService.getById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping(value = "/restaurants/{id}")
    public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable("id") int id) throws NotFoundException {
        Restaurant restaurant = restaurantService.getById(id);
        LOG.info("delete restaurant with id={}", id);
        restaurantService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        LOG.info("get all restaurants");
        return new ResponseEntity<>(restaurantService.getAll(), HttpStatus.OK);
    }

    //Managing dishes
    @PostMapping(value = "/dishes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> saveDish(@Valid @RequestBody DishTo dishTo) {
        ValidationUtil.checkNew(dishTo);
        Dish entity = DishUtils.createNewDishFromTo(dishTo);

        LOG.info("create {}", entity);
        Dish created = dishService.save(entity);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> updateDish(@PathVariable("id") int id, @Valid @RequestBody DishTo dishTo) throws NotFoundException {
        ValidationUtil.checkIdConsistent(dishTo, id);
        Dish entity = DishUtils.createNewDishFromTo(dishTo);

        Dish dish = dishService.getById(id);

        dish.setName(entity.getName());
        dish.setPrice(entity.getPrice());
        dish.setRestaurant(entity.getRestaurant());

        LOG.info("update {} with id={}", entity, id);
        dishService.update(dish);
        return new ResponseEntity<>(DishUtils.asTo(dish), HttpStatus.OK);
    }

    @GetMapping(value = "/dishes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> getDishById(@PathVariable("id") int id) throws NotFoundException {
        LOG.info("get dish with id={}", id);
        Dish dish = dishService.getById(id);
        return new ResponseEntity<>(DishUtils.asTo(dish), HttpStatus.OK);
    }

    @DeleteMapping(value = "/dishes/{id}")
    public ResponseEntity<Dish> deleteDish(@PathVariable("id") int id) throws NotFoundException {
        Dish dish = dishService.getById(id);
        LOG.info("delete dish with id={}", id);
        dishService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DishTo>> getAllDishes() {
        LOG.info("get all dishes");
        List<Dish> dishList = dishService.getAll();
        List<DishTo> dishToList = dishList.stream()
                .map(DishUtils::asTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dishToList, HttpStatus.OK);
    }

    //Managing user votes
    @GetMapping(value = "/votes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserVote>> getAllVotesFiltered(
            @RequestParam(value = "sort", required = false) String order,
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "restaurantId", required = false) Integer chosenRestaurantId,
            @RequestParam(value = "userId", required = false) Integer userId) {
        if (userId != null) {
            LOG.info("get votes filtered by user");
            return new ResponseEntity<>(userVoteService.getFilteredByUser(userId), HttpStatus.OK);
        }
        if (chosenRestaurantId != null) {
            LOG.info("get votes filtered by restaurant");
            return new ResponseEntity<>(userVoteService.getFilteredByRestaurant(chosenRestaurantId), HttpStatus.OK);
        }
        if (date != null) {
            LOG.info("get votes filtered by date");
            return new ResponseEntity<>(userVoteService.getFilteredByDate(date), HttpStatus.OK);
        }
        LOG.info("get all votes sorted by date");
        return new ResponseEntity<>(userVoteService.getSortedByDate(order == null ? "" : order), HttpStatus.OK);
    }
}