package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.dto.ServiceExtendUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceModelExtensionHelper.ExtensionUnionResponse;
import com.company.IntelligentPlatform.common.service.ServiceExtensionException;
import com.company.IntelligentPlatform.common.service.ServiceExtensionManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityLogModel;

@Service
public class ServiceUIModuleProxy {

    @Autowired
    protected ServiceModuleProxy serviceModuleProxy;

    @Autowired
    protected ServiceEntityLogModelManager serviceEntityLogModelManager;

    @Autowired
    protected ServiceExtensionManager serviceExtensionManager;

    @Autowired
    protected LogonInfoManager logonInfoManager;

    final static Logger logger = LoggerFactory.getLogger(ServiceUIModuleProxy.class);

    /**
     * Pair Union for both ServiceModule and ServiceUIModel
     */
    public static class ServiceUIModelUnion {

        private ServiceUIModule serviceUIModule;

        private ServiceModule serviceModule;

        public ServiceUIModelUnion() {
        }

        public ServiceUIModelUnion(ServiceUIModule serviceUIModule, ServiceModule serviceModule) {
            this.serviceUIModule = serviceUIModule;
            this.serviceModule = serviceModule;
        }

        public ServiceUIModule getServiceUIModule() {
            return serviceUIModule;
        }

        public void setServiceUIModule(ServiceUIModule serviceUIModule) {
            this.serviceUIModule = serviceUIModule;
        }

        public ServiceModule getServiceModule() {
            return serviceModule;
        }

        public void setServiceModule(ServiceModule serviceModule) {
            this.serviceModule = serviceModule;
        }
    }

    public SEUIComModel updateServiceEntityWrapper(
            SEUIComModel seUIComModel,
            Class<?> seNodeClass,
            ServiceEntityManager serviceEntityManager,
            Function<ServiceEntityNode, ServiceEntityNode> serviceNodeUpdatedCallback,
            Function<ServiceEntityNode, String[]> setLogIdNameCallBack,
            ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
            String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException, ServiceModuleProxyException {
        ServiceEntityNode serviceEntityNode = serviceEntityManager
                .getEntityNodeByKey(seUIComModel.getUuid(),
                        IServiceEntityNodeFieldConstant.UUID,
                        serviceUIModelExtensionUnion.getNodeName(),
                        seUIComModel.getClient(), null);
        ServiceEntityNode serviceEntityNodeBack = null;
        if (serviceEntityNode != null) {
            // In case update existed se node
            serviceEntityNodeBack = (ServiceEntityNode) serviceEntityNode
                    .clone();
        }
        List<ServiceEntityNode> rawSeNodeList = serviceEntityManager
                .genSeNodeListInExtensionUnion(serviceUIModelExtensionUnion,
                        seNodeClass, seUIComModel);
        serviceEntityManager.updateSeNodeListWrapper(rawSeNodeList,
                logonUserUUID, organizationUUID);
        /*
         * [Step3] Call serviceNodeUpdatedCallback after service node is
         * finished update
         */
        serviceEntityNode = serviceEntityManager
                .getEntityNodeByKey(seUIComModel.getUuid(),
                        IServiceEntityNodeFieldConstant.UUID,
                        serviceUIModelExtensionUnion.getNodeName(),
                        seUIComModel.getClient(), null);
        if (serviceNodeUpdatedCallback != null) {
            serviceNodeUpdatedCallback.apply(serviceEntityNode);
        }
        /*
         * [Step4] LogAction for update/create
         */
        serviceEntityLogModelManager.logServiceEntityNodeWrap(
                serviceEntityNode, serviceEntityNodeBack,
                ServiceEntityLogModel.MESSAGE_TYPE_INFO, setLogIdNameCallBack,
                logonUserUUID, organizationUUID);
        return serviceEntityManager.genUIModelFromUIModelExtension(
                seUIComModel.getClass(), serviceUIModelExtensionUnion,
                serviceEntityNode, null, null);
    }

    /**
     * Main Entrance to update service module as well as log actions for
     * update/create
     *
     * @param serviceModuleType
     * @param serviceEntityManager
     * @param serviceUIModule
     * @param serviceUIModelExtension
     * @param serviceUIModelExtensionUnion
     * @param serviceModuleUpdatedCallback : Call back after service module is already updated.
     * @param deleteSubListItemFlag
     * @return
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     * @throws ServiceUIModuleProxyException
     */
    public ServiceUIModule updateServiceModuleWrapper(
            Class<?> serviceModuleType,
            ServiceEntityManager serviceEntityManager,
            ServiceUIModule serviceUIModule,
            ServiceUIModelExtension serviceUIModelExtension,
            ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
            Function<ServiceModule, ServiceModule> serviceModuleUpdatedCallback,
            Function<ServiceEntityNode, String[]> setLogIdNameCallBack,
            LogonInfo logonInfo, boolean deleteSubListItemFlag)
            throws ServiceModuleProxyException,
            ServiceEntityConfigureException, ServiceUIModuleProxyException {
        SEUIComModel seUIComModel;
        try {
            seUIComModel = getCoreUIModel(serviceUIModule,
                    serviceUIModelExtensionUnion);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return null;
        }
        if (seUIComModel == null) {
            return null;
        }
        /*
         * [Step1] Backup the service module from DB, update from UI
         */
        ServiceEntityNode serviceEntityNode = serviceEntityManager
                .getEntityNodeByKey(seUIComModel.getUuid(),
                        IServiceEntityNodeFieldConstant.UUID,
                        serviceUIModelExtensionUnion.getNodeName(),
                        seUIComModel.getClient(), null);
        ServiceModule serviceModule = null;
        ServiceModule serviceModuleBack = null;
        if (serviceEntityNode != null) {
            // In case of update existed service module.
            serviceModule = serviceModuleProxy.loadServiceModule(
                    serviceModuleType, serviceEntityManager, serviceEntityNode);
            serviceModuleBack = ServiceModuleCloneService.deepCloneServiceModule(
                    serviceModule, false);
            serviceModule = genServiceModuleFromServiceUIModel(
                    serviceModuleType, serviceUIModule.getClass(),
                    serviceUIModule, serviceEntityManager,
                    serviceUIModelExtension);
        } else {
            // In case of create and insert new service module
            serviceModule = genServiceModuleFromServiceUIModel(
                    serviceModuleType, serviceUIModule.getClass(),
                    serviceUIModule, serviceEntityManager,
                    serviceUIModelExtension);
        }
        /*
         * [Step3] Update service module to back-end
         */
        updateServiceModuleWithLog(serviceModuleType, serviceEntityManager,
                serviceModule, serviceModuleBack, serviceUIModelExtension,
                serviceUIModelExtensionUnion, serviceModuleUpdatedCallback,
                setLogIdNameCallBack, logonInfo.getRefUserUUID(),
                logonInfo.getResOrgUUID(), deleteSubListItemFlag);
        /*
         * [Step4] Refresh UI model again from updated back-end.
         */
        serviceUIModule = genServiceUIModuleFromServiceModelCore(
                serviceUIModule.getClass(), serviceModule.getClass(),
                serviceUIModelExtension, serviceUIModelExtensionUnion,
                serviceEntityManager, serviceModule, logonInfo,
                null);
        return serviceUIModule;
    }

    public void updateServiceModuleWithLog(
            Class<?> serviceModuleType,
            ServiceEntityManager serviceEntityManager,
            ServiceModule serviceModule,
            ServiceModule serviceModuleBack,
            ServiceUIModelExtension serviceUIModelExtension,
            ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
            Function<ServiceModule, ServiceModule> serviceModuleUpdatedCallback,
            Function<ServiceEntityNode, String[]> setLogIdNameCallBack,
            String logonUserUUID, String organizationUUID,
            boolean deleteSubListItemFlag) throws ServiceModuleProxyException,
            ServiceEntityConfigureException {
        /*
         * [Step1] Update service module to back-end
         */
        serviceModuleProxy.updateServiceModule(serviceModuleType,
                serviceEntityManager, serviceModule, logonUserUUID,
                organizationUUID, deleteSubListItemFlag);

        /*
         * [Step2] Service module call back if this module is already updated
         * from UI.
         */
        if (serviceModuleUpdatedCallback != null) {
            serviceModuleUpdatedCallback.apply(serviceModule);
        }
        /*
         * [Step3] Log Action for update/create
         */
        serviceEntityLogModelManager.logActionForServiceModuleWrapper(
                serviceModule, serviceModuleBack,
                ServiceEntityLogModel.MESSAGE_TYPE_INFO,
                serviceUIModelExtension, setLogIdNameCallBack, logonUserUUID,
                organizationUUID, serviceUIModelExtensionUnion);
    }


    public ServiceModule genServiceModuleFromServiceUIModel(
            Class<?> serviceModuleType, Class<?> serviceUIModuleType,
            ServiceUIModule serviceUIModule,
            ServiceEntityManager serviceEntityManager,
            ServiceUIModelExtension serviceUIModelExtension)
            throws ServiceModuleProxyException, ServiceUIModuleProxyException,
            ServiceEntityConfigureException {
        if (serviceUIModelExtension == null) {
            return genServiceModuleFromServiceUIModule(serviceModuleType,
                    serviceUIModuleType, serviceEntityManager, serviceUIModule,
                    null, null);
        }
        // Check Extension union
        if (ServiceCollectionsHelper.checkNullList(serviceUIModelExtension
                .genUIModelExtensionUnion())) {
            throw new ServiceUIModuleProxyException(
                    ServiceUIModuleProxyException.TYPE_SYSTEM_WRONG);
        }
        // Get the default first extension union.
        ServiceUIModelExtensionUnion serviceUIModelExtensionUnion = serviceUIModelExtension
                .genUIModelExtensionUnion().get(0);
        return genServiceModuleFromServiceUIModule(serviceModuleType,
                serviceUIModuleType, serviceEntityManager, serviceUIModule,
                serviceUIModelExtension, serviceUIModelExtensionUnion);
    }

    /**
     * Core Logic to get Core UIModel from ServiceUIModel
     *
     * @param serviceUIModule
     * @param serviceUIModelExtensionUnion
     * @return
     * @throws ServiceUIModuleProxyException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static SEUIComModel getCoreUIModel(ServiceUIModule serviceUIModule,
                                              ServiceUIModelExtensionUnion serviceUIModelExtensionUnion)
            throws ServiceUIModuleProxyException, IllegalArgumentException,
            IllegalAccessException {
        if (serviceUIModule == null) {
            return null;
        }
        Field coreUIModuleField = ServiceModuleProxy
                .getUIModuleFieldByNodeInstId(serviceUIModule.getClass(),
                        serviceUIModelExtensionUnion.getNodeInstId());
        if (coreUIModuleField == null) {
            // raise exception when no core module field found
            throw new ServiceUIModuleProxyException(
                    ServiceUIModuleProxyException.PARA_NOCOREMODULE,
                    serviceUIModule.getClass().getSimpleName());
        }
        coreUIModuleField.setAccessible(true);
        // Get core UI model value
        return (SEUIComModel) coreUIModuleField
                .get(serviceUIModule);
    }

    public static ServiceModule quickCreateServiceModule(Class<?> serviceModuleType, ServiceEntityNode seNodeValue,
                                                  String nodeInstId,
                                                  List<ServiceEntityNode> docMatItemList,
                                                  String itemNodeInstId) throws ServiceModuleProxyException,
            IllegalAccessException, InstantiationException {
        /*
         *
         */
        Field coreModuleField = ServiceModuleProxy
                .getModuleFieldByNodeInstId(serviceModuleType, nodeInstId);
        if (coreModuleField == null) {
            // raise exception when no core module field found
            throw new ServiceModuleProxyException(
                    ServiceModuleProxyException.PARA_NOCOREMODULE,
                    serviceModuleType.getSimpleName());
        }
        coreModuleField.setAccessible(true);
        IServiceModuleFieldConfig serviceModuleFieldConfig = coreModuleField
                .getAnnotation(IServiceModuleFieldConfig.class);
        if (serviceModuleFieldConfig == null) {
            // raise exception when no core module field found
            throw new ServiceModuleProxyException(
                    ServiceModuleProxyException.PARA_NOANNOTATION,
                    coreModuleField.getName());
        }
        // New ServiceModule instance in reflective way.
        ServiceModule serviceModule = (ServiceModule) serviceModuleType
                .newInstance();
        coreModuleField.setAccessible(true);
        coreModuleField.set(serviceModule, seNodeValue);
        if(ServiceEntityStringHelper.checkNullString(itemNodeInstId) || ServiceCollectionsHelper.checkNullList(docMatItemList)){
            // return directly
            return serviceModule;
        }
        Field listTypeField = ServiceModuleProxy.getModuleFieldByNodeInstIdRecursive(itemNodeInstId, serviceModuleType);
        if(listTypeField == null) {
            return serviceModule;
        }
        if (ServiceEntityFieldsHelper
                .checkSuperClassExtends(ServiceEntityFieldsHelper
                                .getListSubType(listTypeField),
                        ServiceEntityNode.class.getSimpleName())) {
            try {
                listTypeField.setAccessible(true);
                listTypeField.set(serviceModule,
                        docMatItemList);
            } catch (IllegalArgumentException
                    | IllegalAccessException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            }
        }
        if (ServiceEntityFieldsHelper
                .checkSuperClassExtends(ServiceEntityFieldsHelper
                                .getListSubType(listTypeField),
                        ServiceModule.class.getSimpleName())) {
            Class<?> subServiceModelType = ServiceEntityFieldsHelper
                    .getListSubType(listTypeField);
            /*
             * [Step 2.5] In case sub list is for service module
             * node list
             */
            IServiceModuleFieldConfig subListFieldConfig = listTypeField
                    .getAnnotation(IServiceModuleFieldConfig.class);
            if (subListFieldConfig == null) {
                throw new ServiceModuleProxyException(
                        ServiceUIModuleProxyException.PARA_NOANNOTATION,
                        listTypeField.getName());
            }
            List<ServiceModule> subServiceModuleList = new ArrayList<>();
            for(ServiceEntityNode tempSENode:docMatItemList){
                ServiceModule subServiceMode = quickCreateServiceModule(subServiceModelType, tempSENode, subListFieldConfig.nodeInstId(), null, null);
                subServiceModuleList.add(subServiceMode);
            }
            try {
                listTypeField.setAccessible(true);
                listTypeField.set(serviceModule,
                        subServiceModuleList);
            } catch (IllegalArgumentException
                    | IllegalAccessException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            }
        }
        return serviceModule;
    }

    public static ServiceJSONRequest convUIToServiceJSONRequest(ServiceJSONRequest serviceJSONRequest){
        if(!ServiceEntityStringHelper.checkNullString(serviceJSONRequest.getDateMinString())){
            try {
                serviceJSONRequest
                        .setExecuteDate(DefaultDateFormatConstant.DATE_MIN_FORMAT
                                .parse(serviceJSONRequest.getDateMinString()));
            } catch (ParseException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            }
        }
        return serviceJSONRequest;
    }

    public static ServiceJSONRequest convServiceJSONRequestToUI(ServiceJSONRequest serviceJSONRequest){
        serviceJSONRequest
                .setDateMinString(DefaultDateFormatConstant.DATE_MIN_FORMAT
                        .format(serviceJSONRequest.getExecuteDate()));
        return serviceJSONRequest;
    }

