package net.samplelogin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    //TODO logout

    @GetMapping
    public String getView(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            logger.debug("User not authenticated, redirect to auth page");
            return Redirects.AUTH;
        }
        String email = auth.getName();
        logger.debug("User profile [email: {}]", email);
        model.addAttribute("email", email);
        return Views.PROFILE;
    }

}
