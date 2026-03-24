package com.company.IntelligentPlatform.common.service;

public interface ISerEventListener {

    void handleEvent(IEvent event) throws SerEventException;

}
