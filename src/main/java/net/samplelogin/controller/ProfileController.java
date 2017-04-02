package net.samplelogin.controller;

import net.samplelogin.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/profile")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @GetMapping
    public String getView(ModelMap model) {
        String email = SecurityUtils.getCurrentUserEmail();
        logger.debug("User profile [email: {}]", email);
        model.addAttribute("email", email);
        return Views.PROFILE;
    }
}
