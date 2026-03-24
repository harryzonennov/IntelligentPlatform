package com.company.IntelligentPlatform.production.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.company.IntelligentPlatform.production.dto.ProdPlanItemReqProposalServiceUIModel;
import com.company.IntelligentPlatform.production.dto.ProdPlanSupplyWarehouseUIModel;
import com.company.IntelligentPlatform.production.dto.ProdPlanTarSubItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProdPlanTargetMatItemServiceUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionPlanAttachmentUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionPlanInitModel;
import com.company.IntelligentPlatform.production.dto.ProductionPlanItemServiceUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionPlanServiceUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionPlanServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProductionPlanUIModel;
import com.company.IntelligentPlatform.production.dto.ProductiveBOMOrderServiceUIModel;
import com.company.IntelligentPlatform.production.dto.ProductiveBOMOrderServiceUIModelExtension;
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

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.DocActionNodeUIModel;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;


@Scope("session")
@Controller(value = "productionPlanEditorController")
@RequestMapping(value = "/productionPlan")
public class ProductionPlanEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_PRODUCTIONPLAN;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected LockObjectManager lockObjectManager;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected ProductionPlanManager productionPlanManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected WarehouseManager warehouseManager;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected ProductionPlanServiceUIModelExtension productionPlanServiceUIModelExtension;

    @Autowired
    protected ProductiveBOMOrderServiceUIModelExtension productiveBOMOrderServiceUIModelExtension;

    @Autowired
    protected ProductiveBOMOrderManager productiveBOMOrderManager;

    @Autowired
    protected ServiceModuleProxy serviceModuleProxy;

    @Autowired
    protected DocAttachmentProxy docAttachmentProxy;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    protected Logger logger = LoggerFactory.getLogger(ProductionPlanEditorController.class);

    @Autowired
    protected ProductionPlanActionExecutionProxy productionPlanActionExecutionProxy;

    protected ProductionPlanServiceUIModel parseToServiceUIModel(@RequestBody String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes") Map<String, Class> classMap = new HashMap<>();
        classMap.put("prodPlanSupplyWarehouseUIModelList", ProdPlanSupplyWarehouseUIModel.class);
        classMap.put("productionPlanItemUIModelList", ProductionPlanItemServiceUIModel.class);
        classMap.put("productionPlanAttachmentList", ProductionPlanAttachmentUIModel.class);
        classMap.put("prodPlanItemReqProposalUIModelList", ProdPlanItemReqProposalServiceUIModel.class);
        classMap.put("prodPlanTargetMatItemList", ProdPlanTargetMatItemServiceUIModel.class);
        classMap.put("prodPlanTarSubItemUIModelList", ProdPlanTarSubItemUIModel.class);
        return (ProductionPlanServiceUIModel) JSONObject.toBean(jsonObject, ProductionPlanServiceUIModel.class,
                classMap);
    }

    private ProductionPlanServiceModel parseToServiceModel(String request)
            throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
        ProductionPlanServiceUIModel productionPlanServiceUIModel = parseToServiceUIModel(request);
        return (ProductionPlanServiceModel) productionPlanManager.genServiceModuleFromServiceUIModel(
                ProductionPlanServiceModel.class, ProductionPlanServiceUIModel.class, productionPlanServiceUIModel,
                productionPlanServiceUIModelExtension);
    }


    protected ProductionPlanServiceUIModel refreshLoadServiceUIModel(ProductionPlan productionPlan, String acId,
                                                                     LogonInfo logonInfo, boolean postUpdateFlag)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceUIModuleProxyException,
            LogonInfoException, MaterialException, AuthorizationException {
        if (!ServiceEntityStringHelper.checkNullString(acId)) {
            boolean checkAuthor =
                    serviceBasicUtilityController.checkTargetDataAccess(logonInfo.getLogonUser(), productionPlan, acId,
                            logonInfo.getAuthorizationActionCodeMap());
            if (!checkAuthor) {
                throw new AuthorizationException(AuthorizationException.TYPE_NO_AUTHORIZATION);
            }
        }
        productionPlanManager.updateBuffer(productionPlan);
        ProductionPlanServiceModel productionPlanServiceModel =
                (ProductionPlanServiceModel) productionPlanManager.loadServiceModule(ProductionPlanServiceModel.class,
                        productionPlan);
        if (postUpdateFlag) {
            try {
                productionPlanManager.postUpdateProductionPlan(productionPlanServiceModel);
            } catch (DocActionException e) {
                throw new RuntimeException(e);
            }
        }
        // Merge plan proposal item list
        mergeProposalItemList(productionPlanServiceModel);
        ProductionPlanServiceUIModel productionPlanServiceUIModel =
                (ProductionPlanServiceUIModel) productionPlanManager.genServiceUIModuleFromServiceModel(
                        ProductionPlanServiceUIModel.class, ProductionPlanServiceModel.class,
                        productionPlanServiceModel, productionPlanServiceUIModelExtension, logonInfo);
        productionPlanManager.updateBuffer(productionPlan);
        return productionPlanServiceUIModel;
    }

    protected void mergeProposalItemList(ProductionPlanServiceModel productionPlanServiceModel) {
        List<ProductionPlanItemServiceModel> productionPlanItemList =
                productionPlanServiceModel.getProductionPlanItemList();
        if (ServiceCollectionsHelper.checkNullList(productionPlanItemList)) {
            return;
        }
        for (ProductionPlanItemServiceModel productionPlanItemServiceModel : productionPlanItemList) {
            // Traverse each production plan item
            processProposalItemListCore(productionPlanItemServiceModel);
        }
    }

    /**
     * Core recursive method to process proposal item list
     *
     */
    private void processProposalItemListCore(ProductionPlanItemServiceModel productionPlanItemServiceModel) {
        List<String> removeOutboundProposalUUIDList = new ArrayList<>();
        Map<String, ProdPlanItemReqProposal> outboundProposalMap = new HashMap<>();
        if (ServiceCollectionsHelper.checkNullList(productionPlanItemServiceModel.getProdPlanItemReqProposalList())) {
            return;
        }
        ProductionPlanItem productionPlanItem = productionPlanItemServiceModel.getProductionPlanItem();
        List<ProdPlanItemReqProposalServiceModel> prodPlanItemReqProposalList =
                productionPlanItemServiceModel.getProdPlanItemReqProposalList();
        for (ProdPlanItemReqProposalServiceModel prodPlanItemReqProposalServiceModel : prodPlanItemReqProposalList) {
            ProdPlanItemReqProposal prodPlanItemReqProposal =
                    prodPlanItemReqProposalServiceModel.getProdPlanItemReqProposal();
            List<ProductionPlanItemServiceModel> subProductionPlanItemList =
                    prodPlanItemReqProposalServiceModel.getSubProductionPlanItemList();
            if (!ServiceCollectionsHelper.checkNullList(subProductionPlanItemList)) {
                for (ProductionPlanItemServiceModel subProductionPlanItemServiceModel : subProductionPlanItemList) {
                    ProductionPlanItem subProductionPlanItem =
                            subProductionPlanItemServiceModel.getProductionPlanItem();
                    if (subProductionPlanItem.getPlanStartDate() == null) {
                        subProductionPlanItem.setPlanStartDate(prodPlanItemReqProposal.getPlanStartDate());
                        subProductionPlanItem.setPlanEndDate(prodPlanItemReqProposal.getPlanEndDate());
                    }
                    processProposalItemListCore(subProductionPlanItemServiceModel);
                }
            }
            if (prodPlanItemReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
                /*
                 * [Step1] Set Plan start/end time, special handing when time is
                 * empty
                 */
                if (prodPlanItemReqProposal.getPlanStartDate() == null &&
                        prodPlanItemReqProposal.getPlanEndDate() == null) {
                    // In case both times are empty, just set as start date from
                    // parent
                    prodPlanItemReqProposal.setPlanStartDate(productionPlanItem.getPlanStartDate());
                    prodPlanItemReqProposal.setPlanEndDate(productionPlanItem.getPlanStartDate());
                }
                if (prodPlanItemReqProposal.getPlanStartDate() == null) {
                    // In case start time is empty, set as start date from
                    // parent
                    prodPlanItemReqProposal.setPlanStartDate(productionPlanItem.getPlanStartDate());
                    prodPlanItemReqProposal.setPlanEndDate(productionPlanItem.getPlanStartDate());
                }
                if (prodPlanItemReqProposal.getPlanEndDate() == null) {
                    // In case end time is empty, set as start date
                    prodPlanItemReqProposal.setPlanEndDate(prodPlanItemReqProposal.getPlanStartDate());
                }
                if (ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposal.getRefWarehouseUUID())) {
                    // In case empty warehouse
                    continue;
                }
                ProdPlanItemReqProposal existProposal =
                        outboundProposalMap.get(prodPlanItemReqProposal.getRefWarehouseUUID());
                if (existProposal == null) {
                    outboundProposalMap.put(prodPlanItemReqProposal.getRefWarehouseUUID(), prodPlanItemReqProposal);
                } else {
                    try {
                        // Merge 2 out bound proposal
                        productionPlanManager.mergeOutboundProposal(existProposal, prodPlanItemReqProposal);
                        removeOutboundProposalUUIDList.add(prodPlanItemReqProposal.getUuid());
                    } catch (MaterialException | ServiceEntityConfigureException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (ServiceCollectionsHelper.checkNullList(removeOutboundProposalUUIDList)) {
            return;
        }
        for (String proposalUUID : removeOutboundProposalUUIDList) {
            prodPlanItemReqProposalList.removeIf(prodPlanItemReqProposal -> proposalUUID.equals(
                    prodPlanItemReqProposal.getProdPlanItemReqProposal().getUuid()));
        }
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String saveModuleService(@RequestBody String request) {
        return this.executeActionCore(request,  null);
    }

    @RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDocActionNodeList(String uuid, String actionCode) {
        try {
            List<DocActionNodeUIModel> docActionNodeUIModelList =
                    docActionNodeProxy.getSubActionCodeUIModelList(uuid, ProductionPlanActionNode.SENAME,
                            ProductionPlanActionNode.NODENAME, actionCode, productionPlanManager, null,
                            logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONArray(docActionNodeUIModelList);
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getActionCodeMap() {
        try {
            Map<Integer, String> actionCodeMap =
                    productionPlanActionExecutionProxy.getActionCodeMap(logonActionController.getLanguageCode());
            return productionPlanManager.getDefaultSelectMap(actionCodeMap, false);
        } catch (ServiceEntityInstallationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDocActionConfigureList() {
        return serviceBasicUtilityController.getDocActionConfigureListCore(productionPlanActionExecutionProxy);
    }


    @RequestMapping(value = "/newModuleSerInit", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String newModuleSerInit(@RequestBody String request) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(request);
            @SuppressWarnings("rawtypes") Map<String, Class> classMap = new HashMap<>();
            ProductionPlanInitModel productionPlanInitModel =
                    (ProductionPlanInitModel) JSONObject.toBean(jsonObject, ProductionPlanInitModel.class, classMap);
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            ProductionPlanServiceModel productionPlanServiceModel =
                    productionPlanManager.newProductionPlanServiceModel(productionPlanInitModel,
                            ProductionPlan.CATEGORY_MANUAL, logonActionController.getResUserUUID(),
                            logonActionController.getResOrgUUID(), logonActionController.getClient());
            ProductionPlanServiceUIModel productionrderServiceUIModel =
                    (ProductionPlanServiceUIModel) productionPlanManager.genServiceUIModuleFromServiceModel(
                            ProductionPlanServiceUIModel.class, ProductionPlanServiceModel.class,
                            productionPlanServiceModel, productionPlanServiceUIModelExtension,
                            logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONObject(productionrderServiceUIModel);
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException | MaterialException | ProductionPlanException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String newModuleService() {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            ProductionPlan productionPlan =
                    (ProductionPlan) productionPlanManager.newRootEntityNode(logonActionController.getClient());
            ProductionPlanServiceModel productionPlanServiceModel = new ProductionPlanServiceModel();
            productionPlanServiceModel.setProductionPlan(productionPlan);
            ProductionPlanServiceUIModel productionPlanServiceUIModel =
                    (ProductionPlanServiceUIModel) productionPlanManager.genServiceUIModuleFromServiceModel(
                            ProductionPlanServiceUIModel.class, ProductionPlanServiceModel.class,
                            productionPlanServiceModel, productionPlanServiceUIModelExtension,
                            logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONObject(productionPlanServiceUIModel);
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
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
            ProductionPlan productionPlan =
                    loadDataByCheckAccess(requestJSON.getUuid(), false, ISystemActionCode.ACID_DELETE);
            productionPlanManager.deletePlan(productionPlan, logonActionController.getClient(),
                    logonActionController.getResUserUUID(), logonActionController.getResOrgUUID());
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | ProductionPlanException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String preLockService(@RequestBody SimpleSEJSONRequest request) {
        return preLock(request.getUuid());
    }


    private ProductionPlan loadDataByCheckAccess(String uuid, boolean lockFlag, String acId)
            throws AuthorizationException, ServiceEntityConfigureException, LogonInfoException,
            ProductionPlanException {
        ProductionPlan productionPlan =
                (ProductionPlan) serviceBasicUtilityController.loadDataByCheckAccess(uuid, productionPlanManager,
                        ProductionPlan.NODENAME, AOID_RESOURCE, acId, lockFlag,
                        logonActionController.getLogonInfo().getAuthorizationACUnionList());
        if (productionPlan == null) {
            throw new ProductionPlanException(ProductionPlanException.PARA_NO_PRODPLAN, uuid);
        }
        return productionPlan;
    }


    @RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModule(String uuid) {
        try {
            ProductionPlan productionPlan = loadDataByCheckAccess(uuid, false, ISystemActionCode.ACID_VIEW);
            productionPlanManager.updateBuffer(productionPlan);
            return ServiceJSONParser.genDefOKJSONObject(productionPlan);
        } catch (AuthorizationException | LogonInfoException | ProductionPlanException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }


    @RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String executeDocAction(@RequestBody String request) {
        return this.executeActionCore(request, (productionPlanServiceModel, actionCode, logonInfo) -> {
            try {
                productionPlanActionExecutionProxy.executeActionCore(productionPlanServiceModel, actionCode,
                        logonActionController.getSerialLogonInfo());
            } catch (DocActionException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        });
    }


    String executeActionCore(@RequestBody String request,
                             DocActionNodeProxy.IActionExecutor<ProductionPlanServiceModel> iActionExecutor) {
        return serviceBasicUtilityController.defaultActionServiceWrapper(request, AOID_RESOURCE,
                productionPlanActionExecutionProxy, ProductionPlan.SENAME, productionPlanManager,
                new DocActionNodeProxy.IActionCodeServiceExecutor<ProductionPlanServiceModel, ProductionPlanServiceUIModel>() {
                    @Override
                    public ProductionPlanServiceModel parseToServiceModule(String request)
                            throws ServiceModuleProxyException, ServiceEntityConfigureException,
                            ServiceUIModuleProxyException {
                        return parseToServiceModel(request);
                    }

                    @Override
                    public boolean preExecute(ProductionPlanServiceModel serviceModule, int actionCode) {
                        return true;
                    }

                    @Override
                    public void executeService(ProductionPlanServiceModel productionPlanServiceModel, int actionCode,
                                               LogonInfo logonInfo)
                            throws DocActionException, ServiceEntityConfigureException, ServiceModuleProxyException {
                        if (iActionExecutor != null) {
                            iActionExecutor.executeService(productionPlanServiceModel, actionCode, logonInfo);
                        }
                    }

                    @Override
                    public ProductionPlanServiceUIModel refreshServiceUIModel(
                            ProductionPlanServiceModel productionPlanServiceModel, String acId, LogonInfo logonInfo)
                            throws ServiceEntityConfigureException, LogonInfoException, ServiceModuleProxyException,
                            ServiceUIModuleProxyException, AuthorizationException {
                        try {
                            return refreshLoadServiceUIModel(productionPlanServiceModel.getProductionPlan(), acId, logonInfo, true);
                        } catch (MaterialException e) {
                            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                            throw new ServiceUIModuleProxyException(ServiceUIModuleProxyException.PARA_SYSTEM_WRONG,
                                    e.getErrorMessage());
                        }
                    }

                    @Override
                    public void postHandle(ProductionPlanServiceUIModel productionPlanServiceUIModel, int actionCode,
                                           SerialLogonInfo logonInfo) throws DocActionException {

                    }

                }, productionPlanServiceUIModelExtension);
    }


    @RequestMapping(value = "/genProductionPlanProposal", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String genProductionPlanProposal(@RequestBody String request) {
        ProductionPlanServiceUIModel productionPlanServiceUIModel = this.parseToServiceUIModel(request);
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            ProductionPlanServiceModel productionPlanServiceModel =
                    (ProductionPlanServiceModel) productionPlanManager.genServiceModuleFromServiceUIModel(
                            ProductionPlanServiceModel.class, ProductionPlanServiceUIModel.class,
                            productionPlanServiceUIModel, productionPlanServiceUIModelExtension);
            productionPlanManager.updateServiceModuleWithDelete(ProductionPlanServiceModel.class,
                    productionPlanServiceModel, logonActionController.getResUserUUID(),
                    logonActionController.getResOrgUUID());
            ProductionPlan productionPlan = productionPlanServiceModel.getProductionPlan();
            // Should delete the previous sub generated resources
            productionPlanManager.deletePlanSubResource(productionPlan.getUuid(),
                    logonActionController.getResUserUUID(), logonActionController.getResOrgUUID(),
                    productionPlan.getClient());
            List<ServiceEntityNode> rawProdPlanItemList =
                    productionPlanManager.generateProductItemListEntry(productionPlan, logonActionController.getLogonInfo(), true);
            productionPlanServiceModel =
                    (ProductionPlanServiceModel) productionPlanManager.convertToProductionPlanServiceModel(
                            productionPlan, rawProdPlanItemList);
            ProductionPlanServiceUIModel productionrderServiceUIModel =
                    (ProductionPlanServiceUIModel) productionPlanManager.genServiceUIModuleFromServiceModel(
                            ProductionPlanServiceUIModel.class, ProductionPlanServiceModel.class,
                            productionPlanServiceModel, productionPlanServiceUIModelExtension,
                            logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONObject(productionrderServiceUIModel);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException | BillOfMaterialException | MaterialException | ProductionPlanException | SearchConfigureException | ServiceComExecuteException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/genProductiveBOM", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String genProductiveBOM(String uuid) {
        try {
            ProductionPlan productionPlan = loadDataByCheckAccess(uuid, false, ISystemActionCode.ACID_VIEW);
            productionPlanManager.updateBuffer(productionPlan);
            BillOfMaterialOrder billOfMaterialOrder =
                    (BillOfMaterialOrder) billOfMaterialOrderManager.getEntityNodeByUUID(
                            productionPlan.getRefBillOfMaterialUUID(), BillOfMaterialOrder.NODENAME,
                            productionPlan.getClient());
            billOfMaterialOrderManager.updateBuffer(billOfMaterialOrder);
            if (billOfMaterialOrder == null) {
                throw new BillOfMaterialException(BillOfMaterialException.PARA_NO_BOMOrder,
                        productionPlan.getRefBillOfMaterialUUID());
            }
            ServiceModule productiveBOMOrderServiceModel =
                    productionPlanManager.generateProductiveBOMData(productionPlan);
            ProductiveBOMOrderServiceUIModel productiveBOMOrderServiceUIModel =
                    (ProductiveBOMOrderServiceUIModel) productiveBOMOrderManager.genServiceUIModuleFromServiceModel(
                            ProductiveBOMOrderServiceUIModel.class, ProductiveBOMOrderServiceModel.class,
                            productiveBOMOrderServiceModel, productiveBOMOrderServiceUIModelExtension,
                            logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONObject(productiveBOMOrderServiceUIModel);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException | BillOfMaterialException | MaterialException | ProductionPlanException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleViewService(String uuid) {
        try {
            ProductionPlan productionPlan = loadDataByCheckAccess(uuid, false, ISystemActionCode.ACID_VIEW);
            productionPlanManager.updateBuffer(productionPlan);
            ProductionPlanServiceUIModel productionPlanServiceUIModel =
                    refreshLoadServiceUIModel(productionPlan, null, logonActionController.getLogonInfo(),
                            false);
            return ServiceJSONParser.genDefOKJSONObject(productionPlanServiceUIModel);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException | ProductionPlanException | MaterialException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleEditService(String uuid) {
        try {
            ProductionPlan productionPlan = loadDataByCheckAccess(uuid, true, ISystemActionCode.ACID_EDIT);
            productionPlanManager.updateBuffer(productionPlan);
            ProductionPlanServiceUIModel productionPlanServiceUIModel =
                    refreshLoadServiceUIModel(productionPlan, ISystemActionCode.ACID_EDIT,
                            logonActionController.getLogonInfo(), false);
            return ServiceJSONParser.genDefOKJSONObject(productionPlanServiceUIModel);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (LockObjectFailureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException | ProductionPlanException | MaterialException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/getPriorityCode", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPriorityCode() {
        try {
            Map<Integer, String> priorityCodeMap =
                    productionPlanManager.initPriorityCodeMap(logonActionController.getLanguageCode());
            return productionPlanManager.getDefaultSelectMap(priorityCodeMap);
        } catch (ServiceEntityInstallationException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        }

    }

    @RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getStatus() {
        try {
            Map<Integer, String> statusMap =
                    productionPlanManager.initStatusMap(logonActionController.getLanguageCode());
            return productionPlanManager.getDefaultSelectMap(statusMap);
        } catch (ServiceEntityInstallationException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        }
    }

    @RequestMapping(value = "/getCategory", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getCategory() {
        try {
            Map<Integer, String> categoryMap =
                    productionPlanManager.initCategoryMap(logonActionController.getLanguageCode());
            return productionPlanManager.getDefaultSelectMap(categoryMap);
        } catch (ServiceEntityInstallationException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        }
    }

    private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
        return new DocAttachmentProxy.DocAttachmentProcessPara(productionPlanManager, ProductionPlanAttachment.NODENAME,
                ProductionPlan.NODENAME, null, null, null);
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
        return super.checkDuplicateIDCore(simpleRequest, productionPlanManager);
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
                            IServiceEntityNodeFieldConstant.UUID, MaterialStockKeepUnit.NODENAME,
                            logonActionController.getClient(), null);
            if (materialStockKeepUnit == null) {
                throw new ProductionPlanException(ProductionPlanException.PARA_NO_MATERIALSKU, request.getUuid());
            }
            BillOfMaterialOrder billOfMaterialOrder =
                    billOfMaterialOrderManager.getDefaultBOMBySKU(materialStockKeepUnit.getUuid(),
                            logonActionController.getClient());
            if (billOfMaterialOrder == null) {
                throw new ProductionPlanException(ProductionPlanException.PARA_NO_BOM, request.getUuid());
            }
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (ServiceEntityConfigureException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        } catch (AuthorizationException | LogonInfoException | ProductionPlanException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getErrorMessage());
        }
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String preLock(String uuid) {
        try {
            ProductionPlan productionPlan = (ProductionPlan) productionPlanManager.getEntityNodeByKey(uuid,
                    IServiceEntityNodeFieldConstant.UUID, ProductionPlan.NODENAME, logonActionController.getClient(),
                    null);
            String baseUUID = productionPlan.getUuid();
            List<ServiceEntityNode> lockSEList = new ArrayList<>();
            lockSEList.add(productionPlan);
            List<ServiceEntityNode> lockResult =
                    lockObjectManager.preLockServiceEntityList(lockSEList, logonActionController.getResUserUUID());
            return lockObjectManager.genJSONLockCheckResult(lockResult, productionPlan.getName(),
                    productionPlan.getId(), baseUUID);
        } catch (ServiceEntityConfigureException e) {
            return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
        }
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

}
