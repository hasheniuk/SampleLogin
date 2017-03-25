package net.samplelogin.service.impl;

import net.samplelogin.service.ConnectionService;
import net.samplelogin.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Objects;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private ConnectionRepository connectionRepository;

    @Override
    public boolean isConnected(Class<?> provider) {
        boolean connected = connectionRepository.findConnections(provider) != null;
        logConnectionStatus(provider, connected);
        return connected;
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
    public boolean isAnyConnected(Class<?> ... providers) {
        if (providers.length > 0) {
            return Arrays.stream(providers)
                    .filter(Objects::nonNull)
                    .anyMatch(this::isConnected);
        }
        MultiValueMap<String, Connection<?>> connections = connectionRepository.findAllConnections();
        logger.debug("{} connections found", connections.size());
        return !connections.isEmpty();
    }

    private void logConnectionStatus(Class<?> provider, boolean connected) {
        String user = "[username]"; // TODO log status for user
        String providerName = provider.getSimpleName();
        String status = connected ? "connected" : "disconnected";
        logger.debug("Current connection: user: {}, provider: {}, status: {}", user, providerName, status);
    }

    @Inject
    public void setConnectionRepository(ConnectionRepository connectionRepository) {
        Assert.notNull(connectionRepository, ConnectionRepository.class);
        this.connectionRepository = connectionRepository;
    }
}
