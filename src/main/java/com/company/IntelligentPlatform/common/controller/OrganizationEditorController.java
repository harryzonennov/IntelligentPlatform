package com.company.IntelligentPlatform.common.controller;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.service.OrganizationServiceModel;
import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.dto.*;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;

@Scope("session")
@Controller(value = "organizationEditorController")
@RequestMapping(value = "/organization")
public class OrganizationEditorController extends SEEditorController {

    @Autowired
    protected OrganizationManager organizationManager;

    @Autowired
    protected LogonUserManager logonUserManager;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected OrganizationServiceUIModelExtension organizationServiceUIModelExtension;

    public static final String AOID_RESOURCE = IServiceModelConstants.Organization;

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                OrganizationServiceUIModel.class,
                OrganizationServiceModel.class, AOID_RESOURCE,
                Organization.NODENAME,
                Organization.SENAME, organizationServiceUIModelExtension,
                organizationManager
        );
    }

    private OrganizationServiceUIModel parseToServiceUIModel(String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("organizationAttachmentUIModelList",
                OrganizationAttachmentUIModel.class);
        classMap.put("logonUserOrganizationUIModelList", LogonUserOrganizationUIModel.class);
        return (OrganizationServiceUIModel) JSONObject
                .toBean(jsonObject, OrganizationServiceUIModel.class, classMap);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        OrganizationServiceUIModel organizationServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                organizationServiceUIModel, null, null, getServiceUIModuleExecutor(),
                organizationServiceUIModel.getOrganizationUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/newModuleService")
    public @ResponseBody String newModuleService() {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(
                        Organization.SENAME, Organization.NODENAME,
                        null), ISystemActionCode.ACID_EDIT);
    }

    //TODO check this weird logic
    @RequestMapping(value = "/getOrganizationFunctionMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getOrganizationFunctionMap() {
        return serviceBasicUtilityController.getListMeta(() -> organizationManager
                .initOrganizationFunctionMap( logonActionController.getClient(), true), AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
    }

    @RequestMapping(value = "/newModuleFromParentService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleFromParentService(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                ServiceBasicUtilityController.InitServiceEntityRequestBuilder.getBuilder(Organization.SENAME, Organization.NODENAME)
                        .processServiceEntityNode(organization -> organizationManager.newOrganizationFromParent(request.getBaseUUID(),
                                (Organization) organization)).build(), ISystemActionCode.ACID_EDIT);
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String preLockService(
            @RequestBody SimpleSEJSONRequest request) {
        return preLock(request.getUuid());
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
    public @ResponseBody String preLock(String uuid) {
        return serviceBasicUtilityController.preLock(uuid, ISystemActionCode.ACID_EDIT, getServiceUIModelRequest());
    }

    @RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModule(String uuid) {
        return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
                getServiceUIModelRequest());
    }


    ServiceBasicUtilityController.IServiceUIModuleExecutor<OrganizationServiceUIModel> getServiceUIModuleExecutor() {
        return (organizationServiceUIModel, serviceModule) -> {
            organizationManager.postLoadServiceUIModel(organizationServiceUIModel, logonActionController.getLogonInfo());
            return organizationServiceUIModel;
        };
    }

    @RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleEditService(String uuid) {
        return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true, getServiceUIModuleExecutor(),
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleViewService(String uuid) {
        return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,getServiceUIModuleExecutor(),
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/exitEditor")
    public String exitEditor(
            @RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

    @RequestMapping(value = "/getOrganType", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getOrganType() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> organizationManager.initOrganTypeMap(lanCode));
    }

    @RequestMapping(value = "/getOrganLevel", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getOrganLevel() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> organizationManager.initOrganLevelMap(lanCode));
    }

    @RequestMapping(value = "/getRegularType", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getRegularType() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> organizationManager.initRegularTypeMap(lanCode));
    }

    @RequestMapping(value = "/getAccountType", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getAccountType() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> organizationManager.initAccountTypeMap(lanCode));
    }

    private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
        return new DocAttachmentProxy.DocAttachmentProcessPara(organizationManager,
                OrganizationAttachment.NODENAME, Material.NODENAME, null, null, null);
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

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody String checkDuplicateID(
            @RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest, organizationManager);
    }

    @RequestMapping(value = "/chooseParentOrganization", produces = "text/html;charset=UTF-8")
    public @ResponseBody String chooseParentOrganization(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getObjectMeta(
                () -> {
                    Organization organization = (Organization) organizationManager
                            .getEntityNodeByUUID(request.getBaseUUID(),
                                    Organization.NODENAME, logonActionController.getClient());
                    Organization parentOrganization = (Organization) organizationManager
                            .getEntityNodeByKey(request.getUuid(),
                                    IServiceEntityNodeFieldConstant.UUID,
                                    Organization.NODENAME, null);
                    if (parentOrganization == null) {
                        return ServiceJSONParser
                                .generateSimpleErrorJSON("No Org found by UUID:["
                                        + request.getUuid() + "]");
                    }
                    // change the organization SE name to common for public interface
                    organization
                            .setParentOrganizationUUID(parentOrganization.getUuid());
                    return parentOrganization;
                }, AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/chooseFinOrgToOrganization", produces = "text/html;charset=UTF-8")
    public @ResponseBody String chooseFinOrgToOrganization(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getObjectMeta(
                () -> {
                    Organization organization = (Organization) organizationManager
                            .getEntityNodeByUUID(request.getBaseUUID(),
                                    Organization.NODENAME, logonActionController.getClient());
                    Organization refFinOrganization = (Organization) organizationManager
                            .getEntityNodeByKey(request.getUuid(),
                                    IServiceEntityNodeFieldConstant.UUID,
                                    Organization.NODENAME, null);
                    if (refFinOrganization == null) {
                        return ServiceJSONParser
                                .generateSimpleErrorJSON("No Org found by UUID:["
                                        + request.getUuid() + "]");
                    }
                    // change the organization SE name to common for public interface
                    organization.setRefFinOrgUUID(request.getUuid());
                    refFinOrganization.setServiceEntityName(Organization.SENAME);
                    return organization;
                }, AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
    }

}
