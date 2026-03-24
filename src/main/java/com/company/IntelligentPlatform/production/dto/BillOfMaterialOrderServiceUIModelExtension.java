package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
import com.company.IntelligentPlatform.production.service.BillOfMaterialTemplateManager;
import com.company.IntelligentPlatform.production.service.ProcessRouteOrderManager;
import com.company.IntelligentPlatform.production.service.ProdWorkCenterManager;
import com.company.IntelligentPlatform.production.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class BillOfMaterialOrderServiceUIModelExtension extends
        ServiceUIModelExtension {

    @Autowired
    protected BillOfMaterialItemServiceUIModelExtension billOfMaterialItemServiceUIModelExtension;

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected ProcessRouteOrderManager processRouteOrderManager;

    @Autowired
    protected ProdWorkCenterManager prodWorkCenterManager;

    @Autowired
    protected BillOfMaterialTemplateManager billOfMaterialTemplateManager;

    @Autowired
    protected DocAttachmentProxy docAttachmentProxy;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
        List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
        resultList.add(billOfMaterialItemServiceUIModelExtension);
        resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
                BillOfMaterialAttachment.SENAME,
                BillOfMaterialAttachment.NODENAME,
                BillOfMaterialAttachment.NODENAME
        )));

        resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
                BillOfMaterialOrderActionNode.SENAME,
                BillOfMaterialOrderActionNode.NODENAME,
                SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE,
                billOfMaterialOrderManager, BillOfMaterialOrderActionNode.DOC_ACTION_APPROVE
        )));
        resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
                BillOfMaterialOrderActionNode.SENAME,
                BillOfMaterialOrderActionNode.NODENAME,
                SystemDefDocActionCodeProxy.NODEINST_ACTION_COUNTAPPROVE,
                billOfMaterialOrderManager, BillOfMaterialOrderActionNode.DOC_ACTION_ACTIVE
        )));
        resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
                BillOfMaterialOrderActionNode.SENAME,
                BillOfMaterialOrderActionNode.NODENAME,
                BillOfMaterialOrderActionNode.NODEINST_ACTION_SUBMIT,
                billOfMaterialOrderManager, BillOfMaterialOrderActionNode.DOC_ACTION_SUBMIT
        )));
        resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
                BillOfMaterialOrderActionNode.SENAME,
                BillOfMaterialOrderActionNode.NODENAME,
                BillOfMaterialOrderActionNode.NODEINST_ACTION_REVOKE_SUBMIT,
                billOfMaterialOrderManager, BillOfMaterialOrderActionNode.DOC_ACTION_REVOKE_SUBMIT
        )));
        resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
                BillOfMaterialOrderActionNode.SENAME,
                BillOfMaterialOrderActionNode.NODENAME,
                BillOfMaterialOrderActionNode.NODEINST_ACTION_REJECT_APPROVE,
                billOfMaterialOrderManager, BillOfMaterialOrderActionNode.DOC_ACTION_REJECT_APPROVE
        )));
        return resultList;
    }

    @Override
    public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
        List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        ServiceUIModelExtensionUnion billOfMaterialOrderExtensionUnion = new ServiceUIModelExtensionUnion();
        billOfMaterialOrderExtensionUnion
                .setNodeInstId(BillOfMaterialOrder.SENAME);
        billOfMaterialOrderExtensionUnion
                .setNodeName(BillOfMaterialOrder.NODENAME);

        // UI Model Configure of node:[BillOfMaterialOrder]
        UIModelNodeMapConfigure billOfMaterialOrderMap = new UIModelNodeMapConfigure();
        billOfMaterialOrderMap.setSeName(BillOfMaterialOrder.SENAME);
        billOfMaterialOrderMap.setNodeName(BillOfMaterialOrder.NODENAME);
        billOfMaterialOrderMap.setNodeInstID(BillOfMaterialOrder.SENAME);
        billOfMaterialOrderMap.setHostNodeFlag(true);
        Class<?>[] billOfMaterialOrderConvToUIParas = {
                BillOfMaterialOrder.class, BillOfMaterialOrderUIModel.class};
        billOfMaterialOrderMap
                .setConvToUIMethodParas(billOfMaterialOrderConvToUIParas);
        billOfMaterialOrderMap
                .setConvToUIMethod(BillOfMaterialOrderManager.METHOD_ConvBillOfMaterialOrderToUI);
        Class<?>[] BillOfMaterialOrderConvUIToParas = {
                BillOfMaterialOrderUIModel.class, BillOfMaterialOrder.class};
        billOfMaterialOrderMap
                .setConvUIToMethodParas(BillOfMaterialOrderConvUIToParas);
        billOfMaterialOrderMap
                .setConvUIToMethod(BillOfMaterialOrderManager.METHOD_ConvUIToBillOfMaterialOrder);
        uiModelNodeMapList.add(billOfMaterialOrderMap);

        // UI Model Configure of node:[TargetMaterial]
        UIModelNodeMapConfigure targetMaterialMap = new UIModelNodeMapConfigure();
        targetMaterialMap.setSeName(MaterialStockKeepUnit.SENAME);
        targetMaterialMap.setNodeName(MaterialStockKeepUnit.NODENAME);
        targetMaterialMap.setNodeInstID("TargetMaterial");
        targetMaterialMap.setBaseNodeInstID(BillOfMaterialOrder.SENAME);
        targetMaterialMap
                .setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
        targetMaterialMap.setServiceEntityManager(materialStockKeepUnitManager);

        List<SearchConfigConnectCondition> refMaterialConditionList = new ArrayList<>();
        SearchConfigConnectCondition refMaterialCondition0 = new SearchConfigConnectCondition();
        refMaterialCondition0.setSourceFieldName("refMaterialSKUUUID");
        refMaterialCondition0
                .setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
        refMaterialConditionList.add(refMaterialCondition0);
        targetMaterialMap.setConnectionConditions(refMaterialConditionList);

        Class<?>[] targetMaterialConvToUIParas = {MaterialStockKeepUnit.class,
                BillOfMaterialOrderUIModel.class};
        targetMaterialMap.setConvToUIMethodParas(targetMaterialConvToUIParas);
        targetMaterialMap
                .setConvToUIMethod(BillOfMaterialOrderManager.METHOD_ConvMaterialStockKeepUnitToUI);
        uiModelNodeMapList.add(targetMaterialMap);
        billOfMaterialOrderExtensionUnion
                .setUiModelNodeMapList(uiModelNodeMapList);

        // UI Model Configure of node:[Prod work center]
        UIModelNodeMapConfigure prodWorkCenterMap = new UIModelNodeMapConfigure();
        prodWorkCenterMap.setSeName(ProdWorkCenter.SENAME);
        prodWorkCenterMap.setNodeName(ProdWorkCenter.NODENAME);
        prodWorkCenterMap.setNodeInstID(ProdWorkCenter.SENAME);
        prodWorkCenterMap.setBaseNodeInstID(BillOfMaterialOrder.SENAME);
        prodWorkCenterMap
                .setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
        prodWorkCenterMap.setServiceEntityManager(prodWorkCenterManager);
        List<SearchConfigConnectCondition> prodWorkCenterConditionList = new ArrayList<>();
        SearchConfigConnectCondition prodWorkCenterCondition0 = new SearchConfigConnectCondition();
        prodWorkCenterCondition0.setSourceFieldName("refWocUUID");
        prodWorkCenterCondition0
                .setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
        prodWorkCenterConditionList.add(prodWorkCenterCondition0);
        prodWorkCenterMap.setConnectionConditions(prodWorkCenterConditionList);
        Class<?>[] prodWorkCenterConvToUIParas = {ProdWorkCenter.class,
                BillOfMaterialOrderUIModel.class};
        prodWorkCenterMap
                .setConvToUIMethodParas(prodWorkCenterConvToUIParas);
        prodWorkCenterMap
                .setConvToUIMethod(BillOfMaterialOrderManager.METHOD_ConvProdWorkCenterToUI);
        uiModelNodeMapList.add(prodWorkCenterMap);

        // UI Model Configure of node:[Prod work center]
        UIModelNodeMapConfigure bomTemplateMap = new UIModelNodeMapConfigure();
        bomTemplateMap.setSeName(BillOfMaterialTemplate.SENAME);
        bomTemplateMap.setNodeName(BillOfMaterialTemplate.NODENAME);
        bomTemplateMap.setNodeInstID(BillOfMaterialTemplate.SENAME);
        bomTemplateMap.setBaseNodeInstID(BillOfMaterialOrder.SENAME);
        bomTemplateMap
                .setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
        bomTemplateMap.setServiceEntityManager(billOfMaterialTemplateManager);
        List<SearchConfigConnectCondition> bomTemplateConditionList = new ArrayList<>();
        SearchConfigConnectCondition bomTemplateCondition0 = new SearchConfigConnectCondition();
        bomTemplateCondition0.setSourceFieldName(BillOfMaterialOrder.FIELD_RefTemplateUUID);
        bomTemplateCondition0
                .setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
        bomTemplateConditionList.add(bomTemplateCondition0);
        bomTemplateMap.setConnectionConditions(bomTemplateConditionList);
        Class<?>[] bomTemplateConvToUIParas = {BillOfMaterialTemplate.class,
                BillOfMaterialOrderUIModel.class};
        bomTemplateMap
                .setConvToUIMethodParas(bomTemplateConvToUIParas);
        bomTemplateMap
                .setConvToUIMethod(BillOfMaterialOrderManager.METHOD_ConvBillOfMaterialTemplateToUI);
        uiModelNodeMapList.add(bomTemplateMap);


        // UI Model Configure of node:[Prod Process order]
        UIModelNodeMapConfigure processRouteOrderMap = new UIModelNodeMapConfigure();
        processRouteOrderMap.setSeName(ProcessRouteOrder.SENAME);
        processRouteOrderMap.setNodeName(ProcessRouteOrder.NODENAME);
        processRouteOrderMap.setNodeInstID(ProcessRouteOrder.SENAME);
        processRouteOrderMap.setBaseNodeInstID(BillOfMaterialOrder.SENAME);
        processRouteOrderMap
                .setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
        processRouteOrderMap.setServiceEntityManager(processRouteOrderManager);
        List<SearchConfigConnectCondition> processRouteConditionList = new ArrayList<>();
        SearchConfigConnectCondition processRouteCondition0 = new SearchConfigConnectCondition();
        processRouteCondition0.setSourceFieldName("refRouteOrderUUID");
        processRouteCondition0
                .setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
        processRouteConditionList.add(processRouteCondition0);
        processRouteOrderMap.setConnectionConditions(processRouteConditionList);
        Class<?>[] processRouteOrderConvToUIParas = {ProcessRouteOrder.class,
                BillOfMaterialOrderUIModel.class};
        processRouteOrderMap
                .setConvToUIMethodParas(processRouteOrderConvToUIParas);
        processRouteOrderMap
                .setConvToUIMethod(BillOfMaterialOrderManager.METHOD_ConvProcessRouteOrderToUI);
        uiModelNodeMapList.add(processRouteOrderMap);

        uiModelNodeMapList.addAll(docFlowProxy
                .getDocDefCreateUpdateNodeMapConfigureList(BillOfMaterialOrder.SENAME));


        billOfMaterialOrderExtensionUnion
                .setUiModelNodeMapList(uiModelNodeMapList);

        resultList.add(billOfMaterialOrderExtensionUnion);
        return resultList;
    }

}
