package com.company.IntelligentPlatform.common.service;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.SimpleSEMessageResponse;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SerialIdDocumentProxy {

    @Autowired
    MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    RegisteredProductManager registeredProductManager;
    
    protected Logger logger = LoggerFactory.getLogger(SerialIdDocumentProxy.class);

    /**
     * Core Logic to create registered product instance & new purchase item
     * mapping to new registered product.
     *
     */
    public List<ServiceEntityNode> newRegProductDocMatItem(DocumentContent documentContent,
                                                           String refTempMaterialSKUUUID, List<String> serialIdList,
                                                           DocumentContentSpecifier<?,?,?> documentContentSpecifier,
                                                           RegisteredProductManager.RegisteredProductInvolvePartyMatrix registeredProductInvolvePartyMatrix,
                                                           IPostProcessDocMatItem iPostProcessDocMatItem,
                                                           SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, RegisteredProductException {
        if (ServiceCollectionsHelper.checkNullList(serialIdList)) {
            return null;
        }
        // remove duplicate of serialIdList
        serialIdList = serialIdList.stream().distinct().collect(Collectors.toList());
        // pre-check these serial id
        List<SimpleSEMessageResponse> errorMessageList = preCheckNewRegProdDocMatItem(documentContent, refTempMaterialSKUUUID, serialIdList, documentContentSpecifier, serialLogonInfo.getClient());
        if (!ServiceCollectionsHelper.checkNullList(errorMessageList)) {
            throw new RegisteredProductException(
                    RegisteredProductException.PARA2_DUPLICATE_SERIALID,
                    errorMessageList);
        }
        MaterialStockKeepUnit tempMaterialSKU =
                (MaterialStockKeepUnit) materialStockKeepUnitManager.getEntityNodeByUUID(refTempMaterialSKUUUID,
                        MaterialStockKeepUnit.NODENAME, serialLogonInfo.getClient());
        if (tempMaterialSKU == null) {
            throw new RegisteredProductException(RegisteredProductException.PARA_NO_TEMPLATE_MAT, refTempMaterialSKUUUID);
        }
        // Logic to get Supplier and contact info
        List<ServiceEntityNode> resultList = new ArrayList<>();
        ServiceEntityManager serviceEntityManager = documentContentSpecifier.getDocumentManager();
        String matItemNodeName = documentContentSpecifier.getMatItemNodeInstId();
        /*
         * [Step2] Check duplicate registered product
         */
        int offset = 0;
        for (String serialNumber : serialIdList) {
            RegisteredProduct existedRegProduct =
                    registeredProductManager.getRegisteredProductBySerialId(serialNumber, tempMaterialSKU.getUuid(),
                            tempMaterialSKU.getClient());
            DocMatItemNode docMatItemNode = null;
            if (existedRegProduct != null) {
                docMatItemNode =
                        (DocMatItemNode) serviceEntityManager.getEntityNodeByKey(existedRegProduct.getUuid(),
                                IServiceEntityCommonFieldConstant.REFMATERIALSKUUUID,
                                matItemNodeName, tempMaterialSKU.getClient(), null);
                if (docMatItemNode != null) {
                    // should raise exception
                    continue;
                }
            }
            docMatItemNode = (DocMatItemNode) serviceEntityManager.newEntityNode(documentContent,
                    matItemNodeName);
            List<ServiceEntityNode> registeredProductList =
                    registeredProductManager.createRegisteredProductWrapper(tempMaterialSKU, serialNumber,
                            docMatItemNode, registeredProductInvolvePartyMatrix,
                            serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
            Optional<ServiceEntityNode> registeredProductOptional = registeredProductList.stream()
                    .filter(rawSeNode -> ServiceEntityNode.NODENAME_ROOT.equals(rawSeNode.getNodeName())).findFirst();
            existedRegProduct = (RegisteredProduct) registeredProductOptional.get();
            initRegProductForDocMatItem(tempMaterialSKU, existedRegProduct, documentContent,
                    offset++, iPostProcessDocMatItem, docMatItemNode);
            resultList.add(docMatItemNode);
        }
        if (!ServiceCollectionsHelper.checkNullList(resultList)) {
            serviceEntityManager.updateSENodeList(resultList, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
        }
        return resultList;
    }

    public List<SimpleSEMessageResponse> preCheckNewRegProdDocMatItem(
            DocumentContent documentContent, String refTempMaterialSKUUUID, List<String> serialIdList,
            DocumentContentSpecifier<?,?,?> documentContentSpecifier, String client) throws ServiceEntityConfigureException {
        List<SimpleSEMessageResponse> resultList = new ArrayList<>();
        ServiceEntityManager serviceEntityManager = documentContentSpecifier.getDocumentManager();
        String matItemNodeName = documentContentSpecifier.getMatItemNodeInstId();
        for (String serialNumber : serialIdList) {
            /*
             * [Step2] Check duplicate registered product
             */
            RegisteredProduct existedRegProduct =
                    registeredProductManager.getRegisteredProductBySerialId(serialNumber, refTempMaterialSKUUUID,
                            client);
            if (existedRegProduct != null) {
                DocMatItemNode docMatItemNode =
                        (DocMatItemNode) serviceEntityManager.getEntityNodeByKey(existedRegProduct.getUuid(),
                                IServiceEntityCommonFieldConstant.REFMATERIALSKUUUID,
                                matItemNodeName, client, null);
                if (docMatItemNode != null) {
                    SimpleSEMessageResponse simpleSEMessageResponse =
                            new SimpleSEMessageResponse();
                    simpleSEMessageResponse.setMessageLevel(SimpleSEMessageResponse.MESSAGELEVEL_ERROR);
                    simpleSEMessageResponse.setRefException(
                            new RegisteredProductException(RegisteredProductException.PARA2_DUPLICATE_SERIALID));
                    simpleSEMessageResponse.setErrorCode(RegisteredProductException.PARA2_DUPLICATE_SERIALID);
                    simpleSEMessageResponse.setErrorParas(new String[]{documentContent.getId(), serialNumber});
                    resultList.add(simpleSEMessageResponse);
                }
            }
        }
        return resultList;
    }

    /**
     * Core Logic When create registered product for purchase contract,
     * initialize basic data.
     *
     * @param tempMaterialSKU
     * @param docMatItemNode
     */
    private void initRegProductForDocMatItem(MaterialStockKeepUnit tempMaterialSKU,
                                             RegisteredProduct registeredProduct,
                                             DocumentContent documentContent,
                                             int offset, IPostProcessDocMatItem iPostProcessDocMatItem,
                                             DocMatItemNode docMatItemNode) throws ServiceEntityConfigureException {
        if (iPostProcessDocMatItem != null) {
            iPostProcessDocMatItem.execute(documentContent, docMatItemNode, offset);
        }
        docMatItemNode.setRefUUID(registeredProduct.getUuid());
        docMatItemNode.setRefMaterialSKUUUID(registeredProduct.getUuid());
        /*
         * [Step1] By default, for each registered product set
         */
        docMatItemNode.setAmount(1);
        docMatItemNode.setRefUnitUUID(materialStockKeepUnitManager.getMainUnitUUID(tempMaterialSKU));
        /*
         * [Step2] Calculate the price
         */
        docMatItemNode.setUnitPrice(registeredProduct.getUnitCost());
        StorageCoreUnit storageCoreUnit =
                new StorageCoreUnit(tempMaterialSKU.getUuid(), docMatItemNode.getRefUnitUUID(),
                        docMatItemNode.getAmount());
        try {
            double itemPrice = materialStockKeepUnitManager.calculatePrice(storageCoreUnit, tempMaterialSKU,
                    docMatItemNode.getUnitPrice());
            docMatItemNode.setItemPrice(itemPrice);
        } catch (MaterialException | ServiceEntityConfigureException e) {
            // just continue;
        }
    }

    public static interface IPostProcessDocMatItem {

        void execute(DocumentContent documentContent,
                     DocMatItemNode docMatItemNode, int offset) throws ServiceEntityConfigureException;

    }

    public static SerialIdInputBatchModel parseToSerialIdInputBatchModel(
            @RequestBody String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("serialIdInputModelList", SerialIdInputModel.class);
        classMap.put("serialIdList", String.class);
        return (SerialIdInputBatchModel) JSONObject
                .toBean(jsonObject, SerialIdInputBatchModel.class,
                        classMap);
    }

    /**
     * Pre-checks if any serial ID from the input list is already duplicated in existing registered products.
     * It returns a map of duplicate serial IDs found in the registered products.
     *
     * @param serialIdList A list of serial IDs to be checked against the registered products.
     * @param refTemplateSKUUUID The reference UUID of the template SKU used to query registered products.
     * @param rawDocItemUnionList A list of document-material item unions used for filtering the results,
     *                            expected to contain details on current products and related data.
     * @return A map where the keys are reference material SKU UUIDs and the values are lists of duplicated serial IDs;
     *         returns null if no duplicates are found or if the input data is unexpectedly empty.
     * @throws ServiceEntityConfigureException if there are issues configuring the service entity during the process.
     */
    public Map<String, List<String>> preCheckSerialIdDuplicate(List<String> serialIdList, String refTemplateSKUUUID,
                                                               List<DocMatItemMaterialUnion> rawDocItemUnionList) throws ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(rawDocItemUnionList)) {
            return null; // This scenario should not occur; indicates empty input list
        }
        String client = rawDocItemUnionList.get(0).getDocMatItemNode().getClient();
        ServiceBasicKeyStructure key1 =
                new ServiceBasicKeyStructure(refTemplateSKUUUID, IServiceEntityNodeFieldConstant.UUID);
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(serialIdList, RegisteredProduct.FEILD_SERIALID);
        List<ServiceEntityNode> existRegProductList =
                registeredProductManager.getEntityNodeListByKeyList(ServiceCollectionsHelper.asList(key1, key2),
                        RegisteredProduct.NODENAME, client, null,true, true);
        if (ServiceCollectionsHelper.checkNullList(existRegProductList)) {
            return null; // No registered products found with matching serial IDs
        }
        Map<String, List<String>> duplicateSerialIdMap = new HashMap<>();
        for (ServiceEntityNode existRegProduct : existRegProductList) {
            // Filters out products that already exist in the current document material items update batch
            List<DocMatItemMaterialUnion> filterResultList =
                    rawDocItemUnionList.stream().filter(docMatItemMaterialUnion ->
                            docMatItemMaterialUnion.getRegisteredProduct().getUuid().equals(existRegProduct.getUuid())).collect(Collectors.toList());
            // If not found in the current document material items update batch, then register as duplicate
            if (ServiceCollectionsHelper.checkNullList(filterResultList)) {
                RegisteredProduct registeredProduct = (RegisteredProduct) existRegProduct;
                ServiceCollectionsHelper.mergeToStringListMap(registeredProduct.getRefMaterialSKUUUID(), registeredProduct.getSerialId(), duplicateSerialIdMap);
            }
        }
        return duplicateSerialIdMap;
    }

    /**
     * Updates the list of serial IDs for registered products that are associated with the same template material SKU.
     * This method accommodates both newly registered products and existing ones that require a serial ID update.
     *
     * @param serialIdList A list containing the serial IDs to be updated.
     * @param docMatNodeItemList A list of ServiceEntityNode objects representing all relevant document material node items.
     * @param docItemUnionListMap A map that links specific keys to lists of DocMatItemMaterialUnion objects, detailing material associations.
     * @param refTemplateSKUUUID The UUID of the template material SKU that these products belong to.
     * @param logonUserUUID The UUID of the currently logged-in user performing the update.
     * @param organizationUUID The UUID of the organization under which this operation is being performed.
     *
     * @throws RegisteredProductException If an error occurs during the registration of product serial IDs.
     * @throws ServiceEntityConfigureException If a configuration error is encountered within the service entity.
     */
    public void updateSerialIdToRegProduct(List<String> serialIdList,
                                           List<ServiceEntityNode> docMatNodeItemList,
                                           Map<String, List<DocMatItemMaterialUnion>> docItemUnionListMap,
                                           String refTemplateSKUUUID,
                                           String logonUserUUID, String organizationUUID) throws RegisteredProductException, ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(docMatNodeItemList) || ServiceCollectionsHelper.checkNullList(serialIdList)) {
            return;
        }
        // [Step1] Optional step: Build a map of DocMatItemMaterialUnion lists grouped by key: refTemplate Material UUID
        if (docItemUnionListMap == null) {
            docItemUnionListMap =
                    this.buildDocItemUnionListMap(docMatNodeItemList, false);
        }
        List<DocMatItemMaterialUnion> rawDocItemUnionList = docItemUnionListMap.get(refTemplateSKUUUID);
        MaterialStockKeepUnit templateMaterialSKU = rawDocItemUnionList.get(0).getTemplateMaterialSKU();
        // [Step2] Pre-check if duplicate serial id input, raise error message if duplicate serial id identified.
        Map<String, List<String>> duplicateSerialIdMap = preCheckSerialIdDuplicate(serialIdList, refTemplateSKUUUID, rawDocItemUnionList);
        if (duplicateSerialIdMap != null && !ServiceCollectionsHelper.checkNullList(duplicateSerialIdMap.get(refTemplateSKUUUID))) {
            List<String> duplicateSerialIdList = duplicateSerialIdMap.get(refTemplateSKUUUID);
            String dupSerialId = ServiceCollectionsHelper.convStringListToString(duplicateSerialIdList, " ");
            throw new RegisteredProductException(RegisteredProductException.PARA2_DUPLICATE_SERIALID, templateMaterialSKU.getId(), dupSerialId);
        }
        // Build the update registered product matrix, for updating the serialId to registered products list
        UpdateDocItemMaterialMatrix updateDocItemMaterialMatrix =
                buildDocItemMaterialMatrixBySerialIdArray(rawDocItemUnionList, serialIdList);
        if (updateDocItemMaterialMatrix == null) {
            logger.error("updateDocItemMaterialMatrix is empty");
            throw new RegisteredProductException(RegisteredProductException.TYPE_SYSTEM_ERROR);
        }
        // the newly input serial id list
        List<String> newSerialIdList = updateDocItemMaterialMatrix.getNewSerialIdList();
        if (ServiceCollectionsHelper.checkNullList(newSerialIdList)) {
            return;
        }
        int serialIdLen = newSerialIdList.size();
        int emptyLen = 0;
        List<ServiceEntityNode> registeredProductList = new ArrayList<>();
        // Priority 1: insert new serial id into empty registered product
        if (!ServiceCollectionsHelper.checkNullList(updateDocItemMaterialMatrix.getEmptyList())) {
            for (int i = 0; i < updateDocItemMaterialMatrix.getEmptyList().size(); i++) {
                RegisteredProduct registeredProduct =
                        updateDocItemMaterialMatrix.getEmptyList().get(i).getRegisteredProduct();
                if (i >= serialIdLen) {
                    break;
                }
                registeredProduct.setSerialId(newSerialIdList.get(i));
                registeredProductList.add(registeredProduct);
            }
            emptyLen = updateDocItemMaterialMatrix.getEmptyList().size();
        }
        // Priority 2: update new serial id into to delete registered product
        if (!ServiceCollectionsHelper.checkNullList(updateDocItemMaterialMatrix.getToDeleteList())) {
            for (int i = 0; i < updateDocItemMaterialMatrix.getToDeleteList().size(); i++) {
                RegisteredProduct registeredProduct =
                        updateDocItemMaterialMatrix.getToDeleteList().get(i).getRegisteredProduct();
                if (i + emptyLen >= serialIdLen) {
                    break;
                }
                registeredProduct.setSerialId(newSerialIdList.get(i + emptyLen));
                registeredProductList.add(registeredProduct);
            }
        }
        registeredProductManager.updateSENodeList(registeredProductList, logonUserUUID, organizationUUID);
    }

    /**
     * Process of init SerialIdInputBatchModel from document
     *
     * @param baseUUID
     * @param docMatItemList
     * @return
     * @throws ServiceEntityConfigureException
     */
    public SerialIdInputBatchModel initSerialInputBatchModel(String baseUUID,
                                                             String client, List<ServiceEntityNode> docMatItemList) throws ServiceEntityConfigureException {
        Map<String, List<DocMatItemMaterialUnion>> buildDocItemUnionListMap =
                buildDocItemUnionListMap(docMatItemList,
                        false);
        if(buildDocItemUnionListMap == null){
            return null;
        }
        SerialIdInputBatchModel serialIdInputBatchModel = new SerialIdInputBatchModel();
        serialIdInputBatchModel.setBaseUUID(baseUUID);
        List<SerialIdInputModel> serialIdInputModelList = new ArrayList<>();
        Set<String> keySet = buildDocItemUnionListMap.keySet();
        for (String key : keySet) {
            SerialIdInputModel serialIdInputModel = new SerialIdInputModel();
            serialIdInputModel.setRefTemplateMaterialSKUUUID(key);
            List<DocMatItemMaterialUnion> docMatItemMaterialUnionList =
                    buildDocItemUnionListMap.get(key);
            if (ServiceCollectionsHelper.checkNullList(docMatItemMaterialUnionList)) {
                continue;
            }
            MaterialStockKeepUnit templateMaterialSKU = docMatItemMaterialUnionList.get(0).getTemplateMaterialSKU();
            List<String> serialIdList = docMatItemMaterialUnionList.stream().filter(docMatItemMaterialUnion -> {
                return docMatItemMaterialUnion.getRegisteredProduct() != null;
            }).map(docMatItemMaterialUnion -> {
                return docMatItemMaterialUnion.getRegisteredProduct().getSerialId();
            }).collect(Collectors.toList());
            // calculate the amount
            double grossAmount = ServiceCollectionsHelper.checkNullList(serialIdList) ? 0 : serialIdList.size();
            List<String> newSerialIdList = new ArrayList<>();
            // Filter out empty serial id list
            newSerialIdList = serialIdList.stream().filter(serialId -> {
                return !ServiceEntityStringHelper.checkNullString(serialId);
            }).collect(Collectors.toList());
            serialIdInputModel.setGrossAmount(grossAmount);
            String amountLabel = null;
            try {
                amountLabel = materialStockKeepUnitManager.getAmountLabel(key,
                        materialStockKeepUnitManager.getMainUnitUUID(templateMaterialSKU), grossAmount, client
                );
                serialIdInputModel.setGrossAmountLabel(amountLabel);
            } catch (MaterialException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            }
            serialIdInputModel.setRefTemplateMaterialSKU(templateMaterialSKU);
            serialIdInputModel.setSerialIdList(newSerialIdList);
            serialIdInputModelList.add(serialIdInputModel);
        }
        serialIdInputBatchModel.setSerialIdInputModelList(serialIdInputModelList);
        return serialIdInputBatchModel;
    }

    public class DocMatItemMaterialUnion {

        private DocMatItemNode docMatItemNode;

        private RegisteredProduct registeredProduct;

        private MaterialStockKeepUnit templateMaterialSKU;

        public DocMatItemMaterialUnion() {
        }

        public DocMatItemMaterialUnion(DocMatItemNode docMatItemNode, RegisteredProduct registeredProduct,
                                       MaterialStockKeepUnit templateMaterialSKU) {
            this.docMatItemNode = docMatItemNode;
            this.registeredProduct = registeredProduct;
            this.templateMaterialSKU = templateMaterialSKU;
        }

        public DocMatItemNode getDocMatItemNode() {
            return docMatItemNode;
        }

        public void setDocMatItemNode(DocMatItemNode docMatItemNode) {
            this.docMatItemNode = docMatItemNode;
        }

        public RegisteredProduct getRegisteredProduct() {
            return registeredProduct;
        }

        public void setRegisteredProduct(RegisteredProduct registeredProduct) {
            this.registeredProduct = registeredProduct;
        }

        public MaterialStockKeepUnit getTemplateMaterialSKU() {
            return templateMaterialSKU;
        }

        public void setTemplateMaterialSKU(MaterialStockKeepUnit templateMaterialSKU) {
            this.templateMaterialSKU = templateMaterialSKU;
        }
    }

    public static class EditSerialIdUnion {

        protected int editSerialIdFlag;

        protected int emptySerialIdFlag;

        public EditSerialIdUnion() {
        }

        public EditSerialIdUnion(int editSerialIdFlag, int emptySerialIdFlag) {
            this.editSerialIdFlag = editSerialIdFlag;
            this.emptySerialIdFlag = emptySerialIdFlag;
        }

        public int getEditSerialIdFlag() {
            return editSerialIdFlag;
        }

        public void setEditSerialIdFlag(int editSerialIdFlag) {
            this.editSerialIdFlag = editSerialIdFlag;
        }

        public int getEmptySerialIdFlag() {
            return emptySerialIdFlag;
        }

        public void setEmptySerialIdFlag(int emptySerialIdFlag) {
            this.emptySerialIdFlag = emptySerialIdFlag;
        }
    }

    public EditSerialIdUnion setEditSerialIdFlagWrapper(List<ServiceEntityNode> refMaterialSKUList,
                                                        String refMaterialSKUUUID,
                                                        Map<String, List<DocMatItemMaterialUnion>> emptyItemUnionListMap) {
        MaterialStockKeepUnit materialStockKeepUnit =
                (MaterialStockKeepUnit) ServiceCollectionsHelper.filterOnline(refMaterialSKUList,
                        seNode -> {
                            return seNode.getUuid().equals(refMaterialSKUUUID);
                        });
        if (materialStockKeepUnit != null) {
            return setEditSerialIdFlagCore(materialStockKeepUnit, emptyItemUnionListMap);
        } else {
            return new EditSerialIdUnion(StandardSwitchProxy.SWITCH_OFF, StandardSwitchProxy.SWITCH_OFF);
        }
    }

    public void updateSerialIdMetaToDocumentWrapper(List<? extends DocMatItemUIModel> docMatItemUIModelList, List<ServiceEntityNode> docMatItemList)
            throws ServiceEntityConfigureException {
        if(!ServiceCollectionsHelper.checkNullList(docMatItemUIModelList) && !ServiceCollectionsHelper.checkNullList(docMatItemList)){
            List<String> refMaterialSKUUUIDList =
                    docMatItemUIModelList.stream().map(DocMatItemUIModel::getRefMaterialSKUUUID).collect(Collectors.toList());
            List<ServiceEntityNode> refMaterialSKUList =
                    materialStockKeepUnitManager.getEntityNodeListByMultipleKey(refMaterialSKUUUIDList,
                            IServiceEntityNodeFieldConstant.UUID, MaterialStockKeepUnit.NODENAME,
                            docMatItemList.get(0).getClient(), null);
            Map<String, List<SerialIdDocumentProxy.DocMatItemMaterialUnion>> buildDocItemUnionListMap =
                    buildDocItemUnionListMap( docMatItemList, true);
            for(DocMatItemUIModel docMatItemUIModel: docMatItemUIModelList){
                updateSerialIdMetaDocItemUIModel(docMatItemUIModel, refMaterialSKUList, buildDocItemUnionListMap);
            }
        }
    }

    public void updateSerialIdMetaDocItemUIModel(DocMatItemUIModel docMatItemUIModel,
                                                 List<ServiceEntityNode> refMaterialSKUList, Map<String, List<SerialIdDocumentProxy.DocMatItemMaterialUnion>> emptyItemUnionListMap) {
        // set editSerialId & emptySerialId
        if (ServiceCollectionsHelper.checkNullList(refMaterialSKUList)) {
            return;
        }
        SerialIdDocumentProxy.EditSerialIdUnion editSerialIdUnion =
                setEditSerialIdFlagWrapper(refMaterialSKUList,
                        docMatItemUIModel.getRefMaterialSKUUUID(), emptyItemUnionListMap);
        docMatItemUIModel.setEditSerialIdFlag(editSerialIdUnion.getEditSerialIdFlag());
        docMatItemUIModel.setEmptySerialIdFlag(editSerialIdUnion.getEmptySerialIdFlag());
    }

    /**
     * Core Logic to set serial id edit flag union by checking material SKU instance
     *
     * @param materialStockKeepUnit
     * @return
     */
    public EditSerialIdUnion setEditSerialIdFlagCore(MaterialStockKeepUnit materialStockKeepUnit,
                                                     Map<String, List<DocMatItemMaterialUnion>> emptyItemUnionListMap) {
        if (RegisteredProductManager.checkRegisteredProduct(materialStockKeepUnit)) {
            // In case registered Product
            RegisteredProduct registeredProduct = (RegisteredProduct) materialStockKeepUnit;
            int emptySerialIdFlag = registeredProductManager.checkEmptyRegisteredProduct(materialStockKeepUnit) ?
                    StandardSwitchProxy.SWITCH_ON : StandardSwitchProxy.SWITCH_OFF;
            return new EditSerialIdUnion(StandardSwitchProxy.SWITCH_ON, emptySerialIdFlag);
        }
        if (materialStockKeepUnit.getTraceMode() == MaterialStockKeepUnit.TRACEMODE_SINGLE) {
            if (emptyItemUnionListMap != null) {
                List<DocMatItemMaterialUnion> rawDocItemUnionList =
                        emptyItemUnionListMap.get(materialStockKeepUnit.getUuid());
                int emptySerialIdFlag = StandardSwitchProxy.SWITCH_OFF;
                if (ServiceCollectionsHelper.checkNullList(rawDocItemUnionList)) {
                    // should raise exception
                    emptySerialIdFlag = StandardSwitchProxy.SWITCH_ON;
                }
                return new EditSerialIdUnion(StandardSwitchProxy.SWITCH_ON, emptySerialIdFlag);
            } else {
                return new EditSerialIdUnion(StandardSwitchProxy.SWITCH_ON, StandardSwitchProxy.SWITCH_OFF);
            }
        } else {
            return new EditSerialIdUnion(StandardSwitchProxy.SWITCH_OFF, StandardSwitchProxy.SWITCH_OFF);
        }
    }

    /**
     * Constructs a map of lists containing <code>DocMatItemMaterialUnion</code> objects from a given list of
     * raw document material items. Items are skipped if the relative material are not in 'Single trace' mode.
     *
     * @param docMatNodeItemList A list of <code>ServiceEntityNode</code> instances representing the document material items to process.
     * @param filterEmpty A boolean flag indicating whether to filter out empty entries from the resulting map.
     *
     * @return A map where the key is a <code>String</code> represents the `refTemplateSKUUUID` and the value is a list of
     * <code>DocMatItemMaterialUnion</code> objects. Each <code>DocMatItemMaterialUnion</code> contains:
     * - <code>DocMatItemNode</code>: The document material item instance.
     * - <code>RegisteredProduct</code>: The registered product contained within the document material item.
     * - <code>MaterialStockKeepUnit</code>: The template material SKU that the registered product belongs to.
     *
     * @throws ServiceEntityConfigureException If there is an error configuring or processing the service entities.
     */
    public Map<String, List<DocMatItemMaterialUnion>> buildDocItemUnionListMap(List<ServiceEntityNode> docMatNodeItemList, boolean filterEmpty) throws ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(docMatNodeItemList)) {
            return null;
        }
        List<String> refMaterialSKUUUIDList = DocFlowProxy.pluckToRefMaterialSKUUUIDList(docMatNodeItemList);
        List<ServiceEntityNode> refMaterialSKUList =
                materialStockKeepUnitManager.getEntityNodeListByMultipleKey(refMaterialSKUUUIDList,
                        IServiceEntityNodeFieldConstant.UUID, MaterialStockKeepUnit.NODENAME,
                        docMatNodeItemList.get(0).getClient(), null);
        if (ServiceCollectionsHelper.checkNullList(refMaterialSKUList)) {
            return null;
        }
        Map<String, List<DocMatItemMaterialUnion>> resultMap = new HashMap<>();
        for (ServiceEntityNode serviceEntityNode : refMaterialSKUList) {
            // Step 2: Check the registered product with empty serial id or not .
            MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) serviceEntityNode;
            if (!RegisteredProductManager.checkRegisteredProduct(materialStockKeepUnit)) {
                // skip if current material is not registered product
                continue;
            }
            RegisteredProduct registeredProduct = (RegisteredProduct) materialStockKeepUnit;
            if (filterEmpty) {
                if (!registeredProductManager.checkEmptyRegisteredProduct(registeredProduct)) {
                    continue;
                }
            }
            DocMatItemNode docMatItemNode = (DocMatItemNode) ServiceCollectionsHelper.filterOnline(docMatNodeItemList,
                    seNode -> {
                        DocMatItemNode tempDocMatItem = (DocMatItemNode) seNode;
                        return registeredProduct.getUuid().equals(tempDocMatItem.getRefMaterialSKUUUID());
                    });
            // Step 3: Get the template material SKU
            String refTemplateMaterialUUID = registeredProduct.getRefMaterialSKUUUID();
            MaterialStockKeepUnit templateMaterialSKU;
            if (resultMap.containsKey(refTemplateMaterialUUID)) {
                List<DocMatItemMaterialUnion> tempDocItemMaterialUnionList = resultMap.get(refTemplateMaterialUUID);
                templateMaterialSKU = tempDocItemMaterialUnionList.get(0).getTemplateMaterialSKU();
            } else {
                templateMaterialSKU =
                        materialStockKeepUnitManager.getRefTemplateMaterialSKU(registeredProduct);
            }
            // Step 4: build in the map
            DocMatItemMaterialUnion docMatItemMaterialUnion = new DocMatItemMaterialUnion(docMatItemNode,
                    registeredProduct, templateMaterialSKU);
            if (resultMap.containsKey(refTemplateMaterialUUID)) {
                List<DocMatItemMaterialUnion> tempDocItemMaterialUnionList = resultMap.get(refTemplateMaterialUUID);
                tempDocItemMaterialUnionList.add(docMatItemMaterialUnion);
            } else {
                resultMap.put(refTemplateMaterialUUID, ServiceCollectionsHelper.asList(docMatItemMaterialUnion));
            }
        }
        return resultMap;
    }

    public static class UpdateDocItemMaterialMatrix {

        private List<DocMatItemMaterialUnion> keepList;

        private List<DocMatItemMaterialUnion> toDeleteList;

        private List<DocMatItemMaterialUnion> emptyList;

        private List<String> newSerialIdList;

        public UpdateDocItemMaterialMatrix() {
        }

        public UpdateDocItemMaterialMatrix(List<DocMatItemMaterialUnion> keepList,
                                           List<DocMatItemMaterialUnion> toDeleteList,
                                           List<DocMatItemMaterialUnion> emptyList, List<String> newSerialIdList) {
            this.keepList = keepList;
            this.toDeleteList = toDeleteList;
            this.emptyList = emptyList;
            this.newSerialIdList = newSerialIdList;
        }

        public List<DocMatItemMaterialUnion> getKeepList() {
            return keepList;
        }

        public void setKeepList(List<DocMatItemMaterialUnion> keepList) {
            this.keepList = keepList;
        }

        public List<DocMatItemMaterialUnion> getToDeleteList() {
            return toDeleteList;
        }

        public void setToDeleteList(List<DocMatItemMaterialUnion> toDeleteList) {
            this.toDeleteList = toDeleteList;
        }

        public List<DocMatItemMaterialUnion> getEmptyList() {
            return emptyList;
        }

        public void setEmptyList(List<DocMatItemMaterialUnion> emptyList) {
            this.emptyList = emptyList;
        }

        public List<String> getNewSerialIdList() {
            return newSerialIdList;
        }

        public void setNewSerialIdList(List<String> newSerialIdList) {
            this.newSerialIdList = newSerialIdList;
        }
    }

    /**
     * @param rawDocMaterialIUnionList
     * @param serialIdList:            newly input serial id list
     * @return
     */
    public UpdateDocItemMaterialMatrix buildDocItemMaterialMatrixBySerialIdArray(List<DocMatItemMaterialUnion> rawDocMaterialIUnionList, List<String> serialIdList) {
        if (ServiceCollectionsHelper.checkNullList(rawDocMaterialIUnionList)) {
            return null;
        }
        if (ServiceCollectionsHelper.checkNullList(serialIdList)) {
            return null;
        }
        List<DocMatItemMaterialUnion> keepList = new ArrayList<>();
        List<DocMatItemMaterialUnion> toDeleteList = new ArrayList<>();
        List<DocMatItemMaterialUnion> emptyList = new ArrayList<>();
        List<String> keepSerialIdList = new ArrayList<>();
        for (DocMatItemMaterialUnion docMatItemMaterialUnion : rawDocMaterialIUnionList) {
            RegisteredProduct registeredProduct = docMatItemMaterialUnion.getRegisteredProduct();
            // Add to `emptyList` in case the registerProduct serial id is empty
            if (registeredProductManager.checkEmptyRegisteredProduct(registeredProduct)) {
                emptyList.add(docMatItemMaterialUnion);
                continue;
            }
            String serialIdExisted = ServiceCollectionsHelper.filterOnline(serialIdList,
                    tmpSerialId -> tmpSerialId.equals(registeredProduct.getSerialId()));
            if (!ServiceEntityStringHelper.checkNullString(serialIdExisted)) {
                ServiceCollectionsHelper.mergeToList(keepSerialIdList, serialIdExisted);
                keepList.add(docMatItemMaterialUnion);
            } else {
                toDeleteList.add(docMatItemMaterialUnion);
            }
        }
        serialIdList.removeAll(keepSerialIdList);
        return new UpdateDocItemMaterialMatrix(keepList, toDeleteList, emptyList, serialIdList);
    }

}
