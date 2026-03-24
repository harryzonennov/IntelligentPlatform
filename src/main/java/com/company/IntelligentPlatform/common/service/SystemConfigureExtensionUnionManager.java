package com.company.IntelligentPlatform.common.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.SystemConfigureExtensionUnionUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.SpringContextBeanService;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SystemCodeValueCollection;
import com.company.IntelligentPlatform.common.model.SystemConfigureElement;
import com.company.IntelligentPlatform.common.model.SystemConfigureExtensionUnion;
import com.company.IntelligentPlatform.common.model.SystemConfigureResource;

@Service
public class SystemConfigureExtensionUnionManager {

	public static final String METHOD_ConvSystemConfigureExtensionUnionToUI = "convSystemConfigureExtensionUnionToUI";

	public static final String METHOD_ConvSystemCodeValueCollectionToUI = "convSystemCodeValueCollectionToUI";

	public static final String METHOD_ConvUIToSystemConfigureExtensionUnion = "convUIToSystemConfigureExtensionUnion";

	public static final String METHOD_ConvResourceToExtensionUnionUI = "convResourceToExtensionUnionUI";

	public static final String METHOD_ConvElementToExtensionUnionUI = "convElementToExtensionUnionUI";

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected SpringContextBeanService springContextBeanService;
	
	@Autowired
	protected SystemConfigureCategoryManager systemConfigureCategoryManager;
	
	@Autowired
	protected SystemConfigureElementManager systemConfigureElementManager;
	
