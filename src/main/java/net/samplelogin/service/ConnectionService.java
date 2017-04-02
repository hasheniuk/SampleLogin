package net.samplelogin.service;

public interface ConnectionService {
    boolean isFacebookConnected();
    boolean isTwitterConnected();
    boolean isLinkedInConnected();
    boolean isGoogleConnected();
}