    /**
     * Main entrance to generate service module instance from Service UI Model
     *
     * @param serviceModuleType
     * @param serviceUIModuleType
     * @param serviceEntityManager
     * @param serviceUIModule
     * @return
     * @throws ServiceUIModuleProxyException
     * @throws ServiceModuleProxyException
     */
    public ServiceModule genServiceModuleFromServiceUIModule(
            Class<?> serviceModuleType, Class<?> serviceUIModuleType,
            ServiceEntityManager serviceEntityManager,
            ServiceUIModule serviceUIModule,
            ServiceUIModelExtension serviceUIModelExtension,
            ServiceUIModelExtensionUnion serviceUIModelExtensionUnion)
            throws ServiceUIModuleProxyException, ServiceModuleProxyException {
        try {
            /*
             * [Step1] Retrieve core UI model field
             */
            String nodeInstId = serviceUIModelExtensionUnion.getNodeInstId();
            Field coreUIModuleField = ServiceModuleProxy
                    .getUIModuleFieldByNodeInstId(serviceUIModuleType,
                            nodeInstId);
            if (coreUIModuleField == null) {
                // raise exception when no core module field found
                throw new ServiceUIModuleProxyException(
                        ServiceUIModuleProxyException.PARA_NOCOREMODULE,
                        serviceUIModuleType.getSimpleName());
            }
            coreUIModuleField.setAccessible(true);
            // Get core UI model value
            SEUIComModel uiModelValue = (SEUIComModel) coreUIModuleField
                    .get(serviceUIModule);
            IServiceUIModuleFieldConfig serviceUIModuleFieldConfig = coreUIModuleField
                    .getAnnotation(IServiceUIModuleFieldConfig.class);
            if (serviceUIModuleFieldConfig == null) {
                // raise exception when no core module field found
                throw new ServiceUIModuleProxyException(
                        ServiceUIModuleProxyException.PARA_NOANNOTATION,
                        serviceUIModuleType.getSimpleName());
            }
            /*
             * [Step2] Retrieve the core module field
             */
            Field coreModuleField = ServiceModuleProxy
                    .getModuleFieldByNodeInstId(serviceModuleType, nodeInstId);
            if (coreModuleField == null) {
                // raise exception when no core module field found
                throw new ServiceModuleProxyException(
                        ServiceModuleProxyException.PARA_NOCOREMODULE,
                        serviceModuleType.getSimpleName());
            }
            coreModuleField.setAccessible(true);
            IServiceModuleFieldConfig serviceModuleFieldConfig = coreModuleField
                    .getAnnotation(IServiceModuleFieldConfig.class);
            if (serviceModuleFieldConfig == null) {
                // raise exception when no core module field found
                throw new ServiceModuleProxyException(
                        ServiceModuleProxyException.PARA_NOANNOTATION,
                        coreModuleField.getName());
            }
            // New ServiceModule instance in reflective way.
            ServiceModule serviceModule = (ServiceModule) serviceModuleType
                    .newInstance();
            // Decide the executeManager
            UIModelNodeMapConfigure uiModelNodeMapConfigure = ServiceModelExtensionHelper
                    .filterNodeMapConfigureByNodeInstId(
                            serviceUIModelExtensionUnion
                                    .getUiModelNodeMapList(),
                            serviceUIModelExtensionUnion.getNodeInstId());
            ServiceEntityManager executeManager = serviceEntityManager;
            if (uiModelNodeMapConfigure.getServiceEntityManager() != null) {
                executeManager = uiModelNodeMapConfigure
                        .getServiceEntityManager();
            }
            ServiceEntityNode seNodeValue = executeManager.getEntityNodeByKey(
                    uiModelValue.getUuid(),
                    IServiceEntityNodeFieldConstant.UUID,
                    serviceUIModuleFieldConfig.nodeName(),
                    uiModelValue.getClient(), null);
            if (seNodeValue == null) {
                // New Core SE node instance
                seNodeValue = (ServiceEntityNode) coreModuleField.getType()
                        .newInstance();
            }
            /*
             * [Step3] Reflective call convUITo method, and set the value to
             * seNodeValue.
             */
            reflectConvertUIToUnion(serviceUIModuleFieldConfig, serviceModule,
                    seNodeValue, uiModelValue, serviceEntityManager,
                    serviceUIModelExtensionUnion);
            // Set the UI model back to service UI model
            coreModuleField.setAccessible(true);
            coreModuleField.set(serviceModule, seNodeValue);
            /*
             * [Step4] find the sub flat attributes
             */
            List<Field> subUIModelTypeFields = getNonListSubFields(
                    serviceUIModuleType, nodeInstId);
            List<Field> subTypeFields = ServiceModuleProxy.getNonListSubFields(
                    serviceModuleType, nodeInstId);
            if (!ServiceCollectionsHelper.checkNullList(subUIModelTypeFields)) {
                for (Field subUIField : subUIModelTypeFields) {
                    IServiceUIModuleFieldConfig subFieldConfig = subUIField
                            .getAnnotation(IServiceUIModuleFieldConfig.class);
                    if (subFieldConfig == null) {
                        // Just continue
                        continue;
                    }
                    ExtensionUnionResponse subExtensionUnionResponse = ServiceModelExtensionHelper
                            .getUIModelExtensionByNodeInstId(
                                    subFieldConfig.nodeInstId(),
                                    serviceUIModelExtension);
                    if (subExtensionUnionResponse == null) {
                        // Just continue
                        logger.error("no sub extension by:" + subFieldConfig.nodeInstId());
                        continue;
                    }
                    ServiceUIModelExtensionUnion subExtensionUnion =
                            subExtensionUnionResponse.getServiceUIModelExtensionUnion();
                    ServiceUIModelExtension subExtension = subExtensionUnionResponse.getServiceUIModelExtension();
                    if (ServiceEntityFieldsHelper.checkSuperClassExtends(
                            subUIField.getType(),
                            SEUIComModel.class.getSimpleName())) {
                        setSubUIModelFlatValueWrapper(subUIField,
                                subTypeFields, serviceModule, serviceUIModule,
                                serviceEntityManager, uiModelValue.getClient(),
                                subExtension, subExtensionUnion);
                    }
                    if (ServiceEntityFieldsHelper.checkSuperClassExtends(
                            subUIField.getType(),
                            ServiceUIModule.class.getSimpleName())) {
                        setSubServiceUIModelFlatValueWrapper(subUIField,
                                subTypeFields, serviceModule,
                                serviceUIModule, serviceUIModuleType,
                                serviceModuleType, serviceEntityManager,
                                subExtension,
                                subExtensionUnion);
                    }
                }
            }
            /*
             * [Step5] find the sub list attributes
             */
            List<Field> listUIModelTypeFields = getListTypeFields(serviceUIModuleType);
            List<Field> listTypeFields = getListTypeFields(serviceModuleType);
            if (!ServiceCollectionsHelper.checkNullList(listUIModelTypeFields)) {
                for (Field listUIField : listUIModelTypeFields) {
                    IServiceUIModuleFieldConfig subListFieldConfig = listUIField
                            .getAnnotation(IServiceUIModuleFieldConfig.class);
                    if (subListFieldConfig == null) {
                        // Just continue
                        continue;
                    }
                    /*
                     * [Step5.2] Get the relative sub Extension Union to this
                     * sub field
                     */
                    ExtensionUnionResponse subExtensionUnionResponse = ServiceModelExtensionHelper
                            .getUIModelExtensionByNodeInstId(
                                    subListFieldConfig.nodeInstId(),
                                    serviceUIModelExtension);
                    if (subExtensionUnionResponse == null) {
                        // Just continue
                        logger.error("no sub extension by:" + subListFieldConfig.nodeInstId());
                        continue;
                    }
                    ServiceUIModelExtensionUnion subExtensionUnion =
                            subExtensionUnionResponse.getServiceUIModelExtensionUnion();
                    ServiceUIModelExtension subExtension = subExtensionUnionResponse.getServiceUIModelExtension();
                    if (ServiceEntityFieldsHelper.checkSuperClassExtends(
                            ServiceEntityFieldsHelper
                                    .getListSubType(listUIField),
                            SEUIComModel.class.getSimpleName())) {
                        // In case the list sub type is service entity node
                        setSubUIModelListValueWrapper(listUIField,
                                listTypeFields, serviceModule, serviceUIModule,
                                serviceEntityManager, uiModelValue.getClient(),
                                subExtension, subExtensionUnion);
                    }
                    if (ServiceEntityFieldsHelper.checkSuperClassExtends(
                            ServiceEntityFieldsHelper
                                    .getListSubType(listUIField),
                            ServiceUIModule.class.getSimpleName())) {
                        // In case the list sub type is service module
                        setSubServiceUIModelListValueWrapper(listUIField,
                                listTypeFields, serviceModule, serviceUIModule,
                                serviceUIModuleType, serviceModuleType,
                                serviceEntityManager, subExtension,
                                subExtensionUnion);
                    }
                }
            }
            /*
             * [Step6]  copy other meta
             */
            if(serviceUIModule.getServiceJSONRequest() != null){
                serviceModule.setServiceJSONRequest(convUIToServiceJSONRequest(serviceUIModule.getServiceJSONRequest()));
            }
            /*
             * [Step7] For Non-Embedded Sub node, get their service entity node
             * instance and value from UIModel
             */
            if (serviceUIModelExtensionUnion != null) {
                /*
                 * [Important Logic]:In case para:
                 * <code>serviceUIModelExtensionUnion</code> is not null, means
                 * some sub entity model's information should also be generated
                 * OR converted from this UI Model class instance.
                 */
                List<ServiceEntityNode> subEntityNodeList = genSeNodeListInExtensionUnion(
                        serviceUIModelExtensionUnion, serviceEntityManager,
                        seNodeValue, serviceModuleType, uiModelValue);
                // Traverse each service entity node instance.
                if (!ServiceCollectionsHelper.checkNullList(subEntityNodeList)) {
                    for (ServiceEntityNode serviceEntityNode : subEntityNodeList) {

                        nodeInstId = serviceEntityNode.getNodeName();
                        if (ServiceEntityNode.NODENAME_ROOT
                                .equals(serviceEntityNode.getNodeName())) {
                            nodeInstId = serviceEntityNode
                                    .getServiceEntityName();
                        }
                        // Try to find the field from serviceModule as list type
                        // field.
                        Field listField = ServiceModuleProxy
                                .getModuleFieldByNodeInstIdRecursive(
                                        nodeInstId, serviceModuleType);
                        if (listField == null) {
                            continue;
                        }
                        listField.setAccessible(true);
                        /*
                         * [Step 6.2] In case current list type field is for
                         * List<ServiceEntityNode>
                         */
                        if (ServiceEntityFieldsHelper.checkSuperClassExtends(
                                ServiceEntityFieldsHelper
                                        .getListSubType(listField),
                                ServiceEntityNode.class.getSimpleName())) {
                            @SuppressWarnings("unchecked")
                            List<ServiceEntityNode> rawSENodeList = (List<ServiceEntityNode>) listField
                                    .get(serviceModule);
                            if (rawSENodeList == null) {
                                rawSENodeList = new ArrayList<>();
                            }
                            ServiceCollectionsHelper.mergeToList(rawSENodeList,
                                    serviceEntityNode);
                        }
                        /*
                         * [Step 6.3] In case current list type field is for
                         * List<ServiceModule>
                         */
                        if (ServiceEntityFieldsHelper.checkSuperClassExtends(
                                ServiceEntityFieldsHelper
                                        .getListSubType(listField),
                                ServiceModule.class.getSimpleName())) {
                            setSENodeValueToListServiceModule(listField,
                                    serviceModule, serviceEntityNode);
                        }

                    }
                }
            }
            return serviceModule;
        } catch (IllegalAccessException | InstantiationException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ServiceEntityConfigureException | NoSuchFieldException e) {
            throw new ServiceModuleProxyException(
                    ServiceModuleProxyException.PARA_SYSTEM_WRONG,
                    e.getMessage());
        }
    }

    private void setSENodeValueToListServiceModule(Field listField,
                                                   ServiceModule serviceModule, ServiceEntityNode serviceEntityNode)
            throws IllegalArgumentException, IllegalAccessException,
            InstantiationException {
        List<?> rawSubServiceModuleList = (List<?>) listField
                .get(serviceModule);
        if (rawSubServiceModuleList == null) {
            rawSubServiceModuleList = (List<?>) listField.getType()
                    .newInstance();
        }
        IServiceModuleFieldConfig subListFieldConfig = listField
                .getAnnotation(IServiceModuleFieldConfig.class);
        if (subListFieldConfig == null) {
            return;
        }
        /*
         * Traverse current existed service module list attributes, try to
         * update to the member with identical service Node object (with same
         * UUID).
         */
        if (!ServiceCollectionsHelper.checkNullList(rawSubServiceModuleList)) {
            for (Object rawObject : rawSubServiceModuleList) {
                ServiceModule subServiceModule = (ServiceModule) rawObject;
                Field coreField = ServiceModuleProxy
                        .getModuleFieldByNodeInstId(
                                subServiceModule.getClass(),
                                subListFieldConfig.nodeInstId());
                coreField.setAccessible(true);
                ServiceEntityNode rawSubServiceEntityNode = (ServiceEntityNode) coreField
                        .get(subServiceModule);
                if (rawSubServiceEntityNode != null) {
                    if (rawSubServiceEntityNode.getUuid().equals(
                            serviceEntityNode.getUuid())) {
                        coreField.set(subServiceModule, serviceEntityNode);
                        return;
                    }
                }
            }
        }
        /*
         * In case not hit existed service module instance in service module
         * list, then create new Service module instance, set SE node value.
         */
        Class<?> subServiceModuleClass = ServiceEntityFieldsHelper
                .getListSubType(listField);
        ServiceModule subServiceModule = (ServiceModule) subServiceModuleClass
                .newInstance();
        Field coreField = ServiceModuleProxy.getModuleFieldByNodeInstId(
                subServiceModule.getClass(), subListFieldConfig.nodeInstId());
        coreField.setAccessible(true);
        coreField.set(subServiceModule, serviceEntityNode);
        // rawSubServiceModuleList.add(0, subServiceModule);

    }

    /**
     * Logic to generate default node inst id from service entity node instance.
     *
     * @param seNodeInstance
     * @return
     */
    public String getDefaultNodeInstId(ServiceEntityNode seNodeInstance) {
        String nodeInstId = ServiceEntityStringHelper.EMPTYSTRING;
        if (seNodeInstance.getNodeName()
                .equals(ServiceEntityNode.NODENAME_ROOT)) {
            nodeInstId = seNodeInstance.getServiceEntityName();
        } else {
            nodeInstId = seNodeInstance.getNodeName();
        }
        return nodeInstId;
    }

