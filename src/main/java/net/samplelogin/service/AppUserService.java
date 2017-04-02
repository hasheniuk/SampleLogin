package net.samplelogin.service;

import net.samplelogin.domain.AppUser;
import net.samplelogin.form.RegistrationForm;
import net.samplelogin.util.TwitterProfileWithEmail;
import org.springframework.social.facebook.api.User;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.linkedin.api.LinkedInProfile;

public interface AppUserService {
    AppUser register(RegistrationForm registrationForm);
    AppUser registerOrFind(User facebookUser);
    AppUser registerOrFind(TwitterProfileWithEmail twitterProfile);
    AppUser registerOrFind(LinkedInProfile linkedInProfile);
    AppUser registerOrFind(Person person);
}
