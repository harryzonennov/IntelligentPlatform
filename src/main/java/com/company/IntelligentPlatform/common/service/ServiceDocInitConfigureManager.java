package com.company.IntelligentPlatform.common.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.dto.*;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocActionExecutionProxyFactory;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchContextBuilder;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.ServiceDocInitConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.lang.reflect.Field;
import java.util.*;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class ServiceDocInitConfigureManager {

    public static final String METHOD_ConvServiceDocInitConfigureToUI = "convServiceDocInitConfigureToUI";

    public static final String METHOD_ConvUIToServiceDocInitConfigure = "convUIToServiceDocInitConfigure";

    public static final String METHOD_ConvHomeDocumentToConfigureUI = "convHomeDocumentToConfigureUI";
    
    @Autowired
    protected ServiceDocumentSettingManager serviceDocumentSettingManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected StandardSwitchProxy standardSwitchProxy;

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

    @Autowired
    protected StandardSystemVariableProxy standardSystemVariableProxy;

    @Autowired
    protected ServiceDocInitConfigureSearchProxy serviceDocInitConfigureSearchProxy;

    @Autowired
    protected ServiceDocInitConfigureServiceUIModelExtension serviceDocInitConfigureServiceUIModelExtension;

    protected Logger logger = LoggerFactory.getLogger(ServiceDocInitConfigureManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ServiceDocumentSetting.NODENAME,
                        request.getUuid(), ServiceDocInitConfigure.NODENAME, serviceDocumentSettingManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<ServiceDocumentSetting>) serviceDocumentSetting -> {
                    // How to get the base page header model list
                    List<PageHeaderModel> pageHeaderModelList =
                            docPageHeaderModelProxy.getDocPageHeaderModelList(serviceDocumentSetting,
                                    IServiceEntityNodeFieldConstant.NAME);
                    return pageHeaderModelList;
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<ServiceDocInitConfigure>) (serviceDocInitConfigure, pageHeaderModel) -> {
                    // How to render current page header
                    pageHeaderModel.setHeaderName(serviceDocInitConfigure.getUuid());
                    return pageHeaderModel;
                });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, serialLogonInfo.getClient());
    }

    /**
     * API to get the service entity initial process configuration.
     * @param serviceEntityName
     * @param nodeName
     * @return
     */
    public ServiceDocInitConfigureServiceModel getServiceDocInitConfigure(String serviceEntityName, String nodeName,
                                                            String initConfigureId, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceEntityInstallationException,
            SearchConfigureException, LogonInfoException, AuthorizationException {
        String defNodeInstId = ServiceEntityStringHelper.getDefModelId(serviceEntityName, nodeName);
        ServiceDocInitConfigureSearchModel serviceDocInitConfigureSearchModel = new ServiceDocInitConfigureSearchModel();
        serviceDocInitConfigureSearchModel.setRefServiceEntityName(serviceEntityName);
        serviceDocInitConfigureSearchModel.setRefNodeName(nodeName);
        serviceDocInitConfigureSearchModel.setInitConfigureId(initConfigureId);
        SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonInfo).searchModel(serviceDocInitConfigureSearchModel);
        List<ServiceEntityNode> serviceDocInitConfigureList =
                serviceDocInitConfigureSearchProxy.searchDocList(searchContextBuilder.build()).getResultList();
        if (ServiceCollectionsHelper.checkNullList(serviceDocInitConfigureList)) {
            return null;
        }
        ServiceDocInitConfigure serviceDocInitConfigure = null;
        if (ServiceEntityStringHelper.checkNullString(initConfigureId) ||
                initConfigureId.equals(defNodeInstId)) {
            // In case using the default init configure template
            serviceDocInitConfigure =
                    (ServiceDocInitConfigure) ServiceCollectionsHelper.filterOnline(serviceDocInitConfigureList, serviceEntityNode->
                            ServiceEntityStringHelper.checkNullString(serviceEntityNode.getId()) || defNodeInstId.equals(serviceEntityNode.getId()));
        } else {
            serviceDocInitConfigure = (ServiceDocInitConfigure) serviceDocInitConfigureList.get(0);
        }
        if (serviceDocInitConfigure == null) {
            return null;
        }
        return (ServiceDocInitConfigureServiceModel) serviceDocumentSettingManager.loadServiceModule(ServiceDocInitConfigureServiceModel.class, serviceDocInitConfigure,
                serviceDocInitConfigureServiceUIModelExtension);
    }

    public static class ServiceDocInitConfigureFieldMap {

        protected String fieldName;

        protected String inputFieldName;

        public ServiceDocInitConfigureFieldMap() {

        }

        public ServiceDocInitConfigureFieldMap(String fieldName, String inputFieldName) {
            this.fieldName = fieldName;
            this.inputFieldName = inputFieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getInputFieldName() {
            return inputFieldName;
        }

        public void setInputFieldName(String inputFieldName) {
            this.inputFieldName = inputFieldName;
        }
    }

    public static ServiceDocInitConfigureMeta createMetaWithDiffFieldList(String fieldName, Object inputFieldValue,
                                                               List<ServiceDocInitConfigureFieldMap> diffFieldMapList) {
        ServiceDocInitConfigureMeta serviceDocInitConfigureMeta = new ServiceDocInitConfigureMeta();
        serviceDocInitConfigureMeta.setFieldName(fieldName);
        serviceDocInitConfigureMeta.setInputFieldValue(inputFieldValue);
        serviceDocInitConfigureMeta.setDiffFieldMapList(diffFieldMapList);
        return serviceDocInitConfigureMeta;
    }

    /**
     * JSON Class for parsing from ServiceDocInitConfigure->configMeta
     */
    public static class ServiceDocInitConfigureMeta {

        public static final int VALUE_CAT_SIM_FIELD = 1;

        public static final int VALUE_CAT_COM_OBJECT = 2;

        protected String fieldName;

        protected String inputFieldName;

        protected Object inputFieldValue;

        protected int valueCategory;

        protected List<ServiceDocInitConfigureFieldMap> diffFieldMapList;

        public ServiceDocInitConfigureMeta() {
        }

        public ServiceDocInitConfigureMeta(String fieldName, String inputFieldName, Object inputFieldValue) {
            this.fieldName = fieldName;
            this.inputFieldName = inputFieldName;
            this.inputFieldValue = inputFieldValue;
        }


        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getInputFieldName() {
            return inputFieldName;
        }

        public void setInputFieldName(String inputFieldName) {
            this.inputFieldName = inputFieldName;
        }

        public Object getInputFieldValue() {
            return inputFieldValue;
        }

        public void setInputFieldValue(Object inputFieldValue) {
            this.inputFieldValue = inputFieldValue;
        }

        public int getValueCategory() {
            return valueCategory;
        }

        public void setValueCategory(int valueCategory) {
            this.valueCategory = valueCategory;
        }

        public List<ServiceDocInitConfigureFieldMap> getDiffFieldMapList() {
            return diffFieldMapList;
        }

        public void setDiffFieldMapList(List<ServiceDocInitConfigureFieldMap> diffFieldMapList) {
            this.diffFieldMapList = diffFieldMapList;
        }
    }

    public static class ServiceDocInitConfigureMetaParseResult {

        protected List<ServiceDocInitConfigureMeta> coreNodeMetaList;

        protected Map<String, List<ServiceDocInitConfigureMeta>> subNodeMetaMap;

        public ServiceDocInitConfigureMetaParseResult() {
        }

        public ServiceDocInitConfigureMetaParseResult(List<ServiceDocInitConfigureMeta> coreNodeMetaList,
                                               Map<String, List<ServiceDocInitConfigureMeta>> subNodeMetaMap) {
            this.coreNodeMetaList = coreNodeMetaList;
            this.subNodeMetaMap = subNodeMetaMap;
        }

        public List<ServiceDocInitConfigureMeta> getCoreNodeMetaList() {
            return coreNodeMetaList;
        }

        public void setCoreNodeMetaList(List<ServiceDocInitConfigureMeta> coreNodeMetaList) {
            this.coreNodeMetaList = coreNodeMetaList;
        }

        public Map<String, List<ServiceDocInitConfigureMeta>> getSubNodeMetaMap() {
            return subNodeMetaMap;
        }

        public void setSubNodeMetaMap(Map<String, List<ServiceDocInitConfigureMeta>> subNodeMetaList) {
            this.subNodeMetaMap = subNodeMetaList;
        }
    }

    private ServiceDocInitConfigureMetaParseResult parseConfigMeta(String configMeta) {
        JSONArray jsonArray = JSONArray.fromObject(configMeta);
        List<ServiceDocInitConfigureMeta> serviceDocInitConfigureMetaList = new ArrayList<>();
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            // Create a MyClass object and set its fields from the JSONObject
            ServiceDocInitConfigureMeta serviceDocInitConfigureMeta = new ServiceDocInitConfigureMeta();
            String fieldName = jsonObject.getString("fieldName");
            if (ServiceEntityStringHelper.checkNullString(fieldName)) {
                continue;
            }
            serviceDocInitConfigureMeta.setFieldName(jsonObject.getString("fieldName"));
            serviceDocInitConfigureMeta.setInputFieldName(jsonObject.getString("inputFieldName"));
            serviceDocInitConfigureMeta.setInputFieldValue(jsonObject.getString("inputFieldValue"));
            serviceDocInitConfigureMetaList.add(serviceDocInitConfigureMeta);
        }
        return parseConfigMeta(serviceDocInitConfigureMetaList);
    }

    /**
     * Parses the configuration meta list into the final result in the format of ServiceDocInitConfigureMetaParseResult.
     *
     * @param serviceDocInitConfigureMetaList the list of ServiceDocInitConfigureMeta to be parsed
     * @return the final result in the format of ServiceDocInitConfigureMetaParseResult
     */
    private ServiceDocInitConfigureMetaParseResult parseConfigMeta(List<ServiceDocInitConfigureMeta> serviceDocInitConfigureMetaList) {
        Map<String, List<ServiceDocInitConfigureMeta>> subNodeMetaMap = new HashMap<>();
        List<ServiceDocInitConfigureMeta> coreNodeMetaList = new ArrayList<>();
        if (ServiceCollectionsHelper.checkNullList(serviceDocInitConfigureMetaList)) {
            return null;
        }
        for (ServiceDocInitConfigureMeta serviceDocInitConfigureMeta : serviceDocInitConfigureMetaList) {
            String fieldName = serviceDocInitConfigureMeta.getFieldName();
            String[] fieldNameArray = fieldName.split("\\.");
            if (fieldNameArray.length == 1) {
                coreNodeMetaList.add(serviceDocInitConfigureMeta);
            }
            if (fieldNameArray.length > 1) {
                // sub node name is the first part of the field name
                String subNodeName = fieldNameArray[0];
                if (subNodeMetaMap.containsKey(subNodeName)) {
                    List<ServiceDocInitConfigureMeta> subList = subNodeMetaMap.get(subNodeName);
                    subList = (subList == null) ? new ArrayList<>(): subList;
                    subList.add(serviceDocInitConfigureMeta) ;
                } else {
                    subNodeMetaMap.put(subNodeName, ServiceCollectionsHelper.asList(serviceDocInitConfigureMeta));
                }
            }
        }
        return new ServiceDocInitConfigureMetaParseResult(coreNodeMetaList, subNodeMetaMap);
    }

    /**
     * Get Document node initialization configuration, with the logic to compare default (defined in each Document specifier) and
     * custom configuration (defined in DB:ServiceDocInitConfigure), it then parse to the final result in the format of ServiceDocInitConfigureMetaParseResult.
     *
     * @param serviceEntityName: Service Entity Name
     * @param nodeName: Node name
     * @param initConfigureId: the ID of the initialization configuration, if a specific configuration model ID is specified
     * @param documentContentSpecifier: the Document Specifier instance for this document
     * @param logonInfo: the logon information
     * @return ServiceDocInitConfigureMetaParseResult representing the final result
     */
    public ServiceDocInitConfigureMetaParseResult getServiceDocInitConfigureMetaResult(String serviceEntityName, String nodeName,
                                                                         String initConfigureId,
                                                                         DocumentContentSpecifier<?,?,?> documentContentSpecifier, LogonInfo logonInfo)
            throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceEntityInstallationException,
            SearchConfigureException, LogonInfoException, AuthorizationException {
        // Try to get custom configured init configure model from DB
        ServiceDocInitConfigureServiceModel serviceDocInitConfigureServiceModel = getServiceDocInitConfigure(serviceEntityName, nodeName,
                initConfigureId,
                logonInfo);
        if (serviceDocInitConfigureServiceModel == null) {
            // In case no custom configured init configure, then trying to get default init configure from each Document specifier
            if (documentContentSpecifier != null) {
                if (!ServiceEntityStringHelper.checkNullString(initConfigureId)) {
                    return parseConfigMeta(documentContentSpecifier.getDefInitConfigureList(initConfigureId));
                } else {
                    return parseConfigMeta(documentContentSpecifier.getDefInitConfigureList(
                            ServiceEntityStringHelper.getDefModelId(serviceEntityName, nodeName)));
                }
            }
            return null;
        } else {
            return parseConfigMeta(serviceDocInitConfigureServiceModel.getServiceDocInitConfigure().getConfigMeta());
        }
    }


    /**
     * Initializes a Core Service Entity instance using a list of metadata configurations <code>ServiceDocInitConfigureMeta</code>.
     * During this process, each field in the Service Entity is configured based on the metadata <code>ServiceDocInitConfigureMeta</code> provided.
     *
     * The metadata may define either system variables or constant values:
     * - System variables are dynamically resolved at runtime using the environment and the logon information.
     * - Constant values are directly applied to the respective fields in the Service Entity.
     * - If a field does not have a preconfigured value, its initial value can be fetched from the {@code inputRequest}
     *  by the corresponding field name specified in the metadata.
     *
     *
     * @param coreNodeMetaList A list of {@link ServiceDocInitConfigureMeta} containing metadata configurations
     *                         for initializing the Service Entity fields.
     * @param serviceEntityNode The instance of Core Service Entity to be initialized. If {@code null}, the method exits early.
     * @param inputRequest The input request object from the UI client, which may contain initial field values.
     * @param logonInfo The system login information, used for resolving system variables during initialization.
     *
     * @throws ServiceEntityConfigureException If there is an issue configuring the Service Entity due to meta information inconsistencies.
     * @throws NoSuchFieldException If the specified field in the metadata is not found in the Service Entity class.
     * @throws IllegalAccessException If access to a field is denied due to Java access control restrictions.
     */
    public void initServiceEntityWithMeta(List<ServiceDocInitConfigureMeta> coreNodeMetaList,
                                          ServiceEntityNode serviceEntityNode, Object inputRequest, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, NoSuchFieldException,
            IllegalAccessException {
        if (serviceEntityNode == null) {
            return;
        }
        for(ServiceDocInitConfigureMeta serviceDocInitConfigureMeta: coreNodeMetaList) {
            String fieldName = getCoreFieldName(serviceDocInitConfigureMeta.getFieldName());
            String inputFieldName = serviceDocInitConfigureMeta.getInputFieldName();
            Object inputFieldValue = serviceDocInitConfigureMeta.getInputFieldValue();

            // Check if the input field value is provided in the metadata configuration.
            if (inputFieldValue != null) {
                // In case the input field value is provided in the metadata configuration, in most cases should be a system variable, otherwise a constant value.
                try {
                    int valueCategory = standardSystemVariableProxy.getValueCategory(inputFieldValue.toString());
                    if (valueCategory == StandardSystemVariableProxy.ISystemVariableConverter.VALUE_CAT_SIM_FIELD) {
                        Field backendField = ServiceEntityFieldsHelper.getServiceField(serviceEntityNode, fieldName);
                        backendField.setAccessible(true);
                        Object runtimeValue = standardSystemVariableProxy.getSystemVariableValue(inputFieldValue.toString(),
                                logonInfo);
                        if (runtimeValue != null) {
                            backendField.set(serviceEntityNode, runtimeValue);
                        } else {
                            // Value is not a system variable; treat it as a constant and apply directly.
                            backendField.set(serviceEntityNode, inputFieldValue);
                        }
                    }
                    if (valueCategory == StandardSystemVariableProxy.ISystemVariableConverter.VALUE_CAT_COM_OBJECT) {
                        mapValueForComObject(serviceEntityNode, serviceDocInitConfigureMeta, logonInfo);
                    }
                    if (valueCategory == 0) {
                        // In case this is not system variable, just treat the input value as constants
                        Field backendField = ServiceEntityFieldsHelper.getServiceField(serviceEntityNode, fieldName);
                        backendField.setAccessible(true);
                        backendField.set(serviceEntityNode, inputFieldValue);
                    }
                } catch (DocActionException e) {
                    throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG,
                            e.getErrorMessage());
                }
            } else {
                // If the input field value is null, fetch the value from the inputRequest using the field name.
                if (!ServiceEntityStringHelper.checkNullString(inputFieldName)) {
                    inputFieldValue = Objects.requireNonNull(
                            ServiceEntityFieldsHelper.getObjectFieldValue(inputRequest, inputFieldName)).toString();
                    Field backendField = ServiceEntityFieldsHelper.getServiceField(serviceEntityNode, fieldName);
                    backendField.setAccessible(true);
                    backendField.set(serviceEntityNode, inputFieldValue);
                }
            }
        }
    }

    protected void mapValueForComObject(ServiceEntityNode serviceEntityNode, ServiceDocInitConfigureMeta serviceDocInitConfigureMeta, LogonInfo logonInfo)
            throws DocActionException {
        Object inputFieldValue = serviceDocInitConfigureMeta.getInputFieldValue();
        StandardSystemVariableProxy.ISystemVariableConverter<?> systemVariableConverter =
                standardSystemVariableProxy.getSystemVariableConverter(inputFieldValue.toString());
        Object runtimeValue = standardSystemVariableProxy.getSystemVariableValue(inputFieldValue.toString(),
                logonInfo);
        List<String> inputFieldNameList = systemVariableConverter.getFieldNameList();
        List<ServiceDocInitConfigureFieldMap> diffFieldMapList = serviceDocInitConfigureMeta.getDiffFieldMapList();
        if (!ServiceCollectionsHelper.checkNullList(inputFieldNameList)) {
            // In case specify the field name list
            for (String inputFieldName: inputFieldNameList) {
                try {
                    Field inputField = ServiceEntityFieldsHelper.getServiceField(runtimeValue.getClass(), inputFieldName);
                    inputField.setAccessible(true);
                    ServiceDocInitConfigureFieldMap serviceDocInitConfigureFieldMap =
                            ServiceCollectionsHelper.filterOnline(diffFieldMapList,
                                    configureFieldMap-> configureFieldMap.getInputFieldName().equals(inputFieldName));
                    // by default: input field name value is equal to field name
                    String fieldName = inputFieldName;
                    if(serviceDocInitConfigureFieldMap != null) {
                        // In case specify the field name to input field mapping
                        fieldName = serviceDocInitConfigureFieldMap.getFieldName();
                    }
                    Field backendField = ServiceEntityFieldsHelper.getServiceField(serviceEntityNode, fieldName);
                    backendField.setAccessible(true);
                    Object tmpInputFieldValue = inputField.get(runtimeValue);
                    backendField.set(serviceEntityNode, tmpInputFieldValue);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    throw new DocActionException(DocActionException.PARA_MISS_CONFIG, e.getMessage());
                }
            }
        }
    }


    /**
     * Initializes the Service Module instance with metadata in Map format. This provides configuration for
     * initializing each module and each field within those modules in the service module. It is used in scenarios
     * where the service module needs initialization.
     *
     * @param serviceModule               the raw input Service Module instance
     * @param subNodeInitConfigureMetaMap the initialization configuration in Map format
     * @param coreNodeInstId              the core node instance ID
     * @param serviceEntityManager        the Service Entity Manager instance
     * @param serviceUIModelExtension     the Service UI Model extension instance
     * @param inputRequest                the raw input request from the client
     * @param logonInfo                   the logon info instance
     * @throws ServiceModuleProxyException if an error occurs during initialization
     */
    public void initServiceModuleWithMeta(ServiceModule serviceModule, Map<String,
            List<ServiceDocInitConfigureMeta>> subNodeInitConfigureMetaMap,
                                          String coreNodeInstId, ServiceEntityManager serviceEntityManager,
                                          ServiceUIModelExtension serviceUIModelExtension,
                                          Object inputRequest, LogonInfo logonInfo)
            throws ServiceModuleProxyException {
        try {
            ServiceEntityNode hostEntityNode = ServiceModuleProxy.getCoreServiceEntityNode(
                    serviceModule, coreNodeInstId);
            for(String nodeInstId: subNodeInitConfigureMetaMap.keySet()) {
                Field subField = ServiceModuleProxy.getModuleFieldByNodeInstId(serviceModule.getClass(), nodeInstId);
                if (subField == null) {
                    // record error and continue
                    continue;
                }
                ServiceModelExtensionHelper.ExtensionUnionResponse extensionUnionResponse =  ServiceModelExtensionHelper
                        .getUIModelExtensionByNodeInstId(
                                nodeInstId,
                                serviceUIModelExtension);
                if (extensionUnionResponse == null) {
                    // record error and continue
                    continue;
                }
                ServiceUIModelExtensionUnion serviceUIModelExtensionUnion =
                        extensionUnionResponse.getServiceUIModelExtensionUnion();
                ServiceEntityNode subNode = serviceEntityManager.newEntityNode(hostEntityNode,
                        serviceUIModelExtensionUnion.getNodeName());
                initServiceEntityWithMeta(subNodeInitConfigureMetaMap.get(nodeInstId), subNode, inputRequest, logonInfo);
                subField.setAccessible(true);
                subField.set(serviceModule, subNode);
//                if (serviceUIModelExtensionUnion.getToParentModelNodeMapConfigure() != null) {
//                    // In case the relationship to parent is configured
////                    return this.getSubNodeListByNodeConfigure(parentEntityNode,
////                            serviceEntityManager,
////                            subExtensionUnion.getToParentModelNodeMapConfigure());
//                } else {
//                    // Default mode: sub node list as parent-to-child relationship
//                    ServiceEntityNode subNode = serviceEntityManager.newEntityNode(hostEntityNode,
//                            serviceUIModelExtensionUnion.getNodeName());
//                    initServiceEntityWithMeta(subNodeInitConfigureMetaMap.get(nodeInstId), subNode, inputRequest, serialLogonInfo);
//                    subField.setAccessible(true);
//                    subField.set(serviceModule, subNode);
//                }
            }
        } catch (IllegalAccessException | ServiceEntityConfigureException | NoSuchFieldException e) {
            throw new ServiceModuleProxyException(ServiceModuleProxyException.PARA_SYSTEM_WRONG, e.getMessage());
        }
    }

    private String getCoreFieldName(String fieldName) {
        if (ServiceEntityStringHelper.checkNullString(fieldName)) {
            return null;
        }
        String[] fieldNameArray = fieldName.split("\\.");
        return fieldNameArray[fieldNameArray.length - 1];
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convServiceDocInitConfigureToUI(
            ServiceDocInitConfigure serviceDocInitConfigure,
            ServiceDocInitConfigureUIModel serviceDocInitConfigureUIModel,
            LogonInfo logonInfo)  {
        if (serviceDocInitConfigure != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(serviceDocInitConfigure, serviceDocInitConfigureUIModel);
            serviceDocInitConfigureUIModel.setConfigMeta(serviceDocInitConfigure.getConfigMeta());
            serviceDocInitConfigureUIModel.setRefNodeName(serviceDocInitConfigure.getRefNodeName());
            serviceDocInitConfigureUIModel.setRefServiceEntityName(serviceDocInitConfigure.getRefServiceEntityName());
            serviceDocInitConfigureUIModel.setNodeInstId(serviceDocInitConfigure.getNodeInstId());
        }
    }


    public void convUIToServiceDocInitConfigure(ServiceDocInitConfigureUIModel serviceDocInitConfigureUIModel, ServiceDocInitConfigure rawEntity) {
        if(serviceDocInitConfigureUIModel != null && rawEntity != null) {
            DocFlowProxy.convUIToServiceEntityNode(serviceDocInitConfigureUIModel, rawEntity);
            rawEntity.setConfigMeta(serviceDocInitConfigureUIModel.getConfigMeta());
            rawEntity.setRefNodeName(serviceDocInitConfigureUIModel.getRefNodeName());
            rawEntity.setRefServiceEntityName(serviceDocInitConfigureUIModel.getRefServiceEntityName());
            rawEntity.setNodeInstId(serviceDocInitConfigureUIModel.getNodeInstId());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convHomeDocumentToConfigureUI(
            ServiceDocumentSetting serviceDocumentSetting,
            ServiceDocInitConfigureUIModel serviceDocInitConfigureUIModel,
            LogonInfo logonInfo)  {
        if (serviceDocumentSetting != null) {

        }
    }


}
