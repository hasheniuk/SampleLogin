package net.samplelogin.service.impl;

import net.samplelogin.domain.AppUser;
import net.samplelogin.form.RegistrationForm;
import net.samplelogin.repository.AppUserRepository;
import net.samplelogin.service.AppUserService;
import net.samplelogin.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String TEMP_SOCIAL_ACC_PASSWORD = "!tempsoc1"; // TODO ??? social account password

    private AppUserRepository appUserRepository;

    @Override
    public AppUser register(RegistrationForm form) {
        Assert.notNull(form, RegistrationForm.class);
        if (exists(form.getEmail())) {
            logger.debug("User {} already registered", form.getEmail());
            return null;
        }
        AppUser registeredUser = appUserRepository.saveAndFlush(createUser(form));
        logger.debug("User {} registered successfully", registeredUser.getEmail());
        return registeredUser;
    }

    private AppUser createUser(RegistrationForm form) {
        AppUser user = new AppUser();
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setCreatedAt(LocalDate.now());
        return user;
    }

    @Override
    public AppUser registerOrFind(User facebookUser) {
        Assert.notNull(facebookUser, User.class);
        String email = facebookUser.getEmail();
        if (exists(facebookUser.getEmail())) {
            logger.debug("Facebook user {} already registered", email);
            return appUserRepository.findByEmail(email);
        }
        AppUser registeredUser = appUserRepository.saveAndFlush(createUser(facebookUser));
        logger.debug("User {} registered successfully", registeredUser.getEmail());
        return registeredUser;
    }

    private AppUser createUser(User facebookUser) {
        AppUser user = new AppUser();
        user.setEmail(facebookUser.getEmail());
        user.setPassword(TEMP_SOCIAL_ACC_PASSWORD);
        user.setCreatedAt(LocalDate.now());
        return user;
    }

    private boolean exists(String email) {
        AppUser foundUser = appUserRepository.findByEmail(email);
        boolean exists = foundUser != null;
        logger.debug("User {} {}", email, exists ? "exists" : "not exists");
        return exists;
    }

    @Inject
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        Assert.notNull(appUserRepository, AppUserRepository.class);
        this.appUserRepository = appUserRepository;
    }
}
