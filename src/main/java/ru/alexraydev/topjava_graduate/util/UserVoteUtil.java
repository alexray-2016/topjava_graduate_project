package ru.alexraydev.topjava_graduate.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alexraydev.topjava_graduate.model.UserVote;
import ru.alexraydev.topjava_graduate.service.restaurant.RestaurantService;
import ru.alexraydev.topjava_graduate.to.UserVoteTo;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserVoteUtil {
    private static RestaurantService restaurantService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        UserVoteUtil.restaurantService = restaurantService;
    }

    public static UserVote createNewUserVoteFromTo(UserVoteTo userVoteTo) {
        return new UserVote(userVoteTo.getId(), userVoteTo.getDate(), userVoteTo.getTime(), restaurantService.getById(userVoteTo.getChosenRestaurantId()));
    }

    public static UserVoteTo asToForAdmin(UserVote userVote) {
        return new UserVoteTo(userVote.getId(), userVote.getDate(), userVote.getTime(), userVote.getRestaurant().getId(), userVote.getUser().getId());
    }
    public static UserVoteTo asToForUser(UserVote userVote) {
        return new UserVoteTo(userVote.getId(), userVote.getDate(), userVote.getTime(), userVote.getRestaurant().getId(), null);
    }

    public static List<UserVoteTo> asToList(List<UserVote> userVoteList) {
        return userVoteList.stream()
                .map(UserVoteUtil::asToForAdmin)
                .collect(Collectors.toList());
    }
}
