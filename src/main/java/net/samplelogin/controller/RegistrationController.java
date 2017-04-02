package net.samplelogin.controller;

import net.samplelogin.domain.AppUser;
import net.samplelogin.form.RegistrationForm;
import net.samplelogin.service.AppUserService;
import net.samplelogin.util.Assert;
import net.samplelogin.util.RecaptchaUtils;
import net.samplelogin.util.SecurityUtils;
import net.samplelogin.validator.RegistrationFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private RegistrationFormValidator registrationFormValidator;
    private AppUserService appUserService;
    private String recaptchaDataSecret;

    @PostMapping
    public String register(@ModelAttribute RegistrationForm registrationForm, BindingResult result, Model model,
                           @RequestParam("g-recaptcha-response") String gRecaptchaResponse) {
        boolean robot = RecaptchaUtils.isRobot(gRecaptchaResponse, recaptchaDataSecret);
        registrationFormValidator.validate(registrationForm, result);
        if (robot || result.hasErrors()) {
            logger.debug("Return to login page with errors");
            model.addAttribute("registration", true);
            model.addAttribute("robot", robot);
            return Views.LOGIN;
        }
        AppUser appUser = appUserService.register(registrationForm);
        SecurityUtils.signIn(appUser);
        return Redirects.PROFILE;
    }

    @Inject
    public void setRegistrationFormValidator(RegistrationFormValidator registrationFormValidator) {
        Assert.notNull(registrationFormValidator, RegistrationFormValidator.class);
        this.registrationFormValidator = registrationFormValidator;
    }

    @Inject
    public void setAppUserService(AppUserService appUserService) {
        Assert.notNull(appUserService, AppUserService.class);
        this.appUserService = appUserService;
    }

    @Value("${recaptcha.data.secret}")
    public void setRecaptchaDataSecret(String recaptchaDataSecret) {
        Assert.notNull(recaptchaDataSecret, "Missed environment variable recaptcha.data.secret");
        this.recaptchaDataSecret = recaptchaDataSecret;
    }
}
