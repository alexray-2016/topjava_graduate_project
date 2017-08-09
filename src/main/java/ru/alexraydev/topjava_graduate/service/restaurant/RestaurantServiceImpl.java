package ru.alexraydev.topjava_graduate.service.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.alexraydev.topjava_graduate.model.Restaurant;
import ru.alexraydev.topjava_graduate.repository.restaurant.RestaurantRepository;
import ru.alexraydev.topjava_graduate.util.exception.NotFoundException;
import static ru.alexraydev.topjava_graduate.util.ValidationUtil.*;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    private RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public Restaurant save(Restaurant entity) {
        Assert.notNull(entity, "restaurant must not be null");
        return restaurantRepository.save(entity);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public Restaurant update(Restaurant entity) throws NotFoundException {
        Assert.notNull(entity, "restaurant must not be null");
        return checkNotFoundWithId(restaurantRepository.save(entity), entity.getId());
    }

    @Override
    public Restaurant getById(int id) throws NotFoundException {
        return checkNotFoundWithId(restaurantRepository.getById(id), id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(restaurantRepository.delete(id), id);
    }

    @Cacheable("restaurants")
    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void evictCache() {
    }
}
