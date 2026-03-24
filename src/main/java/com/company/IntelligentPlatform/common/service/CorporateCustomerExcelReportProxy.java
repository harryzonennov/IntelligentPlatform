package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.CorporateCustomerExcelModel;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportProxy;

@Service
public class CorporateCustomerExcelReportProxy extends ServiceExcelReportProxy{
	
	public static final String CONFIG_NAME = "CorporateCustomer";	

	@PostConstruct
	public void initConfig(){
		this.configureName = CONFIG_NAME;
		this.excelModelClass = CorporateCustomerExcelModel.class;
	}

}
