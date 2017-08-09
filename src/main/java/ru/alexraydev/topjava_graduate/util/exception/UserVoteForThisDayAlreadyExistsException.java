package ru.alexraydev.topjava_graduate.util.exception;

public class UserVoteForThisDayAlreadyExistsException extends RuntimeException {
    public UserVoteForThisDayAlreadyExistsException(String message) {
        super(message);
    }
}
