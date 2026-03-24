package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.ServiceDocConsumerUnionSearchModel;
import com.company.IntelligentPlatform.common.controller.ServiceDocConsumerUnionUIModel;
// TODO-DAO: import ...ServiceDocConsumerUnionDAO;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceDocConsumerUnion;
import com.company.IntelligentPlatform.common.model.ServiceDocConsumerUnionConfigureProxy;

/**
 * Logic Manager CLASS FOR Service Entity [ServiceDocConsumerUnion]
 *
 * @author
 * @date Fri May 06 22:49:44 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class ServiceDocConsumerUnionManager extends ServiceEntityManager {

	// TODO-DAO: @Autowired

	// TODO-DAO: 	protected ServiceDocConsumerUnionDAO serviceDocConsumerUnionDAO;

	@Autowired
	protected ServiceDocConsumerUnionConfigureProxy serviceDocConsumerUnionConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;
	
	@Autowired
	protected BsearchService bsearchService;

	public ServiceDocConsumerUnionManager() {
		super.seConfigureProxy = new ServiceDocConsumerUnionConfigureProxy();
		// TODO-DAO: super.serviceEntityDAO = new ServiceDocConsumerUnionDAO();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(serviceDocConsumerUnionDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(serviceDocConsumerUnionConfigureProxy);
	}

	public Map<String, String> getUIModelTitleMap() throws IOException {
		Locale locale = ServiceLanHelper.getDefault();
		String path = SEEditorController.class.getResource("").getPath();
		String resFileName = "ServiceUIModelTitleResource";
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	public String getUIModelTitle(String key,
			Map<String, String> uiModelTitleMap) {
		String uiModelTitle = uiModelTitleMap.get(key);
		if (ServiceEntityStringHelper.checkNullString(uiModelTitle)) {
			return key;
		} else {
			return uiModelTitleMap.get(key);
		}
	}

	public void convServiceDocConsumerUnionToUI(
			ServiceDocConsumerUnion serviceDocConsumerUnion,
			ServiceDocConsumerUnionUIModel serviceDocConsumerUnionUIModel) {
		if (serviceDocConsumerUnion != null
				&& serviceDocConsumerUnionUIModel != null) {
			serviceDocConsumerUnionUIModel
					.setUiModelType(serviceDocConsumerUnion.getUiModelType());
			serviceDocConsumerUnionUIModel
					.setUiModelTypeFullName(serviceDocConsumerUnion
							.getUiModelTypeFullName());
			serviceDocConsumerUnionUIModel.setName(serviceDocConsumerUnion
					.getName());
			serviceDocConsumerUnionUIModel.setId(serviceDocConsumerUnion
					.getId());
			serviceDocConsumerUnionUIModel.setNote(serviceDocConsumerUnion
					.getNote());
			serviceDocConsumerUnionUIModel.setUuid(serviceDocConsumerUnion
					.getUuid());
			serviceDocConsumerUnionUIModel
					.setRootNodeUUID(serviceDocConsumerUnion.getRootNodeUUID());
			serviceDocConsumerUnionUIModel
					.setParentNodeUUID(serviceDocConsumerUnion
							.getParentNodeUUID());
			serviceDocConsumerUnionUIModel.setClient(serviceDocConsumerUnion
					.getClient());
			serviceDocConsumerUnionUIModel
					.setI18nFullPath(serviceDocConsumerUnion.getI18nFullPath());
		}
	}

	public void convUIToServiceDocConsumerUnion(
			ServiceDocConsumerUnionUIModel serviceDocConsumerUnionUIModel,
			ServiceDocConsumerUnion serviceDocConsumerUnion) {
		if (serviceDocConsumerUnion != null
				&& serviceDocConsumerUnionUIModel != null) {
			serviceDocConsumerUnion
					.setUiModelType(serviceDocConsumerUnionUIModel
							.getUiModelType());
			serviceDocConsumerUnion
					.setUiModelTypeFullName(serviceDocConsumerUnionUIModel
							.getUiModelTypeFullName());
			serviceDocConsumerUnion.setName(serviceDocConsumerUnionUIModel
					.getName());
			serviceDocConsumerUnion.setId(serviceDocConsumerUnionUIModel
					.getId());
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConsumerUnionUIModel.getUuid())) {
				serviceDocConsumerUnion.setUuid(serviceDocConsumerUnionUIModel
						.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConsumerUnionUIModel
							.getRootNodeUUID())) {
				serviceDocConsumerUnion
						.setRootNodeUUID(serviceDocConsumerUnionUIModel
								.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConsumerUnionUIModel
							.getParentNodeUUID())) {
				serviceDocConsumerUnion
						.setParentNodeUUID(serviceDocConsumerUnionUIModel
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConsumerUnionUIModel.getClient())) {
				serviceDocConsumerUnion
						.setClient(serviceDocConsumerUnionUIModel.getClient());
			}
			serviceDocConsumerUnion.setNote(serviceDocConsumerUnionUIModel
					.getNote());
			serviceDocConsumerUnion
					.setI18nFullPath(serviceDocConsumerUnionUIModel
							.getI18nFullPath());
		}
	}
	

	public List<ServiceEntityNode> searchInternal(
			ServiceDocConsumerUnionSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(ServiceDocConsumerUnion.SENAME);
		searchNodeConfig0.setNodeName(ServiceDocConsumerUnion.NODENAME);
		searchNodeConfig0.setNodeInstID(ServiceDocConsumerUnion.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}
}
