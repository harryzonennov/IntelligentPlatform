package com.company.IntelligentPlatform.platform.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.SystemCodeValueCollection;
import com.company.IntelligentPlatform.platform.model.SystemCodeValueUnion;

@Component
public class SystemCodeValueCollectionServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SystemCodeValueCollection.NODENAME, nodeInstId = SystemCodeValueCollection.SENAME)
	protected SystemCodeValueCollectionUIModel systemCodeValueCollectionUIModel;

	@IServiceUIModuleFieldConfig(nodeName = SystemCodeValueUnion.NODENAME, nodeInstId = SystemCodeValueUnion.NODENAME)
	protected List<SystemCodeValueUnionServiceUIModel> systemCodeValueUnionUIModelList = new ArrayList<>();

	public SystemCodeValueCollectionUIModel getSystemCodeValueCollectionUIModel() {
		return this.systemCodeValueCollectionUIModel;
	}

	public void setSystemCodeValueCollectionUIModel(
			SystemCodeValueCollectionUIModel systemCodeValueCollectionUIModel) {
		this.systemCodeValueCollectionUIModel = systemCodeValueCollectionUIModel;
	}

	public List<SystemCodeValueUnionServiceUIModel> getSystemCodeValueUnionUIModelList() {
		return this.systemCodeValueUnionUIModelList;
	}

	public void setSystemCodeValueUnionUIModelList(
			List<SystemCodeValueUnionServiceUIModel> systemCodeValueUnionUIModelList) {
		this.systemCodeValueUnionUIModelList = systemCodeValueUnionUIModelList;
	}

}
