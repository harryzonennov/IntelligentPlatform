package com.company.IntelligentPlatform.common.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.service.ServiceItemIdGenerator;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;


@Service
public class SplitMatItemProxy {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected ServiceItemIdGenerator serviceItemIdGenerator;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger logger = LoggerFactory.getLogger(SplitMatItemProxy.class);

    /**
     * @param docMatItemNode
     * @return
     */
    //TODO check usage
    public static SplitMatItemModel initSplitMatModel(DocMatItemNode docMatItemNode) {
        SplitMatItemModel splitMatItemModel = new SplitMatItemModel();
        splitMatItemModel.setItemUUID(docMatItemNode.getUuid());
        splitMatItemModel.setRefItemId(docMatItemNode.getId());
        splitMatItemModel.setRefMaterialSKUUUID(docMatItemNode.getRefMaterialSKUUUID());
        splitMatItemModel.setAllAmount(docMatItemNode.getAmount());
        splitMatItemModel.setAllUnitUUID(docMatItemNode.getRefUnitUUID());
        splitMatItemModel.setLeftAmount(docMatItemNode.getAmount());
        splitMatItemModel.setLeftUnitUUID(docMatItemNode.getRefUnitUUID());
        splitMatItemModel.setSplitAmount(0);
        splitMatItemModel.setSplitUnitUUID(docMatItemNode.getRefUnitUUID());
        return splitMatItemModel;
    }

    private StorageCoreUnit getDocMatItemStorageRequest(DocMatItemNode docMatItemNode) {
        StorageCoreUnit storageCoreUnit = new StorageCoreUnit();
        storageCoreUnit.setAmount(docMatItemNode.getAmount());
        storageCoreUnit.setRefUnitUUID(docMatItemNode.getRefUnitUUID());
        storageCoreUnit.setRefMaterialSKUUUID(docMatItemNode.getRefMaterialSKUUUID());
        return storageCoreUnit;
    }

    private StorageCoreUnit getSplitStorageRequest(SplitMatItemModel splitMatItemModel) {
        StorageCoreUnit splitRequest = new StorageCoreUnit();
        splitRequest.setAmount(splitMatItemModel.getSplitAmount());
        splitRequest.setRefUnitUUID(splitMatItemModel.getSplitUnitUUID());
        splitRequest.setRefMaterialSKUUUID(splitMatItemModel.getRefMaterialSKUUUID());
        return splitRequest;
    }

    /**
     * Calculate differential amount by plan amount from production order and
     * already finished amount.
     *
     * @return
     * @throws MaterialException
     * @throws ServiceEntityConfigureException
     */
    public StorageCoreUnit calculateLeftAmount(SplitMatItemModel splitMatItemModel, DocMatItemNode docMatItemNode)
            throws MaterialException, ServiceEntityConfigureException, SplitMatItemException {
        StorageCoreUnit storageCoreUnit = getDocMatItemStorageRequest(docMatItemNode);
        StorageCoreUnit splitRequest = getSplitStorageRequest(splitMatItemModel);
        if (splitRequest.getAmount() < 0) {
            throw new SplitMatItemException(SplitMatItemException.PARA_SPLIT_NOMINUS, splitRequest.getAmount());
        }
        splitRequest.setRefMaterialSKUUUID(docMatItemNode.getRefMaterialSKUUUID());
        StorageCoreUnit resultUnit = materialStockKeepUnitManager
                .mergeStorageUnitCore(storageCoreUnit, splitRequest, StorageCoreUnit.OPERATOR_MINUS,
                        docMatItemNode.getClient());
        if (resultUnit.getAmount() < 0) {
            String labelAmount = materialStockKeepUnitManager
                    .getAmountLabel(splitRequest.getRefMaterialSKUUUID(), splitRequest.getRefUnitUUID(),
                            splitRequest.getAmount(),
                            docMatItemNode.getClient());
            throw new SplitMatItemException(SplitMatItemException.PARA_SPLIT_OVERAMOUNT, labelAmount);
        }
        return resultUnit;
    }

