package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * TODO-DAO: Stub replacing legacy LockObjectDAO.
 */
@Component
public class LockObjectDAO {

    public List<ServiceEntityNode> getEntityNodeListByKey(String keyValue, String fieldName, String nodeName) {
        return new ArrayList<>();
    }

    public void insertEntity(List<ServiceEntityNode> nodeList) {
        // TODO-DAO: implement
    }

    public void insertEntity(ServiceEntityNode node) {
        // TODO-DAO: implement
    }

    public void deleteEntityNodeByKey(String keyValue, String fieldName, String nodeName) {
        // TODO-DAO: implement
    }
}
