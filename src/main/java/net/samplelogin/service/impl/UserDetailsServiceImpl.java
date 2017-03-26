package net.samplelogin.service.impl;

import net.samplelogin.domain.AppUser;
import net.samplelogin.repository.AppUserRepository;
import net.samplelogin.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null) {
            RuntimeException e = new UsernameNotFoundException("No user found with username: " + email);
            logger.error(e.getMessage(), e);
            throw e;
        }
        logger.debug("Found user: {}", appUser.getEmail());
        return User.withUsername(appUser.getEmail())
                .password(appUser.getPassword())
                .disabled(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .authorities(getAuthorities(new ArrayList<>())) // TODO authorities
                .build();
    }

    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Inject
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        Assert.notNull(appUserRepository, AppUserRepository.class);
        this.appUserRepository = appUserRepository;
    }
}
