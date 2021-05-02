package de.yjulian.merly.subsystem.service;

public interface Service {

    long initialDelayMs();

    long serviceDelayMs();

    void onServiceExecute();

}
