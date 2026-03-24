package com.company.IntelligentPlatform.production.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemManager;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemSearchModel;
import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemUIModel;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.ServiceModuleConvertPara;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;


@Scope("session")
@Controller(value = "productionOrderEditorController")
@RequestMapping(value = "/productionOrder")
public class ProductionOrderEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_PRODUCTIONORDER;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ProdPickingRefMaterialItemManager prodPickingRefMaterialItemManager;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected ProductionOrderManager productionOrderManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected WarehouseStoreItemManager warehouseStoreItemManager;

    @Autowired
    protected ProductionOrderServiceUIModelExtension productionOrderServiceUIModelExtension;

    @Autowired
    protected ProductiveBOMOrderServiceUIModelExtension productiveBOMOrderServiceUIModelExtension;

    @Autowired
    protected ProductiveBOMOrderManager productiveBOMOrderManager;

    @Autowired
    protected ProdPickingOrderManager prodPickingOrderManager;

    @Autowired
    protected ProductionOrderItemManager productionOrderItemManager;

    @Autowired
    protected ProdOrderWithPickingOrderProxy prodOrderWithPickingOrderProxy;

    @Autowired
    protected ProdPickingRefMaterialItemServiceUIModelExtension prodPickingRefMaterialItemServiceUIModelExtension;

    @Autowired
    protected ProductionOrderActionExecutionProxy productionOrderActionExecutionProxy;

    @Autowired
    protected ProductionOrderSpecifier productionOrderSpecifier;

    protected Logger logger = LoggerFactory.getLogger(ProductionOrderEditorController.class);

    public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
        return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
                ProductionOrderServiceUIModel.class,
                ProductionOrderServiceModel.class, AOID_RESOURCE,
                ProductionOrder.NODENAME, ProductionOrder.SENAME, productionOrderServiceUIModelExtension,
                productionOrderManager, ProductionOrderActionNode.NODENAME, productionOrderActionExecutionProxy
        );
    }

    protected ProductionOrderServiceUIModel parseToServiceUIModel(@RequestBody String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes") Map<String, Class> classMap = new HashMap<>();
        classMap.put("prodOrderSupplyWarehouseUIModelList", ProdOrderSupplyWarehouseUIModel.class);
        classMap.put("productionOrderItemUIModelList", ProductionOrderItemServiceUIModel.class);
        classMap.put("productionOrderAttachmentList", ProductionOrderAttachmentUIModel.class);
        classMap.put("prodOrderTargetItemAttachmentUIModelList", ProdOrderTargetItemAttachmentUIModel.class);
        classMap.put("subProductionOrderItemUIModelList", ProductionOrderItemServiceUIModel.class);
        classMap.put("prodOrderReportList", ProdOrderReportServiceUIModel.class);
        classMap.put("prodOrderItemReqProposalUIModelList", ProdOrderItemReqProposalServiceUIModel.class);
        classMap.put("prodOrderTargetMatItemList", ProdOrderTargetMatItemServiceUIModel.class);
        classMap.put("prodOrderTarSubItemUIModelList", ProdOrderTarSubItemUIModel.class);
        return (ProductionOrderServiceUIModel) JSONObject.toBean(jsonObject, ProductionOrderServiceUIModel.class,
                classMap);
    }


    private ProductionOrderServiceModel parseToServiceModel(String request)
            throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
        ProductionOrderServiceUIModel productionOrderServiceUIModel = parseToServiceUIModel(request);
        return (ProductionOrderServiceModel) productionOrderManager.genServiceModuleFromServiceUIModel(
                ProductionOrderServiceModel.class, ProductionOrderServiceUIModel.class, productionOrderServiceUIModel,
                productionOrderServiceUIModelExtension);
    }

    private ProductionOrder loadDataByCheckAccess(String uuid, boolean lockFlag, String acId)
            throws AuthorizationException, ServiceEntityConfigureException, LogonInfoException,
            ProductionOrderException {
        ProductionOrder productionOrder = (ProductionOrder) serviceBasicUtilityController.loadDataByCheckAccess(uuid,
                productionOrderManager, ProductionOrder.NODENAME, AOID_RESOURCE, acId, lockFlag,
                logonActionController.getLogonInfo().getAuthorizationACUnionList());
        if (productionOrder == null) {
            throw new ProductionOrderException(ProductionOrderException.PARA_NO_PRODORDER, uuid);
        }
        return productionOrder;
    }

    protected ProductionOrderServiceUIModel refreshLoadServiceUIModel(ProductionOrder productionOrder, String acId, LogonInfo logonInfo,
                                                                      boolean postUpdateFlag)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceUIModuleProxyException,
            SearchConfigureException, MaterialException, NodeNotFoundException, ServiceEntityInstallationException,
            LogonInfoException, AuthorizationException, ServiceComExecuteException {
        if (!ServiceEntityStringHelper.checkNullString(acId)) {
            boolean checkAuthor =
                    serviceBasicUtilityController.checkTargetDataAccess(logonInfo.getLogonUser(), productionOrder,
                            AOID_RESOURCE, acId, logonInfo.getAuthorizationACUnionList());
            if (!checkAuthor) {
                throw new AuthorizationException(AuthorizationException.TYPE_NO_AUTHORIZATION);
            }
        }
        productionOrderManager.updateBuffer(productionOrder);
        ProductionOrderServiceModel productionOrderServiceModel =
                (ProductionOrderServiceModel) productionOrderManager.loadServiceModule(
                        ProductionOrderServiceModel.class, productionOrder);
        if (postUpdateFlag) {
            // Refresh and update production order's information into DB
            try {
                productionOrderManager.postUpdateProductionOrder(productionOrderServiceModel, logonInfo.getRefUserUUID(),
                        logonInfo.getResOrgUUID());
            } catch (DocActionException e) {
                throw new RuntimeException(e);
            }
        }
        ProductionOrderServiceUIModel productionOrderServiceUIModel =
                (ProductionOrderServiceUIModel) productionOrderManager.genServiceUIModuleFromServiceModel(
                        ProductionOrderServiceUIModel.class, ProductionOrderServiceModel.class,
                        productionOrderServiceModel, productionOrderServiceUIModelExtension, logonInfo);
        // Sort target item by process index
        ProdOrderTargetMatItemManager.sortTargetMatItemListByProcessIndex(
                productionOrderServiceUIModel.getProdOrderTargetMatItemList());
        return productionOrderServiceUIModel;
    }


    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String saveModuleService(@RequestBody String request) {
        return this.executeActionCore(request,  null);
    }

    @RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getActionCodeMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> productionOrderActionExecutionProxy.getActionCodeMap(lanCode));
    }

    @RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionConfigureList() {
        return serviceBasicUtilityController.getDocActionConfigureListCore(this.productionOrderActionExecutionProxy);
    }

    @RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
    public @ResponseBody String executeDocAction(@RequestBody String request) {
        return this.executeActionCore(request,
                (productionOrderServiceModel, actionCode, logonInfo) -> {
                    try {
                        productionOrderActionExecutionProxy.executeActionCore(productionOrderServiceModel,
                                actionCode, logonActionController.getSerialLogonInfo());
                    } catch (DocActionException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                });
    }


    String executeActionCore(@RequestBody String request,
                             DocActionNodeProxy.IActionExecutor<ProductionOrderServiceModel> iActionExecutor) {
        return serviceBasicUtilityController.defaultActionServiceWrapper(request, AOID_RESOURCE,
                productionOrderActionExecutionProxy,  ProductionOrder.SENAME,
                productionOrderManager,
                new DocActionNodeProxy.IActionCodeServiceExecutor<ProductionOrderServiceModel, ProductionOrderServiceUIModel>() {
                    @Override
                    public ProductionOrderServiceModel parseToServiceModule(String request) throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
                        return parseToServiceModel(request);
                    }

                    @Override
                    public boolean preExecute(ProductionOrderServiceModel serviceModule, int actionCode) {
                        return true;
                    }

                    @Override
                    public void executeService(ProductionOrderServiceModel productionOrderServiceModel, int actionCode,
                                               LogonInfo logonInfo) throws DocActionException,
                            ServiceEntityConfigureException, ServiceModuleProxyException {
                        if(iActionExecutor != null){
                            iActionExecutor.executeService(productionOrderServiceModel, actionCode, logonInfo);
                        }
                    }

                    @Override
                    public ProductionOrderServiceUIModel refreshServiceUIModel(ProductionOrderServiceModel productionOrderServiceModel
                            , String acId, LogonInfo logonInfo) throws ServiceEntityConfigureException,
                            LogonInfoException, ServiceModuleProxyException, ServiceUIModuleProxyException, AuthorizationException {
                        ProductionOrder productionOrder = productionOrderServiceModel.getProductionOrder();
                        try {
                            return refreshLoadServiceUIModel(productionOrder, acId, logonInfo, true);
                        } catch (MaterialException | SearchConfigureException |  ServiceComExecuteException e) {
                            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                            throw new ServiceUIModuleProxyException(ServiceUIModuleProxyException.PARA_SYSTEM_WRONG,
                                    e.getErrorMessage());
                        } catch (NodeNotFoundException | ServiceEntityInstallationException e) {
                            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                            throw new ServiceUIModuleProxyException(ServiceUIModuleProxyException.PARA_SYSTEM_WRONG,
                                    e.getMessage());
                        }
                    }

                    @Override
                    public void postHandle(ProductionOrderServiceUIModel productionOrderServiceUIModel,
                                           int actionCode,
                                           SerialLogonInfo logonInfo) throws DocActionException {

                    }

                }, productionOrderServiceUIModelExtension);
    }


    @RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionNodeList(String uuid, String actionCode) {
        return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
    }


    private List<ProdPickingRefMaterialItemUIModel> getPickingMatItemListCore(List<ServiceEntityNode> rawList)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return serviceBasicUtilityController.convUIModuleList(ProdPickingRefMaterialItemUIModel.class, rawList,
                prodPickingOrderManager, prodPickingRefMaterialItemServiceUIModelExtension);
    }


    @RequestMapping(value = "/getInStockItemList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getInStockItemList(String baseUUID) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            List<ServiceEntityNode> rawList =
                    productionOrderItemManager.getInStockItemListBatch(baseUUID, logonActionController.getClient());
            rawList = serviceBasicUtilityController.sortServiceEntityList(rawList);
            List<WarehouseStoreItemUIModel> warehouseSToreItemUIModelList =
                    warehouseStoreItemManager.getStoreModuleListCore(rawList, logonActionController.getLogonInfo(),
                            WarehouseStoreItemSearchModel.BATCH_MODE_DISPLAY);
            return ServiceJSONParser.genDefOKJSONArray(warehouseSToreItemUIModelList);
        } catch (AuthorizationException | LogonInfoException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/getToPickMatItemList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getToPickMatItemList(String baseUUID) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            List<ServiceEntityNode> rawList =
                    productionOrderItemManager.getToPickListBatch(baseUUID, logonActionController.getClient());
            rawList = serviceBasicUtilityController.sortServiceEntityList(rawList);
            List<ProdPickingRefMaterialItemUIModel> prodPickingRefMaterialItemUIModelList =
                    getPickingMatItemListCore(rawList);
            return ServiceJSONParser.genDefOKJSONArray(prodPickingRefMaterialItemUIModelList);
        } catch (AuthorizationException | LogonInfoException | MaterialException | ServiceModuleProxyException |
                 DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/newModuleSerInit", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String newModuleSerInit(@RequestBody String request) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(request);
            @SuppressWarnings("rawtypes") Map<String, Class> classMap = new HashMap<>();
            ProductionOrderInitModel productionOrderInitModel =
                    (ProductionOrderInitModel) JSONObject.toBean(jsonObject, ProductionOrderInitModel.class, classMap);
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            ProductionOrderServiceModel productionOrderServiceModel =
                    productionOrderManager.newProductionOrderServiceModel(productionOrderInitModel,
                            ProductionOrder.CATEGORY_MANUAL, logonActionController.getLogonUser(),
                            logonActionController.getResOrgUUID());
            ProductionOrderServiceUIModel productionOrderServiceUIModel =
                    (ProductionOrderServiceUIModel) productionOrderManager.genServiceUIModuleFromServiceModel(
                            ProductionOrderServiceUIModel.class, ProductionOrderServiceModel.class,
                            productionOrderServiceModel, productionOrderServiceUIModelExtension,
                            logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONObject(productionOrderServiceUIModel);
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | ParseException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException | MaterialException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String deleteModule(@RequestBody String request) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(request);
            @SuppressWarnings("rawtypes") Map<String, Class> classMap = new HashMap<>();
            SimpleSEJSONRequest requestJSON =
                    (SimpleSEJSONRequest) JSONObject.toBean(jsonObject, SimpleSEJSONRequest.class, classMap);
            if (ServiceEntityStringHelper.checkNullString(requestJSON.getUuid())) {
                // UUID should not be null
                throw new ServiceModuleProxyException(ServiceModuleProxyException.TYPE_SYSTEM_WRONG);
            }
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_DELETE);

            ProductionOrder productionOrder =
                    (ProductionOrder) productionOrderManager.getEntityNodeByKey(requestJSON.getUuid(),
                            IServiceEntityNodeFieldConstant.UUID, ProductionOrder.NODENAME, logonActionController.getClient(),
                            null);
            if (productionOrder != null) {
                productionOrderManager.archiveDeleteEntityByKey(productionOrder.getUuid(),
                        IServiceEntityNodeFieldConstant.UUID, logonActionController.getClient(), ProductionOrder.NODENAME,
                        logonActionController.getResUserUUID(), logonActionController.getResOrgUUID());
            }
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/initAddProdToPickingMaterialItem", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String initAddProdToPickingMaterialItem(@RequestBody SimpleSEJSONRequest simpleRequest) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            /*
             * [Step1] Get all picking material item list from base production
             * order item Calculate in plan diff amount
             */
            List<ServiceEntityNode> prodPickingRefMaterialItemList =
                    prodPickingRefMaterialItemManager.getSubRefMaterialItemListByOrderItem(simpleRequest.getBaseUUID(),
                            logonActionController.getClient());
            ProductionOrderItem productionOrderItem =
                    (ProductionOrderItem) productionOrderManager.getEntityNodeByKey(simpleRequest.getBaseUUID(),
                            IServiceEntityNodeFieldConstant.UUID, ProductionOrderItem.NODENAME, logonActionController.getClient(),
                            null);
            StorageCoreUnit inPlanDiffCoreUnit =
                    this.getInPlanDiff(productionOrderItem, prodPickingRefMaterialItemList);
            if (inPlanDiffCoreUnit.getAmount() < 0) {
                inPlanDiffCoreUnit.setAmount(0);
            }
            ProdPickingRefMaterialInitialUIModel prodPickingRefMaterialInitialUIModel =
                    new ProdPickingRefMaterialInitialUIModel();
            prodPickingRefMaterialInitialUIModel.setAmount(inPlanDiffCoreUnit.getAmount());
            prodPickingRefMaterialInitialUIModel.setRefUnitUUID(inPlanDiffCoreUnit.getRefUnitUUID());
            prodPickingRefMaterialInitialUIModel.setInPlanAmount(inPlanDiffCoreUnit.getAmount());
            prodPickingRefMaterialInitialUIModel.setRefMaterialSKUUUID(productionOrderItem.getRefMaterialSKUUUID());
            prodPickingRefMaterialInitialUIModel.setInPlanUnitUUID(inPlanDiffCoreUnit.getRefUnitUUID());
            prodPickingRefMaterialInitialUIModel.setBaseUUID(simpleRequest.getBaseUUID());
            prodOrderWithPickingOrderProxy.convProdOrderItemToInitialUIModel(productionOrderItem,
                    prodPickingRefMaterialInitialUIModel);
            return ServiceJSONParser.genDefOKJSONObject(prodPickingRefMaterialInitialUIModel);
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (MaterialException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/addProdToPickingMaterialItem", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String addProdToPickingMaterialItem(
            @RequestBody ProdPickingRefMaterialInitialUIModel prodPickingRefMaterialInitialUIModel) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            /*
             * [Step1] Get all picking material item list from base production
             * order item Calculate in plan diff amount
             */
            List<ServiceEntityNode> prodPickingRefMaterialItemList =
                    prodPickingRefMaterialItemManager.getSubRefMaterialItemListByOrderItem(
                            prodPickingRefMaterialInitialUIModel.getBaseUUID(), logonActionController.getClient());
            ProductionOrderItem productionOrderItem = (ProductionOrderItem) productionOrderManager.getEntityNodeByKey(
                    prodPickingRefMaterialInitialUIModel.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID,
                    ProductionOrderItem.NODENAME, logonActionController.getClient(), null);
            StorageCoreUnit inPlanDiffCoreUnit =
                    this.getInPlanDiff(productionOrderItem, prodPickingRefMaterialItemList);
            StorageCoreUnit requestCoreUnit =
                    new StorageCoreUnit(prodPickingRefMaterialInitialUIModel.getRefMaterialSKUUUID(),
                            prodPickingRefMaterialInitialUIModel.getRefUnitUUID(),
                            prodPickingRefMaterialInitialUIModel.getAmount());
            if (requestCoreUnit.getAmount() == 0) {
                // If request number is 0, just return
                throw new ProductionOrderException(ProductionOrderException.PARA_ZERO_REQUEST, "");
            }
            StorageCoreUnit tmpRequestCoreUnit = (StorageCoreUnit) requestCoreUnit.clone();
            if (inPlanDiffCoreUnit.getAmount() < 0) {
                inPlanDiffCoreUnit.setAmount(0);
            } else {
                // Compare current request and in plan resources
                tmpRequestCoreUnit =
                        materialStockKeepUnitManager.mergeStorageUnitCore(tmpRequestCoreUnit, inPlanDiffCoreUnit,
                                StorageCoreUnit.OPERATOR_MINUS, productionOrderItem.getClient());
            }
            List<ServiceEntityNode> newRefMaterialItemList = new ArrayList<>();
            if (tmpRequestCoreUnit.getAmount() <= 0) {
                // in case in plan resources could meet request
                newRefMaterialItemList =
                        prodOrderWithPickingOrderProxy.updateRequestToPickingOrderWrapper(requestCoreUnit,
                                productionOrderItem, null, ProdPickingOrder.PROCESSTYPE_INPLAN, true,
                                logonActionController.getLogonInfo());
            } else {
                // in case in plan resources CAN NOT meet request
                // 1st, use all in plan resources
                List<ServiceEntityNode> inPlanRefMaterialItemList =
                        prodOrderWithPickingOrderProxy.updateRequestToPickingOrderWrapper(inPlanDiffCoreUnit,
                                productionOrderItem, null, ProdPickingOrder.PROCESSTYPE_INPLAN, true,
                                logonActionController.getLogonInfo());
                if (!ServiceCollectionsHelper.checkNullList(inPlanRefMaterialItemList)) {
                    newRefMaterialItemList.addAll(inPlanRefMaterialItemList);
                }
                // 2nd, use out of plan request
                List<ServiceEntityNode> outPlanRefMaterialItemList =
                        prodOrderWithPickingOrderProxy.updateRequestToPickingOrderWrapper(tmpRequestCoreUnit,
                                productionOrderItem, null, ProdPickingOrder.PROCESSTYPE_REPLENISH, true,
                                logonActionController.getLogonInfo());
                if (!ServiceCollectionsHelper.checkNullList(outPlanRefMaterialItemList)) {
                    newRefMaterialItemList.addAll(outPlanRefMaterialItemList);
                }
            }
            prodPickingRefMaterialInitialUIModel.setRefUnitUUID(inPlanDiffCoreUnit.getRefUnitUUID());
            prodPickingRefMaterialInitialUIModel.setInPlanAmount(inPlanDiffCoreUnit.getAmount());
            prodPickingRefMaterialInitialUIModel.setInPlanUnitUUID(inPlanDiffCoreUnit.getRefUnitUUID());
            prodPickingRefMaterialInitialUIModel.setRefMaterialSKUUUID(productionOrderItem.getRefMaterialSKUUUID());
            if (!ServiceCollectionsHelper.checkNullList(newRefMaterialItemList)) {
                ProdPickingRefMaterialItem prodPickingRefMaterialItem =
                        (ProdPickingRefMaterialItem) newRefMaterialItemList.get(0);
                prodPickingRefMaterialInitialUIModel.setRefOrderItemUUID(
                        prodPickingRefMaterialItem.getParentNodeUUID());
            }
            prodOrderWithPickingOrderProxy.convProdOrderItemToInitialUIModel(productionOrderItem,
                    prodPickingRefMaterialInitialUIModel);
            return ServiceJSONParser.genDefOKJSONObject(prodPickingRefMaterialInitialUIModel);
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException | ServiceModuleProxyException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (MaterialException | BillOfMaterialException | SearchConfigureException | ProductionOrderException |
                 ServiceComExecuteException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/initAddProdToReturnMaterialItem", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String initAddProdToReturnMaterialItem(@RequestBody SimpleSEJSONRequest simpleRequest) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            /*
             * [Step1] Get all picking material item list from base production
             * order item Calculate all the actual amount
             */
            List<ServiceEntityNode> prodPickingRefMaterialItemList =
                    prodPickingRefMaterialItemManager.getSubRefMaterialItemListByOrderItem(simpleRequest.getBaseUUID(),
                            logonActionController.getClient());
            ProductionOrderItem productionOrderItem =
                    (ProductionOrderItem) productionOrderManager.getEntityNodeByKey(simpleRequest.getBaseUUID(),
                            IServiceEntityNodeFieldConstant.UUID, ProductionOrderItem.NODENAME, logonActionController.getClient(),
                            null);
            StorageCoreUnit allExistCoreUnit =
                    prodPickingOrderManager.calculateSuppliedGrossAmount(prodPickingRefMaterialItemList,
                            productionOrderItem.getRefMaterialSKUUUID());
            ProdPickingRefMaterialInitialUIModel prodPickingRefMaterialInitialUIModel =
                    new ProdPickingRefMaterialInitialUIModel();
            prodPickingRefMaterialInitialUIModel.setAmount(allExistCoreUnit.getAmount());
            prodPickingRefMaterialInitialUIModel.setRefUnitUUID(allExistCoreUnit.getRefUnitUUID());
            prodPickingRefMaterialInitialUIModel.setInPlanAmount(allExistCoreUnit.getAmount());
            prodPickingRefMaterialInitialUIModel.setInPlanUnitUUID(allExistCoreUnit.getRefUnitUUID());
            prodPickingRefMaterialInitialUIModel.setBaseUUID(simpleRequest.getBaseUUID());
            prodPickingRefMaterialInitialUIModel.setRefMaterialSKUUUID(productionOrderItem.getRefMaterialSKUUUID());
            prodOrderWithPickingOrderProxy.convProdOrderItemToInitialUIModel(productionOrderItem,
                    prodPickingRefMaterialInitialUIModel);
            return ServiceJSONParser.genDefOKJSONObject(prodPickingRefMaterialInitialUIModel);
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (MaterialException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/addProdToReturnMaterialItem", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String addProdToReturnMaterialItem(
            @RequestBody ProdPickingRefMaterialInitialUIModel prodPickingRefMaterialInitialUIModel) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            /*
             * [Step1] Get all picking material item list from base production
             * order item Calculate in plan diff amount
             */
            List<ServiceEntityNode> prodPickingRefMaterialItemList =
                    prodPickingRefMaterialItemManager.getSubRefMaterialItemListByOrderItem(
                            prodPickingRefMaterialInitialUIModel.getBaseUUID(), logonActionController.getClient());
            ProductionOrderItem productionOrderItem = (ProductionOrderItem) productionOrderManager.getEntityNodeByKey(
                    prodPickingRefMaterialInitialUIModel.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID,
                    ProductionOrderItem.NODENAME, logonActionController.getClient(), null);
            StorageCoreUnit requestCoreUnit =
                    new StorageCoreUnit(prodPickingRefMaterialInitialUIModel.getRefMaterialSKUUUID(),
                            prodPickingRefMaterialInitialUIModel.getRefUnitUUID(),
                            prodPickingRefMaterialInitialUIModel.getAmount());
            if (requestCoreUnit.getAmount() == 0) {
                // If request number is 0, just return
                throw new ProductionOrderException(ProductionOrderException.PARA_ZERO_REQUEST, "");
            }
            StorageCoreUnit requestCoreUnitBack = (StorageCoreUnit) requestCoreUnit.clone();
            requestCoreUnitBack.setAmount(0);
            StorageCoreUnit allExistCoreUnit =
                    prodPickingOrderManager.calculateSuppliedGrossAmount(prodPickingRefMaterialItemList,
                            productionOrderItem.getRefMaterialSKUUUID());
            /*
             * Compare the request and all the actual amount
             */
            int result = materialStockKeepUnitManager.compareSKURequestsAmount(requestCoreUnit, allExistCoreUnit,
                    productionOrderItem.getClient());
            if (result > 0) {
                // In case return request is larger than all the amount, raise
                // exception
                String allAmountLabel = allExistCoreUnit.getAmount() + "";
                Map<String, String> materialUnitMap =
                        materialStockKeepUnitManager.initMaterialUnitMap(productionOrderItem.getRefMaterialSKUUUID(),
                                productionOrderItem.getClient());
                if (!ServiceEntityStringHelper.checkNullString(productionOrderItem.getRefUnitUUID())) {
                    allAmountLabel = allAmountLabel + materialUnitMap.get(productionOrderItem.getRefUnitUUID());
                }
                throw new ProductionOrderException(ProductionOrderException.PARA_RETURN_EXCEED_LIMIT, allAmountLabel);
            }
            StorageCoreUnit tmpRequestCoreUnit = (StorageCoreUnit) requestCoreUnit.clone();
            // Core Execution to generate picking material item
            ProdPickingRefMaterialItem prodPickingRefMaterialItem =
                    prodOrderWithPickingOrderProxy.updateRequestToReturnOrderWrapper(tmpRequestCoreUnit,
                            productionOrderItem, true, logonActionController.getLogonInfo());
            prodPickingRefMaterialInitialUIModel.setRefUnitUUID(requestCoreUnit.getRefUnitUUID());
            prodPickingRefMaterialInitialUIModel.setAmount(requestCoreUnit.getAmount());
            prodPickingRefMaterialInitialUIModel.setInPlanAmount(allExistCoreUnit.getAmount());
            prodPickingRefMaterialInitialUIModel.setInPlanUnitUUID(allExistCoreUnit.getRefUnitUUID());
            prodPickingRefMaterialInitialUIModel.setRefMaterialSKUUUID(productionOrderItem.getRefMaterialSKUUUID());
            prodPickingRefMaterialInitialUIModel.setRefOrderItemUUID(prodPickingRefMaterialItem.getParentNodeUUID());
            prodOrderWithPickingOrderProxy.convProdOrderItemToInitialUIModel(productionOrderItem,
                    prodPickingRefMaterialInitialUIModel);
            return ServiceJSONParser.genDefOKJSONObject(prodPickingRefMaterialInitialUIModel);
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException | ServiceModuleProxyException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (MaterialException | BillOfMaterialException | SearchConfigureException | ProductionOrderException |
                 ServiceComExecuteException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    private StorageCoreUnit getInPlanDiff(ProductionOrderItem productionOrderItem,
                                          List<ServiceEntityNode> prodPickingRefMaterialItemList)
            throws MaterialException, ServiceEntityConfigureException, DocActionException {
        StorageCoreUnit storageCoreUnit = new StorageCoreUnit();
        storageCoreUnit.setAmount(productionOrderItem.getAmountWithLossRate());
        storageCoreUnit.setRefUnitUUID(productionOrderItem.getRefUnitUUID());
        storageCoreUnit.setRefMaterialSKUUUID(productionOrderItem.getRefMaterialSKUUUID());
        if (ServiceCollectionsHelper.checkNullList(prodPickingRefMaterialItemList)) {
            return storageCoreUnit;
        }
        StorageCoreUnit allExistCoreUnit =
                prodPickingOrderManager.calculateSuppliedGrossAmount(prodPickingRefMaterialItemList,
                        productionOrderItem.getRefMaterialSKUUUID());
        if (allExistCoreUnit == null) {
            return storageCoreUnit;
        }
        storageCoreUnit = materialStockKeepUnitManager.mergeStorageUnitCore(storageCoreUnit, allExistCoreUnit,
                StorageCoreUnit.OPERATOR_MINUS, productionOrderItem.getClient());
        return storageCoreUnit;
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService() {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(ProductionOrder.SENAME, ProductionOrder.NODENAME, null,
                        null, productionOrderSpecifier, null),
                ISystemActionCode.ACID_EDIT);
    }



    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String preLockService(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.preLock(request.getUuid(), ISystemActionCode.ACID_EDIT, getDocUIModelRequest());
    }

    @RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModule(String uuid) {
        return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
                getDocUIModelRequest());
    }

    @RequestMapping(value = "/genProductionOrderProposal", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String genProductionOrderProposal(String uuid) {
        try {
            ProductionOrder productionOrder = loadDataByCheckAccess(uuid, false, ISystemActionCode.ACID_EDIT);
            List<ServiceEntityNode> rawProdOrderItemList =
                    productionOrderManager.generateProductItemListEntry(productionOrder, logonActionController.getLogonInfo(), true);
            ServiceModule productionOrderServiceModel =
                    productionOrderManager.convertToProductionOrderServiceModel(productionOrder, rawProdOrderItemList);
            ProductionOrderServiceUIModel productionrderServiceUIModel =
                    (ProductionOrderServiceUIModel) productionOrderManager.genServiceUIModuleFromServiceModel(
                            ProductionOrderServiceUIModel.class, ProductionOrderServiceModel.class,
                            productionOrderServiceModel, productionOrderServiceUIModelExtension,
                            logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONObject(productionrderServiceUIModel);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | SearchConfigureException | ProductionOrderException | MaterialException | BillOfMaterialException | ServiceUIModuleProxyException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/genProductiveBOM", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String genProductiveBOM(String uuid) {
        try {
            ProductionOrder productionOrder = loadDataByCheckAccess(uuid, false, ISystemActionCode.ACID_EDIT);
            BillOfMaterialOrder billOfMaterialOrder =
                    (BillOfMaterialOrder) billOfMaterialOrderManager.getEntityNodeByKey(
                            productionOrder.getRefBillOfMaterialUUID(), IServiceEntityNodeFieldConstant.UUID,
                            BillOfMaterialOrder.NODENAME, productionOrder.getClient(), null);
            if (billOfMaterialOrder == null) {
                throw new BillOfMaterialException(BillOfMaterialException.PARA_NO_BOMOrder,
                        productionOrder.getRefBillOfMaterialUUID());
            }
            ServiceModule productiveBOMOrderServiceModel =
                    productionOrderManager.generateProductiveBOMData(productionOrder);
            ProductiveBOMOrderServiceUIModel productiveBOMOrderServiceUIModel =
                    (ProductiveBOMOrderServiceUIModel) productiveBOMOrderManager.genServiceUIModuleFromServiceModel(
                            ProductiveBOMOrderServiceUIModel.class, ProductiveBOMOrderServiceModel.class,
                            productiveBOMOrderServiceModel, productiveBOMOrderServiceUIModelExtension,
                            logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONObject(productiveBOMOrderServiceUIModel);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException  e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | BillOfMaterialException | ServiceUIModuleProxyException | MaterialException | ProductionOrderException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/loadLeanViewService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadLeanViewService(String uuid) {
        try {
            ProductionOrder productionOrder = loadDataByCheckAccess(uuid, false, ISystemActionCode.ACID_VIEW);
            productionOrderManager.updateBuffer(productionOrder);
            List<ServiceModuleConvertPara> addtionalConvertParaList = new ArrayList<>();
            ProductionOrderUIModel productionOrderUIModel =
                    (ProductionOrderUIModel) productionOrderManager.genUIModelFromUIModelExtension(
                            ProductionOrderUIModel.class,
                            productionOrderServiceUIModelExtension.genUIModelExtensionUnion().get(0), productionOrder,
                            logonActionController.getLogonInfo(), addtionalConvertParaList);
            return ServiceJSONParser.genDefOKJSONObject(productionOrderUIModel);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | ProductionOrderException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }


    @RequestMapping(value = "/loadModuleWithPostUpdateService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleWithPostUpdateService(String uuid) {
        try {
            ProductionOrder productionOrder = loadDataByCheckAccess(uuid, false, ISystemActionCode.ACID_VIEW);
            productionOrderManager.updateBuffer(productionOrder);
            ProductionOrderServiceUIModel productionOrderServiceUIModel =
                    refreshLoadServiceUIModel(productionOrder,
                            ISystemActionCode.ACID_EDIT, logonActionController.getLogonInfo(), true);
            return ServiceJSONParser.genDefOKJSONObject(productionOrderServiceUIModel);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (LockObjectFailureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        } catch (ServiceModuleProxyException | ProductionOrderException | MaterialException | SearchConfigureException | ServiceUIModuleProxyException | ServiceComExecuteException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }


    @RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleEditService(String uuid) {
        return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
                getDocUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleViewService(String uuid) {
        return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
                getDocUIModelRequest());
    }

    @RequestMapping(value = "/getPriorityCode", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPriorityCode() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> productionOrderManager.initPriorityCodeMap(lanCode));
    }

    @RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getStatus() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> productionOrderManager.initStatusMap(lanCode));
    }

    @RequestMapping(value = "/getDoneStatus", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDoneStatus() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> productionOrderManager.initDoneStatusMap(lanCode));
    }

    @RequestMapping(value = "/getCategory", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getCategory() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> productionOrderManager.initCategoryMap(lanCode));
    }

    @RequestMapping(value = "/getOrderType", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getOrderType() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> productionOrderManager.initOrderTypeMap(lanCode));
    }

    private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
        return new DocAttachmentProxy.DocAttachmentProcessPara(productionOrderManager,
                ProductionOrderAttachment.NODENAME, ProductionOrder.NODENAME, null, null, null);
    }

    /**
     * load the attachment content to consumer.
     */
    @RequestMapping(value = "/loadAttachment")
    public ResponseEntity<byte[]> loadAttachment(String uuid) {
        return serviceBasicUtilityController.loadAttachment(uuid, AOID_RESOURCE, genDocAttachmentProcessPara());
    }

    /**
     * Delete attachment
     */
    @RequestMapping(value = "/deleteAttachment", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String deleteAttachment(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.deleteAttachment(request, AOID_RESOURCE, genDocAttachmentProcessPara());
    }


    /**
     * Upload the attachment content information.
     */
    @RequestMapping(value = "/uploadAttachment", consumes = "multipart/form-data", method = RequestMethod.POST)
    public @ResponseBody
    String uploadAttachment(HttpServletRequest request) {
        return serviceBasicUtilityController.uploadAttachment(request, AOID_RESOURCE, genDocAttachmentProcessPara());
    }

    /**
     * Upload the attachment text information.
     */
    @RequestMapping(value = "/uploadAttachmentText", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String uploadAttachmentText(@RequestBody FileAttachmentTextRequest request) {
        return serviceBasicUtilityController.uploadAttachmentText(request, AOID_RESOURCE,
                genDocAttachmentProcessPara());
    }


    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
        simpleRequest.setClient(logonActionController.getClient());
        return super.checkDuplicateIDCore(simpleRequest, productionOrderManager);
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preCreate", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String preCreate(@RequestBody SimpleSEJSONRequest request) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            MaterialStockKeepUnit materialStockKeepUnit =
                    (MaterialStockKeepUnit) materialStockKeepUnitManager.getEntityNodeByKey(request.getUuid(),
                            IServiceEntityNodeFieldConstant.UUID, MaterialStockKeepUnit.NODENAME, logonActionController.getClient(),
                            null);
            if (materialStockKeepUnit == null) {
                throw new ProductionOrderException(ProductionOrderException.PARA_NO_MATERIALSKU, request.getUuid());
            }
            BillOfMaterialOrder billOfMaterialOrder =
                    billOfMaterialOrderManager.getDefaultBOMBySKU(materialStockKeepUnit.getUuid(),
                            logonActionController.getClient());
            if (billOfMaterialOrder == null) {
                throw new ProductionOrderException(ProductionOrderException.PARA_NO_BOM, request.getUuid());
            }
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (ServiceEntityConfigureException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ProductionOrderException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getErrorMessage());
        }
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

}
