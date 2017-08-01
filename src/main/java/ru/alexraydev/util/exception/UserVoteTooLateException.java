package ru.alexraydev.util.exception;

public class UserVoteTooLateException extends RuntimeException {
    public UserVoteTooLateException(String message) {
        super(message);
    }
}
