package ru.alexraydev.service.restaurant;

import ru.alexraydev.model.Restaurant;
import ru.alexraydev.service.GenericService;

public interface RestaurantService extends GenericService<Restaurant>{
    void evictCache();
}
