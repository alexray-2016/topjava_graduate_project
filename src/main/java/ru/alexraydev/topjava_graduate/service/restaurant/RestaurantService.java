package ru.alexraydev.topjava_graduate.service.restaurant;

import ru.alexraydev.topjava_graduate.model.Restaurant;
import ru.alexraydev.topjava_graduate.service.GenericService;

public interface RestaurantService extends GenericService<Restaurant>{
    void evictCache();
}
