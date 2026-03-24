package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.service.*;
import com.company.IntelligentPlatform.logistics.model.WarehouseStore;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreActionNode;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreAttachment;
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
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonUser;

import jakarta.servlet.http.HttpServletRequest;

@Scope("session")
@Controller(value = "warehouseStoreEditorController")
@RequestMapping(value = "/warehouseStore")
public class WarehouseStoreEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_WAREHOUSESTORE;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected WarehouseStoreItemServiceUIModelExtension warehouseStoreItemServiceUIModelExtension;

    @Autowired
    protected WarehouseStoreItemManager warehouseStoreItemManager;

    @Autowired
    protected WarehouseStoreServiceUIModelExtension warehouseStoreServiceUIModelExtension;

    @Autowired
    protected WarehouseStoreActionExecutionProxy warehouseStoreActionExecutionProxy;

    protected Logger logger = LoggerFactory.getLogger(WarehouseStoreEditorController.class);

    public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
        return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
                WarehouseStoreServiceUIModel.class,
                WarehouseStoreServiceModel.class, AOID_RESOURCE,
                WarehouseStore.NODENAME, WarehouseStore.SENAME,
                warehouseStoreManager, WarehouseStoreActionNode.NODENAME, warehouseStoreActionExecutionProxy
        );
    }

    private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
        return new DocAttachmentProxy.DocAttachmentProcessPara(warehouseStoreManager,
                WarehouseStoreAttachment.NODENAME, WarehouseStore.NODENAME, null, null, null);
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
                lanCode -> warehouseStoreItemManager.initItemStatus(lanCode));
    }

    @RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDocActionNodeList(String uuid, String actionCode) {
        return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
    }

    @RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionConfigureList() {
        return serviceBasicUtilityController.getDocActionConfigureListCore(this.warehouseStoreActionExecutionProxy);
    }

    public @RequestMapping(value = "/genDefNextDocBatchFromPrevProf", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String genDefNextDocBatchFromPrevProf(
            @RequestBody String request) {
        String response = serviceBasicUtilityController.genDefNextDocBatchFromPrevProf(request,
                IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
                null);
        return response;
    }

    public @RequestMapping(value = "/genDefNextDocBatchToPrevProf", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String genDefNextDocBatchToPrevProf(
            @RequestBody String request) {
        String response = serviceBasicUtilityController.genDefNextDocBatchToPrevProf(request,
                IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
                null);
        return response;
    }

    @RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
    public @ResponseBody String executeDocAction(@RequestBody String request) {
        return serviceBasicUtilityController.executeDocActionFramework(request,
                (DocActionNodeProxy.IActionExecutor<WarehouseStoreServiceModel>) (warehouseStoreServiceModel, actionCode, logonInfo) -> {
                    try {
                        warehouseStoreActionExecutionProxy.executeActionCore(warehouseStoreServiceModel,
                                actionCode, logonActionController.getSerialLogonInfo());
                    } catch (DocActionException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                }, getDocUIModelRequest());
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

    public @RequestMapping(value = "/mergeDocBatch", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String mergeDocBatch(
            @RequestBody String request) {
        return serviceBasicUtilityController.mergeDocBatchWrapper(request,
                IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM,
                AOID_RESOURCE, DocumentMatItemBatchGenRequest.class, null);
    }

    public @RequestMapping(value = "/loadProperTargetDocListBatchGen", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String loadProperTargetDocListBatchGen(
            @RequestBody String request) {
        return serviceBasicUtilityController.loadProperTargetDocListBatchGen(request,
                IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM, DeliveryMatItemBatchGenRequest.class, AOID_RESOURCE);
    }

    public @RequestMapping(value = "/generateNextDocBatch", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String generateNextDocBatch(
            @RequestBody String request) {
        return serviceBasicUtilityController.genDefNextDocBatchWrapper(request,
                IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
                null);
    }

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkDuplicateID(
            @RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest,
                warehouseStoreManager);
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(
            @RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String saveModuleService(@RequestBody String request) {
        return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
    }
}
