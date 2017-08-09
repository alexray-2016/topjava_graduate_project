package ru.alexraydev.topjava_graduate;

import ru.alexraydev.topjava_graduate.matcher.ModelMatcher;
import ru.alexraydev.topjava_graduate.model.Dish;
import static ru.alexraydev.topjava_graduate.RestaurantTestData.*;

public class DishTestData {
    public static final ModelMatcher<Dish> MATCHER = ModelMatcher.of(Dish.class);

    public static final Dish DISH1 = new Dish(1, "Masala dosa", 230, RESTAURANT1);
    public static final Dish DISH2 = new Dish(3, "Tacos", 125, RESTAURANT1);
    public static final Dish DISH3 = new Dish(5, "Texas barbecue pork", 675, RESTAURANT1);
    public static final Dish DISH4 = new Dish(7, "Pho", 355, RESTAURANT1);
    public static final Dish DISH5 = new Dish(9, "Croissant", 299, RESTAURANT1);
    public static final Dish DISH6 = new Dish(11, "Kalua pig", 3599, RESTAURANT1);
    public static final Dish DISH7 = new Dish(13, "Ice cream", 315, RESTAURANT1);
    public static final Dish DISH8 = new Dish(15, "Chocolate", 399, RESTAURANT1);


    public static final Dish DISH9 = new Dish(2, "Potato chips", 70, RESTAURANT2);
    public static final Dish DISH10 = new Dish(4, "Buttered toast with Marmite", 312, RESTAURANT2);
    public static final Dish DISH11 = new Dish(6, "Chili crab", 999, RESTAURANT2);
    public static final Dish DISH12 = new Dish(8, "Montreal-style smoked meat", 1099, RESTAURANT2);
    public static final Dish DISH13 = new Dish(10, "Arepas", 499, RESTAURANT2);
    public static final Dish DISH14 = new Dish(12, "Donuts", 199, RESTAURANT2);
    public static final Dish DISH15 = new Dish(14, "Tom yum goong", 649, RESTAURANT2);
    public static final Dish DISH16 = new Dish(16, "Neapolitan pizza", 579, RESTAURANT2);
}
