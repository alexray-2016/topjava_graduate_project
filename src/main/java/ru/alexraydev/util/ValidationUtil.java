package ru.alexraydev.util;

import ru.alexraydev.HasId;
import ru.alexraydev.model.UserVote;
import ru.alexraydev.util.exception.NotFoundException;
import ru.alexraydev.util.exception.UserVoteIncorrectDateException;
import ru.alexraydev.util.exception.UserVoteTooLateException;

import java.time.LocalDate;
import java.time.LocalTime;

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

    public static void checkDateConsistent(UserVote userVote) {
        if (userVote.getDate() == null) {
            userVote.setDate(LocalDate.now());
        }
        else if (!userVote.getDate().isEqual(LocalDate.now())) {
            throw new UserVoteIncorrectDateException(userVote + " must be with date=" + LocalDate.now());
        }
    }

    public static void checkTimeConsistent(UserVote userVote) {
        if (userVote.getTime() == null) {
            userVote.setTime(LocalTime.now());
        }
        if (userVote.getTime().isAfter(LocalTime.of(11, 0))) {
            throw new UserVoteTooLateException("It is too late, vote can't be changed");
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

}
