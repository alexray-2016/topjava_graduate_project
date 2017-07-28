package ru.alexraydev;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.alexraydev.model.Dish;
import ru.alexraydev.model.Restaurant;
import ru.alexraydev.model.User;
import ru.alexraydev.model.UserVote;
import ru.alexraydev.repository.dish.DishRepository;
import ru.alexraydev.repository.dish.DishRepositoryImpl;
import ru.alexraydev.repository.restaurant.RestaurantRepository;
import ru.alexraydev.repository.restaurant.RestaurantRepositoryImpl;
import ru.alexraydev.repository.user.UserRepository;
import ru.alexraydev.repository.user.UserRepositoryImpl;
import ru.alexraydev.service.user.UserService;

public class SpringMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
        UserService userService = applicationContext.getBean(UserService.class);
    }
}
