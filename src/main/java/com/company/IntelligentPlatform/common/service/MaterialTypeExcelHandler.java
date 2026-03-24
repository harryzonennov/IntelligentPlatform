package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialTypeUIModel;
import com.company.IntelligentPlatform.common.service.CorporateCustomerException;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportConfig;
import com.company.IntelligentPlatform.common.dto.ServiceExcelReportResponseModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.common.service.ServiceExcelHandlerProxy;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.*;

@Service
public class MaterialTypeExcelHandler extends ServiceExcelHandlerProxy {

    @Autowired
    protected MaterialTypeSpecifier materialTypeSpecifier;

    @Autowired
    protected MaterialTypeManager materialTypeManager;

    @Override
    public List<FieldNameUnit> getDefFieldNameList() {
        List<FieldNameUnit> fieldNameList = new ArrayList<>();
        fieldNameList.add(new FieldNameUnit("id"));
        fieldNameList.add(new FieldNameUnit("name"));
        fieldNameList.add(new FieldNameUnit("genderValue"));
        fieldNameList.add(new FieldNameUnit("operateTypeValue"));
        fieldNameList.add(new FieldNameUnit("workRoleValue"));
        fieldNameList.add(new FieldNameUnit("jobLevelValue"));
        return fieldNameList;
    }

