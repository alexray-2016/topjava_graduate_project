package ru.alexraydev.topjava_graduate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.alexraydev.topjava_graduate.model.User;
import ru.alexraydev.topjava_graduate.model.UserVote;
import ru.alexraydev.topjava_graduate.service.user.UserService;
import ru.alexraydev.topjava_graduate.service.uservote.UserVoteService;

import java.time.LocalDate;
import java.time.Month;

public class SpringMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
        UserVoteService userVoteService = applicationContext.getBean(UserVoteService.class);
        UserService userService = applicationContext.getBean(UserService.class);
        User user = userService.getById(1);
        //UserVote userVote = new UserVote(2,userService.getById(2));
        //userVote.setDate(LocalDate.of(2017, Month.AUGUST, 1));
        //userVote.setTime(LocalTime.of(20, 0));
        //userVoteService.save(userVote, 2);
    }
}
