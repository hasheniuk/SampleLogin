package net.samplelogin.service.impl;

import net.samplelogin.domain.AppUser;
import net.samplelogin.repository.AppUserRepository;
import net.samplelogin.util.Assert;
import net.samplelogin.util.UserDetailsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;

@Service
@Transactional
public class SocialUserDetailsServiceImpl implements SocialUserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private AppUserRepository appUserRepository;

    @Override
    public SocialUserDetails loadUserByUserId(String email) {
        AppUser appUser = appUserRepository.findByEmail(email);
        return UserDetailsHelper.toSocialUserDetails(appUser, email);
    }

    @Inject
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        Assert.notNull(appUserRepository, AppUserRepository.class);
        this.appUserRepository = appUserRepository;
    }
}
