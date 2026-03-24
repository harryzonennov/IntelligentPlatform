package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.SerExtendPageI18nUIModel;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SerExtendPageI18n;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;

import java.util.List;

@Service
public class SerExtendPageI18nManager {

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	public static final String METHOD_ConvSerExtendPageI18nToUI = "convSerExtendPageI18nToUI";

	public static final String METHOD_ConvUIToSerExtendPageI18n = "convUIToSerExtendPageI18n";

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ServiceExtensionSetting.NODENAME,
						request.getUuid(), SerExtendPageI18n.NODENAME, serviceExtensionSettingManager);
		docPageHeaderInputPara.setGenBaseModelList(
				(DocPageHeaderModelProxy.GenBaseModelList<ServiceExtensionSetting>) serviceExtensionSetting -> {
					// How to get the base page header model list
					List<PageHeaderModel> pageHeaderModelList =
							serviceExtensionSettingManager
									.getPageHeaderModelList(serviceExtensionSetting);
					return pageHeaderModelList;
				});
		docPageHeaderInputPara.setGenHomePageModel(
				(DocPageHeaderModelProxy.GenHomePageModel<SerExtendPageI18n>) (serExtendPageI18n, pageHeaderModel) -> {
					// How to render current page header
					pageHeaderModel.setHeaderName(serExtendPageI18n.getLanCode());
					return pageHeaderModel;
				});
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	public SerExtendPageI18n newModuleService(ServiceExtensionSetting serviceExtensionSetting)
			throws ServiceEntityConfigureException {
		return (SerExtendPageI18n) serviceExtensionSettingManager.newEntityNode(serviceExtensionSetting, SerExtendPageI18n.NODENAME);
	}

	public void convSerExtendPageI18nToUI(SerExtendPageI18n serExtendPageI18n,
										  SerExtendPageI18nUIModel serExtendPageI18nUIModel) {
		convSerExtendPageI18nToUI(serExtendPageI18n, serExtendPageI18nUIModel,null);
	}

	public void convSerExtendPageI18nToUI(SerExtendPageI18n serExtendPageI18n,
										  SerExtendPageI18nUIModel serExtendPageI18nUIModel,
										  LogonInfo logonInfo) {
		DocFlowProxy.convServiceEntityNodeToUIModel(serExtendPageI18n, serExtendPageI18nUIModel);
		serExtendPageI18nUIModel.setLanCode(serExtendPageI18n.getLanCode());
		serExtendPageI18nUIModel.setPropertyContent(serExtendPageI18n.getPropertyContent());
		serExtendPageI18nUIModel.setId(serExtendPageI18n.getId());
	}

	public void convUIToSerExtendPageI18n(SerExtendPageI18nUIModel serExtendPageI18nUIModel,
										  SerExtendPageI18n rawEntity){
		DocFlowProxy.convUIToServiceEntityNode(serExtendPageI18nUIModel, rawEntity);
		rawEntity.setPropertyContent(serExtendPageI18nUIModel.getPropertyContent());
		if (!ServiceEntityStringHelper
				.checkNullString(serExtendPageI18nUIModel.getLanCode())) {
			rawEntity.setLanCode(serExtendPageI18nUIModel.getLanCode());
		}
	}

}
