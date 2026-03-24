package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.CorporateDealerExcelModel;
import com.company.IntelligentPlatform.common.dto.ServiceExcelReportResponseModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportProxy;

@Service
public class CorporateDealerExcelReportProxy extends ServiceExcelReportProxy{
	
	public static final String CONFIG_NAME = "CorporateDealer";	
	
	protected CorporateCustomerManager corporateCustomerManager;

	@PostConstruct
	public void initConfig(){
		this.configureName = CONFIG_NAME;
		this.excelModelClass = CorporateDealerExcelModel.class;
	}

	@Override
	public void insertExcelBatchData(
			ServiceExcelReportResponseModel serviceExcelReportResponseModel,
			String modelName) throws ServiceExcelConfigException,
			AuthorizationException, LogonInfoException {		
		super.insertExcelBatchData(serviceExcelReportResponseModel, modelName);
	}

}
