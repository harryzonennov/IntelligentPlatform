package com.company.IntelligentPlatform.common.service;

public interface ISerEventSource {

    void addListener(ISerEventListener listener);

    void removeListener(ISerEventListener listener);

    void notifyListener(IEvent event) throws SerEventException;

}
