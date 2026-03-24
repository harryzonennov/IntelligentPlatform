package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class ServiceEntityConfigureProxyService {

    /**
     * Method to get the child configure map list (traverse into deeper layer)
     * from this Node name
     *
     * @param rawConfigureMapList
     * @param nodeName
     * @param includeFlag
     * @return
     */
    public static List<ServiceEntityConfigureMap> getChildConfigureMap(
            List<ServiceEntityConfigureMap> rawConfigureMapList,
            String nodeName, boolean includeFlag) {
        if (ServiceCollectionsHelper.checkNullList(rawConfigureMapList)) {
            return null;
        }
        List<ServiceEntityConfigureMap> resultList = new ArrayList<>();
        for (ServiceEntityConfigureMap tmpConfigureMap : rawConfigureMapList) {
            if (checkParentNodeConfigureMap(tmpConfigureMap, nodeName,
                    rawConfigureMapList)) {
                resultList.add(tmpConfigureMap);
            }
            if (includeFlag) {
                if (nodeName.equals(tmpConfigureMap.getNodeName())) {
                    resultList.add(tmpConfigureMap);
                }
            }
        }
        return resultList;
    }

    /**
     * Method to get the parent configure map list (traverse into deeper layer)
     * from this Node name
     *
     * @param rawConfigureMapList
     * @param nodeName
     * @param includeFlag
     * @return
     */
    public static List<ServiceEntityConfigureMap> getParentConfigureMap(
            List<ServiceEntityConfigureMap> rawConfigureMapList,
            ServiceEntityConfigureMap currentNode, boolean includeFlag) {
        if (ServiceCollectionsHelper.checkNullList(rawConfigureMapList)) {
            return null;
        }
        List<ServiceEntityConfigureMap> resultList = new ArrayList<>();
        for (ServiceEntityConfigureMap tmpConfigureMap : rawConfigureMapList) {
            if (checkParentNodeConfigureMap(currentNode,
                    tmpConfigureMap.getNodeName(), rawConfigureMapList)) {
                resultList.add(tmpConfigureMap);
            }
        }
        if (includeFlag) {
            resultList.add(currentNode);
        }
        return resultList;
    }

    /**
     * Method to get the child configure map list from this Node name
     *
     * @param rawConfigureMapList
     * @param nodeName
     * @param includeFlag
     * @return
     */
    public static List<ServiceEntityConfigureMap> getDirectChildConfigureMapList(
            List<ServiceEntityConfigureMap> rawConfigureMapList, String nodeName) {
        if (ServiceCollectionsHelper.checkNullList(rawConfigureMapList)) {
            return null;
        }
        List<ServiceEntityConfigureMap> resultList = new ArrayList<>();
        for (ServiceEntityConfigureMap tmpConfigureMap : rawConfigureMapList) {
            if (nodeName.equals(tmpConfigureMap.getParentNodeName())) {
                resultList.add(tmpConfigureMap);
            }
        }
        return resultList;
    }

    public static List<ServiceEntityConfigureMap> getOneLayerChildConfigureMapList(
            List<ServiceEntityConfigureMap> rawConfigureMapList) {
        List<ServiceEntityConfigureMap> resultList = new ArrayList<>();
        List<ServiceEntityConfigureMap> leavesConfigureList = getLeavesConfigureMapList(rawConfigureMapList);
        if (ServiceCollectionsHelper.checkNullList(leavesConfigureList)) {
            return null;
        }
        for (ServiceEntityConfigureMap leavesConfigureMap : leavesConfigureList) {
            ServiceEntityConfigureMap parentConfigureMap = filterConfigureMapOnline(
                    rawConfigureMapList, leavesConfigureMap.getParentNodeName());
            if (!resultList.contains(parentConfigureMap)) {
                resultList.add(parentConfigureMap);
            }
        }
        return resultList;
    }

    /**
     * Get the list of Leaves configure map, which configure map nodes doesn't
     * have children nodes
     *
     * @param rawConfigureMapList
     * @return
     */
    public static List<ServiceEntityConfigureMap> getLeavesConfigureMapList(
            List<ServiceEntityConfigureMap> rawConfigureMapList) {
        List<ServiceEntityConfigureMap> resultList = new ArrayList<>();
        for (ServiceEntityConfigureMap serConfigureMap : rawConfigureMapList) {
            if (!hasChildConfigureMap(serConfigureMap, rawConfigureMapList)) {
                resultList.add(serConfigureMap);
            }
        }
        return resultList;
    }

    private static boolean hasChildConfigureMap(
            ServiceEntityConfigureMap serConfigureMap,
            List<ServiceEntityConfigureMap> rawConfigureMapList) {
        for (ServiceEntityConfigureMap tmpConfigureMap : rawConfigureMapList) {
            if (tmpConfigureMap.getParentNodeName().equals(
                    serConfigureMap.getNodeName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param rawConfigureMapList
     * @param nodeName
     * @param includeFlag
     * @return
     */
    public static List<ServiceEntityConfigureMap> getNextConfigureMap(
            List<ServiceEntityConfigureMap> rawConfigureMapList,
            String nodeName, boolean includeFlag) {
        if (ServiceCollectionsHelper.checkNullList(rawConfigureMapList)) {
            return null;
        }
        List<ServiceEntityConfigureMap> resultList = new ArrayList<>();
        for (ServiceEntityConfigureMap tmpConfigureMap : rawConfigureMapList) {
            if (nodeName.equals(tmpConfigureMap.getParentNodeName())) {
                resultList.add(tmpConfigureMap);
            }
            if (includeFlag) {
                if (nodeName.equals(tmpConfigureMap.getNodeName())) {
                    resultList.add(tmpConfigureMap);
                }
            }
        }
        return resultList;
    }

    private static boolean checkParentNodeConfigureMap(
            ServiceEntityConfigureMap serviceEntityConfigureMap,
            String parentNodeName,
            List<ServiceEntityConfigureMap> rawConfigureMapList) {
        if (parentNodeName.equals(serviceEntityConfigureMap.getNodeName())) {
            return false;
        }
        if (parentNodeName
                .equals(serviceEntityConfigureMap.getParentNodeName())) {
            return true;
        }
        ServiceEntityConfigureMap parentServiceEntityConfigureMap = filterConfigureMapOnline(
                rawConfigureMapList,
                serviceEntityConfigureMap.getParentNodeName());
        if (parentServiceEntityConfigureMap != null) {
            return checkParentNodeConfigureMap(parentServiceEntityConfigureMap,
                    parentNodeName, rawConfigureMapList);
        } else {
            return false;
        }
    }

    /**
     * Filter the configure map instance by checking node name
     *
     * @param rawConfigureMapList
     * @param nodeName
     * @return
     */
    public static ServiceEntityConfigureMap filterConfigureMapOnline(
            List<ServiceEntityConfigureMap> rawConfigureMapList, String nodeName) {
        if (ServiceCollectionsHelper.checkNullList(rawConfigureMapList)) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(nodeName)) {
            return null;
        }
        for (ServiceEntityConfigureMap tmpConfigureMap : rawConfigureMapList) {
            if (nodeName.equals(tmpConfigureMap.getNodeName())) {
                return tmpConfigureMap;
            }
        }
        return null;
    }

}
