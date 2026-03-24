package com.company.IntelligentPlatform.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.AuthorizationObjectUIModel;
// TODO-DAO: import ...AuthorizationObjectDAO;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ISystemAuthorizationObject;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.AuthorizationGroup;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.AuthorizationObjectConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Logic Manager CLASS FOR Service Entity [Authorization]
 * 
 * @author
 * @date Sun Dec 23 00:11:35 CST 2012
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
public class AuthorizationObjectManager extends ServiceEntityManager {

	public static final String Method_convertToAuthorizationObjectUICom = "convertToAuthorizationObjectUICom";

	public static final String Method_ConvAuthorizationObjectToUI = "convAuthorizationObjectToUI";

	public static final String Method_ConvUIToAuthorizationObject = "convUIToAuthorizationObject";

	public static final String Method_ConvAuthorizationGroupToUI = "convAuthorizationGroupToUI";

	// TODO-DAO: @Autowired

	// TODO-DAO: 	protected AuthorizationObjectDAO authorizationObjectDAO;

	@Autowired
	protected AuthorizationObjectConfigureProxy authorizationObjectConfigureProxy;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	@Autowired
	protected AuthorizationObjectSearchProxy authorizationObjectSearchProxy;

	private Map<String, Map<Integer, String>> authorizationObjectTypeMapLan = new HashMap<>();

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(authorizationObjectDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(authorizationObjectConfigureProxy);
	}

	public Map<Integer, String> initAuthorizationObjectTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.authorizationObjectTypeMapLan, AuthorizationObjectUIModel.class,
				"authorizationObjectType");
	}

	/**
	 * Get all system AuthorizationObject list
	 * 
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getAllAuthorizationObjectList(String client)
			throws ServiceEntityConfigureException {
		return getEntityNodeListByKey(null, null, AuthorizationObject.NODENAME,
				client, null);
	}

	public List<ServiceEntityNode> getSystemAuthorizationObjectList(String client) throws ServiceEntityConfigureException {
		return getEntityNodeListByKey(AuthorizationObject.AUTH_TYPE_SYS,
				AuthorizationObject.FIELD_AuthorizationObjectType,
				AuthorizationObject.NODENAME, client,
				null);
	}

	public List<ServiceEntityNode> getCrossUserSysAuthorizationObjectList(String client) throws ServiceEntityConfigureException {
		List<ServiceEntityNode> rawList = getSystemAuthorizationObjectList(client);
		if(ServiceCollectionsHelper.checkNullList(rawList)){
			return null;
		}
        return rawList.stream().filter(serviceEntityNode -> !ISystemAuthorizationObject.AOID_SELFUSER_DATA.equals(serviceEntityNode.getId())).collect(Collectors.toList());
	}

	public AuthorizationObject getAuthorizationObject(String uuid, String client, List<ServiceEntityNode> rawAOList) throws ServiceEntityConfigureException {
		ServiceEntityNode seNode = ServiceCollectionsHelper.filterSENodeOnline(uuid, rawAOList);
		if(seNode != null){
			return (AuthorizationObject) seNode;
		}
		return (AuthorizationObject) this.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, AuthorizationObject.NODENAME,
				client, rawAOList);
	}

	public void convAuthorizationObjectToUI(
			AuthorizationObject authorizationObject,
			AuthorizationObjectUIModel authorizationObjectUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (authorizationObject != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(authorizationObject, authorizationObjectUIModel);
			authorizationObjectUIModel.setId(authorizationObject.getId());
			authorizationObjectUIModel.setName(authorizationObject.getName());
			authorizationObjectUIModel.setServiceEntityName(authorizationObject.getServiceEntityName());
			authorizationObjectUIModel.setSimObjectArray(ServiceCollectionsHelper
					.convertStringToArray(authorizationObject
							.getSimObjectArray()));			

			authorizationObjectUIModel.setRefAGUUID(authorizationObject.getRefAGUUID());
			authorizationObjectUIModel.setNote(authorizationObject.getNote());
			authorizationObjectUIModel
					.setAuthorizationObjectType(authorizationObject
							.getAuthorizationObjectType());
			if (logonInfo != null) {
				Map<Integer, String> authorizationObjectTypeMap = initAuthorizationObjectTypeMap(logonInfo.getLanguageCode());
				authorizationObjectUIModel
						.setAuthorizationObjectTypeValue(authorizationObjectTypeMap
								.get(authorizationObject
										.getAuthorizationObjectType()));
			}
			authorizationObjectUIModel.setSubSystemAuthorNeed(authorizationObject.getSubSystemAuthorNeed());
			authorizationObjectUIModel.setSystemAuthorCheck(authorizationObject.getSystemAuthorCheck());
			if(logonInfo != null){
				authorizationObjectUIModel.setSubSystemAuthorNeedLabel(standardSwitchProxy.getSwitchValue(authorizationObjectUIModel.getSubSystemAuthorNeed()));
				authorizationObjectUIModel.setSystemAuthorCheckLabel(standardSwitchProxy.getSwitchValue(authorizationObjectUIModel.getSystemAuthorCheck()));
			}
		}
	}

	public void convUIToAuthorizationObject(
			AuthorizationObjectUIModel authorizationObjectUIModel,
			AuthorizationObject rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(authorizationObjectUIModel, rawEntity);
		if (!ServiceEntityStringHelper
				.checkNullString(authorizationObjectUIModel.getRefAGUUID())) {
			rawEntity.setRefAGUUID(authorizationObjectUIModel.getRefAGUUID());
		}
		if (authorizationObjectUIModel.getSimObjectArray() != null) {
			rawEntity.setSimObjectArray(ServiceCollectionsHelper
					.convStringArrayToString(authorizationObjectUIModel
							.getSimObjectArray()));
		}
		rawEntity
				.setAuthorizationObjectType(authorizationObjectUIModel
						.getAuthorizationObjectType());
		rawEntity.setSubSystemAuthorNeed(authorizationObjectUIModel.getSubSystemAuthorNeed());

	}

	public void convAuthorizationGroupToUI(
			AuthorizationGroup authorizationGroup,
			AuthorizationObjectUIModel authorizationObjectUIModel) {
		if (authorizationGroup != null) {
			authorizationObjectUIModel.setAuthorizationGroupId(authorizationGroup.getId());
			authorizationObjectUIModel.setAuthorizationGroupName(authorizationGroup.getName());
		}
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return this.authorizationObjectSearchProxy;
	}

}