    /**
     * Core Logic to get Service UI Model from Service Model With Login Info
     *
     * @param serviceUIModuleType
     * @param serviceModuleType
     * @param serviceUIModelExtensionUnion
     * @param serviceEntityManager
     * @param serviceModule
     * @return
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     * @throws ServiceUIModuleProxyException
     */
    public ServiceUIModule genServiceUIModuleWithFlatNode(
            Class<?> serviceUIModuleType, Class<?> serviceModuleType,
            ServiceUIModelExtension serviceUIModelExtension,
            ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
            ServiceEntityManager serviceEntityManager,
            ServiceModule serviceModule, LogonInfo logonInfo,
            List<ServiceModuleConvertPara> additionalConvertParaList)
            throws ServiceModuleProxyException,
            ServiceEntityConfigureException, ServiceUIModuleProxyException {
        try {
            /*
             * [Step1] Retrieve the core module field
             */
            Field coreModuleField = ServiceModuleProxy
                    .getModuleFieldByNodeInstId(serviceModuleType,
                            serviceUIModelExtensionUnion.getNodeInstId());
            if (coreModuleField == null) {
                // raise exception when no core module field found
                throw new ServiceModuleProxyException(
                        ServiceModuleProxyException.PARA_NOCOREMODULE,
                        serviceModuleType.getSimpleName());
            }
            coreModuleField.setAccessible(true);
            ServiceEntityNode seNodeValue = (ServiceEntityNode) coreModuleField
                    .get(serviceModule);
            // Add to additional parameter for later usage.
            mergeToConvertParaList(additionalConvertParaList, seNodeValue);
            IServiceModuleFieldConfig serviceModuleFieldConfig = coreModuleField
                    .getAnnotation(IServiceModuleFieldConfig.class);
            if (serviceModuleFieldConfig == null) {
                // raise exception when no core module field found
                throw new ServiceModuleProxyException(
                        ServiceModuleProxyException.PARA_NOANNOTATION,
                        coreModuleField.getName());
            }
            /*
             * [Step2] Retrieve core UI model field
             */
            Field coreUIModuleField = getUIModuleFieldByNodeInstId(
                    serviceUIModuleType,
                    serviceUIModelExtensionUnion.getNodeInstId());
            if (coreUIModuleField == null) {
                // raise exception when no core module field found
                throw new ServiceUIModuleProxyException(
                        ServiceUIModuleProxyException.PARA_NOCOREMODULE,
                        serviceUIModuleType.getSimpleName());
            }
            coreUIModuleField.setAccessible(true);
            IServiceUIModuleFieldConfig serviceUIModuleFieldConfig = coreUIModuleField
                    .getAnnotation(IServiceUIModuleFieldConfig.class);
            if (serviceUIModuleFieldConfig == null) {
                // raise exception when no core module field found
                throw new ServiceUIModuleProxyException(
                        ServiceUIModuleProxyException.PARA_NOANNOTATION,
                        serviceUIModuleType.getSimpleName());
            }
            // New Service UI model instance
            ServiceUIModule serviceUIModule = (ServiceUIModule) serviceUIModuleType
                    .newInstance();
            // New UI model instance
            SEUIComModel uiModelInstance = null;
            if (ServiceCollectionsHelper
                    .checkNullList(serviceUIModelExtensionUnion
                            .getUiModelNodeMapList())) {
                // In case the UIModelNodeMaplist is null, then using the
                // tradition way to generate UI Model instance
                // New UI model instance
                uiModelInstance = (SEUIComModel) coreUIModuleField.getType()
                        .newInstance();
                reflectConvertToUIUnion(serviceUIModuleFieldConfig,
                        coreModuleField, coreUIModuleField, seNodeValue,
                        uiModelInstance, serviceEntityManager, logonInfo);
            } else {
                uiModelInstance = genUIModuleInExtensionUnion(
                        coreUIModuleField.getType(),
                        serviceUIModelExtensionUnion, serviceEntityManager,
                        seNodeValue, logonInfo, additionalConvertParaList);
            }
            /*
             * [Step4] Set the UI model back to service UI model
             */
            coreUIModuleField.setAccessible(true);
            coreUIModuleField.set(serviceUIModule, uiModelInstance);

            /*
             * [Step5] find the sub flat attributes
             */
            String nodeInstId = serviceUIModelExtensionUnion.getNodeInstId();
            List<Field> subFlatFields = ServiceModuleProxy.getNonListSubFields(
                    serviceModuleType, nodeInstId);
            List<Field> subFlatUIFields = ServiceUIModuleProxy
                    .getNonListSubFields(serviceUIModuleType, nodeInstId);
            if (!ServiceCollectionsHelper.checkNullList(subFlatFields)) {
                for (Field subField : subFlatFields) {
                    fillSubUIFieldWrapper(subField, serviceUIModelExtension,
                            serviceUIModelExtensionUnion, subFlatUIFields,
                            serviceModule, serviceUIModule,
                            serviceEntityManager, logonInfo,
                            additionalConvertParaList, false);
                }
            }
            return serviceUIModule;
        } catch (ServiceEntityConfigureException | IllegalArgumentException | IllegalAccessException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            throw new ServiceModuleProxyException(
                    ServiceModuleProxyException.PARA_SYSTEM_WRONG,
                    e.getMessage());
        }
    }


