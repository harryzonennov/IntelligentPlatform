package com.company.IntelligentPlatform.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.dto.*;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;

@Scope("session")
@Controller(value = "logonUserOrgListController")
@RequestMapping(value = "/logonUserOrg")
public class LogonUserOrgListController extends SEListController {

    @Autowired
    protected OrganizationManager organizationManager;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected LogonUserOrgServiceUIModelExtension logonUserOrgServiceUIModelExtension;

    @Autowired
    protected LogonUserManager logonUserManager;

    public static final String AOID_RESOURCE = OrganizationEditorController.AOID_RESOURCE;


    protected List<LogonUserOrganizationUIModel> getModuleListCore(
            List<ServiceEntityNode> rawList)
            throws ServiceEntityInstallationException,
            ServiceEntityConfigureException, ServiceModuleProxyException {
        return serviceBasicUtilityController.convUIModuleList(LogonUserOrganizationUIModel.class, rawList,
                logonUserManager, logonUserOrgServiceUIModelExtension);
    }

    /**
     * Used for Organization Area Selection
     * @param request
     * @return
     **/
    @RequestMapping(value = "/searchLeanModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String searchLeanModuleService(@RequestBody String request) {
        return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
                LogonUserOrgSearchModel.class, searchContext -> organizationManager
                        .getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
    }

}