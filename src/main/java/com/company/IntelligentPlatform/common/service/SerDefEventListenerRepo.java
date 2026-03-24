package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;

/**
 * Utility method for register event listener in repository
 *
 * @author Zhang, Hang
 */
@Service
public class SerDefEventListenerRepo {

    public List<ISerEventListener> addListener(ISerEventListener listener, List<ISerEventListener> listenerList) {
        if (listenerList == null) {
            listenerList = new ArrayList<ISerEventListener>();
        }
        if (listenerList.contains(listener)) {
            return listenerList;
        }
        listenerList.add(listener);
        return listenerList;
    }

    public List<ISerEventListener> removeListener(ISerEventListener listener, List<ISerEventListener> listenerList) {
        if (ServiceCollectionsHelper.checkNullList(listenerList)) {
            return new ArrayList<ISerEventListener>();
        }
        if (!listenerList.contains(listener)) {
            return listenerList;
        }
        listenerList.remove(listener);
        return listenerList;
    }

    public void notifyListener(IEvent event, List<ISerEventListener> listenerList) throws SerEventException {
        if (ServiceCollectionsHelper.checkNullList(listenerList)) {
            return;
        }
        for (ISerEventListener listener : listenerList) {
            listener.handleEvent(event);
        }
    }

}