    public SplitResult splitDefMatItemService(SplitMatItemModel splitMatItemModel, DocMatItemNode docMatItemNode)
            throws MaterialException, SplitMatItemException, ServiceEntityConfigureException {
        /*
         * [Step1] Calculate the left amount and update to existed item
         */
        List<ServiceEntityNode> resultList = new ArrayList<>();
        StorageCoreUnit storageCoreUnit = getDocMatItemStorageRequest(docMatItemNode);
        StorageCoreUnit splitRequest = getSplitStorageRequest(splitMatItemModel);
        splitRequest.setRefMaterialSKUUUID(docMatItemNode.getRefMaterialSKUUUID());
        storageCoreUnit.setRefMaterialSKUUUID(docMatItemNode.getRefMaterialSKUUUID());
        StorageCoreUnit leftUnit = calculateLeftAmount(splitMatItemModel, docMatItemNode);
        docMatItemNode.setAmount(leftUnit.getAmount());
        docMatItemNode.setRefUnitUUID(leftUnit.getRefUnitUUID());
        // calculate default item price from left item
        docFlowProxy.calculateDefItemPrice(docMatItemNode);
        resultList.add(docMatItemNode);
        String idPrefix = docMatItemNode.getId();

        double leftRatio = materialStockKeepUnitManager.getStorageUnitRatio(
                leftUnit, storageCoreUnit, docMatItemNode.getClient());
        double splitRatio = materialStockKeepUnitManager.getStorageUnitRatio(
                splitRequest, storageCoreUnit, docMatItemNode.getClient());
        /*
         * [Step2] New Item , generate new item id, set split amount
         */
        DocMatItemNode newItem = (DocMatItemNode) docMatItemNode.clone();
        newItem.setUuid(UUID.randomUUID().toString());
        newItem.setAmount(splitMatItemModel.getSplitAmount());
        try {
            newItem.setId(serviceItemIdGenerator
                    .genItemIdParentUUIDWithPost(docMatItemNode.getNodeName(), IServiceEntityNodeFieldConstant.ID,
                            idPrefix,
                            docMatItemNode.getParentNodeUUID(), 0, docMatItemNode.getClient()));
        } catch (SearchConfigureException e) {
            // just ignore
        }
        newItem.setRefUnitUUID(splitMatItemModel.getSplitUnitUUID());
        resultList.add(newItem);
        // calculate default item price from splited item
        docFlowProxy.calculateDefItemPrice(newItem);
        SplitResult splitResult = new SplitResult();
        splitResult.setMergeResult(resultList);
        splitResult.setLeftMatItemNode(docMatItemNode);
        splitResult.setSplitMatItemNode(newItem);
        splitResult.setLeftRatio(leftRatio);
        splitResult.setSplitRatio(splitRatio);
        return splitResult;

    }

    public class SplitResult {

        protected double splitRatio;

        protected double leftRatio;

        protected DocMatItemNode splitMatItemNode;

        protected DocMatItemNode leftMatItemNode;

        protected List<ServiceEntityNode> mergeResult;

        public double getSplitRatio() {
            return splitRatio;
        }

        public void setSplitRatio(final double splitRatio) {
            this.splitRatio = splitRatio;
        }

        public double getLeftRatio() {
            return leftRatio;
        }

        public void setLeftRatio(final double leftRatio) {
            this.leftRatio = leftRatio;
        }

        public DocMatItemNode getSplitMatItemNode() {
            return splitMatItemNode;
        }

        public void setSplitMatItemNode(final DocMatItemNode splitMatItemNode) {
            this.splitMatItemNode = splitMatItemNode;
        }

        public DocMatItemNode getLeftMatItemNode() {
            return leftMatItemNode;
        }

        public void setLeftMatItemNode(final DocMatItemNode leftMatItemNode) {
            this.leftMatItemNode = leftMatItemNode;
        }

        public List<ServiceEntityNode> getMergeResult() {
            return mergeResult;
        }

        public void setMergeResult(final List<ServiceEntityNode> mergeResult) {
            this.mergeResult = mergeResult;
        }
    }


    public SplitMatItemModel initSplitModel(DocMatItemNode docMatItemNode, LogonInfo logonInfo)
            throws ServiceModuleProxyException, ServiceEntityConfigureException, MaterialException {
        SplitMatItemModel splitMatItemModel = new SplitMatItemModel();
        splitMatItemModel.setItemUUID(docMatItemNode.getUuid());
        splitMatItemModel.setRefItemId(docMatItemNode.getId());
        splitMatItemModel.setRefMaterialSKUUUID(docMatItemNode.getRefMaterialSKUUUID());
        splitMatItemModel.setAllAmount(docMatItemNode.getAmount());
        splitMatItemModel.setAllUnitUUID(docMatItemNode.getRefUnitUUID());
        splitMatItemModel.setLeftAmount(docMatItemNode.getAmount());
        splitMatItemModel.setLeftUnitUUID(docMatItemNode.getRefUnitUUID());
        splitMatItemModel.setSplitAmount(0);
        splitMatItemModel.setSplitUnitUUID(docMatItemNode.getRefUnitUUID());

        // Init Material information
        MaterialStockKeepUnit materialStockKeepUnit = docFlowProxy.getMaterialSKUFromDocMatItem(docMatItemNode);
        splitMatItemModel.setRefMaterialSKUId(materialStockKeepUnit.getId());
        splitMatItemModel.setRefMaterialSKUName(materialStockKeepUnit.getName());

        String unitName = materialStockKeepUnitManager
                .getRefUnitName(materialStockKeepUnit.getUuid(), docMatItemNode.getRefUnitUUID(),
                        docMatItemNode.getClient());
        splitMatItemModel.setAllUnitName(unitName);
        return splitMatItemModel;
    }

