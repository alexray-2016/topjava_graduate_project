package ru.alexraydev.util.exception;

/**
 * Created by Administrator on 31.07.2017.
 */
public class UserVoteForThisDayAlreadyExistsException extends RuntimeException {
    public UserVoteForThisDayAlreadyExistsException(String message) {
        super(message);
    }
}
