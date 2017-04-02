package net.samplelogin.service.impl;

import net.samplelogin.domain.AppUser;
import net.samplelogin.domain.Role;
import net.samplelogin.form.RegistrationForm;
import net.samplelogin.repository.AppUserRepository;
import net.samplelogin.service.AppUserService;
import net.samplelogin.util.Assert;
import net.samplelogin.util.TwitterProfileWithEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.User;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser register(RegistrationForm form) {
        Assert.notNull(form, RegistrationForm.class);
        if (existsUser(form.getEmail())) {
            logger.debug("User {} already registered", form.getEmail());
            return null;
        }
        AppUser registeredUser = appUserRepository.saveAndFlush(createAppUser(form));
        logger.debug("User {} registered successfully", registeredUser.getEmail());
        return registeredUser;
    }

    private AppUser createAppUser(RegistrationForm form) {
        AppUser user = new AppUser();
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        System.out.println(user.getPassword());
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDate.now());
        return user;
    }

    @Override
    public AppUser registerOrFind(User facebookUser) {
        Assert.notNull(facebookUser, User.class);
        String email = facebookUser.getEmail();
        if (existsUser(email)) {
            logger.debug("Facebook user {} already registered", email);
            return appUserRepository.findByEmail(email);
        }
        AppUser registeredUser = appUserRepository.saveAndFlush(createAppUser(facebookUser));
        logger.debug("Facebook user {} registered successfully", registeredUser.getEmail());
        return registeredUser;
    }

    private AppUser createAppUser(User facebookUser) {
        AppUser user = getSocialAppUser();
        user.setEmail(facebookUser.getEmail());
        return user;
    }

    @Override
    public AppUser registerOrFind(TwitterProfileWithEmail twitterProfile) {
        Assert.notNull(twitterProfile, TwitterProfileWithEmail.class);
        String email = twitterProfile.getEmail();
        if (existsUser(email)) {
            logger.debug("Twitter profile {} already registered", email);
            return appUserRepository.findByEmail(email);
        }
        AppUser registeredUser = appUserRepository.saveAndFlush(createAppUser(twitterProfile));
        logger.debug("Twitter user {} registered successfully", registeredUser.getEmail());
        return registeredUser;
    }

    private AppUser createAppUser(TwitterProfileWithEmail twitterProfile) {
        AppUser user = getSocialAppUser();
        user.setEmail(twitterProfile.getEmail());
        return user;
    }

    @Override
    public AppUser registerOrFind(LinkedInProfile linkedInProfile) {
        Assert.notNull(linkedInProfile, LinkedInProfile.class);
        String email = linkedInProfile.getEmailAddress();
        if (existsUser(email)) {
            logger.debug("LinkedIn profile {} already registered", email);
            return appUserRepository.findByEmail(email);
        }
        AppUser registeredUser = appUserRepository.saveAndFlush(createAppUser(linkedInProfile));
        logger.debug("LinkedIn user {} registered successfully", registeredUser.getEmail());
        return registeredUser;
    }

    private AppUser createAppUser(LinkedInProfile linkedInProfile) {
        AppUser user = getSocialAppUser();
        user.setEmail(linkedInProfile.getEmailAddress());
        return user;
    }

    @Override
    public AppUser registerOrFind(Person person) {
        Assert.notNull(person, Person.class);
        String email = person.getAccountEmail();
        if (existsUser(email)) {
            logger.debug("Google profile {} already registered", email);
            return appUserRepository.findByEmail(email);
        }
        AppUser registeredUser = appUserRepository.saveAndFlush(createAppUser(person));
        logger.debug("Google user {} registered successfully", registeredUser.getEmail());
        return registeredUser;
    }

    private AppUser createAppUser(Person person) {
        AppUser user = getSocialAppUser();
        user.setEmail(person.getAccountEmail());
        return user;
    }

    private AppUser getSocialAppUser() {
        AppUser user = new AppUser();
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDate.now());
        return user;
    }

    private boolean existsUser(String email) {
        AppUser foundUser = appUserRepository.findByEmail(email);
        boolean exists = foundUser != null;
        logger.debug("User {} {}", email, exists ? "existsUser" : "not existsUser");
        return exists;
    }

    @Inject
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        Assert.notNull(appUserRepository, AppUserRepository.class);
        this.appUserRepository = appUserRepository;
    }

    @Inject
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, PasswordEncoder.class);
        this.passwordEncoder = passwordEncoder;
    }
}
