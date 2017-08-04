package ru.alexraydev.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import ru.alexraydev.model.Dish;
import ru.alexraydev.service.restaurant.RestaurantService;
import ru.alexraydev.to.DishTo;

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
