package ru.alexraydev.topjava_graduate.service.dish;

import ru.alexraydev.topjava_graduate.model.Dish;
import ru.alexraydev.topjava_graduate.service.GenericService;

public interface DishService extends GenericService<Dish>{
    void evictCache();
}
