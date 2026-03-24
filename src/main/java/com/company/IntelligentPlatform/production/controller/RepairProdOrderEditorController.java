package com.company.IntelligentPlatform.production.controller;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
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
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.DocActionNodeUIModel;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ISystemAuthorizationObject;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEMessageResponse;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.ServiceModuleConvertPara;

import jakarta.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Scope("session")
@Controller(value = "repairProdOrderEditorController")
@RequestMapping(value = "/repairProdOrder")
public class RepairProdOrderEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = RepairProdOrder.SENAME;

    public static final String ID_RESOURCE = RepairProdOrder.SENAME;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected LockObjectManager lockObjectManager;

    @Autowired
    protected ProdPickingRefMaterialItemManager prodPickingRefMaterialItemManager;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected RepairProdOrderManager repairProdOrderManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected ProdOrderSupplyWarehouseListController repairProdSupplyWarehouseListController;

    @Autowired
    protected WarehouseManager warehouseManager;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected WarehouseStoreItemManager warehouseStoreItemManager;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected RepairProdOrderServiceUIModelExtension repairProdOrderServiceUIModelExtension;

    @Autowired
    protected ProductiveBOMOrderServiceUIModelExtension productiveBOMOrderServiceUIModelExtension;

    @Autowired
    protected ProductiveBOMOrderManager productiveBOMOrderManager;

    @Autowired
    protected ProdJobOrderManager prodJobOrderManager;

    @Autowired
    protected ProdPickingOrderManager prodPickingOrderManager;

    @Autowired
    protected RepairProdOrderItemManager repairProdOrderItemManager;

    @Autowired
    protected ProdOrderWithPickingOrderProxy prodOrderWithPickingOrderProxy;

    @Autowired
    protected RepairProdTargetMatItemManager repairProdTargetMatItemManager;

    @Autowired
    protected RepairProdTargetItemToCrossInboundProxy repairProdTargetItemToCrossInboundProxy;

    @Autowired
    protected RepairProdOrderActionExecutionProxy repairProdOrderActionExecutionProxy;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    @Autowired
    protected DocAttachmentProxy docAttachmentProxy;

    protected Logger logger = LoggerFactory.getLogger(RepairProdOrderEditorController.class);

    protected RepairProdOrderServiceUIModel parseToServiceUIModel(@RequestBody String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("repairProdSupplyWarehouseUIModelList", RepairProdSupplyWarehouseUIModel.class);
        classMap.put("repairProdOrderItemUIModelList", RepairProdOrderItemServiceUIModel.class);
        classMap.put("repairProdOrderAttachmentList", RepairProdOrderAttachmentUIModel.class);
        classMap.put("repairProdTargetItemAttachmentUIModelList", RepairProdTargetItemAttachmentUIModel.class);
        classMap.put("subRepairProdOrderItemUIModelList", RepairProdOrderItemServiceUIModel.class);
        classMap.put("repairProdItemReqProposalUIModelList", RepairProdItemReqProposalServiceUIModel.class);
        classMap.put("repairProdTargetMatItemList", RepairProdTargetMatItemServiceUIModel.class);
        classMap.put("repairProdTarSubItemUIModelList", RepairProdTarSubItemUIModel.class);
        return (RepairProdOrderServiceUIModel) JSONObject
                .toBean(jsonObject, RepairProdOrderServiceUIModel.class, classMap);
    }


    private RepairProdOrderServiceModel parseToServiceModel(String request)
            throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
        RepairProdOrderServiceUIModel repairProdOrderServiceUIModel = parseToServiceUIModel(request);
        return (RepairProdOrderServiceModel) repairProdOrderManager
                .genServiceModuleFromServiceUIModel(
                        RepairProdOrderServiceModel.class,
                        RepairProdOrderServiceUIModel.class,
                        repairProdOrderServiceUIModel,
                        repairProdOrderServiceUIModelExtension);
    }

    protected RepairProdOrderServiceUIModel saveModuleCore(RepairProdOrderServiceUIModel repairProdOrderServiceUIModel) throws LogonInfoException, AuthorizationException, ServiceEntityConfigureException, ServiceModuleProxyException, ServiceUIModuleProxyException, SearchConfigureException, MaterialException, NodeNotFoundException, ServiceEntityInstallationException, ServiceComExecuteException {
        serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
        LogonUser logonUser = logonActionController.getLogonUser();
        if (logonUser == null) {
            throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
        }
        String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
        Organization organization = logonActionController.getOrganizationByUser(logonUser.getUuid());
        if (organization != null) {
            organizationUUID = organization.getUuid();
        }
        RepairProdOrderServiceModel repairProdOrderServiceModel = (RepairProdOrderServiceModel) repairProdOrderManager
                .genServiceModuleFromServiceUIModel(RepairProdOrderServiceModel.class,
                        RepairProdOrderServiceUIModel.class,
                        repairProdOrderServiceUIModel, repairProdOrderServiceUIModelExtension);
        repairProdOrderManager
                .updateServiceModule(RepairProdOrderServiceModel.class, repairProdOrderServiceModel,
                        logonUser.getUuid(),
                        organizationUUID);
        // Async post update tasks
        repairProdOrderManager.postUpdateRepairProdOrderAsyncWrapper(repairProdOrderServiceModel, logonUser.getUuid(),
                organizationUUID);
        return refreshLoadServiceUIModel(repairProdOrderServiceModel.getRepairProdOrder().getUuid(),
                ISystemActionCode.ACID_EDIT,
                logonActionController.getLogonInfo(), true);
    }

    protected RepairProdOrderServiceUIModel refreshLoadServiceUIModel(String uuid, String acId, LogonInfo logonInfo,
                                                                      boolean postUpdateFlag) throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceUIModuleProxyException, SearchConfigureException, MaterialException, NodeNotFoundException, ServiceEntityInstallationException, LogonInfoException, AuthorizationException, ServiceComExecuteException {
        // Refresh service model
        RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
                .getEntityNodeByUUID(uuid, RepairProdOrder.NODENAME, logonInfo.getClient());
        if (!ServiceEntityStringHelper.checkNullString(acId)) {
            boolean checkAuthor = serviceBasicUtilityController
                    .checkTargetDataAccess(logonInfo.getLogonUser(), repairProdOrder, acId,
                            logonInfo.getAuthorizationActionCodeMap());
            if (!checkAuthor) {
                throw new AuthorizationException(AuthorizationException.TYPE_NO_AUTHORIZATION);
            }
        }
        repairProdOrderManager.updateBuffer(repairProdOrder);
        RepairProdOrderServiceModel repairProdOrderServiceModel = (RepairProdOrderServiceModel) repairProdOrderManager
                .loadServiceModule(RepairProdOrderServiceModel.class, repairProdOrder, repairProdOrderServiceUIModelExtension);
        if (postUpdateFlag) {
            // Refresh and update production order's information into DB
            try {
                repairProdOrderManager.postUpdateRepairProdOrder(repairProdOrderServiceModel, logonInfo.getRefUserUUID(),
                        logonInfo.getResOrgUUID());
            } catch (DocActionException e) {
                throw new RuntimeException(e);
            }
        }
        RepairProdOrderServiceUIModel repairProdOrderServiceUIModel =
                (RepairProdOrderServiceUIModel) repairProdOrderManager
                .genServiceUIModuleFromServiceModel(RepairProdOrderServiceUIModel.class,
                        RepairProdOrderServiceModel.class,
                        repairProdOrderServiceModel, repairProdOrderServiceUIModelExtension, logonInfo);
        // Sort target item by process index
        RepairProdTargetMatItemManager.sortTargetMatItemListByProcessIndex(repairProdOrderServiceUIModel.getRepairProdTargetMatItemList());
        return repairProdOrderServiceUIModel;
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String saveModuleService(@RequestBody String request) {
        return this.executeActionCore(request, ISystemActionCode.ACID_EDIT,0,
                null);
    }

    @RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionNodeList(String uuid, String actionCode) {
        try {
            List<DocActionNodeUIModel> docActionNodeUIModelList = docActionNodeProxy.getSubActionCodeUIModelList(uuid
                    , RepairProdOrderActionNode.SENAME, RepairProdOrderActionNode.NODENAME, actionCode,
                    repairProdOrderManager,null,
                    logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONArray(docActionNodeUIModelList);
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }


    @RequestMapping(value = "/approveOrderService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String approveOrderService(@RequestBody String request) {
        return this.executeActionCore(request, ISystemActionCode.ACID_AUDITDOC,
                RepairProdOrderActionNode.DOC_ACTION_APPROVE,
                (repairProdOrderServiceModel, logonInfo) -> {
                    try {
                        repairProdOrderManager.approveOrder(repairProdOrderServiceModel, false,
                                logonActionController.getLogonInfo());
                    } catch (BillOfMaterialException | SearchConfigureException | ProductionOrderException |
                             MaterialException | LogonInfoException | AuthorizationException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                        throw new ServiceModuleProxyException(ServiceModuleProxyException.PARA_SYSTEM_WRONG,
                                e.getErrorMessage());
                    } catch (NodeNotFoundException | ServiceEntityInstallationException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                        throw new ServiceModuleProxyException(ServiceModuleProxyException.PARA_SYSTEM_WRONG,
                                e.getMessage());
                    }
                });
    }

	
    @RequestMapping(value = "/countApproveOrderService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String countApproveOrderService(@RequestBody String request) {
        RepairProdOrderServiceUIModel repairProdOrderServiceUIModel = parseToServiceUIModel(request);
        try {
            repairProdOrderServiceUIModel = saveModuleCore(repairProdOrderServiceUIModel);
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_AUDITDOC);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
            Organization organization = logonActionController.getOrganizationByUser(logonUser.getUuid());
            if (organization != null) {
                organizationUUID = organization.getUuid();
            }
            RepairProdOrderServiceModel repairProdOrderServiceModel =
                    (RepairProdOrderServiceModel) repairProdOrderManager
                    .genServiceModuleFromServiceUIModel(RepairProdOrderServiceModel.class,
                            RepairProdOrderServiceUIModel.class,
                            repairProdOrderServiceUIModel, repairProdOrderServiceUIModelExtension);
            repairProdOrderManager
                    .countApproveOrder(repairProdOrderServiceModel, logonUser.getUuid(), organizationUUID);
            repairProdOrderServiceUIModel =
                    refreshLoadServiceUIModel(repairProdOrderServiceModel.getRepairProdOrder().getUuid(),
                    ISystemActionCode.ACID_EDIT, logonActionController.getLogonInfo(), true);
            return ServiceJSONParser.genDefOKJSONObject(repairProdOrderServiceUIModel);
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException | SearchConfigureException | MaterialException | ServiceComExecuteException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }


    @RequestMapping(value = "/rejectApproveService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String rejectApproveService(@RequestBody String request) {
        return this.executeActionCore(request, ISystemActionCode.ACID_AUDITDOC,
                RepairProdOrderActionNode.DOC_ACTION_REJECT_APPROVE,
                (repairProdOrderServiceModel, logonInfo) -> repairProdOrderManager.rejectApproveService(repairProdOrderServiceModel,
                        logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID()));
    }

    @RequestMapping(value = "/submitService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String submitService(@RequestBody String request) {
        return this.executeActionCore(request, ISystemActionCode.ACID_EDIT, RepairProdOrderActionNode.DOC_ACTION_SUBMIT,
                (repairProdOrderServiceModel, logonInfo) -> repairProdOrderManager.submitService(repairProdOrderServiceModel, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID()));
    }


    @RequestMapping(value = "/revokeSubmitService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String revokeSubmitService(@RequestBody String request) {
        return this.executeActionCore(request, ISystemActionCode.ACID_EDIT,
                RepairProdOrderActionNode.DOC_ACTION_REVOKE_SUBMIT,
                (repairProdOrderServiceModel, logonInfo) -> repairProdOrderManager.revokeSubmitService(repairProdOrderServiceModel, logonInfo.getRefUserUUID(),
                        logonInfo.getResOrgUUID()));
    }

    @RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionConfigureList() {
        return serviceBasicUtilityController.getDocActionConfigureListCore(this.repairProdOrderActionExecutionProxy);
    }

    @RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
    public @ResponseBody String executeDocAction(@RequestBody String request) {
        return this.executeActionCore(request,
                (repairProdOrderServiceModel, actionCode, logonInfo) -> {
                    try {
                        repairProdOrderActionExecutionProxy.executeActionCore(repairProdOrderServiceModel,
                                actionCode, logonActionController.getSerialLogonInfo());
                    } catch (DocActionException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                });
    }

    @RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getActionCodeMap() {
        try {
            Map<Integer, String> actionCodeMap =
                    repairProdOrderActionExecutionProxy.getActionCodeMap(logonActionController.getLanguageCode());
            return repairProdOrderManager.getDefaultSelectMap(actionCodeMap, false);
        } catch (ServiceEntityInstallationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    String executeActionCore(@RequestBody String request,
                             DocActionNodeProxy.IActionExecutor<RepairProdOrderServiceModel> iActionExecutor) {
        return serviceBasicUtilityController.defaultActionServiceWrapper(request, AOID_RESOURCE,
                repairProdOrderActionExecutionProxy,  RepairProdOrder.SENAME,
                repairProdOrderManager,
                new DocActionNodeProxy.IActionCodeServiceExecutor<RepairProdOrderServiceModel, RepairProdOrderServiceUIModel>() {
                    @Override
                    public RepairProdOrderServiceModel parseToServiceModule(String request) throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
                        return parseToServiceModel(request);
                    }

                    @Override
                    public boolean preExecute(RepairProdOrderServiceModel serviceModule, int actionCode) {
                        return true;
                    }

                    @Override
                    public void executeService(RepairProdOrderServiceModel repairProdOrderServiceModel, int actionCode,
                                               LogonInfo logonInfo) throws DocActionException,
                            ServiceEntityConfigureException, ServiceModuleProxyException {
                        if(iActionExecutor != null){
                            iActionExecutor.executeService(repairProdOrderServiceModel, actionCode, logonInfo);
                        }
                    }

                    @Override
                    public RepairProdOrderServiceUIModel refreshServiceUIModel(RepairProdOrderServiceModel repairProdOrderServiceModel
                            , String acId, LogonInfo logonInfo) throws ServiceEntityConfigureException,
                            LogonInfoException, ServiceModuleProxyException, ServiceUIModuleProxyException, AuthorizationException {
                        RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
                        try {
                            return refreshLoadServiceUIModel(repairProdOrder.getUuid(), acId, logonInfo, true);
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
                    public void postHandle(RepairProdOrderServiceUIModel repairProdOrderServiceUIModel,
                                           int actionCode,
                                           SerialLogonInfo logonInfo) throws DocActionException {

                    }

                }, repairProdOrderServiceUIModelExtension);
    }


    String executeActionCore(@RequestBody String request, String acId, int actionCode,
                             DocActionNodeProxy.IActionExecute<RepairProdOrderServiceModel> iActionExecute) {
        return serviceBasicUtilityController.defaultActionServiceWrapper(request, AOID_RESOURCE,
                acId, IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST, actionCode, RepairProdOrder.SENAME,
                repairProdOrderManager,
                new DocActionNodeProxy.IActionCodeServiceWrapper<RepairProdOrderServiceModel,
                        RepairProdOrderServiceUIModel>() {
                    @Override
                    public RepairProdOrderServiceModel parseToServiceModule(String request) throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
                        return parseToServiceModel(request);
                    }

                    @Override
                    public boolean preExecute(RepairProdOrderServiceModel serviceModule, int documentType) {
                        return true;
                    }

                    @Override
                    public void executeService(RepairProdOrderServiceModel repairProdOrderServiceModel,
                                               LogonInfo logonInfo) throws DocActionException,
                            ServiceEntityConfigureException, ServiceModuleProxyException {
                        if (iActionExecute != null) {
                            iActionExecute.executeService(repairProdOrderServiceModel, logonInfo);
                        }
                    }

                    @Override
                    public RepairProdOrderServiceUIModel refreshServiceUIModel(RepairProdOrderServiceModel repairProdOrderServiceModel
                            , String acId, LogonInfo logonInfo) throws ServiceEntityConfigureException,
                            LogonInfoException, ServiceModuleProxyException, ServiceUIModuleProxyException,
                            AuthorizationException {
                        RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
                        try {
                            return refreshLoadServiceUIModel(repairProdOrder.getUuid(), acId, logonInfo, false);
                        } catch (SearchConfigureException | MaterialException | ServiceComExecuteException e) {
                            throw new ServiceUIModuleProxyException(ServiceUIModuleProxyException.PARA_SYSTEM_WRONG,
                                    e.getErrorMessage());
                        } catch (NodeNotFoundException | ServiceEntityInstallationException e) {
                            throw new ServiceUIModuleProxyException(ServiceUIModuleProxyException.PARA_SYSTEM_WRONG,
                                    e.getMessage());
                        }
                    }

                    @Override
                    public void postHandle(RepairProdOrderServiceUIModel serviceUIModule, int actionCode,
                                           SerialLogonInfo logonInfo) {

                    }

                }, repairProdOrderServiceUIModelExtension);
    }

    @RequestMapping(value = "/inProductionService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String inProductionService(@RequestBody String request) {
        RepairProdOrderServiceUIModel repairProdOrderServiceUIModel = parseToServiceUIModel(request);
        try {
            repairProdOrderServiceUIModel = saveModuleCore(repairProdOrderServiceUIModel);
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
            Organization organization = logonActionController.getOrganizationByUser(logonUser.getUuid());
            if (organization != null) {
                organizationUUID = organization.getUuid();
            }
            RepairProdOrderServiceModel repairProdOrderServiceModel =
                    (RepairProdOrderServiceModel) repairProdOrderManager
                    .genServiceModuleFromServiceUIModel(RepairProdOrderServiceModel.class,
                            RepairProdOrderServiceUIModel.class,
                            repairProdOrderServiceUIModel, repairProdOrderServiceUIModelExtension);
            repairProdOrderManager
                    .inProduction(repairProdOrderServiceModel, logonUser.getUuid(),
                            organizationUUID);
            repairProdOrderServiceUIModel =
                    refreshLoadServiceUIModel(repairProdOrderServiceModel.getRepairProdOrder().getUuid(),
                    ISystemActionCode.ACID_EDIT, logonActionController.getLogonInfo(), false);
            return ServiceJSONParser.genDefOKJSONObject(repairProdOrderServiceUIModel);
        } catch (LogonInfoException | AuthorizationException | ServiceComExecuteException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException | SearchConfigureException | MaterialException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/getActionCode", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getActionCode() {
        try {
            Map<Integer, String> actionCodeMap =
                    repairProdOrderManager.initActionCodeMap(logonActionController.getLanguageCode());
            return repairProdOrderManager.getDefaultSelectMap(actionCodeMap);
        } catch (ServiceEntityInstallationException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        }
    }



    @RequestMapping(value = "/getInStockItemList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getInStockItemList(String baseUUID) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            List<ServiceEntityNode> rawList = repairProdOrderItemManager.getInStockItemListBatch(baseUUID,
                    logonUser.getClient());
            rawList.sort(new ServiceEntityNodeLastUpdateTimeCompare());
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

    @RequestMapping(value = "/preCheckOrderCompleteService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String preCheckOrderCompleteService(@RequestBody String request) {
        RepairProdOrderServiceUIModel repairProdOrderServiceUIModel = parseToServiceUIModel(request);
        try {
            repairProdOrderServiceUIModel = saveModuleCore(repairProdOrderServiceUIModel);
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
            Organization organization = logonActionController.getOrganizationByUser(logonUser.getUuid());
            if (organization != null) {
                organizationUUID = organization.getUuid();
            }
            RepairProdOrderServiceModel repairProdOrderServiceModel =
                    (RepairProdOrderServiceModel) repairProdOrderManager
                    .genServiceModuleFromServiceUIModel(RepairProdOrderServiceModel.class,
                            RepairProdOrderServiceUIModel.class,
                            repairProdOrderServiceUIModel, repairProdOrderServiceUIModelExtension);
            List<SimpleSEMessageResponse> rawMessageList =
                    repairProdOrderManager.preCheckSetComplete(repairProdOrderServiceModel,
                            logonActionController.getLogonInfo());
            List<SimpleSEMessageResponse> errorMessageList = ServiceMessageResponseHelper
                    .filerSEMessageResponseByLevel(
                            SimpleSEMessageResponse.MESSAGELEVEL_ERROR,
                            rawMessageList);
            if (!ServiceCollectionsHelper.checkNullList(errorMessageList)) {
                throw new ProductionOrderException(ProductionOrderException.PARA_NODONE_TAEGET, errorMessageList);
            }
            repairProdOrderServiceUIModel =
                    refreshLoadServiceUIModel(repairProdOrderServiceModel.getRepairProdOrder().getUuid(),
                    ISystemActionCode.ACID_EDIT, logonActionController.getLogonInfo(), false);
            return ServiceJSONParser.genDefOKJSONObject(repairProdOrderServiceUIModel);
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException | SearchConfigureException | MaterialException | ProductionOrderException | ServiceComExecuteException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/setOrderCompleteService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String setOrderCompleteService(@RequestBody String request) {
        RepairProdOrderServiceUIModel repairProdOrderServiceUIModel = parseToServiceUIModel(request);
        try {
            repairProdOrderServiceUIModel = saveModuleCore(repairProdOrderServiceUIModel);
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
            Organization organization = logonActionController.getOrganizationByUser(logonUser.getUuid());
            if (organization != null) {
                organizationUUID = organization.getUuid();
            }
            RepairProdOrderServiceModel repairProdOrderServiceModel =
                    (RepairProdOrderServiceModel) repairProdOrderManager
                    .genServiceModuleFromServiceUIModel(RepairProdOrderServiceModel.class,
                            RepairProdOrderServiceUIModel.class,
                            repairProdOrderServiceUIModel, repairProdOrderServiceUIModelExtension);
            repairProdOrderManager
                    .setOrderComplete(repairProdOrderServiceModel, logonActionController.getLogonInfo());
            repairProdOrderServiceUIModel =
                    refreshLoadServiceUIModel(repairProdOrderServiceModel.getRepairProdOrder().getUuid(),
                    ISystemActionCode.ACID_EDIT, logonActionController.getLogonInfo(), false);
            return ServiceJSONParser.genDefOKJSONObject(repairProdOrderServiceUIModel);
        } catch (LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException | SearchConfigureException | MaterialException | ProductionOrderException | ServiceComExecuteException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/newModuleSerInit", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String newModuleSerInit(@RequestBody String request) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(request);
            @SuppressWarnings("rawtypes")
            Map<String, Class> classMap = new HashMap<>();
            RepairProdOrderInitModel repairProdOrderInitModel = (RepairProdOrderInitModel) JSONObject
                    .toBean(jsonObject, RepairProdOrderInitModel.class, classMap);
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
            Organization organization = logonActionController.getOrganizationByUser(logonUser.getUuid());
            if (organization != null) {
                organizationUUID = organization.getUuid();
            }
            RepairProdOrderServiceModel repairProdOrderServiceModel = repairProdOrderManager
                    .newRepairProdOrderServiceModel(repairProdOrderInitModel, RepairProdOrder.CATEGORY_MANUAL,
                            logonActionController.getLogonInfo());
            RepairProdOrderServiceUIModel repairProdOrderServiceUIModel =
                    (RepairProdOrderServiceUIModel) repairProdOrderManager
                    .genServiceUIModuleFromServiceModel(RepairProdOrderServiceUIModel.class,
                            RepairProdOrderServiceModel.class,
                            repairProdOrderServiceModel, repairProdOrderServiceUIModelExtension,
                            logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONObject(repairProdOrderServiceUIModel);
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | ParseException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException | MaterialException | ProductionOrderException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String deleteModule(@RequestBody String request) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(request);
            @SuppressWarnings("rawtypes")
            Map<String, Class> classMap = new HashMap<String, Class>();
            SimpleSEJSONRequest requestJSON = (SimpleSEJSONRequest) JSONObject
                    .toBean(jsonObject, SimpleSEJSONRequest.class, classMap);
            if (ServiceEntityStringHelper.checkNullString(requestJSON.getUuid())) {
                // UUID should not be null
                throw new ServiceModuleProxyException(ServiceModuleProxyException.TYPE_SYSTEM_WRONG);
            }
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_DELETE);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
            Organization organization = logonActionController.getOrganizationByUser(logonUser.getUuid());
            if (organization != null) {
                organizationUUID = organization.getUuid();
            }
            RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
                    .getEntityNodeByKey(requestJSON.getUuid(), IServiceEntityNodeFieldConstant.UUID,
                            RepairProdOrder.NODENAME,
                            logonUser.getClient(), null);
            if (repairProdOrder != null) {
                repairProdOrderManager.archiveDeleteEntityByKey(repairProdOrder.getUuid(),
                        IServiceEntityNodeFieldConstant.UUID,
                        logonUser.getClient(), RepairProdOrder.NODENAME, logonUser.getUuid(), organizationUUID);
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
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            /*
             * [Step1] Get all picking material item list from base production
             * order item Calculate in plan diff amount
             */
            List<ServiceEntityNode> prodPickingRefMaterialItemList = prodPickingRefMaterialItemManager
                    .getSubRefMaterialItemListByOrderItem(simpleRequest.getBaseUUID(), logonUser.getClient());
            RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) repairProdOrderManager
                    .getEntityNodeByKey(simpleRequest.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID,
                            RepairProdOrderItem.NODENAME, logonUser.getClient(), null);
            StorageCoreUnit inPlanDiffCoreUnit = this.getInPlanDiff(repairProdOrderItem,
                    prodPickingRefMaterialItemList);
            if (inPlanDiffCoreUnit.getAmount() < 0) {
                inPlanDiffCoreUnit.setAmount(0);
            }
            ProdPickingRefMaterialInitialUIModel prodPickingRefMaterialInitialUIModel =
                    new ProdPickingRefMaterialInitialUIModel();
            prodPickingRefMaterialInitialUIModel.setAmount(inPlanDiffCoreUnit.getAmount());
            prodPickingRefMaterialInitialUIModel.setRefUnitUUID(inPlanDiffCoreUnit.getRefUnitUUID());
            prodPickingRefMaterialInitialUIModel.setInPlanAmount(inPlanDiffCoreUnit.getAmount());
            prodPickingRefMaterialInitialUIModel.setRefMaterialSKUUUID(repairProdOrderItem.getRefMaterialSKUUUID());
            prodPickingRefMaterialInitialUIModel.setInPlanUnitUUID(inPlanDiffCoreUnit.getRefUnitUUID());
            prodPickingRefMaterialInitialUIModel.setBaseUUID(simpleRequest.getBaseUUID());
            prodOrderWithPickingOrderProxy
                    .convProdOrderItemToInitialUIModel(repairProdOrderItem, prodPickingRefMaterialInitialUIModel);
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
    String addProdToPickingMaterialItem(@RequestBody ProdPickingRefMaterialInitialUIModel prodPickingRefMaterialInitialUIModel) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
            Organization organization = logonActionController.getOrganizationByUser(logonUser.getUuid());
            if (organization != null) {
                organizationUUID = organization.getUuid();
            }
            /*
             * [Step1] Get all picking material item list from base production
             * order item Calculate in plan diff amount
             */
            List<ServiceEntityNode> prodPickingRefMaterialItemList = prodPickingRefMaterialItemManager
                    .getSubRefMaterialItemListByOrderItem(prodPickingRefMaterialInitialUIModel.getBaseUUID(),
                            logonUser.getClient());
            RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) repairProdOrderManager
                    .getEntityNodeByKey(prodPickingRefMaterialInitialUIModel.getBaseUUID(),
                            IServiceEntityNodeFieldConstant.UUID,
                            RepairProdOrderItem.NODENAME, logonUser.getClient(), null);
            StorageCoreUnit inPlanDiffCoreUnit = this.getInPlanDiff(repairProdOrderItem,
                    prodPickingRefMaterialItemList);
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
                tmpRequestCoreUnit = materialStockKeepUnitManager
                        .mergeStorageUnitCore(tmpRequestCoreUnit, inPlanDiffCoreUnit, StorageCoreUnit.OPERATOR_MINUS,
                                repairProdOrderItem.getClient());
            }
            List<ServiceEntityNode> newRefMaterialItemList = new ArrayList<>();
            if (tmpRequestCoreUnit.getAmount() <= 0) {
                // in case in plan resources could meet request
                newRefMaterialItemList = prodOrderWithPickingOrderProxy
                        .updateRequestToPickingOrderWrapper(requestCoreUnit, repairProdOrderItem, null,
                                ProdPickingOrder.PROCESSTYPE_INPLAN, true, logonActionController.getLogonInfo());
            } else {
                // in case in plan resources CAN NOT meet request
                // 1st, use all in plan resources
                List<ServiceEntityNode> inPlanRefMaterialItemList = prodOrderWithPickingOrderProxy
                        .updateRequestToPickingOrderWrapper(inPlanDiffCoreUnit, repairProdOrderItem, null,
                                ProdPickingOrder.PROCESSTYPE_INPLAN, true, logonActionController.getLogonInfo());
                if (!ServiceCollectionsHelper.checkNullList(inPlanRefMaterialItemList)) {
                    newRefMaterialItemList.addAll(inPlanRefMaterialItemList);
                }
                // 2nd, use out of plan request
                List<ServiceEntityNode> outPlanRefMaterialItemList = prodOrderWithPickingOrderProxy
                        .updateRequestToPickingOrderWrapper(tmpRequestCoreUnit, repairProdOrderItem, null,
                                ProdPickingOrder.PROCESSTYPE_REPLENISH, true, logonActionController.getLogonInfo());
                if (!ServiceCollectionsHelper.checkNullList(outPlanRefMaterialItemList)) {
                    newRefMaterialItemList.addAll(outPlanRefMaterialItemList);
                }
            }
            prodPickingRefMaterialInitialUIModel.setRefUnitUUID(inPlanDiffCoreUnit.getRefUnitUUID());
            prodPickingRefMaterialInitialUIModel.setInPlanAmount(inPlanDiffCoreUnit.getAmount());
            prodPickingRefMaterialInitialUIModel.setInPlanUnitUUID(inPlanDiffCoreUnit.getRefUnitUUID());
            prodPickingRefMaterialInitialUIModel.setRefMaterialSKUUUID(repairProdOrderItem.getRefMaterialSKUUUID());
            if (!ServiceCollectionsHelper.checkNullList(newRefMaterialItemList)) {
                ProdPickingRefMaterialItem prodPickingRefMaterialItem =
                        (ProdPickingRefMaterialItem) newRefMaterialItemList.get(0);
                prodPickingRefMaterialInitialUIModel.setRefOrderItemUUID(prodPickingRefMaterialItem.getParentNodeUUID());
            }
            prodOrderWithPickingOrderProxy
                    .convProdOrderItemToInitialUIModel(repairProdOrderItem, prodPickingRefMaterialInitialUIModel);
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
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            /*
             * [Step1] Get all picking material item list from base production
             * order item Calculate all the actual amount
             */
            List<ServiceEntityNode> prodPickingRefMaterialItemList = prodPickingRefMaterialItemManager
                    .getSubRefMaterialItemListByOrderItem(simpleRequest.getBaseUUID(), logonUser.getClient());
            RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) repairProdOrderManager
                    .getEntityNodeByKey(simpleRequest.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID,
                            RepairProdOrderItem.NODENAME, logonUser.getClient(), null);
            StorageCoreUnit allExistCoreUnit = prodPickingOrderManager
                    .calculateSuppliedGrossAmount(prodPickingRefMaterialItemList,
                            repairProdOrderItem.getRefMaterialSKUUUID());
            ProdPickingRefMaterialInitialUIModel prodPickingRefMaterialInitialUIModel =
                    new ProdPickingRefMaterialInitialUIModel();
            prodPickingRefMaterialInitialUIModel.setAmount(allExistCoreUnit.getAmount());
            prodPickingRefMaterialInitialUIModel.setRefUnitUUID(allExistCoreUnit.getRefUnitUUID());
            prodPickingRefMaterialInitialUIModel.setInPlanAmount(allExistCoreUnit.getAmount());
            prodPickingRefMaterialInitialUIModel.setInPlanUnitUUID(allExistCoreUnit.getRefUnitUUID());
            prodPickingRefMaterialInitialUIModel.setBaseUUID(simpleRequest.getBaseUUID());
            prodPickingRefMaterialInitialUIModel.setRefMaterialSKUUUID(repairProdOrderItem.getRefMaterialSKUUUID());
            prodOrderWithPickingOrderProxy
                    .convProdOrderItemToInitialUIModel(repairProdOrderItem, prodPickingRefMaterialInitialUIModel);
            return ServiceJSONParser.genDefOKJSONObject(prodPickingRefMaterialInitialUIModel);
        } catch (LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (MaterialException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/addProdToReturnMaterialItem", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String addProdToReturnMaterialItem(@RequestBody ProdPickingRefMaterialInitialUIModel prodPickingRefMaterialInitialUIModel) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
            Organization organization = logonActionController.getOrganizationByUser(logonUser.getUuid());
            if (organization != null) {
                organizationUUID = organization.getUuid();
            }
            /*
             * [Step1] Get all picking material item list from base production
             * order item Calculate in plan diff amount
             */
            List<ServiceEntityNode> prodPickingRefMaterialItemList = prodPickingRefMaterialItemManager
                    .getSubRefMaterialItemListByOrderItem(prodPickingRefMaterialInitialUIModel.getBaseUUID(),
                            logonUser.getClient());
            RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) repairProdOrderManager
                    .getEntityNodeByKey(prodPickingRefMaterialInitialUIModel.getBaseUUID(),
                            IServiceEntityNodeFieldConstant.UUID,
                            RepairProdOrderItem.NODENAME, logonUser.getClient(), null);
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
            StorageCoreUnit allExistCoreUnit = prodPickingOrderManager
                    .calculateSuppliedGrossAmount(prodPickingRefMaterialItemList,
                            repairProdOrderItem.getRefMaterialSKUUUID());
            /*
             * Compare the request and all the actual amount
             */
            int result = materialStockKeepUnitManager
                    .compareSKURequestsAmount(requestCoreUnit, allExistCoreUnit, repairProdOrderItem.getClient());
            if (result > 0) {
                // In case return request is larger than all the amount, raise
                // exception
                String allAmountLabel = allExistCoreUnit.getAmount() + "";
                Map<String, String> materialUnitMap = materialStockKeepUnitManager
                        .initMaterialUnitMap(repairProdOrderItem.getRefMaterialSKUUUID(),
                                repairProdOrderItem.getClient());
                if (!ServiceEntityStringHelper.checkNullString(repairProdOrderItem.getRefUnitUUID())) {
                    allAmountLabel = allAmountLabel + materialUnitMap.get(repairProdOrderItem.getRefUnitUUID());
                }
                throw new ProductionOrderException(ProductionOrderException.PARA_RETURN_EXCEED_LIMIT, allAmountLabel);
            }
            StorageCoreUnit tmpRequestCoreUnit = (StorageCoreUnit) requestCoreUnit.clone();
            // Core Execution to generate picking material item
            ProdPickingRefMaterialItem prodPickingRefMaterialItem = prodOrderWithPickingOrderProxy
                    .updateRequestToReturnOrderWrapper(tmpRequestCoreUnit, repairProdOrderItem, true,
                            logonActionController.getLogonInfo());
            prodPickingRefMaterialInitialUIModel.setRefUnitUUID(requestCoreUnit.getRefUnitUUID());
            prodPickingRefMaterialInitialUIModel.setAmount(requestCoreUnit.getAmount());
            prodPickingRefMaterialInitialUIModel.setInPlanAmount(allExistCoreUnit.getAmount());
            prodPickingRefMaterialInitialUIModel.setInPlanUnitUUID(allExistCoreUnit.getRefUnitUUID());
            prodPickingRefMaterialInitialUIModel.setRefMaterialSKUUUID(repairProdOrderItem.getRefMaterialSKUUUID());
            prodPickingRefMaterialInitialUIModel.setRefOrderItemUUID(prodPickingRefMaterialItem.getParentNodeUUID());
            prodOrderWithPickingOrderProxy
                    .convProdOrderItemToInitialUIModel(repairProdOrderItem, prodPickingRefMaterialInitialUIModel);
            return ServiceJSONParser.genDefOKJSONObject(prodPickingRefMaterialInitialUIModel);
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException | ServiceModuleProxyException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (MaterialException | BillOfMaterialException | SearchConfigureException | ProductionOrderException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        } catch (ServiceComExecuteException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    private StorageCoreUnit getInPlanDiff(RepairProdOrderItem repairProdOrderItem,
                                          List<ServiceEntityNode> prodPickingRefMaterialItemList) throws MaterialException, ServiceEntityConfigureException, DocActionException {
        StorageCoreUnit storageCoreUnit = new StorageCoreUnit();
        storageCoreUnit.setAmount(repairProdOrderItem.getAmountWithLossRate());
        storageCoreUnit.setRefUnitUUID(repairProdOrderItem.getRefUnitUUID());
        storageCoreUnit.setRefMaterialSKUUUID(repairProdOrderItem.getRefMaterialSKUUUID());
        if (ServiceCollectionsHelper.checkNullList(prodPickingRefMaterialItemList)) {
            return storageCoreUnit;
        }
        StorageCoreUnit allExistCoreUnit = prodPickingOrderManager
                .calculateSuppliedGrossAmount(prodPickingRefMaterialItemList,
                        repairProdOrderItem.getRefMaterialSKUUUID());
        if (allExistCoreUnit == null) {
            return storageCoreUnit;
        }
        storageCoreUnit = materialStockKeepUnitManager
                .mergeStorageUnitCore(storageCoreUnit, allExistCoreUnit, StorageCoreUnit.OPERATOR_MINUS,
                        repairProdOrderItem.getClient());
        return storageCoreUnit;
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String newModuleService() {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            RepairProdOrder repairProdOrder =
                    (RepairProdOrder) repairProdOrderManager.newRootEntityNode(logonUser.getClient());
            RepairProdOrderServiceModel repairProdOrderServiceModel = new RepairProdOrderServiceModel();
            repairProdOrderServiceModel.setRepairProdOrder(repairProdOrder);
            RepairProdOrderServiceUIModel repairProdOrderServiceUIModel =
                    (RepairProdOrderServiceUIModel) repairProdOrderManager
                    .genServiceUIModuleFromServiceModel(RepairProdOrderServiceUIModel.class,
                            RepairProdOrderServiceModel.class,
                            repairProdOrderServiceModel, repairProdOrderServiceUIModelExtension,
                            logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONObject(repairProdOrderServiceUIModel);
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
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

    @RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModule(String uuid) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
                    .getEntityNodeByUUID(uuid, RepairProdOrder.NODENAME, logonUser.getClient());
            repairProdOrderManager.updateBuffer(repairProdOrder);
            return ServiceJSONParser.genDefOKJSONObject(repairProdOrder);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/genRepairProdOrderProposal", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String genRepairProdOrderProposal(String uuid) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
                    .getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdOrder.NODENAME,
                            logonUser.getClient(),
                            null);
            RepairProdOrderServiceModel repairProdOrderServiceModel = (RepairProdOrderServiceModel) repairProdOrderManager
                    .loadServiceModule(RepairProdOrderServiceModel.class, repairProdOrder, repairProdOrderServiceUIModelExtension);
            List<ServiceEntityNode> rawProdOrderItemList = repairProdOrderManager
                    .generateProductItemListEntry(repairProdOrderServiceModel, logonActionController.getLogonInfo(), true);
            if(!ServiceCollectionsHelper.checkNullList(rawProdOrderItemList)){
                repairProdOrderManager.updateSENodeList(rawProdOrderItemList, logonUser.getUuid(),
                        logonActionController.getLogonInfo().getResOrgUUID());
            }
            RepairProdOrderServiceUIModel productionrderServiceUIModel =
                    (RepairProdOrderServiceUIModel) repairProdOrderManager
                    .genServiceUIModuleFromServiceModel(RepairProdOrderServiceUIModel.class,
                            RepairProdOrderServiceModel.class,
                            repairProdOrderServiceModel, repairProdOrderServiceUIModelExtension,
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
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
                    .getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdOrder.NODENAME,
                            logonUser.getClient(),
                            null);
            BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) billOfMaterialOrderManager
                    .getEntityNodeByKey(repairProdOrder.getRefBillOfMaterialUUID(),
                            IServiceEntityNodeFieldConstant.UUID,
                            BillOfMaterialOrder.NODENAME, repairProdOrder.getClient(), null);
            if (billOfMaterialOrder == null) {
                throw new BillOfMaterialException(BillOfMaterialException.PARA_NO_BOMOrder,
                        repairProdOrder.getRefBillOfMaterialUUID());
            }
            ServiceModule productiveBOMOrderServiceModel =
                    repairProdOrderManager.generateProductiveBOMData(repairProdOrder);
            ProductiveBOMOrderServiceUIModel productiveBOMOrderServiceUIModel =
                    (ProductiveBOMOrderServiceUIModel) productiveBOMOrderManager
                    .genServiceUIModuleFromServiceModel(ProductiveBOMOrderServiceUIModel.class,
                            ProductiveBOMOrderServiceModel.class,
                            productiveBOMOrderServiceModel, productiveBOMOrderServiceUIModelExtension,
                            logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONObject(productiveBOMOrderServiceUIModel);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | BillOfMaterialException | ServiceUIModuleProxyException | MaterialException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/loadLeanViewService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadLeanViewService(String uuid) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
                    .getEntityNodeByUUID(uuid, RepairProdOrder.NODENAME, logonUser.getClient());
            if (repairProdOrder == null) {
                throw new ProductionOrderException(ProductionOrderException.PARA_NO_PRODORDER, uuid);
            }
            repairProdOrderManager.updateBuffer(repairProdOrder);
            RepairProdOrderServiceModel repairProdOrderServiceModel = (RepairProdOrderServiceModel) repairProdOrderManager
                    .loadServiceModule(RepairProdOrderServiceModel.class, repairProdOrder, repairProdOrderServiceUIModelExtension);
            List<ServiceModuleConvertPara> addtionalConvertParaList = new ArrayList<ServiceModuleConvertPara>();
            RepairProdOrderUIModel repairProdOrderUIModel = (RepairProdOrderUIModel) repairProdOrderManager
                    .genUIModelFromUIModelExtension(RepairProdOrderUIModel.class,
                            repairProdOrderServiceUIModelExtension.genUIModelExtensionUnion().get(0), repairProdOrder,
                            logonActionController.getLogonInfo(), addtionalConvertParaList);
            return ServiceJSONParser.genDefOKJSONObject(repairProdOrderUIModel);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | ProductionOrderException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleViewService(String uuid) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
                    .getEntityNodeByUUID(uuid, RepairProdOrder.NODENAME, logonUser.getClient());
            if (repairProdOrder == null) {
                throw new ProductionOrderException(ProductionOrderException.PARA_NO_PRODORDER, uuid);
            }
            repairProdOrderManager.updateBuffer(repairProdOrder);
            RepairProdOrderServiceModel repairProdOrderServiceModel = (RepairProdOrderServiceModel) repairProdOrderManager
                    .loadServiceModule(RepairProdOrderServiceModel.class, repairProdOrder, repairProdOrderServiceUIModelExtension);
            RepairProdOrderServiceUIModel repairProdOrderServiceUIModel = refreshLoadServiceUIModel(
                    repairProdOrderServiceModel.getRepairProdOrder().getUuid(), ISystemActionCode.ACID_VIEW,
                    logonActionController.getLogonInfo(), false);
            return ServiceJSONParser.genDefOKJSONObject(repairProdOrderServiceUIModel);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | MaterialException | SearchConfigureException | ServiceUIModuleProxyException | ProductionOrderException | ServiceComExecuteException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/loadModuleWithPostUpdateService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleWithPostUpdateService(String uuid) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
                    .getEntityNodeByUUID(uuid, RepairProdOrder.NODENAME, logonUser.getClient());
            if (repairProdOrder == null) {
                throw new ProductionOrderException(ProductionOrderException.PARA_NO_PRODORDER, uuid);
            }
            repairProdOrderManager.updateBuffer(repairProdOrder);
            RepairProdOrderServiceModel repairProdOrderServiceModel = (RepairProdOrderServiceModel) repairProdOrderManager
                    .loadServiceModule(RepairProdOrderServiceModel.class, repairProdOrder, repairProdOrderServiceUIModelExtension);
            RepairProdOrderServiceUIModel repairProdOrderServiceUIModel = refreshLoadServiceUIModel(
                    repairProdOrderServiceModel.getRepairProdOrder().getUuid(), ISystemActionCode.ACID_EDIT,
                    logonActionController.getLogonInfo(), true);
            return ServiceJSONParser.genDefOKJSONObject(repairProdOrderServiceUIModel);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (LockObjectFailureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        } catch (ServiceModuleProxyException | ProductionOrderException | MaterialException | SearchConfigureException |
                ServiceUIModuleProxyException | ServiceComExecuteException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleEditService(String uuid) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            List<ServiceEntityNode> lockSEList = new ArrayList<>();
            RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
                    .getEntityNodeByUUID(uuid, RepairProdOrder.NODENAME, logonUser.getClient());
            if (repairProdOrder == null) {
                throw new ProductionOrderException(ProductionOrderException.PARA_NO_PRODORDER, uuid);
            }
            repairProdOrderManager.updateBuffer(repairProdOrder);
            lockSEList.add(repairProdOrder);
            lockObjectManager
                    .lockServiceEntityList(lockSEList, logonUser,
                            logonActionController.getOrganizationByUser(logonUser.getUuid()));
            RepairProdOrderServiceModel repairProdOrderServiceModel =
                    (RepairProdOrderServiceModel) repairProdOrderManager
                    .loadServiceModule(RepairProdOrderServiceModel.class, repairProdOrder);
            RepairProdOrderServiceUIModel repairProdOrderServiceUIModel = refreshLoadServiceUIModel(
                    repairProdOrderServiceModel.getRepairProdOrder().getUuid(), ISystemActionCode.ACID_EDIT,
                    logonActionController.getLogonInfo(), false);
            return ServiceJSONParser.genDefOKJSONObject(repairProdOrderServiceUIModel);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (LockObjectFailureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException | SearchConfigureException | MaterialException
                | ProductionOrderException | ServiceComExecuteException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/getPriorityCode", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPriorityCode() {
        try {
            Map<Integer, String> priorityCodeMap = repairProdOrderManager
                    .initPriorityCodeMap(logonActionController.getLanguageCode());
            return repairProdOrderManager.getDefaultSelectMap(priorityCodeMap);
        } catch (ServiceEntityInstallationException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        }
    }

    @RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getStatus() {
        try {
            Map<Integer, String> statusMap =
                    repairProdOrderManager.initStatusMap(logonActionController.getLanguageCode());
            return repairProdOrderManager.getDefaultSelectMap(statusMap);
        } catch (ServiceEntityInstallationException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        }
    }

    @RequestMapping(value = "/getGenOrderItemMode", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getGenOrderItemMode() {
        try {
            Map<Integer, String> genOrderItemModeMap =
                    repairProdOrderManager.initGenOrderItemModeMap(logonActionController.getLanguageCode());
            return repairProdOrderManager.getDefaultSelectMap(genOrderItemModeMap);
        } catch (ServiceEntityInstallationException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        }
    }

    @RequestMapping(value = "/getDoneStatus", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDoneStatus() {
        try {
            Map<Integer, String> doneStatusMap =
                    repairProdOrderManager.initDoneStatusMap(logonActionController.getLanguageCode());
            return repairProdOrderManager.getDefaultSelectMap(doneStatusMap);
        } catch (ServiceEntityInstallationException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        }
    }

    @RequestMapping(value = "/getCategory", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getCategory() {
        try {
            Map<Integer, String> categoryMap =
                    repairProdOrderManager.initCategoryMap(logonActionController.getLanguageCode());
            return repairProdOrderManager.getDefaultSelectMap(categoryMap);
        } catch (ServiceEntityInstallationException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        }
    }

    @RequestMapping(value = "/getOrderType", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getOrderType() {
        try {
            Map<Integer, String> orderTypeMap =
                    repairProdOrderManager.initOrderTypeMap(logonActionController.getLanguageCode());
            return repairProdOrderManager.getDefaultSelectMap(orderTypeMap);
        } catch (ServiceEntityInstallationException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        }
    }

    private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
        return new DocAttachmentProxy.DocAttachmentProcessPara(repairProdOrderManager,
                RepairProdOrderAttachment.NODENAME, RepairProdOrder.NODENAME, null, null, null);
    }

    /**
     * load the attachment content to consumer.
     */
    @RequestMapping(value = "/loadAttachment")
    public ResponseEntity<byte[]> loadAttachment(String uuid) {
        return serviceBasicUtilityController.loadAttachment(uuid, AOID_RESOURCE,
                genDocAttachmentProcessPara());
    }

    /**
     * Delete attachment
     */
    @RequestMapping(value = "/deleteAttachment", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String deleteAttachment(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.deleteAttachment(request, AOID_RESOURCE,
                genDocAttachmentProcessPara());
    }


    /**
     * Upload the attachment content information.
     */
    @RequestMapping(value = "/uploadAttachment", consumes = "multipart/form-data", method = RequestMethod.POST)
    public @ResponseBody
    String uploadAttachment(HttpServletRequest request) {
        return serviceBasicUtilityController.uploadAttachment(request, AOID_RESOURCE,
                genDocAttachmentProcessPara());
    }

    ;

    /**
     * Upload the attachment text information.
     */
    @RequestMapping(value = "/uploadAttachmentText", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String uploadAttachmentText(
            @RequestBody FileAttachmentTextRequest request) {
        return serviceBasicUtilityController.uploadAttachmentText(request, AOID_RESOURCE,
                genDocAttachmentProcessPara());
    }

    ;


    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest, repairProdOrderManager);
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
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
                    .getEntityNodeByKey(request.getUuid(), IServiceEntityNodeFieldConstant.UUID,
                            MaterialStockKeepUnit.NODENAME,
                            logonUser.getClient(), null);
            if (materialStockKeepUnit == null) {
                throw new ProductionOrderException(ProductionOrderException.PARA_NO_MATERIALSKU, request.getUuid());
            }
            BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager
                    .getDefaultBOMBySKU(materialStockKeepUnit.getUuid(), logonUser.getClient());
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

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String preLock(String uuid) {
        try {
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
                    .getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdOrder.NODENAME,
                            logonUser.getClient(),
                            null);
            String baseUUID = repairProdOrder.getUuid();
            List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
            lockSEList.add(repairProdOrder);
            List<ServiceEntityNode> lockResult = lockObjectManager.preLockServiceEntityList(lockSEList,
                    logonUser.getUuid());
            return lockObjectManager
                    .genJSONLockCheckResult(lockResult, repairProdOrder.getName(), repairProdOrder.getId(), baseUUID);

        } catch (ServiceEntityConfigureException e) {
            return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
        } catch (LogonInfoException e) {
            return lockObjectManager.genJSONLockCheckOtherIssue(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

    @RequestMapping(value = "/preGenFinAccount")
    public @ResponseBody
    String preGenFinAccount(@RequestBody SimpleSEJSONRequest request) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (LogonInfoException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getErrorMessage());
        } catch (AuthorizationException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getErrorMessage());
        } catch (ServiceEntityConfigureException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        }
    }

    @RequestMapping(value = "/grantReEdit", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String grantReEdit(@RequestBody SimpleSEJSONRequest request) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            serviceBasicUtilityController
                    .preCheckResourceAccessCore(ISystemAuthorizationObject.AOID_RES_DOCUMENT_GRANT, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            Organization organization = logonActionController.getOrganizationByUser(logonUser.getUuid());
            String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
            if (organization != null) {
                organizationUUID = organization.getUuid();
            }
            String baseUUID = request.getUuid();
            // Set status of repairProdOrder to inital
            RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
                    .getEntityNodeByKey(baseUUID, IServiceEntityNodeFieldConstant.UUID, RepairProdOrder.NODENAME,
                            logonUser.getClient(), null);
            repairProdOrder.setStatus(RepairProdOrder.STATUS_INITIAL);
            repairProdOrderManager.updateSENode(repairProdOrder, logonUser.getUuid(), organizationUUID);
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (AuthorizationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (LogonInfoException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    protected void registerSupplyWarehouseUnion(RepairProdOrder repairProdOrder,
                                                List<ServiceEntityNode> repairProdSupplyWarehouseList, String itemUUID, String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException {
        for (ServiceEntityNode seNode : repairProdSupplyWarehouseList) {
            RepairProdSupplyWarehouse repairProdSupplyWarehouse = (RepairProdSupplyWarehouse) seNode;
            if (itemUUID.equals(repairProdSupplyWarehouse.getRefUUID())) {
                return;
            }
        }
        Warehouse warehouse = (Warehouse) warehouseManager
                .getEntityNodeByKey(itemUUID, IServiceEntityNodeFieldConstant.UUID, Warehouse.NODENAME, repairProdOrder.getClient(),
                        null);
        if (warehouse == null) {
            return;
        }
        RepairProdSupplyWarehouse repairProdSupplyWarehouse = (RepairProdSupplyWarehouse) repairProdOrderManager
                .newEntityNode(repairProdOrder, RepairProdSupplyWarehouse.NODENAME);
        repairProdOrderManager
                .buildReferenceNode(warehouse, repairProdSupplyWarehouse, ServiceEntityFieldsHelper.getCommonPackage(Warehouse.class));
        repairProdOrderManager.insertSENode(repairProdSupplyWarehouse, logonUserUUID, organizationUUID);
    }

    @RequestMapping(value = "/generateProductionItemProposal", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String generateProductionItemProposal(@RequestBody SimpleSEJSONRequest request) throws ServiceEntityConfigureException {
        try {
            String uuid = request.getUuid();
            // In case create mode, has to retrieve employee entity information
            // from buffer
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
                    .getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdOrder.NODENAME, logonUser.getClient(),
                            null);
            if (repairProdOrder == null) {
                throw new ProductionOrderException(ProductionOrderException.PARA_NO_PRODORDER, uuid);
            }
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (LogonInfoException | AuthorizationException | ProductionOrderException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/deleteWarehouse", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String deleteWarehouse(@RequestBody SimpleSEJSONRequest request) throws ServiceEntityConfigureException {
        try {
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
            Organization organization = logonActionController.getOrganizationByUser(logonUser.getUuid());
            if (organization != null) {
                organizationUUID = organization.getUuid();
            }
            RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
                    .getEntityNodeByKey(request.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID, RepairProdOrder.NODENAME,
                            logonUser.getClient(), null);
            if (repairProdOrder == null) {
                throw new ProductionOrderException(ProductionOrderException.PARA_NO_PRODORDER, request.getBaseUUID());
            }
            List<ServiceEntityNode> repairProdSupplyWarehouseListBack = repairProdOrderManager
                    .getEntityNodeListByKey(repairProdOrder.getUuid(), IServiceEntityNodeFieldConstant.PARENTNODEUUID,
                            RepairProdSupplyWarehouse.NODENAME, logonUser.getClient(), null);
            if (repairProdSupplyWarehouseListBack != null && repairProdSupplyWarehouseListBack.size() > 0) {
                for (ServiceEntityNode seNode : repairProdSupplyWarehouseListBack) {
                    RepairProdSupplyWarehouse repairProdSupplyWarehouse = (RepairProdSupplyWarehouse) seNode;
                    if (request.getUuid().equals(repairProdSupplyWarehouse.getUuid())) {
                        repairProdOrderManager.deleteSENode(seNode, logonUser.getUuid(), organizationUUID);
                    }
                }
            }
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (LogonInfoException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getErrorMessage());
        } catch (ServiceEntityConfigureException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        } catch (ProductionOrderException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/chooseWarehouseToRepairProdOrder", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String chooseWarehouseToRepairProdOrder(@RequestBody SimpleSEJSONRequest request) throws ServiceEntityConfigureException {
        try {
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
            Organization organization = logonActionController.getOrganizationByUser(logonUser.getUuid());
            if (organization != null) {
                organizationUUID = organization.getUuid();
            }
            RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
                    .getEntityNodeByKey(request.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID, RepairProdOrder.NODENAME,
                            logonUser.getClient(), null);
            if (repairProdOrder == null) {
                throw new ProductionOrderException(ProductionOrderException.PARA_NO_PRODORDER, request.getBaseUUID());
            }
            List<ServiceEntityNode> repairProdSupplyWarehouseListBack = repairProdOrderManager
                    .getEntityNodeListByKey(repairProdOrder.getUuid(), IServiceEntityNodeFieldConstant.PARENTNODEUUID,
                            RepairProdSupplyWarehouse.NODENAME, logonUser.getClient(), null);
            registerSupplyWarehouseUnion(repairProdOrder, repairProdSupplyWarehouseListBack, request.getUuid(), logonUser.getUuid(),
                    organizationUUID);
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (LogonInfoException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getErrorMessage());
        } catch (ServiceEntityConfigureException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        } catch (ProductionOrderException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

}
