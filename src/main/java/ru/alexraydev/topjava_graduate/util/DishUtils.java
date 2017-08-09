package ru.alexraydev.topjava_graduate.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alexraydev.topjava_graduate.model.Dish;
import ru.alexraydev.topjava_graduate.service.restaurant.RestaurantService;
import ru.alexraydev.topjava_graduate.to.DishTo;

@Component
public class DishUtils {

    private static RestaurantService restaurantService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        DishUtils.restaurantService = restaurantService;
    }

    public static Dish createNewDishFromTo(DishTo dishTo) {
        return new Dish(dishTo.getId(), dishTo.getName(),
                dishTo.getPrice(), restaurantService.getById(dishTo.getRestaurantId()));
    }

    public static DishTo asTo(Dish dish) {
        return new DishTo(dish.getId(), dish.getName(), dish.getPrice(), dish.getRestaurant().getId());
    }

}
