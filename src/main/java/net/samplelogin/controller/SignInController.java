package net.samplelogin.controller;

import net.samplelogin.domain.AppUser;
import net.samplelogin.form.RegistrationForm;
import net.samplelogin.service.AppUserService;
import net.samplelogin.service.ConnectionService;
import net.samplelogin.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/signin")
public class SignInController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private AppUserService appUserService;
    private ConnectionService connectionService;
    private Facebook facebook;
    private Twitter twitter;
    private LinkedIn linkedIn;
    private Google google;

    @PostConstruct
    public void init() throws ReflectiveOperationException {
        FacebookUtils.excludeProfileFields("bio");
    }

    @GetMapping
    public String getView() {
        if (SecurityUtils.isAuthenticated()) {
            logger.debug("Redirected to profile signed in user: {}", SecurityUtils.getCurrentUserEmail());
            return Redirects.PROFILE;
        }
        return Views.LOGIN;
    }

    @GetMapping("/facebook")
    public String authFacebook() {
        if (!connectionService.isFacebookConnected()) {
            return Redirects.SIGN_IN;
        }
        User facebookUser = facebook.userOperations().getUserProfile();
        AppUser appUser = appUserService.registerOrFind(facebookUser);
        SecurityUtils.signIn(appUser);
        return Redirects.PROFILE;
    }

    @GetMapping("/twitter")
    public String authTwitter() {
        if (!connectionService.isTwitterConnected()) {
            return Redirects.SIGN_IN;
        }
        TwitterProfileWithEmail twitterProfile = twitter.restOperations()
                .getForObject(TwitterHelper.TWITTER_INCLUDE_EMAIL_URL, TwitterProfileWithEmail.class);
        AppUser appUser = appUserService.registerOrFind(twitterProfile);
        SecurityUtils.signIn(appUser);
        return Redirects.PROFILE;
    }

    @GetMapping("/linkedin")
    public String authLinkedIn() {
        if (!connectionService.isLinkedInConnected()) {
            return Redirects.SIGN_IN;
        }
        LinkedInProfile linkedInProfile = linkedIn.profileOperations().getUserProfile();
        AppUser appUser = appUserService.registerOrFind(linkedInProfile);
        SecurityUtils.signIn(appUser);
        return Redirects.PROFILE;
    }

    @GetMapping("/google")
    public String authGoogle() {
        if (!connectionService.isGoogleConnected()) {
            return Redirects.SIGN_IN;
        }
        Person person = google.plusOperations().getGoogleProfile();
        AppUser appUser = appUserService.registerOrFind(person);
        SecurityUtils.signIn(appUser);
        return Redirects.PROFILE;
    }

    @Inject
    public void setAppUserService(AppUserService appUserService) {
        Assert.notNull(appUserService, AppUserService.class);
        this.appUserService = appUserService;
    }

    @Inject
    public void setConnectionService(ConnectionService connectionService) {
        Assert.notNull(connectionService, ConnectionService.class);
        this.connectionService = connectionService;
    }

    @Inject
    public void setFacebook(Facebook facebook) {
        Assert.notNull(facebook, Facebook.class);
        this.facebook = facebook;
    }

    @Inject
    public void setTwitter(Twitter twitter) {
        Assert.notNull(twitter, Twitter.class);
        this.twitter = twitter;
    }

    @Inject
    public void setLinkedIn(LinkedIn linkedIn) {
        Assert.notNull(linkedIn, LinkedIn.class);
        this.linkedIn = linkedIn;
    }

    @Inject
    public void setGoogle(Google google) {
        Assert.notNull(google, Google.class);
        this.google = google;
    }

    @ModelAttribute("registrationForm")
    public RegistrationForm loadEmptyRegistrationForm() {
        logger.debug("Initialized empty registration form");
        return new RegistrationForm();
    }
}
