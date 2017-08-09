package ru.alexraydev.topjava_graduate;

import ru.alexraydev.topjava_graduate.matcher.ModelMatcher;
import ru.alexraydev.topjava_graduate.model.Restaurant;

public class RestaurantTestData {
    public static final ModelMatcher<Restaurant> MATCHER = ModelMatcher.of(Restaurant.class);

    public static final Restaurant RESTAURANT1 = new Restaurant(1, "Hof Van Cleve");
    public static final Restaurant RESTAURANT2 = new Restaurant(2, "Mugaritz");
}
