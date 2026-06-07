package com.company.IntelligentPlatform.platform.service;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.dto.MaterialUIModel;
import com.company.IntelligentPlatform.platform.dto.ServiceExcelReportResponseModel;
import com.company.IntelligentPlatform.platform.service.AuthorizationException;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.platform.service.ServiceExcelReportProxy;

@Service
public class MaterialExcelReportProxy extends ServiceExcelReportProxy{
	
	public static final String CONFIG_NAME = "Material";	

	@PostConstruct
	public void initConfig(){
		this.configureName = CONFIG_NAME;
		this.excelModelClass = MaterialUIModel.class;
	}

	@Override
	public void insertExcelBatchData(
			ServiceExcelReportResponseModel serviceExcelReportResponseModel,
			String modelName) throws ServiceExcelConfigException,
			AuthorizationException, LogonInfoException {
		super.insertExcelBatchData(serviceExcelReportResponseModel, modelName);
	}

}
