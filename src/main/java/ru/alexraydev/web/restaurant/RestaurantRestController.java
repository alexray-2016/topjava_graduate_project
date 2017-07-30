package ru.alexraydev.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.alexraydev.model.Restaurant;
import ru.alexraydev.service.restaurant.RestaurantService;
import ru.alexraydev.util.ValidationUtil;
import ru.alexraydev.util.exception.NotFoundException;
import ru.alexraydev.web.GenericRestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(RestaurantRestController.REST_URL)
public class RestaurantRestController implements GenericRestController<Restaurant>{

    static final String REST_URL = "/rest/restaurants";

    @Autowired
    private RestaurantService restaurantService;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> save(@RequestBody Restaurant entity) {
        ValidationUtil.checkNew(entity);

        Restaurant created = restaurantService.save(entity);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> update(@PathVariable("id") int id, @RequestBody Restaurant entity) throws NotFoundException {
        ValidationUtil.checkIdConsistent(entity, id);

        Restaurant restaurant = restaurantService.getById(id);

        restaurant.setName(entity.getName());

        restaurantService.update(restaurant);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> getById(@PathVariable("id") int id) throws NotFoundException {
        Restaurant restaurant = restaurantService.getById(id);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        }
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Restaurant> delete(@PathVariable("id") int id) throws NotFoundException {
        Restaurant restaurant = restaurantService.getById(id);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        restaurantService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Restaurant>> getAll() {
        return new ResponseEntity<List<Restaurant>>(restaurantService.getAll(), HttpStatus.OK);
    }
}
