package ru.alexraydev.topjava_graduate.service;


import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alexraydev.topjava_graduate.model.Dish;
import ru.alexraydev.topjava_graduate.model.Role;
import ru.alexraydev.topjava_graduate.service.dish.DishService;
import ru.alexraydev.topjava_graduate.util.exception.NotFoundException;

import static ru.alexraydev.topjava_graduate.DishTestData.*;
import static ru.alexraydev.topjava_graduate.RestaurantTestData.RESTAURANT1;
import static ru.alexraydev.topjava_graduate.RestaurantTestData.RESTAURANT2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class DishServiceTest extends AbstractServiceTest{

    @Autowired
    private DishService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @Test
    public void testSave() throws Exception {
        Dish newDish = new Dish("New dish", 555, RESTAURANT1);
        Dish created = service.save(newDish);
        newDish.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(DISH1, DISH9, DISH2, DISH10, DISH3, DISH11, DISH4, DISH12,
                DISH5, DISH13, DISH6, DISH14, DISH7, DISH15, DISH8, DISH16, created), service.getAll());
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(DISH1.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(DISH9, DISH2, DISH10, DISH3, DISH11, DISH4, DISH12,
                DISH5, DISH13, DISH6, DISH14, DISH7, DISH15, DISH8, DISH16), service.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(100500);
    }

    @Test
    public void testGet() throws Exception {
        Dish dish = service.getById(DISH1.getId());
        MATCHER.assertEquals(DISH1, dish);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.getById(100500);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Dish> all = service.getAll();
        MATCHER.assertCollectionEquals(Arrays.asList(DISH1, DISH9, DISH2, DISH10, DISH3, DISH11, DISH4, DISH12,
                DISH5, DISH13, DISH6, DISH14, DISH7, DISH15, DISH8, DISH16), all);
    }

    @Test
    public void testUpdate() throws Exception {
        Dish updated = new Dish(DISH1.getId(), DISH1.getName(), DISH1.getPrice(), RESTAURANT1);
        updated.setName("UpdatedName");
        service.update(updated);
        MATCHER.assertEquals(updated, service.getById(DISH1.getId()));
    }
}
