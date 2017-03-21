package net.samplelogin.controller;

import net.samplelogin.util.ClassUtils;
import org.slf4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(ClassUtils.getCurrentClassName());

    private static final String INDEX_REDIRECT = "redirect:/";

    @GetMapping("/facebook")
    public String authFacebook() {
        logger.debug("facebook auth");
        return INDEX_REDIRECT;
    }

    @GetMapping("/twitter")
    public String authTwitter() {
        logger.debug("twitter auth");
        return INDEX_REDIRECT;
    }

    @GetMapping("/linkedin")
    public String authLinkedIn() {
        logger.debug("linkedin auth");
        return INDEX_REDIRECT;
    }

    @GetMapping("/google")
    public String authGoogle() {
        logger.debug("google auth");
        return INDEX_REDIRECT;
    }
}
