package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility Class for clone Service Model Or Service UI Model
 */
@Service
public class ServiceModuleCloneService {

    /**
     * Core Logic to Deeply clone Service Model
     *
     * @param serviceModule
     * @param updateUUID:   in case need to update all new instance UUID
     * @return
     * @throws ServiceModuleProxyException
     */
    public static ServiceModule deepCloneServiceModule(
            ServiceModule serviceModule, boolean updateUUID)
            throws ServiceModuleProxyException {
        ServiceModuleCloneResult serviceModuleCloneResult = deepCloneServiceModuleResult(serviceModule, updateUUID);
        return serviceModuleCloneResult.getServiceModule();
    }

    public static class ServiceModuleCloneResult {

        private ServiceModule serviceModule;

        private List<ServiceEntityNode> pluckSEList;

        private List<UUIDUnion> uuidUnionList;

        public ServiceModuleCloneResult() {
        }

        public ServiceModuleCloneResult(ServiceModule serviceModule, List<ServiceEntityNode> pluckSEList,
                                        List<UUIDUnion> uuidUnionList) {
            this.serviceModule = serviceModule;
            this.pluckSEList = pluckSEList;
            this.uuidUnionList = uuidUnionList;
        }

        public ServiceModule getServiceModule() {
            return serviceModule;
        }

        public void setServiceModule(ServiceModule serviceModule) {
            this.serviceModule = serviceModule;
        }

        public List<ServiceEntityNode> getPluckSEList() {
            return pluckSEList;
        }

        public void setPluckSEList(List<ServiceEntityNode> pluckSEList) {
            this.pluckSEList = pluckSEList;
        }

        public List<UUIDUnion> getUuidUnionList() {
            return uuidUnionList;
        }

        public void setUuidUnionList(List<UUIDUnion> uuidUnionList) {
            this.uuidUnionList = uuidUnionList;
        }
    }