    /**
     * API method to provide default logic to update excel to batch
     * @param serviceExcelReportResponseModel
     * @param convertDocumentModel
     * @param overwiteFlag
     * @param serialLogonInfo
     * @throws ServiceComExecuteException
     * @throws ServiceExcelConfigException
     * @throws ServiceEntityConfigureException
     */
    @Override
    public <U extends SEUIComModel, R extends ServiceModule> void updateDefExcelBatch(
            ServiceExcelReportResponseModel serviceExcelReportResponseModel,
            IConvertDocumentModel<U, R> convertDocumentModel,
            boolean overwiteFlag,
            SerialLogonInfo serialLogonInfo)
            throws ServiceComExecuteException, ServiceExcelConfigException, ServiceEntityConfigureException {
        try {
            updateExcelBatch(serviceExcelReportResponseModel, serialLogonInfo);
        } catch (ServiceEntityInstallationException e) {
            throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

    public void updateExcelBatch(
            ServiceExcelReportResponseModel serviceExcelReportResponseModel,
            SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException,
            ServiceEntityInstallationException {
        Map<Integer, SEUIComModel> dataMap = serviceExcelReportResponseModel
                .getDataMap();
        if (dataMap == null || dataMap.isEmpty()) {
            return;
        }
        /**
         * [Step1] retrieve raw data list
         */
        List<ServiceEntityNode> materialTypeList = new ArrayList<>();
        materialTypeList = materialTypeManager.getEntityNodeListByKey(null, null,
                MaterialType.NODENAME, serialLogonInfo.getClient(), null);
        Set<Integer> keySet = dataMap.keySet();
        Iterator<Integer> it = keySet.iterator();
        List<MaterialTypeUIModel> rawMaterialTypeUIModelList = new ArrayList<>();
        while (it.hasNext()) {
            int key = it.next();
            MaterialTypeUIModel materialTypeUIModel = (MaterialTypeUIModel) dataMap
                    .get(key);
            rawMaterialTypeUIModelList.add(materialTypeUIModel);
        }
        if (ServiceCollectionsHelper.checkNullList(rawMaterialTypeUIModelList)) {
            return;
        }
        List<MaterialTypeUIModel> topLevelMaterialTypeUIModelList = filterTopLevelTypeUIModelList(rawMaterialTypeUIModelList);
        if (!ServiceCollectionsHelper
                .checkNullList(topLevelMaterialTypeUIModelList)) {
            updateTypeExcelModelBatch(topLevelMaterialTypeUIModelList,
                    rawMaterialTypeUIModelList, materialTypeList,
                    serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID(), serialLogonInfo.getClient());
        }
    }


    private List<MaterialTypeUIModel> filterTopLevelTypeUIModelList(
            List<MaterialTypeUIModel> rawMaterialTypeUIModelList) {
        List<MaterialTypeUIModel> resultList = new ArrayList<>();
        if (ServiceCollectionsHelper.checkNullList(rawMaterialTypeUIModelList)) {
            return resultList;
        }
        for (MaterialTypeUIModel materialTypeUIModel : rawMaterialTypeUIModelList) {
            String parentTypeId = materialTypeUIModel.getParentTypeId();
            if (ServiceEntityStringHelper.checkNullString(parentTypeId)) {
                resultList.add(materialTypeUIModel);
                continue;
            }
            MaterialTypeUIModel parentTypeUIModel = (MaterialTypeUIModel) ServiceCollectionsHelper
                    .filterOnline(
                            parentTypeId,
                            rawModel -> {
                                MaterialTypeUIModel tempUIModel = (MaterialTypeUIModel) rawModel;
                                return tempUIModel.getId();
                            }, rawMaterialTypeUIModelList);
            if (parentTypeUIModel == null) {
                resultList.add(materialTypeUIModel);
                continue;
            }
        }
        return resultList;
    }

    public void updateTypeExcelModelBatch(
            List<MaterialTypeUIModel> materialTypeUIModelList,
            List<MaterialTypeUIModel> rawMaterialTypeUIModelList,
            List<ServiceEntityNode> materialTypeList, String logonUserUUID,
            String organizationUUID, String client) {
        if (ServiceCollectionsHelper.checkNullList(materialTypeUIModelList)) {
            return;
        }
        if (ServiceCollectionsHelper.checkNullList(rawMaterialTypeUIModelList)) {
            return;
        }
        for (MaterialTypeUIModel materialTypeUIModel : materialTypeUIModelList) {
            String materialTypeId = materialTypeUIModel.getId();
            if (ServiceEntityStringHelper.checkNullString(materialTypeId)) {
                return;
            }
            if (!ServiceEntityStringHelper.checkNullString(materialTypeUIModel
                    .getParentTypeId())) {
                // In case need link to parent Type
                ServiceEntityNode parentSENode = ServiceCollectionsHelper
                        .filterSENodeOnline(
                                materialTypeUIModel.getParentTypeId(),
                                materialTypeList, seNode -> {
                                    return seNode.getId();
                                });
                if (parentSENode != null) {
                    materialTypeUIModel.setParentTypeUUID(parentSENode
                            .getUuid());
                } else {
                    // In case have to retrieve from back-end
                    try {
                        parentSENode = materialTypeManager.getEntityNodeByKey(
                                materialTypeUIModel.getParentTypeId(),
                                IServiceEntityNodeFieldConstant.ID,
                                MaterialType.NODENAME, null, true);
                        if (parentSENode != null) {
                            materialTypeUIModel.setParentTypeUUID(parentSENode
                                    .getUuid());
                        } else {
                            // Can not find parent type, just continue;
                            continue;
                        }
                    } catch (ServiceEntityConfigureException e) {
                        logger.error(ServiceEntityStringHelper
                                .genDefaultErrorMessage(e, materialTypeId));
                    }
                }
            }
            /*
             * [Step2] insert into DB
             */
            try {
                insertExcelModelUnion(materialTypeUIModel, logonUserUUID,
                        organizationUUID, client);
            } catch (ServiceEntityConfigureException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, materialTypeId));
            }
            /*
             * [Step3] Batch process child materialTypeList
             */
            @SuppressWarnings("unchecked")
            List<MaterialTypeUIModel> childTypeUIModelList = (List<MaterialTypeUIModel>) ServiceCollectionsHelper
                    .filterListOnline(
                            materialTypeId,
                            rawObject -> {
                                MaterialTypeUIModel tempUIModel = (MaterialTypeUIModel) rawObject;
                                return tempUIModel.getParentTypeId();
                            }, rawMaterialTypeUIModelList, false);
            if (!ServiceCollectionsHelper.checkNullList(childTypeUIModelList)) {
                updateTypeExcelModelBatch(childTypeUIModelList,
                        rawMaterialTypeUIModelList, materialTypeList,
                        logonUserUUID, organizationUUID, client);
            }
        }
    }

    /**
     * Core method for insert corporate distributor excel model
     *
     * @param logonUserUUID
     * @param organizationUUID
     * @throws CorporateCustomerException
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public void insertExcelModelUnion(MaterialTypeUIModel materialTypeUIModel,
                                      String logonUserUUID, String organizationUUID, String client)
            throws ServiceEntityConfigureException {
        /*
         * [Step1] Necessary conversion and insert into DB
         */
        ServiceEntityNode backSENode = materialTypeManager.getEntityNodeByKey(
                materialTypeUIModel.getId(),
                IServiceEntityNodeFieldConstant.ID, MaterialType.NODENAME,
                null, true);
        if (backSENode != null) {
            return;
        }
        MaterialType materialType = (MaterialType) materialTypeManager.newRootEntityNode(client);
        materialTypeManager.convUIToMaterialType(materialTypeUIModel, materialType);
        materialTypeManager.insertSENode(materialType, logonUserUUID, organizationUUID);
    }

    @Override
    public boolean checkCustomUploadExcel(String configureName, String client) throws ServiceEntityConfigureException {
        return false;
    }

    @Override
    public boolean checkCustomDownloadExcel(String configureName, String client)
            throws ServiceEntityConfigureException {
        return false;
    }

    @Override
    public Class<?> getExcelModelClass() {
        return MaterialTypeUIModel.class;
    }

    @Override
    public DocumentContentSpecifier getDocumentSpecifier() {
        return materialTypeSpecifier;
    }

    @Override
    public ServiceExcelReportConfig getDefExcelConfigure(SerialLogonInfo serialLogonInfo) throws ServiceExcelConfigException {
        return this.getDefExcelConfigureTemplate(serialLogonInfo);
    }

    @Override
    public String getConfigureName() {
        return MaterialType.SENAME;
    }

    @Override
    protected List<MetaModelConfigure<SEUIComModel>> getMetaModelConfigure(SerialLogonInfo serialLogonInfo) {
        return null;
    }

    @Override
    protected List<FieldMeta<SEUIComModel>> getFieldMetaList(SerialLogonInfo serialLogonInfo)
            throws ServiceEntityInstallationException {
        return null;
    }


}
