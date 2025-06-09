package school.sorokin.eventmanager.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
}
