package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.CorporateCustomerException;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.ServiceExcelCellConfig;
import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.dto.ServiceExcelReportResponseModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.common.service.ServiceExcelHandlerProxy;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public abstract class DocMatItemExcelHandler extends ServiceExcelHandlerProxy {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    protected Logger logger = LoggerFactory.getLogger(DocMatItemExcelHandler.class);

    public interface IConvertDocMatItemUIToModel<ItemUI extends DocMatItemUIModel, Item extends DocMatItemNode>{

        void execute(ItemUI docMatItemUIModel, Item docMatItem, SerialLogonInfo serialLogonInfo);

    }

    @Override
    protected <U extends SEUIComModel> List<MetaModelConfigure<U>> getMetaModelConfigure(
            SerialLogonInfo serialLogonInfo) {
        return null;
    }

    @Override
    protected <U extends SEUIComModel> List<FieldMeta<U>> getFieldMetaList(SerialLogonInfo serialLogonInfo)
            throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public String getConfigureName() {
        return getDocumentSpecifier().getMatItemNodeInstId();
    }

    public static List<FieldNameUnit> getDefDocMatItemFieldNameList(){
        List<FieldNameUnit> fieldNameList = new ArrayList<>();
        fieldNameList.add(new FieldNameUnit("refMaterialSKUId"));
        fieldNameList.add(new FieldNameUnit("refMaterialSKUName"));
        fieldNameList.add(new FieldNameUnit("amount"));
        fieldNameList.add(new FieldNameUnit("refUnitName"));
        fieldNameList.add(new FieldNameUnit("parentDocId"));
        fieldNameList.add(new FieldNameUnit("parentDocName"));
        fieldNameList.add(new FieldNameUnit("unitPrice"));
        fieldNameList.add(new FieldNameUnit("itemPrice"));
        fieldNameList.add(new FieldNameUnit("unitPriceDisplay"));
        fieldNameList.add(new FieldNameUnit("itemPriceDisplay"));
        return fieldNameList;
    }

    public static List<ServiceExcelCellConfig> buildDocMatItemFieldNameList(List<FieldNameUnit> fieldList,
                                                                            String propertyPath, String lanCode)
            throws ServiceExcelConfigException {
        try {
            Map<String, String> labelMap = ServiceDropdownListHelper.getStrStaticDropDownMap(propertyPath, lanCode);
            return ServiceExcelHandlerProxy.buildDefServiceCellConfigureList(fieldList, labelMap);
        } catch (IOException e) {
            throw new ServiceExcelConfigException(ServiceExcelConfigException.PARA_SYSTEM_WRONG, e.getMessage());
        }
    }

    public void updateItemExcel(
            ServiceExcelReportResponseModel serviceExcelReportResponseModel,
            String baseUUID,
            IConvertDocMatItemUIToModel convertDocMatItemUIToModel,
            boolean overwiteFlag,
            SerialLogonInfo serialLogonInfo)
            throws ServiceComExecuteException, ServiceExcelConfigException, ServiceEntityConfigureException {
        Map<Integer, SEUIComModel> dataMap = serviceExcelReportResponseModel.getDataMap();
        if (dataMap == null || dataMap.isEmpty()) {
            return;
        }
        DocumentContentSpecifier documentContentSpecifier = this.getDocumentSpecifier();
        ServiceEntityManager serviceEntityManager = documentContentSpecifier.getDocumentManager();
        ServiceEntityNode parentDoc = serviceEntityManager
                .getEntityNodeByKey(baseUUID,
                        IServiceEntityNodeFieldConstant.UUID,
                        documentContentSpecifier.getDocNodeName(),
                        serialLogonInfo.getClient(), null);
        List<ServiceEntityNode> rawDocMatItemList = serviceEntityManager
                .getEntityNodeListByKey(
                        baseUUID,
                        IServiceEntityNodeFieldConstant.PARENTNODEUUID,
                        documentContentSpecifier.getMatItemNodeInstId(),
                        serialLogonInfo.getClient(), null);
        /*
         * [Step1] retrieve raw data list
         */
        Set<Integer> keySet = dataMap.keySet();
        for (int row : keySet) {
            DocMatItemUIModel itemUIModel = (DocMatItemUIModel) dataMap.get(row);
            try {
                this.updateExcelModel(row, itemUIModel, parentDoc, rawDocMatItemList, convertDocMatItemUIToModel,
                        documentContentSpecifier,
                        overwiteFlag,
                        serialLogonInfo);
            } catch (MaterialException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, row + ""));
                throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            } catch (ServiceEntityInstallationException | ServiceEntityConfigureException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, row + ""));
                throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, e.getMessage());
            } catch (DocActionException | ServiceModuleProxyException e) {
                throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }
    }

    /**
     * Core method to update excel upload content into persistence
     *
     * @throws ServiceEntityConfigureException
     * @throws ServiceEntityInstallationException
     * @throws MaterialException
     * @throws CorporateCustomerException
     */
    public void updateExcelModel(int rowIndex,
                                 DocMatItemUIModel docMatItemUIModel,
                                 ServiceEntityNode parentDoc,
                                 List<ServiceEntityNode> rawDocMatItemList,
                                 IConvertDocMatItemUIToModel convertDocMatItemUIToModel,
                                 DocumentContentSpecifier documentContentSpecifier, boolean overwriteFlag,
                                 SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, ServiceEntityInstallationException, MaterialException,
            DocActionException, ServiceModuleProxyException {
        /*
         * [Step1] Check if the material SKU ID exist.
         */
        String refMaterialSKUId = docMatItemUIModel.getRefMaterialSKUId();
        if (ServiceEntityStringHelper.checkNullString(refMaterialSKUId)) {
            // If not, this is invalid record
            return;
        }
        MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
                .getEntityNodeByKey(refMaterialSKUId,
                        IServiceEntityNodeFieldConstant.ID,
                        MaterialStockKeepUnit.NODENAME,
                        serialLogonInfo.getClient(), null, true);
        if (materialStockKeepUnit == null) {
            // log error and return
            logger.error("Material SKU can not be found by:" + refMaterialSKUId);
            return;
        }
        String refMaterialSKUUUID = materialStockKeepUnit.getUuid();
        docMatItemUIModel.setRefMaterialSKUUUID(materialStockKeepUnit
                .getUuid());

        /*
         * [Step2] Ignore insert this doc material item, if same
         * material uuid exist.
         */
        ServiceEntityNode docMatItemNodeBack = _filterDocMatItemOnline(
                rawDocMatItemList, docMatItemUIModel.getRefMaterialSKUUUID(),
                parentDoc.getUuid());
        if (docMatItemNodeBack != null && !overwriteFlag) {
            return;
        }
        /*
         * [Step3] Process Unit
         */
        Map<String, String> materialUnitMap = materialStockKeepUnitManager
                .initMaterialUnitMap(refMaterialSKUUUID, serialLogonInfo.getClient());
        String refUnitValueKey = ServiceCollectionsHelper.getStringKeyByValue(
                materialUnitMap, docMatItemUIModel.getRefUnitName());
        if (ServiceEntityStringHelper.checkNullString(refUnitValueKey)) {
            logger.error("NO Unit:" + refMaterialSKUId);
            return;
        }
        docMatItemUIModel.setRefUnitUUID(refUnitValueKey);
        ServiceEntityManager serviceEntityManager = documentContentSpecifier.getDocumentManager();

        /*
         * [Step4] Execute insert data batch
         */
        ServiceModule parentDocServiceModule = documentContentSpecifier.loadServiceModule(parentDoc.getUuid(),
                parentDoc.getClient());
        ServiceModule docMatItemServiceModel = documentContentSpecifier.createItemServiceModule(parentDocServiceModule);
        DocMatItemNode docMatItem = null;
        try {
            docMatItem =
                    DocumentContentSpecifier.getDocMatItemNodeFromItemServiceModule(docMatItemServiceModel);
            convertDocMatItemUIToModel.execute(docMatItemUIModel, docMatItem, serialLogonInfo);
        } catch (IllegalAccessException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
        // update into persistence
        serviceEntityManager.updateServiceModule(docMatItemServiceModel.getClass(), docMatItemServiceModel, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());

    }

    private ServiceEntityNode _filterDocMatItemOnline(
            List<ServiceEntityNode> rawDocMatItemList,
            String refMaterialSKUUUID, String baseUUID) {
        if (ServiceCollectionsHelper.checkNullList(rawDocMatItemList)) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(refMaterialSKUUUID)
                || ServiceEntityStringHelper.checkNullString(baseUUID)) {
            return null;
        }
        for (ServiceEntityNode seNode : rawDocMatItemList) {
            DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
            if (baseUUID.equals(docMatItemNode.getParentNodeUUID())
                    && refMaterialSKUUUID.equals(docMatItemNode
                    .getRefMaterialSKUUUID())) {
                return docMatItemNode;
            }
        }
        return null;
    }

}
