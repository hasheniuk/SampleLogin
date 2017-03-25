package net.samplelogin.service;

public interface ConnectionService {
    boolean isConnected(Class<?> c);
    boolean isTwitterConnected();
    boolean isLinkedInConnected();
    boolean isAnyConnected(Class<?> ... providers);
}
