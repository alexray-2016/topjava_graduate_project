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
import ru.alexraydev.service.uservote.UserVoteService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class SpringMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
        UserVoteService userVoteService = applicationContext.getBean(UserVoteService.class);
        UserService userService = applicationContext.getBean(UserService.class);
        UserVote userVote = new UserVote(2,userService.getById(2));
        userVote.setDate(LocalDate.of(2017, Month.AUGUST, 1));
        //userVote.setTime(LocalTime.of(20, 0));
        userVoteService.save(userVote, 2);
    }
}
