package net.samplelogin.controller;

import net.samplelogin.domain.AppUser;
import net.samplelogin.form.RegistrationForm;
import net.samplelogin.service.AppUserService;
import net.samplelogin.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private AppUserService appUserService;

    @PostMapping
    public String register(RegistrationForm registrationForm) {
        // TODO validate before
        AppUser user = appUserService.register(registrationForm);
        if (user != null) {
            logger.debug("User {} registered successfully", user.getEmail());
        } else {
            logger.debug("User {} was not registered", registrationForm.getEmail());
        }
        return null;
    }

    @Inject
    public void setAppUserService(AppUserService appUserService) {
        Assert.notNull(appUserService, AppUserService.class);
        this.appUserService = appUserService;
    }
}
