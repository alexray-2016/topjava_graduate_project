package ru.alexraydev.topjava_graduate.service;


import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alexraydev.topjava_graduate.model.Restaurant;
import ru.alexraydev.topjava_graduate.service.restaurant.RestaurantService;
import ru.alexraydev.topjava_graduate.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Collection;

import static ru.alexraydev.topjava_graduate.RestaurantTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest{

    @Autowired
    private RestaurantService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @Test
    public void testSave() throws Exception {
        Restaurant newRestaurant = new Restaurant("New restaurant");
        Restaurant created = service.save(newRestaurant);
        newRestaurant.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(RESTAURANT1, RESTAURANT2, created), service.getAll());
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(RESTAURANT1.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(RESTAURANT2), service.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(100500);
    }

    @Test
    public void testGet() throws Exception {
        Restaurant restaurant = service.getById(RESTAURANT1.getId());
        MATCHER.assertEquals(RESTAURANT1, restaurant);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.getById(100500);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Restaurant> all = service.getAll();
        MATCHER.assertCollectionEquals(Arrays.asList(RESTAURANT1, RESTAURANT2), all);
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT1.getId(), RESTAURANT1.getName());
        updated.setName("UpdatedName");
        service.update(updated);
        MATCHER.assertEquals(updated, service.getById(RESTAURANT1.getId()));
    }
}
