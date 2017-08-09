package ru.alexraydev.topjava_graduate.util;

import org.springframework.util.CollectionUtils;
import ru.alexraydev.topjava_graduate.HasId;
import ru.alexraydev.topjava_graduate.model.UserVote;
import ru.alexraydev.topjava_graduate.to.UserVoteTo;
import ru.alexraydev.topjava_graduate.util.exception.NotFoundException;
import ru.alexraydev.topjava_graduate.util.exception.UserVoteIncorrectDateException;
import ru.alexraydev.topjava_graduate.util.exception.UserVoteTooLateException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ValidationUtil {
    private ValidationUtil () {

    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static <T> T checkNotFoundForToday(T object, String msg) {
        if (object == null) {
            throw new NotFoundException(msg);
        }
        return object;
    }

    public static void checkNew(HasId bean) {
        if (!(bean.getId() == null)) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }

    public static void checkIdConsistent(HasId bean, int id) {
//      http://stackoverflow.com/a/32728226/548473
        if ((bean.getId() == null)) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalArgumentException(bean + " must be with id=" + id);
        }
    }

    public static void checkDateConsistent(UserVoteTo userVote) {
        if (userVote.getDate() == null) {
            userVote.setDate(LocalDate.now());
        }
        else if (!userVote.getDate().isEqual(LocalDate.now())) {
            throw new UserVoteIncorrectDateException(userVote + " must be with date=" + LocalDate.now());
        }
    }

    public static void checkTimeConsistentForSave(UserVoteTo userVote) {
        if (userVote.getTime() == null) {
            LocalTime now = LocalTime.now();
            if (now.isAfter(LocalTime.of(11, 0))) {
                throw new UserVoteTooLateException("It is too late, user vote can't be created");
            }
            userVote.setTime(now);
        }
    }

    public static void checkTimeConsistentForUpdate(UserVoteTo userVote) {
        if (userVote.getTime() == null) {
            userVote.setTime(LocalTime.now());
        }
        if (userVote.getTime().isAfter(LocalTime.of(11, 0))) {
            throw new UserVoteTooLateException("It is too late, vote can't be changed");
        }
    }

    public static void checkTimeConsistentForDelete() {
        if (LocalTime.now().isAfter(LocalTime.of(11, 0))) {
            throw new UserVoteTooLateException("It is too late, vote can't be deleted");
        }
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static List<UserVote> checkUserVotesFiltered (List<UserVote> userVotes, String msg) {
        if (CollectionUtils.isEmpty(userVotes)) {
            throw new NotFoundException(msg);
        }
        return userVotes;
    }

}
