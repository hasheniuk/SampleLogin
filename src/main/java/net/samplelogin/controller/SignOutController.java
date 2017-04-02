package net.samplelogin.controller;

import net.samplelogin.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/signout")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class SignOutController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @GetMapping
    public String signOut (HttpServletRequest request, HttpServletResponse response) {
        String currentUserEmail = SecurityUtils.getCurrentUserEmail();
        new SecurityContextLogoutHandler().logout(request, response, SecurityUtils.getAuthentication());
        logger.debug("User: {} signed out", currentUserEmail);
        return Redirects.SIGN_OUT;
    }
}
