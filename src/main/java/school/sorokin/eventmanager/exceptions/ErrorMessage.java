package school.sorokin.eventmanager.exceptions;

import java.time.LocalDateTime;

public record ErrorMessage(
        String message,
        String detailedMessage,
        LocalDateTime dateTime
) {
}
