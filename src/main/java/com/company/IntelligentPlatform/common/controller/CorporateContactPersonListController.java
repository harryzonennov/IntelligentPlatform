package com.company.IntelligentPlatform.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.dto.CorporateContactPersonSearchModel;
import com.company.IntelligentPlatform.common.dto.CorporateContactPersonServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.CorporateContactPersonUIModel;
import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;

@Scope("session")
@Controller(value = "corporateContactPersonListController")
@RequestMapping(value = "/corporateContactPerson")
public class CorporateContactPersonListController extends SEListController {

    @Autowired
    protected CorporateCustomerManager corporateCustomerManager;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected CorporateContactPersonServiceUIModelExtension corporateContactPersonServiceUIModelExtension;

    public static final String AOID_RESOURCE = CorporateCustomerEditorController.AOID_RESOURCE;

    protected List<CorporateContactPersonUIModel> getModuleListCore(
            List<ServiceEntityNode> rawList)
            throws ServiceEntityInstallationException,
            ServiceEntityConfigureException, ServiceModuleProxyException {
        return serviceBasicUtilityController.convUIModuleList(CorporateContactPersonUIModel.class, rawList,
                corporateCustomerManager, corporateContactPersonServiceUIModelExtension);
    }

    /**
     * Used for CorporateCustomer Area Selection
     * @param request
     * @return
     **/
    @RequestMapping(value = "/searchLeanModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String searchLeanModuleService(@RequestBody String request) {
        return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
                CorporateContactPersonSearchModel.class, searchContext -> corporateCustomerManager
                        .getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
    }

}