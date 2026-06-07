package com.company.IntelligentPlatform.platform.service;

public interface ISerEventSource {

    void addListener(ISerEventListener listener);

    void removeListener(ISerEventListener listener);

    void notifyListener(IEvent event) throws SerEventException;

}
