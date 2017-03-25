package net.samplelogin.controller;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.stereotype.Controller;

@Controller
public class ConnectControllerExtended extends ConnectController {

    public ConnectControllerExtended(ConnectionFactoryLocator locator, ConnectionRepository connectionRepository) {
        super(locator, connectionRepository);
    }

    @Override
    protected String connectedView(String providerId) {
        return Redirections.AUTH + "/" + providerId;
    }
}
