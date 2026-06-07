package com.company.IntelligentPlatform.platform.service;

public interface ISerEventListener {

    void handleEvent(IEvent event) throws SerEventException;

}
