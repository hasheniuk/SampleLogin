package net.samplelogin.controller;

import net.samplelogin.domain.AppUser;
import net.samplelogin.service.AppUserService;
import net.samplelogin.service.ConnectionService;
import net.samplelogin.util.Assert;
import net.samplelogin.util.FacebookUtils;
import net.samplelogin.util.SecurityUtils;
import net.samplelogin.util.TwitterProfileWithEmail;
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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/auth")
public class AuthController {
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
            return Redirects.PROFILE;
        }
        return Views.LOGIN;
    }

    @GetMapping("/facebook")
    public String authFacebook() {
        if (SecurityUtils.isAuthenticated()) {
            return Redirects.PROFILE;
        }
        if (!connectionService.isConnected(Facebook.class)) {
            return Redirects.AUTH;
        }
        User facebookUser = facebook.userOperations().getUserProfile();
        AppUser appUser = appUserService.registerOrFind(facebookUser);
        SecurityUtils.signInUser(appUser);
        logger.debug("Facebook user email: {}", appUser.getEmail());
        return Redirects.PROFILE;
    }

    @GetMapping("/twitter")
    public String authTwitter() {
        if (SecurityUtils.isAuthenticated()) {
            return Redirects.PROFILE;
        }
        if (!connectionService.isTwitterConnected()) {
            return Redirects.AUTH;
        }
        TwitterProfileWithEmail twitterProfile = twitter.restOperations().getForObject("https://api.twitter.com/1.1/account/verify_credentials.json?include_email=true", TwitterProfileWithEmail.class);
        logger.debug("Twitter user email: {}", twitterProfile.getEmail());
        return Redirects.PROFILE;
    }

    @GetMapping("/linkedin")
    public String authLinkedIn() {
        if (SecurityUtils.isAuthenticated()) {
            return Redirects.PROFILE;
        }
        if (!connectionService.isLinkedInConnected()) {
            return Redirects.AUTH;
        }
        LinkedInProfile linkedInProfile = linkedIn.profileOperations().getUserProfile();
        logger.debug("LinkedIn user email: {}", linkedInProfile.getEmailAddress());
        return Redirects.PROFILE;
    }

    @GetMapping("/google")
    public String authGoogle() {
        if (SecurityUtils.isAuthenticated()) {
            return Redirects.PROFILE;
        }
        if (!connectionService.isConnected(Google.class)) {
            return Redirects.AUTH;
        }
        Person person = google.plusOperations().getGoogleProfile();
        logger.debug("Google person email: {}", person.getAccountEmail());
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
}
