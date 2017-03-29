package net.samplelogin.controller;

import net.samplelogin.util.Assert;
import net.samplelogin.util.SecurityUtils;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/signout")
public class SignOutController {

    private ConnectionRepository connectionRepository;

    @GetMapping
    public String signOut (HttpServletRequest request, HttpServletResponse response) {
        if (SecurityUtils.isAuthenticated()) {
            connectionRepository.findAllConnections().keySet().forEach(providerId -> {
                connectionRepository.removeConnections(providerId);
            });
            new SecurityContextLogoutHandler().logout(request, response, SecurityUtils.getAuthentication());
        }
        return Redirects.SIGN_OUT;
    }

    @Inject
    public void setConnectionRepository(ConnectionRepository connectionRepository) {
        Assert.notNull(connectionRepository, ConnectionRepository.class);
        this.connectionRepository = connectionRepository;
    }
}
