package com.company.IntelligentPlatform.production.dto;

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

import java.util.ArrayList;
import java.util.List;

@Service
public class BillOfMaterialTemplateServiceUIModelExtension extends
        ServiceUIModelExtension {

    @Autowired
    protected BillOfMaterialTemplateItemServiceUIModelExtension billOfMaterialTemplateItemServiceUIModelExtension;

    @Autowired
    protected BillOfMaterialTemplateManager billOfMaterialTemplateManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected ProcessRouteOrderManager processRouteOrderManager;

    @Autowired
    protected ProdWorkCenterManager prodWorkCenterManager;

    @Autowired
    protected DocAttachmentProxy docAttachmentProxy;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
        List<ServiceUIModelExtension> resultList = new ArrayList<>();
        resultList.add(billOfMaterialTemplateItemServiceUIModelExtension);
        resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
                BillOfMaterialAttachment.SENAME,
                BillOfMaterialAttachment.NODENAME,
                BillOfMaterialAttachment.NODENAME
        )));

        resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
                BillOfMaterialTemplateActionNode.SENAME,
                BillOfMaterialTemplateActionNode.NODENAME,
                SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE,
                billOfMaterialTemplateManager, BillOfMaterialTemplateActionNode.DOC_ACTION_APPROVE
        )));

        resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
                BillOfMaterialTemplateActionNode.SENAME,
                BillOfMaterialTemplateActionNode.NODENAME,
                BillOfMaterialTemplateActionNode.NODEINST_ACTION_SUBMIT,
                billOfMaterialTemplateManager, BillOfMaterialTemplateActionNode.DOC_ACTION_SUBMIT
        )));
        resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
                BillOfMaterialTemplateActionNode.SENAME,
                BillOfMaterialTemplateActionNode.NODENAME,
                BillOfMaterialTemplateActionNode.NODEINST_ACTION_REVOKE_SUBMIT,
                billOfMaterialTemplateManager, BillOfMaterialTemplateActionNode.DOC_ACTION_REVOKE_SUBMIT
        )));
        resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
                BillOfMaterialTemplateActionNode.SENAME,
                BillOfMaterialTemplateActionNode.NODENAME,
                BillOfMaterialTemplateActionNode.NODEINST_ACTION_REJECT_APPROVE,
                billOfMaterialTemplateManager, BillOfMaterialTemplateActionNode.DOC_ACTION_REJECT_APPROVE
        )));
        return resultList;
    }

    @Override
    public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
        List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        ServiceUIModelExtensionUnion billOfMaterialTemplateExtensionUnion = new ServiceUIModelExtensionUnion();
        billOfMaterialTemplateExtensionUnion
                .setNodeInstId(BillOfMaterialTemplate.SENAME);
        billOfMaterialTemplateExtensionUnion
                .setNodeName(BillOfMaterialTemplate.NODENAME);

        // UI Model Configure of node:[BillOfMaterialTemplate]
        UIModelNodeMapConfigure billOfMaterialTemplateMap = new UIModelNodeMapConfigure();
        billOfMaterialTemplateMap.setSeName(BillOfMaterialTemplate.SENAME);
        billOfMaterialTemplateMap.setNodeName(BillOfMaterialTemplate.NODENAME);
        billOfMaterialTemplateMap.setNodeInstID(BillOfMaterialTemplate.SENAME);
        billOfMaterialTemplateMap.setHostNodeFlag(true);
        Class<?>[] billOfMaterialTemplateConvToUIParas = {
                BillOfMaterialTemplate.class, BillOfMaterialTemplateUIModel.class};
        billOfMaterialTemplateMap
                .setConvToUIMethodParas(billOfMaterialTemplateConvToUIParas);
        billOfMaterialTemplateMap
                .setConvToUIMethod(BillOfMaterialTemplateManager.METHOD_ConvBillOfMaterialTemplateToUI);
        Class<?>[] BillOfMaterialTemplateConvUIToParas = {
                BillOfMaterialTemplateUIModel.class, BillOfMaterialTemplate.class};
        billOfMaterialTemplateMap
                .setConvUIToMethodParas(BillOfMaterialTemplateConvUIToParas);
        billOfMaterialTemplateMap
                .setConvUIToMethod(BillOfMaterialTemplateManager.METHOD_ConvUIToBillOfMaterialTemplate);
        uiModelNodeMapList.add(billOfMaterialTemplateMap);

        // UI Model Configure of node:[TargetMaterial]
        UIModelNodeMapConfigure targetMaterialMap = new UIModelNodeMapConfigure();
        targetMaterialMap.setSeName(MaterialStockKeepUnit.SENAME);
        targetMaterialMap.setNodeName(MaterialStockKeepUnit.NODENAME);
        targetMaterialMap.setNodeInstID("TargetMaterial");
        targetMaterialMap.setBaseNodeInstID(BillOfMaterialTemplate.SENAME);
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
                BillOfMaterialTemplateUIModel.class};
        targetMaterialMap.setConvToUIMethodParas(targetMaterialConvToUIParas);
        targetMaterialMap
                .setConvToUIMethod(BillOfMaterialTemplateManager.METHOD_ConvMaterialStockKeepUnitToUI);
        uiModelNodeMapList.add(targetMaterialMap);
        billOfMaterialTemplateExtensionUnion
                .setUiModelNodeMapList(uiModelNodeMapList);

        // UI Model Configure of node:[Prod work center]
        UIModelNodeMapConfigure prodWorkCenterMap = new UIModelNodeMapConfigure();
        prodWorkCenterMap.setSeName(ProdWorkCenter.SENAME);
        prodWorkCenterMap.setNodeName(ProdWorkCenter.NODENAME);
        prodWorkCenterMap.setNodeInstID(ProdWorkCenter.SENAME);
        prodWorkCenterMap.setBaseNodeInstID(BillOfMaterialTemplate.SENAME);
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
                BillOfMaterialTemplateUIModel.class};
        prodWorkCenterMap
                .setConvToUIMethodParas(prodWorkCenterConvToUIParas);
        prodWorkCenterMap
                .setConvToUIMethod(BillOfMaterialTemplateManager.METHOD_ConvProdWorkCenterToUI);
        uiModelNodeMapList.add(prodWorkCenterMap);

        // UI Model Configure of node:[Prod Process order]
        UIModelNodeMapConfigure processRouteOrderMap = new UIModelNodeMapConfigure();
        processRouteOrderMap.setSeName(ProcessRouteOrder.SENAME);
        processRouteOrderMap.setNodeName(ProcessRouteOrder.NODENAME);
        processRouteOrderMap.setNodeInstID(ProcessRouteOrder.SENAME);
        processRouteOrderMap.setBaseNodeInstID(BillOfMaterialTemplate.SENAME);
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
                BillOfMaterialTemplateUIModel.class};
        processRouteOrderMap
                .setConvToUIMethodParas(processRouteOrderConvToUIParas);
        processRouteOrderMap
                .setConvToUIMethod(BillOfMaterialTemplateManager.METHOD_ConvProcessRouteOrderToUI);
        uiModelNodeMapList.add(processRouteOrderMap);

        uiModelNodeMapList.addAll(docFlowProxy
                .getDocDefCreateUpdateNodeMapConfigureList(BillOfMaterialTemplate.SENAME));

        billOfMaterialTemplateExtensionUnion
                .setUiModelNodeMapList(uiModelNodeMapList);

        resultList.add(billOfMaterialTemplateExtensionUnion);
        return resultList;
    }

}
