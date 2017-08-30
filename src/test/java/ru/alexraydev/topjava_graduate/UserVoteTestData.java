package ru.alexraydev.topjava_graduate;

import ru.alexraydev.topjava_graduate.matcher.ModelMatcher;
import ru.alexraydev.topjava_graduate.model.UserVote;
import ru.alexraydev.topjava_graduate.to.UserVoteTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static ru.alexraydev.topjava_graduate.RestaurantTestData.RESTAURANT1;
import static ru.alexraydev.topjava_graduate.RestaurantTestData.RESTAURANT2;

public class UserVoteTestData {
    public static final ModelMatcher<UserVote> MATCHER = ModelMatcher.of(UserVote.class);
    public static final ModelMatcher<UserVoteTo> TO_MATCHER = ModelMatcher.of(UserVoteTo.class);

    //USER1 votes
    public static final UserVote USERVOTE1 = new UserVote(1, LocalDate.of(2017, 8, 1), LocalTime.of(10,0), RESTAURANT1);
    public static final UserVote USERVOTE2 = new UserVote(3, LocalDate.of(2017, 8, 2), LocalTime.of(10,0), RESTAURANT1);
    public static final UserVote USERVOTE3 = new UserVote(5, LocalDate.of(2017, 8, 3), LocalTime.of(10,0), RESTAURANT1);
    public static final UserVote USERVOTE4 = new UserVote(7, LocalDate.of(2017, 8, 4), LocalTime.of(10,0), RESTAURANT1);
    public static final UserVote USERVOTE5 = new UserVote(9, LocalDate.of(2017, 8, 5), LocalTime.of(10,0), RESTAURANT1);

    //USER2 votes
    public static final UserVote USERVOTE6 = new UserVote(2, LocalDate.of(2017, 8, 1), LocalTime.of(10,0), RESTAURANT2);
    public static final UserVote USERVOTE7 = new UserVote(4, LocalDate.of(2017, 8, 2), LocalTime.of(10,0), RESTAURANT2);
    public static final UserVote USERVOTE8 = new UserVote(6, LocalDate.of(2017, 8, 3), LocalTime.of(10,0), RESTAURANT2);
    public static final UserVote USERVOTE9 = new UserVote(8, LocalDate.of(2017, 8, 4), LocalTime.of(10,0), RESTAURANT2);
    public static final UserVote USERVOTE10 = new UserVote(10, LocalDate.of(2017, 8, 5), LocalTime.of(10,0), RESTAURANT2);

    public static final List<UserVote> USER_VOTES = new ArrayList<>(Arrays.asList(USERVOTE1, USERVOTE2, USERVOTE3, USERVOTE4,
            USERVOTE5, USERVOTE6, USERVOTE7, USERVOTE8, USERVOTE9, USERVOTE10));
}
