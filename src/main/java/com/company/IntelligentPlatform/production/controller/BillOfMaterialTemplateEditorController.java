package com.company.IntelligentPlatform.production.controller;

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
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Controller(value = "billOfMaterialTemplateEditorController")
@RequestMapping(value = "/billOfMaterialTemplate")
public class BillOfMaterialTemplateEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.BillOfMaterialTemplate;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected BillOfMaterialTemplateManager billOfMaterialTemplateManager;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected BillOfMaterialTemplateExecutionProxy billOfMaterialTemplateExecutionProxy;

    @Autowired
    protected BillOfMaterialTemplateServiceUIModelExtension billOfMaterialTemplateServiceUIModelExtension;

    @Autowired
    private BillOfMaterialTemplateSpecifier billOfMaterialTemplateSpecifier;

    protected Logger logger = LoggerFactory.getLogger(BillOfMaterialTemplateEditorController.class);

    public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
        return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
                BillOfMaterialTemplateServiceUIModel.class,
                BillOfMaterialTemplateServiceModel.class, AOID_RESOURCE,
                BillOfMaterialTemplate.NODENAME, BillOfMaterialTemplate.SENAME, billOfMaterialTemplateServiceUIModelExtension,
                billOfMaterialOrderManager, BillOfMaterialTemplateActionNode.NODENAME, billOfMaterialTemplateExecutionProxy
        );
    }

    @RequestMapping(value = "/getLeadTimeCalModeMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getLeadTimeCalModeMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> billOfMaterialTemplateManager.initLeadTimeCalModeMap(lanCode));
    }

    @RequestMapping(value = "/getItemViewTypeMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getItemViewTypeMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> billOfMaterialTemplateManager.initItemViewTypeMap());
    }

    @RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDocActionNodeList(String uuid, String actionCode) {
        return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
    }

    @RequestMapping(value = "/getStatusMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getStatusMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> billOfMaterialTemplateManager.initStatusMap(lanCode));
    }

    protected BillOfMaterialTemplateServiceUIModel parseToServiceUIModel(
            @RequestBody String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("billOfMaterialTemplateItemUIModelList",
                BillOfMaterialTemplateItemServiceUIModel.class);
        classMap.put("billOfMaterialTemplateItemUpdateLogList",
                BillOfMaterialTemplateItemUpdateLogUIModel.class);
        classMap.put("billOfMaterialTemplateAttachmentUIModelList", BillOfMaterialTemplateAttachmentUIModel.class);
        return (BillOfMaterialTemplateServiceUIModel) JSONObject
        .toBean(jsonObject, BillOfMaterialTemplateServiceUIModel.class,
                classMap);
    }

    private SimpleSEJSONRequest parseSimpleSEJSONRequest(String request){
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("billOfMaterialTemplateAttachmentUIModelList", BillOfMaterialTemplateAttachmentUIModel.class);
        return (SimpleSEJSONRequest) JSONObject
                .toBean(jsonObject, SimpleSEJSONRequest.class,
                        classMap);
    }


    private BillOfMaterialTemplateServiceModel parseToServiceModel(String request)
            throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
        BillOfMaterialTemplateServiceUIModel billOfMaterialTemplateServiceUIModel = this.parseToServiceUIModel(request);
        return (BillOfMaterialTemplateServiceModel) billOfMaterialTemplateManager
                .genServiceModuleFromServiceUIModel(
                        BillOfMaterialTemplateServiceModel.class,
                        BillOfMaterialTemplateServiceUIModel.class,
                        billOfMaterialTemplateServiceUIModel,
                        billOfMaterialTemplateServiceUIModelExtension);
    }

    @RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getActionCodeMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> billOfMaterialTemplateExecutionProxy.getActionCodeMap(lanCode));
    }

    @RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionConfigureList() {
        return serviceBasicUtilityController.getDocActionConfigureListCore(this.billOfMaterialTemplateExecutionProxy);
    }

    @RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
    public @ResponseBody String executeDocAction(@RequestBody String request) {
        return this.executeActionCore(request,
                (billOfMaterialTemplateServiceModel, actionCode, logonInfo) -> {
                    try {
                        billOfMaterialTemplateExecutionProxy.executeActionCore(billOfMaterialTemplateServiceModel,
                                actionCode, logonActionController.getSerialLogonInfo());
                    } catch (DocActionException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                });
    }


    String executeActionCore(@RequestBody String request,
                             DocActionNodeProxy.IActionExecutor<BillOfMaterialTemplateServiceModel> iActionExecutor) {
        return serviceBasicUtilityController.defaultActionServiceWrapper(request, AOID_RESOURCE,
                billOfMaterialTemplateExecutionProxy,  BillOfMaterialTemplate.SENAME,
                billOfMaterialTemplateManager,
                new DocActionNodeProxy.IActionCodeServiceExecutor<BillOfMaterialTemplateServiceModel, BillOfMaterialTemplateServiceUIModel>() {
                    @Override
                    public BillOfMaterialTemplateServiceModel parseToServiceModule(String request) throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
                        return parseToServiceModel(request);
                    }

                    @Override
                    public boolean preExecute(BillOfMaterialTemplateServiceModel serviceModule, int actionCode){
                        return true;
                    }

                    @Override
                    public void executeService(BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel, int actionCode,
                                               LogonInfo logonInfo) throws DocActionException,
                            ServiceEntityConfigureException, ServiceModuleProxyException {
                        if(iActionExecutor != null){
                            iActionExecutor.executeService(billOfMaterialTemplateServiceModel, actionCode, logonInfo);
                        }
                    }

                    @Override
                    public BillOfMaterialTemplateServiceUIModel refreshServiceUIModel(BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel
                            , String acId, LogonInfo logonInfo) throws ServiceEntityConfigureException,
                            LogonInfoException, ServiceModuleProxyException, ServiceUIModuleProxyException, AuthorizationException {
                        BillOfMaterialTemplate billOfMaterialTemplate = billOfMaterialTemplateServiceModel.getBillOfMaterialTemplate();
                        return refreshLoadServiceUIModel(billOfMaterialTemplate, logonInfo.getClient());
                    }

                    @Override
                    public void postHandle(BillOfMaterialTemplateServiceUIModel billOfMaterialTemplateServiceUIModel,
                                           int actionCode,
                                           SerialLogonInfo logonInfo) throws DocActionException {

                    }

                }, billOfMaterialTemplateServiceUIModelExtension);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String saveModuleService(@RequestBody String request) {
        return this.executeActionCore(request,
                null);
    }

    private BillOfMaterialTemplate loadDataByCheckAccess(String uuid, boolean lockFlag, String acId) throws AuthorizationException,
            ServiceEntityConfigureException, LogonInfoException {
        return (BillOfMaterialTemplate) serviceBasicUtilityController.loadDataByCheckAccess(uuid,
                billOfMaterialTemplateManager, BillOfMaterialTemplate.NODENAME, AOID_RESOURCE, acId, lockFlag,
                logonActionController.getLogonInfo().getAuthorizationACUnionList());
    }

    @RequestMapping(value = "/revertToBOMOrder", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String revertToBOMOrder(@RequestBody String request) {
        try {
            SimpleSEJSONRequest simpleSEJSONRequest = parseSimpleSEJSONRequest(request);
            SerialLogonInfo serialLogonInfo = logonActionController.getSerialLogonInfo();
            BillOfMaterialOrder billOfMaterialOrder =
                    (BillOfMaterialOrder) billOfMaterialOrderManager.getEntityNodeByKey(simpleSEJSONRequest.getUuid(),
                            IServiceEntityNodeFieldConstant.UUID,
                            BillOfMaterialOrder.NODENAME, serialLogonInfo.getClient(), null);
            if (billOfMaterialOrder == null) {
                throw new BillOfMaterialException(BillOfMaterialException.PARA_NO_BOMOrder, simpleSEJSONRequest.getUuid());
            }
            BillOfMaterialTemplate billOfMaterialTemplate =
                    loadDataByCheckAccess(billOfMaterialOrder.getRefTemplateUUID(), false, ISystemActionCode.ACID_EDIT);
            billOfMaterialTemplateManager.updateBuffer(billOfMaterialTemplate);
            BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel =
                    (BillOfMaterialTemplateServiceModel) billOfMaterialTemplateManager
                    .loadServiceModule(BillOfMaterialTemplateServiceModel.class,
                            billOfMaterialTemplate);
            billOfMaterialTemplateManager.revertToBOMOrder(billOfMaterialTemplateServiceModel, billOfMaterialOrder,
                    serialLogonInfo);
            BillOfMaterialTemplateServiceUIModel billOfMaterialTemplateServiceUIModel =
                    refreshLoadServiceUIModel(billOfMaterialTemplate, serialLogonInfo.getClient());
            return ServiceJSONParser.genDefOKJSONObject(billOfMaterialTemplateServiceUIModel);
        } catch (BillOfMaterialException | ServiceModuleProxyException | ServiceUIModuleProxyException | LogonInfoException | AuthorizationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getMessage());
        }
    }

    @RequestMapping(value = "/revertToRecentBOMOrder", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String revertToRecentBOMOrder(@RequestBody String request) {
        try {
            SimpleSEJSONRequest simpleSEJSONRequest = parseSimpleSEJSONRequest(request);
            SerialLogonInfo serialLogonInfo = logonActionController.getSerialLogonInfo();

            BillOfMaterialTemplate billOfMaterialTemplate =
                    loadDataByCheckAccess(simpleSEJSONRequest.getUuid(), false, ISystemActionCode.ACID_EDIT);
            billOfMaterialTemplateManager.updateBuffer(billOfMaterialTemplate);
            BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel =
                    (BillOfMaterialTemplateServiceModel) billOfMaterialTemplateManager
                            .loadServiceModule(BillOfMaterialTemplateServiceModel.class,
                                    billOfMaterialTemplate);
            billOfMaterialTemplateManager.revertToRecentBOMOrder(billOfMaterialTemplateServiceModel, serialLogonInfo);
            BillOfMaterialTemplateServiceUIModel billOfMaterialTemplateServiceUIModel =
                    refreshLoadServiceUIModel(billOfMaterialTemplate, serialLogonInfo.getClient());
            return ServiceJSONParser.genDefOKJSONObject(billOfMaterialTemplateServiceUIModel);
        } catch (BillOfMaterialException | ServiceModuleProxyException | ServiceUIModuleProxyException | LogonInfoException | AuthorizationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getMessage());
        }
    }

    protected BillOfMaterialTemplateServiceUIModel refreshLoadServiceUIModel(
            BillOfMaterialTemplate billOfMaterialTemplate, String client) throws ServiceEntityConfigureException,
            ServiceModuleProxyException, ServiceUIModuleProxyException {
        // Refresh service model
        billOfMaterialTemplateManager.updateBuffer(billOfMaterialTemplate);
        BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel =
                (BillOfMaterialTemplateServiceModel) billOfMaterialTemplateManager
                        .loadServiceModule(BillOfMaterialTemplateServiceModel.class,
                                billOfMaterialTemplate);
        BillOfMaterialTemplateServiceUIModel billOfMaterialTemplateServiceUIModel =
                (BillOfMaterialTemplateServiceUIModel) billOfMaterialTemplateManager
                        .genServiceUIModuleFromServiceModel(
                                BillOfMaterialTemplateServiceUIModel.class,
                                BillOfMaterialTemplateServiceModel.class,
                                billOfMaterialTemplateServiceModel,
                                billOfMaterialTemplateServiceUIModelExtension, logonActionController.getLogonInfo());
        return billOfMaterialTemplateServiceUIModel;
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService() {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(
                        BillOfMaterialTemplate.SENAME, BillOfMaterialTemplate.NODENAME,
                        billOfMaterialTemplateSpecifier), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkDuplicateID(
            @RequestBody SimpleSEJSONRequest simpleRequest) {
        simpleRequest.setClient(logonActionController.getClient());
        return super.checkDuplicateIDCore(simpleRequest,
                billOfMaterialTemplateManager);
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String preLockService(
            @RequestBody SimpleSEJSONRequest request) {
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
    public @ResponseBody
    String exitEditor(
            @RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

    private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
        return new DocAttachmentProxy.DocAttachmentProcessPara(billOfMaterialTemplateManager,
                BillOfMaterialTemplateAttachment.NODENAME, BillOfMaterialTemplate.NODENAME, null, null,
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
