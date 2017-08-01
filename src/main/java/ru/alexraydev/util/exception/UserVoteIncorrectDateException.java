package ru.alexraydev.util.exception;

public class UserVoteIncorrectDateException extends RuntimeException {
    public UserVoteIncorrectDateException(String message) {
        super(message);
    }
}
