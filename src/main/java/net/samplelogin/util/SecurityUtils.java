package net.samplelogin.util;

import net.samplelogin.domain.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.security.SocialUserDetails;

import java.lang.invoke.MethodHandles;

public class SecurityUtils {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String ANONYMOUS_USER_NAME = "anonymousUser";

    private SecurityUtils() {}

    public static void signIn(AppUser appUser) {
        SocialUserDetails userDetails = UserDetailsHelper.toSocialUserDetails(appUser, appUser.getEmail());
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.info("User: {} signed in", authentication.getName());
    }

    public static boolean isAuthenticated() {
        Authentication auth = getAuthentication();
        boolean authenticated = !(auth == null || auth.getName().equals(ANONYMOUS_USER_NAME));
        logger.debug("User {} {}", auth.getName(), authenticated ? "authenticated" : "not authenticated");
        return authenticated;
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getCurrentUserEmail() {
        return getAuthentication().getName();
    }
}
