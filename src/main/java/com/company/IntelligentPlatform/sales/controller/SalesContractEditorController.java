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
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Scope("session")
@Controller(value = "salesContractEditorController")
@RequestMapping(value = "/salesContract")
public class SalesContractEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected SalesContractManager salesContractManager;

    @Autowired
    protected SalesContractMaterialItemExcelHelper salesContractMaterialItemExcelHelper;

    @Autowired
    protected SalesContractMaterialItemManager salesContractMaterialItemManager;

    @Autowired
    protected DocAttachmentProxy docAttachmentProxy;

    @Autowired
    protected SalesContractSpecifier salesContractSpecifier;

    @Autowired
    protected SalesContractActionExecutionProxy salesContractActionExecutionProxy;

    protected Logger logger = LoggerFactory.getLogger(SalesContractEditorController.class);

    @RequestMapping(value = "/getStatusMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getStatusMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> salesContractManager.initStatus(lanCode));
    }

    @RequestMapping(value = "/getPriorityMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPriorityMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> salesContractManager.initPriorityCode(lanCode));
    }

    @RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDocActionNodeList(String uuid, String actionCode) {
        return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
    }

    public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
        return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
                SalesContractServiceUIModel.class,
                SalesContractServiceModel.class, AOID_RESOURCE,
                SalesContract.NODENAME, SalesContract.SENAME,
                salesContractManager, SalesContractActionNode.NODENAME, salesContractActionExecutionProxy
        );
    }

    public @RequestMapping(value = "/genDefNextDocBatchFromPrevProf", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String genDefNextDocBatchFromPrevProf(
            @RequestBody String request) {
        return serviceBasicUtilityController.genDefNextDocBatchFromPrevProf(request,
                IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
                null);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String saveModuleService(@RequestBody String request) {
        return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionConfigureList() {
        return serviceBasicUtilityController.getDocActionConfigureListCore(this.salesContractActionExecutionProxy);
    }

    @RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
    public @ResponseBody String executeDocAction(@RequestBody String request) {
        return serviceBasicUtilityController.executeDocActionFramework(request,
                (DocActionNodeProxy.IActionExecutor<SalesContractServiceModel>) (salesContractServiceModel, actionCode, logonInfo) -> {
                    try {
                        salesContractActionExecutionProxy.executeActionCore(salesContractServiceModel,
                                actionCode, logonActionController.getSerialLogonInfo());
                    } catch (DocActionException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                }, getDocUIModelRequest());
    }

    @RequestMapping(value = "/uploadItemExcel", consumes = "multipart/form-data", method = RequestMethod.POST)
    public @ResponseBody String uploadItemExcel(
            HttpServletRequest request) {
        return serviceBasicUtilityController.uploadExcelWrapper(new ServiceBasicUtilityController.ExcelUploadRequest(request,
                this.salesContractMaterialItemExcelHelper, AOID_RESOURCE, ISystemActionCode.ACID_EDIT,
                (serviceExcelReportResponseModel, baseUUID) -> {
                    try {
                        salesContractMaterialItemExcelHelper.updateItemExcel(serviceExcelReportResponseModel, baseUUID,
                                (docMatItemUIModel, docMatItem, serialLogonInfo) -> {
                                    salesContractMaterialItemManager.convUIToSalesContractMaterialItem((SalesContractMaterialItemUIModel) docMatItemUIModel,
                                            (SalesContractMaterialItem) docMatItem);
                                }, false, logonActionController.getSerialLogonInfo());
                    } catch (ServiceEntityConfigureException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                        throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, e.getMessage());
                    }
                }));
    }

    public @RequestMapping(value = "/loadProperTargetDocListBatchGen", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String loadProperTargetDocListBatchGen(
            @RequestBody String request) {
        return serviceBasicUtilityController.loadProperTargetDocListBatchGen(request,
                IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT, DeliveryMatItemBatchGenRequest.class, AOID_RESOURCE);
    }

    public @RequestMapping(value = "/loadProperTargetDocListBatchGenFromPrevDoc", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String loadProperTargetDocListBatchGenFromPrevDoc(
            @RequestBody String request) {
        return serviceBasicUtilityController.loadProperTargetDocListBatchGenFromPrevDoc(request,
                IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT, DeliveryMatItemBatchGenRequest.class, AOID_RESOURCE);
    }

    public @RequestMapping(value = "/loadProperTargetDocListBatchGenReserved", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String loadProperTargetDocListBatchGenReserved(
            @RequestBody String request) {
        return serviceBasicUtilityController.loadProperTargetDocListBatchGenReserved(request,
                IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT, DeliveryMatItemBatchGenRequest.class, AOID_RESOURCE);
    }

    public @RequestMapping(value = "/generateNextDocBatch", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String generateNextDocBatch(
            @RequestBody String request) {
        return serviceBasicUtilityController.genDefNextDocBatchWrapper(request,
                IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
                null);
    }

    public @RequestMapping(value = "/genDefNextDocBatchReserved", produces = "text/html;"
            + "charset=UTF-8") @ResponseBody String genDefNextDocBatchReserved(
            @RequestBody String request) {
        return serviceBasicUtilityController.genDefNextDocBatchReserved(request,
                IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
                null);
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String newModuleService() {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(SalesContract.SENAME, SalesContract.NODENAME, null,
                        null, salesContractSpecifier, null),
                ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkDuplicateID(
            @RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest, salesContractManager);
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

    @RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleViewService(String uuid) {
        return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
                getDocUIModelRequest());
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
        return new DocAttachmentProxy.DocAttachmentProcessPara(salesContractManager,
                SalesContractAttachment.NODENAME, SalesContract.NODENAME, null, null, null);
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