    /**
     * Provide default logic to check if current doc material item is split enable
     *
     * @param docMatItemNode
     * @return
     * @throws MaterialException
     * @throws ServiceEntityConfigureException
     */
    public int calculateSplitFlag(
            DocMatItemNode docMatItemNode)
            throws MaterialException, ServiceEntityConfigureException {
        String refUnitUUID = docMatItemNode.getRefUnitUUID();
        StorageCoreUnit requestSplitUnit = new StorageCoreUnit(
                docMatItemNode.getRefMaterialSKUUUID(),
                refUnitUUID, docMatItemNode.getAmount());
        return materialStockKeepUnitManager.calculateSplitFlag(
                requestSplitUnit, docMatItemNode.getClient());
    }


    /**
     * Splits a document material items list based on their relative material SKUs.
     * This method processes a list of document material items (`rawDocMatItemList`) and checks each item to determine
     * its material category. If an item is associated with a "Single-trace" mode template material SKU, the method splits the item into multiple items,
     * each pointing to a newly created registered product instance. Items that are not associated with a "Single-trace" mode template are left unchanged.
     *
     *
     * @param rawDocMatItemList The list of document material items to be processed.
     * @param involvePartyMap A map indicating involved parties for creating registered product instance.
     * @param logonUserUUID The UUID of the user who is currently logged in.
     * @param organizationUUID The UUID of the organization under which the operation is performed.
     * @return A list of `ServiceEntityNode` objects representing the processed and potentially split document material items.
     * @throws ServiceEntityConfigureException If there is a configuration error encountered while processing.
     * @throws MaterialException If there is an error related to material processing.
     */
    public List<ServiceEntityNode> splitDocMatForMaterial(List<ServiceEntityNode> rawDocMatItemList, Map<Integer,
            Account> involvePartyMap, String logonUserUUID
            , String organizationUUID) throws ServiceEntityConfigureException, MaterialException {
        if (ServiceCollectionsHelper.checkNullList(rawDocMatItemList)) {
            return null;
        }
        List<String> refMaterialSKUUUIDList = DocFlowProxy.pluckToRefMaterialSKUUUIDList(rawDocMatItemList);
        if (ServiceCollectionsHelper.checkNullList(rawDocMatItemList)) {
            return rawDocMatItemList;
        }
        List<ServiceEntityNode> refMaterialSKUList =
                materialStockKeepUnitManager.getEntityNodeListByMultipleKey(refMaterialSKUUUIDList,
                        IServiceEntityNodeFieldConstant.UUID, MaterialStockKeepUnit.NODENAME,
                        rawDocMatItemList.get(0).getClient(), null);
        List<ServiceEntityNode> resultList = new ArrayList<>();
        for (ServiceEntityNode seNode : rawDocMatItemList) {
            DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
            MaterialStockKeepUnit materialStockKeepUnit =
                    (MaterialStockKeepUnit) ServiceCollectionsHelper.filterOnline(refMaterialSKUList,
                            materialSENode -> {
                                return materialSENode.getUuid().equals(docMatItemNode.getRefMaterialSKUUUID());
                            });
            if (materialStockKeepUnit == null || RegisteredProductManager.checkRegisteredProduct(materialStockKeepUnit)
                    || materialStockKeepUnit.getTraceMode() != MaterialStockKeepUnit.TRACEMODE_SINGLE) {
                resultList.add(docMatItemNode);
                continue;
            }
            List<ServiceEntityNode> tempSplitDocMatList = splitDocMatForMaterial(docMatItemNode,
                    materialStockKeepUnit, involvePartyMap, logonUserUUID, organizationUUID);
            if (!ServiceCollectionsHelper.checkNullList(tempSplitDocMatList)) {
                ServiceCollectionsHelper.mergeToList(resultList, tempSplitDocMatList);
            }
        }
        return resultList;
    }


