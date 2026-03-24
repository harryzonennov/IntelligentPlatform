package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.service.*;
import com.company.IntelligentPlatform.logistics.model.*;
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
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

import jakarta.servlet.http.HttpServletRequest;

@Scope("session")
@Controller(value = "purchaseReturnOrderEditorController")
@RequestMapping(value = "/purchaseReturnOrder")
public class PurchaseReturnOrderEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.PurchaseReturnOrder;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected PurchaseReturnOrderManager purchaseReturnOrderManager;

    @Autowired
    protected PurchaseReturnOrderServiceUIModelExtension purchaseReturnOrderServiceUIModelExtension;

    @Autowired
    protected DocAttachmentProxy docAttachmentProxy;

    @Autowired
    protected PurchaseReturnOrderActionExecutionProxy purchaseReturnOrderActionExecutionProxy;

    protected Logger logger = LoggerFactory.getLogger(PurchaseReturnOrderEditorController.class);

    public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
        return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
                PurchaseReturnOrderServiceUIModel.class,
                PurchaseReturnOrderServiceModel.class, AOID_RESOURCE,
                PurchaseReturnOrder.NODENAME, PurchaseReturnOrder.SENAME,
                purchaseReturnOrderManager, PurchaseReturnOrderActionNode.NODENAME, purchaseReturnOrderActionExecutionProxy
        );
    }

    private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
        return new DocAttachmentProxy.DocAttachmentProcessPara(purchaseReturnOrderManager,
                PurchaseReturnOrderAttachment.NODENAME, PurchaseReturnOrder.NODENAME, null, null, null);
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

    @RequestMapping(value = "/getStatusMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getStatusMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> purchaseReturnOrderManager.initStatus(lanCode));
    }

    @RequestMapping(value = "/getPriorityMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPriorityMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> purchaseReturnOrderManager.initPriorityCode(lanCode));
    }

    @RequestMapping(value = "/getItemStatusMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getItemStatusMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> purchaseReturnOrderManager.initItemStatus(lanCode));
    }

    public @RequestMapping(value = "/loadProperTargetDocListBatchGenFromPrevDoc", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String loadProperTargetDocListBatchGenFromPrevDoc(
            @RequestBody String request) {
        return serviceBasicUtilityController.loadProperTargetDocListBatchGenFromPrevDoc(request,
                IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER, DeliveryMatItemBatchGenRequest.class, AOID_RESOURCE);
    }

    public @RequestMapping(value = "/genDefNextDocBatchReserved", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String genDefNextDocBatchReserved(
            @RequestBody String request) {
        return serviceBasicUtilityController.genDefNextDocBatchReserved(request,
                IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
                null);
    }

    @RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDocActionNodeList(String uuid, String actionCode) {
        return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String preLockService(SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.preLock(request.getUuid(), ISystemActionCode.ACID_EDIT, getDocUIModelRequest());
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
    String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
        simpleRequest.setClient(logonActionController.getClient());
        return super.checkDuplicateIDCore(simpleRequest, purchaseReturnOrderManager);
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String saveModuleService(@RequestBody String request) {
        return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDocActionConfigureList() {
        return serviceBasicUtilityController.getDocActionConfigureListCore(
                this.purchaseReturnOrderActionExecutionProxy);
    }

    @RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String executeDocAction(@RequestBody String request) {
        return serviceBasicUtilityController.executeDocActionFramework(request,
                (DocActionNodeProxy.IActionExecutor<PurchaseReturnOrderServiceModel>) (purchaseReturnOrderServiceModel, actionCode, logonInfo) -> {
                    try {
                        purchaseReturnOrderActionExecutionProxy.executeActionCore(purchaseReturnOrderServiceModel,
                                actionCode, logonActionController.getSerialLogonInfo());
                    } catch (DocActionException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                }, getDocUIModelRequest());
    }

}
