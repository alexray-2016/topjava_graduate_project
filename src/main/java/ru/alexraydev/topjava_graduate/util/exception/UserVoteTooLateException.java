package ru.alexraydev.topjava_graduate.util.exception;

public class UserVoteTooLateException extends RuntimeException {
    public UserVoteTooLateException(String message) {
        super(message);
    }
}
