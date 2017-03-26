package net.samplelogin.controller;

import net.samplelogin.service.ConnectionService;
import net.samplelogin.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller
@RequestMapping("/")
public class IndexController {
    private ConnectionService connectionService;

    @GetMapping
    public String redirect() {
        if (!connectionService.isAnyConnected()) {
            return Redirects.AUTH;
        }
        return Redirects.PROFILE;
    }

    @Inject
    public void setConnectionService(ConnectionService connectionService) {
        Assert.notNull(connectionService, ConnectionService.class);
        this.connectionService = connectionService;
    }
}
