package com.company.IntelligentPlatform.production.controller;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
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

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import jakarta.servlet.http.HttpServletRequest;

@Scope("session")
@Controller(value = "billOfMaterialOrderEditorController")
@RequestMapping(value = "/billOfMaterialOrder")
public class BillOfMaterialOrderEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.BillOfMaterialOrder;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected BillOfMaterialOrderServiceUIModelExtension billOfMaterialOrderServiceUIModelExtension;

    @Autowired
    protected BillOfMaterialOrderExecutionProxy billOfMaterialOrderExecutionProxy;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    protected Logger logger = LoggerFactory.getLogger(BillOfMaterialOrderEditorController.class);

    @Autowired
    private BillOfMaterialOrderSpecifier billOfMaterialOrderSpecifier;

    public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
        return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
                BillOfMaterialOrderServiceUIModel.class,
                BillOfMaterialOrderServiceModel.class, AOID_RESOURCE,
                BillOfMaterialOrder.NODENAME, BillOfMaterialOrder.SENAME, billOfMaterialOrderServiceUIModelExtension,
                billOfMaterialOrderManager, BillOfMaterialOrderActionNode.NODENAME, billOfMaterialOrderExecutionProxy
        );
    }

    @RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionNodeList(String uuid, String actionCode) {
        return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
    }

    @RequestMapping(value = "/getLeadTimeCalModeMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getLeadTimeCalModeMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> billOfMaterialOrderManager.initLeadTimeCalModeMap(lanCode));
    }

    @RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
    public @ResponseBody String executeDocAction(@RequestBody String request) {
        return this.executeActionCore(request,
                (billOfMaterialTemplateServiceModel, actionCode, logonInfo) -> {
                    try {
                        billOfMaterialOrderExecutionProxy.executeActionCore(billOfMaterialTemplateServiceModel,
                                actionCode, logonActionController.getSerialLogonInfo());
                    } catch (DocActionException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                });
    }


    String executeActionCore(@RequestBody String request,
                             DocActionNodeProxy.IActionExecutor<BillOfMaterialOrderServiceModel> iActionExecutor) {
        return serviceBasicUtilityController.defaultActionServiceWrapper(request, AOID_RESOURCE,
                billOfMaterialOrderExecutionProxy, BillOfMaterialOrder.SENAME,
                billOfMaterialOrderManager,
                new DocActionNodeProxy.IActionCodeServiceExecutor<BillOfMaterialOrderServiceModel, BillOfMaterialOrderServiceUIModel>() {
                    @Override
                    public BillOfMaterialOrderServiceModel parseToServiceModule(String request) throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
                        return parseToServiceModel(request);
                    }

                    @Override
                    public boolean preExecute(BillOfMaterialOrderServiceModel serviceModule, int actionCode) {
                        return true;
                    }

                    @Override
                    public void executeService(BillOfMaterialOrderServiceModel billOfMaterialOrderServiceModel, int actionCode,
                                               LogonInfo logonInfo) throws DocActionException,
                            ServiceEntityConfigureException, ServiceModuleProxyException {
                        if (iActionExecutor != null) {
                            iActionExecutor.executeService(billOfMaterialOrderServiceModel, actionCode, logonInfo);
                        }
                    }

                    @Override
                    public BillOfMaterialOrderServiceUIModel refreshServiceUIModel(BillOfMaterialOrderServiceModel billOfMaterialOrderServiceModel
                            , String acId, LogonInfo logonInfo) throws ServiceEntityConfigureException,
                            LogonInfoException, ServiceModuleProxyException, ServiceUIModuleProxyException, AuthorizationException, DocActionException {
                        BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderServiceModel.getBillOfMaterialOrder();
                        return refreshLoadServiceUIModel(billOfMaterialOrder, acId);
                    }

                    @Override
                    public void postHandle(BillOfMaterialOrderServiceUIModel billOfMaterialOrderServiceUIModel,
                                           int actionCode,
                                           SerialLogonInfo logonInfo) throws DocActionException {

                    }

                }, billOfMaterialOrderServiceUIModelExtension);
    }


    @RequestMapping(value = "/getStatusMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getStatusMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> billOfMaterialOrderManager.initStatusMap(lanCode));
    }

    protected BillOfMaterialOrderServiceUIModel parseToServiceUIModel(
            @RequestBody String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("billOfMaterialItemUIModelList",
                BillOfMaterialItemServiceUIModel.class);
        classMap.put("billOfMaterialItemUpdateLogList",
                BillOfMaterialItemUpdateLogUIModel.class);
        classMap.put("billOfMaterialAttachmentUIModelList", BillOfMaterialAttachmentUIModel.class);
        return (BillOfMaterialOrderServiceUIModel) JSONObject
                .toBean(jsonObject, BillOfMaterialOrderServiceUIModel.class,
                        classMap);
    }

    private BillOfMaterialOrderServiceModel parseToServiceModel(String request)
            throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
        BillOfMaterialOrderServiceUIModel billOfMaterialOrderServiceUIModel = this.parseToServiceUIModel(request);
        return (BillOfMaterialOrderServiceModel) billOfMaterialOrderManager
                .genServiceModuleFromServiceUIModel(
                        BillOfMaterialOrderServiceModel.class,
                        BillOfMaterialOrderServiceUIModel.class,
                        billOfMaterialOrderServiceUIModel,
                        billOfMaterialOrderServiceUIModelExtension);
    }

    @RequestMapping(value = "/getBOMItemMaterialSKUList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getBOMItemMaterialSKUList(String uuid) {
        return serviceBasicUtilityController.getListMeta(
                () -> {
                    try {
                        return billOfMaterialOrderManager.getBOMItemMaterialSKUList(uuid,
                                logonActionController.getLogonInfo().getClient());
                    } catch (BillOfMaterialException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                }, AOID_RESOURCE, ISystemActionCode.ACID_LIST);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String saveModuleService(@RequestBody String request) {
        return this.executeActionCore(request,
                null);
    }

    protected BillOfMaterialOrderServiceUIModel refreshLoadServiceUIModel(
            BillOfMaterialOrder billOfMaterialOrder, String acId) throws ServiceEntityConfigureException,
            ServiceModuleProxyException, ServiceUIModuleProxyException, DocActionException, AuthorizationException, LogonInfoException {
        return (BillOfMaterialOrderServiceUIModel) serviceBasicUtilityController.refreshLoadServiceUIModel(
                getDocUIModelRequest(),
                billOfMaterialOrder, acId);
    }

    @RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getActionCodeMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> billOfMaterialOrderExecutionProxy.getActionCodeMap(lanCode));
    }

    @RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionConfigureList() {
        return serviceBasicUtilityController.getDocActionConfigureListCore(this.billOfMaterialOrderExecutionProxy);
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService() {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(
                        BillOfMaterialOrder.SENAME, BillOfMaterialOrder.NODENAME,
                        billOfMaterialOrderSpecifier), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody String checkDuplicateID(
            @RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest,
                billOfMaterialOrderManager);
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

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody String exitEditor(
            @RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

    private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
        return new DocAttachmentProxy.DocAttachmentProcessPara(billOfMaterialOrderManager,
                BillOfMaterialAttachment.NODENAME, BillOfMaterialOrder.NODENAME, null, null,
                null);
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
    public @ResponseBody String deleteAttachment(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.deleteAttachment(request, AOID_RESOURCE,
                genDocAttachmentProcessPara());
    }

    /**
     * Upload the attachment content information.
     */
    @RequestMapping(value = "/uploadAttachment", consumes = "multipart/form-data", method = RequestMethod.POST)
    public @ResponseBody String uploadAttachment(HttpServletRequest request) {
        return serviceBasicUtilityController.uploadAttachment(request, AOID_RESOURCE,
                genDocAttachmentProcessPara());
    }

    /**
     * Upload the attachment text information.
     */
    @RequestMapping(value = "/uploadAttachmentText", produces = "text/html;charset=UTF-8")
    public @ResponseBody String uploadAttachmentText(
            @RequestBody FileAttachmentTextRequest request) {
        return serviceBasicUtilityController.uploadAttachmentText(request, AOID_RESOURCE,
                genDocAttachmentProcessPara());
    }

}
