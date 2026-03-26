package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.dto.ProdPickingRefMaterialItemSearchModel;
import com.company.IntelligentPlatform.production.model.*;
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
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ProdPickingOrderSearchProxy extends ServiceSearchProxy {

    @Autowired
    protected ProdPickingOrderManager prodPickingOrderManager;

    @Autowired
    protected SearchDocConfigHelper searchDocConfigHelper;

    @Autowired
    protected ProdOrderItemReqProposalServiceUIModelExtension prodOrderItemReqProposalServiceUIModelExtension;

    @Override
    public Class<?> getDocSearchModelCls() {
        return ProdPickingOrderSearchModel.class;
    }

    @Override
    public Class<?> getMatItemSearchModelCls() {
        return ProdPickingRefMaterialItemSearchModel.class;
    }

    @Override
    public String getAuthorizationResource() {
        return prodPickingOrderManager.getAuthorizationResource();
    }

    @Override
    public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return prodPickingOrderManager.initItemStatusMap(languageCode);
    }

    public List<ServiceEntityNode> searchDocListCore(
            SEUIComModel searchModel, Map<String, List<?>> processTypeMap, String client)
            throws SearchConfigureException, ServiceEntityConfigureException,
             ServiceEntityInstallationException {
        //TODO to check this logic
        return null;
//        List<BSearchNodeComConfigure> searchNodeConfigList = this.getDocSearchNodeConfigList();
//        ProdPickingOrderSearchModel prodPickingOrderSearchModel = (ProdPickingOrderSearchModel) searchModel;
//        String[] fieldNameArray = SearchDocConfigHelper.genDefaultMaterialFieldNameArray();
//        Map<String, List<?>> multiValueMap = bsearchService
//                .generateMulitpleSearchMap(prodPickingOrderSearchModel, fieldNameArray);
//        if(processTypeMap != null && processTypeMap.size() > 0){
//            Set<String> keySet = processTypeMap.keySet();
//            for(String key: keySet){
//                multiValueMap.put(key, processTypeMap.get(key));
//            }
//        }
//        return bsearchService.doSearch(
//                searchModel, searchNodeConfigList, multiValueMap, client, true);
    }

    /**
     * Search Only [Plan] [Replenish] Picking Order
     * @param searchModel
     * @param client
     * @return
     * @throws SearchConfigureException
     * @throws ServiceEntityConfigureException
     * @throws NodeNotFoundException
     * @throws ServiceEntityInstallationException
     */
    public List<ServiceEntityNode> searchPickingOrderList(
            SEUIComModel searchModel, String client)
            throws SearchConfigureException, ServiceEntityConfigureException,
            NodeNotFoundException, ServiceEntityInstallationException {
        Map<String, List<?>> processTypeMap = ProdPickingOrderManager
                .genProcessTypeMapSearch(ProdPickingOrderManager
                        .getProcessType());
        return searchDocListCore(searchModel, processTypeMap, client);
    }

    /**
     * Search Only [Plan] [Replenish] Picking Order
     * @param searchModel
     * @param client
     * @return
     * @throws SearchConfigureException
     * @throws ServiceEntityConfigureException
     * @throws NodeNotFoundException
     * @throws ServiceEntityInstallationException
     */
    public List<ServiceEntityNode> searchReturnOrderList(
            SEUIComModel searchModel, String client)
            throws SearchConfigureException, ServiceEntityConfigureException,
            NodeNotFoundException, ServiceEntityInstallationException {
        Map<String, List<?>> processTypeMap = ProdPickingOrderManager
                .genProcessTypeMapSearch(ProdPickingOrderManager
                        .getReturnType());
        return searchDocListCore(searchModel, processTypeMap, client);
    }

    @Override
    public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // Search node:[prodPickingOrder]
        BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
        searchNodeConfig0.setSeName(ProdPickingOrder.SENAME);
        searchNodeConfig0.setNodeName(ProdPickingOrder.NODENAME);
        searchNodeConfig0.setNodeInstID(ProdPickingOrder.SENAME);
        searchNodeConfig0.setStartNodeFlag(true);
        searchNodeConfigList.add(searchNodeConfig0);
        // Search node:[prodPickingRefMaterialItem]
        BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
        searchNodeConfig1.setSeName(ProdPickingRefMaterialItem.SENAME);
        searchNodeConfig1.setNodeName(ProdPickingRefMaterialItem.NODENAME);
        searchNodeConfig1.setNodeInstID(ProdPickingRefMaterialItem.NODENAME);
        searchNodeConfig1.setStartNodeFlag(false);
        searchNodeConfig1.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
        searchNodeConfig1.setBaseNodeInstID(ProdPickingRefOrderItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig1);

        // Search node:[productionOrderItem]
        BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
        searchNodeConfig2.setSeName(ProductionOrderItem.SENAME);
        searchNodeConfig2.setNodeName(ProductionOrderItem.NODENAME);
        searchNodeConfig2.setNodeInstID(ProductionOrderItem.NODENAME);
        searchNodeConfig2.setStartNodeFlag(false);
        searchNodeConfig2.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig2.setMapBaseFieldName("prevDocMatItemUUID");
        searchNodeConfig2.setMapSourceFieldName("uuid");
        searchNodeConfig2.setBaseNodeInstID(ProdPickingRefMaterialItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig2);
        // Search node:[itemMaterialStockKeepUnit]
        BSearchNodeComConfigure searchNodeConfig3 = new BSearchNodeComConfigure();
        searchNodeConfig3.setSeName(MaterialStockKeepUnit.SENAME);
        searchNodeConfig3.setNodeName(MaterialStockKeepUnit.NODENAME);
        searchNodeConfig3.setNodeInstID(ProdPickingOrderSearchModel.NODEINST_ITEMMATERIALSKU);
        searchNodeConfig3.setStartNodeFlag(false);
        searchNodeConfig3.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig3.setMapBaseFieldName("refMaterialSKUUUID");
        searchNodeConfig3.setMapSourceFieldName("uuid");
        searchNodeConfig3.setBaseNodeInstID(ProductionOrderItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig3);
        // Search node:[prodPickingRefOrderItem]
        BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
        searchNodeConfig4.setSeName(ProdPickingRefOrderItem.SENAME);
        searchNodeConfig4.setNodeName(ProdPickingRefOrderItem.NODENAME);
        searchNodeConfig4.setNodeInstID(ProdPickingRefOrderItem.NODENAME);
        searchNodeConfig4.setStartNodeFlag(false);
        searchNodeConfig4.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
        searchNodeConfig4.setBaseNodeInstID(ProdPickingOrder.SENAME);
        searchNodeConfigList.add(searchNodeConfig4);

        // Search node:[productionOrder]
        BSearchNodeComConfigure searchNodeConfig5 = new BSearchNodeComConfigure();
        searchNodeConfig5.setSeName(ProductionOrder.SENAME);
        searchNodeConfig5.setNodeName(ProductionOrder.NODENAME);
        searchNodeConfig5.setNodeInstID(ProductionOrder.SENAME);
        searchNodeConfig5.setStartNodeFlag(false);
        searchNodeConfig5.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig5.setMapBaseFieldName("refProdOrderUUID");
        searchNodeConfig5.setMapSourceFieldName("uuid");
        searchNodeConfig5.setBaseNodeInstID(ProdPickingRefOrderItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig5);

        // Search node:[orderMaterialStockKeepUnit]
        BSearchNodeComConfigure searchNodeConfig8 = new BSearchNodeComConfigure();
        searchNodeConfig8.setSeName(MaterialStockKeepUnit.SENAME);
        searchNodeConfig8.setNodeName(MaterialStockKeepUnit.NODENAME);
        searchNodeConfig8.setNodeInstID(ProdPickingOrderSearchModel.NODEINST_ORDERMATERIALSKU);
        searchNodeConfig8.setStartNodeFlag(false);
        searchNodeConfig8.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig8.setMapBaseFieldName("refMaterialSKUUUID");
        searchNodeConfig8.setMapSourceFieldName("uuid");
        searchNodeConfig8.setBaseNodeInstID(ProductionOrder.SENAME);
        searchNodeConfigList.add(searchNodeConfig8);
        return searchNodeConfigList;
    }

    public List<ServiceEntityNode> searchPickingItemList(
            SEUIComModel searchModel, String client)
            throws SearchConfigureException, ServiceEntityConfigureException,
            NodeNotFoundException, ServiceEntityInstallationException {
        Map<String, List<?>> processTypeMap = ProdPickingOrderManager
                .genProcessTypeMapSearch(ProdPickingOrderManager
                        .getProcessType());
        return searchItemListCore(searchModel, processTypeMap, client);
    }

    public List<ServiceEntityNode> searchReturnItemList(
            SEUIComModel searchModel, String client)
            throws SearchConfigureException, ServiceEntityConfigureException,
            ServiceEntityInstallationException {
        Map<String, List<?>> processTypeMap = ProdPickingOrderManager
            .genProcessTypeMapSearch(ProdPickingOrderManager
                    .getReturnType());
        return searchItemListCore(searchModel, processTypeMap, client);
    }

    @Deprecated
    public List<ServiceEntityNode> searchItemListCore(
            SEUIComModel searchModel, Map<String, List<?>> processTypeMap, String client)
            throws SearchConfigureException, ServiceEntityConfigureException,
            ServiceEntityInstallationException {
        ProdPickingRefMaterialItemSearchModel prodPickingRefMaterialItemSearchModel = (ProdPickingRefMaterialItemSearchModel) searchModel;
        List<BSearchNodeComConfigure> searchNodeConfigList = getBasicItemSearchNodeConfigureList(null);
        String[] fieldNameArray = SearchDocConfigHelper.genDefaultMaterialFieldNameArray();
        Map<String, List<?>> multiValueMap = bsearchService
                .generateMulitpleSearchMap(searchModel, fieldNameArray);
        if(processTypeMap != null && processTypeMap.size() > 0){
            Set<String> keySet = processTypeMap.keySet();
            for(String key: keySet){
                multiValueMap.put(key, processTypeMap.get(key));
            }
        }
        return bsearchService.doSearch(
                searchModel, searchNodeConfigList, multiValueMap, client, true);
    }

    @Override
    public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // Search node:[prodPickingOrder]
        BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
        searchNodeConfig0.setSeName(ProdPickingRefMaterialItem.SENAME);
        searchNodeConfig0.setNodeName(ProdPickingRefMaterialItem.NODENAME);
        searchNodeConfig0.setNodeInstID(ProdPickingRefMaterialItem.NODENAME);
        searchNodeConfig0.setStartNodeFlag(true);
        searchNodeConfigList.add(searchNodeConfig0);
        // Search node:[prodPickingRefMaterialItem]
        BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
        searchNodeConfig1.setSeName(ProdPickingRefOrderItem.SENAME);
        searchNodeConfig1.setNodeName(ProdPickingRefOrderItem.NODENAME);
        searchNodeConfig1.setNodeInstID(ProdPickingRefOrderItem.NODENAME);
        searchNodeConfig1.setStartNodeFlag(false);
        searchNodeConfig1.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD);
        searchNodeConfig1.setBaseNodeInstID(ProdPickingRefMaterialItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig1);

        // Search node:[productionOrderItem]
        BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
        searchNodeConfig2.setSeName(ProductionOrder.SENAME);
        searchNodeConfig2.setNodeName(ProductionOrder.NODENAME);
        searchNodeConfig2.setNodeInstID(ProductionOrder.NODENAME);
        searchNodeConfig2.setStartNodeFlag(false);
        searchNodeConfig2.setToBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE);
        searchNodeConfig2.setBaseNodeInstID(ProdPickingRefOrderItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig2);
        // Search node:[itemMaterialStockKeepUnit]
        BSearchNodeComConfigure searchNodeConfig3 = new BSearchNodeComConfigure();
        searchNodeConfig3.setSeName(MaterialStockKeepUnit.SENAME);
        searchNodeConfig3.setNodeName(MaterialStockKeepUnit.NODENAME);
        searchNodeConfig3.setNodeInstID(MaterialStockKeepUnit.SENAME);
        searchNodeConfig3.setStartNodeFlag(false);
        searchNodeConfig3.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig3.setMapBaseFieldName("refMaterialSKUUUID");
        searchNodeConfig3.setMapSourceFieldName("uuid");
        searchNodeConfig3.setBaseNodeInstID(ProdPickingRefMaterialItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig3);

        // Search node:[ProdPickingOrder]
        BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
        searchNodeConfig4.setSeName(ProdPickingOrder.SENAME);
        searchNodeConfig4.setNodeName(ProdPickingOrder.NODENAME);
        searchNodeConfig4.setNodeInstID(ProdPickingOrder.SENAME);
        searchNodeConfig4.setStartNodeFlag(false);
        searchNodeConfig4.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD);
        searchNodeConfig4.setBaseNodeInstID(ProdPickingRefOrderItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig4);
        return searchNodeConfigList;
    }
}
