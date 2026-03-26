package com.company.IntelligentPlatform.common.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.NavigationGroupSettingUIModel;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.NavigationGroupSetting;
import com.company.IntelligentPlatform.common.model.NavigationItemSetting;
import com.company.IntelligentPlatform.common.model.NavigationSystemSetting;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [NavigationSystemSetting]
 *
 * @author
 * @date Thu Aug 06 23:43:51 CST 2020
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
public class NavigationGroupSettingManager extends ServiceEntityManager {

	public static final String METHOD_ConvNavigationGroupSettingToUI = "convNavigationGroupSettingToUI";

	public static final String METHOD_ConvUIToNavigationGroupSetting = "convUIToNavigationGroupSetting";

	public static final String METHOD_ConvSystemToGroupUI = "convSystemToGroupUI";

	public static final String METHOD_ConvGroupDefItemToUI = "convGroupDefItemToUI";

	public static final String METHOD_ConvAuthorizationObjectToGroupUI = "convAuthorizationObjectToGroupUI";

	@Autowired
	protected NavigationSystemSettingManager navigationSystemSettingManager;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), NavigationSystemSetting.NODENAME,
						request.getUuid(), NavigationGroupSetting.NODENAME, navigationSystemSettingManager);
		docPageHeaderInputPara.setGenBaseModelList((DocPageHeaderModelProxy.GenBaseModelList<NavigationSystemSetting>) navigationSystemSetting -> {
            // How to get the base page header model list
            return docPageHeaderModelProxy.getDocPageHeaderModelList(navigationSystemSetting, null);
        });
		docPageHeaderInputPara.setGenHomePageModel((DocPageHeaderModelProxy.GenHomePageModel<NavigationGroupSetting>) (navigationGroupSetting, pageHeaderModel) -> {
            // How to render current page header
            pageHeaderModel.setHeaderName(navigationGroupSetting.getName());
            return pageHeaderModel;
        });
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convNavigationGroupSettingToUI(
			NavigationGroupSetting navigationGroupSetting,
			NavigationGroupSettingUIModel navigationGroupSettingUIModel,
			LogonInfo logonInfo) {
		if (navigationGroupSetting != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(navigationGroupSetting,
					navigationGroupSettingUIModel);
			navigationGroupSettingUIModel.setElementIcon(navigationGroupSetting
					.getElementIcon());
			navigationGroupSettingUIModel
					.setRefDefItemUUID(navigationGroupSetting
							.getRefDefItemUUID());
			navigationGroupSettingUIModel.setResOrgUUID(navigationGroupSetting
					.getResOrgUUID());
			if (navigationGroupSetting.getCreatedTime() != null) {
				navigationGroupSettingUIModel
						.setCreatedTime(DefaultDateFormatConstant.DATE_FORMAT
								.format(navigationGroupSetting.getCreatedTime()));
			}
			navigationGroupSettingUIModel
					.setDisplayIndex(navigationGroupSetting.getDisplayIndex());
			navigationGroupSettingUIModel
					.setResEmployeeUUID(navigationGroupSetting
							.getResEmployeeUUID());
			navigationGroupSettingUIModel
					.setRefSimAuthorObjectUUID(navigationGroupSetting
							.getRefSimAuthorObjectUUID());
			navigationGroupSettingUIModel
					.setServiceEntityName(navigationGroupSetting
							.getServiceEntityName());
			navigationGroupSettingUIModel
					.setLastUpdateBy(navigationGroupSetting.getLastUpdateBy());
			navigationGroupSettingUIModel
					.setRefAuthorActionCodeUUID(navigationGroupSetting
							.getRefAuthorActionCodeUUID());
			navigationGroupSettingUIModel
					.setRefSourceUUID(navigationGroupSetting.getRefSourceUUID());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:navigationGroupSetting
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToNavigationGroupSetting(
			NavigationGroupSettingUIModel navigationGroupSettingUIModel,
			NavigationGroupSetting rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(navigationGroupSettingUIModel,
				rawEntity);
		rawEntity
				.setElementIcon(navigationGroupSettingUIModel.getElementIcon());
		rawEntity.setRefDefItemUUID(navigationGroupSettingUIModel
				.getRefDefItemUUID());
		rawEntity.setResOrgUUID(navigationGroupSettingUIModel.getResOrgUUID());
		if (!ServiceEntityStringHelper
				.checkNullString(navigationGroupSettingUIModel.getCreatedTime())) {
			try {
				rawEntity.setCreatedTime(DefaultDateFormatConstant.DATE_FORMAT
						.parse(navigationGroupSettingUIModel.getCreatedTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());

			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setDisplayIndex(navigationGroupSettingUIModel
				.getDisplayIndex());
		rawEntity.setResEmployeeUUID(navigationGroupSettingUIModel
				.getResEmployeeUUID());
		rawEntity.setCreatedBy(navigationGroupSettingUIModel.getCreatedBy());
		rawEntity.setRefSimAuthorObjectUUID(navigationGroupSettingUIModel
				.getRefSimAuthorObjectUUID());
		rawEntity.setServiceEntityName(navigationGroupSettingUIModel
				.getServiceEntityName());
		rawEntity.setRefAuthorActionCodeUUID(navigationGroupSettingUIModel
				.getRefAuthorActionCodeUUID());
		rawEntity.setRefSourceUUID(navigationGroupSettingUIModel
				.getRefSourceUUID());
	}

	public void convSystemToGroupUI(
			NavigationSystemSetting navigationSystemSetting,
			NavigationGroupSettingUIModel navigationGroupSettingUIModel) {
		if (navigationSystemSetting != null) {
			navigationGroupSettingUIModel
					.setRefSystemName(navigationSystemSetting.getName());
			navigationGroupSettingUIModel
					.setRefSystemId(navigationSystemSetting.getId());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convGroupDefItemToUI(NavigationItemSetting groupDefItem,
									 NavigationGroupSettingUIModel navigationGroupSettingUIModel) {
		if (groupDefItem != null) {
			navigationGroupSettingUIModel
					.setDefItemName(groupDefItem.getName());
			navigationGroupSettingUIModel.setDefItemId(groupDefItem.getId());
		}
	}
	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convAuthorizationObjectToGroupUI(
			AuthorizationObject groupAuthorizationObject,
			NavigationGroupSettingUIModel navigationGroupSettingUIModel) {
		if (groupAuthorizationObject != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(groupAuthorizationObject.getClient())) {
				navigationGroupSettingUIModel
						.setClient(groupAuthorizationObject.getClient());
			}
			navigationGroupSettingUIModel
					.setRefAuthorizationObjectName(groupAuthorizationObject
							.getName());
			navigationGroupSettingUIModel
					.setRefAuthorizationObjectId(groupAuthorizationObject
							.getId());
		}
	}

}