    /**
     * Core Logic to get Service UI Model from Service Model With Login Info
     *
     * @param serviceUIModuleType
     * @param serviceModuleType
     * @param serviceUIModelExtensionUnion
     * @param serviceEntityManager
     * @param serviceModule
     * @return
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     * @throws ServiceUIModuleProxyException
     */
    public ServiceUIModule genServiceUIModuleFromServiceModelCore(
            Class<?> serviceUIModuleType, Class<?> serviceModuleType,
            ServiceUIModelExtension serviceUIModelExtension,
            ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
            ServiceEntityManager serviceEntityManager,
            ServiceModule serviceModule, LogonInfo logonInfo,
            List<ServiceModuleConvertPara> additionalConvertParaList)
            throws ServiceModuleProxyException,
            ServiceEntityConfigureException, ServiceUIModuleProxyException {
        try {
            /*
             * [Step1] Retrieve the core module field
             */
            Field coreModuleField = ServiceModuleProxy
                    .getModuleFieldByNodeInstId(serviceModuleType,
                            serviceUIModelExtensionUnion.getNodeInstId());
            if (coreModuleField == null) {
                // raise exception when no core module field found
                throw new ServiceModuleProxyException(
                        ServiceModuleProxyException.PARA_NOCOREMODULE,
                        serviceModuleType.getSimpleName());
            }
            coreModuleField.setAccessible(true);
            ServiceEntityNode seNodeValue = (ServiceEntityNode) coreModuleField
                    .get(serviceModule);
            // Add to additional parameter for later usage.
            mergeToConvertParaList(additionalConvertParaList, seNodeValue);
            IServiceModuleFieldConfig serviceModuleFieldConfig = coreModuleField
                    .getAnnotation(IServiceModuleFieldConfig.class);
            if (serviceModuleFieldConfig == null) {
                // raise exception when no core module field found
                throw new ServiceModuleProxyException(
                        ServiceModuleProxyException.PARA_NOANNOTATION,
                        coreModuleField.getName());
            }
            /*
             * [Step2] Retrieve core UI model field
             */
            Field coreUIModuleField = getUIModuleFieldByNodeInstId(
                    serviceUIModuleType,
                    serviceUIModelExtensionUnion.getNodeInstId());
            if (coreUIModuleField == null) {
                // raise exception when no core module field found
                throw new ServiceUIModuleProxyException(
                        ServiceUIModuleProxyException.PARA_NOCOREMODULE,
                        serviceUIModuleType.getSimpleName());
            }
            coreUIModuleField.setAccessible(true);
            IServiceUIModuleFieldConfig serviceUIModuleFieldConfig = coreUIModuleField
                    .getAnnotation(IServiceUIModuleFieldConfig.class);
            if (serviceUIModuleFieldConfig == null) {
                // raise exception when no core module field found
                throw new ServiceUIModuleProxyException(
                        ServiceUIModuleProxyException.PARA_NOANNOTATION,
                        serviceUIModuleType.getSimpleName());
            }
            // New Service UI model instance
            ServiceUIModule serviceUIModule = (ServiceUIModule) serviceUIModuleType
                    .newInstance();
            // New UI model instance
            SEUIComModel uiModelInstance = null;
            if (ServiceCollectionsHelper
                    .checkNullList(serviceUIModelExtensionUnion
                            .getUiModelNodeMapList())) {
                // In case the UIModelNodeMaplist is null, then using the
                // tradition way to generate UI Model instance
                // New UI model instance
                uiModelInstance = (SEUIComModel) coreUIModuleField.getType()
                        .newInstance();
                reflectConvertToUIUnion(serviceUIModuleFieldConfig,
                        coreModuleField, coreUIModuleField, seNodeValue,
                        uiModelInstance, serviceEntityManager, logonInfo);
            } else {
                uiModelInstance = genUIModuleInExtensionUnion(
                        coreUIModuleField.getType(),
                        serviceUIModelExtensionUnion, serviceEntityManager,
                        seNodeValue, logonInfo, additionalConvertParaList);
            }
            /*
             * [Step4] Set the UI model back to service UI model
             */
            coreUIModuleField.setAccessible(true);
            coreUIModuleField.set(serviceUIModule, uiModelInstance);

            /*
             * [Step5] find the sub flat attributes
             */
            String nodeInstId = serviceUIModelExtensionUnion.getNodeInstId();
            List<Field> subFlatFields = ServiceModuleProxy.getNonListSubFields(
                    serviceModuleType, nodeInstId);
            List<Field> subFlatUIFields = ServiceUIModuleProxy
                    .getNonListSubFields(serviceUIModuleType, nodeInstId);
            if (!ServiceCollectionsHelper.checkNullList(subFlatFields)) {
                for (Field subField : subFlatFields) {
                    fillSubUIFieldWrapper(subField, serviceUIModelExtension,
                            serviceUIModelExtensionUnion, subFlatUIFields,
                            serviceModule, serviceUIModule,
                            serviceEntityManager, logonInfo,
                            additionalConvertParaList, false);
                }
            }
            /*
             * [Step5] find the sub list attributes
             */
            List<Field> listTypeFields = getListTypeFields(serviceModuleType);
            List<Field> listUIModelTypeFields = getListTypeFields(serviceUIModuleType);
            if (!ServiceCollectionsHelper.checkNullList(listTypeFields)) {
                for (Field listField : listTypeFields) {
                    fillSubUIFieldWrapper(listField, serviceUIModelExtension,
                            serviceUIModelExtensionUnion,
                            listUIModelTypeFields, serviceModule,
                            serviceUIModule, serviceEntityManager, logonInfo,
                            additionalConvertParaList, true);
                }
            }
            /*
             * [Step6] Load the extension module if possible
             */
            try {
                serviceExtensionManager.getExtendUIModelWrapper(
                        serviceUIModule, seNodeValue.getClient());
            } catch (ServiceExtensionException e) {
                // currently record exception
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, "genServiceUIModuleFromServiceModelCore"));
            }
            return serviceUIModule;
        } catch (ServiceEntityConfigureException | IllegalArgumentException | IllegalAccessException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            throw new ServiceModuleProxyException(
                    ServiceModuleProxyException.PARA_SYSTEM_WRONG,
                    e.getMessage());
        }
    }

    /**
     * Utility method to generate dummy empty instance for "flat node" service UI model, to avoid errors in list view
     * @param serviceUIModule
     * @param serviceUIModelExtensionUnion
     */
    public static void fillInDummyValueForFlatNode(ServiceUIModule serviceUIModule, List<String> fieldInstIdList,
                                                   ServiceUIModelExtensionUnion serviceUIModelExtensionUnion){
        Class<?> serviceUIModuleType = serviceUIModule.getClass();
        List<Field> subFlatUIFields = ServiceUIModuleProxy
                .getNonListSubFields(serviceUIModuleType, serviceUIModelExtensionUnion.getNodeInstId());
        if (!ServiceCollectionsHelper.checkNullList(subFlatUIFields)) {
            for (Field subUIField : subFlatUIFields) {
                subUIField.setAccessible(true);
                try {
                    Object value = subUIField.get(serviceUIModule);
                    if(value != null){
                        continue;
                    }
                } catch (IllegalAccessException e) {
                    continue;
                }
                IServiceUIModuleFieldConfig subUIFieldConfigure = subUIField
                        .getAnnotation(IServiceUIModuleFieldConfig.class);
                if (subUIFieldConfigure == null) {
                    continue;
                }
                if(!ServiceCollectionsHelper.checkNullList(fieldInstIdList)){
                    // in case need to specify by field inst id
                    List<String> filteredResult = fieldInstIdList.stream().filter(fieldInstId->{
                        return subUIFieldConfigure.nodeInstId().equals(fieldInstId);
                    }).collect(Collectors.toList());
                    if(ServiceCollectionsHelper.checkNullList(filteredResult)){
                        continue;
                    }
                }
                Class<?> subUIModelClass = subUIField.getType();
                try {
                    SEUIComModel seUIModelInstance = (SEUIComModel)subUIModelClass
                            .newInstance();
                    subUIField.setAccessible(true);
                    subUIField.set(serviceUIModule, seUIModelInstance);
                } catch (InstantiationException | IllegalAccessException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
        }
    }

    /**
     * [Internal method] Using reflect to call convert to UI method union
     *
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private void reflectConvertToUIUnion(
            IServiceUIModuleFieldConfig serviceUIModuleFieldConfig,
            Field coreModuleField, Field coreUIModuleField,
            ServiceEntityNode seNodeValue, SEUIComModel uiModelInstance,
            ServiceEntityManager serviceEntityManager, LogonInfo logonInfo)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        if (ServiceEntityStringHelper
                .checkNullString(serviceUIModuleFieldConfig.convToUIMethod())) {
            // In case no convToUIMethod in config, then just return.
            return;
        }
        // Hard code the default convTOUIMethod paras
        Class<?>[] parameterTypes = {coreModuleField.getType(),
                coreUIModuleField.getType()};
        Method convToUIMethod = getConvToMethodWrapper(
                serviceEntityManager.getClass(),
                serviceUIModuleFieldConfig.convToUIMethod(), parameterTypes,
                logonInfo);
        invokeConvToMethodWrapper(serviceEntityManager, convToUIMethod,
                seNodeValue, uiModelInstance, logonInfo);
    }

    /**
     * Utility method to find Conversion method by attempt to get by logonInfo
     * paras
     *
     * @param hostClass
     * @param methodName
     * @param parameterTypes
     * @param logonInfo
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public Method getConvToMethodWrapper(Class<?> hostClass, String methodName,
                                         Class<?>[] parameterTypes, LogonInfo logonInfo)
            throws NoSuchMethodException, SecurityException {
        if (logonInfo == null) {
            return hostClass.getMethod(methodName, parameterTypes);
        }
        Class<?>[] refinedParameterTypes = new Class<?>[parameterTypes.length + 1];
        System.arraycopy(parameterTypes, 0, refinedParameterTypes, 0, parameterTypes.length);
        refinedParameterTypes[parameterTypes.length] = LogonInfo.class;
        try {
            Method targetMethod = hostClass.getMethod(methodName,
                    refinedParameterTypes);
            return targetMethod;
        } catch (NoSuchMethodException | SecurityException ex) {
            return hostClass.getMethod(methodName, parameterTypes);
        }
    }

    public void invokeConvToMethodWrapper(Object logicManager,
                                          Method convToMethod, ServiceEntityNode seNodeValue,
                                          SEUIComModel uiModelInstance, LogonInfo logonInfo)
            throws SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        if (convToMethod == null) {
            return;
        }
        Class<?>[] parameterTypes = convToMethod.getParameterTypes();
        if (logonInfoManager.getInfoSwitch(logonInfo)) {
            logger.info("before execute:" + convToMethod.getName());
        }
        if (parameterTypes.length == 2) {
            convToMethod.invoke(logicManager, seNodeValue,
                    uiModelInstance);
        }
        if (parameterTypes.length == 3) {
            convToMethod.invoke(logicManager, seNodeValue,
                    uiModelInstance, logonInfo);
        }
    }

    /**
     * [Internal method] Using reflect to call convert UI To
     *
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ServiceEntityConfigureException
     * @throws InstantiationException
     * @throws NoSuchFieldException
     * @throws ServiceModuleProxyException
     */
    private void reflectConvertUIToUnion(
            IServiceUIModuleFieldConfig serviceUIModuleFieldConfig,
            ServiceModule serviceModule, ServiceEntityNode seNodeValue,
            SEUIComModel uiModelInstance,
            ServiceEntityManager serviceEntityManager,
            ServiceUIModelExtensionUnion serviceUIModelExtensionUnion)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, ServiceEntityConfigureException,
            InstantiationException, NoSuchFieldException,
            ServiceModuleProxyException {
        /*
         * Convert information from UIModel by invoking the convert UI To method
         * in reflective way
         */
        if (!ServiceCollectionsHelper
                .checkNullList(serviceUIModelExtensionUnion
                        .getUiModelNodeMapList())) {
            UIModelNodeMapConfigure uiModelNodeMapConfigure = ServiceModelExtensionHelper
                    .filterNodeMapConfigureByNodeInstId(
                            serviceUIModelExtensionUnion
                                    .getUiModelNodeMapList(),
                            serviceUIModelExtensionUnion.getNodeInstId());
            ServiceEntityManager executeManager = serviceEntityManager;
            if (uiModelNodeMapConfigure.getServiceEntityManager() != null) {
                executeManager = uiModelNodeMapConfigure
                        .getServiceEntityManager();
            }
            if (uiModelNodeMapConfigure != null) {
                if (ServiceEntityStringHelper
                        .checkNullString(uiModelNodeMapConfigure
                                .getConvUIToMethod())) {
                    return;
                }
                Object logicManager = executeManager;
                if (uiModelNodeMapConfigure.getLogicManager() != null) {
                    logicManager = uiModelNodeMapConfigure
                            .getLogicManager();
                }
                convertUIValueWrapper(uiModelNodeMapConfigure, logicManager,
                        uiModelInstance, seNodeValue);
                /*
                 * [Step3]Get the child node from current level
                 */
                if (serviceModule != null) {
                    List<UIModelNodeMapConfigure> childNodeMapList = ServiceModelExtensionHelper
                            .filterChildNodeMapList(
                                    serviceUIModelExtensionUnion
                                            .getUiModelNodeMapList(),
                                    uiModelNodeMapConfigure);
                    if (!ServiceCollectionsHelper
                            .checkNullList(childNodeMapList)) {
                        for (UIModelNodeMapConfigure subNodeMapConfigure : childNodeMapList) {
                            if (ServiceEntityStringHelper
                                    .checkNullString(subNodeMapConfigure
                                            .getConvUIToMethod())) {
                                continue;
                            } else {
                                /*
                                 * Pay Attention: if sub seNode also need to
                                 * update to db, should declare in service model
                                 * [Step 3.5] In case also need to update sub
                                 * module to DB
                                 */
                                ServiceEntityManager subExecuteManager = serviceEntityManager;
                                if (subNodeMapConfigure.getServiceEntityManager() != null) {
                                    subExecuteManager = subNodeMapConfigure
                                            .getServiceEntityManager();
                                }
                                ServiceEntityNode subEntityNode = ServiceModuleProxy
                                        .getSENodeFromDBWrapper(
                                                subNodeMapConfigure,
                                                subExecuteManager,
                                                seNodeValue,
                                                seNodeValue.getClient());
                                if (subEntityNode == null) {
                                    subEntityNode = ServiceModuleProxy
                                            .reflectCreateNewNode(
                                                    subNodeMapConfigure,
                                                    seNodeValue,
                                                    seNodeValue.getClient(),
                                                    subExecuteManager);
                                }
                                Object subLogicManager = serviceEntityManager;
                                if (subNodeMapConfigure.getLogicManager() != null) {
                                    subLogicManager = subNodeMapConfigure
                                            .getLogicManager();
                                }
                                convertUIValueWrapper(subNodeMapConfigure,
                                        subLogicManager, uiModelInstance,
                                        subEntityNode);
                                Field relativeModuleField = ServiceModuleProxy
                                        .getModuleFieldByNodeInstId(
                                                serviceModule.getClass(),
                                                subNodeMapConfigure
                                                        .getNodeInstID());
                                if (relativeModuleField != null) {
                                    //TODO reflective this logic
                                    setSEValueWrapper(relativeModuleField, serviceModule, subEntityNode);
                                } else {
                                    logger.error("No relative field defined in Service Model"
                                            + subNodeMapConfigure
                                            .getNodeInstID());
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // In case no uiModelNodeMapConfigure
            if (ServiceEntityStringHelper
                    .checkNullString(serviceUIModuleFieldConfig
                            .convUIToMethod())) {
                return;
            }
            Class<?>[] parameterTypes = {uiModelInstance.getClass(),
                    seNodeValue.getClass()};
            Method convUIToMethod = serviceEntityManager.getClass()
                    .getMethod(serviceUIModuleFieldConfig.convUIToMethod(),
                            parameterTypes);
            convUIToMethod.invoke(serviceEntityManager, uiModelInstance,
                    seNodeValue);
        }
    }

    private void setSEValueWrapper(Field field, Object instance, ServiceEntityNode seNodeValue) throws IllegalArgumentException, IllegalAccessException {
        boolean listFlag = field.getType().isAssignableFrom(List.class);
        field.setAccessible(true);
        if (listFlag) {
            List<ServiceEntityNode> seNodeList = new ArrayList<>();
            seNodeList.add(seNodeValue);
            field.set(instance, seNodeList);
        } else {
            field.set(instance, seNodeValue);
        }
    }

    private void convertUIValueWrapper(
            UIModelNodeMapConfigure uiModelNodeMapConfigure,
            Object logicManager, SEUIComModel uiModelInstance,
            ServiceEntityNode seNodeValue) throws NoSuchMethodException,
            SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Class<?>[] parameterTypes = uiModelNodeMapConfigure
                .getConvUIToMethodParas();
        Method convUIToMethod = logicManager.getClass().getMethod(
                uiModelNodeMapConfigure.getConvUIToMethod(), parameterTypes);
        convUIToMethod.invoke(logicManager, uiModelInstance, seNodeValue);
    }

    /**
     * Main entrance to generate Service UI Model from service module instance
     *
     * @param serviceUIModuleType
     * @param serviceModuleType
     * @param serviceEntityManager
     * @param serviceModule
     * @return
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     * @throws ServiceUIModuleProxyException
     */
    public ServiceUIModule genServiceUIModuleFromServiceModel(
            Class<?> serviceUIModuleType, Class<?> serviceModuleType,
            ServiceEntityManager serviceEntityManager,
            ServiceModule serviceModule, LogonInfo logonInfo,
            List<ServiceModuleConvertPara> additionalConvertParaList)
            throws ServiceModuleProxyException,
            ServiceEntityConfigureException, ServiceUIModuleProxyException {

        try {
            /*
             * [Step1] Retrieve the core module field
             */
            Field coreModuleField = ServiceModuleProxy
                    .getCoreModuleField(serviceModuleType);
            if (coreModuleField == null) {
                // raise exception when no core module field found
                throw new ServiceModuleProxyException(
                        ServiceModuleProxyException.PARA_NOCOREMODULE,
                        serviceModuleType.getSimpleName());
            }
            coreModuleField.setAccessible(true);
            ServiceEntityNode seNodeValue = (ServiceEntityNode) coreModuleField
                    .get(serviceModule);
            IServiceModuleFieldConfig serviceModuleFieldConfig = coreModuleField
                    .getAnnotation(IServiceModuleFieldConfig.class);
            if (serviceModuleFieldConfig == null) {
                // raise exception when no core module field found
                throw new ServiceModuleProxyException(
                        ServiceModuleProxyException.PARA_NOANNOTATION,
                        coreModuleField.getName());
            }
            /*
             * [Step2] Retrieve core UI model field
             */
            Field coreUIModuleField = getCoreUIModuleField(serviceUIModuleType);
            if (coreUIModuleField == null) {
                // raise exception when no core module field found
                throw new ServiceUIModuleProxyException(
                        ServiceUIModuleProxyException.PARA_NOCOREMODULE,
                        serviceUIModuleType.getSimpleName());
            }
            coreUIModuleField.setAccessible(true);
            IServiceUIModuleFieldConfig serviceUIModuleFieldConfig = coreUIModuleField
                    .getAnnotation(IServiceUIModuleFieldConfig.class);
            if (serviceUIModuleFieldConfig == null) {
                // raise exception when no core module field found
                throw new ServiceUIModuleProxyException(
                        ServiceUIModuleProxyException.PARA_NOANNOTATION,
                        serviceUIModuleType.getSimpleName());
            }
            // New Service UI model instance
            ServiceUIModule serviceUIModule = (ServiceUIModule) serviceUIModuleType
                    .newInstance();
            // New UI model instance
            SEUIComModel uiModelInstance = (SEUIComModel) coreUIModuleField
                    .getType().newInstance();
            /*
             * [Step3] Invoke the convert to UI method in reflective way
             */
            reflectConvertToUIUnion(serviceUIModuleFieldConfig,
                    coreModuleField, coreUIModuleField, seNodeValue,
                    uiModelInstance, serviceEntityManager, logonInfo);
            /*
             * [Step4] Set the UI model back to service UI model
             */
            coreUIModuleField.setAccessible(true);
            coreUIModuleField.set(serviceUIModule, uiModelInstance);
            /*
             * [Step5] find the sub list attributes
             */
            List<Field> listTypeFields = getListTypeFields(serviceModuleType);
            List<Field> listUIModelTypeFields = getListTypeFields(serviceUIModuleType);
            if (!ServiceCollectionsHelper.checkNullList(listTypeFields)) {
                for (Field listField : listTypeFields) {
                    if (ServiceEntityFieldsHelper
                            .checkSuperClassExtends(ServiceEntityFieldsHelper
                                            .getListSubType(listField),
                                    ServiceEntityNode.class.getSimpleName())) {
                        // In case the list sub type is service entity node
                        setSubEntityListValueWrapper(null, listField,
                                listUIModelTypeFields, serviceModule,
                                serviceUIModule, serviceEntityManager,
                                logonInfo, additionalConvertParaList);
                    }
                    if (ServiceEntityFieldsHelper
                            .checkSuperClassExtends(ServiceEntityFieldsHelper
                                            .getListSubType(listField),
                                    ServiceModule.class.getSimpleName())) {
                        // In case the list sub type is service module
                        setSubServiceModelListValueWrapper(null, null,
                                listField, listUIModelTypeFields,
                                serviceModule, serviceUIModule,
                                serviceUIModuleType, serviceModuleType,
                                serviceEntityManager, logonInfo,
                                additionalConvertParaList);
                    }
                }
            }
            return serviceUIModule;
        } catch (IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
            throw new ServiceModuleProxyException(
                    ServiceModuleProxyException.PARA_SYSTEM_WRONG,
                    e.getMessage());
        }
    }

    /**
     * [Internal method] Wrapper method to fill the sub field value to Service
     * UI Model
     *
     * @param subField
     * @param serviceUIModelExtension
     * @param serviceUIModelExtensionUnion
     * @param subUIModelTypeFields
     * @param serviceModule
     * @param serviceUIModule
     * @param serviceEntityManager
     * @param logonInfo
     * @param additionalConvertParaList
     * @throws ServiceUIModuleProxyException
     * @throws ServiceModuleProxyException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ServiceEntityConfigureException
     * @throws InvocationTargetException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     */
    private void fillSubUIFieldWrapper(Field subField,
                                       ServiceUIModelExtension serviceUIModelExtension,
                                       ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
                                       List<Field> subUIModelTypeFields, ServiceModule serviceModule,
                                       ServiceUIModule serviceUIModule,
                                       ServiceEntityManager serviceEntityManager, LogonInfo logonInfo,
                                       List<ServiceModuleConvertPara> additionalConvertParaList,
                                       boolean listFlag) throws ServiceUIModuleProxyException,
            ServiceModuleProxyException, IllegalArgumentException,
            ServiceEntityConfigureException, NoSuchMethodException,
            InvocationTargetException {
        IServiceModuleFieldConfig subFieldConfig = subField
                .getAnnotation(IServiceModuleFieldConfig.class);
        if (subFieldConfig == null) {
            throw new ServiceModuleProxyException(
                    ServiceUIModuleProxyException.PARA_NOANNOTATION,
                    subField.getName());
        }
        ExtensionUnionResponse subExtensionUnionResponse = ServiceModelExtensionHelper
                .getUIModelExtensionByNodeInstId(
                        subFieldConfig.nodeInstId(),
                        serviceUIModelExtension);
        if (subExtensionUnionResponse == null) {
            logger.error("Missing ServiceUIModelExtensionUnion:"
                    + subFieldConfig.nodeInstId());
            return;
        }
        ServiceUIModelExtensionUnion subExtensionUnion = subExtensionUnionResponse.getServiceUIModelExtensionUnion();
        ServiceUIModelExtension subExtension = subExtensionUnionResponse.getServiceUIModelExtension();
        try {
            if (listFlag) {
                if (ServiceEntityFieldsHelper.checkSuperClassExtends(
                        ServiceEntityFieldsHelper.getListSubType(subField),
                        ServiceEntityNode.class.getSimpleName())) {
                    setSubEntityListValueWrapper(subExtensionUnion, subField,
                            subUIModelTypeFields, serviceModule,
                            serviceUIModule, serviceEntityManager, logonInfo,
                            additionalConvertParaList);
                }
                if (ServiceEntityFieldsHelper.checkSuperClassExtends(
                        ServiceEntityFieldsHelper.getListSubType(subField),
                        ServiceModule.class.getSimpleName())) {
                    // In case the list sub type is service module
                    setSubServiceModelListValueWrapper(subExtension,
                            subExtensionUnion, subField, subUIModelTypeFields,
                            serviceModule, serviceUIModule,
                            serviceUIModule.getClass(),
                            serviceModule.getClass(), serviceEntityManager,
                            logonInfo, additionalConvertParaList);
                }
            } else {
                if (ServiceEntityFieldsHelper.checkSuperClassExtends(
                        subField.getType(),
                        ServiceEntityNode.class.getSimpleName())) {
                    setSubEntityFlatValueWrapper(subExtensionUnion,
                            subField, subUIModelTypeFields, serviceModule,
                            serviceUIModule, serviceEntityManager, logonInfo,
                            additionalConvertParaList);
                }
                if (ServiceEntityFieldsHelper
                        .checkSuperClassExtends(subField.getType(),
                                ServiceModule.class.getSimpleName())) {
                    setSubServiceModelFlatValueWrapper(subExtension,
                            subExtensionUnion, subField,
                            subUIModelTypeFields, serviceModule,
                            serviceUIModule, serviceUIModule.getClass(),
                            serviceModule.getClass(), serviceEntityManager,
                            logonInfo, additionalConvertParaList);
                }
            }

        } catch (InstantiationException | IllegalAccessException | SecurityException e) {
            throw new ServiceModuleProxyException(
                    ServiceModuleProxyException.PARA_SYSTEM_WRONG,
                    e.getMessage());
        }
    }

    /**
     * [Internal method] In case the current list property element is service
     * model instance, then generating the SE Service UI module instance list
     * and set back to parent service UI model
     *
     * @param serviceModule
     * @param serviceUIModule
     * @param serviceUIModuleType
     * @param serviceModuleType
     * @param serviceEntityManager
     * @throws ServiceUIModuleProxyException
     * @throws ServiceModuleProxyException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ServiceEntityConfigureException
     */
    private void setSubServiceModelFlatValueWrapper(
            ServiceUIModelExtension serviceUIModelExtension,
            ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
            Field subField, List<Field> subUIModelTypeFields,
            ServiceModule serviceModule, ServiceUIModule serviceUIModule,
            Class<?> serviceUIModuleType, Class<?> serviceModuleType,
            ServiceEntityManager serviceEntityManager, LogonInfo logonInfo,
            List<ServiceModuleConvertPara> additionalConvertParaList)
            throws ServiceUIModuleProxyException, ServiceModuleProxyException,
            IllegalArgumentException, IllegalAccessException,
            ServiceEntityConfigureException {
        /*
         * [Step1] Get the list value from list field of the service module
         */
        subField.setAccessible(true);
        ServiceModule subServiceModule = (ServiceModule) subField
                .get(serviceModule);

        if (subServiceModule != null) {
            /*
             * [Step2] In case the list value is not null, then get the field
             * configure and retrieve the nodename
             */
            IServiceModuleFieldConfig subFieldConfigure = subField
                    .getAnnotation(IServiceModuleFieldConfig.class);
            if (subFieldConfigure == null) {
                // raise exception when no core module field found
                throw new ServiceModuleProxyException(
                        ServiceModuleProxyException.PARA_NOANNOTATION,
                        subField.getName());
            }
            /*
             * [Step3] Retrieve the relative field and field configure from
             * service UI module
             */
            Field subServiceUIModelField = this.filterFieldByNodeInstId(
                    subUIModelTypeFields, subFieldConfigure.nodeInstId());
            if (subServiceUIModelField == null) {
                return;
            }
            IServiceUIModuleFieldConfig subUIFieldConfigure = subServiceUIModelField
                    .getAnnotation(IServiceUIModuleFieldConfig.class);
            if (subUIFieldConfigure == null) {
                // raise exception when no core module field found
                throw new ServiceUIModuleProxyException(
                        ServiceUIModuleProxyException.PARA_NOANNOTATION,
                        subServiceUIModelField.getName());
            }
            /*
             * [Step4] Traverse each list data (service module element),and gen
             * Service UI module value from method
             * genServiceUIModuleFromServiceModel and build the
             * list<ServiceUIModule> properties and value, then set back
             */
            if (serviceUIModelExtensionUnion == null) {
                Class<?> subServiceModuleClass = subField.getType();
                Class<?> subServiceUIModuleClass = subServiceUIModelField
                        .getType();
                ServiceUIModule subServiceUIModule = genServiceUIModuleFromServiceModel(
                        subServiceUIModuleClass, subServiceModuleClass,
                        serviceEntityManager, subServiceModule, logonInfo,
                        additionalConvertParaList);
                // Set sub UI model class back to service UI Model property
                subServiceUIModelField.setAccessible(true);
                subServiceUIModelField.set(serviceUIModule, subServiceUIModule);
            } else {
                Class<?> subServiceModuleClass = subField.getType();
                Class<?> subServiceUIModuleClass = subServiceUIModelField
                        .getType();
                ServiceUIModule subServiceUIModule = genServiceUIModuleFromServiceModelCore(
                        subServiceUIModuleClass, subServiceModuleClass,
                        serviceUIModelExtension, serviceUIModelExtensionUnion,
                        serviceEntityManager, subServiceModule, logonInfo,
                        additionalConvertParaList);
                // Set sub UI model class back to service UI Model property
                subServiceUIModelField.setAccessible(true);
                subServiceUIModelField.set(serviceUIModule, subServiceUIModule);
            }

        }
    }

    /**
     * [Internal method] In case the current list property element is service
     * model instance, then generating the SE Service UI module instance list
     * and set back to parent service UI model
     *
     * @param listField
     * @param listUIModelTypeFields
     * @param serviceModule
     * @param serviceUIModule
     * @param serviceUIModuleType
     * @param serviceModuleType
     * @param serviceEntityManager
     * @throws ServiceUIModuleProxyException
     * @throws ServiceModuleProxyException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ServiceEntityConfigureException
     */
    @SuppressWarnings("unchecked")
    private void setSubServiceModelListValueWrapper(
            ServiceUIModelExtension serviceUIModelExtension,
            ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
            Field listField, List<Field> listUIModelTypeFields,
            ServiceModule serviceModule, ServiceUIModule serviceUIModule,
            Class<?> serviceUIModuleType, Class<?> serviceModuleType,
            ServiceEntityManager serviceEntityManager, LogonInfo logonInfo,
            List<ServiceModuleConvertPara> additionalConvertParaList)
            throws ServiceUIModuleProxyException, ServiceModuleProxyException,
            IllegalArgumentException, IllegalAccessException,
            ServiceEntityConfigureException {
        /*
         * [Step1] Get the list value from list field of the service module
         */
        listField.setAccessible(true);
        List<?> subListValue = (List<?>) listField.get(serviceModule);
        if (!ServiceCollectionsHelper.checkNullList(subListValue)) {
            /*
             * [Step2] In case the list value is not null, then get the field
             * configure and retrieve the nodename
             */
            IServiceModuleFieldConfig subListFieldConfigure = listField
                    .getAnnotation(IServiceModuleFieldConfig.class);
            if (subListFieldConfigure == null) {
                // raise exception when no core module field found
                throw new ServiceModuleProxyException(
                        ServiceModuleProxyException.PARA_NOANNOTATION,
                        listField.getName());
            }
            /*
             * [Step3] Retrieve the relative field and field configure from
             * service UI module
             */
            Field listServiceUIModelField = this.filterFieldByNodeName(
                    listUIModelTypeFields, subListFieldConfigure.nodeName());
            if (listServiceUIModelField == null) {
                return;
            }
            IServiceUIModuleFieldConfig subListUIFieldConfigure = listServiceUIModelField
                    .getAnnotation(IServiceUIModuleFieldConfig.class);
            if (subListUIFieldConfigure == null) {
                // raise exception when no core module field found
                throw new ServiceUIModuleProxyException(
                        ServiceUIModuleProxyException.PARA_NOANNOTATION,
                        listServiceUIModelField.getName());
            }
            /*
             * [Step4] Traverse each list data (service module element),and gen
             * Service UI module value from method
             * genServiceUIModuleFromServiceModel and build the
             * list<ServiceUIModule> properties and value, then set back
             */
            @SuppressWarnings("rawtypes")
            List subServiceUIModelList = new ArrayList();
            for (int i = 0; i < subListValue.size(); i++) {
                if (serviceUIModelExtensionUnion == null) {
                    ServiceModule subServiceModule = (ServiceModule) subListValue
                            .get(i);
                    Class<?> subServiceModuleClass = ServiceEntityFieldsHelper
                            .getListSubType(listField);
                    Class<?> subServiceUIModuleClass = ServiceEntityFieldsHelper
                            .getListSubType(listServiceUIModelField);
                    ServiceUIModule subServiceUIModule = genServiceUIModuleFromServiceModel(
                            subServiceUIModuleClass, subServiceModuleClass,
                            serviceEntityManager, subServiceModule, logonInfo,
                            additionalConvertParaList);
                    subServiceUIModelList.add(subServiceUIModule);
                } else {
                    ServiceModule subServiceModule = (ServiceModule) subListValue
                            .get(i);
                    Class<?> subServiceModuleClass = ServiceEntityFieldsHelper
                            .getListSubType(listField);
                    Class<?> subServiceUIModuleClass = ServiceEntityFieldsHelper
                            .getListSubType(listServiceUIModelField);
                    ServiceUIModule subServiceUIModule = genServiceUIModuleFromServiceModelCore(
                            subServiceUIModuleClass, subServiceModuleClass,
                            serviceUIModelExtension,
                            serviceUIModelExtensionUnion, serviceEntityManager,
                            subServiceModule, logonInfo,
                            additionalConvertParaList);
                    subServiceUIModelList.add(subServiceUIModule);
                }
            }
            // Set sub ui model class back to service UI Model property
            listServiceUIModelField.setAccessible(true);
            listServiceUIModelField.set(serviceUIModule, subServiceUIModelList);
        }
    }

    /**
     * In case need to generate Service Entity Node models list from UIModel by
     *
     * @param serviceUIModelExtensionUnion
     * @param serviceEntityManager
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws ServiceEntityConfigureException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public List<ServiceEntityNode> genSeNodeListInExtensionUnion(
            ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
            ServiceEntityManager serviceEntityManager,
            ServiceEntityNode baseEntityNode, Class<?> serviceModuleType,
            SEUIComModel uiModelValue) throws NoSuchFieldException,
            SecurityException, IllegalArgumentException,
            IllegalAccessException, NoSuchMethodException,
            ServiceEntityConfigureException, InvocationTargetException,
            InstantiationException {
        List<UIModelNodeMapConfigure> uiModelNodeMapList = serviceUIModelExtensionUnion
                .getUiModelNodeMapList();
        UIModelNodeMapConfigure hostMapConfigure = ServiceModelExtensionHelper
                .getFirstNodeMapConfigure(uiModelNodeMapList);
        List<UIModelNodeMapConfigure> childNodeMapList = ServiceModelExtensionHelper
                .filterChildNodeMapList(uiModelNodeMapList, hostMapConfigure);
        List<ServiceEntityNode> resultList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(childNodeMapList)) {
            for (UIModelNodeMapConfigure uiModelNodeMapConfigure : childNodeMapList) {
                // Only generate the updated sub Service entity node instance
                // when it is in home SE
                if (!hostMapConfigure.getSeName().equals(
                        uiModelNodeMapConfigure.getSeName())) {
                    continue;
                }
                // In case there is no conv UI to method, don't need to generate
                // SE instance for update
                if (uiModelNodeMapConfigure.getConvUIToMethod() == null) {
                    continue;
                }
                List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
                keyList = ServiceModelExtensionHelper
                        .generateKeyStructFromNodeMapConfigure(
                                uiModelNodeMapConfigure, baseEntityNode,
                                baseEntityNode.getClient());
                if (ServiceCollectionsHelper.checkNullList(keyList)) {
                    continue;
                }
                ServiceEntityNode seNodeValue = null;
                ServiceEntityManager executeManager = serviceEntityManager;
                if (uiModelNodeMapConfigure.getServiceEntityManager() != null) {
                    executeManager = uiModelNodeMapConfigure
                            .getServiceEntityManager();
                }
                seNodeValue = executeManager.getEntityNodeByKeyList(keyList,
                        uiModelNodeMapConfigure.getNodeName(), null);
                if (seNodeValue == null) {
                    if (serviceModuleType != null) {
                        /*
                         * In case current service entity node instance can not
                         * be found in persistence, then generate a new
                         * instance.
                         */
                        String seNodeName = uiModelNodeMapConfigure
                                .getNodeName();
                        if (ServiceEntityNode.NODENAME_ROOT.equals(seNodeName)) {
                            /*
                             * In case this node type is a root node.
                             */
                            seNodeValue = executeManager
                                    .newRootEntityNode(baseEntityNode
                                            .getClient());
                        } else {
                            /*
                             * In case this node type is not a root node,
                             * generate new one from baseNode.
                             */
                            seNodeValue = executeManager.newEntityNode(
                                    baseEntityNode, seNodeName);
                        }
                    }
                }
                // In case can not initialize
                if (seNodeValue == null) {
                    continue;
                }
                Class<?>[] parameterTypes = {uiModelValue.getClass(),
                        seNodeValue.getClass()};
                if (uiModelNodeMapConfigure.getConvUIToMethod() != null) {
                    Object logicManager = serviceEntityManager;
                    if (uiModelNodeMapConfigure.getLogicManager() != null) {
                        logicManager = uiModelNodeMapConfigure
                                .getLogicManager();
                    }
                    Method convUIToMethod = logicManager.getClass().getMethod(
                            uiModelNodeMapConfigure.getConvUIToMethod(),
                            parameterTypes);
                    convUIToMethod.invoke(logicManager, uiModelValue,
                            seNodeValue);
                    resultList.add(seNodeValue);
                }
            }
        }
        return resultList;
    }

    public static List<ServiceModuleConvertPara> genSimpleConvertPara(String targetNodeInstId, ServiceConvertMeta serviceConvertMeta){
        ServiceModuleConvertPara serviceModuleConvertPara = new ServiceModuleConvertPara();
        serviceModuleConvertPara.setTargetNodeInstId(targetNodeInstId);
        serviceModuleConvertPara.setMeta(serviceConvertMeta);
        return ServiceCollectionsHelper.asList(serviceModuleConvertPara);
    }

    public SEUIComModel genUIModuleInExtensionUnion(Class<?> uiModelClass,
                                                    ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
                                                    ServiceEntityManager serviceEntityManager,
                                                    ServiceEntityNode rawSENodeValue, LogonInfo logonInfo,
                                                    List<ServiceModuleConvertPara> additionalConvertParaList)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        try {
            List<UIModelNodeMapConfigure> uiModelNodeMapList = serviceUIModelExtensionUnion
                    .getUiModelNodeMapList();
            // create new instance of UIModel
            SEUIComModel uiModelInstance = (SEUIComModel) uiModelClass
                    .newInstance();
            if (!ServiceCollectionsHelper.checkNullList(uiModelNodeMapList)) {
                UIModelNodeMapConfigure startMapConfigure = ServiceModelExtensionHelper
                        .getFirstNodeMapConfigure(uiModelNodeMapList);
                if (startMapConfigure == null) {
                    throw new ServiceModuleProxyException(ServiceModuleProxyException.PARA_NO_STARTCOFIG_NODE,
                            uiModelClass.getSimpleName());
                }
                // Process the host map configure
                if (!ServiceEntityStringHelper
                        .checkNullString(startMapConfigure.getConvToUIMethod())) {
                    callConvToUIReflectiveWrapper(startMapConfigure,
                            serviceEntityManager, rawSENodeValue,
                            uiModelInstance, logonInfo,
                            additionalConvertParaList);
                }
                List<UIModelNodeMapConfigure> childNodeMapList = ServiceModelExtensionHelper
                        .filterChildNodeMapList(uiModelNodeMapList,
                                startMapConfigure);
                if (!ServiceCollectionsHelper.checkNullList(childNodeMapList)) {
                    for (UIModelNodeMapConfigure uiModelNodeMapConfigure : childNodeMapList) {
                        processUIModelByConfigureUnionRecursive(
                                uiModelInstance, rawSENodeValue,
                                serviceEntityManager, uiModelNodeMapConfigure,
                                rawSENodeValue.getClient(), uiModelNodeMapList,
                                logonInfo);
                    }
                }
            }
            return uiModelInstance;
        } catch (InstantiationException | IllegalAccessException | SecurityException | NoSuchMethodException
                | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e,
                    null));
            throw new ServiceModuleProxyException(
                    ServiceModuleProxyException.PARA_SYSTEM_WRONG,
                    e.getMessage());
        }
    }

    /**
     * [Internal method] Reflective call conv to UI method with additional
     * service entity values
     *
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private void callConvToUIReflectiveWrapper(
            UIModelNodeMapConfigure hostMapConfigure,
            ServiceEntityManager serviceEntityManager,
            ServiceEntityNode rawSENodeValue, SEUIComModel uiModelInstance,
            LogonInfo logonInfo,
            List<ServiceModuleConvertPara> additionalConvertParaList)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        Object logicManager = serviceEntityManager;
        if (hostMapConfigure.getLogicManager() != null) {
            logicManager = hostMapConfigure
                    .getLogicManager();
        }
        Method convToUIMethod = getConvToMethodWrapper(logicManager.getClass(),
                hostMapConfigure.getConvToUIMethod(),
                hostMapConfigure.getConvToUIMethodParas(), logonInfo);
        Class<?>[] paras = hostMapConfigure.getConvToUIMethodParas();
        if (logonInfoManager.getInfoSwitch(logonInfo)) {
            logger.info("before execute:" + convToUIMethod.getName());
        }
        if (paras != null && paras.length == 2) {
            invokeConvToMethodWrapper(logicManager, convToUIMethod,
                    rawSENodeValue, uiModelInstance, logonInfo);
        }
        // In case should using additional paras to call conv to UI method.
        if (paras != null && paras.length > 2) {
            List<Object> paraObjectList = getCallConvetToUIParaArray(
                    hostMapConfigure, rawSENodeValue, uiModelInstance,
                    additionalConvertParaList);
            if (paras.length == 3) {
                convToUIMethod.invoke(logicManager, paraObjectList.get(0),
                        paraObjectList.get(1), paraObjectList.get(2), logonInfo);
            }

            if (paras.length == 4) {
                convToUIMethod.invoke(logicManager, paraObjectList.get(0),
                        paraObjectList.get(1), paraObjectList.get(2),
                        paraObjectList.get(3), logonInfo);
            }

            if (paras.length == 5) {
                convToUIMethod.invoke(logicManager, paraObjectList.get(0),
                        paraObjectList.get(1), paraObjectList.get(2),
                        paraObjectList.get(3), paraObjectList.get(4), logonInfo);
            }
        }
    }

    /**
     * [Internal method] When calling convert to UI method reflective, then
     *
     * @param uiModelMapConfigure
     * @param rawSENodeValue
     * @param uiModelInstance
     * @param additionalConvertParaList
     * @return
     */
    private List<Object> getCallConvetToUIParaArray(
            UIModelNodeMapConfigure uiModelMapConfigure,
            ServiceEntityNode rawSENodeValue, SEUIComModel uiModelInstance,
            List<ServiceModuleConvertPara> additionalConvertParaList) {
        Class<?>[] paras = uiModelMapConfigure.getConvToUIMethodParas();
        List<Object> paraArray = new ArrayList<>();
        // Traverse the conversion to UI method parameter classes
        for (int i = 0; i < paras.length; i++) {
            Class<?> curParaType = paras[i];
            if (curParaType.getSimpleName().equals(
                    rawSENodeValue.getClass().getSimpleName())) {
                // In case input parameter <code>rawSENodeValue</code> matches
                // this parameter class type
                paraArray.add(rawSENodeValue);
                continue;
            }
            if (ServiceEntityFieldsHelper.checkSuperClassExtends(curParaType,
                    SEUIComModel.class.getSimpleName())) {
                // In case input parameter <code>uiModelInstance</code> matches
                // this parameter class type
                paraArray.add(uiModelInstance);
                continue;
            }
            // Search parameter from additional conversion parameter:
            String targetNodeInstId = uiModelMapConfigure.getNodeInstID();
            Object result = filterParaCom(curParaType, targetNodeInstId,
                    additionalConvertParaList);
            paraArray.add(result);
        }
        return paraArray;

    }

    public ServiceModuleConvertPara filterParaByNodeInstId(
            List<ServiceModuleConvertPara> additionalConvertParaList,
            String nodeInstId) {
        if (ServiceCollectionsHelper.checkNullList(additionalConvertParaList)) {
            return null;
        }
        for (ServiceModuleConvertPara serviceModuleConvertPara : additionalConvertParaList) {
            if (serviceModuleConvertPara.getNodeInstId().equals(nodeInstId)) {
                // In case the parameter type matches.
                return serviceModuleConvertPara;
            }
        }
        return null;
    }

    /**
     * Package serviceEntityList list value to ConvertPara automatically and add
     * or merge to additionalConvertParaList
     *
     * @param additionalConvertParaList
     * @param serviceEntityList
     */
    public void mergeToConvertParaList(
            List<ServiceModuleConvertPara> additionalConvertParaList,
            List<ServiceEntityNode> serviceEntityList) {
        if (ServiceCollectionsHelper.checkNullList(serviceEntityList)) {
            return;
        }
        String nodeInstId = getDefaultNodeInstId(serviceEntityList.get(0));
        ServiceModuleConvertPara serviceModuleConvertPara = filterParaByNodeInstId(
                additionalConvertParaList, nodeInstId);
        if (serviceModuleConvertPara != null) {
            // In case there is no pre-defined value list
            if(ServiceCollectionsHelper.checkNullList(serviceModuleConvertPara.getServiceEntityList())){
                serviceModuleConvertPara.setServiceEntityList(serviceEntityList);
            }
        } else {
            serviceModuleConvertPara = new ServiceModuleConvertPara();
            serviceModuleConvertPara.setNodeInstId(nodeInstId);
            serviceModuleConvertPara.setServiceEntityList(serviceEntityList);
            if(additionalConvertParaList == null){
                return;
            }
            additionalConvertParaList.add(serviceModuleConvertPara);
        }
    }

    /**
     * Package serviceEntityNode value to ConvertPara automatically and add or
     * merge to additionalConvertParaList
     *
     * @param additionalConvertParaList
     */
    public void mergeToConvertParaList(
            List<ServiceModuleConvertPara> additionalConvertParaList,
            ServiceEntityNode serviceEntityNode) {
        if (serviceEntityNode == null) {
            return;
        }
        String nodeInstId = getDefaultNodeInstId(serviceEntityNode);
        ServiceModuleConvertPara serviceModuleConvertPara = filterParaByNodeInstId(
                additionalConvertParaList, nodeInstId);
        if (serviceModuleConvertPara != null) {
            // In case there is no pre-defined service entity value
            if(serviceModuleConvertPara.getServiceEntityNode() == null){
                serviceModuleConvertPara.setServiceEntityNode(serviceEntityNode);
            }
        } else {
            serviceModuleConvertPara = new ServiceModuleConvertPara();
            serviceModuleConvertPara.setNodeInstId(nodeInstId);
            serviceModuleConvertPara.setServiceEntityNode(serviceEntityNode);
            if(additionalConvertParaList == null){
                additionalConvertParaList = new ArrayList<>();
            }
            additionalConvertParaList.add(serviceModuleConvertPara);
        }
    }

    public Object filterParaCom(Class<?> paraType, String targetNodeInstId,
                                       List<ServiceModuleConvertPara> additionalConvertParaList) {
        if(ServiceCollectionsHelper.checkNullList(additionalConvertParaList)){
            return null;
        }
        ServiceModuleConvertPara targetConvertPara = ServiceCollectionsHelper.filterOnline(additionalConvertParaList,
                tmpConvertPara->{
            return targetNodeInstId.equals(tmpConvertPara.getTargetNodeInstId());
                });
        if(targetConvertPara == null){
            return null;
        }
        if (ServiceEntityFieldsHelper.checkSuperClassExtends(paraType,
                ServiceEntityNode.class.getSimpleName())) {
            return targetConvertPara.getServiceEntityNode();
        }
        if (paraType.getSimpleName().equals(List.class.getSimpleName())) {
            // In case current input parameter is type of List
            return targetConvertPara.getServiceEntityList();
        }
        if(ServiceEntityFieldsHelper.checkSuperClassExtends(paraType, ServiceConvertMeta.class.getSimpleName())){
            return targetConvertPara.getMeta();
        }
        // Impossible case:
        return null;
    }

    protected void processUIModelByConfigureUnionRecursive(
            SEUIComModel uiModelInstance, ServiceEntityNode rawSENodeValue,
            ServiceEntityManager serviceEntityManager,
            UIModelNodeMapConfigure curNodeConfigure, String client,
            List<UIModelNodeMapConfigure> uiModelNodeMapList,
            LogonInfo logonInfo) throws NoSuchMethodException,
            SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException,
            NoSuchFieldException, ServiceEntityConfigureException {
        /*
         * [Step1] retrieve the SE node value to current node configure from raw
         * SE node value
         */
        ServiceEntityNode seNodeValue = ServiceModuleProxy
                .getSENodeFromDBWrapper(curNodeConfigure, serviceEntityManager,
                        rawSENodeValue, client);
        if (seNodeValue == null) {
            // In case no service entity node instance found from persistence,
            // then just return.
            return;
        }
        // call conv to UI method
        if (!ServiceEntityStringHelper.checkNullString(curNodeConfigure
                .getConvToUIMethod())) {
            Object logicManager = serviceEntityManager;
            if (curNodeConfigure.getLogicManager() != null) {
                logicManager = curNodeConfigure.getLogicManager();
            }
            Method convToUIMethod = getConvToMethodWrapper(
                    logicManager.getClass(),
                    curNodeConfigure.getConvToUIMethod(),
                    curNodeConfigure.getConvToUIMethodParas(), logonInfo);
            invokeConvToMethodWrapper(logicManager, convToUIMethod,
                    seNodeValue, uiModelInstance, logonInfo);

        }
        /*
         * [Step2] Retrieve all the sub node from current node configure and
         * call this method recursive
         */
        List<UIModelNodeMapConfigure> childNodeMapList = ServiceModelExtensionHelper
                .filterChildNodeMapList(uiModelNodeMapList, curNodeConfigure);
        if (!ServiceCollectionsHelper.checkNullList(childNodeMapList)) {
            for (UIModelNodeMapConfigure uiModelNodeMapConfigure : childNodeMapList) {
                processUIModelByConfigureUnionRecursive(uiModelInstance,
                        seNodeValue, serviceEntityManager,
                        uiModelNodeMapConfigure, client, uiModelNodeMapList,
                        logonInfo);
            }
        }
    }

    /**
     * [Internal method] In case the current list property element is service UI
     * model instance, then generating the SE Service module instance list and
     * set back to parent service model
     *
     * @param serviceModule
     * @param serviceUIModule
     * @param serviceUIModuleType
     * @param serviceModuleType
     * @param serviceEntityManager
     * @throws ServiceUIModuleProxyException
     * @throws ServiceModuleProxyException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ServiceEntityConfigureException
     */
    private void setSubServiceUIModelFlatValueWrapper(Field subServiceUIField,
                                                      List<Field> subServiceModelTypeFieldList,
                                                      ServiceModule serviceModule, ServiceUIModule serviceUIModule,
                                                      Class<?> serviceUIModuleType, Class<?> serviceModuleType,
                                                      ServiceEntityManager serviceEntityManager,
                                                      ServiceUIModelExtension serviceUIModelExtension,
                                                      ServiceUIModelExtensionUnion serviceUIModelExtensionUnion)
            throws ServiceUIModuleProxyException, ServiceModuleProxyException,
            IllegalArgumentException, IllegalAccessException,
            ServiceEntityConfigureException {
        /*
         * [Step1] Get the list value from list field of the service UI module
         */
        subServiceUIField.setAccessible(true);
        ServiceUIModule subServiceUIModule = (ServiceUIModule) subServiceUIField
                .get(serviceUIModule);
        if (subServiceUIModule != null) {
            /*
             * [Step2] In case the list value is not null, then get the field
             * configure and retrieve the node name
             */
            IServiceUIModuleFieldConfig subUIFieldConfigure = subServiceUIField
                    .getAnnotation(IServiceUIModuleFieldConfig.class);
            if (subUIFieldConfigure == null) {
                // raise exception when no core module field found
                throw new ServiceUIModuleProxyException(
                        ServiceUIModuleProxyException.PARA_NOANNOTATION,
                        subServiceUIField.getName());
            }
            /*
             * [Step3] Retrieve the relative field and field configure from
             * service module
             */
            Field subTypeField = ServiceModuleProxy
                    .filterListFieldByNodeInstId(subServiceModelTypeFieldList,
                            subUIFieldConfigure.nodeInstId());
            if (subTypeField == null) {
                // Try again with node name
                subTypeField = ServiceModuleProxy.filterListFieldByNodeName(
                        subServiceModelTypeFieldList,
                        subUIFieldConfigure.nodeName());
                if (subTypeField == null) {
                    return;
                }
            }
            IServiceModuleFieldConfig subListFieldConfigure = subTypeField
                    .getAnnotation(IServiceModuleFieldConfig.class);
            if (subListFieldConfigure == null) {
                // raise exception when no core module field found
                throw new ServiceUIModuleProxyException(
                        ServiceUIModuleProxyException.PARA_NOANNOTATION,
                        subTypeField.getName());
            }
            /*
             * [Step4] Traverse each list data (service UI module element),and
             * gen Service module value from method
             * getServiceModuleFromServiceUIModule and build the
             * list<ServiceModule> properties and value, then set back
             */

            ServiceModule subServiceModule = genServiceModuleFromServiceUIModule(
                    subTypeField.getType(), subServiceUIField.getType(),
                    serviceEntityManager, subServiceUIModule,
                    serviceUIModelExtension, serviceUIModelExtensionUnion);
            // Set sub UI model class back to service UI Model property
            subServiceUIField.setAccessible(true);
            subServiceUIField.set(serviceModule, subServiceModule);
        }
    }

    /**
     * [Internal method] In case the current list property element is service UI
     * model instance, then generating the SE Service module instance list and
     * set back to parent service model
     *
     * @param listServiceModelTypeFields : the current list field with element: service UI model
     * @param serviceModule
     * @param serviceUIModule
     * @param serviceUIModuleType
     * @param serviceModuleType
     * @param serviceEntityManager
     * @throws ServiceUIModuleProxyException
     * @throws ServiceModuleProxyException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ServiceEntityConfigureException
     */
    @SuppressWarnings("unchecked")
    private void setSubServiceUIModelListValueWrapper(Field listServiceUIField,
                                                      List<Field> listServiceModelTypeFields,
                                                      ServiceModule serviceModule, ServiceUIModule serviceUIModule,
                                                      Class<?> serviceUIModuleType, Class<?> serviceModuleType,
                                                      ServiceEntityManager serviceEntityManager,
                                                      ServiceUIModelExtension serviceUIModelExtension,
                                                      ServiceUIModelExtensionUnion serviceUIModelExtensionUnion)
            throws ServiceUIModuleProxyException, ServiceModuleProxyException,
            IllegalArgumentException, IllegalAccessException,
            ServiceEntityConfigureException {
        /*
         * [Step1] Get the list value from list field of the service UI module
         */
        listServiceUIField.setAccessible(true);
        List<?> subListValue = (List<?>) listServiceUIField
                .get(serviceUIModule);
        if (!ServiceCollectionsHelper.checkNullList(subListValue)) {
            /*
             * [Step2] In case the list value is not null, then get the field
             * configure and retrieve the nodename
             */
            IServiceUIModuleFieldConfig subListUIFieldConfigure = listServiceUIField
                    .getAnnotation(IServiceUIModuleFieldConfig.class);
            if (subListUIFieldConfigure == null) {
                // raise exception when no core module field found
                throw new ServiceUIModuleProxyException(
                        ServiceUIModuleProxyException.PARA_NOANNOTATION,
                        listServiceUIField.getName());
            }
            /*
             * [Step3] Retrieve the relative field and field configure from
             * service module
             */
            Field listServiceModelField = ServiceModuleProxy
                    .filterListFieldByNodeInstId(listServiceModelTypeFields,
                            subListUIFieldConfigure.nodeInstId());

            if (listServiceModelField == null) {
                // Try again with node name
                listServiceModelField = ServiceModuleProxy
                        .filterListFieldByNodeName(listServiceModelTypeFields,
                                subListUIFieldConfigure.nodeName());
                if (listServiceModelField == null) {
                    return;
                }
            }
            IServiceModuleFieldConfig subListFieldConfigure = listServiceModelField
                    .getAnnotation(IServiceModuleFieldConfig.class);
            if (subListFieldConfigure == null) {
                // raise exception when no core module field found
                throw new ServiceUIModuleProxyException(
                        ServiceUIModuleProxyException.PARA_NOANNOTATION,
                        listServiceModelField.getName());
            }
            /*
             * [Step4] Traverse each list data (service UI module element),and
             * gen Service module value from method
             * getServiceModuleFromServiceUIModule and build the
             * list<ServiceModule> properties and value, then set back
             */
            @SuppressWarnings("rawtypes")
            List subServiceModelList = new ArrayList();
            for (int i = 0; i < subListValue.size(); i++) {
                ServiceUIModule subServiceUIModule = (ServiceUIModule) subListValue
                        .get(i);
                Class<?> subServiceUIModuleClass = ServiceEntityFieldsHelper
                        .getListSubType(listServiceUIField);
                Class<?> subServiceModuleClass = ServiceEntityFieldsHelper
                        .getListSubType(listServiceModelField);
                ServiceModule subServiceModule = genServiceModuleFromServiceUIModule(
                        subServiceModuleClass, subServiceUIModuleClass,
                        serviceEntityManager, subServiceUIModule,
                        serviceUIModelExtension, serviceUIModelExtensionUnion);
                subServiceModelList.add(subServiceModule);
            }
            // Set sub ui model class back to service UI Model property
            listServiceModelField.setAccessible(true);
            listServiceModelField.set(serviceModule, subServiceModelList);
        }
    }

    /**
     * [Internal method] In case the current flat sub property element is
     * service entity instance, then generating the SE UI instance and set back
     * to service UI model
     *
     * @param serviceModule
     * @param serviceUIModule
     * @param serviceEntityManager
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ServiceModuleProxyException
     * @throws ServiceUIModuleProxyException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws ServiceEntityConfigureException
     */
    private void setSubEntityFlatValueWrapper(
            ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
            Field subField, List<Field> subUIModelTypeFields,
            ServiceModule serviceModule, ServiceUIModule serviceUIModule,
            ServiceEntityManager serviceEntityManager, LogonInfo logonInfo,
            List<ServiceModuleConvertPara> additionalConvertParaList)
            throws IllegalArgumentException, IllegalAccessException,
            ServiceModuleProxyException, ServiceUIModuleProxyException,
            InstantiationException, NoSuchMethodException, SecurityException,
            InvocationTargetException, ServiceEntityConfigureException {
        /*
         * [Step1] Get the list data value and get the list field configure
         */
        subField.setAccessible(true);
        ServiceEntityNode subSENodeValue = (ServiceEntityNode) subField
                .get(serviceModule);
        if (subSENodeValue == null) {
            return;
        }
        mergeToConvertParaList(additionalConvertParaList, subSENodeValue);
        IServiceModuleFieldConfig subFieldConfigure = subField
                .getAnnotation(IServiceModuleFieldConfig.class);
        if (subFieldConfigure == null) {
            // raise exception when no core module field found
            throw new ServiceModuleProxyException(
                    ServiceModuleProxyException.PARA_NOANNOTATION,
                    subField.getName());
        }
        /*
         * [Step2] Get relative list field & field configure from service UI
         * module by node name
         */
        Field subUIModelField = this.filterFieldByNodeInstId(
                subUIModelTypeFields, subFieldConfigure.nodeInstId());
        if (subUIModelField == null) {
            return;
        }
        IServiceUIModuleFieldConfig subUIFieldConfigure = subUIModelField
                .getAnnotation(IServiceUIModuleFieldConfig.class);
        if (subUIFieldConfigure == null) {
            // raise exception when no core module field found
            throw new ServiceUIModuleProxyException(
                    ServiceUIModuleProxyException.PARA_NOANNOTATION,
                    subUIModelField.getName());
        }
        Class<?> subUIModelClass = subUIModelField.getType();
        if (serviceUIModelExtensionUnion != null) {
            // In case using generate the UI model by extension union
            SEUIComModel seUIModelInstance = genUIModuleInExtensionUnion(
                    subUIModelClass, serviceUIModelExtensionUnion,
                    serviceEntityManager, subSENodeValue, logonInfo,
                    additionalConvertParaList);
            // Set sub UI model class back to service UI Model property
            subUIModelField.setAccessible(true);
            subUIModelField.set(serviceUIModule, seUIModelInstance);
        } else {
            // Invoke converting to UI method for each UI model directly
            // from UI field configure.
            SEUIComModel seUIModelInstance = (SEUIComModel) subUIModelClass
                    .newInstance();
            Class<?>[] subParameterTypes = {subSENodeValue.getClass(),
                    subUIModelClass};
            Method subConvertToUIMethod = getConvToMethodWrapper(
                    serviceEntityManager.getClass(),
                    subUIFieldConfigure.convToUIMethod(), subParameterTypes,
                    logonInfo);
            // subConvertToUIMethod.invoke(serviceEntityManager, seNode,
            // seUIModelInstance);
            invokeConvToMethodWrapper(serviceEntityManager,
                    subConvertToUIMethod, subSENodeValue, seUIModelInstance,
                    logonInfo);
            // Set sub UI model class back to service UI Model property
            subUIModelField.setAccessible(true);
            subUIModelField.set(serviceUIModule, seUIModelInstance);
        }

    }

    /**
     * [Internal method] In case the current list property element is service
     * entity instance, then generating the SE UI instance list and set back to
     * service UI model
     *
     * @param listField
     * @param listUIModelTypeFields
     * @param serviceModule
     * @param serviceUIModule
     * @param serviceEntityManager
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ServiceModuleProxyException
     * @throws ServiceUIModuleProxyException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws ServiceEntityConfigureException
     */
    @SuppressWarnings("unchecked")
    private void setSubEntityListValueWrapper(
            ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
            Field listField, List<Field> listUIModelTypeFields,
            ServiceModule serviceModule, ServiceUIModule serviceUIModule,
            ServiceEntityManager serviceEntityManager, LogonInfo logonInfo,
            List<ServiceModuleConvertPara> additionalConvertParaList)
            throws IllegalArgumentException, IllegalAccessException,
            ServiceModuleProxyException, ServiceUIModuleProxyException,
            InstantiationException, NoSuchMethodException, SecurityException,
            InvocationTargetException, ServiceEntityConfigureException {
        /*
         * [Step1] Get the list data value and get the list field configure
         */
        listField.setAccessible(true);
        List<ServiceEntityNode> seNodeList = (List<ServiceEntityNode>) listField
                .get(serviceModule);
        if (ServiceCollectionsHelper.checkNullList(seNodeList)) {
            return;
        }
        mergeToConvertParaList(additionalConvertParaList, seNodeList);
        IServiceModuleFieldConfig subListFieldConfigure = listField
                .getAnnotation(IServiceModuleFieldConfig.class);
        if (subListFieldConfigure == null) {
            // raise exception when no core module field found
            throw new ServiceModuleProxyException(
                    ServiceModuleProxyException.PARA_NOANNOTATION,
                    listField.getName());
        }
        /*
         * [Step2] Get relative list field & field configure from service UI
         * module by node name
         */
        Field listUIModelField = this.filterFieldByNodeName(
                listUIModelTypeFields, subListFieldConfigure.nodeName());
        if (listUIModelField == null) {
            return;
        }
        IServiceUIModuleFieldConfig subListUIFieldConfigure = listUIModelField
                .getAnnotation(IServiceUIModuleFieldConfig.class);
        if (subListUIFieldConfigure == null) {
            // raise exception when no core module field found
            throw new ServiceUIModuleProxyException(
                    ServiceUIModuleProxyException.PARA_NOANNOTATION,
                    listUIModelField.getName());
        }
        @SuppressWarnings("rawtypes")
        List subUIModelList = new ArrayList();
        for (ServiceEntityNode seNode : seNodeList) {
            /*
             * [Step3] Build new UI model instance and retrieve the convertToUI
             * method, then invoke the method in reflective way
             */
            Class<?> subUIModelClass = ServiceEntityFieldsHelper
                    .getListSubType(listUIModelField);

            if (serviceUIModelExtensionUnion != null) {
                // In case using generate the UI model by extension union
                SEUIComModel seUIModelInstance = genUIModuleInExtensionUnion(
                        subUIModelClass, serviceUIModelExtensionUnion,
                        serviceEntityManager, seNode, logonInfo,
                        additionalConvertParaList);
                subUIModelList.add(seUIModelInstance);
            } else {
                // Invoke converting to UI method for each UI model directly
                // from UI field configure.
                SEUIComModel seUIModelInstance = (SEUIComModel) subUIModelClass
                        .newInstance();
                Class<?>[] subParameterTypes = {seNode.getClass(),
                        subUIModelClass};
                Method subConvertToUIMethod = getConvToMethodWrapper(
                        serviceEntityManager.getClass(),
                        subListUIFieldConfigure.convToUIMethod(),
                        subParameterTypes, logonInfo);
                // subConvertToUIMethod.invoke(serviceEntityManager, seNode,
                // seUIModelInstance);
                invokeConvToMethodWrapper(serviceEntityManager,
                        subConvertToUIMethod, seNode, seUIModelInstance,
                        logonInfo);
                subUIModelList.add(seUIModelInstance);
            }

        }
        // Set sub ui model class back to service UI Model property
        listUIModelField.setAccessible(true);
        listUIModelField.set(serviceUIModule, subUIModelList);
    }

    /**
     * [Internal method] In case the current flat property element is SE UI
     * Model instance, then generating the se node instance and set back to
     * service model
     *
     * @param serviceModule        : curent service module instance
     * @param serviceUIModule      : current service ui module instance
     * @param serviceEntityManager
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ServiceModuleProxyException
     * @throws ServiceUIModuleProxyException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws ServiceEntityConfigureException
     * @throws NoSuchFieldException
     */
    private void setSubUIModelFlatValueWrapper(Field subUIModelField,
                                               List<Field> subTypeFields, ServiceModule serviceModule,
                                               ServiceUIModule serviceUIModule,
                                               ServiceEntityManager serviceEntityManager, String client,
                                               ServiceUIModelExtension serviceUIModelExtension,
                                               ServiceUIModelExtensionUnion serviceUIModelExtensionUnion)
            throws IllegalArgumentException, IllegalAccessException,
            ServiceModuleProxyException, ServiceUIModuleProxyException,
            InstantiationException, NoSuchMethodException, SecurityException,
            InvocationTargetException, ServiceEntityConfigureException,
            NoSuchFieldException {
        /*
         * [Step1] Get the list data value (element uiMoedl) and get the list
         * field configure
         */
        subUIModelField.setAccessible(true);
        SEUIComModel uiModelInstance = (SEUIComModel) subUIModelField
                .get(serviceUIModule);
        if (uiModelInstance == null) {
            return;
        }
        IServiceUIModuleFieldConfig subUIFieldConfigure = subUIModelField
                .getAnnotation(IServiceUIModuleFieldConfig.class);
        if (subUIFieldConfigure == null) {
            // raise exception when no core module field found
            throw new ServiceUIModuleProxyException(
                    ServiceUIModuleProxyException.PARA_NOANNOTATION,
                    subUIModelField.getName());
        }
        /*
         * [Step2] Get relative list field & field configure from service module
         * by node name
         */
        Field subTypeField = ServiceModuleProxy.filterListFieldByNodeInstId(
                subTypeFields, subUIFieldConfigure.nodeInstId());

        if (subTypeField == null) {
            // Try again with node name
            subTypeField = ServiceModuleProxy.filterListFieldByNodeName(
                    subTypeFields, subUIFieldConfigure.nodeName());
            if (subTypeField == null) {
                return;
            }
        }
        IServiceModuleFieldConfig subFieldConfigure = subTypeField
                .getAnnotation(IServiceModuleFieldConfig.class);
        if (subFieldConfigure == null) {
            // raise exception when no core module field found
            throw new ServiceModuleProxyException(
                    ServiceModuleProxyException.PARA_NOANNOTATION,
                    subTypeField.getName());
        }
        // Decide the execute manager
        ServiceEntityManager executeManager = serviceEntityManager;

        UIModelNodeMapConfigure uiModelNodeMapConfigure = ServiceModelExtensionHelper
                .filterNodeMapConfigureByNodeInstId(
                        serviceUIModelExtensionUnion.getUiModelNodeMapList(),
                        serviceUIModelExtensionUnion.getNodeInstId());
        if (uiModelNodeMapConfigure.getServiceEntityManager() != null) {
            executeManager = uiModelNodeMapConfigure.getServiceEntityManager();
        }
        /*
         * [Step] Try to get seNode directly by UUID from DB
         */
        ServiceEntityNode seNodeInstance = executeManager.getEntityNodeByKey(
                uiModelInstance.getUuid(),
                IServiceEntityNodeFieldConstant.UUID,
                uiModelNodeMapConfigure.getNodeName(), client, null);
        if (seNodeInstance == null) {
            Class<?> seNodeClass = serviceModuleProxy.getNodeTypeByConfigureProxy(executeManager,
                    uiModelNodeMapConfigure.getNodeName());
            seNodeInstance = (ServiceEntityNode) seNodeClass.newInstance();
        }
        reflectConvertUIToUnion(subUIFieldConfigure, null, seNodeInstance,
                uiModelInstance, serviceEntityManager,
                serviceUIModelExtensionUnion);
        // Set sub ui model class back to service Model property
        subTypeField.setAccessible(true);
        subTypeField.set(serviceModule, seNodeInstance);
    }

    /**
     * [Internal method] In case the current list property element is SE UI
     * Model instance, then generating the se node instance list and set back to
     * service UI model
     *
     * @param listUIModelField
     * @param listTypeFields       : all of the fields with list general type from service model
     * @param serviceModule        : curent service module instance
     * @param serviceUIModule      : current service ui module instance
     * @param serviceEntityManager
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ServiceModuleProxyException
     * @throws ServiceUIModuleProxyException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws ServiceEntityConfigureException
     * @throws NoSuchFieldException
     */
    private void setSubUIModelListValueWrapper(Field listUIModelField,
                                               List<Field> listTypeFields, ServiceModule serviceModule,
                                               ServiceUIModule serviceUIModule,
                                               ServiceEntityManager serviceEntityManager, String client,
                                               ServiceUIModelExtension serviceUIModelExtension,
                                               ServiceUIModelExtensionUnion serviceUIModelExtensionUnion)
            throws IllegalArgumentException, IllegalAccessException,
            ServiceModuleProxyException, ServiceUIModuleProxyException,
            InstantiationException, NoSuchMethodException, SecurityException,
            InvocationTargetException, ServiceEntityConfigureException,
            NoSuchFieldException {
        /*
         * [Step1] Get the list data value (element uiMoedl) and get the list
         * field configure
         */
        listUIModelField.setAccessible(true);
        @SuppressWarnings("rawtypes")
        List uiModelList = (List) listUIModelField.get(serviceUIModule);
        if (ServiceCollectionsHelper.checkNullList(uiModelList)) {
            return;
        }
        IServiceUIModuleFieldConfig subUIListFieldConfigure = listUIModelField
                .getAnnotation(IServiceUIModuleFieldConfig.class);
        if (subUIListFieldConfigure == null) {
            // raise exception when no core module field found
            throw new ServiceUIModuleProxyException(
                    ServiceUIModuleProxyException.PARA_NOANNOTATION,
                    listUIModelField.getName());
        }
        /*
         * [Step2] Get relative list field & field configure from service module
         * by node name
         */
        Field listTypeField = ServiceModuleProxy.filterListFieldByNodeInstId(
                listTypeFields, subUIListFieldConfigure.nodeInstId());

        if (listTypeField == null) {
            // Try again with node name
            listTypeField = ServiceModuleProxy.filterListFieldByNodeName(
                    listTypeFields, subUIListFieldConfigure.nodeName());
            if (listTypeField == null) {
                return;
            }
        }
        IServiceModuleFieldConfig subListFieldConfigure = listTypeField
                .getAnnotation(IServiceModuleFieldConfig.class);
        if (subListFieldConfigure == null) {
            // raise exception when no core module field found
            throw new ServiceModuleProxyException(
                    ServiceModuleProxyException.PARA_NOANNOTATION,
                    listTypeField.getName());
        }
        // Decide the execute manager
        List<ServiceEntityNode> subSeNodeModelList = new ArrayList<>();
        ServiceEntityManager executeManager = serviceEntityManager;

        UIModelNodeMapConfigure uiModelNodeMapConfigure = ServiceModelExtensionHelper
                .filterNodeMapConfigureByNodeInstId(
                        serviceUIModelExtensionUnion.getUiModelNodeMapList(),
                        serviceUIModelExtensionUnion.getNodeInstId());
        if (uiModelNodeMapConfigure.getServiceEntityManager() != null) {
            executeManager = uiModelNodeMapConfigure.getServiceEntityManager();
        }

        for (int i = 0; i < uiModelList.size(); i++) {
            /*
             * [Step3] Build new SE node instance and retrieve the convertUITo
             * method, then invoke the method in reflective way
             */
            SEUIComModel uiModelInstance = (SEUIComModel) uiModelList.get(i);
            ServiceEntityNode seNodeInstance = executeManager
                    .getEntityNodeByKey(uiModelInstance.getUuid(),
                            IServiceEntityNodeFieldConstant.UUID,
                            uiModelNodeMapConfigure.getNodeName(), client, null);
            if (seNodeInstance == null) {
                Class<?> seNodeClass = serviceModuleProxy.getNodeTypeByConfigureProxy(
                        executeManager, uiModelNodeMapConfigure.getNodeName());
                seNodeInstance = (ServiceEntityNode) seNodeClass.newInstance();
            }
            reflectConvertUIToUnion(subUIListFieldConfigure, null,
                    seNodeInstance, uiModelInstance, serviceEntityManager,
                    serviceUIModelExtensionUnion);
            subSeNodeModelList.add(seNodeInstance);
        }
        // Set sub ui model class back to service Model property
        listTypeField.setAccessible(true);
        listTypeField.set(serviceModule, subSeNodeModelList);
    }


    /**
     * [Internal method] Filter the field out of list fields by node name
     *
     * @param listFieldList
     * @param nodeName
     * @return
     */
    private Field filterFieldByNodeName(List<Field> listFieldList,
                                        String nodeName) {
        if (ServiceCollectionsHelper.checkNullList(listFieldList)) {
            return null;
        }
        for (Field field : listFieldList) {
            IServiceUIModuleFieldConfig subListUIFieldConfigure = field
                    .getAnnotation(IServiceUIModuleFieldConfig.class);
            if (subListUIFieldConfigure == null) {
                continue;
            }
            if (subListUIFieldConfigure.nodeName().equals(nodeName)) {
                return field;
            }
        }
        return null;
    }

    /**
     * [Internal method] Filter the field out of list fields by node name
     *
     * @param nodeInstId
     * @return
     */
    private Field filterFieldByNodeInstId(List<Field> subFieldList,
                                          String nodeInstId) {
        if (ServiceCollectionsHelper.checkNullList(subFieldList)) {
            return null;
        }
        for (Field field : subFieldList) {
            IServiceUIModuleFieldConfig subListUIFieldConfigure = field
                    .getAnnotation(IServiceUIModuleFieldConfig.class);
            if (subListUIFieldConfigure == null) {
                continue;
            }
            if (subListUIFieldConfigure.nodeInstId().equals(nodeInstId)) {
                return field;
            }
        }
        return null;
    }

    /**
     * Core Logic to return the core module field by given service module type
     *
     * @param serviceUIModuleType
     * @return
     */
    public static Field getCoreUIModuleField(Class<?> serviceUIModuleType) {
        List<Field> fieldList = ServiceEntityFieldsHelper
                .getFieldsList(serviceUIModuleType);
        if (ServiceCollectionsHelper.checkNullList(fieldList)) {
            return null;
        }
        for (Field field : fieldList) {
            if (ServiceEntityFieldsHelper.checkSuperClassExtends(
                    field.getType(), SEUIComModel.class.getSimpleName())) {
                return field;
            }
        }
        return null;
    }

    /**
     * Core Logic to return the core module field by given service module type
     *
     * @param serviceUIModuleType
     * @return
     */
    public static Field getUIModuleFieldByNodeInstId(
            Class<?> serviceUIModuleType, String nodeInstId) {
        List<Field> fieldList = ServiceEntityFieldsHelper
                .getFieldsList(serviceUIModuleType);
        if (ServiceCollectionsHelper.checkNullList(fieldList)) {
            return null;
        }
        for (Field field : fieldList) {
            IServiceUIModuleFieldConfig serviceUIModuleFieldConfig = field
                    .getAnnotation(IServiceUIModuleFieldConfig.class);
            if (serviceUIModuleFieldConfig != null) {
                if (nodeInstId.equals(serviceUIModuleFieldConfig.nodeInstId())) {
                    return field;
                }
            }
        }
        return null;
    }



    /**
     * Input Service UI Model List, return List of Simple UI Model, by checking field by nodeInstId
     *
     * @param serviceUIModelList
     * @param nodeInstId: nodeInstId, pay attention can only work on First Layer
     * @return
     */
    public static List simpleListServiceUIModelList(List serviceUIModelList, String nodeInstId) throws IllegalAccessException {
        if(ServiceCollectionsHelper.checkNullList(serviceUIModelList)){
            return null;
        }
        List simpleList = new ArrayList();
        for(Object obj: serviceUIModelList){
            ServiceUIModule serviceUIModule = (ServiceUIModule) obj;
            ServiceBasicFieldValueUnion serviceBasicFieldValueUnion = getCurLayerNodeFieldUnion(serviceUIModule,
                    nodeInstId);
            if(serviceBasicFieldValueUnion == null || serviceBasicFieldValueUnion.getValue() == null){
                continue;
            }
            _attachValueToList(serviceBasicFieldValueUnion.getValue(), simpleList);
        }
        return simpleList;
    }

    private static void _attachValueToList(Object objValue, List rawList ){
        if(objValue == null){
            return;
        }
        if(objValue.getClass().isAssignableFrom(java.util.List.class)){
            List valueList = (List) objValue;
            if(!ServiceCollectionsHelper.checkNullList(valueList)){
                rawList.addAll(valueList);
            }
        } else {
            rawList.add(objValue);
        }
    }

    /**
     * [Internal method] In case node is on current 1st layer of Service UI Model
     * @param serviceUIModule
     * @return
     */
    private static ServiceBasicFieldValueUnion getCurLayerNodeFieldUnion(ServiceUIModule serviceUIModule,
                                                                         String nodeInstId) throws IllegalAccessException {
        Field field = getUIModuleFieldByNodeInstId(serviceUIModule.getClass(), nodeInstId);
        if(field == null){
            return null; // means field not existed on current layer
        }
        field.setAccessible(true);
        if(field.getType().isAssignableFrom(java.util.List.class)){
            // In case list type
            if (ServiceEntityFieldsHelper.checkSuperClassExtends(
                    ServiceEntityFieldsHelper.getListSubType(field),
                    ServiceUIModule.class.getSimpleName())) {
                // In case List of Service UI Model
                List valueList = (List) field.get(serviceUIModule);
                if(ServiceCollectionsHelper.checkNullList(valueList)){
                    return null;
                }
                List listValue = simpleListServiceUIModelList(valueList, nodeInstId);
                return new ServiceBasicFieldValueUnion(field, listValue);
            } else {
                // In case list of simple type, just return simple list value
                field.setAccessible(true);
                return new ServiceBasicFieldValueUnion(field, field.get(serviceUIModule));
            }
        } else {
            // In case non-list type
            if (ServiceEntityFieldsHelper.checkSuperClassExtends(
                    field.getType(),
                    ServiceUIModule.class.getSimpleName())) {
                // This could not happen on current Service UI Model structure
                return null;
            } else {
                // Simple non-list type, just return
                field.setAccessible(true);
                return new ServiceBasicFieldValueUnion(field, field.get(serviceUIModule));
            }
        }
    }

    /**
     * Get Sub Node field value union by parent service UI Model and node inst Id,
     * If node is list type, then return list value
     * @param serviceUIModule
     * @param nodeInstId
     * @return
     * @throws IllegalAccessException
     */
    public static ServiceBasicFieldValueUnion getUIModelNodeFieldValue(ServiceUIModule serviceUIModule,
     String nodeInstId) throws IllegalAccessException {
        Field field = getUIModuleFieldByNodeInstId(serviceUIModule.getClass(), nodeInstId);
        if(field != null){
            // in case can be found on first layer, return directly
            field.setAccessible(true);
            return getCurLayerNodeFieldUnion(serviceUIModule, nodeInstId);
        } else {
            // in case have to navigate into deeper layer
            List<Field> listFieldList = ServiceUIModuleProxy.getListTypeFields(serviceUIModule.getClass());
            if(ServiceCollectionsHelper.checkNullList(listFieldList)){
                return null;
            }
            // traverse each list field, which should have embedded service UI Model type
            for(Field subListField: listFieldList){
                if (ServiceEntityFieldsHelper.checkSuperClassExtends(
                        ServiceEntityFieldsHelper.getListSubType(subListField),
                        ServiceUIModule.class.getSimpleName())) {
                    List subServiceUIModelList = (List) subListField.get(serviceUIModule);
                    if(ServiceCollectionsHelper.checkNullList(subServiceUIModelList)){
                        continue;
                    }
                    Class<?> subServiceUIModelClass = ServiceEntityFieldsHelper.getListSubType(subListField);
                    // Check if sub field could be found by nodeInst Id
                    List<ServiceModelNodeMeta> subServiceFieldMetaList =
                            getServiceModelNodeFieldsMetaList(subServiceUIModelClass);
                    if(ServiceCollectionsHelper.checkNullList(subServiceFieldMetaList)){
                        continue;
                    }
                    List<ServiceModelNodeMeta> resultByNodeInstId =
                            subServiceFieldMetaList.stream().filter(serviceModelNodeMeta -> {
                         return nodeInstId.equals(serviceModelNodeMeta.getNodeInstId());
                     }).collect(Collectors.toList());
                    if(ServiceCollectionsHelper.checkNullList(resultByNodeInstId)){
                        continue;
                    }
                    // Traverse and navigate to UI Model List
                    List simpleList = new ArrayList();
                    for(Object object: subServiceUIModelList){
                        ServiceUIModule subServiceUIModule = (ServiceUIModule) object;
                        ServiceBasicFieldValueUnion subFieldValueUnion = getUIModelNodeFieldValue(subServiceUIModule,
                                nodeInstId);
                        _attachValueToList(subFieldValueUnion.getValue(), simpleList);
                    }
                    return new ServiceBasicFieldValueUnion(subListField, simpleList);
                }
            }
            return null;
        }
    }


    public static class ServiceModelNodeMeta{

        private String nodeInstId;

        private String fieldName;

        private Class<?> fieldType;

        private String typeStr;

        private boolean listAssign;

        public ServiceModelNodeMeta() {
        }

        public ServiceModelNodeMeta(String nodeInstId, String fieldName, Class<?> fieldType, String typeStr,
                                    boolean listAssign) {
            this.nodeInstId = nodeInstId;
            this.fieldName = fieldName;
            this.fieldType = fieldType;
            this.typeStr = typeStr;
            this.listAssign = listAssign;
        }

        public String getNodeInstId() {
            return nodeInstId;
        }

        public void setNodeInstId(String nodeInstId) {
            this.nodeInstId = nodeInstId;
        }

        public String getTypeStr() {
            return typeStr;
        }

        public void setTypeStr(String typeStr) {
            this.typeStr = typeStr;
        }

        public boolean isListAssign() {
            return listAssign;
        }

        public void setListAssign(boolean listAssign) {
            this.listAssign = listAssign;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public Class<?> getFieldType() {
            return fieldType;
        }

        public void setFieldType(Class<?> fieldType) {
            this.fieldType = fieldType;
        }
    }

    /**
     * Core Logic to get the List type field array by given service module type
     *
     * @param
     * @return
     */
    public static List<ServiceModelNodeMeta> getServiceModelNodeFieldsMetaList(Class<?> serviceUIModuleType) {
        List<ServiceModelNodeMeta> resultList = new ArrayList<>();
        List<Field> fieldList = ServiceEntityFieldsHelper
                .getFieldsList(serviceUIModuleType);
        if (ServiceCollectionsHelper.checkNullList(fieldList)) {
            return null;
        }
        for (Field field : fieldList) {
            IServiceUIModuleFieldConfig serviceUIModuleFieldConfig = field
                    .getAnnotation(IServiceUIModuleFieldConfig.class);
            if (serviceUIModuleFieldConfig == null) {
               continue;
            }
            String nodeInstId = serviceUIModuleFieldConfig.nodeInstId();
            boolean listAssign = false;
            if (field.getType().isAssignableFrom(List.class)) {
                listAssign = true;
                // In case sub type field is also service module type
                if (ServiceEntityFieldsHelper.checkSuperClassExtends(
                        ServiceEntityFieldsHelper.getListSubType(field),
                        ServiceUIModule.class.getSimpleName())) {
                    List<ServiceModelNodeMeta> subResultList =
                            getServiceModelNodeFieldsMetaList(ServiceEntityFieldsHelper.getListSubType(field));
                    if(!ServiceCollectionsHelper.checkNullList(subResultList)){
                        resultList.addAll(subResultList);
                    }
                } else {
                    ServiceModelNodeMeta serviceModelNodeMeta = new ServiceModelNodeMeta(nodeInstId, field.getName(),
                            ServiceEntityFieldsHelper.getListSubType(field), ServiceEntityFieldsHelper.getListSubType(field).getSimpleName(), true);
                    resultList.add(serviceModelNodeMeta);
                }
            } else {
                ServiceModelNodeMeta serviceModelNodeMeta = new ServiceModelNodeMeta(nodeInstId, field.getName(),
                        field.getType(), field.getType().getSimpleName(), false);
                resultList.add(serviceModelNodeMeta);
            }
        }
        return resultList;
    }

    public static List<ServiceFieldMeta> getNodeFieldList(ServiceModelNodeMeta serviceModelNodeMeta){
        List<Field> rawFieldList = ServiceEntityFieldsHelper.getFieldsList(serviceModelNodeMeta.getFieldType());
        if(ServiceCollectionsHelper.checkNullList(rawFieldList)){
            return null;
        }
        return rawFieldList.stream().map(field -> {
            return StandardFieldTypeProxy.buildServiceFieldMeta(field);
        }).collect(Collectors.toList());
    }


    /**
     * Core Logic to get the List type field array by given service module type
     *
     * @param serviceModuleType
     * @return
     */
    public static List<Field> getListTypeFields(Class<?> serviceModuleType) {
        List<Field> resultList = new ArrayList<>();
        List<Field> fieldList = ServiceEntityFieldsHelper
                .getFieldsList(serviceModuleType);
        if (ServiceCollectionsHelper.checkNullList(fieldList)) {
            return null;
        }
        for (Field field : fieldList) {
            if (field.getType().isAssignableFrom(List.class)) {
                resultList.add(field);
            }
        }
        return resultList;
    }

    /**
     * Core Logic to get the List type field array by given service module type
     *
     * @param serviceModuleType
     * @return
     */
    public static List<Field> getNonListSubFields(Class<?> serviceModuleType,
                                                  String nodeInstId) {
        List<Field> resultList = new ArrayList<Field>();
        List<Field> fieldList = ServiceEntityFieldsHelper
                .getFieldsList(serviceModuleType);
        if (ServiceCollectionsHelper.checkNullList(fieldList)) {
            return null;
        }
        for (Field field : fieldList) {
            if (field.getType().isAssignableFrom(List.class)) {
                continue;
            }
            IServiceUIModuleFieldConfig serviceUIModuleFieldConfig = field
                    .getAnnotation(IServiceUIModuleFieldConfig.class);
            if (serviceUIModuleFieldConfig != null) {
                if (nodeInstId.equals(serviceUIModuleFieldConfig.nodeInstId())) {
                    continue;
                }
                resultList.add(field);
            }
        }
        return resultList;
    }

    /**
     * Scan if current Service UI Model has extend field list
     *
     * @param serviceUIModuleType
     * @return
     */
    public static Field getExtendListTypeField(Class<?> serviceUIModuleType) {
        List<Field> listUIModelTypeFields = getListTypeFields(serviceUIModuleType);
        if (!ServiceCollectionsHelper.checkNullList(listUIModelTypeFields)) {
            for (Field listField : listUIModelTypeFields) {
                if (ServiceEntityFieldsHelper.checkSuperClassExtends(
                        ServiceEntityFieldsHelper.getListSubType(listField),
                        ServiceExtendUIModel.class.getSimpleName())) {
                    return listField;
                }
            }
        }
        return null;
    }

}
