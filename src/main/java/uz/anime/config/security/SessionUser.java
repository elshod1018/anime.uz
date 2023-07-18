package uz.anime.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.anime.domains.AuthUser;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SessionUser {
    public AuthUser user() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (Objects.isNull(authentication) || authentication instanceof AnonymousAuthenticationToken)
            return null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails)
            return userDetails.authUser();
        return null;
    }
    public Integer id(){
        AuthUser user = user();
        if(Objects.isNull(user)){
            return -1;
        }
        return user.getId();
    }
}
