package school.sorokin.eventmanager.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import school.sorokin.eventmanager.model.user.User;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionService {

    public Long getAuthenticatedUserId(){
        Authentication authentication = SecurityContextHolder.getContext().
                getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NoSuchElementException("Failed authentication");
        }

        User currentUser = (User) authentication.getPrincipal();
        log.info("Got current user id: {}", currentUser.id());
        return currentUser.id();
    }
}
