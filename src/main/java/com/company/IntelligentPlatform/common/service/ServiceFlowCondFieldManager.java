package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.controller.ServiceFlowCondFieldUIModel;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.StandardFieldTypeProxy;
import com.company.IntelligentPlatform.common.service.StandardValueComparatorProxy;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxy;
import com.company.IntelligentPlatform.common.service.ServiceUIModelRepository;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondField;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondGroup;
import com.company.IntelligentPlatform.common.model.ServiceFlowException;
import com.company.IntelligentPlatform.common.model.ServiceFlowModel;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceFieldMeta;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class ServiceFlowCondFieldManager {

    public static final String METHOD_ConvServiceFlowCondFieldToUI = "convServiceFlowCondFieldToUI";

    public static final String METHOD_ConvUIToServiceFlowCondField = "convUIToServiceFlowCondField";

    public static final String METHOD_ConvServiceFlowCondGroupToFieldUI = "convServiceFlowCondGroupToFieldUI";

    public static final String METHOD_ConvRootDocToFieldUI = "convRootDocToFieldUI";

    @Autowired
    protected ServiceFlowModelManager serviceFlowModelManager;

    @Autowired
    protected ServiceFlowCondGroupManager serviceFlowCondGroupManager;

    @Autowired
    protected StandardValueComparatorProxy standardValueComparatorProxy;

    @Autowired
    protected StandardFieldTypeProxy standardFieldTypeProxy;

    @Autowired
    protected ServiceUIModelRepository serviceUIModelRepository;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ServiceFlowCondGroup.NODENAME,
                        request.getUuid(), ServiceFlowCondField.NODENAME, serviceFlowModelManager);
        docPageHeaderInputPara.setGenBaseModelList((DocPageHeaderModelProxy.GenBaseModelList<ServiceFlowCondGroup>) serviceFlowCondGroup -> serviceFlowCondGroupManager.getPageHeaderModelList(DocPageHeaderModelProxy.getDefRequest(serviceFlowCondGroup),
                client));
        docPageHeaderInputPara.setGenHomePageModel((DocPageHeaderModelProxy.GenHomePageModel<ServiceFlowCondField>) (serviceFlowCondField, pageHeaderModel) -> {
            pageHeaderModel.setHeaderName(serviceFlowCondField.getFieldName());
            return pageHeaderModel;
        });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    public void convServiceFlowCondFieldToUI(ServiceFlowCondField serviceFlowCondField,
                                             ServiceFlowCondFieldUIModel serviceFlowCondFieldUIModel) {
        convServiceFlowCondFieldToUI(serviceFlowCondField, serviceFlowCondFieldUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convServiceFlowCondFieldToUI(ServiceFlowCondField serviceFlowCondField,
                                             ServiceFlowCondFieldUIModel serviceFlowCondFieldUIModel,
                                             LogonInfo logonInfo) {
        if (serviceFlowCondField != null) {
            if (!ServiceEntityStringHelper.checkNullString(serviceFlowCondField.getUuid())) {
                serviceFlowCondFieldUIModel.setUuid(serviceFlowCondField.getUuid());
            }
            if (!ServiceEntityStringHelper.checkNullString(serviceFlowCondField.getParentNodeUUID())) {
                serviceFlowCondFieldUIModel.setParentNodeUUID(serviceFlowCondField.getParentNodeUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(serviceFlowCondField.getRootNodeUUID())) {
                serviceFlowCondFieldUIModel.setRootNodeUUID(serviceFlowCondField.getRootNodeUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(serviceFlowCondField.getClient())) {
                serviceFlowCondFieldUIModel.setClient(serviceFlowCondField.getClient());
            }
            serviceFlowCondFieldUIModel.setFieldType(serviceFlowCondField.getFieldType());
            if (logonInfo != null) {
                Map<String, String> fieldTypeMap = this.initFieldTypeMap();
                serviceFlowCondFieldUIModel.setFieldTypeValue(fieldTypeMap.get(serviceFlowCondField.getFieldType()));
            }
            serviceFlowCondFieldUIModel.setNote(serviceFlowCondField.getNote());
            serviceFlowCondFieldUIModel.setNodeInstId(serviceFlowCondField.getNodeInstId());
            serviceFlowCondFieldUIModel.setValueOperator(serviceFlowCondField.getValueOperator());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> valueOperatorMap = this.initValueOperatorMap(logonInfo.getLanguageCode());
                    serviceFlowCondFieldUIModel.setValueOperatorValue(valueOperatorMap.get(serviceFlowCondField.getValueOperator()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "valueOperator"));
                }
            }
            serviceFlowCondFieldUIModel.setFieldName(serviceFlowCondField.getFieldName());
            serviceFlowCondFieldUIModel.setTargetValue(serviceFlowCondField.getTargetValue());
        }
    }

    /**
     * [Internal method] Convert from UI model to se model:serviceFlowCondField
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToServiceFlowCondField(ServiceFlowCondFieldUIModel serviceFlowCondFieldUIModel,
                                             ServiceFlowCondField rawEntity) {
        if (!ServiceEntityStringHelper.checkNullString(serviceFlowCondFieldUIModel.getUuid())) {
            rawEntity.setUuid(serviceFlowCondFieldUIModel.getUuid());
        }
        if (!ServiceEntityStringHelper.checkNullString(serviceFlowCondFieldUIModel.getClient())) {
            rawEntity.setClient(serviceFlowCondFieldUIModel.getClient());
        }
        if (!ServiceEntityStringHelper.checkNullString(serviceFlowCondFieldUIModel.getParentNodeUUID())) {
            rawEntity.setParentNodeUUID(serviceFlowCondFieldUIModel.getParentNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(serviceFlowCondFieldUIModel.getRootNodeUUID())) {
            rawEntity.setRootNodeUUID(serviceFlowCondFieldUIModel.getRootNodeUUID());
        }
        rawEntity.setFieldType(serviceFlowCondFieldUIModel.getFieldType());
        rawEntity.setNodeInstId(serviceFlowCondFieldUIModel.getNodeInstId());
        rawEntity.setValueOperator(serviceFlowCondFieldUIModel.getValueOperator());
        rawEntity.setFieldName(serviceFlowCondFieldUIModel.getFieldName());
        rawEntity.setTargetValue(serviceFlowCondFieldUIModel.getTargetValue());
    }

    public void convServiceFlowCondGroupToFieldUI(ServiceFlowCondGroup serviceFlowCondGroup,
                                             ServiceFlowCondFieldUIModel serviceFlowCondFieldUIModel) {
        if (serviceFlowCondGroup != null) {
            serviceFlowCondFieldUIModel.setGroupId(serviceFlowCondGroup.getId());
        }
    }

    public void convRootDocToFieldUI(ServiceFlowModel serviceFlowModel,
                                     ServiceFlowCondFieldUIModel serviceFlowCondFieldUIModel) {
        if (serviceFlowModel != null) {
            serviceFlowCondFieldUIModel.setRootDocId(serviceFlowModel.getId());
            serviceFlowCondFieldUIModel.setRootDocName(serviceFlowModel.getName());
            serviceFlowCondFieldUIModel.setServiceUIModuleId(serviceFlowModel.getServiceUIModelId());
        }
    }

    public Map<Integer, String> initValueOperatorMap(String languageCode) throws ServiceEntityInstallationException {
        return standardValueComparatorProxy.getValueComparatorMap(languageCode);
    }

    public Map<String, String> getNodeInstMap(String serviceUIModelId, String anguageCode) throws ServiceFlowException {
        List<ServiceUIModuleProxy.ServiceModelNodeMeta> serviceModelNodeMetaList =
                getServiceModelNodeFieldsMetaList(serviceUIModelId);
        // TODO current logic, no lanugage code
        Map<String, String> nodeInstMap = new HashMap<>();
        if(!ServiceCollectionsHelper.checkNullList(serviceModelNodeMetaList)){
            for(ServiceUIModuleProxy.ServiceModelNodeMeta serviceModelNodeMeta:serviceModelNodeMetaList){
                nodeInstMap.put(serviceModelNodeMeta.getNodeInstId(), serviceModelNodeMeta.getNodeInstId());
            }
        }
        return nodeInstMap;
    }

    private List<ServiceUIModuleProxy.ServiceModelNodeMeta> getServiceModelNodeFieldsMetaList(String serviceUIModelId) throws ServiceFlowException {
        ServiceUIModule serviceUIModule = null;
        try {
            serviceUIModule = serviceUIModelRepository.getServiceUIModelById(serviceUIModelId);
            if(serviceUIModule == null){
                throw new ServiceFlowException(ServiceFlowException.PARA_NULL_SERVICEUIMODEL, serviceUIModelId);
            }
        } catch (IllegalAccessException | NoSuchFieldException | ServiceFlowException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "serviceUIModelId"));
            throw new ServiceFlowException(ServiceFlowException.PARA_SYSTEM_ERROR, e.getMessage());
        }
        return ServiceUIModuleProxy.getServiceModelNodeFieldsMetaList(serviceUIModule.getClass());
    }

    private List<ServiceFieldMeta> getNodeFieldMetaList(String nodeInstId, String serviceUIModelId, String anguageCode) throws ServiceFlowException {
        List<ServiceUIModuleProxy.ServiceModelNodeMeta> serviceModelNodeMetaList =
                getServiceModelNodeFieldsMetaList(serviceUIModelId);
        ServiceUIModuleProxy.ServiceModelNodeMeta serviceModelNodeMeta =
                ServiceCollectionsHelper.filterOnline(serviceModelNodeMetaList, tmpModelNodeMeta->{
                    return nodeInstId.equals(tmpModelNodeMeta.getNodeInstId());
                });
        if(serviceModelNodeMeta == null){
            throw new ServiceFlowException(ServiceFlowException.PARA_SYSTEM_ERROR, nodeInstId);
        }
        return ServiceUIModuleProxy.getNodeFieldList(serviceModelNodeMeta);
    }

    public Map<String, String> getFieldNameMap(String nodeInstId, String serviceUIModelId, String languageCode) throws ServiceFlowException {
        List<ServiceFieldMeta> fieldMetaList = getNodeFieldMetaList(nodeInstId, serviceUIModelId, languageCode);
        // TODO current logic, no lanugage code
        Map<String, String> fieldNameMap = new HashMap<>();
        if(!ServiceCollectionsHelper.checkNullList(fieldMetaList)){
            for(ServiceFieldMeta serviceFieldMeta:fieldMetaList){
                fieldNameMap.put(serviceFieldMeta.getFieldName(), serviceFieldMeta.getFieldName());
            }
        }
        return fieldNameMap;
    }

    public ServiceFieldMeta getFieldMeta(String fieldName, String nodeInstId, String serviceUIModelId,
                                            String languageCode) throws ServiceFlowException {
        List<ServiceFieldMeta> fieldMetaList = getNodeFieldMetaList(nodeInstId, serviceUIModelId, languageCode);
        if(!ServiceCollectionsHelper.checkNullList(fieldMetaList)){
            List<ServiceFieldMeta> filterFieldMetaList = fieldMetaList.stream().filter(serviceFieldMeta -> {
                return fieldName.equals(serviceFieldMeta.getFieldName());
            }).collect(Collectors.toList());
            if(!ServiceCollectionsHelper.checkNullList(filterFieldMetaList)){
                return filterFieldMetaList.get(0);
            }
        }
        return null;
    }

    public Map<String, String> initFieldTypeMap(){
        return standardFieldTypeProxy.getFormatFieldTypeMap();
    }

}
