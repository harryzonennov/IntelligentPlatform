package com.company.IntelligentPlatform.platform.service;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.dto.CorporateCustomerExcelModel;
import com.company.IntelligentPlatform.platform.service.ServiceExcelReportProxy;

@Service
public class CorporateCustomerExcelReportProxy extends ServiceExcelReportProxy{
	
	public static final String CONFIG_NAME = "CorporateCustomer";	

	@PostConstruct
	public void initConfig(){
		this.configureName = CONFIG_NAME;
		this.excelModelClass = CorporateCustomerExcelModel.class;
	}

}
