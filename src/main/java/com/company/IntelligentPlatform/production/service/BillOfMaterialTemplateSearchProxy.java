package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateItemSearchModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateItemUIModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateSearchModel;
import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplateItem;
import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplate;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BillOfMaterialTemplateSearchProxy extends ServiceSearchProxy {

    @Autowired
    protected BillOfMaterialTemplateManager billOfMaterialTemplateManager;

    @Autowired
    protected BillOfMaterialTemplateItemServiceUIModelExtension billOfMaterialTemplateItemServiceUIModelExtension;

    @Override
    public Class<?> getDocSearchModelCls() {
        return BillOfMaterialTemplateSearchModel.class;
    }

    @Override
    public Class<?> getMatItemSearchModelCls() {
        return BillOfMaterialTemplateItemSearchModel.class;
    }

    @Override
    public String getAuthorizationResource() {
        return IServiceModelConstants.BillOfMaterialTemplate;
    }

    @Override
    public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return billOfMaterialTemplateManager.initStatusMap(languageCode);
    }

    @Override
    public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // Search node:[billOfMaterialTemplateItem]
        BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
        searchNodeConfig0.setSeName(BillOfMaterialTemplateItem.SENAME);
        searchNodeConfig0.setNodeName(BillOfMaterialTemplateItem.NODENAME);
        searchNodeConfig0.setNodeInstID(BillOfMaterialTemplateItem.NODENAME);
        searchNodeConfig0.setStartNodeFlag(true);
        searchNodeConfigList.add(searchNodeConfig0);
        // Search node:[materialStockKeepUnit]
        BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
        searchNodeConfig2.setSeName(MaterialStockKeepUnit.SENAME);
        searchNodeConfig2.setNodeName(MaterialStockKeepUnit.NODENAME);
        searchNodeConfig2.setNodeInstID(MaterialStockKeepUnit.SENAME);
        searchNodeConfig2.setStartNodeFlag(false);
        searchNodeConfig2
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig2.setMapBaseFieldName("refMaterialSKUUUID");
        searchNodeConfig2.setMapSourceFieldName("uuid");
        searchNodeConfig2.setBaseNodeInstID(BillOfMaterialTemplateItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig2);
        // Search node:[sub billOfMaterialTemplateItem]
        BSearchNodeComConfigure searchNodeConfig3 = new BSearchNodeComConfigure();
        searchNodeConfig3.setSeName(BillOfMaterialTemplate.SENAME);
        searchNodeConfig3.setNodeName(BillOfMaterialTemplate.NODENAME);
        searchNodeConfig3.setNodeInstID(BillOfMaterialTemplate.SENAME);
        searchNodeConfig3.setStartNodeFlag(false);
        searchNodeConfig3
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD);
        searchNodeConfig3.setBaseNodeInstID(BillOfMaterialTemplateItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig3);
        return searchNodeConfigList;
    }

    public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // Search node:[billOfMaterialTemplate]
        BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
        searchNodeConfig0.setSeName(BillOfMaterialTemplate.SENAME);
        searchNodeConfig0.setNodeName(BillOfMaterialTemplate.NODENAME);
        searchNodeConfig0.setNodeInstID(BillOfMaterialTemplate.SENAME);
        searchNodeConfig0.setStartNodeFlag(true);
        searchNodeConfigList.add(searchNodeConfig0);
        // Search node:[materialStockKeepUnit]
        BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
        searchNodeConfig2.setSeName(MaterialStockKeepUnit.SENAME);
        searchNodeConfig2.setNodeName(MaterialStockKeepUnit.NODENAME);
        searchNodeConfig2.setNodeInstID(MaterialStockKeepUnit.SENAME);
        searchNodeConfig2.setStartNodeFlag(false);
        searchNodeConfig2
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig2.setMapBaseFieldName("refMaterialSKUUUID");
        searchNodeConfig2.setMapSourceFieldName("uuid");
        searchNodeConfig2.setBaseNodeInstID(BillOfMaterialTemplate.SENAME);
        searchNodeConfigList.add(searchNodeConfig2);
        // Search node:[sub billOfMaterialTemplateItem]
        BSearchNodeComConfigure searchNodeConfig3 = new BSearchNodeComConfigure();
        searchNodeConfig3.setSeName(BillOfMaterialTemplateItem.SENAME);
        searchNodeConfig3.setNodeName(BillOfMaterialTemplateItem.NODENAME);
        searchNodeConfig3.setNodeInstID(BillOfMaterialTemplateItem.NODENAME);
        searchNodeConfig3.setStartNodeFlag(false);
        searchNodeConfig3
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_ROOT);
        searchNodeConfig3.setBaseNodeInstID(BillOfMaterialTemplate.SENAME);
        searchNodeConfigList.add(searchNodeConfig3);
        // Search node:[sub item material]
        BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
        searchNodeConfig4.setSeName(MaterialStockKeepUnit.SENAME);
        searchNodeConfig4.setNodeName(MaterialStockKeepUnit.NODENAME);
        searchNodeConfig4
                .setNodeInstID(BillOfMaterialTemplateSearchModel.NODEID_SUBITEM_MATERIAL);
        searchNodeConfig4.setStartNodeFlag(false);
        searchNodeConfig4
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig4.setMapBaseFieldName("refMaterialSKUUUID");
        searchNodeConfig4.setMapSourceFieldName("uuid");
        searchNodeConfig4.setBaseNodeInstID(BillOfMaterialTemplateItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig4);

        // Search node:[ProdWorkCenter]
        BSearchNodeComConfigure searchNodeConfig5 = new BSearchNodeComConfigure();
        searchNodeConfig5.setSeName(ProdWorkCenter.SENAME);
        searchNodeConfig5.setNodeName(ProdWorkCenter.NODENAME);
        searchNodeConfig5.setNodeInstID(ProdWorkCenter.SENAME);
        searchNodeConfig5.setStartNodeFlag(false);
        searchNodeConfig5
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig5.setMapBaseFieldName("refWocUUID");
        searchNodeConfig5.setMapSourceFieldName("uuid");
        searchNodeConfig5.setBaseNodeInstID(BillOfMaterialTemplate.SENAME);
        searchNodeConfigList.add(searchNodeConfig5);

        return searchNodeConfigList;
    }

}
