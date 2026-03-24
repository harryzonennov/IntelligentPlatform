package com.company.IntelligentPlatform.sales.controller;

import jakarta.servlet.http.HttpServletRequest;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.sales.dto.*;
import com.company.IntelligentPlatform.sales.service.*;
import com.company.IntelligentPlatform.sales.model.*;

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
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

@Scope("session")
@Controller(value = "salesReturnOrderEditorController")
@RequestMapping(value = "/salesReturnOrder")
public class SalesReturnOrderEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.SalesReturnOrder;

    public static final String ID_RESOURCE = IDefResourceObject.ID_SALESRETURNORDER;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected SalesReturnOrderManager salesReturnOrderManager;

    @Autowired
    protected SalesReturnOrderSpecifier salesReturnOrderSpecifier;

    @Autowired
    protected SalesReturnOrderActionExecutionProxy salesReturnOrderActionExecutionProxy;

    @Autowired
    protected SalesReturnOrderServiceUIModelExtension salesReturnOrderServiceUIModelExtension;

    @Autowired
    protected DocAttachmentProxy docAttachmentProxy;

    protected Logger logger = LoggerFactory.getLogger(SalesReturnOrderEditorController.class);

    public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
        return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
                SalesReturnOrderServiceUIModel.class,
                SalesReturnOrderServiceModel.class, AOID_RESOURCE,
                SalesReturnOrder.NODENAME, SalesReturnOrder.SENAME,
                salesReturnOrderManager, SalesReturnOrderActionNode.NODENAME, salesReturnOrderActionExecutionProxy
        );
    }

    private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
        return new DocAttachmentProxy.DocAttachmentProcessPara(salesReturnOrderManager,
                SalesReturnOrderAttachment.NODENAME, SalesReturnOrder.NODENAME, null, null, null);
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

    @RequestMapping(value = "/getStatusMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getStatusMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> salesReturnOrderManager.initStatus(lanCode));
    }

    @RequestMapping(value = "/getPriorityMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPriorityMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> salesReturnOrderManager.initPriorityCode(lanCode));
    }

    @RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionNodeList(String uuid, String actionCode) {
        return serviceBasicUtilityController.getDocActionConfigureListCore(
                this.salesReturnOrderActionExecutionProxy);
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String preLock(String uuid) {
        return serviceBasicUtilityController.preLock(uuid, ISystemActionCode.ACID_EDIT, getDocUIModelRequest());
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String preLockService(
            @RequestBody SimpleSEJSONRequest request) {
        return preLock(request.getUuid());
    }

    @RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModule(String uuid) {
        return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
                getDocUIModelRequest());
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

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkDuplicateID(
            @RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest,
                salesReturnOrderManager);
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(
            @RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

    @RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionConfigureList() {
        return serviceBasicUtilityController.getDocActionConfigureListCore(this.salesReturnOrderActionExecutionProxy);
    }

    @RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
    public @ResponseBody String executeDocAction(@RequestBody String request) {
        return serviceBasicUtilityController.executeDocActionFramework(request,
                (DocActionNodeProxy.IActionExecutor<SalesReturnOrderServiceModel>) (salesReturnOrderServiceModel, actionCode, logonInfo) -> {
                    try {
                        salesReturnOrderActionExecutionProxy.executeActionCore(salesReturnOrderServiceModel,
                                actionCode, logonActionController.getSerialLogonInfo());
                    } catch (DocActionException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                }, getDocUIModelRequest());
        
    }

    public @RequestMapping(value = "/loadProperTargetDocListBatchGenFromPrevDoc", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String loadProperTargetDocListBatchGenFromPrevDoc(
            @RequestBody String request) {
        return serviceBasicUtilityController.loadProperTargetDocListBatchGenFromPrevDoc(request,
                IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER, DeliveryMatItemBatchGenRequest.class, AOID_RESOURCE);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String saveModuleService(@RequestBody String request) {
        return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
    }

    public @RequestMapping(value = "/loadProperTargetDocListBatchGen", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String loadProperTargetDocListBatchGen(
            @RequestBody String request) {
        return serviceBasicUtilityController.loadProperTargetDocListBatchGen(request,
                IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER, DeliveryMatItemBatchGenRequest.class, AOID_RESOURCE);
    }

    public @RequestMapping(value = "/generateNextDocBatch", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String generateNextDocBatch(
            @RequestBody String request) {
        return serviceBasicUtilityController.genDefNextDocBatchWrapper(request,
                IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
                null);
    }
}