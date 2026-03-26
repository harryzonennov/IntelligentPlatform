package com.company.IntelligentPlatform.finance.dto;

import com.company.IntelligentPlatform.common.controller.ISEUIConfigureMapping;
import com.company.IntelligentPlatform.common.controller.SEUIModelMapConfigure;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ResFinAccountFieldSetting;
import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;

@ISEUIConfigureMapping(uiModelName = "platform.foundation.Controller.Model.SystemResource.ResFinAccountSettingUIModel")
public class ResFinAccountSettingUIModelMapConfigure extends
		SEUIModelMapConfigure {

	@Override
	public void initConfigure() {
		// UI Model Configure of node:[ResFinAccountSetting] Path:
		// [SystemResource] -->[ResFinAccountSetting]
		UIModelNodeMapConfigure resFinAccountSettingUIModelMap = new UIModelNodeMapConfigure();
		resFinAccountSettingUIModelMap.setHostNodeFlag(true);
		resFinAccountSettingUIModelMap.setEditNodeFlag(true);
		resFinAccountSettingUIModelMap.setSeName(ResFinAccountSetting.SENAME);
		resFinAccountSettingUIModelMap
				.setNodeName(ResFinAccountSetting.NODENAME);
		resFinAccountSettingUIModelMap
				.setNodeInstID(ResFinAccountSetting.NODENAME);		
		resFinAccountSettingUIModelMap.setEditNodeFlag(true);
		// [ResFinAccountSetting] -->[FinAccountTitle]
		this.uiModelNodeMapList.add(resFinAccountSettingUIModelMap);
		UIModelNodeMapConfigure finAccountTitleUIModelMap = new UIModelNodeMapConfigure();
		finAccountTitleUIModelMap.setSeName(IServiceModelConstants.FinAccountTitle);
		finAccountTitleUIModelMap.setNodeName(ServiceEntityNode.NODENAME_ROOT);
		finAccountTitleUIModelMap.setNodeInstID(IServiceModelConstants.FinAccountTitle);
		finAccountTitleUIModelMap.setBaseNodeInstID(ResFinAccountSetting.NODENAME);
		finAccountTitleUIModelMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		finAccountTitleUIModelMap.setSelectScenaFlag(true);
		this.uiModelNodeMapList.add(finAccountTitleUIModelMap);
		// [ResFinAccountSetting] -->[ResFinAccountFieldSetting]
		UIModelNodeMapConfigure resFinAccFieldUIModelMap = new UIModelNodeMapConfigure();
		resFinAccFieldUIModelMap.setSeName(ResFinAccountFieldSetting.SENAME);
		resFinAccFieldUIModelMap.setNodeName(ResFinAccountFieldSetting.NODENAME);
		resFinAccFieldUIModelMap.setNodeInstID(ResFinAccountFieldSetting.NODENAME);
		resFinAccFieldUIModelMap.setBaseNodeInstID(ResFinAccountSetting.NODENAME);
		resFinAccFieldUIModelMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
		resFinAccFieldUIModelMap.setIdHelperFlag(true);
		resFinAccFieldUIModelMap.setListEmbededCategory(UIModelNodeMapConfigure.LIST_CATE_EDIT);
		resFinAccFieldUIModelMap.setSubNodeUIModelCls(ResFinAccountFieldSettingUIModel.class);
		this.uiModelNodeMapList.add(resFinAccFieldUIModelMap);
	}

}
