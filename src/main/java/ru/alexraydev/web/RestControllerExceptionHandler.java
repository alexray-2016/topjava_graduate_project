package ru.alexraydev.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.alexraydev.util.ValidationUtil;
import ru.alexraydev.util.exception.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@ControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class RestControllerExceptionHandler {

    private static Logger LOG = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(UserVoteForThisDayAlreadyExistsException.class)
    @ResponseBody
    public ErrorInfo handleError(HttpServletRequest req, UserVoteForThisDayAlreadyExistsException e) {
        return logAndGetErrorInfo(req, e, false);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UserVoteIncorrectDateException.class)
    @ResponseBody
    public ErrorInfo handleError(HttpServletRequest req, UserVoteIncorrectDateException e) {
        return logAndGetErrorInfo(req, e, false);
    }

    @ResponseStatus(value = HttpStatus.LOCKED)
    @ExceptionHandler(UserVoteTooLateException.class)
    @ResponseBody
    public ErrorInfo handleError(HttpServletRequest req, UserVoteTooLateException e) {
        return logAndGetErrorInfo(req, e, false);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetErrorInfo(req, e, true);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ErrorInfo bindValidationError(HttpServletRequest req, BindingResult result) {
        return logAndGetValidationErrorInfo(req, result);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorInfo restValidationError(HttpServletRequest req, MethodArgumentNotValidException e) {
        return logAndGetValidationErrorInfo(req, e.getBindingResult());
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ErrorInfo conflict(HttpServletRequest req, IllegalArgumentException e) {
        return logAndGetErrorInfo(req, e, false);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ErrorInfo handle404Exception(HttpServletRequest request, Exception e) {
        return logAndGetErrorInfo(request, e, true);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorInfo handleException(HttpServletRequest request, Exception e) {
        return logAndGetErrorInfo(request, e, true);
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            LOG.error("Exception at request " + req.getRequestURL(), rootCause);
        } else {
            LOG.warn("Exception at request {}: {}", req.getRequestURL() + ": " + rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), rootCause);
    }

    private ErrorInfo logAndGetValidationErrorInfo(HttpServletRequest req, BindingResult result) {
        String[] details = result.getAllErrors().stream()
                .map(oe -> FieldError.class.cast(oe).getField() + " " + oe.getDefaultMessage())
                .toArray(String[]::new);

        return logAndGetErrorInfo(req, "ValidationException", details);
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, String cause, String... details) {
        LOG.warn("{} exception at request {}: {}", cause, req.getRequestURL(), Arrays.toString(details));
        return new ErrorInfo(req.getRequestURL(), cause, details);
    }
}
