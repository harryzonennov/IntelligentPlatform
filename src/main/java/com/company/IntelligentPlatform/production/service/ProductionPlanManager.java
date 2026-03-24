package com.company.IntelligentPlatform.production.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryWarehouseItemManager;

import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.finance.service.FinanceAccountValueProxyException;
import com.company.IntelligentPlatform.finance.service.SystemResourceException;
import com.company.IntelligentPlatform.finance.service.SystemResourceFinanceAccountProxy;
import com.company.IntelligentPlatform.production.repository.ProductionPlanRepository;
import com.company.IntelligentPlatform.common.dto.MaterialStockKeepUnitSearchModel;
import com.company.IntelligentPlatform.common.service.MatDecisionValueSettingManager;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.logistics.model.StoreAvailableStoreItemRequest;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemException;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemManager;
import com.company.IntelligentPlatform.common.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.controller.DefFinanceControllerResource;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.ServiceFlowRuntimeException;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDateHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;


/**
 * Logic Manager CLASS FOR Service Entity [ProductionPlan]
 *
 * @author
 * @date Sun Jan 03 21:00:34 CST 2016
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
public class ProductionPlanManager extends ServiceEntityManager {

    public static final String METHOD_ConvItemBillOfMaterialItemToUI = "convItemBillOfMaterialItemToUI";

    public static final String METHOD_ConvProdPlanSupplyWarehouseToUI = "convProdPlanSupplyWarehouseToUI";

    public static final String METHOD_ConvUIToProdPlanSupplyWarehouse = "convUIToProdPlanSupplyWarehouse";

    public static final String METHOD_ConvWarehouseToUI = "convWarehouseToUI";

    public static final String METHOD_ConvProductionPlanToUI = "convProductionPlanToUI";

    public static final String METHOD_ConvUIToProductionPlan = "convUIToProductionPlan";

    public static final String METHOD_ConvOutBillOfMaterialOrderToUI = "convOutBillOfMaterialOrderToUI";

    public static final String METHOD_ConvApproveByToUI = "convApproveByToUI";

    public static final String METHOD_ConvCountApproveByToUI = "convCountApproveByToUI";

    public static final String METHOD_ConvMainProductionOrderToUI = "convMainProductionOrderToUI";

    public static final String METHOD_ConvOutMaterialStockKeepUnitToUI = "convOutMaterialStockKeepUnitToUI";

    public static final String METHOD_ConvWarehouseToItemReqProposalUI = "convWarehouseToItemReqProposalUI";

    public static final String METHOD_ConvDocumentToItemReqProposalUI = "convDocumentToItemReqProposalUI";

    private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> categoryMapLan = new HashMap<>();

    private Map<String, ProductionPlan> productionPlanMap = new HashMap<>();

    final Logger logger = LoggerFactory.getLogger(ProductionPlanManager.class);

    @Autowired
    protected ProductionPlanRepository productionPlanDAO;

    @Autowired
    protected WarehouseManager warehouseManager;

    // TODO-LEGACY: @Autowired

    // TODO-LEGACY:     protected ProductionPackageProxy productionPackageProxy;

    @Autowired
    protected ProductionDocRequestService productionDocRequestService;

    @Autowired
    protected WarehouseStoreItemManager warehouseStoreItemManager;

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected ProductionPlanItemManager productionPlanItemManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected ProductionPlanConfigureProxy productionPlanConfigureProxy;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected ProductionPlanIdHelper productionPlanIdHelper;

    @Autowired
    protected SystemResourceFinanceAccountProxy systemResourceFinanceAccountProxy;

    @Autowired
    protected ProcessRouteOrderManager processRouteOrderManager;

    @Autowired
    protected ProductionPlanActionExecutionProxy productionPlanActionExecutionProxy;

    @Autowired
    protected ProdJobOrderManager prodJobOrderManager;

    @Autowired
    protected ProductionOrderManager productionOrderManager;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected ProductiveBOMOrderManager productiveBOMOrderManager;

    @Autowired
    protected ProductionPlanTimeComsumeProxy productionPlanTimeComsumeProxy;

    @Autowired
    protected ProductionReleaseProxy productionReleaseProxy;

    @Autowired
    protected MatDecisionValueSettingManager matDecisionValueSettingManager;

    @Autowired
    protected OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

    @Autowired
    protected StandardPriorityProxy standardPriorityProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected ProductionPlanSearchProxy productionPlanSearchProxy;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    @Autowired
    protected ProductionPlanServiceUIModelExtension productionPlanServiceUIModelExtension;

    @Autowired
    protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;
    @Autowired
    private LogonInfoManager logonInfoManager;

    @Override
    public ServiceEntityNode getEntityNodeByKey(Object keyValue, String keyName, String nodeName, String client,
                                                List<ServiceEntityNode> rawSEList) throws ServiceEntityConfigureException {
        if (IServiceEntityNodeFieldConstant.UUID.equals(keyName) && ProductionPlan.NODENAME.equals(nodeName)) {
            if (this.productionPlanMap.containsKey(keyValue)) {
                return this.productionPlanMap.get(keyValue);
            }
            // In case not find, then find from persistence
            ProductionPlan productionPlan = (ProductionPlan) super
                    .getEntityNodeByKey(keyValue, keyName, nodeName, client, rawSEList);
            if (productionPlan != null) {
                this.productionPlanMap.put(keyName, productionPlan);
            }
            return productionPlan;
        } else {
            return super.getEntityNodeByKey(keyValue, keyName, nodeName, client, rawSEList);
        }
    }

    @Override
    public void updateBuffer(ServiceEntityNode serviceEntityNode) {
        if (serviceEntityNode != null && LogonUser.SENAME.equals(serviceEntityNode.getServiceEntityName())) {
            ProductionPlan productionPlan = (ProductionPlan) serviceEntityNode;
            this.productionPlanMap.put(productionPlan.getUuid(), productionPlan);
        }
    }

    public ProductionPlanServiceModel quickCreateServiceModel(ProductionPlan productionPlan,
                                                              List<ServiceEntityNode> prodPlanTargetMatItemList){
        ProductionPlanServiceModel productionPlanServiceModel = new ProductionPlanServiceModel();
        productionPlanServiceModel.setProductionPlan(productionPlan);
        if(!ServiceCollectionsHelper.checkNullList(prodPlanTargetMatItemList)){
            List<ProdPlanTargetMatItemServiceModel> prodPlanTargetMatItemServiceModelList =
                    new ArrayList<ProdPlanTargetMatItemServiceModel>();
            for(ServiceEntityNode seNode:prodPlanTargetMatItemList){
                ProdPlanTargetMatItemServiceModel prodPlanTargetMatItemServiceModel =
                        new ProdPlanTargetMatItemServiceModel();
                prodPlanTargetMatItemServiceModel.setProdPlanTargetMatItem((ProdPlanTargetMatItem) seNode);
                prodPlanTargetMatItemServiceModelList.add(prodPlanTargetMatItemServiceModel);
            }
            productionPlanServiceModel.setProdPlanTargetMatItemList(prodPlanTargetMatItemServiceModelList);
        }
        return productionPlanServiceModel;
    }


    public List<PageHeaderModel> getPageHeaderModelList(ProductionPlan productionPlan, String client)
            throws ServiceEntityConfigureException {
        List<PageHeaderModel> resultList = new ArrayList<PageHeaderModel>();
        if (productionPlan != null) {
            PageHeaderModel itemHeaderModel = getPageHeaderModel(productionPlan, 0);
            if (itemHeaderModel != null) {
                resultList.add(itemHeaderModel);
            }
        }
        return resultList;
    }

    /**
     * Short way to production order's warehouse UUID List
     *
     * @param baseUUID
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<String> getWarehouseUUIDList(String baseUUID, String client) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> prodSupplyWarehouseList = getEntityNodeListByKey(baseUUID,
                IServiceEntityNodeFieldConstant.ROOTNODEUUID, ProdPlanSupplyWarehouse.NODENAME, client, null);
        if (ServiceCollectionsHelper.checkNullList(prodSupplyWarehouseList)) {
            return null;
        }
        List<String> warehouseUUIDList = new ArrayList<String>();
        prodSupplyWarehouseList.forEach(seNode -> {
            ProdPlanSupplyWarehouse prodPlanSupplyWarehouse = (ProdPlanSupplyWarehouse) seNode;
            warehouseUUIDList.add(prodPlanSupplyWarehouse.getRefUUID());
        });
        return warehouseUUIDList;
    }

    /**
     * Entrance method to update production plan service model
     *
     * @param productionPlanServiceModel
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public void postUpdateProductionPlan(ProductionPlanServiceModel productionPlanServiceModel)
            throws ServiceEntityConfigureException, MaterialException, DocActionException {
        /*
         * [Step1] Update Info for Production Order Item
         */
        ProductionPlan productionPlan = productionPlanServiceModel.getProductionPlan();
        List<String> warehouseUUIDList = getWarehouseUUIDList(productionPlan.getUuid(), productionPlan.getClient());
        List<ProductionPlanItemServiceModel> productionPlanItemList = productionPlanServiceModel.getProductionPlanItemList();
        if (ServiceCollectionsHelper.checkNullList(productionPlanItemList)) {
            return;
        }
        for (ProductionPlanItemServiceModel productionPlanItemServiceModel : productionPlanItemList) {
            productionPlanItemManager.refreshPlanItemStatus(productionPlanItemServiceModel, warehouseUUIDList);
        }
    }

    public ProductionPlanServiceModel newProductionPlanServiceModel(ProductionPlanInitModel productionPlanInitModel, int category,
                                                                    String logonUserUUID, String organizationUUID,
                                                                    String client)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, MaterialException, ProductionPlanException {
        /*
         * [Step1] new module of production plan from init model
         */
        ProductionPlan productionPlan = (ProductionPlan) newRootEntityNode(client);
        productionPlan.setCategory(category);
        productionPlan.setRefMaterialSKUUUID(productionPlanInitModel.getRefMaterialSKUUUID());
        productionPlan.setRefBillOfMaterialUUID(productionPlanInitModel.getRefBillOfMaterialUUID());
        productionPlan.setProductionBatchNumber(productionPlanInitModel.getProductionBatchNumber());
        productionPlan.setAmount(productionPlanInitModel.getAmount());
        productionPlan.setRefUnitUUID(productionPlanInitModel.getRefUnitUUID());
        if(productionPlan.getAmount() <= 0){
            throw new ProductionPlanException(ProductionPlanException.PARA_SYSTEM_ERROR, productionPlan.getAmount());
        }
        MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
                .getEntityNodeByUUID(productionPlanInitModel.getRefMaterialSKUUUID(), MaterialStockKeepUnit.NODENAME,
                        client);

        ProductionPlanServiceModel productionPlanServiceModel = new ProductionPlanServiceModel();
        productionPlanServiceModel.setProductionPlan(productionPlan);
        if (materialStockKeepUnit != null) {
            productionPlan.setName(materialStockKeepUnit.getName());
            // Set default raw material warehouse
            List<ServiceEntityNode> rawWarehouseDecisonList = matDecisionValueSettingManager
                    .getDecisionValueList(materialStockKeepUnit, MatDecisionValueSettingManager.VAUSAGE_RAWMAT_WAREHOUSE);
            if (!ServiceCollectionsHelper.checkNullList(rawWarehouseDecisonList)) {
                List<ServiceEntityNode> prodPlanSupplyWarehouseList = new ArrayList<>();
                for (ServiceEntityNode seNode : rawWarehouseDecisonList) {
                    MatDecisionValueSetting matDecisionValueSetting = (MatDecisionValueSetting) seNode;
                    ProdPlanSupplyWarehouse prodPlanSupplyWarehouse = (ProdPlanSupplyWarehouse) newEntityNode(productionPlan,
                            ProdPlanSupplyWarehouse.NODENAME);
                    prodPlanSupplyWarehouse.setRefUUID(matDecisionValueSetting.getRawValue());
                    prodPlanSupplyWarehouse.setRefNodeName(Warehouse.NODENAME);
                    prodPlanSupplyWarehouse.setRefSEName(Warehouse.SENAME);
                    prodPlanSupplyWarehouseList.add(prodPlanSupplyWarehouse);
                }
                productionPlanServiceModel.setProdPlanSupplyWarehouseList(prodPlanSupplyWarehouseList);
            }
        }
        try{
            if (!ServiceEntityStringHelper.checkNullString(productionPlanInitModel.getPlanStartTime())) {
                productionPlan.setPlanStartTime(DefaultDateFormatConstant.DATE_FORMAT.parse(productionPlanInitModel.getPlanStartTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
                productionPlan.setInitTimeMode(ProductionPlan.INIT_TIMEMODE_PLANSTART);
            }
            if (!ServiceEntityStringHelper.checkNullString(productionPlanInitModel.getPlanEndTime())) {
                productionPlan.setPlanEndTime(DefaultDateFormatConstant.DATE_FORMAT.parse(productionPlanInitModel.getPlanEndTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
                productionPlan.setInitTimeMode(ProductionPlan.INIT_TIMEMODE_PLANEND);
            }
            if (!ServiceEntityStringHelper.checkNullString(productionPlanInitModel.getPlanStartPrepareDate())) {
                productionPlan.setPlanStartPrepareDate(
                        DefaultDateFormatConstant.DATE_FORMAT.parse(productionPlanInitModel.getPlanStartPrepareDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
                productionPlan.setInitTimeMode(ProductionPlan.INIT_TIMEMODE_STARTPREPARE);
            }
        }catch(ParseException e){
            throw new ProductionPlanException(ProductionPlanException.PARA_SYSTEM_ERROR, e.getMessage());
        }
        /*
         * [Step2] generate plan proposal
         */
        // Just left only production plan module alone
        updateServiceModule(ProductionPlanServiceModel.class, productionPlanServiceModel, logonUserUUID, organizationUUID);
        return productionPlanServiceModel;
    }

    public ProductiveBOMOrderServiceModel generateProductiveBOMData(ProductionPlan productionPlan)
            throws ServiceEntityConfigureException, BillOfMaterialException, MaterialException {
        BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager
                .getRefBillOfMaterialOrderWrapper(productionPlan.getRefBillOfMaterialUUID(), productionPlan.getClient());
        List<ServiceEntityNode> productiveBOMList = billOfMaterialOrderManager.genProductiveBOMModel(billOfMaterialOrder);
        ProductiveBOMOrder productiveBOMOrder = billOfMaterialOrderManager.genInitProductiveBomOrder(billOfMaterialOrder);
        double ratio = getRatioFromProductionToBOMOrder(productionPlan, billOfMaterialOrder);
        double amountInProd = productiveBOMOrder.getAmount() * ratio;
        productiveBOMOrder.setAmountWithLossRate(amountInProd);
        /*
         * [Step2] Traverse from first BOM layer into the footer layer to
         * calculate each item required amount
         */
        List<ServiceEntityNode> firstBomLayerList = productiveBOMOrderManager
                .filterSubBOMItemList(productiveBOMOrder.getUuid(), productiveBOMList);
        List<ProductiveBOMItemServiceModel> productiveBOMItemList = new ArrayList<>();
        for (ServiceEntityNode seNode : firstBomLayerList) {
            /*
             * [Step2] Calculate the first BOM layer and generate the relative
             * production　order item
             */
            ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
            double amount = productiveBOMItem.getAmount() * ratio;
            // Calculate the amount with loss rate
            double amountWithLossRate = amount / (1 - productiveBOMItem.getTheoLossRate() / 100);
            amountWithLossRate = Math.ceil(amountWithLossRate);
            // Update the amount of Productive BOM item
            productiveBOMItem.setAmountWithLossRate(amountWithLossRate);
            ProductiveBOMItemServiceModel productiveBOMItemServiceModel = new ProductiveBOMItemServiceModel();
            productiveBOMItemServiceModel.setProductiveBOMItem(productiveBOMItem);
            List<ProductiveBOMItemServiceModel> subProductiveBOMItemList = genSubProdBOMItemSerModel(productiveBOMItem,
                    productiveBOMList);
            if (!ServiceCollectionsHelper.checkNullList(subProductiveBOMItemList)) {
                productiveBOMItemServiceModel.setSubProductiveBOMItemList(subProductiveBOMItemList);
            }
            productiveBOMItemList.add(productiveBOMItemServiceModel);
        }
        ProductiveBOMOrderServiceModel productiveBOMOrderServiceModel = new ProductiveBOMOrderServiceModel();
        productiveBOMOrderServiceModel.setProductiveBOMOrder(productiveBOMOrder);
        productiveBOMOrderServiceModel.setProductiveBOMItemList(productiveBOMItemList);
        return productiveBOMOrderServiceModel;

    }

    private List<ProductiveBOMItemServiceModel> genSubProdBOMItemSerModel(ProductiveBOMItem parentProdBOMItem,
                                                                          List<ServiceEntityNode> productiveBOMList) {
        List<ServiceEntityNode> subBOMItemList = productiveBOMOrderManager
                .filterSubBOMItemList(parentProdBOMItem.getUuid(), productiveBOMList);
        if (ServiceCollectionsHelper.checkNullList(subBOMItemList)) {
            return null;
        }
        List<ProductiveBOMItemServiceModel> result = new ArrayList<>();
        /*
         * Ratio: means the real amount in production / amount set in standard
         * BOM item amount
         */
        double ratio = parentProdBOMItem.getAmountWithLossRate() / parentProdBOMItem.getAmount();
        for (ServiceEntityNode seNode : subBOMItemList) {
            ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
            ProductiveBOMItemServiceModel productiveBOMItemServiceModel = new ProductiveBOMItemServiceModel();
            productiveBOMItemServiceModel.setProductiveBOMItem(productiveBOMItem);
            double amount = ratio * productiveBOMItem.getAmount();
            // theory amount with loss rate
            double amountWithLossRate = amount / (1 - productiveBOMItem.getTheoLossRate() / 100);
            amountWithLossRate = Math.ceil(amountWithLossRate);
            productiveBOMItem.setAmountWithLossRate(amountWithLossRate);
            List<ProductiveBOMItemServiceModel> subProductiveBOMItemList = genSubProdBOMItemSerModel(productiveBOMItem,
                    productiveBOMList);
            if (!ServiceCollectionsHelper.checkNullList(subProductiveBOMItemList)) {
                productiveBOMItemServiceModel.setSubProductiveBOMItemList(subProductiveBOMItemList);
            }
            result.add(productiveBOMItemServiceModel);
        }
        return result;
    }

    /**
     * Core Logic to approve productionPlan and update to DB
     *
     * @param productionPlanServiceModel
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     */
    public void submitService(
            ProductionPlanServiceModel productionPlanServiceModel,
            String logonUserUUID, String organizationUUID)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        try {
            this.checkSubmit(productionPlanServiceModel);
        } catch (ProductionPlanException e) {
            throw new ServiceModuleProxyException(ServiceModuleProxyException.PARA_SYSTEM_WRONG, e.getErrorMessage());
        }
//        this.executeActionCore(productionPlanServiceModel,
//                ServiceCollectionsHelper.asList(ProductionPlan.STATUS_INITIAL,
//                        ProductionPlan.STATUS_REJECT_APPROVAL)
//                , ProductionPlan.STATUS_SUBMITTED,
//                ProductionPlanActionNode.DOC_ACTION_SUBMIT, logonUserUUID, organizationUUID);
    }

    private void checkSubmit(ProductionPlanServiceModel productionPlanServiceModel) throws ProductionPlanException {
        ProductionPlan productionPlan = productionPlanServiceModel.getProductionPlan();
        if(productionPlan.getAmount() <= 0){
            throw new ProductionPlanException(ProductionPlanException.PARA_NEGATIVE_AMOUNT, productionPlan.getId());
        }
    }


    /**
     * Core Logic to approve productionPlan and update to DB
     *
     * @param productionPlanServiceModel
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     */
    public void rejectApproveService(
            ProductionPlanServiceModel productionPlanServiceModel,
            String logonUserUUID, String organizationUUID)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        this.executeActionCore(productionPlanServiceModel,
                ServiceCollectionsHelper.asList(ProductionPlan.STATUS_APPROVED), ProductionPlan.STATUS_REJECT_APPROVAL,
                ProductionPlanActionNode.DOC_ACTION_REJECT_APPROVE, logonUserUUID, organizationUUID);
    }


    /**
     * Core Logic to approve production plan and update to DB
     *
     * @param productionPlanServiceModel
     * @throws ServiceEntityInstallationException
     * @throws NodeNotFoundException
     * @throws ServiceEntityConfigureException
     * @throws SearchConfigureException
     * @throws ProductionPlanException
     * @throws MaterialException
     * @throws BillOfMaterialException
     * @throws ServiceModuleProxyException
     */
    public synchronized void approvePlan(ProductionPlanServiceModel productionPlanServiceModel, LogonInfo logonInfo)
            throws BillOfMaterialException, MaterialException, ProductionPlanException, SearchConfigureException,
            ServiceEntityConfigureException, NodeNotFoundException, ServiceEntityInstallationException,
            ServiceModuleProxyException, ServiceComExecuteException, AuthorizationException, LogonInfoException {

        ProductionPlan productionPlan = productionPlanServiceModel.getProductionPlan();

        // Should delete the previous sub generated resources
        deletePlanSubResource(productionPlan.getUuid(), logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID(), productionPlan.getClient());
        /*
         * [Step3] Refresh BOM Order to recent active
         */
        updateRecentBOMOrderToPlan(productionPlan);
        List<ServiceEntityNode> rawProdPlanItemList = generateProductItemListEntry(productionPlan, logonInfo, true);
        productionPlanServiceModel = (ProductionPlanServiceModel) convertToProductionPlanServiceModel(productionPlan, rawProdPlanItemList);
        updateServiceModuleWithDelete(ProductionPlanServiceModel.class, productionPlanServiceModel, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
//        this.executeActionCore(productionPlanServiceModel,
//                ServiceCollectionsHelper.asList(ProductionPlan.STATUS_SUBMITTED), ProductionPlan.STATUS_APPROVED,
//                ProductionPlanActionNode.DOC_ACTION_APPROVE, logonUserUUID, organizationUUID);
    }

    public void deletePlan(ProductionPlan productionPlan, String client, String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException, DocActionException {
        if (productionPlan != null) {
            // Clear binding prev
            List<ServiceEntityNode> prevRequestDocMatItemList =
                    productionDocRequestService.getAllPrevRequestMatItemByPlan(productionPlan.getUuid(), client);
            if(!ServiceCollectionsHelper.checkNullList(prevRequestDocMatItemList)){
                docFlowProxy.clearDocItemNextInfo(prevRequestDocMatItemList, null, logonUserUUID, organizationUUID);
            }
            archiveDeleteEntityByKey(productionPlan.getUuid(), IServiceEntityNodeFieldConstant.UUID, client,
                            ProductionPlan.NODENAME, logonUserUUID, organizationUUID);
        }
    }


    /**
     * Delete all the generated plan sub resources, keep plan basic information
     *
     * @param baseUUID
     * @param logonUserUUID
     * @param organizationUUID
     * @param client
     * @throws ServiceEntityConfigureException
     */
    public void deletePlanSubResource(String baseUUID, String logonUserUUID, String organizationUUID, String client)
            throws ServiceEntityConfigureException {
        List<ServiceEntityNode> rawProdPlanItemList = this
                .getEntityNodeListByKey(baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID, ProductionPlanItem.NODENAME, client,
                        null);
        if (!ServiceCollectionsHelper.checkNullList(rawProdPlanItemList)) {
            this.deleteSENode(rawProdPlanItemList, logonUserUUID, organizationUUID);
        }
        List<ServiceEntityNode> rawPlanItemReqProposal = this
                .getEntityNodeListByKey(baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID, ProdPlanItemReqProposal.NODENAME,
                        client, null);
        if (!ServiceCollectionsHelper.checkNullList(rawPlanItemReqProposal)) {
            this.deleteSENode(rawPlanItemReqProposal, logonUserUUID, organizationUUID);
        }
    }

    /**
     * Core Logic to count-approve production plan and update to DB
     *
     * @param productionPlanServiceModel
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     */
    public void countApprovePlan(ProductionPlanServiceModel productionPlanServiceModel, String logonUserUUID,
                                 String organizationUUID)
            throws ServiceEntityConfigureException, ServiceModuleProxyException {
        ProductionPlan productionPlan = productionPlanServiceModel.getProductionPlan();
        this.updateSENode(productionPlan, logonUserUUID, organizationUUID);
        deletePlanSubResource(productionPlan.getUuid(), logonUserUUID, organizationUUID, productionPlan.getClient());
        /*
         * [Step2] Refresh BOM Order to recent active
         */
        try {
            updateRecentBOMOrderToPlan(productionPlan);
        } catch (BillOfMaterialException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new ServiceModuleProxyException(ServiceModuleProxyException.PARA_SYSTEM_WRONG, e.getErrorMessage());
        }
//        this.executeActionCore(productionPlanServiceModel,
//                ServiceCollectionsHelper.asList(ProductionPlan.STATUS_APPROVED), ProductionPlan.STATUS_INITIAL,
//                ProductionPlanActionNode.DOC_ACTION_COUNTAPPROVE, logonUserUUID, organizationUUID);
    }

    /**
     * Core Logic to approve productionPlan and update to DB
     *
     * @param productionPlanServiceModel
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     */
    public void executeActionCore(
            ProductionPlanServiceModel productionPlanServiceModel, List<Integer> curStatusList, int targetStatus,
            int actionCode,
            String logonUserUUID, String organizationUUID)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        ProductionPlan productionPlan = productionPlanServiceModel
                .getProductionPlan();
        if (!DocActionNodeProxy.checkCurStatus(curStatusList, productionPlan.getStatus())) {
            return;
        }
        productionPlan.setStatus(targetStatus);
        docActionNodeProxy.updateDocActionWrapper(actionCode,
                ProductionPlanActionNode.NODENAME, null, IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONPLAN, this,
                productionPlanServiceModel,
                productionPlan,
                logonUserUUID,
                organizationUUID);
        updateServiceModuleWithDelete(
                ProductionPlanServiceModel.class,
                productionPlanServiceModel, logonUserUUID, organizationUUID, ProductionPlan.SENAME,
                productionPlanServiceUIModelExtension);
    }

    public PageHeaderModel getPageHeaderModel(ProductionPlan productionPlan, int index) throws ServiceEntityConfigureException {
        PageHeaderModel pageHeaderModel = new PageHeaderModel();
        pageHeaderModel.setPageTitle("productionPlanTitle");
        pageHeaderModel.setHeaderName(productionPlan.getId());
        pageHeaderModel.setNodeInstId(ProductionPlan.SENAME);
        pageHeaderModel.setUuid(productionPlan.getUuid());
        pageHeaderModel.setIndex(index);
        return pageHeaderModel;
    }

    public void mergeOutboundProposal(ProdPlanItemReqProposal existProposal, ProdPlanItemReqProposal proposalToMerge)
            throws ServiceEntityConfigureException, MaterialException {
        if (existProposal.getDocumentType() != IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
            return;
        }
        if (proposalToMerge.getDocumentType() != IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
            return;
        }
        if (!existProposal.getRefWarehouseUUID().equals(proposalToMerge.getRefWarehouseUUID())) {
            return;
        }
        MaterialStockKeepUnit materialStockKeepUnit1 = (MaterialStockKeepUnit) materialStockKeepUnitManager
                .getEntityNodeByKey(existProposal.getRefMaterialSKUUUID(), IServiceEntityNodeFieldConstant.UUID,
                        MaterialStockKeepUnit.NODENAME, existProposal.getClient(), null);
        String refTempUUID1 = MaterialStockKeepUnitManager.getRefTemplateUUID(materialStockKeepUnit1);
        MaterialStockKeepUnit materialStockKeepUnit2 = (MaterialStockKeepUnit) materialStockKeepUnitManager
                .getEntityNodeByKey(proposalToMerge.getRefMaterialSKUUUID(), IServiceEntityNodeFieldConstant.UUID,
                        MaterialStockKeepUnit.NODENAME, proposalToMerge.getClient(), null);
        String refTempUUID2 = MaterialStockKeepUnitManager.getRefTemplateUUID(materialStockKeepUnit2);
        if (!refTempUUID1.equals(refTempUUID2)) {
            return;
        }
        StorageCoreUnit storageCoreUnit1 = new StorageCoreUnit(refTempUUID1, existProposal.getRefUnitUUID(),
                existProposal.getAmount());
        StorageCoreUnit storageCoreUnit2 = new StorageCoreUnit(refTempUUID2, proposalToMerge.getRefUnitUUID(),
                proposalToMerge.getAmount());
        storageCoreUnit1 = materialStockKeepUnitManager
                .mergeStorageUnitCore(storageCoreUnit1, storageCoreUnit2, StorageCoreUnit.OPERATOR_ADD, proposalToMerge.getClient());
        existProposal.setAmount(storageCoreUnit1.getAmount());
        existProposal.setRefUnitUUID(storageCoreUnit1.getRefUnitUUID());
    }



    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convProdPlanSupplyWarehouseToUI(ProdPlanSupplyWarehouse prodPlanSupplyWarehouse,
                                                ProdPlanSupplyWarehouseUIModel prodPlanSupplyWarehouseUIModel) {
        if (prodPlanSupplyWarehouse != null) {
            if (!ServiceEntityStringHelper.checkNullString(prodPlanSupplyWarehouse.getUuid())) {
                prodPlanSupplyWarehouseUIModel.setUuid(prodPlanSupplyWarehouse.getUuid());
            }
            if (!ServiceEntityStringHelper.checkNullString(prodPlanSupplyWarehouse.getParentNodeUUID())) {
                prodPlanSupplyWarehouseUIModel.setParentNodeUUID(prodPlanSupplyWarehouse.getParentNodeUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(prodPlanSupplyWarehouse.getRootNodeUUID())) {
                prodPlanSupplyWarehouseUIModel.setRootNodeUUID(prodPlanSupplyWarehouse.getRootNodeUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(prodPlanSupplyWarehouse.getClient())) {
                prodPlanSupplyWarehouseUIModel.setClient(prodPlanSupplyWarehouse.getClient());
            }
            prodPlanSupplyWarehouseUIModel.setRefUUID(prodPlanSupplyWarehouse.getRefUUID());
        }
    }

    /**
     * [Internal method] Convert from UI model to se
     * model:prodPlanSupplyWarehouse
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToProdPlanSupplyWarehouse(ProdPlanSupplyWarehouseUIModel prodPlanSupplyWarehouseUIModel,
                                                ProdPlanSupplyWarehouse rawEntity) {
        if (!ServiceEntityStringHelper.checkNullString(prodPlanSupplyWarehouseUIModel.getUuid())) {
            rawEntity.setUuid(prodPlanSupplyWarehouseUIModel.getUuid());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodPlanSupplyWarehouseUIModel.getParentNodeUUID())) {
            rawEntity.setParentNodeUUID(prodPlanSupplyWarehouseUIModel.getParentNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodPlanSupplyWarehouseUIModel.getRootNodeUUID())) {
            rawEntity.setRootNodeUUID(prodPlanSupplyWarehouseUIModel.getRootNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodPlanSupplyWarehouseUIModel.getClient())) {
            rawEntity.setClient(prodPlanSupplyWarehouseUIModel.getClient());
        }
        rawEntity.setRefUUID(prodPlanSupplyWarehouseUIModel.getRefUUID());
    }

    /**
     * [Internal method] Convert from UI model to se model:productionPlan
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToProductionPlan(ProductionPlanUIModel productionPlanUIModel, ProductionPlan rawEntity) {
        if (!ServiceEntityStringHelper.checkNullString(productionPlanUIModel.getUuid())) {
            rawEntity.setUuid(productionPlanUIModel.getUuid());
        }
        if (!ServiceEntityStringHelper.checkNullString(productionPlanUIModel.getParentNodeUUID())) {
            rawEntity.setParentNodeUUID(productionPlanUIModel.getParentNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(productionPlanUIModel.getRootNodeUUID())) {
            rawEntity.setRootNodeUUID(productionPlanUIModel.getRootNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(productionPlanUIModel.getClient())) {
            rawEntity.setClient(productionPlanUIModel.getClient());
        }
        if (!ServiceEntityStringHelper.checkNullString(productionPlanUIModel.getPlanStartPrepareDate())) {
            try {
                rawEntity.setPlanStartPrepareDate(
                        DefaultDateFormatConstant.DATE_FORMAT.parse(productionPlanUIModel.getPlanStartPrepareDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!ServiceEntityStringHelper.checkNullString(productionPlanUIModel.getPlanEndTime())) {
            try {
                rawEntity.setPlanEndTime(DefaultDateFormatConstant.DATE_FORMAT.parse(productionPlanUIModel.getPlanEndTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setPriorityCode(productionPlanUIModel.getPriorityCode());
        rawEntity.setRefUnitUUID(productionPlanUIModel.getRefUnitUUID());
        rawEntity.setRefMaterialSKUUUID(productionPlanUIModel.getRefMaterialSKUUUID());
        rawEntity.setName(productionPlanUIModel.getName());
        rawEntity.setUuid(productionPlanUIModel.getUuid());
        if (!ServiceEntityStringHelper.checkNullString(productionPlanUIModel.getRefBillOfMaterialUUID())) {
            rawEntity.setRefBillOfMaterialUUID(productionPlanUIModel.getRefBillOfMaterialUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(productionPlanUIModel.getApproveBy())) {
            rawEntity.setApproveBy(productionPlanUIModel.getApproveBy());
        }
        if (!ServiceEntityStringHelper.checkNullString(productionPlanUIModel.getApproveTime())) {
            try {
                rawEntity.setApproveTime(DefaultDateFormatConstant.DATE_FORMAT.parse(productionPlanUIModel.getApproveTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!ServiceEntityStringHelper.checkNullString(productionPlanUIModel.getCountApproveBy())) {
            rawEntity.setCountApproveBy(productionPlanUIModel.getCountApproveBy());
        }
        if (!ServiceEntityStringHelper.checkNullString(productionPlanUIModel.getCountApproveTime())) {
            try {
                rawEntity
                        .setCountApproveTime(DefaultDateFormatConstant.DATE_FORMAT.parse(productionPlanUIModel.getCountApproveTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!ServiceEntityStringHelper.checkNullString(productionPlanUIModel.getRefMainProdOrderUUID())) {
            rawEntity.setRefMainProdOrderUUID(productionPlanUIModel.getRefMainProdOrderUUID());
        }
        rawEntity.setId(productionPlanUIModel.getId());
        rawEntity.setComLeadTime(productionPlanUIModel.getComLeadTime());
        rawEntity.setCategory(productionPlanUIModel.getCategory());
        rawEntity.setSelfLeadTime(productionPlanUIModel.getSelfLeadTime());
        rawEntity.setNote(productionPlanUIModel.getNote());
        rawEntity.setProductionBatchNumber(productionPlanUIModel.getProductionBatchNumber());
        if (!ServiceEntityStringHelper.checkNullString(productionPlanUIModel.getPlanStartTime())) {
            try {
                rawEntity.setPlanStartTime(DefaultDateFormatConstant.DATE_FORMAT.parse(productionPlanUIModel.getPlanStartTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!ServiceEntityStringHelper.checkNullString(productionPlanUIModel.getActualEndTime())) {
            try {
                rawEntity.setActualEndTime(DefaultDateFormatConstant.DATE_FORMAT.parse(productionPlanUIModel.getActualEndTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setClient(productionPlanUIModel.getClient());
        rawEntity.setAmount(productionPlanUIModel.getAmount());
        if (!ServiceEntityStringHelper.checkNullString(productionPlanUIModel.getActualStartTime())) {
            try {
                rawEntity.setActualStartTime(DefaultDateFormatConstant.DATE_FORMAT.parse(productionPlanUIModel.getActualStartTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
    }

    public Map<Integer, String> initPriorityCodeMap(String languageCode) throws ServiceEntityInstallationException {
        return standardPriorityProxy.getPriorityMap(languageCode);
    }

    public Map<Integer, String> initStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode, this.statusMapLan, ProductionPlanUIModel.class, IDocumentNodeFieldConstant.STATUS);
    }


    public Map<Integer, String> initItemStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return productionPlanItemManager.initItemStatusMap(languageCode);
    }

    public Map<Integer, String> initCategoryMap(String languageCode) throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode, this.categoryMapLan, ProductionPlanUIModel.class, "category");
    }

    public Map<Integer, String> initDocumentTypeMap(String languageCode) throws ServiceEntityInstallationException {
        return serviceDocumentComProxy.getDocumentTypeMap(true, languageCode);
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        // TODO-DAO: super.setServiceEntityDAO(productionPlanDAO);
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(productionPlanConfigureProxy);
    }


    /**
     * [Internal method] Convert from UI model to SE
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToProdPlanItemReqProposal(ProdPlanItemReqProposalUIModel prodPlanItemReqProposalUIModel,
                                                ProdPlanItemReqProposal rawEntity) {
        if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getUuid())) {
            rawEntity.setUuid(prodPlanItemReqProposalUIModel.getUuid());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getParentNodeUUID())) {
            rawEntity.setParentNodeUUID(prodPlanItemReqProposalUIModel.getParentNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getRootNodeUUID())) {
            rawEntity.setRootNodeUUID(prodPlanItemReqProposalUIModel.getRootNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getClient())) {
            rawEntity.setClient(prodPlanItemReqProposalUIModel.getClient());
        }
        rawEntity.setRefUUID(prodPlanItemReqProposalUIModel.getRefUUID());
        if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getPlanStartPrepareDate())) {
            try {
                rawEntity.setPlanStartPrepareDate(
                        DefaultDateFormatConstant.DATE_FORMAT.parse(prodPlanItemReqProposalUIModel.getPlanStartPrepareDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getActualStartDate())) {
            try {
                rawEntity.setActualStartDate(
                        DefaultDateFormatConstant.DATE_FORMAT.parse(prodPlanItemReqProposalUIModel.getActualStartDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setSelfLeadTime(prodPlanItemReqProposalUIModel.getSelfLeadTime());
        rawEntity.setRefUnitUUID(prodPlanItemReqProposalUIModel.getRefUnitUUID());
        if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getRefWarehouseUUID())) {
            rawEntity.setRefWarehouseUUID(prodPlanItemReqProposalUIModel.getRefWarehouseUUID());
        }
        rawEntity.setRefMaterialSKUUUID(prodPlanItemReqProposalUIModel.getRefMaterialSKUUUID());
        rawEntity.setRefBOMItemUUID(prodPlanItemReqProposalUIModel.getRefBOMItemUUID());
        rawEntity.setRefUUID(prodPlanItemReqProposalUIModel.getRefUUID());
        rawEntity.setRefNodeName(prodPlanItemReqProposalUIModel.getRefNodeName());
        if (prodPlanItemReqProposalUIModel.getItemStatus() > 0) {
            rawEntity.setItemStatus(prodPlanItemReqProposalUIModel.getItemStatus());
        }

        if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getPlanStartDate())) {
            try {
                rawEntity.setPlanStartDate(
                        DefaultDateFormatConstant.DATE_FORMAT.parse(prodPlanItemReqProposalUIModel.getPlanStartDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setAmount(prodPlanItemReqProposalUIModel.getAmount());
        if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getActualStartPrepareDate())) {
            try {
                rawEntity.setActualStartPrepareDate(
                        DefaultDateFormatConstant.DATE_FORMAT.parse(prodPlanItemReqProposalUIModel.getActualStartPrepareDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setComLeadTime(prodPlanItemReqProposalUIModel.getComLeadTime());
        rawEntity.setItemIndex(prodPlanItemReqProposalUIModel.getItemIndex());
        if (prodPlanItemReqProposalUIModel.getDocumentType() > 0) {
            rawEntity.setDocumentType(prodPlanItemReqProposalUIModel.getDocumentType());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convWarehouseToItemReqProposalUI(Warehouse warehouse,
                                                 ProdPlanItemReqProposalUIModel prodPlanItemReqProposalUIModel) {
        if (warehouse != null) {
            prodPlanItemReqProposalUIModel.setRefWarehouseId(warehouse.getId());
            prodPlanItemReqProposalUIModel.setRefWarehouseName(warehouse.getName());
        }
    }

    public void convDocumentToItemReqProposalUI(ServiceEntityNode serviceEntityNode,
                                                ProdPlanItemReqProposalUIModel prodPlanItemReqProposalUIModel) {
        if (serviceEntityNode != null) {
            prodPlanItemReqProposalUIModel.setRefDocumentId(serviceEntityNode.getId());
            prodPlanItemReqProposalUIModel.setRefDocumentName(serviceEntityNode.getName());
            if(serviceEntityNode instanceof DocumentContent ){
                DocumentContent documentContent = (DocumentContent) serviceEntityNode;
                prodPlanItemReqProposalUIModel.setRefDocumentStatus(documentContent.getStatus());
            }
        }
    }

    /**
     * Core Logic to set production order status:[Complete]
     *
     * @param productionPlan
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     * @throws ProductionPlanException
     */
    public synchronized void setPlanComplete(ProductionPlan productionPlan, String logonUserUUID,
                                            String organizationUUID)
            throws ServiceEntityConfigureException, ProductionPlanException {
        /*
         * [Step1] Set status of production plan.
         */
        List<ServiceEntityNode> allSubProductionOrderList = this.getAllSubProductionOrderList(productionPlan.getUuid(), productionPlan.getClient());
        if (ServiceCollectionsHelper.checkNullList(allSubProductionOrderList)) {
            throw new ProductionPlanException(ProductionPlanException.PARA_NO_PRODPLAN, productionPlan.getId());
        }
        for (ServiceEntityNode seNode : allSubProductionOrderList) {
            ProductionOrder productionOrder = (ProductionOrder) seNode;
            // TODO with pacakge this logic
            if (productionOrder.getStatus() != ProductionOrder.STATUS_FINISHED || productionOrder.getStatus() != ProductionOrder.STATUS_PROCESS_DONE) {
                throw new ProductionPlanException(ProductionPlanException.PARA_NODONE_ORDER, productionOrder.getId());
            }
        }
        //productionPlan.setStatus(ProductionPlan.STATUS_PROCESSDONE);
        productionPlan.setActualEndTime(java.time.LocalDateTime.now());
        this.updateSENode(productionPlan, logonUserUUID, organizationUUID);

    }

    public List<ServiceEntityNode> getAllSubProductionOrderList(String refPlanUUID, String client)
            throws ServiceEntityConfigureException {
        List<ServiceEntityNode> productionOrderList = productionOrderManager
                .getEntityNodeListByKey(refPlanUUID, "refPlanUUID", ProductionOrder.NODENAME, client, null);
        return productionOrderList;
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convOutBillOfMaterialOrderToUI(BillOfMaterialOrder outBillOfMaterialOrder,
                                               ProductionPlanUIModel productionPlanUIModel) {
        if (outBillOfMaterialOrder != null) {
            productionPlanUIModel.setRefBillOfMaterialId(outBillOfMaterialOrder.getId());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convApproveByToUI(LogonUser logonUser, ProductionPlanUIModel productionPlanUIModel) {
        if (logonUser != null) {
            productionPlanUIModel.setApproveById(logonUser.getId());
            productionPlanUIModel.setApproveByName(logonUser.getName());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convCountApproveByToUI(LogonUser logonUser, ProductionPlanUIModel productionPlanUIModel) {
        if (logonUser != null) {
            productionPlanUIModel.setCountApproveById(logonUser.getId());
            productionPlanUIModel.setCountApproveByName(logonUser.getName());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convMainProductionOrderToUI(ProductionOrder productionOrder, ProductionPlanUIModel productionPlanUIModel) {
        if (productionOrder != null) {
            productionPlanUIModel.setRefMainProdOrderId(productionOrder.getId());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convItemBillOfMaterialItemToUI(BillOfMaterialItem itemBillOfMaterialItem,
                                               ProductionPlanItemUIModel productionPlanItemUIModel) {
        if (itemBillOfMaterialItem != null) {
            productionPlanItemUIModel.setRefBOMItemUUID(itemBillOfMaterialItem.getUuid());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convWarehouseToUI(Warehouse warehouse, ProdPlanSupplyWarehouseUIModel prodPlanSupplyWarehouseUIModel) {
        if (warehouse != null) {
            prodPlanSupplyWarehouseUIModel.setRefWarehouseId(warehouse.getId());
            prodPlanSupplyWarehouseUIModel.setRefWarehouseName(warehouse.getName());
        }
    }

    public void convOutMaterialStockKeepUnitToUI(MaterialStockKeepUnit outMaterialStockKeepUnit,
                                                 ProductionPlanUIModel productionPlanUIModel) {
        convOutMaterialStockKeepUnitToUI(outMaterialStockKeepUnit, productionPlanUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convOutMaterialStockKeepUnitToUI(MaterialStockKeepUnit outMaterialStockKeepUnit,
                                                 ProductionPlanUIModel productionPlanUIModel, LogonInfo logonInfo) {
        if (outMaterialStockKeepUnit != null) {
            productionPlanUIModel.setRefMaterialSKUId(outMaterialStockKeepUnit.getId());
            productionPlanUIModel.setRefMaterialSKUName(outMaterialStockKeepUnit.getName());
            productionPlanUIModel.setPackageStandard(outMaterialStockKeepUnit.getPackageStandard());
            productionPlanUIModel.setPackageStandard(outMaterialStockKeepUnit.getPackageStandard());
            productionPlanUIModel.setSupplyType(outMaterialStockKeepUnit.getSupplyType());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> supplyTypeMap = materialStockKeepUnitManager.initSupplyTypeMap(logonInfo.getLanguageCode());
                    productionPlanUIModel.setSupplyTypeValue(supplyTypeMap.get(outMaterialStockKeepUnit.getSupplyType()));
                } catch (ServiceEntityInstallationException e) {
                    // log error and continue
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
        }
    }

    public ProductionPlanManager() {
        super.seConfigureProxy = new ProductionPlanConfigureProxy();
        // TODO-DAO: super.serviceEntityDAO = new ProductionPlanDAO();
    }

    @Override
    public ServiceEntityNode newRootEntityNode(String client) throws ServiceEntityConfigureException {
        ProductionPlan productionPlan = (ProductionPlan) super.newRootEntityNode(client);
        String productionPlanID = productionPlanIdHelper.genDefaultId(client);
        productionPlan.setId(productionPlanID);
        return productionPlan;
    }

    public ProductionPlan getProductionPlanByMainOrder(String orderUUID, String client) throws ServiceEntityConfigureException {
        ProductionPlan productionPlan = (ProductionPlan) getEntityNodeByKey(orderUUID,
                ProductionPlan.FIELD_RefMainProdOrderUUID, ProductionPlan.NODENAME, client, null);
        return productionPlan;
    }



    /**
     * Logic of cancel this document
     */
    public void cancelDocument(ProductionPlan productionPlan, String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException, SearchConfigureException, NodeNotFoundException,
            ServiceEntityInstallationException {
        /*
         * [Step1] Set the ProductionPlan status as [cancel]
         */
        productionPlan.setStatus(DocumentContent.STATUS_CANCELED);
        this.updateSENode(productionPlan, logonUserUUID, organizationUUID);
    }

    public void genFinAccountCore(String resourceID, ProductionPlan productionPlan, LogonUser logonUser, Organization organization)
            throws SystemResourceException, FinanceAccountValueProxyException, ServiceEntityConfigureException {
        /*
         * [Step1] set the status into [in-settle]
         */
        productionPlan.setStatus(ProductionPlan.STATUS_APPROVED);
        updateSENode(productionPlan, logonUser.getUuid(), organization.getUuid());
        /*
         * [Step2] update the finance account
         */
        systemResourceFinanceAccountProxy.updateFinAccByResourceID(productionPlan, resourceID, productionPlan.getUuid(),
                DefFinanceControllerResource.PROCESS_CODE_SAVE, logonUser.getUuid(), organization.getRefFinOrgUUID(),
                organization.getUuid(), logonUser.getClient());
    }


    public ServiceModule convertToProductionPlanServiceModel(ProductionPlan productionPlan,
                                                             List<ServiceEntityNode> rawProdOrderItemList) throws ServiceEntityConfigureException {

        ProductionPlanServiceModel productionPlanServiceModel = new ProductionPlanServiceModel();
        productionPlanServiceModel.setProductionPlan(productionPlan);
        List<ServiceEntityNode> subProdItemList = ServiceCollectionsHelper
                .filterSENodeByParentUUID(productionPlan.getUuid(), rawProdOrderItemList);
        List<ProductionPlanItemServiceModel> productionPlanItemList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(subProdItemList)) {
            for (ServiceEntityNode seNode : subProdItemList) {
                if (IServiceModelConstants.ProductionPlanItem.equals(seNode.getNodeName())) {
                    ProductionPlanItem productionPlanItem = (ProductionPlanItem) seNode;
                    ProductionPlanItemServiceModel subProductionPlanItemServiceModel = generateSubProductionPlanItem(
                            productionPlanItem, rawProdOrderItemList);
                    productionPlanItemList.add(subProductionPlanItemServiceModel);
                }
            }
            productionPlanServiceModel.setProductionPlanItemList(productionPlanItemList);
        }
        // should add ref warehouse list
        List<ServiceEntityNode> prodPlanSupplyWarehouseList = getEntityNodeListByKey(productionPlan.getUuid(),
                IServiceEntityNodeFieldConstant.ROOTNODEUUID, ProdPlanSupplyWarehouse.NODENAME, productionPlan.getClient(), null);
        productionPlanServiceModel.setProdPlanSupplyWarehouseList(prodPlanSupplyWarehouseList);
        return productionPlanServiceModel;
    }

    protected ProductionPlanItemServiceModel generateSubProductionPlanItem(ProductionPlanItem productionPlanItem,
                                                                           List<ServiceEntityNode> rawProdOrderItemList) {
        List<ProductionPlanItemServiceModel> subProductionPlanItemList = new ArrayList<>();
        List<ProdPlanItemReqProposalServiceModel> prodPlanItemReqProposalList = new ArrayList<>();
        List<ServiceEntityNode> subProdItemList = ServiceCollectionsHelper
                .filterSENodeByParentUUID(productionPlanItem.getUuid(), rawProdOrderItemList);
        ProductionPlanItemServiceModel productionPlanItemServiceModel = new ProductionPlanItemServiceModel();
        productionPlanItemServiceModel.setProductionPlanItem(productionPlanItem);
        if (!ServiceCollectionsHelper.checkNullList(subProdItemList)) {
            for (ServiceEntityNode seNode : subProdItemList) {
                if (IServiceModelConstants.ProductionPlanItem.equals(seNode.getNodeName())) {
                    // In case this node is 'ProductionPlanItem'
                    ProductionPlanItem subProductionPlanItem = (ProductionPlanItem) seNode;
                    ProductionPlanItemServiceModel subProductionPlanItemServiceModel = generateSubProductionPlanItem(
                            subProductionPlanItem, rawProdOrderItemList);
                    subProductionPlanItemList.add(subProductionPlanItemServiceModel);
                }
                if (ProdPlanItemReqProposal.NODENAME.equals(seNode.getNodeName())) {
                    ProdPlanItemReqProposal prodPlanItemReqProposal = (ProdPlanItemReqProposal) seNode;
                    ProdPlanItemReqProposalServiceModel prodPlanItemReqProposalServiceModel = generateSubProdPlanItemReqProposalServiceModel(
                            prodPlanItemReqProposal, rawProdOrderItemList);
                    prodPlanItemReqProposalList.add(prodPlanItemReqProposalServiceModel);
                }
            }
            //			productionPlanItemServiceModel
            //					.setSubProductionPlanItemList(subProductionPlanItemList);
            productionPlanItemServiceModel.setProdPlanItemReqProposalList(prodPlanItemReqProposalList);
        }
        return productionPlanItemServiceModel;
    }

    protected ProdPlanItemReqProposalServiceModel generateSubProdPlanItemReqProposalServiceModel(
            ProdPlanItemReqProposal prodPlanItemReqProposal, List<ServiceEntityNode> rawProdOrderItemList) {
        List<ProductionPlanItemServiceModel> subProductionPlanItemList = new ArrayList<>();
        List<ServiceEntityNode> subProdItemList = ServiceCollectionsHelper
                .filterSENodeByParentUUID(prodPlanItemReqProposal.getUuid(), rawProdOrderItemList);
        ProdPlanItemReqProposalServiceModel prodPlanItemReqProposalServiceModel = new ProdPlanItemReqProposalServiceModel();
        prodPlanItemReqProposalServiceModel.setProdPlanItemReqProposal(prodPlanItemReqProposal);
        if (!ServiceCollectionsHelper.checkNullList(subProdItemList)) {
            for (ServiceEntityNode seNode : subProdItemList) {
                if (IServiceModelConstants.ProductionPlanItem.equals(seNode.getNodeName())) {
                    // In case this node is 'ProductionPlanItem'
                    ProductionPlanItem subProductionPlanItem = (ProductionPlanItem) seNode;
                    ProductionPlanItemServiceModel subProductionPlanItemServiceModel = generateSubProductionPlanItem(
                            subProductionPlanItem, rawProdOrderItemList);
                    subProductionPlanItemList.add(subProductionPlanItemServiceModel);
                }
            }
            prodPlanItemReqProposalServiceModel.setSubProductionPlanItemList(subProductionPlanItemList);
        }
        return prodPlanItemReqProposalServiceModel;
    }

    private void updateRecentBOMOrderToPlan(ProductionPlan productionPlan) throws BillOfMaterialException, ServiceEntityConfigureException {
        BillOfMaterialOrder billOfMaterialOrder =
                billOfMaterialOrderManager.switchToRecentActiveBOMOrder(productionPlan.getRefBillOfMaterialUUID(),
                        productionPlan.getClient(), false);
        if(billOfMaterialOrder.getUuid().equals(productionPlan.getRefBillOfMaterialUUID())){
            // in case need to be updated
            productionPlan.setRefBillOfMaterialUUID(billOfMaterialOrder.getUuid());
        }
    }


    /**
     * Main Entrance and logic to release Production Plan to production orders,
     * picking orders, as well as sub orders.
     * <p>
     * Including logic to update productionPlan status and logic to refresh to
     * latest production plan resources
     *
     * @param productionPlanServiceModel
     * @throws ServiceEntityInstallationException
     * @throws NodeNotFoundException
     * @throws SearchConfigureException
     * @throws ProductionPlanException
     * @throws MaterialException
     * @throws BillOfMaterialException
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     * @throws ProductionOrderException
     */
    public synchronized void releasePlanService(ProductionPlanServiceModel productionPlanServiceModel,
                                                SerialLogonInfo serialLogonInfo, boolean refreshPlanFlag)
            throws BillOfMaterialException, MaterialException, ProductionPlanException, SearchConfigureException,
            ServiceEntityConfigureException, NodeNotFoundException, ServiceEntityInstallationException,
            ServiceModuleProxyException, ProductionOrderException, ServiceComExecuteException, AuthorizationException, LogonInfoException, DocActionException {
        boolean localRefreshFlag = refreshPlanFlag;
        ProductionPlan productionPlan = productionPlanServiceModel.getProductionPlan();
        LogonInfo logonInfo = logonInfoManager.genLogonInfo(serialLogonInfo, false);
        if (!localRefreshFlag) {
            // In case no production plan exist, then have to generate resources
            // in mandatory way
            if (ServiceCollectionsHelper.checkNullList(productionPlanServiceModel.getProductionPlanItemList())) {
                localRefreshFlag = true;
            } else {
                deletePlanSubResource(productionPlanServiceModel.getProductionPlan().getUuid(), serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID(),
                        productionPlanServiceModel.getProductionPlan().getClient());
                localRefreshFlag = true;
            }
            /*
             * [Step2] Refresh BOM Order to recent active
             */
            updateRecentBOMOrderToPlan(productionPlan);
        }
        if (localRefreshFlag) {
            // In case need to refresh plan resource
            List<ServiceEntityNode> rawProdOrderItemList = generateProductItemListEntry(
                    productionPlanServiceModel.getProductionPlan(), logonInfo, true);
            productionPlanServiceModel = (ProductionPlanServiceModel) convertToProductionPlanServiceModel(
                    productionPlanServiceModel.getProductionPlan(), rawProdOrderItemList);
            updateServiceModuleWithDelete(ProductionPlanServiceModel.class, productionPlanServiceModel, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
        }
        productionReleaseProxy.releaseProductionPlanWrapper(productionPlanServiceModel, logonInfo);
        /*
         * [Step3]update production plan status
         */
        updateServiceModuleWithDelete(ProductionPlanServiceModel.class, productionPlanServiceModel, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
    }

    /**
     * Main Entry method to generate all production order item list as well as
     * out-bound delivery and sub production order list by Root instance of
     * production order
     *
     * @return
     * @throws ServiceEntityConfigureException
     * @throws BillOfMaterialException
     * @throws MaterialException
     * @throws ProductionPlanException
     * @throws ServiceEntityInstallationException
     * @throws NodeNotFoundException
     * @throws SearchConfigureException
     */
    public List<ServiceEntityNode> generateProductItemListEntry(ProductionPlan productionPlan, LogonInfo logonInfo, boolean executeFlag)
            throws ServiceEntityConfigureException, BillOfMaterialException, MaterialException, ProductionPlanException,
            SearchConfigureException, NodeNotFoundException, ServiceEntityInstallationException,
            ServiceComExecuteException, AuthorizationException, LogonInfoException {
        /*
         * [Step1] Data prepare: compound productive BOM list
         */
        BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager
                .getRefBillOfMaterialOrderWrapper(productionPlan.getRefBillOfMaterialUUID(), productionPlan.getClient());
        if (billOfMaterialOrder == null) {
            throw new BillOfMaterialException(BillOfMaterialException.PARA_NO_BOMOrder, productionPlan.getRefBillOfMaterialUUID());
        }
        List<ServiceEntityNode> productiveBOMList = billOfMaterialOrderManager.genProductiveBOMModel(billOfMaterialOrder);
        if (productiveBOMList == null || productiveBOMList.size() == 0) {
            return null;
        }
        // All Material list
        List<String> materialUUIDList = new ArrayList<>();
        materialUUIDList.add(productionPlan.getRefMaterialSKUUUID());
        for (ServiceEntityNode seNode : productiveBOMList) {
            ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
            materialUUIDList.add(productiveBOMItem.getRefMaterialSKUUUID());
        }
        MaterialStockKeepUnitSearchModel searchModel = new MaterialStockKeepUnitSearchModel();
        searchModel.setUuid(ServiceEntityStringHelper.convStringListIntoMultiStringValue(materialUUIDList));
        SearchContext searchContext = SearchContextBuilder.genBuilder(logonInfo).searchModel(searchModel).build();
        searchContext.setFieldNameArray(new String[]{IServiceEntityNodeFieldConstant.UUID});
        List<ServiceEntityNode> rawMaterialSKUList = materialStockKeepUnitManager
                .getSearchProxy().searchDocList(searchContext).getResultList();
        // update material SKU list instance to buffer
        if (!ServiceCollectionsHelper.checkNullList(rawMaterialSKUList)) {
            for (ServiceEntityNode seNode : rawMaterialSKUList) {
                materialStockKeepUnitManager.updateBuffer(seNode);
            }
        }
        /*
         * [Step2] Data prepare: rawStoreItemList
         */
        List<ServiceEntityNode> rawStoreItemList = new ArrayList<>();
        List<ServiceEntityNode> prodSupplyWarehouseList = getEntityNodeListByKey(productionPlan.getUuid(),
                IServiceEntityNodeFieldConstant.ROOTNODEUUID, ProdPlanSupplyWarehouse.NODENAME, productionPlan.getClient(), null);

        /*
         * [Step3] Generate the
         */
        List<String> warehouseUUIDList = null;
        try {
            warehouseUUIDList = ServiceCollectionsHelper.pluckList(prodSupplyWarehouseList, IReferenceNodeFieldConstant.REFUUID);
            rawStoreItemList = warehouseStoreItemManager.getInStockStoreItemList(warehouseUUIDList, productionPlan.getClient());
            List<ServiceEntityNode> rawStoreItemListBack = ServiceCollectionsHelper.cloneSEList(rawStoreItemList);
            return generateProductItemListProposal(productionPlan, billOfMaterialOrder, prodSupplyWarehouseList, productiveBOMList,
                    rawStoreItemListBack, rawMaterialSKUList);
        } catch (NoSuchFieldException e) {
            throw new ProductionPlanException(ProductionPlanException.PARA_SYSTEM_ERROR, e.getMessage());
        }

    }

    private double getRatioFromProductionToBOMOrder(ProductionPlan productionPlan, BillOfMaterialOrder billOfMaterialOrder)
            throws MaterialException, ServiceEntityConfigureException {
        StorageCoreUnit prodOrderStorageCoreUnit = new StorageCoreUnit();
        prodOrderStorageCoreUnit.setAmount(productionPlan.getAmount());
        prodOrderStorageCoreUnit.setRefMaterialSKUUUID(productionPlan.getRefMaterialSKUUUID());
        prodOrderStorageCoreUnit.setRefUnitUUID(productionPlan.getRefUnitUUID());
        StorageCoreUnit tmpBomStorageUnit = new StorageCoreUnit();
        tmpBomStorageUnit.setRefUnitUUID(billOfMaterialOrder.getRefUnitUUID());
        tmpBomStorageUnit.setAmount(billOfMaterialOrder.getAmount());
        tmpBomStorageUnit.setRefMaterialSKUUUID(billOfMaterialOrder.getRefMaterialSKUUUID());
        return materialStockKeepUnitManager
                .getStorageUnitRatio(prodOrderStorageCoreUnit, tmpBomStorageUnit, productionPlan.getClient());
    }

    public static double calculateSumProposalAmount(List<ServiceEntityNode> reqProposalList) {
        if (ServiceCollectionsHelper.checkNullList(reqProposalList)) {
            return 0;
        }
        double sumAmount = 0;
        for (ServiceEntityNode seNode : reqProposalList) {
            ProdItemReqProposalTemplate prodItemReqProposalTemplate = (ProdItemReqProposalTemplate) seNode;
            sumAmount += prodItemReqProposalTemplate.getAmount();
        }
        return sumAmount;
    }

    /**
     * Logic for generate the production item proposal including the sub
     * requirement for out-bound delivery and production or purchase requirement
     *
     * @param productionPlan
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     * @throws ProductionPlanException
     */
    public List<ServiceEntityNode> generateProductItemListProposal(ProductionPlan productionPlan,
                                                                   BillOfMaterialOrder billOfMaterialOrder, List<ServiceEntityNode> prodSupplyWarehouseList,
                                                                   List<ServiceEntityNode> productiveBOMList, List<ServiceEntityNode> rawStoreItemList,
                                                                   List<ServiceEntityNode> rawMaterialList) throws ServiceEntityConfigureException, MaterialException, ProductionPlanException, ServiceComExecuteException {
        if (ServiceCollectionsHelper.checkNullList(productiveBOMList)) {
            return null;
        }
        /*
         * [Step1] Calculate the base ratio from production order to BOM
         */
        double ratio = getRatioFromProductionToBOMOrder(productionPlan, billOfMaterialOrder);
        List<ServiceEntityNode> productionProposalItemList = new ArrayList<>();
        /*
         * [Step2] Traverse from first BOM layer into the footer layer to
         * calculate each item required amount
         */
        List<ServiceEntityNode> firstBomLayerList = billOfMaterialOrderManager.filterBOMItemListByLayer(1, productiveBOMList);
        for (ServiceEntityNode seNode : firstBomLayerList) {
            /*
             * [Step2] Calculate the first BOM layer and generate the relative
             * production　order item
             */
            ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
            double amount = productiveBOMItem.getAmount() * ratio;
            // Calculate the amount with loss rate
            double amountWithLossRate = amount / (1 - productiveBOMItem.getTheoLossRate() / 100);
            amountWithLossRate = Math.ceil(amountWithLossRate);
            // Update the amount of Productive BOM item
            productiveBOMItem.setAmountWithLossRate(amountWithLossRate);
            ProductionPlanItem productionPlanItem = (ProductionPlanItem) newEntityNode(productionPlan, ProductionPlanItem.NODENAME);
            productionPlanItem.setAmount(amount);
            productionPlanItem.setActualAmount(amountWithLossRate);
            productionPlanItem.setAmountWithLossRate(amountWithLossRate);
            productionPlanItem.setRefMaterialSKUUUID(productiveBOMItem.getRefMaterialSKUUUID());
            productionPlanItem.setRefUnitUUID(productiveBOMItem.getRefUnitUUID());
            productionPlanItem.setRefBOMItemUUID(productiveBOMItem.getUuid());
            productionPlanItem.setProductionBatchNumber(productionPlan.getProductionBatchNumber());
            productionProposalItemList.add(productionPlanItem);
            /*
             * [Step3]Calculate the current storage
             */
            if (prodSupplyWarehouseList != null && prodSupplyWarehouseList.size() > 0) {
                for (ServiceEntityNode tmpSENode : prodSupplyWarehouseList) {
                    ProdPlanSupplyWarehouse prodPlanSupplyWarehouse = (ProdPlanSupplyWarehouse) tmpSENode;
                    List<ServiceEntityNode> outReqProposalList = generateOutboundDeliveryProposal(productionPlanItem,
                            productiveBOMItem, prodPlanSupplyWarehouse.getRefUUID(), rawStoreItemList);
                    if (outReqProposalList != null && outReqProposalList.size() > 0) {
                        productionProposalItemList.addAll(outReqProposalList);
                    }
                    /*
                     * [Step1.5] Calculate Available amount for production order
                     * item
                     */
                    double availableAmount = calculateSumProposalAmount(outReqProposalList);
                    productionPlanItem.setAvailableAmount(availableAmount);
                    if (productionPlanItem.getItemStatus() == ProductionPlanItem.STATUS_AVAILABLE) {
                        break;
                    }
                }
            }
            /*
             * [Step4] In case the request amount is not satisfied with
             * warehouse store
             */
            if (productiveBOMItem.getAmountWithLossRate() > 0) {
                // generate the purchase or productive order item
                MaterialStockKeepUnit materialStockKeepUnit = materialStockKeepUnitManager
                        .getMaterialSKUWrapper(productiveBOMItem.getRefMaterialSKUUUID(), productiveBOMItem.getClient(),
                                rawMaterialList);
                ProdPlanItemReqProposal prodReqProposal = generateProdPurchaseProposal(productionPlanItem, productiveBOMItem,
                        materialStockKeepUnit);
                if (prodReqProposal != null) {
                    productionProposalItemList.add(prodReqProposal);
                }

                /*
                 * [Step4.5] Process the sub Productive BOM list
                 */
                if (needFurtherBOMPlan(prodReqProposal)) {
                    List<ServiceEntityNode> subProdItemList = generateSubProductionItemUnion(prodReqProposal, productiveBOMItem,
                            productiveBOMList, prodSupplyWarehouseList, rawStoreItemList, rawMaterialList);
                    if (subProdItemList != null && subProdItemList.size() > 0) {
                        productionProposalItemList.addAll(subProdItemList);
                    }
                }
            }
        }
        /*
         * [Step5] Process to calculate lead time for each productive BOM Item
         */
        productionPlanTimeComsumeProxy.calculateProdBOMItemLeadTime(productiveBOMList, rawMaterialList);
        // Also setting the lead time to BOM order
        productionPlanTimeComsumeProxy
                .processSetProductionPlanLeadTime(billOfMaterialOrder, productionPlan, productiveBOMList, rawMaterialList);
        autoCalculatePlanTimeByLeadTime(productionPlan, billOfMaterialOrder, productiveBOMList, productionProposalItemList);
        return productionProposalItemList;
    }

    private void processInitTime(ProductionPlan productionPlan) {
        double maxSubLeadTime = productionPlan.getComLeadTime() - productionPlan.getSelfLeadTime();
        if (productionPlan.getInitTimeMode() == ProductionPlan.INIT_TIMEMODE_PLANEND) {
            if (productionPlan.getPlanEndTime() != null) {
                java.time.LocalDateTime planStartTime = ServiceEntityDateHelper
                        .adjustDays(productionPlan.getPlanEndTime(), -productionPlan.getSelfLeadTime());
                productionPlan.setPlanStartTime(planStartTime);
                java.time.LocalDateTime planStartPrepareDate = ServiceEntityDateHelper.adjustDays(productionPlan.getPlanStartTime(), -maxSubLeadTime);
                productionPlan.setPlanStartPrepareDate(planStartPrepareDate);
                return;
            }
        }
        if (productionPlan.getInitTimeMode() == ProductionPlan.INIT_TIMEMODE_PLANSTART) {
            if (productionPlan.getPlanStartTime() != null) {
                java.time.LocalDateTime planStartPrepareDate = ServiceEntityDateHelper.adjustDays(productionPlan.getPlanStartTime(), -maxSubLeadTime);
                productionPlan.setPlanStartPrepareDate(planStartPrepareDate);
                java.time.LocalDateTime planEndTime = ServiceEntityDateHelper
                        .adjustDays(productionPlan.getPlanStartTime(), productionPlan.getSelfLeadTime());
                productionPlan.setPlanEndTime(planEndTime);
                return;
            }
        }
        if (productionPlan.getInitTimeMode() == ProductionPlan.INIT_TIMEMODE_STARTPREPARE) {
            if (productionPlan.getPlanStartTime() != null) {
                java.time.LocalDateTime planStartTime = ServiceEntityDateHelper.adjustDays(productionPlan.getPlanStartPrepareDate(), maxSubLeadTime);
                productionPlan.setPlanStartTime(planStartTime);
                java.time.LocalDateTime planEndTime = ServiceEntityDateHelper
                        .adjustDays(productionPlan.getPlanStartTime(), productionPlan.getSelfLeadTime());
                productionPlan.setPlanEndTime(planEndTime);
                return;
            }
        }
        // Otherwise, can not get correct time by init time mode, then
        if (productionPlan.getPlanStartPrepareDate() != null) {
            // In case plan start time is not null, then calculate and adjust
            // start prepare date
            java.time.LocalDateTime planStartTime = ServiceEntityDateHelper
                    .adjustDays(productionPlan.getPlanStartPrepareDate(), productionPlan.getComLeadTime());
            productionPlan.setPlanStartTime(planStartTime);
            java.time.LocalDateTime planEndTime = ServiceEntityDateHelper
                    .adjustDays(productionPlan.getPlanStartTime(), productionPlan.getSelfLeadTime());
            productionPlan.setPlanEndTime(planEndTime);
        }

        if (productionPlan.getPlanStartTime() != null) {
            // In case plan start time is not null, then calculate and adjust
            // start prepare date
            java.time.LocalDateTime planStartPrepareDate = ServiceEntityDateHelper
                    .adjustDays(productionPlan.getPlanStartTime(), -productionPlan.getComLeadTime());
            productionPlan.setPlanStartPrepareDate(planStartPrepareDate);
            java.time.LocalDateTime planEndTime = ServiceEntityDateHelper
                    .adjustDays(productionPlan.getPlanStartTime(), productionPlan.getSelfLeadTime());
            productionPlan.setPlanEndTime(planEndTime);
        }
        if (productionPlan.getPlanEndTime() != null) {
            // In case plan start time is not null, then calculate and adjust
            // start prepare date
            java.time.LocalDateTime planStartTime = ServiceEntityDateHelper
                    .adjustDays(productionPlan.getPlanEndTime(), -productionPlan.getSelfLeadTime());
            productionPlan.setPlanStartTime(planStartTime);
            java.time.LocalDateTime planStartPrepareDate = ServiceEntityDateHelper
                    .adjustDays(productionPlan.getPlanStartTime(), -productionPlan.getComLeadTime());
            productionPlan.setPlanStartPrepareDate(planStartPrepareDate);
        }
    }

    /**
     * [Internal method] After self lead time and com lead time is
     *
     * @param productionPlan
     * @param billOfMaterialOrder
     * @param productiveBOMList
     * @param productionProposalItemList
     * @return
     */
    private void autoCalculatePlanTimeByLeadTime(ProductionPlan productionPlan, BillOfMaterialOrder billOfMaterialOrder,
                                                 List<ServiceEntityNode> productiveBOMList, List<ServiceEntityNode> productionProposalItemList) {
        /*
         * [Step1]
         */
        ProductiveBOMOrder productiveBOMOrder = billOfMaterialOrderManager.genInitProductiveBomOrder(billOfMaterialOrder);
        // Process Production plan initial time
        processInitTime(productionPlan);

        productionProposalItemList.add(productiveBOMOrder);
        List<ServiceEntityNode> firstBomLayerList = billOfMaterialOrderManager.filterBOMItemListByLayer(1, productiveBOMList);
        /*
         * [Step2] Process to set the time for each BOM list
         */
        for (ServiceEntityNode seNode : firstBomLayerList) {
            ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
            // Set plan start time
            if (productiveBOMItem.getAmountWithLossRate() == 0) {
                continue;
            }
            java.time.LocalDateTime endPointDate = productionPlan.getPlanStartTime();
            java.time.LocalDateTime planStartPrepareDate = ServiceEntityDateHelper.adjustDays(endPointDate, -productiveBOMItem.getComLeadTime());
            productiveBOMItem.setPlanStartPrepareDate(planStartPrepareDate);
            java.time.LocalDateTime planStartDate = ServiceEntityDateHelper.adjustDays(endPointDate, -productiveBOMItem.getSelfLeadTime());
            productiveBOMItem.setPlanStartDate(planStartDate);
            productiveBOMItem.setPlanEndDate(endPointDate);
            List<ServiceEntityNode> subBOMItemList = billOfMaterialOrderManager
                    .filterSubBOMItemList(productiveBOMItem.getUuid(), productiveBOMList);
            if (subBOMItemList != null && subBOMItemList.size() > 0) {
                setStartDateForBOMItem(productiveBOMItem, subBOMItemList, productiveBOMList, productionProposalItemList);
            }
            /**
             * Find the update the staring time information in proposal
             */
            List<ServiceEntityNode> prodPlanItemReqProposalList = filterItemProposalListByItemUUID(productionProposalItemList,
                    productiveBOMItem.getUuid());
            printProposalList(prodPlanItemReqProposalList);
            if (prodPlanItemReqProposalList != null && prodPlanItemReqProposalList.size() > 0) {
                for (ServiceEntityNode tmpSENode : prodPlanItemReqProposalList) {
                    ProdPlanItemReqProposal prodPlanItemReqProposal = (ProdPlanItemReqProposal) tmpSENode;
                    // TODO check this logic
                    if (prodPlanItemReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
                        // In case proposal is just outbound delivery, then
                        // don't need time
                        prodPlanItemReqProposal.setPlanStartDate(endPointDate);
                        prodPlanItemReqProposal.setPlanEndDate(endPointDate);
                        prodPlanItemReqProposal.setPlanStartPrepareDate(endPointDate);
                        prodPlanItemReqProposal.setSelfLeadTime(0);
                        prodPlanItemReqProposal.setComLeadTime(0);
                    } else {
                        prodPlanItemReqProposal.setPlanStartDate(planStartDate);
                        prodPlanItemReqProposal.setPlanEndDate(endPointDate);
                        prodPlanItemReqProposal.setPlanStartPrepareDate(planStartPrepareDate);

                        prodPlanItemReqProposal
                                .setSelfLeadTime(ServiceEntityDoubleHelper.trancateDoubleScale2(productiveBOMItem.getSelfLeadTime()));
                        prodPlanItemReqProposal
                                .setComLeadTime(ServiceEntityDoubleHelper.trancateDoubleScale2(productiveBOMItem.getComLeadTime()));
                    }

                }
            }
            List<ServiceEntityNode> productionPlanItemList = filterProductionPlanItemListByItemUUID(productionProposalItemList,
                    productiveBOMItem.getUuid());
            printProposalList(prodPlanItemReqProposalList);
            if (productionPlanItemList != null && productionPlanItemList.size() > 0) {
                for (ServiceEntityNode tmpSENode : productionPlanItemList) {
                    ProductionPlanItem productionPlanItem = (ProductionPlanItem) tmpSENode;
                    productionPlanItem.setPlanStartDate(planStartDate);
                    productionPlanItem.setPlanEndDate(endPointDate);
                    productionPlanItem.setPlanStartPrepareDate(planStartPrepareDate);
                    productionPlanItem
                            .setSelfLeadTime(ServiceEntityDoubleHelper.trancateDoubleScale2(productiveBOMItem.getSelfLeadTime()));
                    productionPlanItem
                            .setComLeadTime(ServiceEntityDoubleHelper.trancateDoubleScale2(productiveBOMItem.getComLeadTime()));
                }
            }
        }
    }

    /**
     * Logic to calculate if current Production proposal need further BOM plan
     *
     * @param prodReqProposal
     * @return
     */
    public static boolean needFurtherBOMPlan(ProdPlanItemReqProposal prodReqProposal) {
        if (prodReqProposal != null) {
            // In case prod proposal is [Production Order] Need further BOM Plan
            if (prodReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER) {
                return true;
            }
        }
        return false;
    }

    protected void printProposalList(List<ServiceEntityNode> productionProposalItemList) {

        for (ServiceEntityNode seNode : productionProposalItemList) {
            if (ProdPlanItemReqProposal.NODENAME.equals(seNode.getNodeName())) {
                ProdPlanItemReqProposal prodPlanItemReqProposal = (ProdPlanItemReqProposal) seNode;
                logger.info("Proposal: UUID:" + prodPlanItemReqProposal.getUuid());
                logger.info("   ItemUUID:" + prodPlanItemReqProposal.getRefBOMItemUUID());
            }
            if (ProductionPlanItem.NODENAME.equals(seNode.getNodeName())) {
                ProductionPlanItem productionPlanItem = (ProductionPlanItem) seNode;
                logger.info("ProductionPlanItem: UUID:" + productionPlanItem.getUuid());
                logger.info("   ItemUUID:" + productionPlanItem.getRefBOMItemUUID());
            }
        }
    }

    protected List<ServiceEntityNode> filterItemProposalListByItemUUID(List<ServiceEntityNode> productionProposalItemList,
                                                                       String itemUUID) {
        if (ServiceCollectionsHelper.checkNullList(productionProposalItemList)) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(itemUUID)) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
        for (ServiceEntityNode seNode : productionProposalItemList) {
            if (seNode.getNodeName().equals(ProdPlanItemReqProposal.NODENAME)) {
                ProdPlanItemReqProposal prodPlanItemReqProposal = (ProdPlanItemReqProposal) seNode;
                if (itemUUID.equals(prodPlanItemReqProposal.getRefBOMItemUUID())) {
                    resultList.add(prodPlanItemReqProposal);
                }
            }
        }
        return resultList;
    }

    protected List<ServiceEntityNode> filterProductionPlanItemListByItemUUID(List<ServiceEntityNode> productionProposalItemList,
                                                                             String itemUUID) {
        if (ServiceCollectionsHelper.checkNullList(productionProposalItemList)) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(itemUUID)) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
        for (ServiceEntityNode seNode : productionProposalItemList) {
            if (seNode.getNodeName().equals(ProductionPlanItem.NODENAME)) {
                ProductionPlanItem productionPlanItem = (ProductionPlanItem) seNode;
                if (itemUUID.equals(productionPlanItem.getRefBOMItemUUID())) {
                    resultList.add(productionPlanItem);
                }
            }
        }
        return resultList;
    }

    protected void setStartDateForBOMItem(ProductiveBOMItem parentBOMItem, List<ServiceEntityNode> curBOMItemList,
                                          List<ServiceEntityNode> productiveBOMList, List<ServiceEntityNode> productionPlanItemList) {
        java.time.LocalDateTime endPointDate = parentBOMItem.getPlanStartDate();
        if (ServiceCollectionsHelper.checkNullList(productiveBOMList)) {
            return;
        }
        for (ServiceEntityNode seNode : curBOMItemList) {
            ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
            if (productiveBOMItem.getAmountWithLossRate() == 0) {
                // In case all the requirement has been filled with direct
                // outbound delivery, then just return
                // continue; -->Still need to set lead time and date
            }
            java.time.LocalDateTime planStartPrepareDate = ServiceEntityDateHelper.adjustDays(endPointDate, -productiveBOMItem.getComLeadTime());
            productiveBOMItem.setPlanStartPrepareDate(planStartPrepareDate);
            java.time.LocalDateTime planStartDate = ServiceEntityDateHelper.adjustDays(endPointDate, -productiveBOMItem.getSelfLeadTime());
            productiveBOMItem.setPlanStartDate(planStartDate);
            productiveBOMItem.setPlanEndDate(parentBOMItem.getPlanStartDate());
            List<ServiceEntityNode> subBOMItemList = billOfMaterialOrderManager
                    .filterSubBOMItemList(productiveBOMItem.getUuid(), productiveBOMList);
            if (subBOMItemList != null && subBOMItemList.size() > 0) {
                setStartDateForBOMItem(productiveBOMItem, subBOMItemList, productiveBOMList, productionPlanItemList);
            }
            // Find the update the staring time information in proposal
            List<ServiceEntityNode> prodPlanItemReqProposalList = filterItemProposalListByItemUUID(productionPlanItemList,
                    productiveBOMItem.getUuid());
            printProposalList(prodPlanItemReqProposalList);
            if (prodPlanItemReqProposalList != null && prodPlanItemReqProposalList.size() > 0) {
                for (ServiceEntityNode tmpSENode : prodPlanItemReqProposalList) {
                    ProdPlanItemReqProposal prodPlanItemReqProposal = (ProdPlanItemReqProposal) tmpSENode;
                    if (prodPlanItemReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
                        // In case proposal is just outbound delivery, then
                        // don't need time
                        prodPlanItemReqProposal.setPlanStartDate(endPointDate);
                        prodPlanItemReqProposal.setPlanEndDate(endPointDate);
                        prodPlanItemReqProposal.setPlanStartPrepareDate(endPointDate);
                        prodPlanItemReqProposal.setSelfLeadTime(0);
                        prodPlanItemReqProposal.setComLeadTime(0);
                    } else {
                        prodPlanItemReqProposal.setPlanStartDate(planStartDate);
                        prodPlanItemReqProposal.setPlanEndDate(endPointDate);
                        prodPlanItemReqProposal.setPlanStartPrepareDate(planStartPrepareDate);
                        prodPlanItemReqProposal
                                .setSelfLeadTime(ServiceEntityDoubleHelper.trancateDoubleScale2(productiveBOMItem.getSelfLeadTime()));
                        prodPlanItemReqProposal
                                .setComLeadTime(ServiceEntityDoubleHelper.trancateDoubleScale2(productiveBOMItem.getComLeadTime()));
                    }

                }
            }
        }
    }

    protected List<ServiceEntityNode> generateSubProductionItemUnion(ProdPlanItemReqProposal parentReqProposal,
                                                                     ProductiveBOMItem parentBOMItem, List<ServiceEntityNode> productiveBOMList,
                                                                     List<ServiceEntityNode> prodSupplyWarehouseList, List<ServiceEntityNode> rawStoreItemList,
                                                                     List<ServiceEntityNode> rawMterialSKUList) throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException {
        List<ServiceEntityNode> subBOMItemList = billOfMaterialOrderManager
                .filterSubBOMItemList(parentBOMItem.getUuid(), productiveBOMList);
        if (subBOMItemList == null || subBOMItemList.size() == 0) {
            return null;
        }
        List<ServiceEntityNode> productionPlanItemList = new ArrayList<ServiceEntityNode>();
        /*
         * [Step1] calculate the ratioWithLossRate by comparing parent
         * production item amountWithLossRate to BOM item amount it could
         * calculate the amoutWithLossRate in parent layer
         */
        double ratio = parentReqProposal.getAmount() / parentBOMItem.getAmount();
        for (ServiceEntityNode seNode : subBOMItemList) {
            ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
            ProductionPlanItem productionPlanItem = (ProductionPlanItem) newEntityNode(parentReqProposal,
                    ProductionPlanItem.NODENAME);
            productionPlanItem.setAmount(ratio * productiveBOMItem.getAmount());
            // theory amount with loss rate
            double amountWithLossRate = productionPlanItem.getAmount() / (1 - productiveBOMItem.getTheoLossRate() / 100);
            amountWithLossRate = Math.ceil(amountWithLossRate);
            productionPlanItem.setProductionBatchNumber(parentReqProposal.getProductionBatchNumber());
            productionPlanItem.setAmountWithLossRate(amountWithLossRate);
            productionPlanItem.setActualAmount(amountWithLossRate);
            productiveBOMItem.setAmountWithLossRate(amountWithLossRate);
            productionPlanItem.setRefMaterialSKUUUID(productiveBOMItem.getRefMaterialSKUUUID());
            productionPlanItem.setRefUnitUUID(productiveBOMItem.getRefUnitUUID());
            productionPlanItem.setRefBOMItemUUID(productiveBOMItem.getUuid());
            productionPlanItemList.add(productionPlanItem);
            /*
             * [Step4]Calculate the current storage
             */
            if (prodSupplyWarehouseList != null && prodSupplyWarehouseList.size() > 0) {
                for (ServiceEntityNode tmpSENode : prodSupplyWarehouseList) {
                    ProdPlanSupplyWarehouse prodPlanSupplyWarehouse = (ProdPlanSupplyWarehouse) tmpSENode;
                    List<ServiceEntityNode> outReqProposalList = generateOutboundDeliveryProposal(productionPlanItem,
                            productiveBOMItem, prodPlanSupplyWarehouse.getRefUUID(), rawStoreItemList);
                    if (outReqProposalList != null && outReqProposalList.size() > 0) {
                        productionPlanItemList.addAll(outReqProposalList);
                    }
                    if (productionPlanItem.getItemStatus() == ProductionPlanItem.STATUS_AVAILABLE) {
                        break;
                    }
                }
            }
            if (productiveBOMItem.getAmountWithLossRate() > 0) {
                /*
                 * [Step3] In case the left amount is over than 0, then
                 * recursive call this method to sub layer
                 *
                 */
                MaterialStockKeepUnit materialStockKeepUnit = materialStockKeepUnitManager
                        .getMaterialSKUWrapper(productiveBOMItem.getRefMaterialSKUUUID(), productiveBOMItem.getClient(),
                                rawMterialSKUList);
                ProdPlanItemReqProposal prodPurchaseItemReqProposal = generateProdPurchaseProposal(productionPlanItem,
                        productiveBOMItem, materialStockKeepUnit);
                if (prodPurchaseItemReqProposal != null) {
                    productionPlanItemList.add(prodPurchaseItemReqProposal);
                }
                if (needFurtherBOMPlan(prodPurchaseItemReqProposal)) {
                    List<ServiceEntityNode> subProdItemList = generateSubProductionItemUnion(prodPurchaseItemReqProposal,
                            productiveBOMItem, productiveBOMList, prodSupplyWarehouseList, rawStoreItemList, rawMterialSKUList);
                    if (subProdItemList != null && subProdItemList.size() > 0) {
                        productionPlanItemList.addAll(subProdItemList);
                    }
                }
            }
        }
        return productionPlanItemList;
    }

    protected List<ServiceEntityNode> generateSubProductionItemUnion(ProductionPlanItem parentProdItem,
                                                                     BillOfMaterialItem parentBOMItem, double ratio, List<ServiceEntityNode> rawBomItemList)
            throws ServiceEntityConfigureException, MaterialException {
        List<ServiceEntityNode> subBOMItemList = billOfMaterialOrderManager
                .filterSubBOMItemList(parentBOMItem.getUuid(), rawBomItemList);
        if (subBOMItemList == null || subBOMItemList.size() == 0) {
            return null;
        }
        List<ServiceEntityNode> productionPlanItemList = new ArrayList<ServiceEntityNode>();
        /**
         * [Step1] calculate the ratioWithLossRate by comparing parent
         * production item amountWithLossRate to BOM item amount it could
         * calculate the amoutWithLossRate in parent layer
         */
        StorageCoreUnit prodStorageCoreUnit = new StorageCoreUnit();
        prodStorageCoreUnit.setAmount(parentProdItem.getAmountWithLossRate());
        prodStorageCoreUnit.setRefMaterialSKUUUID(parentProdItem.getRefMaterialSKUUUID());
        prodStorageCoreUnit.setRefUnitUUID(parentProdItem.getRefUnitUUID());
        double ratioWithLossRate = getRatioUnion(prodStorageCoreUnit, parentBOMItem);
        for (ServiceEntityNode seNode : subBOMItemList) {
            BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) seNode;
            ProductionPlanItem productionPlanItem = (ProductionPlanItem) newEntityNode(parentProdItem, ProductionPlanItem.NODENAME);
            productionPlanItem.setAmount(billOfMaterialItem.getAmount() * ratio);
            productionPlanItem.setRefMaterialSKUUUID(billOfMaterialItem.getRefMaterialSKUUUID());
            productionPlanItem.setRefUnitUUID(billOfMaterialItem.getRefUnitUUID());
            /**
             * [Step2] Calculate the key local variable:[pureAmountLossRate]
             * [pureAmountLossRate] is current production item theory amount
             * calculated by amountWithLossRate from last layer
             *
             */
            double pureAmountLossRate = billOfMaterialItem.getAmount() * ratioWithLossRate;
            // Calculate the amount with loss rate by merging this layer's loss
            // rate
            double amountWithLossRate = pureAmountLossRate / (1 - billOfMaterialItem.getTheoLossRate() / 100);
            amountWithLossRate = Math.ceil(amountWithLossRate);
            productionPlanItem.setAmountWithLossRate(amountWithLossRate);
            // Set initial actual amount
            productionPlanItem.setActualAmount(amountWithLossRate);
            productionPlanItemList.add(productionPlanItem);
            /**
             * [Step3] recursive call this method
             */
            List<ServiceEntityNode> subProdItemList = generateSubProductionItemUnion(productionPlanItem, billOfMaterialItem, ratio,
                    rawBomItemList);
            if (subProdItemList != null && subProdItemList.size() > 0) {
                productionPlanItemList.addAll(subProdItemList);
            }
        }
        return productionPlanItemList;
    }


    /**
     * Generate the outbound delivery proposal from each warehouse
     *
     * @param productionPlanItem
     * @param productiveBOMItem
     * @param warehouseUUID
     * @param rawStoreItemList
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    protected List<ServiceEntityNode> generateOutboundDeliveryProposal(ProductionPlanItem productionPlanItem,
                                                                       ProductiveBOMItem productiveBOMItem, String warehouseUUID, List<ServiceEntityNode> rawStoreItemList)
            throws ServiceEntityConfigureException, MaterialException {
        /*
         * [Step1] check the available store item
         */
        List<WarehouseStoreItem> storeItemList = new ArrayList<>();
        if (rawStoreItemList != null && rawStoreItemList.size() > 0) {
            storeItemList = warehouseStoreManager
                    .getStoreItemBySKUWarehouseOnline(productionPlanItem.getRefMaterialSKUUUID(), warehouseUUID, rawStoreItemList);
        } else {
            storeItemList = warehouseStoreItemManager.getInStockStoreItemBySKUWarehouse(productionPlanItem.getRefMaterialSKUUUID(), warehouseUUID);
        }
        if (storeItemList == null || storeItemList.size() == 0) {
            return null;
        }
        List<ServiceEntityNode> prodReqProposalList = new ArrayList<>();
        StorageCoreUnit requestCoreUnit = new StorageCoreUnit();
        requestCoreUnit.setRefMaterialSKUUUID(productionPlanItem.getRefMaterialSKUUUID());
        requestCoreUnit.setRefUnitUUID(productionPlanItem.getRefUnitUUID());
        requestCoreUnit.setAmount(productionPlanItem.getActualAmount());
        /*
         * [Step2] traverse each possible store item list from current warehouse
         */
        for (ServiceEntityNode seNode : storeItemList) {
            WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) seNode;
            if (warehouseStoreItem.getItemStatus() == WarehouseStoreItem.STATUS_ARCHIVE) {
                continue;
            }
            StorageCoreUnit requestCoreUnitBack = (StorageCoreUnit) requestCoreUnit.clone();
            StoreAvailableStoreItemRequest storeAvailableStoreItemRequest = new StoreAvailableStoreItemRequest(warehouseStoreItem, null, true);
            StorageCoreUnit availableStoreCoreUnit = outboundDeliveryWarehouseItemManager.getAvailableStoreItemAmountUnion(
                    storeAvailableStoreItemRequest);
            if (availableStoreCoreUnit.getAmount() <= 0) {
                // In case this store item already been fully occupied with other documents, just skip
                continue;
            }
            try {
                /*
                 * WarehouseStoreItem will not be updated in any case
                 */
                outboundDeliveryWarehouseItemManager.checkAndUpdateWarehouseStoreItemAmountCore(requestCoreUnit,
                        true, storeAvailableStoreItemRequest, availableStoreCoreUnit);
                if (requestCoreUnit.getAmount() == 0) {
                    /*
                     * In case current warehouseStore meet requirement,
                     */
                    ProdPlanItemReqProposal prodPlanItemReqProposal = (ProdPlanItemReqProposal) this
                            .newEntityNode(productionPlanItem, ProdPlanItemReqProposal.NODENAME);
                    if (requestCoreUnitBack.getAmount() <= 0) {
                        // ??? If available amount is 0, just continue;
                        continue;
                    }
                    prodPlanItemReqProposal.setAmount(requestCoreUnitBack.getAmount());
                    prodPlanItemReqProposal.setRefUnitUUID(requestCoreUnitBack.getRefUnitUUID());
                    prodPlanItemReqProposal.setRefMaterialSKUUUID(requestCoreUnitBack.getRefMaterialSKUUUID());
                    prodPlanItemReqProposal.setRefWarehouseUUID(warehouseUUID);
                    prodPlanItemReqProposal.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY);
                    prodPlanItemReqProposal.setStoreAmount(availableStoreCoreUnit.getAmount());
                    prodPlanItemReqProposal.setStoreUnitUUID(availableStoreCoreUnit.getRefUnitUUID());
                    // using next doc pointing to store item
                    prodPlanItemReqProposal.setNextDocType(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM);
                    prodPlanItemReqProposal.setNextDocMatItemUUID(warehouseStoreItem.getUuid());
                    prodPlanItemReqProposal.setRefStoreItemUUID(warehouseStoreItem.getUuid());
                    productionPlanItem.setItemStatus(ProductionPlanItem.STATUS_AVAILABLE);
                    prodPlanItemReqProposal.setRefBOMItemUUID(productiveBOMItem.getUuid());
                    // Set produtive BOM item amount 0, already meet production plan item request
                    productiveBOMItem.setAmountWithLossRate(requestCoreUnit.getAmount());
                    productiveBOMItem.setRefUnitUUID(requestCoreUnit.getRefUnitUUID());
                    prodPlanItemReqProposal.setProductionBatchNumber(productionPlanItem.getProductionBatchNumber());
                    prodReqProposalList.add(prodPlanItemReqProposal);
                    return prodReqProposalList;
                }
            } catch (WarehouseStoreItemException e) {
                // In case current warehouse store item can't meet requirement
                ProdPlanItemReqProposal prodPlanItemReqProposal = (ProdPlanItemReqProposal) this
                        .newEntityNode(productionPlanItem, ProdPlanItemReqProposal.NODENAME);
                prodPlanItemReqProposal.setParentNodeUUID(productionPlanItem.getUuid());
                prodPlanItemReqProposal.setRootNodeUUID(productionPlanItem.getRootNodeUUID());
                // update storeCoreUnit
                requestCoreUnit = materialStockKeepUnitManager.mergeStorageUnitCore(requestCoreUnit, availableStoreCoreUnit, StorageCoreUnit.OPERATOR_MINUS, warehouseStoreItem.getClient());
                // set plan proposal amount all possible maxinum amount from
                prodPlanItemReqProposal.setAmount(availableStoreCoreUnit.getAmount());
                prodPlanItemReqProposal.setRefUnitUUID(availableStoreCoreUnit.getRefUnitUUID());
                // Reduce the amount for current store item
                warehouseStoreItemManager.minusWarehouseStoreItemLoc(warehouseStoreItem, availableStoreCoreUnit);
                prodPlanItemReqProposal.setRefMaterialSKUUUID(warehouseStoreItem.getRefMaterialSKUUUID());
                prodPlanItemReqProposal.setRefWarehouseUUID(warehouseUUID);
                // using next doc pointing to store item
                prodPlanItemReqProposal.setNextDocType(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM);
                prodPlanItemReqProposal.setNextDocMatItemUUID(warehouseStoreItem.getUuid());
                prodPlanItemReqProposal.setRefStoreItemUUID(warehouseStoreItem.getUuid());
                prodPlanItemReqProposal.setStoreAmount(availableStoreCoreUnit.getAmount());
                prodPlanItemReqProposal.setStoreUnitUUID(availableStoreCoreUnit.getRefUnitUUID());
                prodPlanItemReqProposal.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY);
                prodPlanItemReqProposal.setRefBOMItemUUID(productiveBOMItem.getUuid());
                prodPlanItemReqProposal.setProductionBatchNumber(productionPlanItem.getProductionBatchNumber());
                // set the current left amount
                productiveBOMItem.setAmountWithLossRate(requestCoreUnit.getAmount());
                productiveBOMItem.setRefUnitUUID(requestCoreUnit.getRefUnitUUID());
                prodReqProposalList.add(prodPlanItemReqProposal);
            }
        }
        return prodReqProposalList;
    }

    /**
     * [Internal method] After the out-bound delivery proposal, generate the
     * production or purchase proposal for production order Item
     *
     * @param productionPlanItem
     * @param productiveBOMItem
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    protected ProdPlanItemReqProposal generateProdPurchaseProposal(ProductionPlanItem productionPlanItem,
                                                                   ProductiveBOMItem productiveBOMItem, MaterialStockKeepUnit materialStockKeepUnit)
            throws ServiceEntityConfigureException, MaterialException {
        ProdPlanItemReqProposal prodPlanItemReqProposal = (ProdPlanItemReqProposal) this
                .newEntityNode(productionPlanItem, ProdPlanItemReqProposal.NODENAME);
        prodPlanItemReqProposal.setRefBOMItemUUID(productiveBOMItem.getUuid());
        prodPlanItemReqProposal.setAmount(productiveBOMItem.getAmountWithLossRate());
        prodPlanItemReqProposal.setRefUnitUUID(productiveBOMItem.getRefUnitUUID());
        prodPlanItemReqProposal.setRefMaterialSKUUUID(productiveBOMItem.getRefMaterialSKUUUID());
        prodPlanItemReqProposal.setProductionBatchNumber(productionPlanItem.getProductionBatchNumber());
        int documentType = calculateMaterialReqDocumentType(materialStockKeepUnit);
        prodPlanItemReqProposal.setDocumentType(documentType);

        return prodPlanItemReqProposal;
    }

    /**
     * Core Logic to decide material supply type during production
     *
     * @return
     */
    public int calculateMaterialReqDocumentType(MaterialStockKeepUnit materialStockKeepUnit) {
        if (materialStockKeepUnit == null) {
            return IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
        }
        if (materialStockKeepUnit.getSupplyType() == Material.SUPPLYTYPE_SELF_PROD) {
            return IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER;
        }
        if (materialStockKeepUnit.getSupplyType() == Material.SUPPLYTYPE_SUBCONTRACT) {
            return IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
        }
        if (materialStockKeepUnit.getSupplyType() == Material.SUPPLYTYPE_MIXED) {
            return IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER;
        }
        if (materialStockKeepUnit.getSupplyType() == Material.SUPPLYTYPE_PURCHASE) {
            return IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
        }
        return IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
    }

    /**
     * [Internal method] Comparing the production order item and BOM item,
     * calculate the theorical Ratio Comparing production item amount to BOM
     * model item amount
     *
     * @param prodStoreUnit
     * @param billOfMaterialItem
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    protected double getRatioUnion(StorageCoreUnit prodStoreUnit, BillOfMaterialItem billOfMaterialItem)
            throws ServiceEntityConfigureException, MaterialException {
        StorageCoreUnit prodStorageCoreUnit = new StorageCoreUnit();
        prodStorageCoreUnit.setAmount(prodStoreUnit.getAmount());
        prodStorageCoreUnit.setRefMaterialSKUUUID(prodStoreUnit.getRefMaterialSKUUUID());
        prodStorageCoreUnit.setRefUnitUUID(prodStoreUnit.getRefUnitUUID());
        StorageCoreUnit tmpBomStorageUnit = new StorageCoreUnit();
        tmpBomStorageUnit.setRefMaterialSKUUUID(billOfMaterialItem.getRefMaterialSKUUUID());
        tmpBomStorageUnit.setRefUnitUUID(billOfMaterialItem.getRefUnitUUID());
        tmpBomStorageUnit.setAmount(billOfMaterialItem.getAmount());
        double ratio = materialStockKeepUnitManager
                .getStorageUnitRatio(prodStorageCoreUnit, tmpBomStorageUnit, billOfMaterialItem.getClient());
        return ratio;
    }

    public List<ServiceEntityNode> filterProductionItemList(List<ServiceEntityNode> rawList) {
        if (rawList == null || rawList.size() == 0) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
        for (ServiceEntityNode seNode : rawList) {
            if (ProductionPlanItem.NODENAME.equals(seNode.getNodeName())) {
                resultList.add(seNode);
            }
        }
        return resultList;
    }

    public List<ServiceEntityNode> filterSubProdItemReqList(ProductionPlanItem productionPlanItem,
                                                            List<ServiceEntityNode> rawList) {
        if (rawList == null || rawList.size() == 0) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
        for (ServiceEntityNode seNode : rawList) {
            if (ProdPlanItemReqProposal.NODENAME.equals(seNode.getNodeName()) && productionPlanItem.getUuid()
                    .equals(seNode.getParentNodeUUID())) {
                resultList.add(seNode);
            }
        }
        return resultList;
    }

    public void convProductionPlanToUI(ProductionPlan productionPlan, ProductionPlanUIModel productionPlanUIModel)
            throws ServiceEntityInstallationException {
        convProductionPlanToUI(productionPlan, productionPlanUIModel, null);
    }

    public void convProductionPlanToUI(ProductionPlan productionPlan, ProductionPlanUIModel productionPlanUIModel,
                                       LogonInfo logonInfo) throws ServiceEntityInstallationException {
        if (productionPlan != null) {
            docFlowProxy.convDocumentToUI(productionPlan, productionPlanUIModel, logonInfo);
            if (productionPlan.getPlanStartPrepareDate() != null) {
                productionPlanUIModel.setPlanStartPrepareDate(
                        DefaultDateFormatConstant.DATE_MIN_FORMAT.format(productionPlan.getPlanStartPrepareDate()));
            }
            if (productionPlan.getPlanStartTime() != null) {
                productionPlanUIModel
                        .setPlanStartTime(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(productionPlan.getPlanStartTime()));
            }
            if (productionPlan.getPlanEndTime() != null) {
                productionPlanUIModel
                        .setPlanEndTime(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(productionPlan.getPlanEndTime()));
            }
            productionPlanUIModel.setRefUnitUUID(productionPlan.getRefUnitUUID());
            productionPlanUIModel.setRefMaterialSKUUUID(productionPlan.getRefMaterialSKUUUID());
            productionPlanUIModel.setStatus(productionPlan.getStatus());
            productionPlanUIModel.setRefBillOfMaterialUUID(productionPlan.getRefBillOfMaterialUUID());
            productionPlanUIModel.setComLeadTime(productionPlan.getComLeadTime());
            productionPlanUIModel.setApproveBy(productionPlan.getApproveBy());
            productionPlanUIModel.setProductionBatchNumber(productionPlan.getProductionBatchNumber());
            if (productionPlan.getApproveTime() != null) {
                productionPlanUIModel.setApproveTime(DefaultDateFormatConstant.DATE_FORMAT.format(productionPlan.getApproveTime()));
            }
            productionPlanUIModel.setCountApproveBy(productionPlan.getCountApproveBy());
            if (productionPlan.getCountApproveTime() != null) {
                productionPlanUIModel
                        .setCountApproveTime(DefaultDateFormatConstant.DATE_FORMAT.format(productionPlan.getCountApproveTime()));
            }
            productionPlanUIModel.setRefMainProdOrderUUID(productionPlan.getRefMainProdOrderUUID());
            productionPlanUIModel.setSelfLeadTime(productionPlan.getSelfLeadTime());
            productionPlanUIModel.setNote(productionPlan.getNote());
            if (productionPlan.getActualEndTime() != null) {
                productionPlanUIModel
                        .setActualEndTime(DefaultDateFormatConstant.DATE_FORMAT.format(productionPlan.getActualEndTime()));
            }
            try {
                String amountLabel = materialStockKeepUnitManager
                        .getAmountLabel(productionPlan.getRefMaterialSKUUUID(), productionPlan.getRefUnitUUID(),
                                productionPlan.getAmount(), productionPlan.getClient());
                productionPlanUIModel.setAmountLabel(amountLabel);
            } catch (MaterialException e) {
                // log error and continue
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "amountLabel"), e);
            } catch (ServiceEntityConfigureException e) {
                // log error and continue
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "amountLabel"), e);
            }
            if (productionPlan.getActualStartTime() != null) {
                productionPlanUIModel
                        .setActualStartTime(DefaultDateFormatConstant.DATE_FORMAT.format(productionPlan.getActualStartTime()));
            }
            productionPlanUIModel.setId(productionPlan.getId());
            productionPlanUIModel.setStatus(productionPlan.getStatus());
            productionPlanUIModel.setPriorityCode(productionPlan.getPriorityCode());
            productionPlanUIModel.setCategory(productionPlan.getCategory());
            if (logonInfo != null) {
                Map<Integer, String> statusMap = this.initStatusMap(logonInfo.getLanguageCode());
                productionPlanUIModel.setStatusValue(statusMap.get(productionPlan.getStatus()));
                Map<Integer, String> priorityCodeMap = initPriorityCodeMap(logonInfo.getLanguageCode());
                productionPlanUIModel.setPriorityCodeValue(priorityCodeMap.get(productionPlan.getPriorityCode()));
                Map<Integer, String> categoryMap = initCategoryMap(logonInfo.getLanguageCode());
                productionPlanUIModel.setCategoryValue(categoryMap.get(productionPlan.getCategory()));
            }
            productionPlanUIModel.setNote(productionPlan.getNote());
            productionPlanUIModel.setSelfLeadTime(productionPlan.getSelfLeadTime());
            productionPlanUIModel.setComLeadTime(productionPlan.getComLeadTime());
            productionPlanUIModel.setRefMaterialSKUUUID(productionPlan.getRefMaterialSKUUUID());
            productionPlanUIModel.setRefBillOfMaterialUUID(productionPlan.getRefBillOfMaterialUUID());
            productionPlanUIModel.setAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(productionPlan.getAmount()));
            productionPlanUIModel.setRefUnitUUID(productionPlan.getRefUnitUUID());
        }
    }

    public void convMaterialStockKeepUnitToUI(MaterialStockKeepUnit materialStockKeepUnit,
                                              ProductionPlanUIModel productionPlanUIModel) {
        convMaterialStockKeepUnitToUI(materialStockKeepUnit, productionPlanUIModel, null);
    }

    public void convMaterialStockKeepUnitToUI(MaterialStockKeepUnit materialStockKeepUnit,
                                              ProductionPlanUIModel productionPlanUIModel, LogonInfo logonInfo) {
        if (materialStockKeepUnit != null) {
            productionPlanUIModel.setRefMaterialSKUId(materialStockKeepUnit.getId());
            productionPlanUIModel.setRefMaterialSKUName(materialStockKeepUnit.getName());
            productionPlanUIModel.setPackageStandard(materialStockKeepUnit.getPackageStandard());
            productionPlanUIModel.setSupplyType(materialStockKeepUnit.getSupplyType());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> supplyTypeMap = materialStockKeepUnitManager.initSupplyTypeMap(logonInfo.getLanguageCode());
                    productionPlanUIModel.setSupplyTypeValue(supplyTypeMap.get(materialStockKeepUnit.getSupplyType()));
                } catch (ServiceEntityInstallationException e) {
                    // log error and continue
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "supplyType"), e);
                }
            }
        }
    }

    public void convBillOfMaterialOrderToUI(BillOfMaterialOrder billOfMaterialOrder, ProductionPlanUIModel productionPlanUIModel) {
        if (billOfMaterialOrder != null) {
            productionPlanUIModel.setRefBillOfMaterialId(billOfMaterialOrder.getId());
        }
    }

    @Override
    public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(ServiceEntityNode seNode, LogonInfo logonInfo) {
        if (seNode == null) {
            return null;
        }
        if (ProductionPlanItem.NODENAME.equals(seNode.getNodeName())) {
            return productionPlanItemManager.convToDocumentExtendUIModel(seNode, logonInfo);
        }
        if (ProdPlanItemReqProposal.NODENAME.equals(seNode.getNodeName())) {
            return productionPlanItemManager.convToDocumentExtendUIModel(seNode, logonInfo);
        }
        return null;
    }

    @Override
    public String getAuthorizationResource() {
        return IServiceModelConstants.ProductionPlan;
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return productionPlanSearchProxy;
    }


    public boolean checkBlockExecutionByDocflow(int actionCode, String uuid, ServiceJSONRequest serviceJSONRequest,
                                                SerialLogonInfo serialLogonInfo){
        if(actionCode == ProductionPlanActionNode.DOC_ACTION_APPROVE){
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONPLAN, uuid, serviceJSONRequest, serialLogonInfo,
                    actionCode);
        }
        if(actionCode == ProductionPlanActionNode.DOC_ACTION_REJECT_APPROVE){
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONPLAN, uuid,serviceJSONRequest, serialLogonInfo,
                    actionCode);
        }
        if(actionCode == ProductionPlanActionNode.DOC_ACTION_REVOKE_SUBMIT){
            serviceFlowRuntimeEngine.clearInvolveProcessIns(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONPLAN,uuid);
            return true;
        }
        return true;
    }

    public void submitFlow(ProductionPlanServiceUIModel productionPlanServiceUIModel,
                           SerialLogonInfo serialLogonInfo) throws ServiceFlowRuntimeException{
        String uuid = productionPlanServiceUIModel.getProductionPlanUIModel().getUuid();
        ServiceFlowRuntimeEngine.ServiceFlowInputPara serviceFlowInputPara =
                new ServiceFlowRuntimeEngine.ServiceFlowInputPara(productionPlanServiceUIModel,
                        IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONPLAN, uuid,
                        ProductionPlanActionNode.DOC_ACTION_APPROVE, serialLogonInfo);
        serviceFlowRuntimeEngine.submitFlow(serviceFlowInputPara);
    }


    @Override
    public void exeFlowActionEnd(int documentType, String uuid, int actionCode,
                                 ServiceJSONRequest serviceJSONRequest, SerialLogonInfo serialLogonInfo){
        try {
            ProductionPlan productionPlan = (ProductionPlan) getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
                    ProductionPlan.NODENAME, serialLogonInfo.getClient(), null);
            ProductionPlanServiceModel productionPlanServiceModel = (ProductionPlanServiceModel) loadServiceModule(ProductionPlanServiceModel.class,
                    productionPlan, productionPlanServiceUIModelExtension);
            productionPlanServiceModel.setServiceJSONRequest(serviceJSONRequest);
            LogonInfo logonInfo = logonInfoManager.genLogonInfo(serialLogonInfo, false);
            if(actionCode == SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE){
                this.approvePlan(productionPlanServiceModel, logonInfo);
            }
            if(actionCode == SystemDefDocActionCodeProxy.DOC_ACTION_REJECT_APPROVE){
                this.rejectApproveService(productionPlanServiceModel, serialLogonInfo.getRefUserUUID(),
                        serialLogonInfo.getHomeOrganizationUUID());
            }
            productionPlanActionExecutionProxy.executeActionCore(productionPlanServiceModel, actionCode,
                    serialLogonInfo);
        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new ServiceFlowRuntimeException(ServiceFlowRuntimeException.PARA_SYSTEM_ERROR, e.getMessage());
        } catch (BillOfMaterialException | ServiceModuleProxyException | ProductionPlanException |
                 SearchConfigureException | MaterialException | ServiceComExecuteException | DocActionException |
                 AuthorizationException | LogonInfoException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new ServiceFlowRuntimeException(ServiceFlowRuntimeException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

}
