package school.sorokin.eventmanager.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(Exception exception) {

        log.error("Handle bad request exception", exception);
        var messageResponse = new ErrorMessage(
                "Bad request",
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(messageResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception exception) {

        log.error("Handle common exception", exception);
        var messageResponse = new ErrorMessage(
                "Server Error",
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(messageResponse);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(Exception e) {

        log.error("Handle entity not found exception", e);
        var messageResponse = new ErrorMessage(
                "Entity not found",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(messageResponse);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleBadCredentialsException(BadCredentialsException e) {

        log.error("Handle authorization exception", e);
        var messageResponse = new ErrorMessage(
                "Failed to authenticate",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(messageResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleValidateException(ConstraintViolationException e) {
        log.error("Handle Bad request exception: ConstraintViolationException = {}", e.getConstraintViolations());
        String detailMessage = e.getConstraintViolations()
                .stream()
                .map(error -> String.format("%s %s %s Rejected value: %s",
                        error.getRootBeanClass().getSimpleName(), error.getPropertyPath(),
                        error.getMessage(), error.getInvalidValue())
                ).collect(Collectors.joining(", "));

        var error = new ErrorMessage(
                "Bad request. Argument is NULL",
                detailMessage,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
