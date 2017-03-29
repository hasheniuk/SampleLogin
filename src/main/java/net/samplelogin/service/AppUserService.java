package net.samplelogin.service;

import net.samplelogin.domain.AppUser;
import net.samplelogin.form.RegistrationForm;
import org.springframework.social.facebook.api.User;

public interface AppUserService {
    AppUser register(RegistrationForm registrationForm);
    AppUser registerOrFind(User facebookUser);
}
