package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProdOrderReportUIModel;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.ProdOrderReport;
import com.company.IntelligentPlatform.production.model.ProductionOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.LogonUser;

@Deprecated
@Service
public class ProdOrderReportServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ProdOrderReportItemServiceUIModelExtension prodOrderReportItemServiceUIModelExtension;

	@Autowired
	protected ProdOrderReportAttachmentServiceUIModelExtension prodOrderReportAttachmentServiceUIModelExtension;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		resultList.add(prodOrderReportItemServiceUIModelExtension);
		resultList.add(prodOrderReportAttachmentServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion prodOrderReportExtensionUnion = new ServiceUIModelExtensionUnion();
		prodOrderReportExtensionUnion.setNodeInstId(ProdOrderReport.NODENAME);
		prodOrderReportExtensionUnion.setNodeName(ProdOrderReport.NODENAME);



		// UI Model Configure of node:[ReportBy]
		UIModelNodeMapConfigure reportByMap = new UIModelNodeMapConfigure();
		reportByMap.setSeName(LogonUser.SENAME);
		reportByMap.setNodeName(LogonUser.NODENAME);
		reportByMap.setNodeInstID(LogonUser.SENAME);
		reportByMap.setBaseNodeInstID(ProdOrderReport.NODENAME);
		reportByMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		reportByMap.setServiceEntityManager(logonUserManager);

		List<SearchConfigConnectCondition> reportByConditionList = new ArrayList<>();
		SearchConfigConnectCondition reportByCondition0 = new SearchConfigConnectCondition();
		reportByCondition0.setSourceFieldName("reportedBy");
		reportByCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		reportByConditionList.add(reportByCondition0);
		reportByMap.setConnectionConditions(reportByConditionList);
		Class<?>[] reportByConvToUIParas = { LogonUser.class,
				ProdOrderReportUIModel.class };
		reportByMap.setConvToUIMethodParas(reportByConvToUIParas);
		reportByMap
				.setConvToUIMethod(ProductionOrderManager.METHOD_ConvReportByToUI);
		uiModelNodeMapList.add(reportByMap);
		prodOrderReportExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodOrderReportExtensionUnion);
		return resultList;
	}

}