    /**
     * In case DocMaterial Item pointing to material with [Single trace] mode and need to split to list of registered
     * product instance ,
     * During this process, multiple 'empty' registered product will be generated.
     *
     * @param docMatItemNode      : origin reference DocMaterial Item instance, will be split.
     * @param involvePartyMap A map indicating involved parties for creating registered product instance.
     * @param templateMaterialSKU : Template material SKU, should be [single trace] mode
     */
    public List<ServiceEntityNode> splitDocMatForMaterial(DocMatItemNode docMatItemNode,
                                                          MaterialStockKeepUnit templateMaterialSKU, Map<Integer,
            Account> involvePartyMap, String logonUserUUID, String organizationUUID) throws ServiceEntityConfigureException, MaterialException {
        /*
         * [Step1] Pre-check & Calculate split amount
         */
        if (templateMaterialSKU.getTraceMode() != MaterialStockKeepUnit.TRACEMODE_SINGLE) {
            return ServiceCollectionsHelper.asList(docMatItemNode);
        }
        int totalStep = 0, index = 0, step = 1;
        String standardRefUnit = materialStockKeepUnitManager.getMainUnitUUID(templateMaterialSKU);
        StorageCoreUnit requestCoreUnit = new StorageCoreUnit(docMatItemNode.getRefMaterialSKUUUID(),
                docMatItemNode.getRefUnitUUID(), docMatItemNode.getAmount());
        double totalAmount = materialStockKeepUnitManager.ratioToMainUnit(requestCoreUnit, templateMaterialSKU);
        if (totalAmount <= 1) {
            return ServiceCollectionsHelper.asList(docMatItemNode);
        }
        /*
         * [Step2]  1st Item: Update reference doc material item amount itself.
         */
        totalStep = step; // reset initial value to skip first item
        docMatItemNode.setAmount(step);
        docMatItemNode.setRefUnitUUID(standardRefUnit);
        // Since standardRefUnit
        docMatItemNode.setItemPrice(step * docMatItemNode.getUnitPrice());
        docMatItemNode.setItemPriceDisplay(step * docMatItemNode.getUnitPriceDisplay());
        // Create new registered product instances list
        List<ServiceEntityNode> registeredProduct0List =
                registeredProductManager.newRegisteredProductCore(templateMaterialSKU, docMatItemNode,
                        null, involvePartyMap);
        RegisteredProduct registeredProduct0 =
                RegisteredProductManager.filterOutRegisteredProduct(registeredProduct0List);
        docMatItemNode.setRefMaterialSKUUUID(registeredProduct0.getUuid());
        List<ServiceEntityNode> resultDocMatItemList = ServiceCollectionsHelper.asList(docMatItemNode);
        List<ServiceEntityNode> registeredProductList = new ArrayList<>(registeredProduct0List);
        while (totalStep < totalAmount) {
            totalStep += step;
            /*
             * [Step3] new instance doc mat item & new empty registered product
             */
            DocMatItemNode newMatItem = docFlowProxy.duplicateDocMatItemNode(docMatItemNode);
            newMatItem.setRefUnitUUID(standardRefUnit);
            newMatItem.setAmount(step);
            newMatItem.setItemPrice(step * docMatItemNode.getUnitPrice());
            newMatItem.setItemPriceDisplay(step * docMatItemNode.getUnitPriceDisplay());
            resultDocMatItemList.add(newMatItem);
            List<ServiceEntityNode> addRegisteredProductList =
                    registeredProductManager.newRegisteredProductCore(templateMaterialSKU, newMatItem, null,
                            involvePartyMap);
            RegisteredProduct registeredProduct =
                    RegisteredProductManager.filterOutRegisteredProduct(addRegisteredProductList);
            newMatItem.setRefMaterialSKUUUID(registeredProduct.getUuid());
            registeredProductList.addAll(addRegisteredProductList);
        }
        /*
         * [Step4] update registered product into DB
         */
        if (!ServiceCollectionsHelper.checkNullList(registeredProductList)) {
            registeredProductManager.updateSENodeList(registeredProductList, logonUserUUID, organizationUUID);
        }
        return resultDocMatItemList;
    }

    /**
     * //TODO read system configuration in the future
     * Check Weather it is mandatory to split Registered product instance
     *
     * @return
     */
    public int getSplitRegProdMandatory(String client) {
        return StandardSwitchProxy.SWITCH_ON;
    }


}
