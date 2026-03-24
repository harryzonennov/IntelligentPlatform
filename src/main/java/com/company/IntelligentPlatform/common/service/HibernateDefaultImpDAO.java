package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * TODO-DAO: Stub replacing legacy platform.foundation.DAO.HibernateDefaultImpDAO.
 * All methods return empty results — must be replaced with JPA-based implementations.
 */
@Component
public class HibernateDefaultImpDAO {

    public List<ServiceEntityNode> getEntityNodeListBySQLCommand(String sqlCommand) {
        // TODO-DAO: implement with JPA native query
        return new ArrayList<>();
    }

    public List<String> getUUIDListBySQLCommand(String sqlCommand) {
        // TODO-DAO: implement with JPA native query
        return new ArrayList<>();
    }

    public int getRecordNumBySQLCommand(String sqlCommand) {
        // TODO-DAO: implement with JPA native query
        return 0;
    }

    public List<ServiceEntityNode> getEntityNodeListByKey(String keyValue, String fieldName, String nodeName) {
        // TODO-DAO: implement with JPA query
        return new ArrayList<>();
    }

    public List<ServiceEntityNode> getEntityNodeListByKey(String keyValue, String fieldName, String nodeName, boolean flag) {
        // TODO-DAO: implement with JPA query
        return new ArrayList<>();
    }

    public List<String> getStringListBySQLCommand(String sqlCommand) {
        // TODO-DAO: implement with JPA native query
        return new ArrayList<>();
    }

    public List<?> executeBySQLCommand(String sqlCommand) {
        // TODO-DAO: implement with JPA native query
        return new ArrayList<>();
    }
}
