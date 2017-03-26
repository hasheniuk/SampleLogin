package net.samplelogin.service;

import net.samplelogin.domain.AppUser;
import net.samplelogin.form.RegistrationForm;

public interface AppUserService {
    AppUser register(RegistrationForm registrationForm);
}
