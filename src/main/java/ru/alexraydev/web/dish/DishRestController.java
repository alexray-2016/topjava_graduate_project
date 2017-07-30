package ru.alexraydev.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.alexraydev.model.Dish;
import ru.alexraydev.service.dish.DishService;
import ru.alexraydev.util.ValidationUtil;
import ru.alexraydev.util.exception.NotFoundException;
import ru.alexraydev.web.GenericRestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(DishRestController.REST_URL)
public class DishRestController implements GenericRestController<Dish>{

    static final String REST_URL = "/rest/dishes";

    @Autowired
    private DishService dishService;


    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> save(@RequestBody Dish entity) {
        ValidationUtil.checkNew(entity);

        Dish created = dishService.save(entity);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> update(@PathVariable("id") int id, @RequestBody Dish entity) throws NotFoundException {
        ValidationUtil.checkIdConsistent(entity, id);

        Dish dish = dishService.getById(id);

        dish.setName(entity.getName());
        dish.setPrice(entity.getPrice());
        dish.setRestaurant(entity.getRestaurant());

        dishService.update(dish);
        return new ResponseEntity<>(dish, HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> getById(@PathVariable("id") int id) throws NotFoundException {
        Dish dish = dishService.getById(id);
        if (dish == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(dish, HttpStatus.OK);
        }
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Dish> delete(@PathVariable("id") int id) throws NotFoundException {
        Dish dish = dishService.getById(id);
        if (dish == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dishService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Dish>> getAll() {
        return new ResponseEntity<List<Dish>>(dishService.getAll(), HttpStatus.OK);
    }
}
