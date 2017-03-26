package net.samplelogin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.stereotype.Controller;

import java.lang.invoke.MethodHandles;

@Controller
public class ConnectControllerExtended extends ConnectController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public ConnectControllerExtended(ConnectionFactoryLocator locator, ConnectionRepository connectionRepository) {
        super(locator, connectionRepository);
    }

    @Override
    protected String connectedView(String providerId) {
        logger.debug("Redirected provider: {} to auth controller", providerId);
        return Redirects.AUTH + "/" + providerId;
    }
}
