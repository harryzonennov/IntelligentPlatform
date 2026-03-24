package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.SerExtendPageMetadataUIModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.DocItemProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.SerExtendPageMetadata;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SerExtendPageMetadataManager {

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected SerExtendPageSettingManager serExtendPageSettingManager;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	@Autowired
	protected StandardSystemCategoryProxy standardSystemCategoryProxy;

	@Autowired
	protected DocItemProxy docItemProxy;

	private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

	public static final String METHOD_ConvSerExtendPageMetadataToUI = "convSerExtendPageMetadataToUI";

	public static final String METHOD_ConvUIToSerExtendPageMetadata = "convUIToSerExtendPageMetadata";

	public SerExtendPageMetadataServiceModel updateUniqueActivePage(SerExtendPageMetadataServiceModel serExtendPageMetadataServiceModel, SerialLogonInfo serialLogonInfo) throws DocActionException {
		try {
			// Check if the `itemStatus` value is active
			SerExtendPageMetadata serExtendPageMetadata = serExtendPageMetadataServiceModel.getSerExtendPageMetadata();
			if (serExtendPageMetadata.getItemStatus() != SerExtendPageSetting.STATUS_ACTIVE) {
				return serExtendPageMetadataServiceModel;
			}
			List<ServiceEntityNode> allSerExtendPageMetadataList = serviceExtensionSettingManager.getEntityNodeListByParentUUID(serExtendPageMetadata.getParentNodeUUID(),
					SerExtendPageMetadata.NODENAME, serExtendPageMetadata.getClient());
			docItemProxy.exclusiveExeSelectItemList(ServiceCollectionsHelper.asList(serExtendPageMetadata), allSerExtendPageMetadataList, null, (serviceEntityNode, serialLogonInfo1) -> {
				SerExtendPageMetadata otherSerExtendPageMetadata = (SerExtendPageMetadata) serviceEntityNode;
				otherSerExtendPageMetadata.setItemStatus(SerExtendPageSetting.STATUS_ARCHIVED);
				return null;
			}, serialLogonInfo);
		} catch (ServiceEntityConfigureException e) {
			throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
		}
		return serExtendPageMetadataServiceModel;
	}

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), SerExtendPageSetting.NODENAME,
						request.getUuid(), SerExtendPageMetadata.NODENAME, serviceExtensionSettingManager);
		docPageHeaderInputPara.setGenBaseModelList(
				(DocPageHeaderModelProxy.GenBaseModelList<SerExtendPageSetting>) serExtendPageSetting -> {
					// How to get the base page header model list
					List<PageHeaderModel> pageHeaderModelList =
							serExtendPageSettingManager
									.getPageHeaderModelList(new SimpleSEJSONRequest(serExtendPageSetting.getUuid(),
											serExtendPageSetting.getParentNodeUUID()), client);
					return pageHeaderModelList;
				});
		docPageHeaderInputPara.setGenHomePageModel(
				(DocPageHeaderModelProxy.GenHomePageModel<SerExtendPageMetadata>) (serExtendPageMetadata, pageHeaderModel) -> {
					// How to render current page header
					pageHeaderModel.setHeaderName(serExtendPageMetadata.getId());
					return pageHeaderModel;
				});
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	public void convSerExtendPageMetadataToUI(SerExtendPageMetadata serExtendPageMetadata,
											  SerExtendPageMetadataUIModel serExtendPageMetadataUIModel)
			throws ServiceEntityInstallationException {
		convSerExtendPageMetadataToUI(serExtendPageMetadata, serExtendPageMetadataUIModel, null);
	}

	public SerExtendPageMetadata newPageMetadata(SerExtendPageMetadata serExtendPageMetadata)
			throws ServiceEntityConfigureException {
		serExtendPageMetadata.setPageMeta(ServiceJSONParser.emptyJson());
		List<ServiceEntityNode> subPageMetaList =
				serviceExtensionSettingManager.getEntityNodeListByParentUUID(serExtendPageMetadata.getParentNodeUUID(),
						SerExtendPageMetadata.NODENAME, serExtendPageMetadata.getClient());
		serExtendPageMetadata.setId(Integer.toString(subPageMetaList.size()) + 1);
		return serExtendPageMetadata;
	}

	public Map<Integer, String> initSystemCategoryMap(String languageCode) throws ServiceEntityInstallationException {
		return standardSystemCategoryProxy.getSystemCategoryMap(languageCode);
	}

	public Map<Integer, String> initItemStatus(String languageCode) throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.statusMapLan, SerExtendPageMetadataUIModel.class,
				"itemStatus");
	}

	public void convSerExtendPageMetadataToUI(SerExtendPageMetadata serExtendPageMetadata,
											  SerExtendPageMetadataUIModel serExtendPageMetadataUIModel,
											  LogonInfo logonInfo) throws ServiceEntityInstallationException {
		DocFlowProxy.convServiceEntityNodeToUIModel(serExtendPageMetadata, serExtendPageMetadataUIModel);
		serExtendPageMetadataUIModel.setId(serExtendPageMetadata.getId());
		serExtendPageMetadataUIModel.setItemStatus(serExtendPageMetadata.getItemStatus());
		serExtendPageMetadataUIModel.setSystemCategory(serExtendPageMetadata.getSystemCategory());
		if (logonInfo != null) {
			Map<Integer, String> systemCategoryMap = initSystemCategoryMap(logonInfo.getLanguageCode());
			serExtendPageMetadataUIModel.setSystemCategoryValue(systemCategoryMap.get(serExtendPageMetadata.getSystemCategory()));
			Map<Integer, String> itemStatusMap = initItemStatus(logonInfo.getLanguageCode());
			serExtendPageMetadataUIModel.setItemStatusValue(itemStatusMap.get(serExtendPageMetadata.getItemStatus()));
		}
		serExtendPageMetadataUIModel.setPageMeta(serExtendPageMetadata.getPageMeta());
	}

	public void convUIToSerExtendPageMetadata(SerExtendPageMetadataUIModel serExtendPageMetadataUIModel,
											  SerExtendPageMetadata rawEntity){
		DocFlowProxy.convUIToServiceEntityNode(serExtendPageMetadataUIModel, rawEntity);
		if (!ServiceEntityStringHelper
				.checkNullString(serExtendPageMetadataUIModel.getPageMeta())) {
			rawEntity.setPageMeta(serExtendPageMetadataUIModel.getPageMeta());
		}
	}

}
