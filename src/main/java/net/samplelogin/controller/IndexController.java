package net.samplelogin.controller;

import net.samplelogin.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/")
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @GetMapping
    public String redirect() {
        if (SecurityUtils.isAuthenticated()) {
            logger.info("redirect to profile page");
            return Redirects.PROFILE;
        }
        logger.info("redirect to auth page");
        return Redirects.AUTH;
    }
}
