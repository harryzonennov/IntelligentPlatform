package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;

@Service
public class PurchaseContractSearchProxy extends ServiceSearchProxy {

    @Autowired
    protected PurchaseContractManager purchaseContractManager;

    @Autowired
    protected SearchDocConfigHelper searchDocConfigHelper;

    @Override
    public Class<?> getDocSearchModelCls() {
        return PurchaseContractSearchModel.class;
    }

    @Override
    public Class<?> getMatItemSearchModelCls() {
        return PurchaseContractMaterialItemSearchModel.class;
    }

    @Override
    public String getAuthorizationResource() {
        return purchaseContractManager.getAuthorizationResource();
    }

    @Override
    public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> getDocSearchConfigureListTemplate() throws SearchConfigureException {
        List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList = new ArrayList<>();
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(PurchaseContract.class).build());
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(PurchaseContractMaterialItem.class).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
        addDefaultDocFlowConfigureList(searchConfigureTemplateNodeList);
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(PurchaseContractActionNode.class).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(PurchaseContractActionNode.NODEINST_ACTION_SUBMIT)
                .nodeInstCode(PurchaseContractActionNode.DOC_ACTION_SUBMIT).build());
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(PurchaseContractActionNode.class).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(PurchaseContractActionNode.NODEINST_ACTION_APPROVE)
                .nodeInstCode(PurchaseContractActionNode.DOC_ACTION_APPROVE).build());
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(PurchaseContractActionNode.class).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(PurchaseContractActionNode.NODEINST_ACTION_DELIVERY_DONE)
                .nodeInstCode(PurchaseContractActionNode.DOC_ACTION_DELIVERY_DONE).build());
        searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
                targetContactClass(IndividualCustomer.class)
                .nodeClass(PurchaseContractParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
                .nodeInstId(PurchaseContractParty.PARTY_NODEINST_PUR_SUPPLIER).nodeInstCode(PurchaseContractParty.ROLE_PARTYB).build());
        searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
                targetContactClass(Employee.class)
                .nodeClass(PurchaseContractParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
                .nodeInstId(PurchaseContractParty.PARTY_NODEINST_PUR_ORG).nodeInstCode(PurchaseContractParty.ROLE_PARTYA).build());
        return searchConfigureTemplateNodeList;
    }

}
