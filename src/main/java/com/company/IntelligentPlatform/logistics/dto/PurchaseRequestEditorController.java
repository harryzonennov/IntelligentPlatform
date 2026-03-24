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
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import jakarta.servlet.http.HttpServletRequest;

@Scope("session")
@Controller(value = "purchaseRequestEditorController")
@RequestMapping(value = "/purchaseRequest")
public class PurchaseRequestEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.PurchaseRequest;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected PurchaseRequestManager purchaseRequestManager;

    @Autowired
    protected PurchaseRequestMaterialItemExcelHelper purchaseRequestMaterialItemExcelHelper;

    @Autowired
    protected PurchaseRequestMaterialItemManager purchaseRequestMaterialItemManager;

    @Autowired
    protected PurchaseRequestSpecifier purchaseRequestSpecifier;

    @Autowired
    protected PurchaseRequestActionExecutionProxy purchaseRequestActionExecutionProxy;

    protected Logger logger = LoggerFactory.getLogger(PurchaseRequestEditorController.class);

    private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
        return new DocAttachmentProxy.DocAttachmentProcessPara(purchaseRequestManager,
                PurchaseRequestAttachment.NODENAME, PurchaseRequest.NODENAME, null, null, null);
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
                lanCode -> purchaseRequestManager.initStatus(lanCode));
    }

    @RequestMapping(value = "/getPriorityMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPriorityMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> purchaseRequestManager.initPriorityCode(lanCode));
    }

    public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
        return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
                PurchaseRequestServiceUIModel.class,
                PurchaseRequestServiceModel.class, AOID_RESOURCE,
                PurchaseRequest.NODENAME, PurchaseRequest.SENAME,
                purchaseRequestManager, PurchaseRequestActionNode.NODENAME, purchaseRequestActionExecutionProxy
        );
    }

    @RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDocActionNodeList(String uuid, String actionCode) {
        return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
    }

    @RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDocActionConfigureList() {
        return serviceBasicUtilityController.getDocActionConfigureListCore(this.purchaseRequestActionExecutionProxy);
    }

    @RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String executeDocAction(@RequestBody String request) {
        return serviceBasicUtilityController.executeDocActionFramework(request,
                (DocActionNodeProxy.IActionExecutor<PurchaseRequestServiceModel>) (purchaseRequestServiceModel, actionCode, logonInfo) -> {
                    try {
                        purchaseRequestActionExecutionProxy.executeActionCore(purchaseRequestServiceModel,
                                actionCode, logonActionController.getSerialLogonInfo());
                    } catch (DocActionException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                }, getDocUIModelRequest());
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService() {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(PurchaseRequest.SENAME, PurchaseRequest.NODENAME, null,
                        null, purchaseRequestSpecifier, null),
                         ISystemActionCode.ACID_EDIT);
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

    public @RequestMapping(value = "/mergeDocBatch", produces = "text/html;" + "charset=UTF-8")
    @ResponseBody
    String mergeDocBatch(@RequestBody String request) {
        return serviceBasicUtilityController.mergeDocBatchWrapper(request,
                IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST, AOID_RESOURCE, DocumentMatItemBatchGenRequest.class,
                null);
    }

    @RequestMapping(value = "/uploadItemExcel", consumes = "multipart/form-data", method = RequestMethod.POST)
    public @ResponseBody
    String uploadItemExcel(HttpServletRequest request) {
        return serviceBasicUtilityController.uploadExcelWrapper(
                new ServiceBasicUtilityController.ExcelUploadRequest(request,
                        this.purchaseRequestMaterialItemExcelHelper, AOID_RESOURCE, ISystemActionCode.ACID_EDIT,
                        (serviceExcelReportResponseModel, baseUUID) -> {
                            try {
                                purchaseRequestMaterialItemExcelHelper.updateItemExcel(serviceExcelReportResponseModel,
                                        baseUUID, (docMatItemUIModel, docMatItem, serialLogonInfo) -> {
                                            purchaseRequestMaterialItemManager.convUIToPurchaseRequestMaterialItem(
                                                    (PurchaseRequestMaterialItemUIModel) docMatItemUIModel,
                                                    (PurchaseRequestMaterialItem) docMatItem);
                                        }, false, logonActionController.getSerialLogonInfo());
                            } catch (ServiceEntityConfigureException e) {
                                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                                throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR,
                                        e.getMessage());
                            }
                        }));
    }

    public @RequestMapping(value = "/loadProperTargetDocListBatchGen", produces = "text/html;" + "charset=UTF-8")
    @ResponseBody
    String loadProperTargetDocListBatchGen(@RequestBody String request) {
        return serviceBasicUtilityController.loadProperTargetDocListBatchGen(request,
                IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST, DeliveryMatItemBatchGenRequest.class,
                AOID_RESOURCE);
    }

    public @RequestMapping(value = "/generateNextDocBatch", produces = "text/html;" + "charset=UTF-8")
    @ResponseBody
    String generateNextDocBatch(@RequestBody String request) {
        return serviceBasicUtilityController.genDefNextDocBatchWrapper(request,
                IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
                null);
    }

    public @RequestMapping(value = "/genDefNextDocBatchFromPrevProf", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String genDefNextDocBatchFromPrevProf(
            @RequestBody String request) {
        return serviceBasicUtilityController.genDefNextDocBatchFromPrevProf(request,
                IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
                null);
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
        return super.checkDuplicateIDCore(simpleRequest, purchaseRequestManager);
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

}
