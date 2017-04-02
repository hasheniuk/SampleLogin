package net.samplelogin.service.impl;

import net.samplelogin.service.ConnectionService;
import net.samplelogin.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.google.api.Google;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private ConnectionRepository connectionRepository;

    @Override
    public boolean isFacebookConnected() {
        return isConnected(Facebook.class);
    }

    @Override
    public boolean isTwitterConnected() {
        return isConnected(Twitter.class);
    }

    @Override
    public boolean isLinkedInConnected() {
        return isConnected(LinkedIn.class);
    }

    @Override
    public boolean isGoogleConnected() {
        return isConnected(Google.class);
    }

    private boolean isConnected(Class<?> provider) {
        boolean connected = connectionRepository.findConnections(provider) != null;
        logConnectionStatus(provider, connected);
        return connected;
    }

    private void logConnectionStatus(Class<?> provider, boolean connected) {
        String providerName = provider.getSimpleName();
        String status = connected ? "connected" : "disconnected";
        logger.debug("Check existing connection to provider: {}, status: {}", providerName, status);
    }

    @Inject
    public void setConnectionRepository(ConnectionRepository connectionRepository) {
        Assert.notNull(connectionRepository, ConnectionRepository.class);
        this.connectionRepository = connectionRepository;
    }
}
