package com.company.IntelligentPlatform.production.controller;

import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
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
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "prodReturnOrderEditorController")
@RequestMapping(value = "/prodReturnOrder")
public class ProdReturnOrderEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = ProdPickingOrder.SENAME;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ProdPickingOrderServiceUIModelExtension prodPickingOrderServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected ProdPickingOrderManager prodPickingOrderManager;

    @Autowired
    protected ProductionOrderManager productionOrderManager;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    @Autowired
    protected ProdReturnOrderActionExecutionProxy prodReturnOrderActionExecutionProxy;

    @Autowired
    protected ProdPickingRefMaterialItemServiceUIModelExtension prodPickingRefMaterialItemServiceUIModelExtension;

    protected Logger logger = LoggerFactory.getLogger(ProdReturnOrderEditorController.class);

    @Autowired
    private ProdReturnOrderSpecifier prodReturnOrderSpecifier;


    public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
        return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
                ProdPickingOrderServiceUIModel.class,
                ProdPickingOrderServiceModel.class, AOID_RESOURCE,
                ProdPickingOrder.NODENAME, ProdPickingOrder.SENAME, prodPickingOrderServiceUIModelExtension,
                prodPickingOrderManager, ProdPickingOrderActionNode.NODENAME, prodReturnOrderActionExecutionProxy
        );
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        return this.executeActionCore(request, ISystemActionCode.ACID_EDIT, 0,
                null);
    }

    @RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionNodeList(String uuid, String actionCode) {
        return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
    }

    @RequestMapping(value = "/addProductionOrder", produces = "text/html;charset=UTF-8")
    public @ResponseBody String addProductionOrder(
            @RequestBody SimpleSEJSONRequest request) {
        return this.executeActionCore(String.valueOf(request), ISystemActionCode.ACID_EDIT, 0,
                (prodPickingOrderServiceModel, logonInfo) -> {
                    try {
                        ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
                                .getEntityNodeByKey(request.getBaseUUID(),
                                        IServiceEntityNodeFieldConstant.UUID,
                                        ProductionOrder.NODENAME, logonInfo.getClient(),
                                        null);
                        List<ServiceEntityNode> productionOrderItemList = productionOrderManager
                                .getEntityNodeListByKey(request.getBaseUUID(),
                                        IServiceEntityNodeFieldConstant.PARENTNODEUUID,
                                        ProductionOrderItem.NODENAME,
                                        logonInfo.getClient(), null);
                        prodPickingOrderManager.updateProdOrderToPickingOrder(
                                productionOrder, productionOrderItemList,
                                prodPickingOrderServiceModel);
                    } catch (MaterialException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                });
    }


    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService() {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(ProdPickingOrder.SENAME, ProdPickingOrder.NODENAME, null,
                        null, prodReturnOrderSpecifier, null),
                ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody String checkDuplicateID(
            @RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest,
                prodPickingOrderManager);
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
    String preLockService(@RequestBody SimpleSEJSONRequest request) {
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
        return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT,
                true, getDocUIModelRequest());
    }

    @RequestMapping(value = "/getToPickMatItemList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getToPickMatItemList(String baseUUID) {
        return serviceBasicUtilityController.getListMeta(() -> {
            try {
                List<ServiceEntityNode> rawList = prodPickingOrderManager.getReturnToPickListBatch(baseUUID,
                        logonActionController.getClient());
                rawList = serviceBasicUtilityController.sortServiceEntityList(rawList);
                return getPickingMatItemListCore(rawList);
            } catch (MaterialException | ServiceModuleProxyException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        });
    }

    private List<ProdPickingRefMaterialItemUIModel> getPickingMatItemListCore(List<ServiceEntityNode> rawList) throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return serviceBasicUtilityController.convUIModuleList(ProdPickingRefMaterialItemUIModel.class, rawList,
                prodPickingOrderManager, prodPickingRefMaterialItemServiceUIModelExtension);
    }

    protected ProdPickingOrderServiceUIModel parseToServiceUIModel(
            @RequestBody String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("prodPickingRefMaterialItemUIModelList",
                ProdPickingRefMaterialItemServiceUIModel.class);
        classMap.put("prodPickingRefOrderItemUIModelList",
                ProdPickingRefOrderItemServiceUIModel.class);
        return (ProdPickingOrderServiceUIModel) JSONObject
                .toBean(jsonObject, ProdPickingOrderServiceUIModel.class,
                        classMap);
    }

    @RequestMapping(value = "/setFinishService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String setFinishService(@RequestBody String request) {
        return this.executeActionCore(request, ISystemActionCode.ACID_EDIT,
                ProdPickingOrderActionNode.DOC_ACTION_DELIVERY_DONE,
                (prodPickingOrderServiceModel, logonInfo) -> {
                    prodPickingOrderManager.setFinishPickingOrder(prodPickingOrderServiceModel, logonInfo.getRefUserUUID(),
                            logonInfo.getResOrgUUID());
                });
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


    @RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getActionCodeMap() {
        try {
            Map<Integer, String> actionCodeMap =
                    prodReturnOrderActionExecutionProxy.getActionCodeMap(logonActionController.getLanguageCode());
            return prodPickingOrderManager.getDefaultSelectMap(actionCodeMap, false);
        } catch (ServiceEntityInstallationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionConfigureList() {
        return serviceBasicUtilityController.getDocActionConfigureListCore(this.prodReturnOrderActionExecutionProxy);
    }

    @RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
    public @ResponseBody String executeDocAction(@RequestBody String request) {
        return this.executeActionCore(request,
                (prodPickingOrderServiceModel, actionCode, logonInfo) -> {
                    try {
                        prodReturnOrderActionExecutionProxy.executeActionCore(prodPickingOrderServiceModel,
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
                prodReturnOrderActionExecutionProxy, ProdPickingOrder.SENAME,
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
                            return refreshLoadServiceUIModel(prodPickingOrder.getUuid(), acId, logonInfo);
                        } catch (MaterialException e) {
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
                            return refreshLoadServiceUIModel(prodPickingOrder.getUuid(), acId, logonInfo);
                        } catch (MaterialException e) {
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


    protected ProdPickingOrderServiceUIModel refreshLoadServiceUIModel(
            String uuid, String acId, LogonInfo logonInfo)
            throws ServiceEntityConfigureException,
            ServiceModuleProxyException, ServiceUIModuleProxyException,
            LogonInfoException, AuthorizationException {
        // Refresh service model
        ProdPickingOrder prodPickingOrder = (ProdPickingOrder) prodPickingOrderManager
                .getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
                        ProdPickingOrder.NODENAME, logonInfo.getClient(), null);
        if (!ServiceEntityStringHelper.checkNullString(acId)) {
            boolean checkAuthor = serviceBasicUtilityController
                    .checkTargetDataAccess(logonInfo.getLogonUser(),
                            prodPickingOrder, acId,
                            logonInfo.getAuthorizationActionCodeMap());
            if (!checkAuthor) {
                throw new AuthorizationException(
                        AuthorizationException.TYPE_NO_AUTHORIZATION);
            }
        }
        ProdPickingOrderServiceModel prodPickingOrderServiceModel = (ProdPickingOrderServiceModel) prodPickingOrderManager
                .loadServiceModule(ProdPickingOrderServiceModel.class,
                        prodPickingOrder);
        ProdPickingOrderServiceUIModel prodPickingOrderServiceUIModel = (ProdPickingOrderServiceUIModel) prodPickingOrderManager
                .genServiceUIModuleFromServiceModel(
                        ProdPickingOrderServiceUIModel.class,
                        ProdPickingOrderServiceModel.class,
                        prodPickingOrderServiceModel,
                        prodPickingOrderServiceUIModelExtension, logonActionController.getLogonInfo());
        return prodPickingOrderServiceUIModel;
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody String exitEditor(
            @RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

    @RequestMapping(value = "/getCategory", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getCategory() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> prodPickingOrderManager.initCategoryMap(lanCode));
    }

    @RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getStatus() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> prodPickingOrderManager.initStatusMap(lanCode));
    }

    @RequestMapping(value = "/getProcessType", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getProcessType() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> prodPickingOrderManager.getProcessTypeMap(ProdPickingOrderManager.getReturnType(), lanCode));
    }

    @RequestMapping(value = "/getNextOrderType", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getNextOrderType() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> prodPickingOrderManager.getOrderTypeMap(ProdPickingOrderManager.getReturnNextOrderType(), lanCode));
    }

    @RequestMapping(value = "/getPrevOrderType", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getPrevOrderType() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> prodPickingOrderManager.getOrderTypeMap(ProdPickingOrderManager.getReturnPrevOrderType(), lanCode));
    }
}
