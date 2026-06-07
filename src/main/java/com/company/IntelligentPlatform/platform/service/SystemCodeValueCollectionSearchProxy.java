package com.company.IntelligentPlatform.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.dto.ServiceDocumentSettingSearchModel;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.*;
import com.company.IntelligentPlatform.platform.model.CorporateContactPerson;
import com.company.IntelligentPlatform.platform.model.CorporateCustomer;
import com.company.IntelligentPlatform.platform.model.ServiceDocumentSetting;
import com.company.IntelligentPlatform.platform.model.SystemCodeValueCollection;
import com.company.IntelligentPlatform.platform.model.SystemCodeValueUnion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SystemCodeValueCollectionSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected SystemCodeValueCollectionManager systemCodeValueCollectionManager;
	
	@Override
	public Class<?> getDocSearchModelCls() {
		return ServiceDocumentSettingSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return systemCodeValueCollectionManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		// start node:[SystemCodeValueCollection->SystemCodeValueUnion]
		return
				SearchModelConfigHelper.buildParentChildConfigure(SystemCodeValueCollection.class,
						SystemCodeValueUnion.class);
	}
}
