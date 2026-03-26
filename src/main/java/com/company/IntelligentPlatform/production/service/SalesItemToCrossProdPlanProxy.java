package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.production.model.ProdPlanTargetMatItem;
import com.company.IntelligentPlatform.production.model.ProductionPlan;
import com.company.IntelligentPlatform.sales.dto.SalesContractServiceUIModelExtension;
import com.company.IntelligentPlatform.sales.dto.SalesForcastServiceUIModelExtension;
import com.company.IntelligentPlatform.sales.service.*;
import com.company.IntelligentPlatform.sales.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.PrevNextDocItemProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SalesItemToCrossProdPlanProxy {

    @Autowired
    protected SalesContractManager salesContractManager;

    @Autowired
    protected SalesContractActionExecutionProxy salesContractActionExecutionProxy;

    @Autowired
    protected SalesForcastActionExecutionProxy salesForcastActionExecutionProxy;

    @Autowired
    protected SalesForcastManager salesForcastManager;

    @Autowired
    protected ProductionPlanManager productionPlanManager;

    @Autowired
    protected WarehouseManager warehouseManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected DocBatchConvertProxy docBatchConvertProxy;

    @Autowired
    protected ServiceModuleProxy serviceModuleProxy;

    @Autowired
    protected SalesContractServiceUIModelExtension salesContractServiceUIModelExtension;

    @Autowired
    protected SalesForcastServiceUIModelExtension salesForcastServiceUIModelExtension;

    @Autowired
    protected ProductionPlanActionExecutionProxy productionPlanActionExecutionProxy;

    /**
     * Main entrance to create direct quality inspect order & in-bound items
     * based on production target and material item list
     *
     * @param refMaterialSKUList : online reference material SKU list, should be quality check
     *                           [OFF] materials
     * @param genRequest
     * @throws ServiceEntityConfigureException
     * @throws SearchConfigureException
     * @throws NodeNotFoundException
     * @throws ServiceEntityInstallationException
     * @throws ServiceModuleProxyException
     */
    public void createProductionPlanBatch(
            List<ServiceEntityNode> allSalesMaterialItemList,
            List<ServiceEntityNode> salesMaterialItemList,
            List<ServiceEntityNode> refMaterialSKUList,
            DeliveryMatItemBatchGenRequest genRequest, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, SalesContractException,
            SalesForcastException, DocActionException {
        /*
         * [Step1] found the existed quality check data: check order and existed
         * reference check material item
         */
        //TODO
        if (ServiceCollectionsHelper.checkNullList(refMaterialSKUList)) {
            return;
        }
        if (ServiceCollectionsHelper.checkNullList(salesMaterialItemList)) {
            return;
        }
        List<ServiceEntityNode> productionPlanList =
                docBatchConvertProxy.getExistTargetDocForCreationBatch(new DocBatchConvertProxy.DocBatchConvertConfig(
                        ProdPlanTargetMatItem.NODENAME, ProductionPlan.NODENAME, productionPlanManager,
                        IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONPLAN, seNode -> {
                    ProductionPlan productionPlan = (ProductionPlan) seNode;
                    return productionPlan.getStatus() == ProductionPlan.STATUS_INITIAL;
                }
                ), allSalesMaterialItemList);
        /*
         * [1.5] Logic build quality inspect order
         */
        ProductionPlan productionPlan;
        boolean newModelFlag = false;
        if (!ServiceCollectionsHelper.checkNullList(productionPlanList)) {
            productionPlan = (ProductionPlan) productionPlanList
                    .get(0);
        } else {
            newModelFlag = true;
            // If can not find, then create new quality inspect order
            productionPlan = (ProductionPlan) productionPlanManager
                    .newRootEntityNode(serialLogonInfo.getClient());
        }
        /*
         * [Step2] Batch creation of in-bound item list from production target
         * material item list
         */
        List<ServiceEntityNode> prodPlanTargetMatItemList = new ArrayList<>();
        for (ServiceEntityNode seNode : salesMaterialItemList) {
            DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
            MaterialStockKeepUnit refMaterialSKU = (MaterialStockKeepUnit) ServiceCollectionsHelper
                    .filterSENodeOnline(docMatItemNode
                            .getRefMaterialSKUUUID(), refMaterialSKUList);
            if (!ServiceEntityStringHelper
                    .checkNullString(docMatItemNode
                            .getNextDocMatItemUUID())) {
                continue;
            }
            ProdPlanTargetMatItem prodPlanTargetMatItem = genProdPlanTargetMatItemCore(
                    refMaterialSKU, docMatItemNode,
                    productionPlan, serialLogonInfo);
            prodPlanTargetMatItemList.add(prodPlanTargetMatItem);
        }
        // Batch set sales document in plan status
        batchInPlanSalesDocList(salesMaterialItemList, serialLogonInfo);
        /*
         * [Step3] Update quality inspect order into Persistence as well as
         * in-bound determination
         */
        if (newModelFlag) {
            ProductionPlanServiceModel productionPlanServiceModel = (ProductionPlanServiceModel) productionPlanManager
                    .quickCreateServiceModel(productionPlan,
                            prodPlanTargetMatItemList);
            productionPlanManager.updateServiceModuleWithPost(
                    ProductionPlanServiceModel.class,
                    productionPlanServiceModel, serialLogonInfo.getRefUserUUID(),
                    serialLogonInfo.getResOrgUUID());
        } else {
            productionPlanManager.updateSENode(productionPlan,
                    serialLogonInfo.getRefUserUUID(),serialLogonInfo.getResOrgUUID());
            productionPlanManager.updateSENodeList(
                    prodPlanTargetMatItemList, serialLogonInfo.getRefUserUUID(),
                    serialLogonInfo.getResOrgUUID());
        }
    }

    /**
     * Wrapper method to batch set sales document: sales contract, sales forcast into in plan status
     *
     * @param salesDocMatItemList: sales material item list
     */
    public void batchInPlanSalesDocList(List<ServiceEntityNode> salesDocMatItemList, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, SalesContractException, ServiceModuleProxyException,
            SalesForcastException, DocActionException {
        if (ServiceCollectionsHelper.checkNullList(salesDocMatItemList)) {
            return;
        }
        List<String> salesContractUUIDList = new ArrayList<>();
        List<String> salesForcastUUIDList = new ArrayList<>();
        List<ServiceEntityNode> salesContractList = new ArrayList<>();
        List<ServiceEntityNode> salesForcastList = new ArrayList<>();
        String client = salesDocMatItemList.get(0).getClient();
        for (ServiceEntityNode seNode : salesDocMatItemList) {
            DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
            if (docMatItemNode instanceof SalesContractMaterialItem) {
                ServiceCollectionsHelper.mergeToList(salesContractUUIDList, docMatItemNode.getRootNodeUUID());
            }
            if (docMatItemNode instanceof SalesForcastMaterialItem) {
                ServiceCollectionsHelper.mergeToList(salesForcastUUIDList, docMatItemNode.getRootNodeUUID());
            }
        }
        if (!ServiceCollectionsHelper.checkNullList(salesContractUUIDList)) {
            salesContractList =
                    salesContractManager.getEntityNodeListByMultipleKey(salesContractUUIDList,
                            IServiceEntityNodeFieldConstant.UUID, SalesContract.NODENAME, client, null);
        }
        if (!ServiceCollectionsHelper.checkNullList(salesForcastUUIDList)) {
            salesForcastList =
                    salesForcastManager.getEntityNodeListByMultipleKey(salesForcastUUIDList,
                            IServiceEntityNodeFieldConstant.UUID, SalesForcast.NODENAME, client, null);
        }
        Map<String, List<ServiceEntityNode>> salesMaterialItemMap =
                DocFlowProxy.mapMaterialItemListByRoot(salesDocMatItemList);
        Set<String> keySets = salesMaterialItemMap.keySet();
        for (String rootNodeUUID : keySets) {
            List<ServiceEntityNode> tempSalesMatItemList = salesMaterialItemMap.get(rootNodeUUID);
            if (ServiceCollectionsHelper.checkNullList(tempSalesMatItemList)) {
                continue;
            }
            String seName = tempSalesMatItemList.get(0).getServiceEntityName();
            if (SalesContract.SENAME.equals(seName)) {
                SalesContract salesContract = (SalesContract) ServiceCollectionsHelper.filterSENodeOnline(rootNodeUUID,
                        salesContractList);
                inPlanSalesContractWrapper(salesContract, tempSalesMatItemList, serialLogonInfo);
            }
            if (SalesForcast.SENAME.equals(seName)) {
                SalesForcast salesForcast = (SalesForcast) ServiceCollectionsHelper.filterSENodeOnline(rootNodeUUID,
                        salesForcastList);
                inPlanSalesForcastWrapper(salesForcast, tempSalesMatItemList, serialLogonInfo);
            }
        }
    }

    /**
     * @param salesContract
     * @param salesContractMaterialItemList
     * @param serialLogonInfo
     * @throws ServiceModuleProxyException
     * @throws SalesContractException
     * @throws ServiceEntityConfigureException
     */
    @Deprecated
    private void inPlanSalesContractWrapper(SalesContract salesContract,
                                            List<ServiceEntityNode> salesContractMaterialItemList,
                                            SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, SalesContractException, ServiceEntityConfigureException,
            DocActionException {
        SalesContractServiceModel salesContractServiceModel =
                (SalesContractServiceModel) salesContractManager.quickCreateServiceModel(salesContract,
                        salesContractMaterialItemList);
        salesContractActionExecutionProxy.executeActionCore(salesContractServiceModel,
                SalesContractActionNode.DOC_ACTION_INPLAN, serialLogonInfo);
    }

    private void inPlanSalesForcastWrapper(SalesForcast salesForcast,
                                           List<ServiceEntityNode> salesForcastMaterialItemList,
                                           SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, SalesForcastException, ServiceEntityConfigureException,
            DocActionException {
        SalesForcastServiceModel salesForcastServiceModel =
                (SalesForcastServiceModel) salesForcastManager.quickCreateServiceModel(salesForcast,
                        salesForcastMaterialItemList);
        salesForcastActionExecutionProxy.executeActionCore(salesForcastServiceModel,
                SalesForcastActionNode.DOC_ACTION_INPLAN, serialLogonInfo);
    }

    /**
     * Core Logic to generate in-bound item or quality inspect item for each
     * salesContractMaterialItem instance
     *
     * @param docMatItemNode
     * @throws ServiceEntityConfigureException
     */
    private ProdPlanTargetMatItem genProdPlanTargetMatItemCore(
            MaterialStockKeepUnit materialStockKeepUnit,
            DocMatItemNode docMatItemNode,
            ProductionPlan productionPlan, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, DocActionException {
        // Initial converting the production targetitem information to in-bound.
        ProdPlanTargetMatItem prodPlanTargetMatItem = (ProdPlanTargetMatItem) productionPlanManager
                .newEntityNode(productionPlan,
                        ProdPlanTargetMatItem.NODENAME);
        initConvertSalesContractItemToProdPlanTargetItem(
                docMatItemNode, materialStockKeepUnit,
                prodPlanTargetMatItem, serialLogonInfo);
//		salesContractMaterialItem.setRefInWarehouseUUID(productionPlan
//				.getRefWarehouseUUID());
        return prodPlanTargetMatItem;
    }

    /**
     * Logic to initial convert and copy information from production targetmaterial item
     * to quality inspect list item.
     *
     * @param docMatItemNode
     * @param prodPlanTargetMatItem
     */
    public void initConvertSalesContractItemToProdPlanTargetItem(
            DocMatItemNode docMatItemNode,
            MaterialStockKeepUnit materialStockKeepUnit,
            ProdPlanTargetMatItem prodPlanTargetMatItem, SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (prodPlanTargetMatItem != null
                && docMatItemNode != null) {
            // Add prev-next relationship between the source and target items.
            docFlowProxy.buildItemPrevNextRelationship(docMatItemNode, prodPlanTargetMatItem, null, serialLogonInfo);
        }
    }

}
