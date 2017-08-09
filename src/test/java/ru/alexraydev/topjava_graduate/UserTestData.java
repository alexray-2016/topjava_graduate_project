package ru.alexraydev.topjava_graduate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexraydev.topjava_graduate.matcher.ModelMatcher;
import ru.alexraydev.topjava_graduate.model.Role;
import ru.alexraydev.topjava_graduate.model.User;

import java.util.EnumSet;
import java.util.Objects;

public class UserTestData {
    private static final Logger LOG = LoggerFactory.getLogger(UserTestData.class);

    public static final int ADMIN_ID = 1;
    public static final int FIRST_USER_ID = 2;
    public static final int SECOND_USER_ID = 3;

    public static final User FIRST_USER = new User(FIRST_USER_ID, "FirstUser", "first_user@gmail.com", "user1password", EnumSet.of(Role.ROLE_USER));
    public static final User SECOND_USER = new User(SECOND_USER_ID, "SecondUser", "second_user@gmail.com", "user2password", EnumSet.of(Role.ROLE_USER));
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "adminpassword", EnumSet.of(Role.ROLE_ADMIN));

    public static final ModelMatcher<User> MATCHER = ModelMatcher.of(User.class,
            (expected, actual) -> expected == actual || (
                               Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getPassword(), actual.getPassword())
                            && Objects.equals(expected.getEmail(), actual.getEmail())
                            && Objects.equals(expected.getRoles(), actual.getRoles())
                    )
    );
}
