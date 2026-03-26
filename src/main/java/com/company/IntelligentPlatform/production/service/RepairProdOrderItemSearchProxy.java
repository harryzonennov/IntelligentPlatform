package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.ProdOrderItemReqProposalSearchModel;
import com.company.IntelligentPlatform.production.dto.ProdOrderItemReqProposalServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProdOrderItemReqProposalUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionOrderItemSearchModel;
import com.company.IntelligentPlatform.production.model.ProdOrderItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.production.model.ProductionOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RepairProdOrderItemSearchProxy extends ServiceSearchProxy {

    @Autowired
    protected ProductionOrderManager productionOrderManager;

    @Autowired
    protected SearchDocConfigHelper searchDocConfigHelper;

    @Autowired
    protected ProdOrderItemReqProposalServiceUIModelExtension prodOrderItemReqProposalServiceUIModelExtension;

    @Override
    public Class<?> getDocSearchModelCls() {
        return ProductionOrderItemSearchModel.class;
    }

    @Override
    public Class<?> getMatItemSearchModelCls() {
        return ProdOrderItemReqProposalSearchModel.class;
    }

    @Override
    public String getAuthorizationResource() {
        return productionOrderManager.getAuthorizationResource();
    }

    @Override
    public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return productionOrderManager.initItemStatusMap(languageCode);
    }

    @Override
    public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
        // Search node Array :[ProductionOrderItem->Material]
        List<BSearchNodeComConfigure> productionOrderItemSearchConfigureList =
                searchDocConfigHelper.genDocMatItemSearchNodeConfigureList(new SearchDocConfigHelper.SearchDocMatItemConfigureUnion(
                        null,
                        ProductionOrderItem.SENAME,
                        ProductionOrderItem.NODENAME,
                        ProductionOrderItem.NODENAME
                ));
        List<BSearchNodeComConfigure> searchNodeConfigList =
                new ArrayList<BSearchNodeComConfigure>(productionOrderItemSearchConfigureList);
        // Search node:[ProductionOrder]
        BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
        searchNodeConfig1.setSeName(ProductionOrder.SENAME);
        searchNodeConfig1.setNodeName(ProductionOrder.NODENAME);
        searchNodeConfig1.setNodeInstID(ProductionOrder.SENAME);
        searchNodeConfig1.setStartNodeFlag(false);
        searchNodeConfig1
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD);
        searchNodeConfig1.setBaseNodeInstID(ProductionOrderItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig1);
        return searchNodeConfigList;
    }

    @Override
    public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
        // Search node Array :[ProductionOrderItem->Material]
        List<BSearchNodeComConfigure> prodOrderItemReqSearchConfigureList =
                searchDocConfigHelper.genDocMatItemSearchNodeConfigureList(new SearchDocConfigHelper.SearchDocMatItemConfigureUnion(
                        null,
                        ProdOrderItemReqProposal.SENAME,
                        ProdOrderItemReqProposal.NODENAME,
                        ProdOrderItemReqProposal.NODENAME
                ));
        List<BSearchNodeComConfigure> searchNodeConfigList =
                new ArrayList<BSearchNodeComConfigure>(prodOrderItemReqSearchConfigureList);
        // Search node:[ProductionOrder]
        BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
        searchNodeConfig1.setSeName(ProductionOrder.SENAME);
        searchNodeConfig1.setNodeName(ProductionOrder.NODENAME);
        searchNodeConfig1.setNodeInstID(ProductionOrder.SENAME);
        searchNodeConfig1.setStartNodeFlag(false);
        searchNodeConfig1
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_FROM_ROOT);
        searchNodeConfig1.setBaseNodeInstID(ProdOrderItemReqProposal.NODENAME);
        searchNodeConfigList.add(searchNodeConfig1);
        return searchNodeConfigList;
    }
}
