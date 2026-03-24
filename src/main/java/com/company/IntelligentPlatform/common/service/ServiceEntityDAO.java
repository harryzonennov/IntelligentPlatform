package com.company.IntelligentPlatform.common.service;

import java.util.List;

import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * TODO-DAO: Interface replacing legacy HibernateDefaultImpDAO used as serviceEntityDAO.
 * Implementations must be provided for each module's JPA repository.
 */
public interface ServiceEntityDAO {

    String ORDER_DESC = "DESC";
    String ORDER_ASC = "ASC";

    void insertEntity(ServiceEntityNode seNode);

    void insertEntity(List<ServiceEntityNode> seNodeList);

    void updateEntity(ServiceEntityNode seNode);

    void updateEntity(List<ServiceEntityNode> seNodeList);

    void deleteEntityNode(ServiceEntityNode seNode);

    void deleteEntityNodeList(List<ServiceEntityNode> seNodeList);

    List<ServiceEntityNode> getEntityNodeListByKey(Object keyValue, String keyName, String nodeName);

    List<ServiceEntityNode> getEntityNodeListByKey(Object keyValue, String keyName, String nodeName, boolean preciseMatchFlag);

    List<ServiceEntityNode> getEntityNodeListByKey(Object keyValue, String keyName, String nodeName,
            int startIndex, int limitNumber, String orderByField, String orderDir, String client, boolean preciseMatchFlag);

    void deleteEntityNodeByKey(String keyValue, String keyName, String nodeName);

    List<ServiceEntityNode> getEntityNodeListByKeyList(List<ServiceBasicKeyStructure> keyList, String nodeName, boolean preciseMatchFlag);

    List<ServiceEntityNode> getEntityNodeListByKeyList(List<ServiceBasicKeyStructure> keyList, String nodeName, boolean preciseMatchFlag, boolean flag2);

    List<String> getEntityFieldListByKeyList(List<ServiceBasicKeyStructure> keyList, String nodeName, String fieldName, boolean preciseMatchFlag);

    int getRecordNum(Object keyValue, String keyName, String nodeName, String client, boolean preciseMatchFlag);

    int getRecordNum(List<ServiceBasicKeyStructure> keyList, String nodeName, boolean preciseMatchFlag);

    void admDeleteEntityByKey(String keyValue, String keyName, String nodeName);

    void archiveDeleteEntityByKey(String keyValue, String keyName, String nodeName, String logonUserUUID, String resOrgUUID);

    void recoverEntityByKey(String keyValue, String keyName, String nodeName, String logonUserUUID, String resOrgUUID);
}