    /**
     * Core Logic to Deeply clone Service Model
     *
     * @param serviceModule
     * @param updateUUID:   in case need to update all new instance UUID
     * @return
     * @throws ServiceModuleProxyException
     */
    public static ServiceModuleCloneResult deepCloneServiceModuleResult(
            ServiceModule serviceModule, boolean updateUUID)
            throws ServiceModuleProxyException {
        try {
            /*
             * [Step1] Get the core seNode value from new model and back model
             */
            List<ServiceEntityNode> pluckSEList = new ArrayList<>();
            ServiceModule serviceModuleBack = serviceModule.getClass()
                    .newInstance();
            Class<?> serviceModuleType = serviceModule.getClass();
            List<Field> flatTypeFields = ServiceModuleProxy.getNonListSubFields(serviceModuleType);
            if (!ServiceCollectionsHelper.checkNullList(flatTypeFields)) {
                for (Field flatField : flatTypeFields) {
                    flatField.setAccessible(true);
                    Object rawValue = flatField.get(serviceModule);
                    if (rawValue instanceof ServiceEntityNode) {
                        ServiceEntityNode serviceEntityNode = (ServiceEntityNode) flatField
                                .get(serviceModule);
                        ServiceEntityNode clonedSEEntityNode = (ServiceEntityNode) serviceEntityNode.clone();
                        ServiceCollectionsHelper.mergeToList(pluckSEList, clonedSEEntityNode);
                        flatField.set(serviceModuleBack, clonedSEEntityNode);
                    }
                }
            }
            /*
             * [Step2] Log for sub list fields
             */
            List<Field> listTypeFields = ServiceModuleProxy
                    .getListTypeFields(serviceModule.getClass());
            if (!ServiceCollectionsHelper.checkNullList(listTypeFields)) {
                for (Field listField : listTypeFields) {
                    if (ServiceEntityFieldsHelper
                            .checkSuperClassExtends(ServiceEntityFieldsHelper
                                            .getListSubType(listField),
                                    ServiceEntityNode.class.getSimpleName())) {
                        List<ServiceEntityNode> subListValueBack = deepCloneListServiceEntityNode(
                                listField, serviceModule);
                        ServiceCollectionsHelper.mergeToList(pluckSEList, subListValueBack);
                        try {
                            listField.setAccessible(true);
                            if (!ServiceCollectionsHelper
                                    .checkNullList(subListValueBack)) {
                                listField.set(serviceModuleBack,
                                        subListValueBack);
                            }
                        } catch (IllegalArgumentException
                                | IllegalAccessException e) {
                            continue;
                        }
                    }
                    if (ServiceEntityFieldsHelper
                            .checkSuperClassExtends(ServiceEntityFieldsHelper
                                            .getListSubType(listField),
                                    ServiceModule.class.getSimpleName())) {
                        /*
                         * [Step 2.5] In case sub list is for service module
                         * node list
                         */
                        IServiceModuleFieldConfig subListFieldConfig = listField
                                .getAnnotation(IServiceModuleFieldConfig.class);
                        if (subListFieldConfig == null) {
                            throw new ServiceModuleProxyException(
                                    ServiceUIModuleProxyException.PARA_NOANNOTATION,
                                    listField.getName());
                        }
                        List<?> subListValueBack = deepCloneListServiceModule(
                                listField, serviceModule, pluckSEList);
                        try {
                            listField.setAccessible(true);
                            if (!ServiceCollectionsHelper
                                    .checkNullList(subListValueBack)) {
                                listField.set(serviceModuleBack,
                                        subListValueBack);
                            }
                        } catch (IllegalArgumentException
                                | IllegalAccessException e) {
                            listField.setAccessible(true);
                            if (!ServiceCollectionsHelper
                                    .checkNullList(subListValueBack)) {
                                listField.set(serviceModuleBack,
                                        subListValueBack);
                            }
                        }
                    }
                }
            }
            List<UUIDUnion> resultList = new ArrayList<>();
            if (updateUUID) {
                // In case need to update all cloned instance UUID
                resultList = deepUpdateUUIDForSENodeList(pluckSEList);
            }
            return new ServiceModuleCloneResult(serviceModuleBack, pluckSEList, resultList);
        } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
            return null;
        }
    }

    public static String findNewUUID(String oldUUID, List<UUIDUnion> uuidUnionList){
        if(ServiceCollectionsHelper.checkNullList(uuidUnionList)){
            return null;
        }
        List<UUIDUnion> resultList = uuidUnionList.stream().filter(uuidUnion -> {
            return oldUUID.equals(uuidUnion.getOldUUID());
        }).collect(Collectors.toList());
        if(ServiceCollectionsHelper.checkNullList(resultList)){
            return null;
        }
        return resultList.get(0).getNewUUID();
    }

    private static ServiceModule deepCloneServiceModuleInternal(
            ServiceModule serviceModule, List<ServiceEntityNode> pluckSEList)
            throws ServiceModuleProxyException {
        try {
            /*
             * [Step1] Get the core seNode value from new model and back model
             */
            ServiceModule serviceModuleBack = serviceModule.getClass()
                    .newInstance();
            Class<?> serviceModuleType = serviceModule.getClass();
            List<Field> flatTypeFields = ServiceModuleProxy.getNonListSubFields(serviceModuleType);
            if (!ServiceCollectionsHelper.checkNullList(flatTypeFields)) {
                for (Field flatField : flatTypeFields) {
                    flatField.setAccessible(true);
                    Object rawValue = flatField.get(serviceModule);
                    if (rawValue instanceof ServiceEntityNode) {
                        ServiceEntityNode serviceEntityNode = (ServiceEntityNode) flatField
                                .get(serviceModule);
                        ServiceEntityNode clonedSEEntityNode = (ServiceEntityNode) serviceEntityNode.clone();
                        ServiceCollectionsHelper.mergeToList(pluckSEList, clonedSEEntityNode);
                        flatField.set(serviceModuleBack, clonedSEEntityNode);
                    }
                }
            }
            /*
             * [Step2] Log for sub list fields
             */
            List<Field> listTypeFields = ServiceModuleProxy
                    .getListTypeFields(serviceModule.getClass());
            if (!ServiceCollectionsHelper.checkNullList(listTypeFields)) {
                for (Field listField : listTypeFields) {
                    if (ServiceEntityFieldsHelper
                            .checkSuperClassExtends(ServiceEntityFieldsHelper
                                            .getListSubType(listField),
                                    ServiceEntityNode.class.getSimpleName())) {
                        List<ServiceEntityNode> subListValueBack = deepCloneListServiceEntityNode(
                                listField, serviceModule);
                        ServiceCollectionsHelper.mergeToList(pluckSEList, subListValueBack);
                        try {
                            listField.setAccessible(true);
                            if (!ServiceCollectionsHelper
                                    .checkNullList(subListValueBack)) {
                                listField.set(serviceModuleBack,
                                        subListValueBack);
                            }
                        } catch (IllegalArgumentException
                                | IllegalAccessException e) {
                            continue;
                        }
                    }
                    if (ServiceEntityFieldsHelper
                            .checkSuperClassExtends(ServiceEntityFieldsHelper
                                            .getListSubType(listField),
                                    ServiceModule.class.getSimpleName())) {
                        /*
                         * [Step 2.5] In case sub list is for service module
                         * node list
                         */
                        IServiceModuleFieldConfig subListFieldConfig = listField
                                .getAnnotation(IServiceModuleFieldConfig.class);
                        if (subListFieldConfig == null) {
                            throw new ServiceModuleProxyException(
                                    ServiceUIModuleProxyException.PARA_NOANNOTATION,
                                    listField.getName());
                        }
                        List<?> subListValueBack = deepCloneListServiceModule(
                                listField, serviceModule, pluckSEList);
                        try {
                            listField.setAccessible(true);
                            if (!ServiceCollectionsHelper
                                    .checkNullList(subListValueBack)) {
                                listField.set(serviceModuleBack,
                                        subListValueBack);
                            }
                        } catch (IllegalArgumentException
                                | IllegalAccessException e) {
                            listField.setAccessible(true);
                            if (!ServiceCollectionsHelper
                                    .checkNullList(subListValueBack)) {
                                listField.set(serviceModuleBack,
                                        subListValueBack);
                            }
                        }
                    }
                }
            }
            return serviceModuleBack;
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        }
    }

    private static List<ServiceEntityNode> deepCloneListServiceEntityNode(
            Field listField, ServiceModule serviceModule)
            throws IllegalArgumentException, IllegalAccessException {
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<ServiceEntityNode> subListValue = (List<ServiceEntityNode>) listField
                .get(serviceModule);
        List<ServiceEntityNode> subListValueBack = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(subListValue)) {
            for (int i = 0; i < subListValue.size(); i++) {
                ServiceEntityNode seNode = subListValue
                        .get(i);
                subListValueBack.add((ServiceEntityNode) seNode.clone());
            }
        }
        return subListValueBack;
    }

    @SuppressWarnings("unchecked")
    private static List<?> deepCloneListServiceModule(Field listField,
                                                      ServiceModule serviceModule, List<ServiceEntityNode> pluckSEList)
            throws IllegalArgumentException, IllegalAccessException {
        listField.setAccessible(true);
        List<?> subListValue = (List<?>) listField.get(serviceModule);
        @SuppressWarnings("rawtypes")
        List subListValueBack = new ArrayList();
        if (!ServiceCollectionsHelper.checkNullList(subListValue)) {
            for (int i = 0; i < subListValue.size(); i++) {
                ServiceModule subServiceModule = (ServiceModule) subListValue
                        .get(i);
                ServiceModule subServiceModuleBack;
                try {
                    subServiceModuleBack = deepCloneServiceModuleInternal(
                            subServiceModule, pluckSEList);
                } catch (ServiceModuleProxyException e) {
                    continue;
                }
                if (subServiceModuleBack != null) {
                    subListValueBack.add(subServiceModuleBack);
                }
            }
        }
        return subListValueBack;
    }

    /**
     * Entrance to deep update new UUID/rootNodeUUID/parentNodeUUID for SE node list plucking from one Service Model
     * Pay attention, all the node instance from one SE root should be involved, otherwise, parent-child relationship
     * might be lost if UUID/rootNodeUUID/parentNodeUUID are batchly updated.
     *
     * @param seNodeList
     */
    public static List<UUIDUnion> deepUpdateUUIDForSENodeList(
            List<ServiceEntityNode> seNodeList) {
        if (ServiceCollectionsHelper.checkNullList(seNodeList)) {
            return null;
        }
        // group by parent node uuid
        Map<String, List<ServiceEntityNode>> seNodeMapRoot = new HashMap<>();
        for (ServiceEntityNode serviceEntityNode : seNodeList) {
            if (seNodeMapRoot.containsKey(serviceEntityNode.getRootNodeUUID())) {
                List<ServiceEntityNode> tempList = seNodeMapRoot.get(serviceEntityNode.getParentNodeUUID());
                ServiceCollectionsHelper.mergeToList(tempList, serviceEntityNode);
            } else {
                seNodeMapRoot.put(serviceEntityNode.getParentNodeUUID(),
                        ServiceCollectionsHelper.asList(serviceEntityNode));
            }
        }
        Set<String> keySet = seNodeMapRoot.keySet();
        List<UUIDUnion> resultList = new ArrayList<>();
        for (String rootNodeUUID : keySet) {
            updateUUIDForList(rootNodeUUID, seNodeMapRoot.get(rootNodeUUID), resultList);
        }
        return resultList;
    }

    private static void updateUUIDForList(String rootNodeUUID, List<ServiceEntityNode> seNodeList,
                                          List<UUIDUnion> resultList) {
        if (ServiceCollectionsHelper.checkNullList(seNodeList)) {
            return;
        }
        // find root level node, to Update rootNodeUUID, parentNodeUUID, uuid.
        List<ServiceEntityNode> rootLevelNodeList = new ArrayList<>();
        // find 2nd level node, to Update rootNodeUUID, parentNodeUUID
        List<ServiceEntityNode> secondeLevelNodeList = new ArrayList<>();
        // find other deeper node list, to Update rootNodeUUID
        List<ServiceEntityNode> otherLevelNodeList = new ArrayList<>();
        for (ServiceEntityNode serviceEntityNode : seNodeList) {
            if (serviceEntityNode.getUuid().equals(rootNodeUUID)) {
                ServiceCollectionsHelper.mergeToList(rootLevelNodeList, serviceEntityNode);
                continue;
            }
            if (serviceEntityNode.getParentNodeUUID().equals(rootNodeUUID)) {
                ServiceCollectionsHelper.mergeToList(secondeLevelNodeList, serviceEntityNode);
                continue;
            }
            ServiceCollectionsHelper.mergeToList(otherLevelNodeList, serviceEntityNode);
        }
        /*
         * [Step2] Execute to update root node UUID
         */
        String newRootNodeUUID = ServiceEntityStringHelper.genUUID();
        if (!ServiceCollectionsHelper.checkNullList(rootLevelNodeList)) {
            for (ServiceEntityNode serviceEntityNode : rootLevelNodeList) {
                resultList.add(new UUIDUnion(serviceEntityNode.getUuid(), newRootNodeUUID));
                serviceEntityNode.setUuid(newRootNodeUUID);
                serviceEntityNode.setParentNodeUUID(newRootNodeUUID);
                serviceEntityNode.setRootNodeUUID(newRootNodeUUID);
            }
        }
        if (!ServiceCollectionsHelper.checkNullList(secondeLevelNodeList)) {
            for (ServiceEntityNode serviceEntityNode : secondeLevelNodeList) {
                serviceEntityNode.setParentNodeUUID(newRootNodeUUID);
                serviceEntityNode.setRootNodeUUID(newRootNodeUUID);
            }
        }
        if (!ServiceCollectionsHelper.checkNullList(otherLevelNodeList)) {
            for (ServiceEntityNode serviceEntityNode : otherLevelNodeList) {
                serviceEntityNode.setRootNodeUUID(newRootNodeUUID);
            }
        }
        /*
         * [Step3] Execute to update parent Node UUID
         */
        if (!ServiceCollectionsHelper.checkNullList(secondeLevelNodeList)) {
            Map<String, List<ServiceEntityNode>> seNodeMapParentNode = new HashMap<>();
            for (ServiceEntityNode serviceEntityNode : secondeLevelNodeList) {
                List<ServiceEntityNode> subNodeList = seNodeList.stream().filter(seNode -> {
                    return serviceEntityNode.getUuid().equals(seNode.getParentNodeUUID()) && !serviceEntityNode.getUuid().equals(seNode.getUuid());
                }).collect(Collectors.toList());
                updateParentNodeUUIDUnion(serviceEntityNode, subNodeList, seNodeList, resultList);
            }
        }
    }

    public static class UUIDUnion{

        private String oldUUID;

        private String newUUID;

        public UUIDUnion() {
        }

        public UUIDUnion(String oldUUID, String newUUID) {
            this.oldUUID = oldUUID;
            this.newUUID = newUUID;
        }

        public String getOldUUID() {
            return oldUUID;
        }

        public void setOldUUID(String oldUUID) {
            this.oldUUID = oldUUID;
        }

        public String getNewUUID() {
            return newUUID;
        }

        public void setNewUUID(String newUUID) {
            this.newUUID = newUUID;
        }
    }

    private static void updateParentNodeUUIDUnion(ServiceEntityNode parentNode, List<ServiceEntityNode> subNodeList,
                                                  List<ServiceEntityNode> allSENodeList, List<UUIDUnion> uuidUnionList) {
        String newParentNodeUUID = ServiceEntityStringHelper.genUUID();
        uuidUnionList.add(new UUIDUnion(parentNode.getUuid(), newParentNodeUUID));
        parentNode.setUuid(newParentNodeUUID);
        if (!ServiceCollectionsHelper.checkNullList(subNodeList)) {
            for (ServiceEntityNode serviceEntityNode : subNodeList) {
                serviceEntityNode.setParentNodeUUID(newParentNodeUUID);
                List<ServiceEntityNode> subChildNodeList = allSENodeList.stream().filter(seNode -> {
                    return serviceEntityNode.getUuid().equals(seNode.getParentNodeUUID()) && !serviceEntityNode.getUuid().equals(seNode.getUuid());
                }).collect(Collectors.toList());
                if (!ServiceCollectionsHelper.checkNullList(subChildNodeList)) {
                    updateParentNodeUUIDUnion(serviceEntityNode, subChildNodeList, allSENodeList, uuidUnionList);
                }
            }
        }
    }

}
