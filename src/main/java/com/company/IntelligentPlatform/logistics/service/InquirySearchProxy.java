package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class InquirySearchProxy extends ServiceSearchProxy {

    @Autowired
    protected SearchDocConfigHelper searchDocConfigHelper;

    @Autowired
    protected InquiryManager inquiryManager;

    @Autowired
    protected InquiryMaterialItemServiceUIModelExtension inquiryMaterialItemServiceUIModelExtension;

    @Override
    public Class<?> getDocSearchModelCls() {
        return InquirySearchModel.class;
    }

    @Override
    public Class<?> getMatItemSearchModelCls() {
        return InquiryMaterialItemSearchModel.class;
    }

    @Override
    public String getAuthorizationResource() {
        return inquiryManager.getAuthorizationResource();
    }

    @Override
    public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return inquiryManager.initStatus(languageCode);
    }

    @Override
    public List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> getDocSearchConfigureListTemplate() throws SearchConfigureException {
        List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList = new ArrayList<>();
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(Inquiry.class).build());
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InquiryMaterialItem.class).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InquiryActionNode.class).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(InquiryActionNode.NODEINST_ACTION_SUBMIT)
                .nodeInstCode(InquiryActionNode.DOC_ACTION_SUBMIT).build());
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InquiryActionNode.class).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(InquiryActionNode.NODEINST_ACTION_APPROVE)
                .nodeInstCode(InquiryActionNode.DOC_ACTION_APPROVE).build());
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InquiryActionNode.class).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(InquiryActionNode.NODEINST_ACTION_INPROCESS)
                .nodeInstCode(InquiryActionNode.DOC_ACTION_INPROCESS).build());
        searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
                targetContactClass(IndividualCustomer.class)
                .nodeClass(InquiryParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
                .nodeInstId(InquiryParty.PARTY_NODEINST_PUR_SUPPLIER).nodeInstCode(InquiryParty.ROLE_PARTYB).build());
        addDefaultDocFlowConfigureList(searchConfigureTemplateNodeList);
        searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
                targetContactClass(Employee.class)
                .nodeClass(InquiryParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
                .nodeInstId(InquiryParty.PARTY_NODEINST_PUR_ORG).nodeInstCode(InquiryParty.ROLE_PARTYA).build());
        return searchConfigureTemplateNodeList;
    }

}