	@Autowired
	protected SystemConfigureResourceManager systemConfigureResourceManager;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	protected Logger logger = LoggerFactory.getLogger(SystemConfigureExtensionUnionManager.class);


	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), SystemConfigureResource.NODENAME,
						request.getUuid(), SystemConfigureElement.NODENAME, systemConfigureCategoryManager);
		docPageHeaderInputPara.setGenBaseModelList(new DocPageHeaderModelProxy.GenBaseModelList<SystemConfigureResource>() {
			@Override
			public List<com.company.IntelligentPlatform.common.controller.PageHeaderModel> execute(SystemConfigureResource systemConfigureResource) throws ServiceEntityConfigureException {
				if(systemConfigureResource != null){
					// in case parent is resource
					List<PageHeaderModel> pageHeaderModelList =
							systemConfigureResourceManager.getPageHeaderModelList(DocPageHeaderModelProxy.getDefRequest(systemConfigureResource),
									client);
					return pageHeaderModelList;
				} else {
					// In case parent is element
					SystemConfigureElement systemConfigureElement = (SystemConfigureElement) systemConfigureCategoryManager
							.getEntityNodeByKey(request.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID,
									SystemConfigureElement.NODENAME, client, null);
					if(systemConfigureElement == null){
						// should not happen, it means no parent element
						logger.error("No parent element for:systemConfigureElement" + request.getBaseUUID());
					}
					List<PageHeaderModel> pageHeaderModelList =
							systemConfigureResourceManager.getPageHeaderModelList(DocPageHeaderModelProxy.getDefRequest(systemConfigureElement),
									client);
					return pageHeaderModelList;
				}
			}
		});
		docPageHeaderInputPara.setGenHomePageModel(new DocPageHeaderModelProxy.GenHomePageModel<SystemConfigureExtensionUnion>() {
			@Override
			public com.company.IntelligentPlatform.common.controller.PageHeaderModel execute(SystemConfigureExtensionUnion systemConfigureExtensionUnion, com.company.IntelligentPlatform.common.controller.PageHeaderModel pageHeaderModel) throws ServiceEntityConfigureException {
				// How to render current page header
				pageHeaderModel.setHeaderName(systemConfigureExtensionUnion.getId());
				if(!ServiceEntityStringHelper.checkNullString(systemConfigureExtensionUnion.getName())){
					pageHeaderModel.setHeaderName(systemConfigureExtensionUnion.getId() + "-" + systemConfigureExtensionUnion.getName());
				}
				return pageHeaderModel;
			}
		});
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	public void convSystemConfigureExtensionUnionToUI(
			SystemConfigureExtensionUnion systemConfigureExtensionUnion,
			SystemConfigureExtensionUnionUIModel systemConfigureExtensionUnionUIModel)
			throws ServiceEntityInstallationException {
		if (systemConfigureExtensionUnionUIModel != null) {
			systemConfigureExtensionUnionUIModel
					.setUuid(systemConfigureExtensionUnion.getUuid());
			systemConfigureExtensionUnionUIModel
					.setParentNodeUUID(systemConfigureExtensionUnion
							.getParentNodeUUID());
			systemConfigureExtensionUnionUIModel
					.setRootNodeUUID(systemConfigureExtensionUnion
							.getRootNodeUUID());
			systemConfigureExtensionUnionUIModel
					.setClient(systemConfigureExtensionUnion.getClient());
			systemConfigureExtensionUnionUIModel
					.setConfigureValueName(systemConfigureExtensionUnion
							.getConfigureValueName());
			systemConfigureExtensionUnionUIModel
					.setConfigureValueId(systemConfigureExtensionUnion
							.getConfigureValueId());
			systemConfigureExtensionUnionUIModel
					.setConfigureValue(systemConfigureExtensionUnion
							.getConfigureValue());
			systemConfigureExtensionUnionUIModel
					.setName(systemConfigureExtensionUnion.getName());
			systemConfigureExtensionUnionUIModel
					.setId(systemConfigureExtensionUnion.getId());
			systemConfigureExtensionUnionUIModel
					.setNote(systemConfigureExtensionUnion.getNote());
			systemConfigureExtensionUnionUIModel
					.setRefCodeValueUUID(systemConfigureExtensionUnion
							.getRefCodeValueUUID());
		}
	}

	public void convSystemCodeValueCollectionToUI(
			SystemCodeValueCollection systemCodeValueCollection,
			SystemConfigureExtensionUnionUIModel systemConfigureExtensionUnionUIModel)
			throws ServiceEntityInstallationException {
		if (systemConfigureExtensionUnionUIModel != null
				&& systemCodeValueCollection != null) {
			systemConfigureExtensionUnionUIModel
					.setRefCodeValueId(systemCodeValueCollection.getId());
			systemConfigureExtensionUnionUIModel
					.setRefCodeValueName(systemCodeValueCollection.getName());
		}
	}

	public void convUIToSystemConfigureExtensionUnion(
			SystemConfigureExtensionUnionUIModel systemConfigureExtensionUnionUIModel,
			SystemConfigureExtensionUnion rawEntity)
			throws ServiceEntityInstallationException {
		if (rawEntity != null && systemConfigureExtensionUnionUIModel != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureExtensionUnionUIModel
							.getUuid())) {
				rawEntity.setUuid(systemConfigureExtensionUnionUIModel
						.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureExtensionUnionUIModel
							.getParentNodeUUID())) {
				rawEntity
						.setParentNodeUUID(systemConfigureExtensionUnionUIModel
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureExtensionUnionUIModel
							.getRootNodeUUID())) {
				rawEntity.setRootNodeUUID(systemConfigureExtensionUnionUIModel
						.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureExtensionUnionUIModel
							.getClient())) {
				rawEntity.setClient(systemConfigureExtensionUnionUIModel
						.getClient());
			}
			rawEntity.setId(systemConfigureExtensionUnionUIModel.getId());
			rawEntity.setName(systemConfigureExtensionUnionUIModel.getName());
			rawEntity.setNote(systemConfigureExtensionUnionUIModel.getNote());
			rawEntity
					.setConfigureValueName(systemConfigureExtensionUnionUIModel
							.getConfigureValueName());
			rawEntity.setConfigureValueId(systemConfigureExtensionUnionUIModel
					.getConfigureValueId());
			rawEntity.setConfigureValue(systemConfigureExtensionUnionUIModel
					.getConfigureValue());
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureExtensionUnionUIModel
							.getRefCodeValueUUID())) {
				rawEntity
						.setRefCodeValueUUID(systemConfigureExtensionUnionUIModel
								.getRefCodeValueUUID());
			}

		}
	}



	public void convResourceToExtensionUnionUI(
			SystemConfigureResource systemConfigureResource,
			SystemConfigureExtensionUnionUIModel systemConfigureExtensionUnionUIModel)
			throws ServiceEntityInstallationException {
		if (systemConfigureExtensionUnionUIModel != null) {
			systemConfigureExtensionUnionUIModel
					.setParentNodeId(systemConfigureResource.getId());
		}
	}

	public void convElementToExtensionUnionUI(
			SystemConfigureElement systemConfigureElement,
			SystemConfigureExtensionUnionUIModel systemConfigureExtensionUnionUIModel)
			throws ServiceEntityInstallationException {
		if (systemConfigureExtensionUnionUIModel != null) {
			systemConfigureExtensionUnionUIModel
					.setParentNodeId(systemConfigureElement.getId());
		}
	}



}
