package net.samplelogin.util;

import net.samplelogin.domain.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;

import java.lang.invoke.MethodHandles;
import java.util.Collections;

public class UserDetailsHelper {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private UserDetailsHelper() {}

    public static UserDetails toUserDetails(AppUser appUser, String email) {
        assertUser(appUser, email);
        UserDetails userDetails = User.withUsername(appUser.getEmail())
                .password(appUser.getPassword())
                .disabled(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(appUser.getRole())))
                .build();
        logger.debug("Converted to UserDetails: {}", userDetails.getUsername());
        return userDetails;
    }

    public static SocialUserDetails toSocialUserDetails(AppUser appUser, String email) {
        assertUser(appUser, email);
        SocialUser socialUser = new SocialUser(appUser.getEmail(), "",
                true, true, true, true,
                Collections.singletonList(new SimpleGrantedAuthority(appUser.getRole())));
        logger.debug("Converted to SocialUserDetails: {}", socialUser.getUsername());
        return socialUser;
    }

    private static void assertUser(AppUser user, String email) {
        if (user == null) {
            RuntimeException e = new UsernameNotFoundException("No user found with email: " + email);
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
