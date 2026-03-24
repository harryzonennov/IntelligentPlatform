package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.dto.QualityInspectOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.logistics.dto.QualityInspectOrderUIModel;
import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.InboundDeliveryServiceModel;
import com.company.IntelligentPlatform.logistics.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.*;

@Service
public class QualityInspectOrderActionExecutionProxy
        extends DocActionExecutionProxy<QualityInspectOrderServiceModel, QualityInspectOrder, QualityInspectMatItem> {

    @Autowired
    protected QualityInspectOrderManager qualityInspectOrderManager;

    @Autowired
    protected QualityInspectOrderSpecifier qualityInspectOrderSpecifier;

    @Autowired
    protected QualityInspectOrderServiceUIModelExtension qualityInspectOrderServiceUIModelExtension;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected InboundDeliveryManager inboundDeliveryManager;

    @Autowired
    protected QualityInspectCrossConvertRequest qualityInspectCrossConvertRequest;

    public static final String PROPERTY_ACTIONCODE_FILE = "QualityInspectOrder_actionCode";

    protected Logger logger = LoggerFactory.getLogger(QualityInspectOrderActionExecutionProxy.class);

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = QualityInspectOrderUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE);
    }

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        return ServiceCollectionsHelper.asList(
                new DocActionConfigure(QualityInsOrderActionNode.DOC_ACTION_START_TEST,
                        QualityInspectOrder.STATUS_INPROCESS,
                        ServiceCollectionsHelper.asList(QualityInspectOrder.STATUS_INITIAL),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(QualityInsOrderActionNode.DOC_ACTION_TESTDONE,
                        QualityInspectOrder.STATUS_TESTDONE,
                        ServiceCollectionsHelper.asList(QualityInspectOrder.STATUS_INPROCESS),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(QualityInsOrderActionNode.DOC_ACTION_DELIVERY_DONE,
                        QualityInspectOrder.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(QualityInspectOrder.STATUS_TESTDONE),
                        ISystemActionCode.ACID_AUDITDOC));
    }

    @Override
    public List<DocActionConfigure> getCustomDocActionConfigureList(String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return null;
    }

    @Override
    public List<CrossDocActConfigure> getDefCrossDocActConfigureList() {
        return null;
    }

    @Override
    public List<CrossDocActConfigure> getCustomCrossDocActConfigureList(String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return this.getCustomCrossDocActionConfigureListTool(QualityInspectOrder.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER, client);
    }

    @Override
    public List<CrossDocBatchConvertProxy.DocContentCreateContext> crossCreateDocumentCore(ServiceModule sourceServiceModule,
                                         List<ServiceEntityNode> selectedSourceDocMatItemList,
                                         DocumentMatItemBatchGenRequest genRequest, CrossDocConvertRequest.InputOption inputOption,
                                                                                           LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException,
            SearchConfigureException, ServiceEntityInstallationException {
        int targetDocType = genRequest.getTargetDocType();
        if(targetDocType == IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY){
            // Special logic for outbound to create inbound delivery
            try {
                return customConvertToInbound(sourceServiceModule, selectedSourceDocMatItemList,
                        (DeliveryMatItemBatchGenRequest) genRequest, logonInfo);
            } catch (MaterialException | QualityInspectException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }else{
            // In case outbound for other document
            return super.crossCreateDocumentCore(sourceServiceModule, selectedSourceDocMatItemList, genRequest,inputOption,
                    logonInfo);
        }
    }

    private List<CrossDocBatchConvertProxy.DocContentCreateContext> customConvertToInbound(ServiceModule sourceServiceModule,
                                       List<ServiceEntityNode> selectedSourceDocMatItemList,
                                       DeliveryMatItemBatchGenRequest genRequest, LogonInfo logonInfo)
            throws DocActionException, ServiceModuleProxyException, SearchConfigureException,
            ServiceEntityConfigureException, ServiceEntityInstallationException, MaterialException,
            QualityInspectException {
        /*
         * [step1]: call target handleInboundCrossCreateDoc
         */
        int targetDocType = genRequest.getTargetDocType();
        List<CrossDocBatchConvertProxy.DocContentCreateContext> docContentCreateContextList = new ArrayList<>();
        DocumentContentSpecifier<QualityInspectOrderServiceModel, QualityInspectOrder, QualityInspectMatItem> documentContentSpecifier =
                this.getDocumentContentSpecifier();
        DocActionExecutionProxy<InboundDeliveryServiceModel, InboundDelivery, InboundItem> targetDocActionProxy =
                docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(targetDocType);
        if(targetDocActionProxy == null){
            throw new DocActionException(DocActionException.PARA_MISS_CONFIG, targetDocType);
        }
        /*
         * [Step1] Standard step to gen in-bound delivery for standard products
         */
        List<ServiceEntityNode> standardWasteInspectMatItemList = selectedSourceDocMatItemList;
        if(ServiceCollectionsHelper.checkNullList(standardWasteInspectMatItemList)){
            return null;
        }
        /*
         * [Step2] Generate inbound item list from wasted material item list
         */
        Map<String, List<ServiceEntityNode>> wasteInboundRequestMap = processWasteInboundRequestMap(selectedSourceDocMatItemList, logonInfo.getClient());
        if(wasteInboundRequestMap != null){
            Set<String> keySet = wasteInboundRequestMap.keySet();
            for (String refWarehouseUUID : keySet) {
                DeliveryMatItemBatchGenRequest wasteGenRequest = (DeliveryMatItemBatchGenRequest) genRequest.clone();
                wasteGenRequest.setRefWarehouseUUID(refWarehouseUUID);
                List<ServiceEntityNode> tmpWasteInspectMatItemList = wasteInboundRequestMap.get(refWarehouseUUID);
                if (!ServiceCollectionsHelper.checkNullList(tmpWasteInspectMatItemList)) {
                    removeQualityMatItemWhenNoPassed(tmpWasteInspectMatItemList, standardWasteInspectMatItemList);
                    CrossDocConvertRequest<InboundDeliveryServiceModel, InboundItem, ?> crossDocConvertRequest =
                            targetDocActionProxy.getCrossDocCovertRequest();
                    crossDocConvertRequest.setCovertToTargetItem(convertItemContext -> convertFailInspectMatItemToInboundItem((QualityInspectMatItem) convertItemContext.getSourceMatItemNode(),
                   (InboundItem) convertItemContext.getTargetMatItemNode()));
                    docContentCreateContextList = targetDocActionProxy.handleInboundCrossCreateDoc(sourceServiceModule,
                            IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER, tmpWasteInspectMatItemList,
                                    wasteGenRequest, new CrossDocConvertRequest.InputOption(),
                                    logonInfo);
                }
            }
        }
        /*
         * [step3]: Generate inbound item list from normal material item list
         */
        if (!ServiceCollectionsHelper.checkNullList(standardWasteInspectMatItemList)) {
            CrossDocConvertRequest<InboundDeliveryServiceModel, InboundItem, ?> crossDocConvertRequest =
                    targetDocActionProxy.getCrossDocCovertRequest();
            crossDocConvertRequest.setCovertToTargetItem((convertItemContext) -> {
                try {
                    return convertNormalInspectMatItemToInboundItem((QualityInspectMatItem) convertItemContext.getSourceMatItemNode(),
                            (InboundItem) convertItemContext.getTargetMatItemNode());
                } catch (MaterialException | QualityInspectException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    return null;
                }
            });
            docContentCreateContextList =
                    targetDocActionProxy.handleInboundCrossCreateDoc(sourceServiceModule,
                    IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER, standardWasteInspectMatItemList, genRequest,
                    new CrossDocConvertRequest.InputOption(), logonInfo);
        }
        return docContentCreateContextList;
    }

    public InboundItem convertFailInspectMatItemToInboundItem(
            QualityInspectMatItem qualityInspectMatItem, InboundItem inboundItem) {
        // Important logic: If there is fail amount, then just calculate success amount
        if(qualityInspectMatItem.getFailAmount() > 0){
            inboundItem.setAmount(qualityInspectMatItem.getFailAmount());
            inboundItem.setRefUnitUUID(qualityInspectMatItem.getFailRefUnitUUID());
            inboundItem.setActualAmount(qualityInspectMatItem.getFailAmount());
            // Important set the material status as 'archive' for not qualified product
            inboundItem.setMaterialStatus(MaterialStockKeepUnit.TRACESTATUS_ARCHIVE);
            return inboundItem;
        }else{
            return null;
        }
    }

    public InboundItem convertNormalInspectMatItemToInboundItem(
            QualityInspectMatItem qualityInspectMatItem, InboundItem inboundItem) throws MaterialException, QualityInspectException {
        if (inboundItem != null && qualityInspectMatItem != null) {
            if (!ServiceEntityStringHelper
                    .checkNullString(qualityInspectMatItem
                            .getRefWarehouseAreaUUID())) {
                inboundItem
                        .setRefWarehouseAreaUUID(qualityInspectMatItem
                                .getRefWarehouseAreaUUID());
            }
            // Important logic: If there is fail amount, then just calculate success amount
            if(qualityInspectMatItem.getFailAmount() > 0){
                StorageCoreUnit successUnit;
                try {
                    successUnit = qualityInspectOrderManager.calculateSuccessAmount(qualityInspectMatItem);
                    if(successUnit.getAmount() <= 0){
                        return null;
                    }
                    /*
                     * [Important] In case no success amount, just return null; then framework will stop writing into delivery
                     */
                    inboundItem.setAmount(successUnit.getAmount());
                    inboundItem.setActualAmount(successUnit.getAmount());
                    inboundItem.setRefUnitUUID(successUnit.getRefUnitUUID());
                    inboundItem.setMaterialStatus(MaterialStockKeepUnit.TRACESTATUS_ACTIVE);
                } catch (ServiceEntityConfigureException e) {
                    throw new QualityInspectException(QualityInspectException.PARA_SYSTEM_ERROR, e.getMessage());
                }
            }else{
                // In case all success
                inboundItem.setMaterialStatus(MaterialStockKeepUnit.TRACESTATUS_ACTIVE);
            }
            return inboundItem;
        }
        return null;
    }

    /**
     * Core Logic to remove from all selected quality inspect mat item list from next process as standard inbound
     * request, if there is no success amount
     * @param tmpWasteInspectMatItemList
     * @param allSelectedWasteInspectMatItemList
     */
    private void removeQualityMatItemWhenNoPassed(List<ServiceEntityNode> tmpWasteInspectMatItemList,
                                                  List<ServiceEntityNode> allSelectedWasteInspectMatItemList)
            throws MaterialException, QualityInspectException, ServiceEntityConfigureException {
        if(ServiceCollectionsHelper.checkNullList(tmpWasteInspectMatItemList)){
            return;
        }
        for(ServiceEntityNode serviceEntityNode:tmpWasteInspectMatItemList){
            QualityInspectMatItem qualityInspectMatItem = (QualityInspectMatItem) serviceEntityNode;
            if(qualityInspectMatItem.getFailAmount() == 0){
                continue;
            }
            StorageCoreUnit successUnit = qualityInspectOrderManager.calculateSuccessAmount(qualityInspectMatItem);
            if(successUnit.getAmount() == 0){
                allSelectedWasteInspectMatItemList.remove(qualityInspectMatItem);
            }
        }
    }


    /**
     * Core Logic to calculate document type when conversion quality to inbound
     * @param qualityInspectMatItemList
     * @return
     */
    private int calculateDocumentType(List<ServiceEntityNode> qualityInspectMatItemList){
        if(ServiceCollectionsHelper.checkNullList(qualityInspectMatItemList)){
            return IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER;
        }
        for (ServiceEntityNode seNode : qualityInspectMatItemList) {
            QualityInspectMatItem qualityInspectMatItem = (QualityInspectMatItem) seNode;
            if(qualityInspectMatItem.getPrevDocType() > 0){
                return qualityInspectMatItem.getPrevDocType();
            }
        }
        return IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER;
    }


    private Map<String, List<ServiceEntityNode>> processWasteInboundRequestMap(
            List<ServiceEntityNode> qualityInspectMatItemList, String client)
            throws ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(qualityInspectMatItemList)) {
            return null;
        }
        Map<String, List<ServiceEntityNode>> resultMap = new HashMap<>();
        List<ServiceEntityNode> filteredResultList = new ArrayList<>();
        List<String> prevItemUUIDList = new ArrayList<>();
        List<String> warehouseUUIDList = new ArrayList<>();
        /*
         * [Step1] Filter the quality item with waste warehouse and fail amount
         */
        for (ServiceEntityNode seNode : qualityInspectMatItemList) {
            QualityInspectMatItem qualityInspectMatItem = (QualityInspectMatItem) seNode;
            if (ServiceEntityStringHelper.checkNullString(qualityInspectMatItem
                    .getRefWasteWarehouseUUID())) {
                continue;
            }
            if (qualityInspectMatItem.getFailAmount() <= 0) {
                continue;
            }
            filteredResultList.add(qualityInspectMatItem);
            prevItemUUIDList.add(qualityInspectMatItem.getUuid());
            if(!warehouseUUIDList.contains(qualityInspectMatItem
                    .getRefWasteWarehouseUUID())){
                warehouseUUIDList.add(qualityInspectMatItem
                        .getRefWasteWarehouseUUID());
            }
        }
        if (ServiceCollectionsHelper.checkNullList(filteredResultList)) {
            return null;
        }
        /*
         * [Step2] Trying to find existed in-bound item pointing to these
         * quality mat item list, to avoid duplicate inbound item list
         */
        List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
        key1.setKeyName(IServiceEntityCommonFieldConstant.PREVDOCMATITEMUUID);
        key1.setMultipleValueList(prevItemUUIDList);
        keyList.add(key1);
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure();
        key2.setKeyName(IServiceEntityNodeFieldConstant.ROOTNODEUUID);
        key2.setMultipleValueList(warehouseUUIDList);
        keyList.add(key2);
        List<ServiceEntityNode> inboundItemList = inboundDeliveryManager
                .getEntityNodeListByKeyList(keyList,
                        InboundItem.NODENAME, client, null);
        List<ServiceEntityNode> toRemoveList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(inboundItemList)) {
            for (ServiceEntityNode seNode : inboundItemList) {
                InboundItem inboundItem = (InboundItem) seNode;
                ServiceEntityNode removeNode = ServiceCollectionsHelper
                        .filterSENodeOnline(
                                inboundItem.getPrevDocMatItemUUID(),
                                filteredResultList);
                if (removeNode != null) {
                    toRemoveList.add(removeNode);
                }
            }
        }
        if (!ServiceCollectionsHelper.checkNullList(toRemoveList)) {
            filteredResultList.removeAll(toRemoveList);
        }
        if (ServiceCollectionsHelper.checkNullList(filteredResultList)) {
            return null;
        }
        /*
         * [Step3] Build in-bound request map
         */
        for (ServiceEntityNode seNode : filteredResultList) {
            QualityInspectMatItem qualityInspectMatItem = (QualityInspectMatItem) seNode;
            String refWarehouseUUID = qualityInspectMatItem
                    .getRefWasteWarehouseUUID();
            if (resultMap.containsKey(refWarehouseUUID)) {
                List<ServiceEntityNode> tempQualityItemList = resultMap
                        .get(refWarehouseUUID);
                if (tempQualityItemList == null) {
                    tempQualityItemList = new ArrayList<>();
                }
                tempQualityItemList.add(qualityInspectMatItem);
            } else {
                List<ServiceEntityNode> tempQualityItemList = new ArrayList<>();
                tempQualityItemList.add(qualityInspectMatItem);
                resultMap.put(refWarehouseUUID, tempQualityItemList);
            }
        }
        return resultMap;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap() {
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap = new HashMap<>();
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER,
                        IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY, null, StandardSwitchProxy.SWITCH_ON,
                        QualityInsOrderActionNode.DOC_ACTION_DELIVERY_DONE, StandardSwitchProxy.SWITCH_OFF));
        return crossCopyDocConversionConfigMap;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap() {
        return null;
    }

    @Override
    public DocumentContentSpecifier<QualityInspectOrderServiceModel, QualityInspectOrder, QualityInspectMatItem> getDocumentContentSpecifier() {
        return qualityInspectOrderSpecifier;
    }

    @Override
    public DocSplitMergeRequest<QualityInspectOrder, QualityInspectMatItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<QualityInspectOrderServiceModel, QualityInspectMatItem, ?> getCrossDocCovertRequest() {
        return qualityInspectCrossConvertRequest;
    }

    private int getCategoryBySourceDocType(int sourceDocType){
        if(sourceDocType == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER || sourceDocType == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER){
            return QualityInspectOrder.CATEGORY_PRODUCTION;
        }
        return QualityInspectOrder.CATEGORY_INBOUND;
    }


    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return qualityInspectOrderManager;
    }


    public void executeActionCore(
            QualityInspectOrderServiceModel qualityInspectOrderServiceModel,
            int docActionCode,
            SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(qualityInspectOrderServiceModel, docActionCode, (outboundDelivery, serialLogonInfo1) -> {
            if(docActionCode == QualityInsOrderActionNode.DOC_ACTION_START_TEST){
                try {
                    qualityInspectOrderManager.startInspectProcess(qualityInspectOrderServiceModel);
                } catch (ServiceEntityConfigureException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                } catch ( ServiceModuleProxyException | QualityInspectException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            }
            return outboundDelivery; }, null, serialLogonInfo);
    }

    @Override
    public void checkUpdateItemStatus(QualityInspectMatItem qualityInspectMatItem, int docActionCode,
                                      SerialLogonInfo serialLogonInfo, boolean skipFlag,
                                      DocItemActionExecution<QualityInspectMatItem> docItemActionExecution)
            throws DocActionException {
        super.checkUpdateItemStatus(qualityInspectMatItem, docActionCode, serialLogonInfo, skipFlag, docItemActionExecution);
        if(docActionCode == QualityInsOrderActionNode.DOC_ACTION_TESTDONE){
            MaterialStockKeepUnit materialStockKeepUnit = null;
            try {
                materialStockKeepUnit = qualityInspectOrderManager.getReferenceMaterialSKU(
                        qualityInspectMatItem.getRefMaterialSKUUUID(),
                        qualityInspectMatItem.getClient());
                if (materialStockKeepUnit != null
                        && RegisteredProduct.SENAME
                        .equals(materialStockKeepUnit
                                .getServiceEntityName())) {
                    qualityInspectOrderManager.setCompleteQualityInspectMatItem(qualityInspectMatItem,
                            serialLogonInfo);
                }
            } catch (ServiceEntityConfigureException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
            }
        }
    }
}
