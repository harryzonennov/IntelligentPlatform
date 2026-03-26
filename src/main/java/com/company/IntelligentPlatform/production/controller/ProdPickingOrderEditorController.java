package com.company.IntelligentPlatform.production.controller;

import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemSearchModel;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemManager;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "prodPickingOrderEditorController")
@RequestMapping(value = "/prodPickingOrder")
public class ProdPickingOrderEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = ProdPickingOrder.SENAME;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ProdPickingOrderServiceUIModelExtension prodPickingOrderServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected WarehouseStoreItemManager warehouseStoreItemManager;

    @Autowired
    protected ProdPickingOrderManager prodPickingOrderManager;

    @Autowired
    protected ProductionOrderManager productionOrderManager;

    @Autowired
    protected ProdPickingRefMaterialItemServiceUIModelExtension prodPickingRefMaterialItemServiceUIModelExtension;

    @Autowired
    protected ProdPickingOrderActionExecutionProxy prodPickingOrderActionExecutionProxy;

    @Autowired
    protected ProdPickingOrderSpecifier prodPickingOrderSpecifier;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    protected Logger logger = LoggerFactory.getLogger(ProdPickingOrderEditorController.class);

    public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
        return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
                ProdPickingOrderServiceUIModel.class,
                ProdPickingOrderServiceModel.class, AOID_RESOURCE,
                ProdPickingOrder.NODENAME, ProdPickingOrder.SENAME, prodPickingOrderServiceUIModelExtension,
                prodPickingOrderManager, ProdPickingOrderActionNode.NODENAME, prodPickingOrderActionExecutionProxy
        );
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String saveModuleService(@RequestBody String request) {
        return this.executeActionCore(request, ISystemActionCode.ACID_EDIT, 0,
                null);

    }

    @RequestMapping(value = "/addProductionOrder", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String addProductionOrder(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getObjectMeta(() -> {
            try {
                ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
                        .getEntityNodeByKey(request.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID, ProductionOrder.NODENAME,
                                logonActionController.getClient(), null);
                List<ServiceEntityNode> productionOrderItemList = productionOrderManager
                        .getEntityNodeListByKey(request.getBaseUUID(), IServiceEntityNodeFieldConstant.PARENTNODEUUID,
                                ProductionOrderItem.NODENAME, logonActionController.getClient(), null);
                ProdPickingOrder prodPickingOrder = (ProdPickingOrder) prodPickingOrderManager
                        .getEntityNodeByKey(request.getUuid(), IServiceEntityNodeFieldConstant.UUID, ProdPickingOrder.NODENAME,
                                logonActionController.getClient(), null);
                ProdPickingOrderServiceModel prodPickingOrderServiceModel = (ProdPickingOrderServiceModel) prodPickingOrderManager
                        .loadServiceModule(ProdPickingOrderServiceModel.class, prodPickingOrder);
                prodPickingOrderManager
                        .updateProdOrderToPickingOrder(productionOrder, productionOrderItemList, prodPickingOrderServiceModel);
                prodPickingOrderManager.updateServiceModuleWithDelete(ProdPickingOrderServiceModel.class, prodPickingOrderServiceModel,
                        logonActionController.getResUserUUID(), logonActionController.getResOrgUUID());
                return refreshLoadServiceUIModel(productionOrder.getUuid(),
                        ISystemActionCode.ACID_EDIT, false, logonActionController.getLogonInfo());
            } catch (ServiceEntityConfigureException | ServiceUIModuleProxyException | AuthorizationException |
                     ServiceModuleProxyException | MaterialException | LogonInfoException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }, AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService() {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(ProdPickingOrder.SENAME, ProdPickingOrder.NODENAME, null,
                        null, prodPickingOrderSpecifier, null),
                ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionNodeList(String uuid, String actionCode) {
        return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
    }

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest, prodPickingOrderManager);
    }

    @RequestMapping(value = "/getToPickMatItemList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getToPickMatItemList(String baseUUID) {
        return serviceBasicUtilityController.getListMeta(() -> {
            try {
                List<ServiceEntityNode> rawList = prodPickingOrderManager.getToPickListBatch(baseUUID,
                        logonActionController.getClient());
                rawList = serviceBasicUtilityController.sortServiceEntityList(rawList);
                return getPickingMatItemListCore(rawList);
            } catch (ServiceModuleProxyException | MaterialException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }, AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
    }

    private List<ProdPickingRefMaterialItemUIModel> getPickingMatItemListCore(List<ServiceEntityNode> rawList) throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return serviceBasicUtilityController.convUIModuleList(ProdPickingRefMaterialItemUIModel.class, rawList,
                prodPickingOrderManager, prodPickingRefMaterialItemServiceUIModelExtension);
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
    public @ResponseBody String loadModule(String uuid) {
        return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
                getDocUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleViewService(String uuid) {
        return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
                getDocUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleEditService(String uuid) {
        return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
                getDocUIModelRequest());
    }

    private ProdPickingOrderServiceModel loadServiceModel(String uuid, String client) throws ServiceEntityConfigureException, ProductionDataException, ServiceModuleProxyException {
        ProdPickingOrder prodPickingOrder = (ProdPickingOrder) prodPickingOrderManager
                .getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProdPickingOrder.NODENAME, client,
                        null);
        if (prodPickingOrder == null) {
            throw new ProductionDataException(ProductionDataException.PARA_NO_DATA, uuid);
        }
        ProdPickingOrderServiceModel prodPickingOrderServiceModel = (ProdPickingOrderServiceModel) prodPickingOrderManager
                .loadServiceModule(ProdPickingOrderServiceModel.class, prodPickingOrder);
        return prodPickingOrderServiceModel;
    }

    @RequestMapping(value = "/loadModuleWithPostUpdateService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleWithPostUpdateService(String uuid) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            ProdPickingOrderServiceModel prodPickingOrderServiceModel = loadServiceModel(uuid, logonUser.getClient());
            // set the gross cost
            prodPickingOrderManager.setGrossCost(prodPickingOrderServiceModel);
            ProdPickingOrderServiceUIModel prodPickingOrderServiceUIModel = refreshLoadServiceUIModel(uuid,
                    ISystemActionCode.ACID_VIEW, true, logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONObject(prodPickingOrderServiceUIModel);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException | ProductionDataException |
                 MaterialException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    private ProdPickingOrderServiceUIModel refreshLoadServiceUIModel(String uuid, String acId, boolean postFlag, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceUIModuleProxyException, LogonInfoException,
            MaterialException, AuthorizationException, DocActionException {
        ProdPickingOrder prodPickingOrder = (ProdPickingOrder) prodPickingOrderManager
                .getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProdPickingOrder.NODENAME, logonInfo.getClient(),
                        null);
        if (!ServiceEntityStringHelper.checkNullString(acId)) {
            boolean checkAuthor = serviceBasicUtilityController
                    .checkTargetDataAccess(logonInfo.getLogonUser(), prodPickingOrder, acId,
                            logonInfo.getAuthorizationActionCodeMap());
            if (!checkAuthor) {
                throw new AuthorizationException(AuthorizationException.TYPE_NO_AUTHORIZATION);
            }
        }
        ProdPickingOrderServiceModel prodPickingOrderServiceModel = (ProdPickingOrderServiceModel) prodPickingOrderManager
                .loadServiceModule(ProdPickingOrderServiceModel.class, prodPickingOrder);
        if (postFlag) {
            prodPickingOrderManager.refreshPickingOrder(prodPickingOrderServiceModel);
        }
        // set the gross cost
        prodPickingOrderManager.setGrossCost(prodPickingOrderServiceModel);
        return (ProdPickingOrderServiceUIModel) prodPickingOrderManager
                .genServiceUIModuleFromServiceModel(ProdPickingOrderServiceUIModel.class, ProdPickingOrderServiceModel.class,
                        prodPickingOrderServiceModel, prodPickingOrderServiceUIModelExtension, logonActionController.getLogonInfo());
    }

    @RequestMapping(value = "/getInStockItemList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getInStockItemList(String baseUUID) {
        return serviceBasicUtilityController.getListMeta(() -> {
            List<ServiceEntityNode> rawList = prodPickingOrderManager.getReservedInStockStoreItemList(baseUUID,
                    IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                    logonActionController.getClient());
            rawList.sort(new ServiceEntityNodeLastUpdateTimeCompare());
            return warehouseStoreItemManager.getStoreModuleListCore(rawList, logonActionController.getLogonInfo(), WarehouseStoreItemSearchModel.BATCH_MODE_DISPLAY);
        }, AOID_RESOURCE, ISystemActionCode.ACID_LIST);
    }

    @RequestMapping(value = "/loadModuleWithPostUpdate", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleWithPostUpdate(String uuid) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
            }
            ProdPickingOrder prodPickingOrder = (ProdPickingOrder) prodPickingOrderManager
                    .getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProdPickingOrder.NODENAME, logonUser.getClient(),
                            null);
            if (prodPickingOrder == null) {
                throw new ProductionDataException(ProductionDataException.PARA_NO_DATA, uuid);
            }
            ProdPickingOrderServiceUIModel prodPickingOrderServiceUIModel = refreshLoadServiceUIModel(prodPickingOrder.getUuid(),
                    ISystemActionCode.ACID_EDIT, true, logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONObject(prodPickingOrderServiceUIModel);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (LockObjectFailureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException | ProductionDataException |
                 MaterialException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    protected ProdPickingOrderServiceUIModel parseToServiceUIModel(@RequestBody String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("prodPickingRefMaterialItemUIModelList", ProdPickingRefMaterialItemServiceUIModel.class);
        classMap.put("prodPickingRefOrderItemUIModelList", ProdPickingRefOrderItemServiceUIModel.class);
        ProdPickingOrderServiceUIModel prodPickingOrderServiceUIModel = (ProdPickingOrderServiceUIModel) JSONObject
                .toBean(jsonObject, ProdPickingOrderServiceUIModel.class, classMap);
        return prodPickingOrderServiceUIModel;
    }

    @RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getActionCodeMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> prodPickingOrderActionExecutionProxy.getActionCodeMap(lanCode));
    }

    @RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionConfigureList() {
        return serviceBasicUtilityController.getDocActionConfigureListCore(this.prodPickingOrderActionExecutionProxy);
    }

    @RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
    public @ResponseBody String executeDocAction(@RequestBody String request) {
        return this.executeActionCore(request,
                (prodPickingOrderServiceModel, actionCode, logonInfo) -> {
                    try {
                        prodPickingOrderActionExecutionProxy.executeActionCore(prodPickingOrderServiceModel,
                                actionCode, logonActionController.getSerialLogonInfo());
                    } catch (DocActionException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                });
    }

    String executeActionCore(@RequestBody String request,
                             DocActionNodeProxy.IActionExecutor<ProdPickingOrderServiceModel> iActionExecutor) {
        return serviceBasicUtilityController.defaultActionServiceWrapper(request, AOID_RESOURCE,
                prodPickingOrderActionExecutionProxy, ProdPickingOrder.SENAME,
                prodPickingOrderManager,
                new DocActionNodeProxy.IActionCodeServiceExecutor<ProdPickingOrderServiceModel, ProdPickingOrderServiceUIModel>() {
                    @Override
                    public ProdPickingOrderServiceModel parseToServiceModule(String request) throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
                        return parseToServiceModel(request);
                    }

                    @Override
                    public boolean preExecute(ProdPickingOrderServiceModel serviceModule, int actionCode) {
                        return true;
                    }

                    @Override
                    public void executeService(ProdPickingOrderServiceModel prodPickingOrderServiceModel, int actionCode,
                                               LogonInfo logonInfo) throws DocActionException,
                            ServiceEntityConfigureException, ServiceModuleProxyException {
                        if (iActionExecutor != null) {
                            iActionExecutor.executeService(prodPickingOrderServiceModel, actionCode, logonInfo);
                        }
                    }

                    @Override
                    public ProdPickingOrderServiceUIModel refreshServiceUIModel(ProdPickingOrderServiceModel prodPickingOrderServiceModel
                            , String acId, LogonInfo logonInfo) throws ServiceEntityConfigureException,
                            LogonInfoException, ServiceModuleProxyException, ServiceUIModuleProxyException, AuthorizationException {
                        ProdPickingOrder prodPickingOrder = prodPickingOrderServiceModel.getProdPickingOrder();
                        try {
                            prodPickingOrderManager
                                    .postUpdateProdPickingOrderServiceModelAsyncWrapper(prodPickingOrderServiceModel,
                                            logonInfo.getRefUserUUID(),
                                            logonInfo.getResOrgUUID());
                            return refreshLoadServiceUIModel(prodPickingOrder.getUuid(), acId, true, logonInfo);
                        } catch (MaterialException | DocActionException e) {
                            throw new ServiceUIModuleProxyException(ServiceUIModuleProxyException.PARA_SYSTEM_WRONG,
                                    e.getErrorMessage());
                        }
                    }

                    @Override
                    public void postHandle(ProdPickingOrderServiceUIModel prodPickingOrderServiceUIModel,
                                           int actionCode,
                                           SerialLogonInfo logonInfo) throws DocActionException {

                    }

                }, prodPickingOrderServiceUIModelExtension);
    }

    private ProdPickingOrderServiceModel parseToServiceModel(String request)
            throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
        ProdPickingOrderServiceUIModel prodPickingOrderServiceUIModel = parseToServiceUIModel(request);
        return (ProdPickingOrderServiceModel) prodPickingOrderManager
                .genServiceModuleFromServiceUIModel(
                        ProdPickingOrderServiceModel.class,
                        ProdPickingOrderServiceUIModel.class,
                        prodPickingOrderServiceUIModel,
                        prodPickingOrderServiceUIModelExtension);
    }

    @Deprecated
    String executeActionCore(@RequestBody String request, String acId, int actionCode,
                             DocActionNodeProxy.IActionExecute<ProdPickingOrderServiceModel> iActionExecute) {
        return serviceBasicUtilityController.defaultActionServiceWrapper(request, AOID_RESOURCE,
                acId, IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER, actionCode, ProdPickingOrder.SENAME,
                prodPickingOrderManager,
                new DocActionNodeProxy.IActionCodeServiceWrapper<ProdPickingOrderServiceModel,
                        ProdPickingOrderServiceUIModel>() {
                    @Override
                    public ProdPickingOrderServiceModel parseToServiceModule(String request) throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
                        return parseToServiceModel(request);
                    }

                    @Override
                    public boolean preExecute(ProdPickingOrderServiceModel serviceModule, int documentType) {
                        return true;
                    }

                    @Override
                    public void executeService(ProdPickingOrderServiceModel prodPickingOrderServiceModel,
                                               LogonInfo logonInfo) throws DocActionException,
                            ServiceEntityConfigureException, ServiceModuleProxyException {
                        if (iActionExecute != null) {
                            iActionExecute.executeService(prodPickingOrderServiceModel, logonInfo);
                        }
                    }

                    @Override
                    public ProdPickingOrderServiceUIModel refreshServiceUIModel(ProdPickingOrderServiceModel prodPickingOrderServiceModel
                            , String acId, LogonInfo logonInfo) throws ServiceEntityConfigureException,
                            LogonInfoException, ServiceModuleProxyException, ServiceUIModuleProxyException,
                            AuthorizationException {
                        ProdPickingOrder prodPickingOrder = prodPickingOrderServiceModel.getProdPickingOrder();
                        try {
                            prodPickingOrderManager
                                    .postUpdateProdPickingOrderServiceModelAsyncWrapper(prodPickingOrderServiceModel,
                                            logonInfo.getRefUserUUID(),
                                            logonInfo.getResOrgUUID());
                            return refreshLoadServiceUIModel(prodPickingOrder.getUuid(), acId, true, logonInfo);
                        } catch (MaterialException | DocActionException e) {
                            throw new ServiceUIModuleProxyException(ServiceUIModuleProxyException.PARA_SYSTEM_WRONG,
                                    e.getErrorMessage());
                        }
                    }

                    @Override
                    public void postHandle(ProdPickingOrderServiceUIModel serviceUIModule, int actionCode,
                                           SerialLogonInfo logonInfo) {

                    }

                }, prodPickingOrderServiceUIModelExtension);
    }

    @Deprecated
    @RequestMapping(value = "/inProcessService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String inProcessService(@RequestBody String request) {
        return this.executeActionCore(request, ISystemActionCode.ACID_EDIT,
                ProdPickingOrderActionNode.DOC_ACTION_INPROCESS,
                (prodPickingOrderServiceModel, logonInfo) -> {
                    prodPickingOrderManager.inProcessPickingOrder(prodPickingOrderServiceModel, logonInfo.getRefUserUUID(),
                            logonInfo.getResOrgUUID());
                });

    }

    @Deprecated
    @RequestMapping(value = "/setFinishService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String setFinishService(@RequestBody String request) {
        return this.executeActionCore(request, ISystemActionCode.ACID_EDIT,
                ProdPickingOrderActionNode.DOC_ACTION_DELIVERY_DONE,
                (prodPickingOrderServiceModel, logonInfo) -> {
                    prodPickingOrderManager.setFinishPickingOrder(prodPickingOrderServiceModel, logonInfo.getRefUserUUID(),
                            logonInfo.getResOrgUUID());
                });
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

    @RequestMapping(value = "/getCategory", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getCategory() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> prodPickingOrderManager.initCategoryMap(lanCode));
    }

    @RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getStatus() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> prodPickingOrderManager.initStatusMap(lanCode));
    }

    @RequestMapping(value = "/getProcessType", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getProcessType() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> prodPickingOrderManager.getProcessTypeMap(ProdPickingOrderManager.getProcessType(), lanCode));
    }

    @RequestMapping(value = "/getNextOrderType", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getNextOrderType() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> prodPickingOrderManager.initDocumentTypeMap(lanCode));
    }

    @RequestMapping(value = "/getPrevOrderType", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPrevOrderType() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> prodPickingOrderManager.getOrderTypeMap(ProdPickingOrderManager.getPrevDocType(), lanCode));
    }

}
