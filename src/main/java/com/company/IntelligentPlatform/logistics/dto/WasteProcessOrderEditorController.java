package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.dto.WasteProcessOrderInitModel;
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
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.*;

import jakarta.servlet.http.HttpServletRequest;

@Scope("session")
@Controller(value = "wasteProcessOrderEditorController")
@RequestMapping(value = "/wasteProcessOrder")
public class WasteProcessOrderEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected WasteProcessOrderManager wasteProcessOrderManager;

    @Autowired
    protected WasteProcessOrderServiceUIModelExtension wasteProcessOrderServiceUIModelExtension;

    @Autowired
    protected DocAttachmentProxy docAttachmentProxy;

    @Autowired
    protected WasteProcessOrderActionExecutionProxy wasteProcessOrderActionExecutionProxy;

    protected Logger logger = LoggerFactory.getLogger(WasteProcessOrderEditorController.class);

    @RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionConfigureList() {
        return serviceBasicUtilityController.getDocActionConfigureListCore(this.wasteProcessOrderActionExecutionProxy);
    }

    public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
        return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
                WasteProcessOrderServiceUIModel.class,
                WasteProcessOrderServiceModel.class, AOID_RESOURCE,
                WasteProcessOrder.NODENAME, WasteProcessOrder.SENAME,
                wasteProcessOrderManager, WasteProcessOrderActionNode.NODENAME, wasteProcessOrderActionExecutionProxy
        );
    }

    @RequestMapping(value = "/getProcessTypeMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getProcessTypeMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> wasteProcessOrderManager.initProcessType(lanCode));
    }

    @RequestMapping(value = "/getStatusMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getStatusMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> wasteProcessOrderManager.initStatus(lanCode));
    }

    @RequestMapping(value = "/getPriorityMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPriorityMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> wasteProcessOrderManager.initPriorityCode(lanCode));
    }

    @RequestMapping(value = "/getItemStatusMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getItemStatusMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> wasteProcessOrderManager.initItemStatus(lanCode));
    }

    @RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDocActionNodeList(String uuid, String actionCode) {
        return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String saveModuleService(@RequestBody String request) {
        return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
    public @ResponseBody String executeDocAction(@RequestBody String request) {
        return serviceBasicUtilityController.executeDocActionFramework(request,
                (DocActionNodeProxy.IActionExecutor<WasteProcessOrderServiceModel>) (wasteProcessOrderServiceModel, actionCode, logonInfo) -> {
                    try {
                        wasteProcessOrderActionExecutionProxy.executeActionCore(wasteProcessOrderServiceModel,
                                actionCode, logonActionController.getSerialLogonInfo());
                    } catch (DocActionException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                }, getDocUIModelRequest());
    }

    public @RequestMapping(value = "/newModuleFromStore", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String newModuleFromStore(
            @RequestBody String request) {
        return serviceBasicUtilityController.genDefNextDocBatchWrapper(request,
                IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM, AOID_RESOURCE, WasteProcessOrderInitModel.class,
                null);
    }

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkDuplicateID(
            @RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest, wasteProcessOrderManager);
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String preLockService(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.preLock(request.getUuid(), ISystemActionCode.ACID_EDIT, getDocUIModelRequest());
    }

    @RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModule(String uuid) {
        return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
                getDocUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleViewService(String uuid) {
        return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
            getDocUIModelRequest());
    }

    public @RequestMapping(value = "/genDefNextDocBatchReserved", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String genDefNextDocBatchReserved(
            @RequestBody String request) {
        return serviceBasicUtilityController.genDefNextDocBatchReserved(request,
                IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
                null);
    }

    @RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleEditService(String uuid) {
        return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
                getDocUIModelRequest());
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(
            @RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

    private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
        return new DocAttachmentProxy.DocAttachmentProcessPara(wasteProcessOrderManager,
                WasteProcessOrderAttachment.NODENAME, WasteProcessOrder.NODENAME, null, null, null);
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
}
