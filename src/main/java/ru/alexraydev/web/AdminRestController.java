package ru.alexraydev.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.alexraydev.model.Dish;
import ru.alexraydev.model.Restaurant;
import ru.alexraydev.service.dish.DishService;
import ru.alexraydev.service.restaurant.RestaurantService;
import ru.alexraydev.to.DishTo;
import ru.alexraydev.util.DishUtils;
import ru.alexraydev.util.ValidationUtil;
import ru.alexraydev.util.exception.NotFoundException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AdminRestController.REST_URL)
public class AdminRestController {

    static final String REST_URL = "rest/admin";

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DishService dishService;

    //Managing restaurants
    @PostMapping(value = "/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> saveRestaurant(@Valid @RequestBody Restaurant entity) {
        ValidationUtil.checkNew(entity);

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

        restaurantService.update(restaurant);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping(value = "/restaurants/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> getByRestaurantId(@PathVariable("id") int id) throws NotFoundException {
        Restaurant restaurant = restaurantService.getById(id);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/restaurants/{id}")
    public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable("id") int id) throws NotFoundException {
        Restaurant restaurant = restaurantService.getById(id);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        restaurantService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return new ResponseEntity<List<Restaurant>>(restaurantService.getAll(), HttpStatus.OK);
    }

    //Managing dishes
    @PostMapping(value = "/dishes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> saveDish(@Valid @RequestBody DishTo dishTo) {
        ValidationUtil.checkNew(dishTo);
        Dish entity = DishUtils.createNewDishFromTo(dishTo);

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

        dishService.update(dish);
        return new ResponseEntity<>(DishUtils.asTo(dish), HttpStatus.OK);
    }

    @GetMapping(value = "/dishes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> getDishById(@PathVariable("id") int id) throws NotFoundException {
        Dish dish = dishService.getById(id);
        if (dish == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(DishUtils.asTo(dish), HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/dishes/{id}")
    public ResponseEntity<Dish> deleteDish(@PathVariable("id") int id) throws NotFoundException {
        Dish dish = dishService.getById(id);
        if (dish == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dishService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DishTo>> getAllDishes() {
        List<Dish> dishList = dishService.getAll();
        List<DishTo> dishToList = dishList.stream()
                .map(DishUtils::asTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dishToList, HttpStatus.OK);
    }
}